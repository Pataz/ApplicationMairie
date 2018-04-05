/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AgentFacade;
import ejb.CongeFacade;
import ejb.DirectionFacade;
import ejb.ServiceFacade;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Agent;
import jpa.Conge;
import jpa.Direction;
import jpa.NatureAgent;
import jpa.ProgressionPoste;
import jpa.Service;
import org.apache.log4j.Logger;
import util.JsfUtil;

/**
 *
 * @author MJLDH
 */
@Named(value = "registreBean")
@ViewScoped
public class RegistreBean implements Serializable {

    Logger logger = Logger.getLogger(RegistreBean.class);
    @Inject
    private ContextBean contextBean;
    
    @EJB
    private DirectionFacade directionFacade;
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private ServiceFacade serviceFacade;
    @EJB
    private CongeFacade congeFacade;
    
    private List<Agent> listeAgents;
    private List<Direction> listeDirections;
    private List<Service> listeServices;
    private Direction selectedDirection;
    private Service selectedService;
    private NatureAgent selectedNatureAgent;
    private List<Conge> listeConges;
    private Long NbJourRestant;
    private String legende;

    /**
     * Creates a new instance of PosteBean
     */
    public RegistreBean() {

    }

    @PostConstruct
    public void init() {
        selectedDirection = null;
        selectedService = null;
        selectedNatureAgent = null;
        listeDirections = directionFacade.findAll();
        listeAgents = agentFacade.findAgentsActifs();
        returnAgentsEchelonList();
    }

    public List<Conge> getListeConges() {
        return listeConges;
    }

    public void setListeConges(List<Conge> listeConges) {
        this.listeConges = listeConges;
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

    public NatureAgent getSelectedNatureAgent() {
        return selectedNatureAgent;
    }

    public void setSelectedNatureAgent(NatureAgent selectedNatureAgent) {
        this.selectedNatureAgent = selectedNatureAgent;
    }

    public Long getNbJourRestant() {
        return NbJourRestant;
    }

    public void setNbJourRestant(Long NbJourRestant) {
        this.NbJourRestant = NbJourRestant;
    }

    public String getLegende() {
        return legende;
    }

    public void setLegende(String legende) {
        this.legende = legende;
    }

    public void returnServices() {
        listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        returnAgentsList();
    }

    public void returnListServices() {
        listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        returnAgentsEchelonList();
    }

    public void returnAgentsList() {
        if (selectedDirection != null && selectedService == null && selectedNatureAgent == null) {
            listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
        }
        if ((selectedService != null && selectedDirection != null && selectedNatureAgent == null)) {
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }
        if (selectedDirection != null && selectedService == null && selectedNatureAgent != null) {
            listeAgents = agentFacade.findAgentsInDirectionAndNature(selectedDirection.getCode(), selectedNatureAgent);
        }
        if (selectedDirection != null && selectedService != null && selectedNatureAgent != null) {
            listeAgents = agentFacade.findAgentsInServiceAndNature(selectedService.getCode(), selectedNatureAgent);
        }
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
    }

    /**
     *
     *
     * @param agent
     * @return
     */
    public Date prochainEchelon(Agent agent) {
        return JsfUtil.differenceFunction(agent.getDebutFonction(), 2);
    }

    /**
     *
     * @param agent
     * @return
     */
    public String prochaineCategorie(Agent agent) {
        int echel = 0;
        String cat = null;
        System.out.println("=== Agent " + agent);
        ProgressionPoste progress = getProgressionPoste(agent);
        System.out.println("=== Progression poste Agent " + progress);
        echel = progress.getCategorie().getEchelle() + 1;
        if (echel <= 12) {
            cat = getProgressionPoste(agent).getCategorie().getSousCategorie().getLibelle() + "-" + String.valueOf(echel);
        }
        return cat;
    }

    /**
     *
     */
    public void returnAgentsEchelonList() {
        if (selectedDirection != null && selectedService == null) {
            listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
        }
        if ((selectedService != null && selectedDirection != null)) {
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }
        if ((selectedService == null && selectedDirection == null)) {
            listeAgents = agentFacade.findAgentsActifs();
        }
    }

    public long zDayCounter(Date dateEhelon) {
        LocalDateTime datedujour = LocalDate.now().atStartOfDay();
        Date dateJour = JsfUtil.convertirEnDate(datedujour);
        return JsfUtil.getDaysBetween(dateJour, dateEhelon);
    }

    public String returnLegende(Date d1) {
        System.out.println("====>  la date d'éxecution " + d1);
        JsfUtil.logInfo(logger, "contextBean.getDureeEnJours(d1, d2)>14" + zDayCounter(d1));
        if (zDayCounter(d1) > 14 && zDayCounter(d1) > 0) {
            legende = "Verte";
        }
        if (zDayCounter(d1) < 30 && zDayCounter(d1) > 0) {
            legende = "Orange";
        }
        if (zDayCounter(d1) <= 0) {
            legende = "Rouge";
        }
        System.out.println("====> legende " + legende);
        return legende;
    }

    /**
     *
     * @param agent
     * @return
     */
    public String prochainePromotion(Agent agent) {
        int echel = 0;
        String cat = null;
        echel = getProgressionPoste(agent).getCategorie().getEchelle() + 1;
        if (echel == 12 || echel == 11 || echel == 10 || echel == 7 || echel == 4) {
            cat = "AP";
        }
        return cat;
    }
    
    /***
     * 
     * @param agent
     * @return 
     */
    public Conge getCongeAgent(Agent agent){
        return congeFacade.findCongeByAgent(agent , contextBean.getCurrentYear());
    }
}
