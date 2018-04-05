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
import jpa.ImpotIPTS;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ImpotIPTSFacade extends AbstractFacade<ImpotIPTS> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ImpotIPTSFacade() {
        super(ImpotIPTS.class);
    }
     
 /**
     * @param valeur
     * @return 
     */
   public ImpotIPTS findIntervalleByValeur(int valeur){
        Query q = getEntityManager().createQuery("select I from ImpotIPTS I WHERE I.mini <=?1 AND I.maxi > ?1 ");
        q.setParameter(1, valeur);
        List<ImpotIPTS> list = q.getResultList();
       return list.get(0);
    }  
   
   /***
    * 
    * @param valeur
    * @return 
    */
   public ImpotIPTS findImpotByValeur(int valeur){
        Query q = getEntityManager().createQuery("select I from ImpotIPTS I WHERE I.maxi = ?1 ");
        q.setParameter(1, valeur);
        List<ImpotIPTS> list = q.getResultList();
       return list.get(0);
    }  
}
