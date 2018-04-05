package alerte.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jpa.Agent;
import jpa.Direction;
import jpa.Evenement;
import jpa.Fonction;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author xess
 */
@NamedQueries({
    // trouver les notifications susceptibles d'être désactivées
    @NamedQuery(name = "trouverNotifaDesactiver",
            query = "select n from Notification n where n.agent.id = ?1 and n.evenement.code = ?2 and n.statut > 0"),
    // trouver les notifications susceptibles d'être désactivées
    @NamedQuery(name = "trouverNotifRetraite",
            query = "select n from Notification n where n.evenement.code = ?1 and n.statut > 0"),
    //trouver les notifications actives relatives à une destination (service, corps)
    @NamedQuery(name = "trouverActifParDestinationSeule",
            query = "select n from Notification n where n.destination = ?1 and n.fonction is null and n.statut >0"),
    //trouver les notifications actives relatives à une destination (service) et a une fonction
    @NamedQuery(name = "trouverActifDestEtFonction",
            query = "select n from Notification n where n.destination = ?1 and n.fonction = ?2 and n.statut >0")
})
@Entity
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Code de l'evenement qui est l'instigateur de la notification.
     */
    private Evenement evenement;
    /**
     * Agent concerné.
     */
    private Agent agent;
    /**
     * Libellé du service d'origine (générateur de la notification).
     */
    private Direction origine;
    /**
     * Libellé de l'objet de destination. <br/><p/>
     * C'est le libellé d'un objet de type <code>Corps</code>(tel que greffier),
     * soit un <code>Service</code> (par exemple une qu'une chambre).
     */
    private Direction destination;

    /**
     * Le code de la fonction, si la notification est adressée à une fonction
     *
     * spécifique d'un service.
     */
    private Fonction fonction;
    /**
     * Description de la notification.
     */
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    /**
     * Date effective ou l'événement de ferméture du dossier a lieu.
     */
    @Temporal(TemporalType.DATE)
    private Date dateCloture;
    /**
     * Date limite de clôture de l'événement, calculée sur la base du délai de
     * l'événement.
     */
    @Temporal(TemporalType.DATE)
    private Date dateLimite;
    /**
     * statut de la notification. 0 = inactif (clôturé) 1 = actif (normal) 2 =
     * actif (alerte)
     */
    private int statut;

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public Direction getOrigine() {
        return origine;
    }

    public void setOrigine(Direction origine) {
        this.origine = origine;
    }

    public Direction getDestination() {
        return destination;
    }

    public void setDestination(Direction destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Date getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(Date dateLimite) {
        this.dateLimite = dateLimite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public boolean estActive() {
        return statut > 0;
    }

    public boolean estAlerte() {
        return statut == 2;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        return builder.append("id", id).
                //                append("orig", svcOrig).
                append("dest", destination).
                //                append("dossier", numeroRP).
                //                append("evt", codeEvenement).
                //                append("typecible", typeCible).
                append("statut", statut).
                //                append("fonction", codeFonction).
                append("creation", dateCreation).
                append("limite", dateLimite).
                build();
    }
}
