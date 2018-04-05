/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AgentFacade;
import ejb.CommonFacade;
import ejb.DirectionFacade;
import ejb.EvenementFacade;
import ejb.PersonneFacade;
import ejb.PlanningCongeFacade;
import ejb.ServiceFacade;
import ejb.StatutFacade;
import ejb.TypeDocumentFacade;
import ejb.VariableSalaireFacade;
import ejb.ProgressionAgentFacade;
import ejb.ProgressionPosteFacade;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import jpa.Agent;
import util.JsfUtil;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import jpa.DetailVariable;
import jpa.VariableSalaire;

import jpa.Direction;
import jpa.Document;
import jpa.PlanningConge;
import jpa.ProgressionAgent;
import jpa.ProgressionPoste;
import jpa.Service;
import jpa.TypeDirection;
import jpa.TypeDocument;
import jpa.TypeVariable;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import util.Pieces;

/**
 *
 * @author MJLDH
 */
@Named(value = "agentBean")
@ViewScoped
public class AgentBean implements Serializable {

    Logger logger = Logger.getLogger(AgentBean.class);
    @EJB
    private CommonFacade commonFacade;
    @Inject
    private MethodeBean methodeBean;
    @Inject
    private ContextBean contextBean;
    @EJB
    private PersonneFacade personneFacade;
    @EJB
    private AgentFacade agentFacade;
    @EJB
    private StatutFacade statutFacade;
    @EJB
    private EvenementFacade evenementFacade;
    @EJB
    private PlanningCongeFacade planningCongeFacade;
    @EJB
    private DirectionFacade directionFacade;
    @EJB
    private ServiceFacade serviceFacade;
    @EJB
    private VariableSalaireFacade variableSalaireFacade;
    @EJB
    private TypeDocumentFacade typeDocumentFacade;
    @EJB
    private ProgressionAgentFacade progressionAgentFacade;
    @EJB
    private ProgressionPosteFacade progressionPosteFacade;

    private Agent selectedAgent;
    private Agent newAgent;
    private DetailVariable selectedDetailVariable;
    private DetailVariable detailVariable;
    private ProgressionPoste newProgressionPoste;
    private List<Agent> listeAgents;
    private List<VariableSalaire> listeVariableSalaires;
    private List<Direction> listeDirections;
    private List<Service> listeServices;
    private Part photoIdentite, photoIdentiteP;
    private TypeDirection selectedTypeDirection;
    private Direction selectedDirection;
    private ProgressionAgent newProgressionAgent;
    private String filePhotoIdentite;
    private UploadedFile file;
    private StreamedContent imagestream;
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    String destination = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/photoProfilTempon");

    private List<TypeDocument> listeTypeDocuments, listeTypeDocumentsRemove;
    private List<Document> listDocumentToPersit;
    private Document newDocument;
    private List<Pieces> listePieces;
    private List<Part> listFile;
    private String viewId, viewMat;

    /**
     * Creates a new instance of AgentBean
     */
    public AgentBean() {

    }

    @PostConstruct
    public void init() {
        listeDirections = new ArrayList<>();
        listeServices = new ArrayList<>();
        selectedTypeDirection = null;
        selectedDirection = new Direction();
        newProgressionAgent = new ProgressionAgent();
        newProgressionPoste = new ProgressionPoste();
        listDocumentToPersit = new ArrayList<>();
        newDocument = new Document();
        listeTypeDocuments = typeDocumentFacade.findAll();
        listeTypeDocumentsRemove = new ArrayList<>();
        listePieces = new ArrayList<>();
        newAgent = new Agent();
        detailVariable = new DetailVariable();
        newAgent.setDetailVariableFixes(new ArrayList<>());
        listeVariableSalaires = variableSalaireFacade.findVariablesByType(TypeVariable.PrimeFixe);
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        if (agent != null) {
            return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
        } else {
            return new ProgressionPoste();
        }
    }

    /**
     * **
     *
     * @param agent
     * @return
     */
    public ProgressionAgent getProgressionAgent(Agent agent) {
        if (agent != null) {
            return agentFacade.findProgressionAgentById(agent.getLastProgressionAgent());
        } else {
            return new ProgressionAgent();
        }
    }

    /**
     * **
     *
     * @param agent
     * @return
     */
    public PlanningConge getPlanningConge(Agent agent) {
        if (agent != null) {
            return planningCongeFacade.findFinalPlanningByAgent(agent, contextBean.getCurrentYear());
        } else {
            return new PlanningConge();
        }
    }

    public String getFinFonction(Agent agent) {
        if (agent != null) {
            return JsfUtil.convertDate(JsfUtil.differenceFunction(agent.getPersonne().getDateNaissance(), 60), "dd MMM yyyy");
        } else {
            return "";
        }
    }

