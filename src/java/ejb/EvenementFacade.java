/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jpa.Evenement;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class EvenementFacade extends AbstractFacade<Evenement> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvenementFacade() {
        super(Evenement.class);
    }

    
    /**
     * Justo
     *
     * @param codetypeEvenement
     * @return Liste des Evenements en fonction de type d'evenement
     */
    public List<Evenement> getJListeEvenementsType(String codetypeEvenement) {
        Query query = em.createQuery("select even from Evenement even where even.typeEvenement.code = :codetypeEvenement");
        query.setParameter("codetypeEvenement", codetypeEvenement);
        return query.getResultList();
    }
    
//     public List<Evenement> getEvenementConge() {
//        Query query = em.createQuery("select E from Evenement E WHERE E.code IN ('DEP1','DEP2')");
//        query.setParameter("codetypeEvenement", codetypeEvenement);
//        return query.getResultList();
//    }
}
