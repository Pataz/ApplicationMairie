/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Ordinateur
 */
@Entity
public class PretEtat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Version
    private Timestamp version;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private VariableSalaire variableSalaires;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private int montant;
    private int reste;

    public PretEtat() {
    }

    public PretEtat(PretEtat absence) {
    }

    @PrePersist
    public void initDateCreation() {
        dateCreation = new Date();
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public int getReste() {
        return reste;
    }

    public void setReste(int reste) {
        this.reste = reste;
    }

    public VariableSalaire getVariableSalaires() {
        return variableSalaires;
    }

    public void setVariableSalaires(VariableSalaire variableSalaires) {
        this.variableSalaires = variableSalaires;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the code fields are not set
        if (!(object instanceof PretEtat)) {
            return false;
        }
        PretEtat other = (PretEtat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
