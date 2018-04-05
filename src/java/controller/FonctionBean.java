/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.FonctionFacade;
import jpa.Fonction;
import util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MJLDH
 */
@Named(value = "fonctionBean")
@ViewScoped
public class FonctionBean implements Serializable {

    @Inject
    private FonctionFacade fonctionFacade;
    private Fonction selectedFonction;
    private Fonction newFonction;
    private List<Fonction> listeFonctions;

    /**
     * Creates a new instance of FonctionBean
     */
    public FonctionBean() {
        
    }

    @PostConstruct
    public void init() {
        listeFonctions = fonctionFacade.findAll();
    }

    public FonctionFacade getFonctionFacade() {
        return fonctionFacade;
    }

    public void setFonctionFacade(FonctionFacade fonctionFacade) {
        this.fonctionFacade = fonctionFacade;
    }

    public Fonction getSelectedFonction() {
        return selectedFonction;
    }

    public void setSelectedFonction(Fonction selectedFonction) {
        this.selectedFonction = selectedFonction;
    }

    public List<Fonction> getListeFonctions() {
        return listeFonctions;
    }

    public void setListeFonctions(List<Fonction> listeFonctions) {
        this.listeFonctions = listeFonctions;
    }

    public Fonction getNewFonction() {
        if (newFonction == null) {
            newFonction = new Fonction();
        }
        return newFonction;
    }

    public void setNewFonction(Fonction newFonction) {
        this.newFonction = newFonction;
    }

    public void passItem(Fonction item) {
        this.selectedFonction = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {

            this.fonctionFacade.create(newFonction);
            msg = bundle.getString("FonctionCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newFonction = new Fonction();
            this.listeFonctions = this.fonctionFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("FonctionCreateErrorMsg");
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
            fonctionFacade.edit(selectedFonction);
            this.listeFonctions = this.fonctionFacade.findAll();
            msg = bundle.getString("FonctionEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("FonctionEditErrorMsg");
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
            fonctionFacade.remove(selectedFonction);
            msg = bundle.getString("FonctionDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeFonctions.remove(this.selectedFonction);
            this.listeFonctions = this.fonctionFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("FonctionDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

 
}
