/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Compteur;
import jpa.Absence;
import jpa.Agent;
import jpa.Mois;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class AbsenceFacade extends AbstractFacade<Absence> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AbsenceFacade() {
        super(Absence.class);
    }

    public Absence createAbsence(Absence absence) {
        absence.setId(commonFacade.getId(absence));
        getEntityManager().persist(absence);
        return absence;
    }

    /**
     * ***
     *
     * @param code
     * @param agent
     * @return
     */
    public int findHeureByAbsence(int code, Agent agent) {
        int nbre = 0;
        Query q = getEntityManager().createQuery("select A from Absence A WHERE A.mois =?1 AND A.progressionAgent.agent = ?2 AND A.deduitSalaire=?3");
        q.setParameter(1, code);
        q.setParameter(2, agent);
        q.setParameter(3, true);
        List<Absence> list = q.getResultList();
        if (!list.isEmpty()) {
            for (Absence abs : list) {
                nbre = nbre + Integer.valueOf(abs.getNbreHeure());
            }
        }

        return nbre;
    }
}
