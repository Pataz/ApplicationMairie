package alerte;

import alerte.entity.Notification;
import ejb.AgentFacade;
import ejb.EvenementFacade;
import jpa.Agent;
import jpa.Evenement;
import jpa.Corps;
import jpa.ExerciceFonction;
import jpa.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import jpa.Direction;
import jpa.Fonction;
import org.apache.log4j.Logger;
import util.JsfUtil;

/**
 * Interface pour la gestion des notifications et alertes
 *
 * @author xess
 */
//@Named
@Singleton
public class NotificationFacade {

    @PersistenceContext
    EntityManager entityMgr;
    Logger logger = Logger.getLogger("alerte.NotificationFacade");
    int delaiDefaut = 10;
    final static String DELAI_NOTIF_DEFAUT = "default.notif.delay";
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private EvenementFacade evenementFacade;

//    @PostConstruct
//    public void init() {
//        try {
//            if (params.isSet(DELAI_NOTIF_DEFAUT)) {
//                delaiDefaut = params.getIntValue(DELAI_NOTIF_DEFAUT);
//            }
//        } catch (Exception e) {
//        }
//    }

    /**
     * Crée et ajoute une nouvelle notification dont le statut est actif et
     * désactive toute autre notification rendue obsolète par cette
     * dernière.<p/>
     * En effet une notification provient d'un evénement, et cet événement peut
     * clôturer d'autres événements, rendant ainsi leurs notifications
     * obsolètes.<p/>
     * Par conséquent chaque nouvel événement peut créer une notification et
     * désactiver d'autres.
     *
     * @param evt l'evenement à l'origine de la notification
     * @param agent
     * @param dirOrig
     * @param destination
     * @param poste
     * @param desc La description de la notification
     */
    public void ajouterUneNotification(Evenement evt, Agent agent, Direction dirOrig, Direction destination,
            Fonction poste, String desc) {
        Notification notif = creerUneNotification(evt, agent, dirOrig, destination, poste, desc);
        entityMgr.persist(notif);
        desactiverNotifCorrespondant(agent, evt);
    }

    /**
     * ajoute une notification avec précision de la date limite. La date limite
     * calculée à l'aide du champ délai de l'événement
     * <p/>
     * est donc ignorée et écrasée par la date passée en paramètre.
     *
     * @param destination
     * @param agent
     * @param dirOrig
     * @param poste
     * @see #ajouterUneNotification(jpa.Evenement, jpa.Dossier, jpa.Service,
     * java.lang.Object, jpa.Fonction, java.lang.String)
     *
     * @param evt l'evenement à l'origine de la notification
     * @param desc La description de la notification
     * @param dateExpiration la date limite en remplacement de celle calculér
     * grâce au délai.
     */
    public void ajouterUneNotification(Evenement evt, Agent agent, Direction dirOrig,
            Direction destination, Fonction poste, String desc, Date dateExpiration) {
        Notification notif = creerUneNotification(evt, agent, dirOrig, destination, poste, desc);
        notif.setDateLimite(dateExpiration);
        entityMgr.persist(notif);
        desactiverNotifCorrespondant(agent, evt);
    }

    /**
     * extrait les <i>n</i> premiers éléments d'une liste.
     *
     * @param n le nombre d'éléments à extraire.
     * @param liste
     * @return la liste des n éléments issus de l'extraction.
     */
    public List<Notification> nPremiers(int n, List<Notification> liste) {
        liste.sort(comparateurDateCreation);
        return liste.subList(0, n - 1);
    }

    public List<Notification> ordonner(List<Notification> liste) {
        liste.sort(comparateurDateCreation);
        return liste;
    }

    /**
     * Retourne une liste ordonnée des notifications simples d'un utilisateur.
     * <p/>
     * La liste contient les notifications relatives au corps au service et au
     * fonction de l'utilisateur.
     *
     * @param poste
     * @return une liste
     */
    public List<Notification> trouverSimpleNotifUtilisateur(ExerciceFonction poste) {
        if (poste == null) {
            return null;
        }
        return trouverNotifUtilisateur(poste, 1);
    }

    /**
     * Retourne une liste ordonnée des alertes d'un utilisateur.
     * <p/>
     * La liste contient les alertes relatives au corps au service et au
     * fonction de l'utilisateur.
     *
     * @param poste
     * @return une liste.
     */
    public List<Notification> trouverAlerteUtilisateur(ExerciceFonction poste) {
        if (poste == null) {
            return null;
        }
        return trouverNotifUtilisateur(poste, 2);
    }

