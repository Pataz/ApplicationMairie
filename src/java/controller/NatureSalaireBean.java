/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.NatureSalaireFacade;
import jpa.NatureSalaire;
import util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author MJLDH
 */
@Named(value = "natureSalaireBean")
@ViewScoped
public class NatureSalaireBean implements Serializable {

    @EJB
    private NatureSalaireFacade natureSalaireFacade;
    
    private NatureSalaire selectedNatureSalaire;
    private NatureSalaire newNatureSalaire;
    private List<NatureSalaire> listeNatureSalaire;

    /**
     * Creates a new instance of CorpsBean
     */
    public NatureSalaireBean() {
        
    }

    @PostConstruct
    public void init() {
        listeNatureSalaire = natureSalaireFacade.findAll();
        newNatureSalaire = new NatureSalaire();
    }

    public NatureSalaire getSelectedNatureSalaire() {
        return selectedNatureSalaire;
    }

    public void setSelectedNatureSalaire(NatureSalaire selectedNatureSalaire) {
        this.selectedNatureSalaire = selectedNatureSalaire;
    }

    public NatureSalaire getNewNatureSalaire() {
         if (newNatureSalaire == null) {
            newNatureSalaire = new NatureSalaire();
        }
        return newNatureSalaire;
    }

    public void setNewNatureSalaire(NatureSalaire newNatureSalaire) {
        this.newNatureSalaire = newNatureSalaire;
    }

    public List<NatureSalaire> getListeNatureSalaire() {
        return listeNatureSalaire;
    }

    public void setListeNatureSalaire(List<NatureSalaire> listeNatureSalaire) {
        this.listeNatureSalaire = listeNatureSalaire;
    }

    
     public void passItem(NatureSalaire item) {
        this.selectedNatureSalaire = item;
    }
     
    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.natureSalaireFacade.create(newNatureSalaire);
            msg = bundle.getString("NatureSalaireCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newNatureSalaire = new NatureSalaire();
            this.listeNatureSalaire = this.natureSalaireFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("NatureSalaireCreateErrorMsg");
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
            natureSalaireFacade.edit(selectedNatureSalaire);
            this.listeNatureSalaire = this.natureSalaireFacade.findAll();
            msg = bundle.getString("NatureSalaireEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("NatureSalaireEditErrorMsg");
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
            natureSalaireFacade.remove(selectedNatureSalaire);
            msg = bundle.getString("CorpsDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeNatureSalaire.remove(this.selectedNatureSalaire);
            this.listeNatureSalaire = this.natureSalaireFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("NatureSalaireDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

 
}
