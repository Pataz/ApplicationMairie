/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CommonFacade;
import ejb.VariableSalaireFacade;
import jpa.VariableSalaire;
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
@Named(value = "variableSalaireBean")
@ViewScoped
public class VariableSalaireBean implements Serializable {

    @EJB
    private VariableSalaireFacade variableSalaireFacade;
    @EJB
    private CommonFacade commonFacade;
    
    private VariableSalaire selectedVariableSalaire;
    private VariableSalaire newVariableSalaire;
    private List<VariableSalaire> listeVariableSalaires;

    /**
     * Creates a new instance of DirectionBean
     */
    public VariableSalaireBean() {
        
    }

    @PostConstruct
    public void init(){
        newVariableSalaire = new VariableSalaire();
        selectedVariableSalaire = new VariableSalaire();
        listeVariableSalaires = variableSalaireFacade.findAll();
    }

    public List<VariableSalaire> getListeVariableSalaires() {
        return listeVariableSalaires;
    }

    public void setListeVariableSalaires(List<VariableSalaire> listeVariableSalaires) {
        this.listeVariableSalaires = listeVariableSalaires;
    }

    public void passItem(VariableSalaire item) {
        this.selectedVariableSalaire = item;
    }

    public VariableSalaire getSelectedVariableSalaire() {
        return selectedVariableSalaire;
    }

    public void setSelectedVariableSalaire(VariableSalaire selectedVariableSalaire) {
        this.selectedVariableSalaire = selectedVariableSalaire;
    }

    public VariableSalaire getNewVariableSalaire() {
        return newVariableSalaire;
    }

    public void setNewVariableSalaire(VariableSalaire newVariableSalaire) {
        this.newVariableSalaire = newVariableSalaire;
    }

    /***
     * 
     * @param event 
     */
    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            newVariableSalaire.setId(commonFacade.getId(newVariableSalaire));
            this.variableSalaireFacade.create(newVariableSalaire);
            msg = bundle.getString("OperationCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newVariableSalaire = new VariableSalaire();
            this.listeVariableSalaires = this.variableSalaireFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEdit(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            variableSalaireFacade.edit(selectedVariableSalaire);
            this.listeVariableSalaires = this.variableSalaireFacade.findAll();
            msg = bundle.getString("OperationCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    /***
     * 
     * @param event 
     */
    public void doDel(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try{
            variableSalaireFacade.remove(selectedVariableSalaire);
            msg = bundle.getString("OperationCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeVariableSalaires.remove(this.selectedVariableSalaire);
            this.listeVariableSalaires = this.variableSalaireFacade.findAll();
        }catch (Exception e){
            msg = bundle.getString("OperationCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

}
