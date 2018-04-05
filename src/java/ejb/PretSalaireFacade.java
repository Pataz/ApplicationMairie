/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.Mois;
import jpa.PretSalaire;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class PretSalaireFacade extends AbstractFacade<PretSalaire> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PretSalaireFacade() {
        super(PretSalaire.class);
    }

    public PretSalaire createSuivi(PretSalaire suiviSalaire) {
        suiviSalaire.setId(commonFacade.getId(suiviSalaire));
        getEntityManager().persist(suiviSalaire);
        return suiviSalaire;
    }
    
    /****
     * @param agent
     * @param mois
     * @return 
     */
     public List<PretSalaire> findPrets(Agent agent , Mois mois) {
        Query q = getEntityManager().createQuery("select P from PretSalaire P WHERE P.progressionAgent.agent=?1 AND P.mois = ?2");
        q.setParameter(1, agent);
        q.setParameter(2, mois);
        List<PretSalaire> list = q.getResultList();
        if (list.isEmpty()) {
            return new LinkedList<>();
        } else {
            return list;
        }
    }
}
