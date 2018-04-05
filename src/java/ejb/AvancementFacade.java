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
import jpa.Avancement;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class AvancementFacade extends AbstractFacade<Avancement> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AvancementFacade() {
        super(Avancement.class);
    }

    public Avancement createAvancement(Avancement avancement) {
        avancement.setId(commonFacade.getId(avancement));
        getEntityManager().persist(avancement);
        return avancement;
    }
}
