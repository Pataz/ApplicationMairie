/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Agent;
import jpa.DetailSalaire;
import jpa.DetailVariable;
import jpa.Salaire;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class SalaireFacade extends AbstractFacade<Salaire> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SalaireFacade() {
        super(Salaire.class);
    }
//    
//    public Salaire createSalaire(Salaire salaire) {
//        salaire.setId(commonFacade.getId(salaire));
//        getEntityManager().persist(salaire);
//        return salaire;
//    }
    
    @Override
    public void create(Salaire entity) {
        entity.setId(commonFacade.getId(entity));
        em.persist(entity);
        for (DetailSalaire variable : entity.getDetailSalaire()) {
            variable.setDateCreation(new Date());
            variable.setId(commonFacade.getId(variable));
        }
    }
}
