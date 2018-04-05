/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Mois;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class MoisFacade extends AbstractFacade<Mois> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MoisFacade() {
        super(Mois.class);
    }
     
 /**
     * Liste des direction internes
     * @param code
     * @return Direction
     */
   public Mois findMoisByCode(int code) {
        Query q = getEntityManager().createQuery("select M from Mois M WHERE M.code =?1");
        q.setParameter(1, code);
        List<Mois> list = q.getResultList();
       return list.get(0);
    }  
}
