/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author xess
 */
@Entity
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id private String id;    
    @Lob byte[] face;
    @Lob byte[] profilGauche;
    @Lob byte[] profilDroit;
    @Version
    private Timestamp version;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getFace() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }

    public byte[] getProfilGauche() {
        return profilGauche;
    }

    public void setProfilGauche(byte[] profilGauche) {
        this.profilGauche = profilGauche;
    }

    public byte[] getProfilDroit() {
        return profilDroit;
    }

    public void setProfilDroit(byte[] profilDroit) {
        this.profilDroit = profilDroit;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }  
    
    @Override
    public String toString() {
        ToStringBuilder builder = new 
            ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE)
                .append("face", face == null ? 0 : face.length)
                .append("profilDroit", profilDroit == null ? 0 : profilDroit.length)
                .append("profilGauche", profilGauche == null ? 0 : profilGauche.length);
        return builder.toString();
    }
       
}
