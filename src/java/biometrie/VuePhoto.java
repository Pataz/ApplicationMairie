/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

/**
 *
 * @author xess
 */
public enum VuePhoto {
    FACE("photo_face"),
    PROFIL_GAUCHE("photo_gauche"),
    PROFIL_DROIT("photo_droit");
    
    String vue;
    VuePhoto(String vue) {
        this.vue = vue;
    }
    
    @Override
    public String toString() {
        return vue;
    }
}
