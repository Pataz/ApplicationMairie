package jpa;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class Agent implements Serializable {
    @Id
    private String id;
    @OneToOne
    private Personne personne;
    @Enumerated(EnumType.STRING)
    private TypeEntree typeEntree;
    @Enumerated(EnumType.STRING)
    private Renumeration renumeration;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "agent", fetch = FetchType.LAZY)
    private Collection<DetailVariable> detailVariableFixes;
    private String nsSociale;
    @Temporal(TemporalType.DATE)
    private Date datenomination;
    @Temporal(TemporalType.DATE)
    private Date debutFonction;
    @Temporal(TemporalType.DATE)
    private Date finFonction;
    private String matricule;
    private String etat;
    private String lastProgressionAgent;
    private String lastProgressionPoste;
    private String lastDirection;
    private String lastService;
    private String decret;
    @Lob
    byte[] face;
    @Version
    private Timestamp version;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    @Transient
    private boolean checkbox;

    @PrePersist
    public void initDateCreation(){
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

    public Renumeration getRenumeration() {
        return renumeration;
    }

    public void setRenumeration(Renumeration renumeration) {
        this.renumeration = renumeration;
    }

    public Date getDatenomination() {
        return datenomination;
    }

    public void setDatenomination(Date datenomination) {
        this.datenomination = datenomination;
    }

    public String getDecret() {
        return decret;
    }

    public void setDecret(String decret) {
        this.decret = decret;
    }

    public Date getDebutFonction() {
        return debutFonction;
    }

    public void setDebutFonction(Date debutFonction) {
        this.debutFonction = debutFonction;
    }

    public Date getFinFonction() {
        return finFonction;
    }

    public void setFinFonction(Date finFonction) {
        this.finFonction = finFonction;
    }

    /******
     * 
     * @return 
     */
    public List<DetailVariable> getDetailVariableFixes() {
        if (detailVariableFixes != null) {
            Iterator<DetailVariable> i = detailVariableFixes.iterator();
            while (i.hasNext()) {
                if (i.next().isNull()) {
                    i.remove();
                }
            }
        }
        return (List<DetailVariable>) detailVariableFixes;
    }

    public void setDetailVariableFixes(Collection<DetailVariable> detailVariableFixes) {
        this.detailVariableFixes = detailVariableFixes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Personne getPersonne() {
        if (personne == null) {
            personne = new Personne();
        }
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public String getNsSociale() {
        return nsSociale;
    }

    public void setNsSociale(String nsSociale) {
        this.nsSociale = nsSociale;
    }

    public TypeEntree getTypeEntree() {
        return typeEntree;
    }

    public void setTypeEntree(TypeEntree typeEntree) {
        this.typeEntree = typeEntree;
    }

    public byte[] getFace() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLastProgressionAgent() {
        return lastProgressionAgent;
    }

    public void setLastProgressionAgent(String lastProgressionAgent) {
        this.lastProgressionAgent = lastProgressionAgent;
    }

    public String getLastProgressionPoste() {
        return lastProgressionPoste;
    }

    public void setLastProgressionPoste(String lastProgressionPoste) {
        this.lastProgressionPoste = lastProgressionPoste;
    }

    public String getLastDirection() {
        return lastDirection;
    }

    public void setLastDirection(String lastDirection) {
        this.lastDirection = lastDirection;
    }

    public String getLastService() {
        return lastService;
    }

    public void setLastService(String lastService) {
        this.lastService = lastService;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}