    /**
     * Liste toutes les notifications simples accessibles à un utilisateur.
     * <p/>
     * Un utilisateur a droit à trois types de notifications:<br/>
     * <ul>
     * <li>les notifications relatives à son corps d'appartenance</li>
     * <li>les notifications relatives à son service d'appartenance</li>
     * <li>les notifications relatives à sa fonction dans un service</li>
     * </ul>
     *
     * @param poste la fonction occupée dans un service dans cette période.
     * @param statut Les notifications recherchées. prends les valeurs 1, 2 et 3
     * respectivement pour simples, alertes et les deux à la fois.
     * @return
     */
    public List<Notification> trouverNotifUtilisateur(ExerciceFonction poste, int statut) {
        List<Notification> resultat = new ArrayList<>();
        resultat.addAll(trouverNotification(poste.getDirection(), poste.getFonction(), statut));
        resultat.addAll(trouverNotification(poste.getDirection(), null, statut));
        resultat.sort(comparateurDateCreation);

        return resultat;
    }

    /**
     * Extrait uniquement les alertes relatives à un dossier et à une cible.
     *
     * @param cible service ou corps cible.
     * @param fonction eventuelle fonction de l'utilisateur concerné.
     * @return
     */
    public List<Notification> trouverAlerte(Direction cible, Fonction fonction) {
        return trouverNotification(cible, fonction, 2);
    }

    /**
     * Extrait uniquement les notifications simples relatives à un service.
     *
     * @param cible service ou corps cible.
     * @param fonction eventuelle fonction de l'utilisateur concerné.
     * @return
     */
    public List<Notification> trouverSimpleNotification(Direction cible, Fonction fonction) {
        return trouverNotification(cible, fonction, 1);
    }

    /**
     * Trouve les notifications en filtrant suivant leur statut.
     * <p/>
     * Permet d'extraire les notifications simples, les alertes ou les deux
     * suivant la valeur du paramètre <code>statut</code>. Ceci est une fonction
     * de support (utilisée par d'autres).
     *
     * @param cible la destination de la notification
     * @param fonction la fonction (optionnel)
     * @param statut la valeur du statut des notif recherchées. 1, 2 et 3
     * respectivement pour simple, alerte et les deux à la fois
     * @return
     */
    protected List<Notification> trouverNotification(Direction cible, Fonction fonction, int statut) {
        List<Notification> notifs = trouverNotificationParCible(cible, fonction);
        List<Notification> simpleNotif = new ArrayList<>();
        //notifs.stream().filter((Notification e) -> e.getStatut() == statut).forEach(simpleNotif::add);
        notifs.stream().filter((Notification e) -> e.getStatut() == (statut & 1) || e.getStatut() == (statut & 2)).
                sorted(comparateurDateCreation).forEachOrdered(simpleNotif::add);
        return simpleNotif;
    }

    /**
     * Trouve les notifications actives relatives à un dossier un service/corps
     * cible et/ou une fonction.
     *
     * @param cible
     * @param fonction la fonction concerné dans le service de destination. peut
     * être <code>null</code> dans le cas où la notification est destinée à tout
     * le service ou à un corps.
     * @return
     */
    private List<Notification> trouverNotificationParCible(Direction cible, Fonction fonction) {
        TypedQuery<Notification> requete;
        List<Notification> liste = null;
        try {
            if (fonction == null) {
                requete = entityMgr.createNamedQuery("trouverActifParDestinationSeule", Notification.class);
//                requete.setParameter(1, trouverIdCible(cible));
            } else {
                requete = entityMgr.createNamedQuery("trouverActifDestEtFonction", Notification.class);
//                requete.setParameter(1, trouverIdCible(cible)).setParameter(2, fonction.getCode());
            }
            liste = requete.getResultList();
        } catch (Exception e) {
            throw new NotificationException("Echec de recherche des notifications", e);
        }
        return liste;
    }

    /**
     * Interface pour créer une nouvelle notification dont le statut est actif.
     *
     * @param evt L'evenement à l'origine de la notification
     * @param dossier Le dossier concernée par l'événement
     * @param svcOrig Le service à l'origine
     * @param destination corps ou service de destination de la notification.
     * @param fonction La fonction éventuelle à laquelle est adressée la
     * notification. Peut être <code>null</code>.
     * @param desc
     * @return L'instance de notification construite sur la base des paramètres.
     */
    private Notification creerUneNotification(Evenement evt, Agent agent, Direction dirOrig,
            Direction destination, Fonction poste, String desc) {
        Notification notif = new Notification();
        try {

            notif.setDateCreation(new Date());
            notif.setStatut(1);
            notif.setAgent(agent);
            notif.setOrigine(dirOrig);
            int delai = evt.getDelai();
            notif.setDateLimite(calculerDateLimite(notif.getDateCreation(), delai <= 0 ? delaiDefaut : delai));
            notif.setEvenement(evt);
            notif.setDescription(desc);
//            TypeCible type;
//            if (destination instanceof Corps) {
//                type = TypeCible.CORPS;
//            } else if (destination instanceof Service) {
//                type = TypeCible.SERVICE;
//            } else {
//                throw new IllegalArgumentException("Type de cible non identifié. Les types attendus sont Corps/Service");
//            }
            notif.setDestination(destination);
            if (poste != null) {
                notif.setFonction(poste);
            }
            notif.setDestination(destination);
        } catch (Exception e) {
            throw new NotificationException("Echec de création de la notification", e);
        }
        return notif;
    }

