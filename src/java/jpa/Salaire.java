package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class Salaire implements Serializable {
    @Id
    private String id;
    @Column(unique = true)
    private String observation;
    @Enumerated(EnumType.STRING)
    private ModePayement modePayement;
    @ManyToOne
    private Agent agent;
    @ManyToOne
    private Mois mois;
    @ManyToOne
    private NatureSalaire natureSalaire;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "salaire", fetch = FetchType.LAZY)
    private Collection<DetailSalaire> detailSalaire;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Temporal(TemporalType.DATE)
    private Date datePaie;
    @Version
    private Timestamp version;
    String heures;
    String prets;
    @ManyToOne
    private Statut statut;

    public Date getDateCreation() {
        return dateCreation;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeures() {
        return heures;
    }

    public void setHeures(String heures) {
        this.heures = heures;
    }

    public String getPrets() {
        return prets;
    }

    public void setPrets(String prets) {
        this.prets = prets;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Date getDatePaie() {
        return datePaie;
    }

    public void setDatePaie(Date datePaie) {
        this.datePaie = datePaie;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Mois getMois() {
        return mois;
    }

    public void setMois(Mois mois) {
        this.mois = mois;
    }

    public ModePayement getModePayement() {
        return modePayement;
    }

    public void setModePayement(ModePayement modePayement) {
        this.modePayement = modePayement;
    }

    public NatureSalaire getNatureSalaire() {
        return natureSalaire;
    }

    public void setNatureSalaire(NatureSalaire natureSalaire) {
        this.natureSalaire = natureSalaire;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<DetailSalaire> getDetailSalaire() {
        if (detailSalaire != null) {
            Iterator<DetailSalaire> i = detailSalaire.iterator();
            while (i.hasNext()) {
                if (i.next().isNull()) {
                    i.remove();
                }
            }
        }
        return (List<DetailSalaire>) detailSalaire;
    }

    public void setDetailSalaire(Collection<DetailSalaire> detailSalaire) {
        this.detailSalaire = detailSalaire;
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
        if (!(object instanceof Salaire)) {
            return false;
        }
        Salaire other = (Salaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Salaire[ id=" + id + " ]";
    }
}
