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
import jpa.SuiviHeures;
import jpa.VariableSalaire;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class SuiviHeuresFacade extends AbstractFacade<SuiviHeures> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SuiviHeuresFacade() {
        super(SuiviHeures.class);
    }

    public SuiviHeures createSuivi(SuiviHeures suiviSalaire) {
        suiviSalaire.setId(commonFacade.getId(suiviSalaire));
        getEntityManager().persist(suiviSalaire);
        return suiviSalaire;
    }
    
    /****
     * @param agent
     * @param mois
     * @return 
     */
     public List<SuiviHeures> findHeureSupplementaires(Agent agent , Mois mois) {
        Query q = getEntityManager().createQuery("select S from SuiviHeures S WHERE S.progressionAgent.agent=?1 AND S.mois = ?2");
        q.setParameter(1, agent);
        q.setParameter(2, mois);
        List<SuiviHeures> list = q.getResultList();
        if (list.isEmpty()) {
            return new LinkedList<>();
        } else {
            return list;
        }
    }
}
