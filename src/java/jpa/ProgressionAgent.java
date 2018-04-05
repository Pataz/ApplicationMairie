package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class ProgressionAgent implements Serializable {
    @Id
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Version
    private Timestamp version;
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Statut statut;
    @ManyToOne
    private Evenement evenement;
    @ManyToOne
    private Annee anneeCivile;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateProgression;
    @Lob
    @Column(length = 500)
    private String observation;
    @Transient
    private boolean checkbox;

    public ProgressionAgent() {
    }

    public ProgressionAgent(ProgressionAgent progressionAgent) {
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Annee getAnneeCivile() {
        return anneeCivile;
    }

    public void setAnneeCivile(Annee anneeCivile) {
        this.anneeCivile = anneeCivile;
    }

    @PrePersist
    public void initDateCreation() {
        dateCreation = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Date getDateProgression() {
        return dateProgression;
    }

    public void setDateProgression(Date dateProgression) {
        this.dateProgression = dateProgression;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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
        if (!(object instanceof ProgressionAgent)) {
            return false;
        }
        ProgressionAgent other = (ProgressionAgent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public ProgressionAgent(Agent agent, Evenement evenement, Statut statut, Utilisateur utilisateur, Annee anneeCivile, String observation, Date dateProgression) {
        this.agent = agent;
        this.evenement = evenement;
        this.statut = statut;
        this.utilisateur = utilisateur;
        this.observation = observation;
        this.anneeCivile = anneeCivile;
        this.dateProgression = dateProgression;
    }
}
