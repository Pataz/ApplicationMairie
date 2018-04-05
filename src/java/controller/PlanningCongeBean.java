/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import alerte.NotificationFacade;
import ejb.AgentFacade;
import ejb.CommonFacade;
import ejb.CongeFacade;
import ejb.DirectionFacade;
import ejb.EvenementFacade;
import ejb.PlanningCongeFacade;
import ejb.ServiceFacade;
import ejb.StatutFacade;
import ejb.VariableSalaireFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Agent;
import jpa.Conge;
import jpa.Direction;
import jpa.Evenement;
import jpa.PlanningConge;
import jpa.ProgressionAgent;
import jpa.ProgressionPoste;
import jpa.Service;
import jpa.TypeVariable;
import jpa.VariableSalaire;
import org.apache.log4j.Logger;
import util.JsfUtil;

/**
 *
 * @author MJLDH
 */
@Named(value = "planningCongeBean")
@ViewScoped
public class PlanningCongeBean implements Serializable {

    Logger logger = Logger.getLogger(PlanningCongeBean.class);
    @EJB
    private DirectionFacade directionFacade;
    @EJB
    private PlanningCongeFacade planningCongeFacade;
    @EJB
    private CommonFacade commonFacade;
    @Inject
    private ContextBean contextBean;
    @Inject
    private CommonBean commonBean;
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private StatutFacade statutFacade;
    @EJB
    private NotificationFacade notificationFacade;
    @EJB
    private ServiceFacade serviceFacade;
    @EJB
    private EvenementFacade evenementFacade;
    @EJB
    private CongeFacade congeFacade;
    @EJB
    private VariableSalaireFacade variableSalaireFacade;
    
    private Conge newConge;
    private ProgressionAgent newProgressionAgent;
    private List<Agent> listeAgents;
    private List<Direction> listeDirections;
    private List<Evenement> listeEvenements;
    private List<Service> listeServices;
    private Direction selectedDirection;
    private Service selectedService;
    private Evenement selectedEvenement;
    private Agent selectedAgent;
    private PlanningConge newPlanningConge, selectedPlanningConge;
    private Date dateDebut, dateFin;

     private List<VariableSalaire> listeVariablePrimes;
    /**
     * Creates a new instance of FonctionBean
     */
    public PlanningCongeBean() {

    }

    @PostConstruct
    public void init() {
        selectedEvenement = null;
        selectedDirection = null;
        selectedAgent = null;
        selectedService = null;
        newConge = new Conge();
        newProgressionAgent = new ProgressionAgent();
        listeDirections = directionFacade.findAll();
        listeEvenements = evenementFacade.getJListeEvenementsType("CON");
        listeAgents = new ArrayList<>();
        listeServices = new ArrayList<>();
        newPlanningConge = new PlanningConge();
        selectedPlanningConge = new PlanningConge();
        
//        listeVariablePrimes = variableSalaireFacade.findVariablesByType(TypeVariable.PrimeFixe);
    }

    public List<VariableSalaire> getListeVariablePrimes() {
        return listeVariablePrimes;
    }

    public void setListeVariablePrimes(List<VariableSalaire> listeVariablePrimes) {
        this.listeVariablePrimes = listeVariablePrimes;
    }

    
    public Conge getNewConge() {
        return newConge;
    }

    public void setNewConge(Conge newConge) {
        this.newConge = newConge;
    }

    public List<Evenement> getListeEvenements() {
        return listeEvenements;
    }

    public Evenement getSelectedEvenement() {
        return selectedEvenement;
    }

    public void setSelectedEvenement(Evenement selectedEvenement) {
        this.selectedEvenement = selectedEvenement;
    }

    public void setListeEvenements(List<Evenement> listeEvenements) {
        this.listeEvenements = listeEvenements;
    }

    public PlanningConge getSelectedPlanningConge() {
        return selectedPlanningConge;
    }

    public void setSelectedPlanningConge(PlanningConge selectedPlanningConge) {
        this.selectedPlanningConge = selectedPlanningConge;
    }

    public PlanningConge getPlanningConge(Agent agent) {
        return planningCongeFacade.findDefaultPlanningByAgent(agent, contextBean.getCurrentYear());
    }

    public PlanningConge getDefaultPeriodPlanningConge(Agent agent, Date date1, Date date2) {
        return planningCongeFacade.findDefaultPeriodPlanningByAgent(agent, contextBean.getCurrentYear(), date1, date2);
    }

