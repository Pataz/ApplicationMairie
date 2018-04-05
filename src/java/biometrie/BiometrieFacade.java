/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import static biometrie.ModeStockage.*;

import jpa.Agent;
import jpa.Photo;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 *
 * @author xess
 */
@Stateless

@TransactionManagement(TransactionManagementType.BEAN)
public class BiometrieFacade {
    
    @PersistenceContext EntityManager mgr;
    @Resource UserTransaction trx;   

    /**
     * le nom du paramètre qui contrôle la manière dont les données biométriques
     * sont persistées. le paramètre prend d'une des valeurs:
     * <li><b>FS</b> pour le stockage sur le système de fichiers</li>
     * <li><b>DB</b> pour le stockage dans la base de donnée relationnelle(par
     * défaut)</li>
     * <li><b>FS_DB</b> les deux modes de précedents sont activés.</li>
     * <li><b>NONE</b> aucun mode de stockage n'est choisi. la fontionnalité sera ignoré</li>
     * 
     */
    static final String BIOMETRIE_STOCKAGE = "biometrie.modeStockage";

    /**
     * indique le repertoire racine ou les données biométriques sont stockées.
     */
    static final String BIOMETRIE_FS_CHEMIN = "biometrie.cheminStockage";

    static Logger logger = Logger.getLogger("biometrie.BiometrieFacade");
    
    // par défaut on enregistre les données sur le système de fichiers
    ModeStockage modeStockage = FS;
    // le chemin de stockage des données sur le système de fichiers
    String cheminStockage = null;
    final String confurationMessage = "les paramétres de la biométrie ne sont pas configurés";

    EnumMap<ModeStockage, GestionnaireBiometrie> gestionnaireMap = new EnumMap<>(ModeStockage.class);

    /**
     * initialise les paramètres de fonctionnement.
     */
    @PostConstruct
    public void init() {        
      configurer();
//        testconfigurer();
        logger.info("mode de stockage " + modeStockage);
        logger.info("liste gestionnaire stockage " + gestionnaireMap);
    }

    public void testconfigurer() {
//           modeStockage = DB;
        modeStockage = FS;  
//        cheminStockage = "D:/xess/ienv/work/projects/forsetiPenal/biometrie";
        cheminStockage = "C:/Users/Ordinateur/Pictures/eEducation/photos";
        initGestionnaireStockage();
    }

    private void configurer() {
//        if (params.isSet(BIOMETRIE_STOCKAGE)) {
//            modeStockage = ModeStockage.valueOf(params.getStringValue(BIOMETRIE_STOCKAGE));
//        }
        if (modeStockage.equals(NONE)) {
            logger.info("Le stockage des données biométriques n'est pas activé.");
        } else {
            //lit le paramètres cheminStockage
//            cheminStockage = params.getStringValue(BIOMETRIE_FS_CHEMIN);
            if (cheminStockage == null) {
                logger.error("le chemin de stockage des fichiers biométriques n'est pas défini, la fonctionnalité sera donc ignorée.");
            }
            initGestionnaireStockage();
        }
    }

    /**
     * renseigne les registreGestionnaires de stockages actifs dans le registre
     * des registreGestionnaires
     */
    private void initGestionnaireStockage() {
        switch (modeStockage) {
            case FS:
                initStockageFS();
                break;
            case DB:
//                gestionnaireMap.put(DB, new GestionnaireBD(mgr, trx));
                gestionnaireMap.put(DB, new GestionnaireBD());
                break;
            case FS_DB:
                initStockageFS();
                gestionnaireMap.put(DB, new GestionnaireBD());
//                gestionnaireMap.put(DB, new GestionnaireBD(mgr, trx));
                break;
        }
    }

