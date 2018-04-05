/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Patrick
 */
@Entity
public class DetailSalaire implements Serializable {

    @Id
    private String id;
    @ManyToOne
    private Salaire salaire;
    @ManyToOne
    private VariableSalaire variableSalaire;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private String montant;

    public DetailSalaire(){

    }

    public Salaire getSalaire() {
        return salaire;
    }

    public void setSalaire(Salaire salaire) {
        this.salaire = salaire;
    }

    public VariableSalaire getVariableSalaire() {
        return variableSalaire;
    }

    public void setVariableSalaire(VariableSalaire variableSalaire) {
        this.variableSalaire = variableSalaire;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetailSalaire)) {
            return false;
        }
        DetailSalaire other = (DetailSalaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /*****
     * 
     * @return 
     */
    public Boolean isNull() {
        if (this.getVariableSalaire() != null) {
            if ((this.getVariableSalaire().getId() != null) && (this.getVariableSalaire().getLibelle() != null)) {
                if (this.getVariableSalaire().getId().equals("") && this.getVariableSalaire().getLibelle().equals("")) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
