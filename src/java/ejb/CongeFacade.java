/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.Annee;
import jpa.Conge;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class CongeFacade extends AbstractFacade<Conge> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CongeFacade() {
        super(Conge.class);
    }

    /**
     * Liste des Agents en congé au cours d'une année civile
     *
     * @param annee
     * @return Conge
     */
    public List<Conge> findAgentsInConge(Annee annee) {
        Query q = getEntityManager().createQuery("select C from Conge C WHERE C.etat=?1 AND C.progressionAgent.anneeCivile=?2 AND C.progressionAgent.agent.id NOT IN (SELECT C1.progressionAgent.agent.id FROM Conge C1 WHERE C1.etat=?3 AND C.progressionAgent.anneeCivile=?4)");
        q.setParameter(1, "DEP");
        q.setParameter(2, annee);
        q.setParameter(3, "RET");
        q.setParameter(4, annee);
        List<Conge> list = q.getResultList();
        return list;
    }

    /****
     * 
     * @param agent
     * @param annee
     * @return 
     */
    public Conge findCongeByAgent(Agent agent, Annee annee) {
        Query q = getEntityManager().createQuery("select C from Conge C WHERE C.planningConge.agent =?1 AND C.planningConge.anneeCivile = ?2");
        q.setParameter(1, agent);
        q.setParameter(2, annee);
        List<Conge> list = q.getResultList();
        if(list.isEmpty()){
          return null;  
        }else{
          return list.get(0);   
        }
       
    }
    
}
