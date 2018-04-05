/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerte.push;

import  util.JsfUtil;
import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.DependsOn;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author xess
 */

@Singleton
//@Startup
//@DependsOn("ParametreFacade")
public class PushFacade {      
    
    @Resource(lookup = "grh/pushDestination") Queue destination; 
    @Inject JMSContext context;
    
    static final String ALL_DEST = "ALL";    
    final static String PUSH_TIME_TO_LIVE = "push.timetolive"; // valeur entière en nombres d'heures.    
    private int timeToLive = 72_000_000; // deux heures en millisecondes     
    
    final static Logger logger = Logger.getLogger("alerte.push.PushFacade");
//    
//    @PostConstruct
//    public void init() {
//        try {
//            if (paramFacade.isSet(PUSH_TIME_TO_LIVE))
//                timeToLive = paramFacade.getIntValue(PUSH_TIME_TO_LIVE)*3_600_000; // conversion en millisecondes            
//        } catch (Exception e) {
//            logger.error("echec - lecture du paramètre " + PUSH_TIME_TO_LIVE, e);
//        }
//        logger.info("initialisation PushFacade complète!!");            
//    }
    
    @PreDestroy
    public void destroy () {
        context.close();        
    }
       
                                    
    /**
     * envoie le push à la destination spécifiée.
     * En réalité le message est envoyé au service JMS.
     * @param texte contenu textuel du message à transmettre
     * @param expediteur celui qui envoie le push.
     * @param destinataire peut être <code>null</code>; auquel cas le message est destiné à tout les utilisateurs
     */
    public void push(String expediteur, String destinataire, String texte ) {
        try {
            TextMessage msg = composerMessage(expediteur, 
                    destinataire == null || destinataire.isEmpty() ? ALL_DEST: destinataire, texte);
            logger.info("message a envoyer "+ PushManager.transform(msg));
            context.createProducer().setTimeToLive(timeToLive).send(destination, msg);
            logger.info("succès - push envoyé :" + PushManager.transform(msg));
        } catch(JMSException e) {
           logger.error("echec - generation de push", e);
        }        
    }
    
    TextMessage composerMessage(String expediteur, String destinataire, String texte) throws  JMSException{
        TextMessage msg = context.createTextMessage(texte);
//        msg.setStringProperty(EXPEDITEUR, expediteur);
//        msg.setStringProperty(DESTINATAIRE, destinataire);
        return msg;
    }
    
    
    public void parcourir() {        
        try {
            JsfUtil.logInfo(logger, "consulter les messages");
            QueueBrowser browser = context.createBrowser(destination);
            //QueueBrowser browser = context.createBrowser(destination, String.format(MSG_SELECTOR, "Aague"));            
            for(Enumeration e = browser.getEnumeration(); e.hasMoreElements();)
                JsfUtil.logInfo(logger, "push recu -> " +(PushManager.transform((TextMessage)e.nextElement())));
            JsfUtil.logInfo(logger, "consultation terminée");
        } catch (JMSException ex) {
            JsfUtil.logError(logger, "ERREUR RECEPTION " + ex);
        }        
    }
    
}
