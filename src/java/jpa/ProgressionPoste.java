package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class ProgressionPoste implements Serializable {

    @Id
    private String id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Version
    private Timestamp version;
    @ManyToOne
    private Poste poste;
    @ManyToOne
    private Corps corps;
    @ManyToOne
    private Direction direction;
    @ManyToOne
    private ProgressionAgent progressionAgent;
    @ManyToOne
    private Service service;
    @ManyToOne
    private Categorie categorie;
    @Enumerated(EnumType.STRING)
    private NatureAgent natureAgent;
    @Enumerated(EnumType.STRING)
    private SituationMat situationMat;
    private int nbreEnfant;
    private  int baseHoraire;

    public ProgressionPoste() {

    }
    
    public ProgressionPoste(ProgressionPoste progressionPoste) {

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

    public Corps getCorps() {
        return corps;
    }

    public void setCorps(Corps corps) {
        this.corps = corps;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public ProgressionAgent getProgressionAgent() {
        return progressionAgent;
    }

    public void setProgressionAgent(ProgressionAgent progressionAgent) {
        this.progressionAgent = progressionAgent;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public NatureAgent getNatureAgent() {
        return natureAgent;
    }

    public void setNatureAgent(NatureAgent natureAgent) {
        this.natureAgent = natureAgent;
    }

    public SituationMat getSituationMat() {
        return situationMat;
    }

    public void setSituationMat(SituationMat situationMat) {
        this.situationMat = situationMat;
    }

    public int getNbreEnfant() {
        return nbreEnfant;
    }

    public void setNbreEnfant(int nbreEnfant){
        this.nbreEnfant = nbreEnfant;
    }

    public int getBaseHoraire() {
        return baseHoraire;
    }

    public void setBaseHoraire(int baseHoraire) {
        this.baseHoraire = baseHoraire;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgressionPoste)) {
            return false;
        }
        ProgressionPoste other = (ProgressionPoste) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public void reset() {
        this.id = "";
    }
}
