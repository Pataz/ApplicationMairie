/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerte.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * représente la correspondance entre les événements dont les notifications se 
 * désactivent les unes les autres.
 * @author xess
 */
@Entity
@NamedQuery(name = "trouverEvenementsCibles", query="select r.evtCible from RelationEvenement r where r.evtBase= ?1")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"evtBase","evtCible"}))
public class RelationEvenement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    /**
     * code de l'événement à la base d'une notification.
     */
    private String evtBase;
    /**
     * code l'événement dont les notifications seront désactivées par l'événement base. 
     */
    private String evtCible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public String getEvtBase() {
        return evtBase;
    }

    public void setEvtBase(String evtBase) {
        this.evtBase = evtBase;
    }

    public String getEvtCible() {
        return evtCible;
    }

    public void setEvtCible(String evtCible) {
        this.evtCible = evtCible;
    }   

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelationEvenement)) {
            return false;
        }
        RelationEvenement other = (RelationEvenement) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return String.format("{id=%s, evtbase=%s, evtCible=%s}", id, evtBase, evtCible);
    }
    
}
