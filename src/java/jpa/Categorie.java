package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Categorie implements Serializable {
    @Id
    private String code;
    private String libelle;
    private int echelle; 
    private String salaireMensuel;
    private String salaireUnitaire;
    @ManyToOne
    private SousCategorie sousCategorie;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Version
    private Timestamp version;
    

    @PrePersist
    public void initDateCreation() {
        dateCreation = new Date();
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSalaireMensuel() {
        return salaireMensuel;
    }

    public void setSalaireMensuel(String salaireMensuel) {
        this.salaireMensuel = salaireMensuel;
    }

    public String getSalaireUnitaire() {
        return salaireUnitaire;
    }

    public void setSalaireUnitaire(String salaireUnitaire) {
        this.salaireUnitaire = salaireUnitaire;
    }

    
    public Date getDateCreation() {
        return dateCreation;
    }

    public int getEchelle() {
        return echelle;
    }

    public void setEchelle(int echelle) {
        this.echelle = echelle;
    }

    public Timestamp getVersion() {
        return version;
    }

    public SousCategorie getSousCategorie() {
        return sousCategorie;
    }

    public void setSousCategorie(SousCategorie sousCategorie) {
        this.sousCategorie = sousCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        return !((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code)));
    }
}
