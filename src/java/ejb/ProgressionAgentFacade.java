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
import jpa.ProgressionAgent;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ProgressionAgentFacade extends AbstractFacade<ProgressionAgent> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgressionAgentFacade() {
        super(ProgressionAgent.class);
    }

    public ProgressionAgent createProgresseion(ProgressionAgent progressionAgent) {
        progressionAgent.setId(commonFacade.getId(progressionAgent));
        getEntityManager().persist(progressionAgent);
        return progressionAgent;
    }
}