    /**
     * initialise le gestionnaire de stockage sur système de fichiers si le
     * chemin de stockage est défini.
     */
    private void initStockageFS() {
        try {
            if (cheminStockage != null) {
                //registreGestionnaires.add(new GestionnaireFS(cheminStockage));
                gestionnaireMap.put(FS, new GestionnaireFS(cheminStockage));
            }
        } catch (IllegalArgumentException e) { // le chemin spécifié n'est pas trouvé
            logger.error("echec initialisation Stockage sur système de fichier", e);
        }
    }

    GestionnaireFS selectionnerGestionnaireFS() {
        return (GestionnaireFS) selectionnerGestionnaire(FS);
    }

    GestionnaireBD selectionnerGestionnaireDB() {
        return (GestionnaireBD) selectionnerGestionnaire(DB);
    }

    /**
     * selectionner un gestionnaire si il existe dans le registre des
     * Gestionnaires configurés.
     *
     * @param mode le type de Gestionnaire de stockage FS ou DB.
     * @return {@code null} si le gestionnaire recherché n'est pas configurée et
     * n'existe pas dans la liste.
     */
    GestionnaireBiometrie selectionnerGestionnaire(ModeStockage mode) {
        GestionnaireBiometrie resultat = null;
        if (!gestionnaireMap.isEmpty()) {
            return gestionnaireMap.get(mode);
        }
        return resultat;
    }

   

    /**
     * enregistrer les photos suivant le type de stockage choisi.
     *
     * @param apprenant
     * @param personne
     * @param face
     * @param profilGauche
     * @param profilDroit
     * @throws Exception
     */
    public void enregistrerPhotos(Agent apprenant, byte[] face)
            throws Exception {
        System.out.println("enregistrerPhotos personne ==> " + apprenant);
        Validate.notEmpty(gestionnaireMap, confurationMessage);
        System.out.println("gestionnaireMap.values() ==> " + gestionnaireMap.values());
        for (GestionnaireBiometrie e : gestionnaireMap.values()) {
            System.out.println("e ==> " + e);
            System.out.println("personne ==> " + apprenant);
            System.out.println("face ==> " + Arrays.toString(face));
            e.sauvergarderPhotos(apprenant, face);
        }
    }

    

    /**
     * retrouve et charge les photos d'un individu depuis un des dépôts de
     * stockage configurés.
     *
     * @param apprenant
     * @return
     * @throws Exception
     */
    public Photo chargerDonneePhoto(Agent apprenant) throws Exception {
        Validate.notEmpty(gestionnaireMap, confurationMessage);
        Photo resultat;
        // essaie de rechercher en premier sur le système de fichiers et ensuite dans la base de donnée.
        try {
            GestionnaireBiometrie g = selectionnerGestionnaireFS();
            resultat = g != null ? g.extrairePhoto(apprenant) : selectionnerGestionnaireDB().extrairePhoto(apprenant);
        } catch (Exception e) {
            logger.error("erreur de chargement des données empreintes sur FS", e);
            resultat = selectionnerGestionnaireDB().extrairePhoto(apprenant);
        }
        return resultat;
    }

    public static void main(String[] args) throws Exception {
//        byte[] myData = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "Diagramme Progression.jpg");
//        byte[] myData2 = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "packageDossier.jpg");
//        byte[] myData3 = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "Procedure_Penale.jpg");
//        PersonnePhysique personne = new PersonnePhysique();
//        EtatCivil etatcivil = new EtatCivil();
//        etatcivil.setId("123456");
//        personne.setId("123456789");
//        personne.setEtatCivil(etatcivil);
//        logger.info("donnee d'enregistrement initialisées");
//        BiometrieFacade facade = new BiometrieFacade();
//        EntityManagerFactory fcx = Persistence.createEntityManagerFactory("forsetiPenalPU");
//        EntityManager mng = fcx.createEntityManager();
//        facade.init();
//        mng.merge(personne);
//        facade.enregistrerPhotos(personne, myData, myData2, myData3);
        //Empreinte empreinte = facade.chargerDonneeEmpreinte(personne);
        //System.out.println(empreinte);        
    }
}
