/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AgentFacade;
import ejb.CommonFacade;
import ejb.DocumentFacade;
import ejb.FonctionFacade;
import ejb.TypeDocumentFacade;
import util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import jpa.Agent;
import jpa.Document;
import jpa.Personnel;
import jpa.TypeDocument;
import org.apache.log4j.Logger;
import util.Pieces;

/**
 *
 * @author MJLDH
 */
@Named(value = "depotPieceBean")
@ViewScoped
public class DepotPieceBean implements Serializable {

    Logger logger = Logger.getLogger(DepotPieceBean.class);
    @Inject
    private ContextBean contextBean;
    @Inject
    private DocumentFacade documentFacade;
    @Inject
    private CommonFacade commonFacade;
    @Inject
    private AgentFacade agentFacade;
    @Inject
    private FonctionFacade fonctionFacade;
    @Inject
    private TypeDocumentFacade typeDocumentFacade;
    private List<TypeDocument> listeTypeDocuments, listeTypeDocumentsRemove;
    private List<Document> listDocumentToPersit;
    private List<Agent> listeAgents;
    private Document newDocument;
    private Agent selectedAgent;
    private Personnel selectedPersonnel;
    private Part file;
    private List<Pieces> listePieces;
    private List<Part> listFile;
    private String id;

    /**
     * Creates a new instance of EnseignantBean
     */
    public DepotPieceBean() {

    }

    @PostConstruct
    public void init() {
        selectedPersonnel = null;
        listeAgents = new ArrayList<>();
        newDocument = new Document();
        selectedAgent = null;
        listeTypeDocuments = typeDocumentFacade.findAll();
        listeTypeDocumentsRemove = new ArrayList<>();
        listePieces = new ArrayList<>();
        listDocumentToPersit = new ArrayList<>();
        getURLId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TypeDocument> getListeTypeDocuments() {
        return listeTypeDocuments;
    }

    public void setListeTypeDocuments(List<TypeDocument> listeTypeDocuments) {
        this.listeTypeDocuments = listeTypeDocuments;
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

    public List<Agent> getListeAgents() {
        return listeAgents;
    }

    public void setListeAgents(List<Agent> listeAgents) {
        this.listeAgents = listeAgents;
    }

    public Agent getSelectedAgent() {
        return selectedAgent;
    }

    public void setSelectedAgent(Agent selectedAgent) {
        this.selectedAgent = selectedAgent;
    }

    public Personnel getSelectedPersonnel() {
        return selectedPersonnel;
    }

    public void setSelectedPersonnel(Personnel selectedPersonnel) {
        this.selectedPersonnel = selectedPersonnel;
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return "";
    }

    public void getURLId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            id = facesContext.getExternalContext().getRequestParameterMap().get("id");
            if (id == null) {
            } else {
                System.out.println("longueur==>" + id.length());
                System.out.println("id.substring(2, size)==>" + id.substring(2, id.length()));
                if (id.charAt(0) == 's') {
                    System.out.println("je suis stagiaire");
                }
                if (id.charAt(0) == 'a') {
                    System.out.println("je suis agent");
                    selectedAgent = agentFacade.find(id.substring(2, id.length()));
                }
            }
        } catch (Exception e) {
            JsfUtil.logError(logger, "dossier  ");
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
                for (Document doc : listDocumentToPersit) {
                    doc.setId(commonFacade.getId(doc));
                    documentFacade.create(doc);
                    documentFacade.createAgentDocument(selectedAgent, doc);
                }
            }
            selectedPersonnel=null;
            selectedAgent = null;
            listePieces=new ArrayList<>();
             msg = bundle.getString("DepotPieceCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("DepotPieceCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void ajouterDocument(AjaxBehavior event) {
        if (file != null) {
            try {
                listePieces.add(new Pieces(newDocument.getTypeDocument(), getFilename(file), file.getSize(), file));
                listeTypeDocumentsRemove.add(newDocument.getTypeDocument());
                Document newDocumentToPersit = new Document();
                newDocumentToPersit.setFileName(getFilename(file));
                newDocumentToPersit.setMimeType(file.getContentType());
                newDocumentToPersit.setTaille("" + file.getSize());
                newDocumentToPersit.setSens(true);
                newDocumentToPersit.setTypeDocument(newDocument.getTypeDocument());
                listDocumentToPersit.add(newDocumentToPersit);
                listFile.add(file);
                //     ("Ajouter dans liste pieces" + listedespieces);
            } catch (Exception e) {
            }

        }
        System.out.println("listeTypeDocumentsRemove add==>" + listeTypeDocumentsRemove);
        listeTypeDocuments.removeAll(listeTypeDocumentsRemove);
        System.out.println("listeTypeDocuments add ==>" + listeTypeDocuments);
        file = null;
        newDocument.setTypeDocument(new TypeDocument());
    }

    public void retirerDocument(AjaxBehavior event, Pieces p) {
        if (p == null) {

        } else {
            listeTypeDocumentsRemove.remove(p.getTypeDocument());
            listeTypeDocuments.add(p.getTypeDocument());
            System.out.println("listeTypeDocumentsRemove==>" + listeTypeDocumentsRemove);
            Document newDocumentToPersit = new Document();
            newDocumentToPersit.setFileName(getFilename(p.getFile()));
            newDocumentToPersit.setMimeType(p.getFile().getContentType());
            newDocumentToPersit.setTaille("" + p.getFile().getSize());
            newDocumentToPersit.setSens(true);
            newDocumentToPersit.setTypeDocument(p.getTypeDocument());
            listDocumentToPersit.remove(newDocumentToPersit);
            listePieces.remove(p);
            listeTypeDocuments.removeAll(listeTypeDocumentsRemove);
        }
        System.out.println("listeTypeDocuments==>" + listeTypeDocuments);

    }

    public void returnPersonnel() {
        if (selectedPersonnel == Personnel.AGENT) {
            listeAgents = agentFacade.findAgentsInterne();
        }
    }
}
