package audit.ejb;

//import ejb.ParametreFacade;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author xess
 */
@Singleton
public class AuditFacade {    
    @PersistenceContext EntityManager entityMgr;
    
    public static final String AUDIT_ACTIF = "audit.actif";
    public static final String AUDIT_AUTH_ACTIF = "audit.auth.actif";
    
    /**
     * active ou désactive l'audit dans l'application.
     */
    private boolean auditActif = false;
    /**
     * active ou désactive l'audit des sessions (connexions, déconnexions etc des utilisateurs).
     */
    private boolean auditAuthActif;
    /**
     * active ou désactive l'audit des dossier.
     */
    
    static Logger logger = Logger.getLogger("audit.ejb.AuditFacade");
    
    /**
     * initialise les propriétés de la classe grâce aux paramètres
     * de l'application.
     */
//    @PostConstruct
//    public void init() {
//
//    }
    
    private void testInit() {
        auditActif = true;
        auditAuthActif = true;
    }
    
    /**
     * Lit les paramètres de fonctionnement relatifs à l'audit dans le fichier de configuration.
     * lit en premier la valeur du paramètre <i>audit.actif</i>. Si la valeur<br/>
     * est <code>true</code>, lit les valeurs des autres paramètres à savoir
     * <i>audit.auth.actif, audit.dossier.actif</i> et <i>audit.methode.actif</i>
     * et les affectent respectivement aux propriétés <code>auditAuthActif, auditDossierActif,
     * et auditMethodeActif</code>.<p/>
     * Si la valeur est <code>false</code> les trois propriétés précédemment mentionnées prennent
     * des valeurs par défaut; soit respectivement <code>true, false et false</code>.
     */
    
}
