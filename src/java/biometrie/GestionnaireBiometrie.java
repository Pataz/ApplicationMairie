/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

import jpa.Agent;
import jpa.Photo;

/**
 *
 * @author xess
 */
public abstract class GestionnaireBiometrie {
    
   
    public abstract void sauvergarderPhotos(Agent apprenant, byte[] face)
            throws Exception;
    
    /**
     * retrouver et charger les photo d'un individu du dépôt de stockage.
     * @param apprenant
     * @return
     * @throws Exception 
     */
    public abstract Photo extrairePhoto(Agent apprenant)
            throws Exception;
    
    
}
