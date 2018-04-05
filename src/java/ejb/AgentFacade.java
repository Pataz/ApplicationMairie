/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.Annee;
import jpa.DetailVariable;
import jpa.ProgressionPoste;
import jpa.Direction;
import jpa.Evenement;
import jpa.InterneExterne;
import jpa.NatureAgent;
import jpa.ProgressionAgent;
import jpa.Statut;
import jpa.Utilisateur;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class AgentFacade extends AbstractFacade<Agent> {

    @EJB
    private CommonFacade commonFacade;

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgentFacade() {
        super(Agent.class);
    }

    public void createProgressionPoste(ProgressionPoste entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Liste des Agents en fonction de la Direction
     *
     * @param etat
     * @param direction
     * @param statut
     * @return Agents
     */
    public List<Agent> findAgentsByDirection(String etat, Direction direction, Statut statut) {
        Query q = getEntityManager().createQuery("select P from ProgressionPoste P WHERE P.progressionAgent.agent.etat = ?1 and P.direction = ?2 and P.statut = ?3");
        q.setParameter(1, etat);
        q.setParameter(2, direction);
        q.setParameter(3, statut);
        List<Agent> list = q.getResultList();
        return list;
    }

    public ProgressionAgent createProgression(Agent agent, Evenement evenement, Statut statut, Utilisateur utilisateur, Annee anneeCivile, String observation, Date dateProgression) {
        ProgressionAgent newProgressionAgent = new ProgressionAgent(agent, evenement, statut, utilisateur, anneeCivile, observation, dateProgression);
        newProgressionAgent.setId(commonFacade.getId(newProgressionAgent));
        getEntityManager().persist(newProgressionAgent);
        return newProgressionAgent;
    }

    /**
     * Liste de tous les Agents internes
     *
     * @return Agents
     */
    public List<Agent> findAgentsInterne() {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') AND A.lastDirection IN(SELECT D.code FROM Direction D WHERE D.internExtern=?1) ORDER BY A.personne.name");
        q.setParameter(1, InterneExterne.Interne);
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * Liste de tous les Agents internes dans une direction
     *
     * @param directionCode
     * @return Agents
     */
    public List<Agent> findAgentsInDirection(String directionCode) {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') AND A.lastDirection=?1 ORDER BY A.personne.name");
        q.setParameter(1, directionCode);
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * Liste de tous les Agents internes dans une direction
     *
     * @param directionCode
     * @param natureAgent
     * @return Agents
     */
    public List<Agent> findAgentsInDirectionAndNature(String directionCode, NatureAgent natureAgent) {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') AND A.lastDirection=?1 AND A.lastProgressionPoste IN(SELECT P.id FROM ProgressionPoste P WHERE P.natureAgent=?2) ORDER BY A.personne.name");
        q.setParameter(1, directionCode);
        q.setParameter(2, natureAgent);
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * Liste de tous les Agents internes dans un service
     *
     * @param serviceCode
     * @return Agents
     */
    public List<Agent> findAgentsInService(String serviceCode) {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') AND A.lastService=?1 ORDER BY A.personne.name");
        q.setParameter(1, serviceCode);
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * Liste de tous les Agents internes dans un service
     *
     * @param serviceCode
     * @param natureAgent
     * @return Agents
     */
    public List<Agent> findAgentsInServiceAndNature(String serviceCode, NatureAgent natureAgent) {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') AND A.lastService=?1 AND A.lastProgressionPoste IN(SELECT P.id FROM ProgressionPoste P WHERE P.natureAgent=?2) ORDER BY A.personne.name");
        q.setParameter(1, serviceCode);
        q.setParameter(2, natureAgent);
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * La dernière progressionPoste d'un agent
     *
     * @param id
     * @return Agents
     */
    public ProgressionPoste findProgressionPosteById(String id) {
        Query q = getEntityManager().createQuery("select P from ProgressionPoste P WHERE P.id=?1");
        q.setParameter(1, id);
        List<ProgressionPoste> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * La dernière progressionAgent d'un agent
     *
     * @param id
     * @return Agents
     */
    public ProgressionAgent findProgressionAgentById(String id) {
        Query q = getEntityManager().createQuery("select P from ProgressionAgent P WHERE P.id=?1");
        q.setParameter(1, id);
        List<ProgressionAgent> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * Liste de tous les Agents actifs
     *
     * @return Agents
     */
    public List<Agent> findAgentsActifs() {
        Query q = getEntityManager().createQuery("select A from Agent A WHERE A.etat NOT IN('RET','DEM') ORDER BY A.personne.name");
        List<Agent> list = q.getResultList();
        return list;
    }

    /**
     * toutes les progressionAgent d'un agent
     *
     * @param agent
     * @return Agents
     */
    public List<ProgressionAgent> findAllProgressionAgent(Agent agent) {
        Query q = getEntityManager().createQuery("select P from ProgressionAgent P WHERE P.agent=?1 ORDER BY P.dateProgression ASC");
        q.setParameter(1, agent);
        List<ProgressionAgent> list = q.getResultList();
        return list;
    }

    @Override
    public void create(Agent entity) {
        entity.setDateCreation(new Date());
        entity.setId(commonFacade.getId(entity));
        em.persist(entity);
        for (DetailVariable variable : entity.getDetailVariableFixes()) {
            variable.setDateCreation(new Date());
            variable.setId(commonFacade.getId(variable));
        }
    }
}
