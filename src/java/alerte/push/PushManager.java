/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerte.push;

/**
 *
 * @author xess
 */
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.websocket.CloseReason;
import javax.websocket.OnError;
import org.apache.log4j.Logger;

@ServerEndpoint("/ping")
@Singleton
public class PushManager {
    @Resource(lookup="grh/pushDestination") Queue destination;
    @Inject JMSContext context;
    
    final static Logger logger = Logger.getLogger("alerte.push.PushManager");
    final HashMap<String, Session> sessionMap = new HashMap<>();
   
    /**
     * expression du filtre qui sélectionne les messages destinés un utilisateur spécifique.
     */
    static final String MSG_SELECTOR = "destinataire = '%s' OR destinataire ='ALL'";
    static final String EXPEDITEUR = "expediteur";
    static final String DESTINATAIRE = "destinataire";
    

    @PostConstruct
    public void init() {
        logger.info("Server end point initialized!");
    }
    
    public String getUserName(Session s) {
        return s.getUserPrincipal().getName();
    }    
    
    /**
     * Construit l'agent JMS qui recoit les messages et l'enregistre de dans le registre des agents.
     * @param session la session web socket.
     */
    @OnOpen
    public void onOpen(Session session) {
        String username =  getUserName(session);
        if(sessionMap.containsKey(username)) {
            logger.warn(String.format("l'utilisateur %s a déjà une session websocket.", username));
        } else {
            sessionMap.put(username, session);
            logger.info(String.format("liaison websocket etablie, session %s ", username, session.getId()));
        }
    } 
    
    /**
     * Arrête l'agent JMS et le supprime du régistre.
     * @param reason
     * @param session 
     */
    @OnClose
    public void onClose(CloseReason reason, Session session) {
        String username = getUserName(session);        
        if(sessionMap.containsKey(username))                        
            sessionMap.remove(username, session);          
        logger.info(String.format("session websocket fermée %s, %s", username, reason.getReasonPhrase()));
    }
    
    /**
     * when web socket message is received from user agent. 
     * Cependant, on attend pas de message venant de l'utilisateur.
     * @param message user message content
     * @param session user web session
     * @throws IOException
     * @throws InterruptedException 
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        String username = getUserName(session);
        logger.info(String.format("message websocket reçu. expediteur=%s, texte= %s", username, message));        
    }  
    
    @OnError
    public void onError(Throwable t) {
        logger.error("problème - communication websocket", t);
    }      
    
    
        
    @Schedule(info = "Envoie les Push" , hour = "*" , minute = "*" ,second = "*/30")
    public void getPush() {
        for(String username : sessionMap.keySet()) {
            Session session = sessionMap.get(username);
            try (JMSConsumer consumer = context.createConsumer(destination, String.format(MSG_SELECTOR, username))){
                Message msg = consumer.receive(1000);
                if(msg != null) {
                    PushMessage push = transform((TextMessage) msg);
                    logger.info("message push reçu " + push);
                    try {
                        session.getBasicRemote().sendText(push.toString());
                        msg.acknowledge();
                    } catch(IOException ex) {
                        logger.error("echec d'envoi de push", ex);
                    } catch(JMSException ex) {
                        logger.error("echec accusé reception du message", ex);
                    }
                }                
            }
        }
    }
      
    
    public static PushMessage transform(TextMessage msg) {
        PushMessage push = new PushMessage();
        try {            
            push.setDateCreation(new Date(msg.getJMSTimestamp()));
            push.setExpediteur(msg.getStringProperty(EXPEDITEUR));
            push.setDestinataire(msg.getStringProperty(DESTINATAIRE));
            push.setTexte(msg.getText());
        } catch (JMSException ex) {}
        return push;        
    }
}