    /**
     * Trouve le libelle (qui sert de id) de la cible par introspection grâce à
     * l'api de reflection standard.
     *
     * @param cible objet de du type <code>corps</code> ou <code>service</code>.
     * @return
     * @throws Exception si la méthode "getLibelle" n'est pas trouvée. Ne
     * devrait pas se produire.
     */
//    private String trouverIdCible(Object cible) throws Exception {
//        return (String) cible.getClass().getMethod("getLibelle").invoke(cible);
//    }
    /**
     * Désactive toutes les notifications rendues obsolètes par une nouvelle
     * notification(événement) dont le type est en paramètre.<p/>
     * @param agent
     * @param evtNotif le type de l'événement de la nouvelle notification.
     */
    public void desactiverNotifCorrespondant(Agent agent, Evenement evtNotif) {
        List<Evenement> listEvt = trouverEvtCorrespondant(evtNotif);
        listEvt.forEach(e -> desactiverNotification(agent, e));
    }

    /**
     * Désactive les notifications relatives à un dossier et à l'événement.
     *
     * @param agent dossier concerné
     * @param evt Type des événements à desactiver
     */
    //private void desactiverNotification(Dossier dossier, Evenement evt) {
    public void desactiverNotification(Agent agent, Evenement evt) {
        TypedQuery<Notification> requete = entityMgr.createNamedQuery("trouverNotifaDesactiver", Notification.class);
        List<Notification> notifs = requete.setParameter(1, agent.getId()).
                setParameter(2, evt.getCode()).getResultList();
        notifs.forEach((Notification e) -> e.setStatut(0));
    }

    /**
     * Extrais les événements dont les notifications seront désactivées par
     * celui passé en argument.
     *
     * @param evt le type de l'évenement dont on recherche les correspondant à
     * désactiver.
     * @return une liste
     */
    protected List<Evenement> trouverEvtCorrespondant(Evenement evt) {
        TypedQuery<String> requete = entityMgr.createNamedQuery("trouverEvenementsCibles", String.class);
        return requete.setParameter(1, evt.getCode()).getResultList().stream().
                map(e -> entityMgr.find(Evenement.class, e)).collect(Collectors.toList());
    }

    /**
     * Désactive toutes les notifications actives d'un dossier.
     *
     * @param agent
     */
    public void desactiverNotifDossier(Agent agent) {
        trouverNotificationActive().stream().filter(e -> e.getAgent().getMatricule().equals(agent.getMatricule())).
                forEach(e -> ((Notification) e).setStatut(0));
    }

    /**
     * Recherche toutes les notifications qui sont actives.<br/>
     * Il s'agit des notifications dont le statut est supérieur à 0
     * .<p/>
     * est utilisé par le Timer Service
     *
     * @return
     */
    private List<Notification> trouverNotificationActive() {
        List<Notification> resultat;
        CriteriaBuilder cb = entityMgr.getCriteriaBuilder();
        CriteriaQuery cquery = cb.createQuery();
        Root<Notification> from = cquery.from(Notification.class);
        Predicate filtre = cb.gt(from.get("statut"), 0);
        cquery.select(from).where(filtre);
        TypedQuery<Notification> requete = entityMgr.createQuery(cquery);
        resultat = requete.getResultList();
        return resultat;
    }

    /**
     * Calcule la date limite d'une notification sur la base du délai accordé.
     *
     * @param dateDebut date de création de la notification
     * @param delai nombres de jour avant échéance.
     * @return la date correspondante à <code>dateDébut + delai</code>
     */
    public Date calculerDateLimite(Date dateDebut, int delai) {
        Date resultat;
        LocalDate debut = dateDebut.toInstant().
                atZone(ZoneId.systemDefault()).toLocalDate();
        resultat = Date.from(debut.plusDays(delai).
                atStartOfDay(ZoneId.systemDefault()).toInstant());
        return resultat;
    }

    /**
     * Parcours la table des notifications, retrouve les notifications expirées
     * et change leur status en 'alerte'.
     */
    @Schedule(hour = "2,14", minute = "5", persistent = false)
    public void scannerNotification() {
        List<Notification> notifs = trouverNotificationActive();
        Date courante = new Date();
        notifs.stream().filter(e -> !e.estAlerte() && courante.after(e.getDateLimite())).
                forEach((Notification e) -> e.setStatut(2)); // la notification devient une alerte        
    }

