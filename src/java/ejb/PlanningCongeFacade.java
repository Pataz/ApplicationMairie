/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
import jpa.PlanningConge;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.Annee;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class PlanningCongeFacade extends AbstractFacade<PlanningConge> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanningCongeFacade() {
        super(PlanningConge.class);
    }
    /**
     * Le planning d'un agent au cours d'une année civile
     *
     * @param agent
     * @param annee
     * @return PlanningConge
     */
    public PlanningConge findDefaultPlanningByAgent(Agent agent, Annee annee) {
        Query q = getEntityManager().createQuery("select P from PlanningConge P WHERE P.agent= ?1 and P.anneeCivile = ?2 and P.defaultexe = ?3");
        q.setParameter(1, agent);
        q.setParameter(2, annee);
        q.setParameter(3, false);
        List<PlanningConge> list = q.getResultList();
        if(list.isEmpty()){
          return null;  
        }else{
          return list.get(0);   
        }
       
    }
    /**
     * Le planning d'un agent au cours d'une année civile
     *
     * @param agent
     * @param annee
     * @param date1
     * @param date2
     * @return PlanningConge
     */
    public PlanningConge findDefaultPeriodPlanningByAgent(Agent agent, Annee annee,Date date1,Date date2) {
        Query q = getEntityManager().createQuery("select P from PlanningConge P WHERE P.agent= ?1 and P.anneeCivile = ?2 and P.defaultexe = ?3 and P.dateDebut=?4 and P.dateFin=?5");
        q.setParameter(1, agent);
        q.setParameter(2, annee);
        q.setParameter(3, false);
        q.setParameter(4, date1);
        q.setParameter(5, date2);
        List<PlanningConge> list = q.getResultList();
        if(list.isEmpty()){
          return null;  
        }else{
          return list.get(0);   
        }
       
    }
    /**
     * Liste des Agents en fonction de la Direction
     *
     * @param agent
     * @param annee
     * @return PlanningConge
     */
    public PlanningConge findFinalPlanningByAgent(Agent agent, Annee annee) {
        Query q = getEntityManager().createQuery("select P from PlanningConge P WHERE P.agent= ?1 and P.anneeCivile = ?2 and P.defaultexe = ?3");
        q.setParameter(1, agent);
        q.setParameter(2, annee);
        q.setParameter(3, true);
        List<PlanningConge> list = q.getResultList();
        if(list.isEmpty()){
          return null;  
        }else{
          return list.get(0);   
        }
       
    }
     /**
     * Le planning final d'un agent au cours d'une année civile
     *
     * @param agent
     * @param annee
     * @param date1
     * @param date2
     * @return PlanningConge
     */
    public PlanningConge findFinalPeriodPlanningByAgent(Agent agent, Annee annee,Date date1,Date date2) {
        Query q = getEntityManager().createQuery("select P from PlanningConge P WHERE P.agent= ?1 and P.anneeCivile = ?2 and P.defaultexe = ?3 and P.dateDebut=?4 and P.dateFin=?5");
        q.setParameter(1, agent);
        q.setParameter(2, annee);
        q.setParameter(3, true);
        q.setParameter(4, date1);
        q.setParameter(5, date2);
        List<PlanningConge> list = q.getResultList();
        if(list.isEmpty()){
          return null;  
        }else{
          return list.get(0);   
        }
       
    }
}
