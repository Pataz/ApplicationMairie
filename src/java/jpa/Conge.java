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
public class Conge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Version
    private Timestamp version;
    @ManyToOne
    private Annee anneeCivile;
    @ManyToOne
    private ProgressionAgent progressionAgent;
    @ManyToOne
    private PlanningConge planningConge;
    @ManyToOne
    private Conge conge;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @Lob
    @Column(length = 500)
    private String observation;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private String etat;
    private String joursReste;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Annee getAnneeCivile() {
        return anneeCivile;
    }

    public void setAnneeCivile(Annee anneeCivile) {
        this.anneeCivile = anneeCivile;
    }

    public String getJoursReste() {
        return joursReste;
    }

    public void setJoursReste(String joursReste) {
        this.joursReste = joursReste;
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

    public PlanningConge getPlanningConge() {
        return planningConge;
    }

    public void setPlanningConge(PlanningConge planningConge) {
        this.planningConge = planningConge;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Conge getConge() {
        return conge;
    }

    public void setConge(Conge conge) {
        this.conge = conge;
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
        if (!(object instanceof Conge)) {
            return false;
        }
        Conge other = (Conge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    public Conge(Etudiant etudiant, FiliereOption filiereoption, AnneeAcademique anneeAcademique, String etat) {
//        this.etudiant = etudiant;
//        this.filiereoption = filiereoption;
//        this.anneeAcademique = anneeAcademique;
//        this.etat = etat;
//    }
}
