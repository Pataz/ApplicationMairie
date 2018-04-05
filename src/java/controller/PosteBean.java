/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.PosteFacade;
import jpa.Poste;
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
@Named(value = "posteBean")
@ViewScoped
public class PosteBean implements Serializable {

    @Inject
    private PosteFacade posteFacade;
    private Poste selectedPoste;
    private Poste newPoste;
    private List<Poste> listePostes;

    /**
     * Creates a new instance of PosteBean
     */
    public PosteBean() {
        
    }

    @PostConstruct
    public void init() {
        listePostes = posteFacade.findAll();
    }

    public PosteFacade getPosteFacade() {
        return posteFacade;
    }

    public void setPosteFacade(PosteFacade posteFacade) {
        this.posteFacade = posteFacade;
    }

    public Poste getSelectedPoste() {
        return selectedPoste;
    }

    public void setSelectedPoste(Poste selectedPoste) {
        this.selectedPoste = selectedPoste;
    }

    public List<Poste> getListePostes() {
        return listePostes;
    }

    public void setListePostes(List<Poste> listePostes) {
        this.listePostes = listePostes;
    }

    public Poste getNewPoste() {
        if (newPoste == null) {
            newPoste = new Poste();
        }
        return newPoste;
    }

    public void setNewPoste(Poste newPoste) {
        this.newPoste = newPoste;
    }

    public void passItem(Poste item) {
        this.selectedPoste = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.posteFacade.create(newPoste);
            msg = bundle.getString("PosteCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newPoste = new Poste();
            this.listePostes = this.posteFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("PosteCreateErrorMsg");
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
            posteFacade.edit(selectedPoste);
            this.listePostes = this.posteFacade.findAll();
            msg = bundle.getString("PosteEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("PosteEditErrorMsg");
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
            posteFacade.remove(selectedPoste);
            msg = bundle.getString("PosteDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listePostes.remove(this.selectedPoste);
            this.listePostes = this.posteFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("PosteDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

 
}
