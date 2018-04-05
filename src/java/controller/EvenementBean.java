/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.EvenementFacade;
import ejb.TypeEvenementFacade;
import jpa.Evenement;
import util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.TypeEvenement;
import org.apache.log4j.Logger;

/**
 *
 * @author MJLDH
 */
@Named(value = "evenementBean")
@ViewScoped
public class EvenementBean implements Serializable {

    Logger logger = Logger.getLogger(EvenementBean.class);
    @Inject
    private EvenementFacade evenementFacade;
    private Evenement selectedEvenement;
    private Evenement newEvenement;
    private List<Evenement> listeEvenements;
//Type evènement

    @Inject
    private TypeEvenementFacade typeEvenementFacade;
    private TypeEvenement selectedTypeEvenement;
    private TypeEvenement newTypeEvenement;
    private List<TypeEvenement> listeTypeEvenements;

    /**
     * Creates a new instance of EvenementBean
     */
    public EvenementBean() {
    }

    @PostConstruct
    public void init() {
        listeEvenements = evenementFacade.findAll();
        listeTypeEvenements = typeEvenementFacade.findAll();
        newTypeEvenement=new TypeEvenement();
        newEvenement=new Evenement();
    }

    public Evenement getSelectedEvenement() {
        return selectedEvenement;
    }

    public void setSelectedEvenement(Evenement selectedEvenement) {
        this.selectedEvenement = selectedEvenement;
    }

    public Evenement getNewEvenement() {
        return newEvenement;
    }

    public void setNewEvenement(Evenement newEvenement) {
        this.newEvenement = newEvenement;
    }

    public List<Evenement> getListeEvenements() {
        return listeEvenements;
    }

    public void setListeEvenements(List<Evenement> listeEvenements) {
        this.listeEvenements = listeEvenements;
    }

    public TypeEvenement getSelectedTypeEvenement() {
        return selectedTypeEvenement;
    }

    public void setSelectedTypeEvenement(TypeEvenement selectedTypeEvenement) {
        this.selectedTypeEvenement = selectedTypeEvenement;
    }

    public TypeEvenement getNewTypeEvenement() {
        return newTypeEvenement;
    }

    public void setNewTypeEvenement(TypeEvenement newTypeEvenement) {
        this.newTypeEvenement = newTypeEvenement;
    }

    public List<TypeEvenement> getListeTypeEvenements() {
        return listeTypeEvenements;
    }

    public void setListeTypeEvenements(List<TypeEvenement> listeTypeEvenements) {
        this.listeTypeEvenements = listeTypeEvenements;
    }

    public void passItem(Evenement item) {
        this.selectedEvenement = item;
    }

    public void passItemType(TypeEvenement item) {
        this.selectedTypeEvenement = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.evenementFacade.create(newEvenement);
            msg = bundle.getString("EvenementCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newEvenement = new Evenement();
            this.listeEvenements = this.evenementFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("EvenementCreateErrorMsg");
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
            evenementFacade.edit(selectedEvenement);
            selectedEvenement = null;
            this.listeEvenements = this.evenementFacade.findAll();
            msg = bundle.getString("EvenementEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("EvenementEditErrorMsg");
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
            evenementFacade.remove(selectedEvenement);
            msg = bundle.getString("EvenementDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeEvenements.remove(this.selectedEvenement);
            this.listeEvenements = this.evenementFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("EvenementDelErrorMsg");
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
            this.typeEvenementFacade.create(newTypeEvenement);
            msg = bundle.getString("TypeEvenementCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newTypeEvenement= new TypeEvenement();
            this.listeTypeEvenements = this.typeEvenementFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("TypeEvenementCreateErrorMsg");
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
           typeEvenementFacade.edit(selectedTypeEvenement);
            selectedTypeEvenement = new TypeEvenement();
            this.listeTypeEvenements = this.typeEvenementFacade.findAll();
            msg = bundle.getString("TypeEvenementEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("TypeEvenementEditErrorMsg");
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
            typeEvenementFacade.remove(selectedTypeEvenement);
            msg = bundle.getString("TypeEvenementDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeTypeEvenements.remove(this.selectedTypeEvenement);
            this.listeTypeEvenements = this.typeEvenementFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("TypeEvenementDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
}
