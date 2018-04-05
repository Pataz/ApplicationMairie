/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.DocumentFacade;
import ejb.TypeDocumentFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Document;
import jpa.TypeDocument;
import org.apache.log4j.Logger;
import util.JsfUtil;

/**
 *
 * @author MJLDH
 */
@Named(value = "documentBean")
@ViewScoped
public class DocumentBean implements Serializable {

    Logger logger = Logger.getLogger(DocumentBean.class);
    @Inject
    private DocumentFacade documentFacade;
    private Document selectedDocument;
    private Document newDocument;
    private List<Document> listeDocuments;
//Type evènement

    @Inject
    private TypeDocumentFacade typeDocumentFacade;
    private TypeDocument selectedTypeDocument;
    private TypeDocument newTypeDocument;
    private List<TypeDocument> listeTypeDocuments;

    /**
     * Creates a new instance of DocumentBean
     */
    public DocumentBean() {
    }

    @PostConstruct
    public void init() {
//        listeDocuments = documentFacade.findAll();
        listeTypeDocuments = typeDocumentFacade.findAll();
        newTypeDocument=new TypeDocument();
        newDocument=new Document();
    }

    public Document getSelectedDocument() {
        return selectedDocument;
    }

    public void setSelectedDocument(Document selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public Document getNewDocument() {
        return newDocument;
    }

    public void setNewDocument(Document newDocument) {
        this.newDocument = newDocument;
    }

    public List<Document> getListeDocuments() {
        return listeDocuments;
    }

    public void setListeDocuments(List<Document> listeDocuments) {
        this.listeDocuments = listeDocuments;
    }

    public TypeDocument getSelectedTypeDocument() {
        return selectedTypeDocument;
    }

    public void setSelectedTypeDocument(TypeDocument selectedTypeDocument) {
        this.selectedTypeDocument = selectedTypeDocument;
    }

    public TypeDocument getNewTypeDocument() {
        return newTypeDocument;
    }

    public void setNewTypeDocument(TypeDocument newTypeDocument) {
        this.newTypeDocument = newTypeDocument;
    }

    public List<TypeDocument> getListeTypeDocuments() {
        return listeTypeDocuments;
    }

    public void setListeTypeDocuments(List<TypeDocument> listeTypeDocuments) {
        this.listeTypeDocuments = listeTypeDocuments;
    }

    public void passItem(Document item) {
        this.selectedDocument = item;
    }

    public void passItemType(TypeDocument item) {
        this.selectedTypeDocument = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.documentFacade.create(newDocument);
            msg = bundle.getString("DocumentCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newDocument = new Document();
            this.listeDocuments = this.documentFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("DocumentCreateErrorMsg");
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
            documentFacade.edit(selectedDocument);
            selectedDocument = null;
            this.listeDocuments = this.documentFacade.findAll();
            msg = bundle.getString("DocumentEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("DocumentEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doDel(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            documentFacade.remove(selectedDocument);
            msg = bundle.getString("DocumentDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeDocuments.remove(this.selectedDocument);
            this.listeDocuments = this.documentFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("DocumentDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
       public void doCreateType(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.typeDocumentFacade.create(newTypeDocument);
            msg = bundle.getString("TypeDocumentCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newTypeDocument= new TypeDocument();
            this.listeTypeDocuments = this.typeDocumentFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("TypeDocumentCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEditType(ActionEvent event) {

        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;

        try {
           typeDocumentFacade.edit(selectedTypeDocument);
            selectedTypeDocument = new TypeDocument();
            this.listeTypeDocuments = this.typeDocumentFacade.findAll();
            msg = bundle.getString("TypeDocumentEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("TypeDocumentEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doDelType(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            typeDocumentFacade.remove(selectedTypeDocument);
            msg = bundle.getString("TypeDocumentDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeTypeDocuments.remove(this.selectedTypeDocument);
            this.listeTypeDocuments = this.typeDocumentFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("TypeDocumentDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
}
