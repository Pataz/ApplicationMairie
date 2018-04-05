package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Compteur implements Serializable {

    @Id
    private String code;
    private String valeur;

    
    @Version
    private Timestamp version;    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    public Compteur() {
    }
    
    public Timestamp getVersion() {
        return version;
    }
    
    @PrePersist
    public void initDateCreation()
    {
        dateCreation = new Date();
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Compteur(String table, String valeur) {
        this.code = table;
        this.valeur = valeur;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Compteur)) {
            return false;
        }
        Compteur other = (Compteur) object;
        return !((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code)));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    
}