/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.ProgressionPoste;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ProgressionPosteFacade extends AbstractFacade<ProgressionPoste> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgressionPosteFacade() {
        super(ProgressionPoste.class);
    }

    public ProgressionPoste createProgression(ProgressionPoste progressionPoste) {
        progressionPoste.setId(commonFacade.getId(progressionPoste));
        getEntityManager().persist(progressionPoste);
        return progressionPoste;
    }
}
