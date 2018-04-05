/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.DetailSalaire;

@Stateless
public class DetailSalaireFacade extends AbstractFacade<DetailSalaire> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetailSalaireFacade() {
        super(DetailSalaire.class);
    }
    
    @Override
    public void create(DetailSalaire entity) {
        em.persist(entity);
    }
    
}
