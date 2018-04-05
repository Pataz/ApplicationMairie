/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Offre les méthodes necessaires pour opérations avec les fichiers de sauvegarde des données biométriques.
 * s'occupe des opérations basiques avec les fichiers telles que, l'enregistrement, la suppression, et la
 * lecture des données.
 * 
 * @author xess
 */
public class UtilFS {
    
    
    /**
     * crée un nouveau fichier à l'emplacement défini.
     * @param donnee    le contenu binaire du fichier
     * @param chemin    le repertoire de destination
     * @param nomFichier    le nom du fichier
     * @param force forcer la création du fichier en écrasant un éventuel fichier existant
     * @throws java.nio.file.FileAlreadyExistsException
     * @throws IOException Si un problème survient 
     */
    static void creerFichierDonnee(byte[] donnee, String chemin, String nomFichier, boolean force) 
            throws FileAlreadyExistsException, IOException{
        Path fullPath = Paths.get(chemin, nomFichier);
        if(!UtilFS.existe(chemin))
            Files.createDirectories(Paths.get(chemin));
        if(force)
            Files.copy(new ByteArrayInputStream(donnee), fullPath, StandardCopyOption.REPLACE_EXISTING);
        else
            Files.copy(new ByteArrayInputStream(donnee), fullPath);
    }
    
    /**
     * crée un nouveau fichier en écrasant un éventuel fichier existant du même nom.
     * @param donnee
     * @param chemin
     * @param nomFichier
     * @throws FileAlreadyExistsException
     * @throws IOException 
     */
    public static void remplacerFichierDonnee(byte[] donnee, String chemin, String nomFichier) 
            throws FileAlreadyExistsException, IOException{
        creerFichierDonnee(donnee, chemin, nomFichier, true);
    }
    
    /**
     * crée un noouveau fichier; l'opération échoue si le fichier existe déja.
     * @param donnee
     * @param chemin
     * @param nomFichier
     * @throws FileAlreadyExistsException
     * @throws IOException 
     */
    public static void  sauvegarderFichierDonnee(byte[] donnee, String chemin, String nomFichier) 
            throws FileAlreadyExistsException, IOException{
        creerFichierDonnee(donnee, chemin, nomFichier, false);
    }
    
    /**
     * supprimer le fichier et retourne le statut de l'opération ( <i>true</i> si succès);
     * @param chemin
     * @param nomFichier
     * @return
     * @throws IOException 
     */
    public static boolean supprimerFichierDonnee(String chemin, String nomFichier) 
            throws IOException{
        Path fullPath = Paths.get(chemin, nomFichier);
        return Files.deleteIfExists(fullPath);
    }
    /**
     * retourne le contenu du fichier.
     * @param chemin
     * @param nomFichier
     * @return 
     * @throws IOException 
     */
    public static byte[] extraireDonneeFichier(String chemin, String nomFichier) throws IOException {
        Path fullPath = Paths.get(chemin, nomFichier);
        return Files.readAllBytes(fullPath);
    }
    
    /**
     * vérifie l'existence du fichier.
     * @param chemin
     * @param nomFichier
     * @return 
     */
    static boolean existe(String chemin, String nomFichier) {
        Path fullPath = Paths.get(chemin, nomFichier);
        return Files.exists(fullPath);
    }
    
    static boolean existe(String chemin) {
        return Files.exists(Paths.get(chemin));
    }  
}