    public PlanningConge getFinalPlanningConge(Agent agent) {
        if (agent != null) {
            return planningCongeFacade.findFinalPlanningByAgent(agent, contextBean.getCurrentYear());
        } else {
            return new PlanningConge();
        }
    }

    public List<ProgressionAgent> getListeProgressionAgents(Agent agent) {
        if (agent != null) {
            return agentFacade.findAllProgressionAgent(agent);
        } else {
            return new ArrayList<>();
        }
    }

    public DetailVariable getDetailVariable() {
        return detailVariable;
    }

    public void setDetailVariable(DetailVariable detailVariable) {
        this.detailVariable = detailVariable;
    }

    public DetailVariable getSelectedDetailVariable() {
        return selectedDetailVariable;
    }

    public void setSelectedDetailVariable(DetailVariable selectedDetailVariable) {
        this.selectedDetailVariable = selectedDetailVariable;
    }

    public ProgressionPoste getNewProgressionPoste() {
        return newProgressionPoste;
    }

    public void setNewProgressionPoste(ProgressionPoste newProgressionPoste) {
        this.newProgressionPoste = newProgressionPoste;
    }

    public ProgressionAgent getNewProgressionAgent() {
        return newProgressionAgent;
    }

    public void setNewProgressionAgent(ProgressionAgent newProgressionAgent) {
        this.newProgressionAgent = newProgressionAgent;
    }

    public List<VariableSalaire> getListeVariableSalaires() {
        return listeVariableSalaires;
    }

