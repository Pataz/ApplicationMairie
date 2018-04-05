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
import jpa.Sanction;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class SanctionFacade extends AbstractFacade<Sanction> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SanctionFacade() {
        super(Sanction.class);
    }

    public Sanction createSanction(Sanction sanction) {
        sanction.setId(commonFacade.getId(sanction));
        getEntityManager().persist(sanction);
        return sanction;
    }
}
