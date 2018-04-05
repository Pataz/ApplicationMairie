/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.ProgressionAgentFacade;
import ejb.ProgressionPosteFacade;
import ejb.AbsenceFacade;
import ejb.AvancementFacade;
import ejb.FormationFacade;
import ejb.SanctionFacade;
import ejb.MutationFacade;

import ejb.DirectionFacade;
import ejb.ServiceFacade;
import ejb.EvenementFacade;
import ejb.PosteFacade;
import ejb.CategorieFacade;
import ejb.SousCategorieFacade;
import ejb.AgentFacade;
import ejb.MoisFacade;
import java.io.IOException;

import jpa.Agent;
import util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import jpa.Service;
import jpa.Direction;
import jpa.ProgressionAgent;
import jpa.TypeDirection;
import jpa.Evenement;
import jpa.Absence;
import jpa.Avancement;
import jpa.Poste;
import jpa.Formation;
import jpa.Sanction;
import jpa.Mutation;
import jpa.ProgressionPoste;
import jpa.Categorie;
import jpa.Mois;
import jpa.SousCategorie;


/**
 *
 * @author MJLDH
 */
@Named(value = "suiviPersonnelBean")
@ViewScoped
public class SuiviPersonnelBean implements Serializable {

    private Agent selectedAgent;
    private List<Agent> listeAgents;
    private List<Direction> listeDirections;
    private List<Service> listeServices;
    private List<Poste> listePostes;
    private TypeDirection selectedTypeDirection;
    private Direction selectedDirection;
    private Service selectedService;

    private ProgressionAgent newProgressionAgent;
    private ProgressionPoste newProgressionPoste;
    private Formation newFormation;
    private Sanction newSanction;
    private Avancement newAvanacement;
    private Absence newAbsence;
    private Mutation newMutation;

    private List<Evenement> listeEvtsAbsences;
    private List<Evenement> listeEvtsMutations;
    private List<Evenement> listeEvtsAvancePromo;
    private List<Evenement> listeEvtsDeparts;
    private List<Evenement> listeEvtsSanctions;
    private List<Evenement> listeEvtsFormations;

    private List<Categorie> listeCategories;
    private List<SousCategorie> listeSousCategories;
    private SousCategorie selectSousCategorie;
    private String id;

    @EJB
    private ProgressionAgentFacade progressionAgentFacade;
    @EJB
    private DirectionFacade directionFacade;
    @EJB
    private ServiceFacade serviceFacade;
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private EvenementFacade evenementFacade;
    @EJB
    private PosteFacade posteFacade;
    @EJB
    private AbsenceFacade absenceFacade;
    @EJB
    private AvancementFacade avancementFacade;
    @EJB
    private FormationFacade formationFacade;
    @EJB
    private SanctionFacade sanctionFacade;
    @EJB
    private MutationFacade mutationFacade;
    @EJB
    private ProgressionPosteFacade progressionPosteFacade;
    @EJB
    private CategorieFacade categorieFacade;
    @EJB
    private SousCategorieFacade sousCategorieFacade;
    @EJB
    private MoisFacade moisFacade;

    @Inject
    private CommonBean commonBean;

    /**
     * Creates a new instance of AgentBean
     */
    public SuiviPersonnelBean() {

    }

