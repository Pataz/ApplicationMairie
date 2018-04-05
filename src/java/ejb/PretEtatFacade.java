/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.Mois;
import jpa.PretEtat;
import jpa.VariableSalaire;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class PretEtatFacade extends AbstractFacade<PretEtat> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private CommonFacade commonFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PretEtatFacade() {
        super(PretEtat.class);
    }

    public void createSuivi(PretEtat pretEtat) {
        pretEtat.setId(commonFacade.getId(pretEtat));
        getEntityManager().persist(pretEtat);
    }
    
    /****
     * @param agent
     * @param variable
     * @return 
     */
     public PretEtat findEtatPret(Agent agent , VariableSalaire variable) {
        Query q = getEntityManager().createQuery("select P from PretEtat P WHERE  P.agent=?1 AND P.variableSalaires=?2 ");
        q.setParameter(1, agent);
        q.setParameter(2, variable);
        List<PretEtat> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    /***
     * @param agent
     * @return 
     */
     public List<PretEtat> findEtatPret(Agent agent ) {
        Query q = getEntityManager().createQuery("select P from PretEtat P WHERE  P.agent=?1 ");
        q.setParameter(1, agent);
        List<PretEtat> list = q.getResultList();
        if (list.isEmpty()) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }
}
