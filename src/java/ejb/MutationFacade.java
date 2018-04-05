/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Mutation;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class MutationFacade extends AbstractFacade<Mutation> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MutationFacade() {
        super(Mutation.class);
    }

    public Mutation createMutation(Mutation mutation) {
        mutation.setId(commonFacade.getId(mutation));
        getEntityManager().persist(mutation);
        return mutation;
    }
}
