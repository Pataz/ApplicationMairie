/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CorpsFacade;
import jpa.Corps;
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
@Named(value = "corpsBean")
@ViewScoped
public class CorpsBean implements Serializable {

    @Inject
    private CorpsFacade corpsFacade;
    private Corps selectedCorps;
    private Corps newCorps;
    private List<Corps> listeCorpss;

    /**
     * Creates a new instance of CorpsBean
     */
    public CorpsBean() {
        
    }

    @PostConstruct
    public void init() {
        listeCorpss = corpsFacade.findAll();
    }

    public CorpsFacade getCorpsFacade() {
        return corpsFacade;
    }

    public void setCorpsFacade(CorpsFacade corpsFacade) {
        this.corpsFacade = corpsFacade;
    }

    public Corps getSelectedCorps() {
        return selectedCorps;
    }

    public void setSelectedCorps(Corps selectedCorps) {
        this.selectedCorps = selectedCorps;
    }

    public List<Corps> getListeCorpss() {
        return listeCorpss;
    }

    public void setListeCorpss(List<Corps> listeCorpss) {
        this.listeCorpss = listeCorpss;
    }

    public Corps getNewCorps() {
        if (newCorps == null) {
            newCorps = new Corps();
        }
        return newCorps;
    }

    public void setNewCorps(Corps newCorps) {
        this.newCorps = newCorps;
    }

    public void passItem(Corps item) {
        this.selectedCorps = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {

            this.corpsFacade.create(newCorps);
            msg = bundle.getString("CorpsCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newCorps = new Corps();
            this.listeCorpss = this.corpsFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CorpsCreateErrorMsg");
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
            corpsFacade.edit(selectedCorps);
            this.listeCorpss = this.corpsFacade.findAll();
            msg = bundle.getString("CorpsEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("CorpsEditErrorMsg");
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
            corpsFacade.remove(selectedCorps);
            msg = bundle.getString("CorpsDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeCorpss.remove(this.selectedCorps);
            this.listeCorpss = this.corpsFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CorpsDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

 
}
