/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;


import jpa.Agent;
import jpa.Photo;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.EnumMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * offre le métier nécessaire pour les opérations avec les fichiers de données de biométrie(empreintes et photos).
 * @author xess
 */
public class GestionnaireFS extends GestionnaireBiometrie {
    
    /**
     * repertoire racine ou sont stockés tous fichiers biométriques i.e photos et empreintes
     */
    final String cheminSauvegarde;
    /**
     * L'application travaille avec les images de types JPEG.
     */
    public static final String EXTENSION = "JPG";
    
    static Logger logger = Logger.getLogger("biometrie.GestonnaireFS");
    
      
    public GestionnaireFS(String chemin) {
        if(!UtilFS.existe(chemin))
            throw new IllegalArgumentException("chemin spécifiée introuvable: "+chemin);
        cheminSauvegarde = chemin;
    }    
    
    /**
     * donne le chemin du repertoire ou se situe les données spécifiques d'une personne.
     * le chemin complet/absolu e stockage des fichiers biométriques d'un particulier est déterminé 
     * par la concaténation du chemin du repertoire racine {@code cheminSauvegarde} et d'un chemin
     * relatif obtenu par les composantes ci-après dans l'ordre:
     * <li>le dernier chiffre</li>
     * <li>les deux chiffres précedents le dernier chiffre</li>
     * <li>les deux chiffres précedents les trois derniers chiffres</li>
     * <li>le reste de l'identifiant(les chiffres avant les 5 derniers)</li>
     * @param id l'identifiant du personnage
     * @return 
     */
    String trouverCheminStockage(String id) {
        String[] composantChemin = {StringUtils.substring(id, -1),
                                    StringUtils.substring(id, -3, -1),
                                    StringUtils.substring(id, -5, -3),
                                    StringUtils.substring(id, 0, -5)};
        return cheminSauvegarde + "/" + StringUtils.join(composantChemin, "/");
    }
   
    @Override
    public void sauvergarderPhotos( Agent apprenant, byte[] face) 
            throws BiometricOperationException{
        byte[][] photos = {face};
        try {
            creerFichierPhotos(apprenant.getId(), photos, true);
        } catch(Exception e) {
            throw new  BiometricOperationException(e);
        }
    }
    

    @Override
    public Photo extrairePhoto(Agent apprenant) throws Exception {
        Photo photo = new Photo();
        photo.setFace(extraireVuePhoto(apprenant, VuePhoto.FACE));
        return photo;
    } 
    
    
    /**
     * crée les fichiers physiques des trois photos standards face, profil gauche et droit.
     * le tableau en argument contient les données des trois photos dans cet ordre.
     * @param id
     * @param photos tableau des photos (chaque photos est du type <code>byte[]</code>.
     * @param ecraser s'il faut écraser les données existantes ou pas.
     * @throws java.lang.Exception
     */
    void creerFichierPhotos(String id, byte[][] photos, boolean ecraser) throws Exception{
        synchronized(id) {
            String chemin = trouverCheminStockage(id);
            UtilFS.creerFichierDonnee(photos[0], chemin, VuePhoto.FACE.toString() + "." + EXTENSION, ecraser);
            }
    }  
    
    byte[] extraireVuePhoto(Agent apprenant, VuePhoto vue) {
        String id = apprenant.getId();
        String chemin = trouverCheminStockage(id);
        byte[] resultat = null;
        try {
            String nomFichier = vue.toString() + "." + EXTENSION;
            if(UtilFS.existe(chemin, nomFichier))
                return UtilFS.extraireDonneeFichier(chemin, nomFichier);            
        } catch(Exception e) {
            logger.error(String.format("Echec extraction de la photo %s de la personne id: %s", vue, id), e);
        }
        return resultat;       
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println(" ==>  DEBUT TEST");
        
        Agent personne = new Agent();
        personne.setId("123456789");
        GestionnaireFS mgr = new GestionnaireFS("D:/xess/ienv/work/projects/forsetiPenal/biometrie");
        
        byte[] myData = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "Diagramme Progression.jpg");
        byte[] myData2 = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "packageDossier.jpg");
        byte[] myData3 = UtilFS.extraireDonneeFichier("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "Procedure_Penale.jpg");
        
        
        System.out.printf("%d bytes lus%n", myData.length);
        //UtilFS.supprimerFichierDonnee("D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "DiagrammAudiance2.jpg");
        //System.out.printf("Suppression reussie%n");
        //UtilFS.sauvegarderFichierDonnee(myData, "D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "diag_audience.JPG");
        //UtilFS.remplacerFichierDonnee(myData, "D:/xess/ienv/work/projects/forsetiPenal/DiagrammeClasse", "diag_audience.JPG");
        //System.out.println("chemin de stockage " + mgr.trouverCheminStockage(personne.getId()));
        //InputStream[] input = new InputStream[10];
        InputStream[] gauche = new InputStream[5];
        InputStream[] droite = new InputStream[5];
        gauche[1] = new ByteArrayInputStream(myData);
        droite[2] = new ByteArrayInputStream(myData2);        
        //mgr.creerFichierEmpreinte("123456789", input, true);
        //mgr.sauvergarderEmpreinte(personne, gauche, droite);
        //Empreinte empreinte = mgr.extraireEmpreinte(personne);        
        //System.out.printf("empreinte lue : %s", empreinte);
        //mgr.creerFichierPhotos(personne.getId(), new byte[][] {myData, myData2, myData3}, true);
        Photo photo = mgr.extrairePhoto(personne);
        System.out.println("extraction photo reussie\n" + photo);
        
        
        System.out.println (" ==>  FIN TEST");  
    }
}
