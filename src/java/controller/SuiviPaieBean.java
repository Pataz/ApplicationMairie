/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

//import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import ejb.ProgressionAgentFacade;
import ejb.DirectionFacade;
import ejb.ServiceFacade;
import ejb.EvenementFacade;
import ejb.AgentFacade;
import ejb.CommonFacade;
import ejb.MoisFacade;
import ejb.NatureSalaireFacade;
import ejb.SalaireFacade;
import ejb.VariableSalaireFacade;
import ejb.SuiviHeuresFacade;
import ejb.DetailVariableFacade;
import ejb.ImpotEnfantFacade;
import ejb.ImpotIPTSFacade;
import ejb.PretEtatFacade;
import ejb.PretSalaireFacade;

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

import jpa.Agent;
import jpa.Service;
import jpa.Direction;
import jpa.ProgressionAgent;
import jpa.Evenement;
import jpa.Poste;
import jpa.ProgressionPoste;
import jpa.DetailVariable;
import jpa.NatureSalaire;
import jpa.Salaire;
import jpa.DetailSalaire;
import jpa.Mois;
import jpa.TypeVariable;
import jpa.VariableSalaire;
import jpa.SuiviHeures;
import jpa.ImpotEnfant;
import jpa.ImpotIPTS;
import jpa.PretEtat;
import jpa.PretSalaire;
import org.apache.log4j.Logger;

/**
 *
 * @author MJLDH
 */
@Named(value = "suiviPaieBean")
@ViewScoped
public class SuiviPaieBean implements Serializable {

    Logger logger = Logger.getLogger(SuiviPaieBean.class);

    private Agent selectedAgent;
    private List<Agent> listeAgents;
    private List<Direction> listeDirections;
    private List<Service> listeServices;
    private List<Poste> listePostes;
    private Direction selectedDirection;
    private Service selectedService;
    private ProgressionAgent newProgressionAgent;
    private ProgressionPoste newProgressionPoste;
    private List<Evenement> listeEvtsSuiviPaies;
    private List<NatureSalaire> listeNatureSalaires;
    private Salaire newSalaire;
    private SuiviHeures newSuiviHeures;
    private PretSalaire newPretSalaire;
    private PretSalaire newSuiviSalaire;
    private String id, salaireNet, primes;
    private DetailVariable detailVariable;
    private DetailSalaire selectedDetailSalaire;
    private DetailSalaire detailSalaire;
    private List<VariableSalaire> listeVariablePrimes;
    private List<VariableSalaire> listeVariableCnss;
    private List<VariableSalaire> listeVariableHeureSuppl;
    private TypeVariable typeVariable;
    private List<Agent> listeAgentTableau;
    private List<Mois> listeMois;
    private boolean selelectedAgentcheckbox;

    @EJB
    private PretSalaireFacade pretSalaireFacade;
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
    private NatureSalaireFacade natureSalaireFacade;
    @EJB
    private SalaireFacade salaireFacade;
    @EJB
    private VariableSalaireFacade variableSalaireFacade;
    @EJB
    private SuiviHeuresFacade suiviSalaireFacade;
    @EJB
    private CommonFacade commonFacade;
    @EJB
    private MoisFacade moisFacade;
    @EJB
    private DetailVariableFacade detailVariableFacade;
    @EJB
    private ImpotEnfantFacade impotEnfantFacade;
    @EJB
    private ImpotIPTSFacade impotIPTSFacade;
    @EJB
    private PretEtatFacade pretEtatFacade;

    @Inject
    private CommonBean commonBean;

    /**
     * Creates a new instance of AgentBean
     */
    public SuiviPaieBean() {

    }