    public void setListeVariableSalaires(List<VariableSalaire> listeVariableSalaires) {
        this.listeVariableSalaires = listeVariableSalaires;
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

    public Agent getNewAgent() {
        if (newAgent == null) {
            newAgent = new Agent();
        }
        return newAgent;
    }

    public void setNewAgent(Agent newAgent) {
        this.newAgent = newAgent;
    }

    public void passItem(Agent item) {
        this.selectedAgent = item;
    }

    public Part getPhotoIdentite() {
        return photoIdentite;
    }

    public void setPhotoIdentite(Part photoIdentite) {
        this.photoIdentite = photoIdentite;
    }

    public String getFilePhotoIdentite() {
        return filePhotoIdentite;
    }

    public void setFilePhotoIdentite(String filePhotoIdentite) {
        this.filePhotoIdentite = filePhotoIdentite;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
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

    public List<TypeDocument> getListeTypeDocumentsRemove() {
        return listeTypeDocumentsRemove;
    }

    public void setListeTypeDocumentsRemove(List<TypeDocument> listeTypeDocumentsRemove) {
        this.listeTypeDocumentsRemove = listeTypeDocumentsRemove;
    }

    public List<Document> getListDocumentToPersit() {
        return listDocumentToPersit;
    }

    public void setListDocumentToPersit(List<Document> listDocumentToPersit) {
        this.listDocumentToPersit = listDocumentToPersit;
    }

    public Document getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(Document newDocument) {
        this.newDocument = newDocument;
    }

    public List<Pieces> getListePieces() {
        return listePieces;
    }

    public void setListePieces(List<Pieces> listePieces) {
        this.listePieces = listePieces;
    }

    public List<Part> getListFile() {
        return listFile;
    }

    public void setListFile(List<Part> listFile) {
        this.listFile = listFile;
    }

    public List<TypeDocument> getListeTypeDocuments() {
        return listeTypeDocuments;
    }

    public void setListeTypeDocuments(List<TypeDocument> listeTypeDocuments) {
        this.listeTypeDocuments = listeTypeDocuments;
    }

    public void receiveImagestream2() throws IOException {
        System.out.println("entrer");
        if (file != null) {
            System.out.println("entrer If");
            imagestream = new DefaultStreamedContent(file.getInputstream(), null);

        } else {
            System.out.println("entrer Eles");
            imagestream = new DefaultStreamedContent();

        }

    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getViewMat() {
        return viewMat;
    }

    public void setViewMat(String viewMat) {
        this.viewMat = viewMat;
    }

    public StreamedContent getImagestream() {
        return imagestream;
    }

    public void setImagestream(StreamedContent imagestream) {
        this.imagestream = imagestream;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle("util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            newAgent.getPersonne().setId(commonFacade.getId(newAgent.getPersonne()));
            newAgent.getPersonne().setName(newAgent.getPersonne().getNom().toUpperCase().trim() + " " + returnMajusculeAjustee(newAgent.getPersonne().getPrenom().trim()));
            personneFacade.create(newAgent.getPersonne());
            if (photoIdentiteP != null) {
                File FFace = new File(destination + "\\" + photoIdentiteP.getSubmittedFileName());
                newAgent.setFace(getByteTelecharger(FFace));
            }

            for (DetailVariable variable : newAgent.getDetailVariableFixes()) {
                variable.setId(commonFacade.getId(variable));
                variable.setAgent(newAgent);
            }
            agentFacade.create(newAgent);
            newProgressionAgent = agentFacade.createProgression(newAgent, evenementFacade.find("ENR1"), statutFacade.find("ENC"), contextBean.getCurrentUser(), contextBean.getCurrentYear(), null, new Date());
            newProgressionPoste.setId(commonFacade.getId(newProgressionPoste));
            newProgressionPoste.setProgressionAgent(newProgressionAgent);
            progressionPosteFacade.createProgression(newProgressionPoste);
//            agentFacade.createProgressionPoste(newProgressionPoste);
            viewMat = newAgent.getMatricule();

            viewId = newAgent.getId();
            System.out.println("=== Matri " + viewMat);
            if (newProgressionPoste.getDirection() != null) {
                newAgent.setLastDirection(newProgressionPoste.getDirection().getCode());
            }

            if (newProgressionPoste.getService() != null) {
                newAgent.setLastService(newProgressionPoste.getService().getCode());
            }

            newAgent.setLastProgressionAgent(newProgressionAgent.getId());
            newAgent.setEtat(newProgressionAgent.getStatut().getCode());
            newAgent.setLastProgressionPoste(newProgressionPoste.getId());
            agentFacade.edit(newAgent);

            msg = "Agent du N° matricule :" + viewMat + " crée avec succès. Veuillez déposer les pièces!";
            JsfUtil.addSuccessMessage(msg);

            RequestContext.getCurrentInstance().execute("$('#confirCreateDialog').modal('show')");
            newAgent = new Agent();
            newProgressionPoste = new ProgressionPoste();
            selectedTypeDirection = null;
        } catch (Exception e) {
            msg = bundle.getString("AgentCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEdit(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            agentFacade.edit(selectedAgent);
            this.listeAgents = this.agentFacade.findAll();
            msg = bundle.getString("AgentEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("AgentEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    /**
     * ***
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public byte[] getByteTelecharger(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            System.err.println("Erreur--->" + ex);
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }

    /**
     * ***
     *
     * @param file
     */
    public void saveTest(Part file) {
        try {
            photoIdentiteP = photoIdentite;
            System.out.println("filename==>" + (destination + "\\" + photoIdentite.getSubmittedFileName()));
            File filepath = new File(destination);
            filePhotoIdentite = filepath.getAbsolutePath() + "\\" + file.getSubmittedFileName();
            System.out.println("filePhotoIdentite1111==>" + filePhotoIdentite);
            System.out.println("filePhotoIdentite100===>" + filePhotoIdentite);
            Path cible = Paths.get(filePhotoIdentite);
            if (Files.exists(cible)) {

            } else {
                InputStream input = file.getInputStream();
                Files.copy(input, new File(destination, file.getSubmittedFileName()).toPath());
            }
        } catch (Exception e) {
            System.out.println(" filePhotoIdentite===>" + filePhotoIdentite);
        }
    }

    public void returnDirections() {
        listeDirections = directionFacade.findDirectionByType(selectedTypeDirection);
    }

    public void returnServices() {
        listeServices = serviceFacade.findServiceByDirection(newProgressionPoste.getDirection());
    }

    public String returnMajusculeAjustee(String str) {
        return methodeBean.ajusterMajuscule(str);
    }

    public void passDetailVariable() {
        System.out.println("=== " + newProgressionPoste.getDirection());
    }

    /**
     ***
     *
     */
    public void addVariableToNewVariables() {
        System.out.println("==== essai Poste" + newProgressionPoste.getPoste());
        try {
            if (!newAgent.getDetailVariableFixes().isEmpty()) {
                boolean test = true;
                for (DetailVariable infrToTest : this.newAgent.getDetailVariableFixes()) {
                    if (infrToTest.getVariableSalaire().getId().equals(detailVariable.getVariableSalaire().getId())) {
                        test = false;
                        break;
                    }
                }
                if (test == true) {
                    newAgent.getDetailVariableFixes().add(detailVariable);
                }
            } else {
                newAgent.getDetailVariableFixes().add(detailVariable);
            }
            detailVariable = new DetailVariable();
            System.out.println("==== essai " + newProgressionPoste.getPoste());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeVariabaleFromNewAgent() {
        this.newAgent.getDetailVariableFixes().remove(this.selectedDetailVariable);
    }
}
