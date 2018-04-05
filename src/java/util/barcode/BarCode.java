/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util.barcode;

import java.io.Serializable;
/**
 *
 * @author xess
 * un objet de cette classe encapsule l'image du code barre dont les <br/>
 * caractéristique de base sont: type MIME, données(tableau de byte).
 * 
 */
public class BarCode implements Serializable {
    
    /**
     * Type de l'image e.g. image/x-png, image/jpeg
     * 
     */
    private String mediaType;
    private byte[] data;
    
    public BarCode() {        
    }
    
    public BarCode(String mediaType, byte[] data) {
        this(mediaType);
        this.data = data;
    }
    
    public BarCode(String mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
  
}
