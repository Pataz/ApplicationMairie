/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  biometrie;


import jpa.Agent;
import jpa.Photo;
import java.io.InputStream;
import java.lang.reflect.Method;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * S'occupe des opérations de biométrie avec la base de données. Utilise les
 * entités prédéfinies pour effectuer les opérations.
 *
 * @author xess
 */
public class GestionnaireBD extends GestionnaireBiometrie {

    static Logger logger = Logger.getLogger("biometrie.GestionnaireBD");
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }

    public GestionnaireBD() {
    }

    

    @Override
    public void sauvergarderPhotos(Agent apprenant, byte[] face)
            throws Exception {
 
//        Photo photo = apprenant.getPhotos();
//        logger.info("objet photo recupere " + photo);
//        System.out.println("objet photo recupere " + photo);
//        if (photo == null) {
//            photo = new Photo();
//            em.persist(photo);
//            System.out.println("nouvel objet photo créé pour personne physique " + photo);
//            }
//         em.merge(apprenant);
//        trx.commit();
        logger.info(String.format("Enregistrement de la photo reussi pour %s", apprenant));
    }

    /**
     *
     * @param apprenant
     * @return
     * @throws Exception
     */
    @Override
    public Photo extrairePhoto(Agent apprenant) throws Exception {
        return null;
    }

   
}
