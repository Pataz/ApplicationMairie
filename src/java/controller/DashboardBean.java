/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import alerte.NotificationFacade;
import ejb.AgentFacade;
import ejb.CongeFacade;
import ejb.DirectionFacade;
import ejb.ServiceFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Agent;
import jpa.Conge;
import jpa.Direction;
import jpa.InterneExterne;
import jpa.ProgressionPoste;
import jpa.Service;
import org.apache.log4j.Logger;

/**
 *
 * @author MJLDH
 */
@Named(value = "dashboardBean")
@ViewScoped
public class DashboardBean implements Serializable {

    Logger logger = Logger.getLogger(DashboardBean.class);
    @Inject
    private ContextBean contextBean;
    @EJB
    private DirectionFacade directionFacade;
    @EJB
    private NotificationFacade notificationFacade;
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private CongeFacade congeFacade;
    @EJB
    private ServiceFacade serviceFacade;
    private List<Conge> listeConges;
    private List<Agent> listeAgents;
    private List<Direction> listeDirections;
    private List<Service> listeServices;
    private Direction selectedDirection;
    private Service selectedService;
    private int agentActif,conge;

    public DashboardBean() {

    }

    @PostConstruct
    public void init() {
        selectedDirection = null;
        selectedService = null;
        listeDirections = directionFacade.findDirectionInterneExterne(InterneExterne.Interne);
        listeAgents = agentFacade.findAgentsInterne();
        listeConges=congeFacade.findAgentsInConge(contextBean.getCurrentYear());
        agentActif = listeAgents.size();
        conge=listeConges.size();
        notificationFacade.saveNofificationRetraite();
    }

    public int getAgentActif() {
        return agentActif;
    }

    public void setAgentActif(int agentActif) {
        this.agentActif = agentActif;
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public List<Conge> getListeConges() {
        return listeConges;
    }

    public void setListeConges(List<Conge> listeConges) {
        this.listeConges = listeConges;
    }

    public int getConge() {
        return conge;
    }

    public void setConge(int conge) {
        this.conge = conge;
    }

    public List<Agent> getListeAgents() {
        return listeAgents;
    }

    public void setListeAgents(List<Agent> listeAgents) {
        this.listeAgents = listeAgents;
    }

    public List<Direction> getListeDirections() {
        return listeDirections;
    }

    public void setListeDirections(List<Direction> listeDirections) {
        this.listeDirections = listeDirections;
    }

    public List<Service> getListeServices() {
        return listeServices;
    }

    public void setListeServices(List<Service> listeServices) {
        this.listeServices = listeServices;
    }

    public Direction getSelectedDirection() {
        return selectedDirection;
    }

    public void setSelectedDirection(Direction selectedDirection) {
        this.selectedDirection = selectedDirection;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public void returnServices() {
        listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        returnAgentsList();
    }

    public void returnAgentsList() {
        if (selectedDirection != null && selectedService == null) {
            listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
        }
        if ((selectedService != null && selectedDirection == null) || (selectedService != null && selectedDirection != null)) {
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }

    }
}
