/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.ModeleFacade;
import jpa.Modele;
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
@Named(value = "modeleBean")
@ViewScoped
public class ModeleBean implements Serializable {

    @Inject
    private ModeleFacade modeleFacade;
    private Modele selectedModele;
    private Modele newModele;
    private List<Modele> listeModeles;

    /**
     * Creates a new instance of ModeleBean
     */
    public ModeleBean() {
        
    }

    @PostConstruct
    public void init() {
        listeModeles = modeleFacade.findAll();
      
    }

    public ModeleFacade getModeleFacade() {
        return modeleFacade;
    }

    public void setModeleFacade(ModeleFacade modeleFacade) {
        this.modeleFacade = modeleFacade;
    }

    public Modele getSelectedModele() {
        return selectedModele;
    }

    public void setSelectedModele(Modele selectedModele) {
        this.selectedModele = selectedModele;
    }

    public List<Modele> getListeModeles() {
        return listeModeles;
    }

    public void setListeModeles(List<Modele> listeModeles) {
        this.listeModeles = listeModeles;
    }

    public Modele getNewModele() {
        if (newModele == null) {
            newModele = new Modele();
        }
        return newModele;
    }

    public void setNewModele(Modele newModele) {
        this.newModele = newModele;
    }

    public void passItem(Modele item) {
        this.selectedModele = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {

            this.modeleFacade.create(newModele);
            msg = bundle.getString("ModeleCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newModele = new Modele();
            this.listeModeles = this.modeleFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("ModeleCreateErrorMsg");
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
            modeleFacade.edit(selectedModele);
            this.listeModeles = this.modeleFacade.findAll();
            msg = bundle.getString("ModeleEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("ModeleEditErrorMsg");
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
            modeleFacade.remove(selectedModele);
            msg = bundle.getString("ModeleDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeModeles.remove(this.selectedModele);
            this.listeModeles = this.modeleFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("ModeleDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
}
