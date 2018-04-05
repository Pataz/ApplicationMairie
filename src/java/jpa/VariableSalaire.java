package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class VariableSalaire implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    String id;
    private String libelle;
    private String partEmploye;
    private String partEmployeur;
    @Enumerated(EnumType.STRING)
    private TypeVariable typeVariable;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Version
    private Timestamp version;

    @PrePersist
    public void initDateCreation() {
        dateCreation = new Date();
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getPartEmploye() {
        return partEmploye;
    }

    public void setPartEmploye(String partEmploye) {
        this.partEmploye = partEmploye;
    }

    public String getPartEmployeur() {
        return partEmployeur;
    }

    public void setPartEmployeur(String partEmployeur) {
        this.partEmployeur = partEmployeur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TypeVariable getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(TypeVariable typeVariable) {
        this.typeVariable = typeVariable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VariableSalaire)) {
            return false;
        }
        VariableSalaire other = (VariableSalaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.libelle;
    }

}
