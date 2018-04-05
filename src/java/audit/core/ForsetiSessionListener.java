package audit.core;

import audit.ejb.AuditFacade;
import javax.ejb.EJB;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author xess
 */
@WebListener
public class ForsetiSessionListener implements HttpSessionListener {
    
    public final static String AUDIT_INFO = "auditInfo";
    @EJB AuditFacade facade;
    
    /**
     * enregistre la session au niveau de EJB {@link AuditFacade} pour utilisation ultérieure.
     * @param se 
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        
    }
    
    /**
     * enregistrer une entrée d'audit de déconnexion et supprime la référence de la session du EJB {@link AuditFacade}.
     * @param se 
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        
    }    
}
