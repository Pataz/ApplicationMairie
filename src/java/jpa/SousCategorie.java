package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Aaron
 */
@Entity
public class SousCategorie implements Serializable {

    @Id
    private String code;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private String libelle;
    @Version
    private Timestamp version;
    @ManyToOne
    private CategorieBase categorieBase;

    public String getLibelle() {
        if (libelle != null) {
            System.out.println("libelle " + libelle);
            return libelle;
        } else {
            return "";
        }
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public CategorieBase getCategorieBase() {
        return categorieBase;
    }

    public void setCategorieBase(CategorieBase categorieBase) {
        this.categorieBase = categorieBase;
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
        if (!(object instanceof SousCategorie)) {
            return false;
        }
        SousCategorie other = (SousCategorie) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.libelle;
    }

    public void reset() {
        this.libelle = "";
    }
}