    @PostConstruct
    public void init() {

        listeDirections = directionFacade.findAll();
        listePostes = posteFacade.findAll();
        selectedService = new Service();
        listeServices = new ArrayList<>();
        selectedDirection = new Direction();
        newProgressionAgent = new ProgressionAgent();
        newProgressionPoste = new ProgressionPoste();
        newFormation = new Formation();
        newAbsence = new Absence();
        newAvanacement = new Avancement();
        newMutation = new Mutation();
        newSanction = new Sanction();
        listeAgents = agentFacade.findAgentsActifs();
        listeEvtsAbsences = evenementFacade.getJListeEvenementsType("ABS");
        listeEvtsAvancePromo = evenementFacade.getJListeEvenementsType("AVP");
        listeEvtsDeparts = evenementFacade.getJListeEvenementsType("DEP");
        listeEvtsMutations = evenementFacade.getJListeEvenementsType("MUT");
        listeEvtsSanctions = evenementFacade.getJListeEvenementsType("SAN");
        listeEvtsFormations = evenementFacade.getJListeEvenementsType("FOR");

        listeSousCategories = sousCategorieFacade.findAll();
        selectSousCategorie = new SousCategorie();
        id = null;
        getSelectedAgentID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public SousCategorie getSelectSousCategorie() {
        return selectSousCategorie;
    }

    public void setSelectSousCategorie(SousCategorie selectSousCategorie) {
        this.selectSousCategorie = selectSousCategorie;
    }

    public Agent getSelectedAgent() {
        return selectedAgent;
    }

    public void setSelectedAgent(Agent selectedAgent) {
        this.selectedAgent = selectedAgent;
    }

    public List<Agent> getListeAgents() {
        return listeAgents;
    }

    public void setListeAgents(List<Agent> listeAgents) {
        this.listeAgents = listeAgents;
    }

    public void passItem(Agent item) {
        this.selectedAgent = item;
    }

    public TypeDirection getSelectedTypeDirection() {
        return selectedTypeDirection;
    }

    public void setSelectedTypeDirection(TypeDirection selectedTypeDirection) {
        this.selectedTypeDirection = selectedTypeDirection;
    }

    public Direction getSelectedDirection() {
        return selectedDirection;
    }

    public void setSelectedDirection(Direction selectedDirection) {
        this.selectedDirection = selectedDirection;
    }

    public List<Service> getListeServices() {
        return listeServices;
    }

    public void setListeServices(List<Service> listeServices) {
        this.listeServices = listeServices;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public ServiceFacade getServiceFacade() {
        return serviceFacade;
    }

    public void setServiceFacade(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    public List<Direction> getListeDirections() {
        return listeDirections;
    }

    public void setListeDirections(List<Direction> listeDirections) {
        this.listeDirections = listeDirections;
    }

    public ProgressionAgent getNewProgressionAgent() {
        return newProgressionAgent;
    }

    public void setNewProgressionAgent(ProgressionAgent newProgressionAgent) {
        this.newProgressionAgent = newProgressionAgent;
    }

    public List<Evenement> getListeEvtsAbsences() {
        return listeEvtsAbsences;
    }

    public void setListeEvtsAbsences(List<Evenement> listeEvtsAbsences) {
        this.listeEvtsAbsences = listeEvtsAbsences;
    }

    public List<Evenement> getListeEvtsMutations() {
        return listeEvtsMutations;
    }

    public void setListeEvtsMutations(List<Evenement> listeEvtsMutations) {
        this.listeEvtsMutations = listeEvtsMutations;
    }

    public List<Evenement> getListeEvtsAvancePromo() {
        return listeEvtsAvancePromo;
    }

    public void setListeEvtsAvancePromo(List<Evenement> listeEvtsAvancePromo) {
        this.listeEvtsAvancePromo = listeEvtsAvancePromo;
    }

    public List<Evenement> getListeEvtsDeparts() {
        return listeEvtsDeparts;
    }

    public void setListeEvtsDeparts(List<Evenement> listeEvtsDeparts) {
        this.listeEvtsDeparts = listeEvtsDeparts;
    }

    public List<Evenement> getListeEvtsSanctions() {
        return listeEvtsSanctions;
    }

    public void setListeEvtsSanctions(List<Evenement> listeEvtsSanctions) {
        this.listeEvtsSanctions = listeEvtsSanctions;
    }

    public Formation getNewFormation() {
        return newFormation;
    }

    public void setNewFormation(Formation newFormation) {
        this.newFormation = newFormation;
    }

    public Sanction getNewSanction() {
        return newSanction;
    }

    public void setNewSanction(Sanction newSanction) {
        this.newSanction = newSanction;
    }

    public Avancement getNewAvanacement() {
        return newAvanacement;
    }

    public void setNewAvanacement(Avancement newAvanacement) {
        this.newAvanacement = newAvanacement;
    }

    public Absence getNewAbsence() {
        return newAbsence;
    }

    public void setNewAbsence(Absence newAbsence) {
        this.newAbsence = newAbsence;
    }

    public Mutation getNewMutation() {
        return newMutation;
    }

    public void setNewMutation(Mutation newMutation) {
        this.newMutation = newMutation;
    }
    
    public List<Evenement> getListeEvtsFormations() {
        return listeEvtsFormations;
    }

    public void setListeEvtsFormations(List<Evenement> listeEvtsFormations) {
        this.listeEvtsFormations = listeEvtsFormations;
    }

    public List<Poste> getListePostes() {
        return listePostes;
    }

    public List<SousCategorie> getListeSousCategories() {
        return listeSousCategories;
    }

    public void setListeSousCategories(List<SousCategorie> listeSousCategories) {
        this.listeSousCategories = listeSousCategories;
    }

    public void setListePostes(List<Poste> listePostes) {
        this.listePostes = listePostes;
    }

    public void passDirection() {
        System.out.println("==== direction " + this.selectedDirection);
        if (newProgressionPoste.getDirection() == null) {
            selectedAgent = new Agent();
            newProgressionPoste = new ProgressionPoste();
            this.listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
            this.listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        } else {
            this.listeAgents = agentFacade.findAgentsInDirection(newProgressionPoste.getDirection().getCode());
            this.listeServices = serviceFacade.findServiceByDirection(newProgressionPoste.getDirection());
        }
        System.out.println("==== " + listeAgents);
    }

    public void passService() {
        System.out.println("==== service " + this.selectedService);
        if (newProgressionPoste.getService() == null) {
            selectedAgent = new Agent();
            newProgressionPoste = new ProgressionPoste();
            this.listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }
        System.out.println("==== " + listeServices);
    }

    public void passAgent() {
        System.out.println("==== agent " + this.selectedAgent);
        this.newProgressionPoste = agentFacade.findProgressionPosteById(selectedAgent.getLastProgressionPoste());
        System.out.println("==== newProgressionPoste" + newProgressionPoste);
    }

    public void passSousCategorie() {
        System.out.println("==== direction " + this.selectSousCategorie);
        this.listeCategories = categorieFacade.findCategoriesByBase(selectSousCategorie);
        System.out.println("==== " + listeCategories);
    }

    public ProgressionPoste getNewProgressionPoste() {
        return newProgressionPoste;
    }

    public void setNewProgressionPoste(ProgressionPoste newProgressionPoste) {
        this.newProgressionPoste = newProgressionPoste;
    }

    public void doCreateAbsence(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if ((newProgressionAgent.getDateProgression() == null) || (newAbsence.getDateDebut() == null) || (newAbsence.getDateFin() == null)) {
                msg = bundle.getString("DatesErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                Mois mois = moisFacade.findMoisByCode(newProgressionAgent.getDateProgression().getMonth());
                newAbsence.setProgressionAgent(newProgressionAgent);
                newAbsence.setMois(mois);
                newAbsence = absenceFacade.createAbsence(newAbsence);
                System.out.println("=== ");
                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                agentFacade.edit(selectedAgent);
                commonBean.generateTrame(newAbsence);
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateAvancement(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenAvanceErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionPoste.getCategorie() == null) {
                msg = bundle.getString("CategorieErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                
                newAvanacement.setProgressionAgent(newProgressionAgent);
                newAvanacement.setCategorie(newProgressionPoste.getCategorie());
                newAvanacement = avancementFacade.createAvancement(newAvanacement);
                System.out.println("=== categorie"+newProgressionPoste.getCategorie());
                newProgressionPoste.setDirection(getSelectedDirection());
                newProgressionPoste.setProgressionAgent(newProgressionAgent);
                newProgressionPoste = progressionPosteFacade.createProgression(newProgressionPoste);

                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                selectedAgent.setLastProgressionPoste(newProgressionPoste.getId());
                agentFacade.edit(selectedAgent);

                reinitAgent();

                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateMutation(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenMutationErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newMutation.getDateDebut() == null) {
                msg = bundle.getString("DatesErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionPoste.getPoste() == null) {
                msg = bundle.getString("PosteErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                System.out.println("=== service de service " + this.getNewProgressionPoste().getService());
                System.out.println("=== service de Potse " + this.getNewProgressionPoste().getPoste());
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                
                newMutation.setProgressionAgent(newProgressionAgent);
                newMutation = mutationFacade.createMutation(newMutation);

                newProgressionPoste.setProgressionAgent(newProgressionAgent);
                newProgressionPoste.setDirection(getSelectedDirection());
                newProgressionPoste.setService(getSelectedService());
                newProgressionPoste = progressionPosteFacade.createProgression(newProgressionPoste);

                selectedAgent.setLastProgressionPoste(newProgressionPoste.getId());
                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                agentFacade.edit(selectedAgent);

                reinitAgent();

                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateFormation(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenFormationErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newFormation.getDateDebut() == null || newFormation.getDateFin() == null) {
                msg = bundle.getString("DatesErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } //            else if (newFormation.getTheme() == null  newFormation.getLieu() == null) {

            else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                
                newFormation.setProgressionAgent(newProgressionAgent);
                newFormation = formationFacade.createFormation(newFormation);
                
                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                agentFacade.edit(selectedAgent);

                reinitAgent();

                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateSanction(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenSanctionErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newSanction.getDateDebut() == null || newSanction.getDateFin() == null) {
                msg = bundle.getString("DatesErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                
                newSanction.setProgressionAgent(newProgressionAgent);
                newSanction = sanctionFacade.createSanction(newSanction);
                
                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                agentFacade.edit(selectedAgent);

                reinitAgent();

                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateDepart(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getEvenement() == null) {
                msg = bundle.getString("EvenDepartErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null) {
                msg = bundle.getString("DatesErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(getSelectedAgent());
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                selectedAgent.setLastProgressionAgent(newProgressionAgent.getId());
                agentFacade.edit(selectedAgent);
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void returnServices() {
        listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        returnAgentsList();
    }

    public void returnAgentsList() {
        if (selectedDirection != null && selectedService == null) {
            listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
        }
        if (selectedService != null && selectedDirection != null) {
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
        }
    }

    public void reinitAgent() {
        newProgressionAgent = new ProgressionAgent();
        newProgressionPoste = new ProgressionPoste();
        selectedAgent = new Agent();
        newAbsence = new Absence();
        newMutation = new Mutation();
        newSanction = new Sanction();
        newFormation = new Formation();
        newAvanacement = new Avancement();
    }

    public String recupererId() throws IOException {
        id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        System.out.println(" Id ---" + id);

        selectedAgent = agentFacade.find(id);
        System.out.println("voici l'agent---" + selectedAgent.getId() + " origine ");
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        response.sendRedirect("suiviPersonnel/suiviAgent/index.xhtml");
        return id;
    }

    public Agent getSelectedAgentID() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            id = facesContext.getExternalContext().getRequestParameterMap().get("id");
            if (id != null) {
                listeDirections = new ArrayList<>();
                listeAgents = new ArrayList<>();
                selectedAgent = agentFacade.find(id);
                this.newProgressionPoste = agentFacade.findProgressionPosteById(selectedAgent.getLastProgressionPoste());
                selectedDirection = this.getNewProgressionPoste().getDirection();
                selectedService = this.getNewProgressionPoste().getService();
                listeDirections.add(selectedDirection);
                listeAgents.add(selectedAgent);
                listeServices.add(selectedService);
            }
        } catch (Exception e) {}
        return selectedAgent;
    }
}