    @PostConstruct
    public void init() {
        id = null;
        listeDirections = directionFacade.findAll();
        selectedService = new Service();
        listeServices = new ArrayList<>();
        selectedDirection = new Direction();
        newProgressionAgent = new ProgressionAgent();
        newProgressionPoste = new ProgressionPoste();
        listeAgents = new ArrayList<>();
//        listeAgents = agentFacade.findAgentsActifs();
        listeEvtsSuiviPaies = evenementFacade.getJListeEvenementsType("ABS");
        listeNatureSalaires = natureSalaireFacade.findAll();
        newSalaire = new Salaire();
        selectedDetailSalaire = new DetailSalaire();
        detailSalaire = new DetailSalaire();
        listeVariablePrimes = variableSalaireFacade.findVariablesByType(TypeVariable.PrimeVariable);
        listeVariableCnss = new ArrayList<>();
        listeVariableHeureSuppl = new ArrayList<>();
        newSalaire.setDetailSalaire(new ArrayList<>());
        typeVariable = TypeVariable.PrimeFixe;
        listeAgentTableau = new ArrayList<>();
//        selectedAgent = new Agent();
        newSuiviHeures = new SuiviHeures();
        newPretSalaire = new PretSalaire();
        newSuiviSalaire = new PretSalaire();
        listeMois = moisFacade.findAll();
        salaireNet = null;
        primes = null;
        System.out.println("=== " + 10 / 100);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Mois> getListeMois() {
        return listeMois;
    }

    public void setListeMois(List<Mois> listeMois) {
        this.listeMois = listeMois;
    }

    public List<VariableSalaire> getListeVariableCnss() {
        return listeVariableCnss;
    }

    public void setListeVariableCnss(List<VariableSalaire> listeVariableCnss) {
        this.listeVariableCnss = listeVariableCnss;
    }

    public List<VariableSalaire> getListeVariableHeureSuppl() {
        return listeVariableHeureSuppl;
    }

    public void setListeVariableHeureSuppl(List<VariableSalaire> listeVariableHeureSuppl) {
        this.listeVariableHeureSuppl = listeVariableHeureSuppl;
    }

    public SuiviHeures getNewSuiviHeures() {
        return newSuiviHeures;
    }

    public void setNewSuiviHeures(SuiviHeures newSuiviHeures) {
        this.newSuiviHeures = newSuiviHeures;
    }

    public PretSalaire getNewPretSalaire() {
        return newPretSalaire;
    }

    public void setNewPretSalaire(PretSalaire newPretSalaire) {
        this.newPretSalaire = newPretSalaire;
    }

    public PretSalaire getNewSuiviSalaire() {
        return newSuiviSalaire;
    }

    public void setNewSuiviSalaire(PretSalaire newSuiviSalaire) {
        this.newSuiviSalaire = newSuiviSalaire;
    }

    
    public TypeVariable getTypeVariable() {
        return typeVariable;
    }

    public List<Agent> getListeAgentTableau() {
        return listeAgentTableau;
    }

    public void setListeAgentTableau(List<Agent> listeAgentTableau) {
        this.listeAgentTableau = listeAgentTableau;
    }

    public boolean isSelelectedAgentcheckbox() {
        return selelectedAgentcheckbox;
    }

    public void setSelelectedAgentcheckbox(boolean selelectedAgentcheckbox) {
        this.selelectedAgentcheckbox = selelectedAgentcheckbox;
    }

    public DetailSalaire getSelectedDetailSalaire() {
        return selectedDetailSalaire;
    }

    public void setSelectedDetailSalaire(DetailSalaire selectedDetailSalaire) {
        this.selectedDetailSalaire = selectedDetailSalaire;
    }

    public List<VariableSalaire> getListeVariablePrimes() {
        return listeVariablePrimes;
    }

    public void setListeVariablePrimes(List<VariableSalaire> listeVariablePrimes) {
        this.listeVariablePrimes = listeVariablePrimes;
    }

    
    public DetailSalaire getDetailSalaire() {
        return detailSalaire;
    }

    public void setDetailSalaire(DetailSalaire detailSalaire) {
        this.detailSalaire = detailSalaire;
    }

    public DetailVariable getDetailVariable() {
        return detailVariable;
    }

    public void setDetailVariable(DetailVariable detailVariable) {
        this.detailVariable = detailVariable;
    }

    public Salaire getNewSalaire() {
        return newSalaire;
    }

    public void setNewSalaire(Salaire newSalaire) {
        this.newSalaire = newSalaire;
    }

    public List<NatureSalaire> getListeNatureSalaires() {
        return listeNatureSalaires;
    }

    public void setListeNatureSalaires(List<NatureSalaire> listeNatureSalaires) {
        this.listeNatureSalaires = listeNatureSalaires;
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
        System.out.println(" === " + item);
        this.selectedAgent = item;
        listeVariableCnss = variableSalaireFacade.findVariablesByType(TypeVariable.CNSS);
        System.out.println("=== " + listeVariableCnss);
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

    public List<Evenement> getListeEvtsSuiviPaies() {
        return listeEvtsSuiviPaies;
    }

    public void setListeEvtsSuiviPaies(List<Evenement> listeEvtsSuiviPaies) {
        this.listeEvtsSuiviPaies = listeEvtsSuiviPaies;
    }

    public List<Poste> getListePostes() {
        return listePostes;
    }

    public void setListePostes(List<Poste> listePostes) {
        this.listePostes = listePostes;
    }

    public void passDirection() {
        if (newProgressionPoste.getDirection() == null) {
            selectedAgent = new Agent();
            newProgressionPoste = new ProgressionPoste();
            this.listeAgents = agentFacade.findAgentsInDirection(selectedDirection.getCode());
            this.listeServices = serviceFacade.findServiceByDirection(selectedDirection);
        } else {
            this.listeAgents = agentFacade.findAgentsInDirection(newProgressionPoste.getDirection().getCode());
            this.listeServices = serviceFacade.findServiceByDirection(newProgressionPoste.getDirection());
        }
    }

    public void passService() {
        if (newProgressionPoste.getService() == null) {
            selectedAgent = new Agent();
            newProgressionPoste = new ProgressionPoste();
            listeAgents = agentFacade.findAgentsInService(selectedService.getCode());
            listeVariableCnss = variableSalaireFacade.findVariablesByType(TypeVariable.CNSS);
        }
    }

    public void passAgent() {
        System.out.println("=== Je suis laaaa " + selectedAgent.getLastProgressionPoste());
        newProgressionPoste = agentFacade.findProgressionPosteById(selectedAgent.getLastProgressionPoste());
        System.out.println("=== Je suis 111 " + newProgressionPoste);
    }

    public ProgressionPoste getNewProgressionPoste() {
        return newProgressionPoste;
    }

    public void setNewProgressionPoste(ProgressionPoste newProgressionPoste) {
        this.newProgressionPoste = newProgressionPoste;
    }

    /**
     *
     * @param event
     */
    public void doCreateSuiviSalaire(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newPretSalaire.getVariableSalaires() == null) {
                msg = bundle.getString("EvenSuiviErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newPretSalaire.getDateSuivi() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                Mois mois = moisFacade.findMoisByCode(newPretSalaire.getDateSuivi().getMonth());
                newPretSalaire.setMois(mois);
                newPretSalaire.setProgressionAgent(newProgressionAgent);
                newPretSalaire.setDateCreation(new java.util.Date());
                pretSalaireFacade.createSuivi(newPretSalaire);
                newPretSalaire = new PretSalaire();
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateSuiviHeures(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getSelectedAgent() == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSuiviHeures.getVariableSalaires() == null) {
                msg = bundle.getString("EvenSuiviErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newSuiviHeures.getDateSuivi() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                newProgressionAgent.setAgent(selectedAgent);
                newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                Mois mois = moisFacade.findMoisByCode(newSuiviHeures.getDateSuivi().getMonth());
                newSuiviHeures.setMois(mois);
                newSuiviHeures.setProgressionAgent(newProgressionAgent);
                newSuiviHeures.setDateCreation(new java.util.Date());
                suiviSalaireFacade.createSuivi(newSuiviHeures);
                newSuiviHeures = new SuiviHeures();
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    /**
     * *
     *
     * @param event
     */
    public void doCreateSuiviCollectif(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (this.getListeAgentTableau().isEmpty()) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSalaire.getDetailSalaire() == null) {
                msg = bundle.getString("EvenSuiviErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newProgressionAgent.getDateProgression() == null || newSalaire.getDatePaie() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else {
                for (Agent agent : getListeAgentTableau()) {
                    newProgressionAgent.setAgent(agent);
                    newProgressionAgent = progressionAgentFacade.createProgresseion(newProgressionAgent);
                    Mois mois = moisFacade.findMoisByCode(newSalaire.getDatePaie().getMonth());
                    newSalaire.setMois(mois);
                    salaireFacade.create(newSalaire);
                }
                newSalaire = new Salaire();
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    /**
     * @param event
     */
    public void doCreateSalaireCollectif(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (listeAgentTableau.isEmpty()) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSalaire.getDatePaie() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSalaire.getModePayement() == null || newSalaire.getNatureSalaire() == null) {
                msg = bundle.getString("allInfos");
                JsfUtil.addErrorMessage(msg);
            } else {
                for (Agent agent : listeAgentTableau) {
                    for (DetailSalaire variable : newSalaire.getDetailSalaire()) {
                        variable.setId(commonFacade.getId(variable));
                        variable.setSalaire(newSalaire);
                    }
                    newSalaire.setAgent(agent);
                    salaireFacade.create(newSalaire);
                }
                newSalaire = new Salaire();
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateSalaire(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if (selectedAgent == null) {
                msg = bundle.getString("AgentErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSalaire.getDatePaie() == null) {
                msg = bundle.getString("DateErrorMsg");
                JsfUtil.addErrorMessage(msg);
            } else if (newSalaire.getModePayement() == null || newSalaire.getNatureSalaire() == null) {
                msg = bundle.getString("allInfos");
                JsfUtil.addErrorMessage(msg);
            } else {
                for (DetailSalaire variable : newSalaire.getDetailSalaire()) {
                    variable.setId(commonFacade.getId(variable));
                    variable.setSalaire(newSalaire);
                }
                newSalaire.setAgent(selectedAgent);
                salaireFacade.create(newSalaire);
                newSalaire = new Salaire();
                listeVariableCnss = new ArrayList<>();
                reinitAgent();
                msg = bundle.getString("CreateSuccessMsg");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            msg = bundle.getString("CreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void passDetailVariable() {
        detailSalaire.setId(commonFacade.getId(detailSalaire));
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
    }

    /**
     * **
     *
     */
    public void removeVariabaleFromNewSalaire() {
        newSalaire.getDetailSalaire().remove(this.selectedDetailSalaire);;
    }

    public void addVariableToNewVariables() {
        try {
            if (!newSalaire.getDetailSalaire().isEmpty()) {
                boolean test = true;
                for (DetailSalaire infrToTest : this.newSalaire.getDetailSalaire()) {
                    if (infrToTest.getVariableSalaire().getId().equals(detailSalaire.getVariableSalaire().getId())) {
                        test = false;
                        break;
                    }
                }
                if (test == true) {
                    newSalaire.getDetailSalaire().add(detailSalaire);
                    primes = primes + detailSalaire.getMontant();
                }
            } else {
                newSalaire.getDetailSalaire().add(detailSalaire);
            }
            detailSalaire = new DetailSalaire();
        } catch (Exception e) {
        }
    }

    /**
     * *
     */
    public void removeVariabaleFromNewAgent() {
        this.newSalaire.getDetailSalaire().remove(this.selectedDetailSalaire);
    }

    /**
     * @param agent
     */
    public void ajouterAgentTableauChoisir(Agent agent) {
        if (agent.isCheckbox()) {
            listeAgentTableau.add(agent);
        } else {
            listeAgentTableau.remove(agent);
        }
    }

    /**
     */
    public void ajouterTousAgentsTableau() {
        listeAgentTableau.clear();
        if (isSelelectedAgentcheckbox()) {
            listeAgents.stream().forEach((undossier) -> {
                listeAgentTableau.add(undossier);
            });
        } else {
            listeAgents.stream().forEach((undossier) -> {
                listeAgentTableau.remove(undossier);
            });
        }
    }

    /**
     * @param agent
     * @return
     */
    public String getSalaireNet(Agent agent) {
        double sal = 0, prets = 0;
        try {
            sal = Integer.valueOf(getSalaireBrut(agent));
            List<PretEtat> liste = pretEtatFacade.findEtatPret(agent);
            if (!liste.isEmpty()) {
                for (PretEtat etat : liste) {
                    prets = prets + etat.getReste();
                }
            }
            double cnss = Double.valueOf(getCNSSEmploye(agent));
            double IPTS = Double.valueOf(getIPTSByEmploye(agent));
            sal = sal - (prets + cnss + IPTS);
        } catch (Exception e) {
        }
        return String.valueOf(sal);
    }

    /**
     * @param agent
     * @return le total de la part d'impot d'employeur
     */
    public String getImpotEmployeur(Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                for (VariableSalaire variable : variableSalaireFacade.findVariablesByType(TypeVariable.Impôt)) {
                    if (!variable.getPartEmployeur().isEmpty()) {
                        somme = somme + (Double.valueOf(variable.getPartEmployeur()) / 100) * (salaireUnit);
                    }
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return le total de la part de CNSS employe
     */
    public String getCNSSEmploye(Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                List<VariableSalaire> liste = variableSalaireFacade.findVariablesByType(TypeVariable.CNSS);
                for (VariableSalaire variable : liste) {
                    if (!variable.getPartEmploye().isEmpty()) {
                        somme = somme + (Double.valueOf(variable.getPartEmploye()) / 100) * (salaireUnit);
                    }
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public String getCNSSEmployeur(Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                for (VariableSalaire variable : variableSalaireFacade.findVariablesByType(TypeVariable.CNSS)) {
                    if (!variable.getPartEmployeur().isEmpty()) {
                        somme = somme + (Double.valueOf(variable.getPartEmployeur()) / 100) * (salaireUnit);
                    }
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public ProgressionPoste getProgressionPoste(Agent agent) {
        ProgressionPoste result = null;
        try {
            if (agent != null) {
                result = agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
            }
        } catch (Exception e) {
            System.out.println("== " + e);
        }
        return result;
    }

    /**
     * @param variable
     * @param agent
     * @return
     */
    public String getByCNSSEmploye(VariableSalaire variable, Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                if (!variable.getPartEmploye().isEmpty()) {
                    salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                    somme = (Double.valueOf(variable.getPartEmploye()) / 100) * (salaireUnit);
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param variable
     * @param agent
     * @return
     */
    public String getByCNSSEmployeur(VariableSalaire variable, Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                if (!variable.getPartEmployeur().isEmpty()) {
                    salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                    somme = (Double.valueOf(variable.getPartEmployeur()) / 100) * (salaireUnit);
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param variable
     * @param agent
     * @return
     */
    public String getByPrimeEmploye(VariableSalaire variable, Agent agent) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                if (!variable.getPartEmploye().isEmpty()) {
                    salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                    somme = ((double) Integer.valueOf(variable.getPartEmploye()) / 100) * (salaireUnit);
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public String getPrimesEmploye(Agent agent) {
        double somme = 0;
        try {
            if (agent != null) {
                String salaireUnit = getProgressionPoste(agent).getCategorie().getSalaireMensuel();
                for (DetailVariable variable : agent.getDetailVariableFixes()) {
                    somme = ((double) Integer.valueOf(variable.getVariableSalaire().getPartEmploye()) / 100) * Integer.valueOf(salaireUnit);
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public String getSalaireBrut(Agent agent) {
        double somme = 0;
        try {
            if (agent != null) {
                double primes = Double.valueOf(getPrimesEmploye(agent));
                double salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireMensuel());
                somme = (primes + salaireUnit);
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public String getIPTSByEmploye(Agent agent) {
        double somme = 0;
        try {
            if (agent != null) {
                int salaireBrut = (Integer.valueOf(getSalaireBrut(agent)) / 1000) * 1000;
                ImpotIPTS impot = impotIPTSFacade.findIntervalleByValeur(salaireBrut);
                ImpotEnfant enfant = impotEnfantFacade.findIntervalleByValeur(getProgressionPoste(agent).getNbreEnfant());
                if (impot != null) {
                    somme = ((double) (salaireBrut - impot.getMini()) * (double) (impot.getTaux()) / 100) + (double) impot.getValeur();
                } else {
                    impot = impotIPTSFacade.findImpotByValeur(1);
                    somme = (double) (salaireBrut - impot.getMini()) * (double) (impot.getTaux() / 100) + impot.getValeur();
                }
                if (enfant == null) {
                    enfant = impotEnfantFacade.findIntervalleByValeur(6);
                    somme = somme - somme * (double) (enfant.getTaux() / 100);
                } else {
                    somme = somme - somme * (double) (enfant.getTaux() / 100);
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    public String getEntierValeur(String valeur) {
        int p = 0;
        for (int i = 0; i < valeur.length(); i++) {
            if (valeur.charAt(i) == '.') {
                p = i;
            }
        }
        return valeur.substring(0, p);
    }

    /**
     * @param agent
     * @param mois
     */
    public String getHeureSupplEmploye(Agent agent, Mois mois) {
        double somme = 0, salaireUnit = 0;
        try {
            if (agent != null) {
                List<SuiviHeures> listeHeures = suiviSalaireFacade.findHeureSupplementaires(agent, mois);
                if (!listeHeures.isEmpty()) {
                    salaireUnit = Double.valueOf(getProgressionPoste(agent).getCategorie().getSalaireUnitaire());
                    for (SuiviHeures variable : listeHeures) {
                        somme = (Double.valueOf(variable.getVariableSalaires().getPartEmploye()) / 100) * (salaireUnit);
                    }
                }
            }
        } catch (Exception e) {
        }
        return getEntierValeur(String.valueOf(somme));
    }

    /**
     * @param agent
     * @return
     */
    public boolean getExistHeureSuppl(Agent agent) {
        try {

        } catch (Exception e) {
        }
        boolean resul = false;
        if (agent != null) {
            List<SuiviHeures> listeHeures = suiviSalaireFacade.findHeureSupplementaires(agent, this.newSalaire.getMois());
            if (!listeHeures.isEmpty()) {
                resul = true;
            }
        }
        return resul;
    }

    /**
     */
    public void chargerCollectif() {
        newSalaire.setDetailSalaire(new ArrayList<>());
        listeVariablePrimes = new ArrayList<>();
        detailSalaire = new DetailSalaire();
    }

    /**
     */
    public void charger() {
        primes = null;
        newSalaire.setDetailSalaire(new ArrayList<>());
        try {
            if (selectedAgent != null) {
                for (DetailVariable variable : detailVariableFacade.findVariablesByAgentANDType(selectedAgent, TypeVariable.PrimeFixe)) {
                    detailSalaire.setMontant(variable.getMontant());
                    detailSalaire.setVariableSalaire(variable.getVariableSalaire());
                    newSalaire.getDetailSalaire().add(detailSalaire);
                    listeVariablePrimes.add(variable.getVariableSalaire());
                }
                listeVariableCnss = variableSalaireFacade.findVariablesByType(typeVariable.CNSS);
                detailSalaire = new DetailSalaire();
//                this.primes = getPrimesEmploye(selectedAgent);
            }
        } catch (Exception e) {
        }
    }

    /**
     * *
     */
//    public void heureTravailMois() {
//        double bre = 0;
//        try {
//            if (selectedAgent != null && newSalaire.getMois() != null) {
//                absenceFacade.findHeureByAbsence(newSalaire.getMois().getCode(), selectedAgent);
//                newProgressionPoste = agentFacade.findProgressionPosteById(selectedAgent.getLastProgressionPoste());
//                bre = newProgressionPoste.getBaseHoraire() - absenceFacade.findHeureByAbsence(newSalaire.getMois().getCode(), selectedAgent);
////            heureT = String.valueOf(bre);
////            salaire = StrheureTing.valueOf(Double.valueOf(newProgressionPoste.getCategorie().getSalaireUnitaire()) * bre);
//            }
//        } catch (Exception e) {
//        }
//    }
    /**
     * **
     * @param agent
     * @param variable
     * @return
     */
    public PretEtat getPretByVariable(Agent agent, VariableSalaire variable) {
        PretEtat result = null;
        try {
            if (agent != null && variable != null) {
                result = pretEtatFacade.findEtatPret(agent, variable);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * *
     * @param agent
     * @return
     */
    public boolean getExistPret(Agent agent) {
        boolean result = false;
        try {
            if (agent != null) {
                List<PretEtat> etat = pretEtatFacade.findEtatPret(agent);
                if (!etat.isEmpty()) {
                    result = true;
                }
            }
        } catch (Exception e) {
        }
        return result;
    }
}
