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

@Entity
public class PlanningConge implements Serializable {

    @Id
    private String id;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Annee anneeCivile;
    @ManyToOne
    private PlanningConge planningConge;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Version
    private Timestamp version;
    @Temporal(TemporalType.DATE)
    private Date jdd;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private boolean defaultexe = false;
    private String etat;
    @Lob
    @Column(length = 500)
    private String observation;

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

    public Date getJdd() {
        return jdd;
    }

    public void setJdd(Date jdd) {
        this.jdd = jdd;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Annee getAnneeCivile() {
        return anneeCivile;
    }

    public void setAnneeCivile(Annee anneeCivile) {
        this.anneeCivile = anneeCivile;
    }

    public PlanningConge getPlanningConge() {
        return planningConge;
    }

    public void setPlanningConge(PlanningConge planningConge) {
        this.planningConge = planningConge;
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

    public boolean isDefaultexe() {
        return defaultexe;
    }

    public void setDefaultexe(boolean defaultexe) {
        this.defaultexe = defaultexe;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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
        if (!(object instanceof PlanningConge)) {
            return false;
        }
        PlanningConge other = (PlanningConge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