    /**
     * comparateur de date par ordre décroissant. La date la plus récente est en
     * première position.
     */
    Comparator<Notification> comparateurDateCreation = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Date o1Date = ((Notification) o1).getDateCreation();
            Date o2Date = ((Notification) o2).getDateCreation();
            if (o1Date.equals(o2Date)) {
                return 0;
            } else {
                return o1Date.before(o2Date) ? 1 : -1;
            }
        }
    };//.reversed();

    public void test() {
        try {
            logger.info("\n==>notif test starts");
            logger.info("entity manager initialisé");
//            Dossier dossier1 = entityMgr.find(Dossier.class, "11012015000001");
//            Dossier dossier2 = entityMgr.find(Dossier.class, "11012015000007");
//            logger.info("Dossier localisé " + dossier1.getNumeroRP());
            Evenement evt = entityMgr.find(Evenement.class, "AUD-001");
            Evenement evt2 = entityMgr.find(Evenement.class, "DOS-001");
            Service svc1 = entityMgr.find(Service.class, "2101-GRE");
            Service svc2 = entityMgr.find(Service.class, "2101-PAR");
            Service svc3 = entityMgr.find(Service.class, "CH-003");
            Fonction fonction1 = entityMgr.find(Fonction.class, "Te");
            Fonction fonction2 = entityMgr.find(Fonction.class, "In");
            Corps corps = entityMgr.find(Corps.class, "CO-001");
            //Notification n = creerUneNotification(evt, dossier1, svc1, svc2, fonction1);
            //System.out.println(n);
            //logger.info(n);
//            ajouterUneNotification(evt2, dossier1, svc1, svc2, null, "");
            //scannerNotification();
            //desactiverNotification(dossier1, evt);
            //logger.info("recherche des alertes");
            //trouverAlerte(svc3, fonction2).forEach(e -> logger.info("\n"+e+"\n"));
            //logger.info("recherche notif active");
            //trouverNotificationActive().forEach(e -> logger.info("\n"+e+"\n"));
        } catch (Exception e) {
            System.out.println("ERREUR " + e);
            logger.fatal("erreur notif", e);
        } finally {
            logger.info("\n==>notif test ends");
        }
    }

    public Notification findNotificationAgent(Agent agent, Evenement evt) {
        TypedQuery<Notification> requete = entityMgr.createNamedQuery("trouverNotifaDesactiver", Notification.class);
        List<Notification> notifs = requete.setParameter(1, agent.getId()).
                setParameter(2, evt.getCode()).getResultList();
        if (notifs.isEmpty()) {
            return null;
        } else {
            return notifs.get(0);
        }
    }

    /**
     * Parcours la table des notifications, retrouve les notifications expirées
     * et change leur status en 'alerte'.
     */
//    @Schedule(hour = "2,14", minute = "5", persistent = false)
    public void saveNofificationRetraite() {
        List<Agent> listeAgents = agentFacade.findAgentsInterne();
        LocalDateTime datedujour = LocalDate.now().atStartOfDay();
        Date dateJour = JsfUtil.convertirEnDate(datedujour);
        Date dateBefore = null;
        Date dateRetraite = null;
        for (Agent agent : listeAgents) {
            dateRetraite = JsfUtil.differenceFunction(agent.getPersonne().getDateNaissance(), 60);
            dateBefore = JsfUtil.differenceFunction(agent.getPersonne().getDateNaissance(), 59);
            if (JsfUtil.getDaysBetween(dateJour, dateBefore) <= 0) {
                if (findNotificationAgent(agent, evenementFacade.find("ALTR")) != null) {
                } else {
                    creerDepartRetraite(evenementFacade.find("ALTR"), agent, dateRetraite, "Départ à la retraite");
                }
            }
        }
//        Date dateBefore = JsfUtil.differenceFunction(datedujour);
//          notifs.stream().filter(e -> !e.estAlerte() && courante.after(e.getDateLimite())).
//                forEach((Notification e) -> e.setStatut(2)); // la notification devient une alerte        
    }

    private void creerDepartRetraite(Evenement evt, Agent agent, Date dateDep, String desc) {
        Notification notif = new Notification();
        try {
            System.out.println("dateDep1==>" + dateDep);
            notif.setDateCreation(new Date());
            dateDep.setHours(23);
            notif.setStatut(1);
            notif.setAgent(agent);
            notif.setDateLimite(dateDep);
            notif.setEvenement(evt);
            notif.setDescription(desc);
            entityMgr.persist(notif);
        } catch (Exception e) {
            throw new NotificationException("Echec de création de la notification", e);
        }
//        return notif;
    }

    public List<Notification> trouverNotificationByEvt(String evt) {
        TypedQuery<Notification> requete = entityMgr.createNamedQuery("trouverNotifRetraite", Notification.class);
        List<Notification> notifs = requete.setParameter(1, evt).getResultList();
        return notifs;
    }
}
