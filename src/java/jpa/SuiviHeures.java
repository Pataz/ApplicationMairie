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
public class SuiviHeures implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Version
    private Timestamp version;
    @ManyToOne
    private ProgressionAgent progressionAgent;
    @ManyToOne
    private VariableSalaire variableSalaires;
    @ManyToOne
    private Mois mois;
    @Lob
    @Column(length = 500)
    private String observation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Temporal(TemporalType.DATE)
    private Date dateSuivi;
    private String nbre;

    public SuiviHeures() {
    }

    public SuiviHeures(SuiviHeures absence) {
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

    public VariableSalaire getVariableSalaires() {
        return variableSalaires;
    }

    public void setVariableSalaires(VariableSalaire variableSalaires) {
        this.variableSalaires = variableSalaires;
    }

    public String getNbre() {
        return nbre;
    }

    public void setNbre(String nbre) {
        this.nbre = nbre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateSuivi() {
        return dateSuivi;
    }

    public void setDateSuivi(Date dateSuivi) {
        this.dateSuivi = dateSuivi;
    }

    public Mois getMois() {
        return mois;
    }

    public void setMois(Mois mois) {
        this.mois = mois;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }


    public ProgressionAgent getProgressionAgent() {
        return progressionAgent;
    }

    public void setProgressionAgent(ProgressionAgent progressionAgent) {
        this.progressionAgent = progressionAgent;
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
        if (!(object instanceof SuiviHeures)) {
            return false;
        }
        SuiviHeures other = (SuiviHeures) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
