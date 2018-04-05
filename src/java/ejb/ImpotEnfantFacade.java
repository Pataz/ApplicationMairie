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
import jpa.ImpotEnfant;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ImpotEnfantFacade extends AbstractFacade<ImpotEnfant> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpotEnfantFacade() {
        super(ImpotEnfant.class);
    }

    /**
     * @param valeur
     * @return
     */
    public ImpotEnfant findIntervalleByValeur(int valeur) {
        Query q = getEntityManager().createQuery("select I from ImpotEnfant I WHERE I.nbre =?1");
        q.setParameter(1, valeur);
        List<ImpotEnfant> list = q.getResultList();
        if(list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }      
    }
}