    public PlanningConge getFinaltPeriodPlanningConge(Agent agent, Date date1, Date date2) {
        return planningCongeFacade.findFinalPeriodPlanningByAgent(agent, contextBean.getCurrentYear(), date1, date2);
    }

    public PlanningConge getFinalPlanningConge(Agent agent) {
        return planningCongeFacade.findFinalPlanningByAgent(agent, contextBean.getCurrentYear());
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
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

    public Agent getSelectedAgent() {
        return selectedAgent;
    }

    public void setSelectedAgent(Agent selectedAgent) {
        this.selectedAgent = selectedAgent;
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

    public PlanningConge getNewPlanningConge() {
        return newPlanningConge;
    }

    public void setNewPlanningConge(PlanningConge newPlanningConge) {
        this.newPlanningConge = newPlanningConge;
    }

    public void returnServices() {
        listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        returnAgentsList();
    }

    public ProgressionPoste getProgressionPoste() {
        if (newPlanningConge.getAgent() != null) {
            selectedPlanningConge = getFinalPlanningConge(newPlanningConge.getAgent());
            return agentFacade.findProgressionPosteById(newPlanningConge.getAgent().getLastProgressionPoste());

        } else {
            return new ProgressionPoste();
        }
    }

    public void returnAgentsList() {
        if (selectedDirection != null && selectedService == null) {
            System.out.println("service is null");
            listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
        }
        if ((selectedService != null && selectedDirection != null)) {
            System.out.println("service not null");
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }

    }

    public void returnListeAgent() {
        listeAgents = new ArrayList<>();
        if (selectedEvenement != null) {
            listeAgents = agentFacade.findAgentsInterne();
        }
    }

    public void passItem(PlanningConge item) {
        JsfUtil.logInfo(logger, "item==>" + item);
        selectedPlanningConge = item;
    }

    public void passItemAgent(Agent item) {
        selectedAgent = item;
    }

    public void returnDateRetour() {
        JsfUtil.logInfo(logger, "newPlanningConge.getDateDebut()+++" + newPlanningConge.getDateDebut());
        if (newPlanningConge.getDateDebut() != null) {

            newPlanningConge.setDateFin(notificationFacade.calculerDateLimite(newPlanningConge.getDateDebut(), 30));
        }
        if (selectedPlanningConge.getDateDebut() != null) {

            selectedPlanningConge.setDateFin(notificationFacade.calculerDateLimite(selectedPlanningConge.getDateDebut(), 30));
        }
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (selectedAgent != null) {
                newPlanningConge.setAgent(selectedAgent);
            }
            if (getPlanningConge(newPlanningConge.getAgent()) != null) {
                newPlanningConge = new PlanningConge();
                msg = bundle.getString("PlanningCongeExistMsg");
                JsfUtil.addErrorMessage(msg);

            } else {
                dateDebut = newPlanningConge.getDateDebut();
                dateFin = newPlanningConge.getDateFin();
                dateDebut.setHours(23);
                dateFin.setHours(23);

                newPlanningConge.setAnneeCivile(contextBean.getCurrentYear());
                newPlanningConge.setId(commonFacade.getId(newPlanningConge));
                planningCongeFacade.create(newPlanningConge);
                newPlanningConge.setDateDebut(dateDebut);
                newPlanningConge.setDateFin(dateFin);
                planningCongeFacade.edit(newPlanningConge);
                newPlanningConge = new PlanningConge();
                selectedAgent = null;
                returnAgentsList();
//            selectedDirection=null;
//            selectedService=null;
                msg = bundle.getString("PlanningCongeCreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }

        } catch (Exception e) {
            msg = bundle.getString("PlanningCongeCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEditDefault(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            JsfUtil.logInfo(logger, "date 1=>" + selectedPlanningConge.getDateDebut() + "==== date2=>" + selectedPlanningConge.getDateFin());
            dateDebut = selectedPlanningConge.getDateDebut();
            dateFin = selectedPlanningConge.getDateFin();
            dateDebut.setHours(23);
            dateFin.setHours(23);
            if (getDefaultPeriodPlanningConge(selectedPlanningConge.getAgent(), dateDebut, dateFin) == null) {
                selectedPlanningConge.setDateDebut(dateDebut);
                selectedPlanningConge.setDateFin(dateFin);
                planningCongeFacade.edit(selectedPlanningConge);
                selectedPlanningConge = new PlanningConge();
                msg = bundle.getString("OperationCreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = bundle.getString("PeriodNoChangeCreateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            }

        } catch (Exception e) {
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEditFinal(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            JsfUtil.logInfo(logger, "date 1=>" + selectedPlanningConge.getDateDebut() + "==== date2=>" + selectedPlanningConge.getDateFin());
            dateDebut = selectedPlanningConge.getDateDebut();
            dateFin = selectedPlanningConge.getDateFin();
            dateDebut.setHours(23);
            dateFin.setHours(23);
            if (getFinaltPeriodPlanningConge(selectedPlanningConge.getAgent(), dateDebut, dateFin) == null) {
                selectedPlanningConge.setDateDebut(dateDebut);
                selectedPlanningConge.setDateFin(dateFin);
                planningCongeFacade.edit(selectedPlanningConge);
                selectedPlanningConge = new PlanningConge();
                msg = bundle.getString("OperationCreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            } else {
                msg = bundle.getString("PeriodNoChangeCreateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            }

        } catch (Exception e) {
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doApprouve(PlanningConge item) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            newPlanningConge.setDateDebut(item.getDateDebut());
            newPlanningConge.setDateFin(item.getDateFin());
            newPlanningConge.setPlanningConge(item);
            newPlanningConge.setAgent(item.getAgent());
            newPlanningConge.setDefaultexe(true);
            newPlanningConge.setEtat("APPR");
            newPlanningConge.setAnneeCivile(contextBean.getCurrentYear());
            newPlanningConge.setId(commonFacade.getId(newPlanningConge));
            planningCongeFacade.create(newPlanningConge);
            newPlanningConge = new PlanningConge();
            msg = bundle.getString("ApprouvePlanningCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("ApprouvePlanningCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doReaffecter(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            dateDebut = newPlanningConge.getDateDebut();
            dateFin = newPlanningConge.getDateFin();
            dateDebut.setHours(23);
            dateFin.setHours(23);
            if (getPlanningConge(selectedAgent) != null) {
                newPlanningConge.setPlanningConge(getPlanningConge(selectedAgent));
            }
            newPlanningConge.setAgent(selectedAgent);
            newPlanningConge.setDefaultexe(true);
            newPlanningConge.setEtat("RDEF");
            newPlanningConge.setAnneeCivile(contextBean.getCurrentYear());
            newPlanningConge.setId(commonFacade.getId(newPlanningConge));
            planningCongeFacade.create(newPlanningConge);
            newPlanningConge.setDateDebut(dateDebut);
            newPlanningConge.setDateFin(dateFin);
            planningCongeFacade.edit(newPlanningConge);
            newPlanningConge = new PlanningConge();
            msg = bundle.getString("RedefiniPlanningCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("RedefiniPlanningCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    /**
     * *
     *
     * @param event
     */
    public void doCreateConge(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (!"CON2".equals(selectedEvenement.getCode())) {
                dateDebut = selectedPlanningConge.getDateDebut();
                dateFin = selectedPlanningConge.getDateFin();
            } else {
                dateDebut = newPlanningConge.getDateDebut();
                dateFin = newPlanningConge.getDateFin();
            }
            dateDebut.setHours(23);
            dateFin.setHours(23);
            newProgressionAgent = agentFacade.createProgression(newPlanningConge.getAgent(), selectedEvenement, statutFacade.find("CON"), contextBean.getCurrentUser(), contextBean.getCurrentYear(), newConge.getObservation(), new Date());

            newConge.setDateDebut(dateDebut);
            newConge.setDateFin(dateFin);
            newConge.setEtat("DEP");
            newConge.setPlanningConge(selectedPlanningConge);
            newConge.setProgressionAgent(newProgressionAgent);
            newConge.setId(commonFacade.getId(newConge));
            congeFacade.create(newConge);

            newPlanningConge.getAgent().setLastProgressionAgent(newProgressionAgent.getId());
            agentFacade.edit(newPlanningConge.getAgent());
            Object[] parameters = {"DATEDEBUT", "DATEFIN"};
            Object[] parameterValue = {JsfUtil.convertDate(dateDebut, "EEEEE dd MMMMM yyyy"), JsfUtil.convertDate(dateFin, "EEEEE dd MMMMM yyyy")};
            commonBean.universalGenerateTrameFunction(parameters, parameterValue, newPlanningConge.getAgent(), selectedEvenement.getModele().getChemin());
            selectedPlanningConge = null;
            newConge = new Conge();
            newProgressionAgent = new ProgressionAgent();
            newPlanningConge = new PlanningConge();
            selectedEvenement = null;
            msg = bundle.getString("OperationCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
}
