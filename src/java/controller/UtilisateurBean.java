/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.ExerciceFonctionFacade;
import ejb.ServiceFacade;
import ejb.UtilisateurFacade;

import jpa.Service;

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
import jpa.ExerciceFonction;
import jpa.Utilisateur;

/**
 *
 * @author MJLDH
 */
@Named(value = "utilisateurBean")
@ViewScoped
public class UtilisateurBean implements Serializable {

    @Inject
    private UtilisateurFacade utilisateurFacade;
    @Inject
    private ContextBean contextBean;
    @Inject
    private ExerciceFonctionFacade exerciceFonctionFacade;
    private Service newService;
    private List<Utilisateur> listeUtilisateursEtablissement;

    private List<Service> listOnlyService;

    /**
     * Creates a new instance of ServiceBean
     */
    public UtilisateurBean() {
    }

    @PostConstruct
    public void init() {
        listeUtilisateursEtablissement = new ArrayList<>();

    }

    public List<Utilisateur> getListeUtilisateursEtablissement() {
        return listeUtilisateursEtablissement;
    }

    public void setListeUtilisateursEtablissement(List<Utilisateur> listeUtilisateursEtablissement) {
        this.listeUtilisateursEtablissement = listeUtilisateursEtablissement;
    }

   

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {

//            this.serviceFacade.create(newService);
            msg = bundle.getString("ServiceCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newService = new Service();
//            this.listeServices = this.serviceFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("ServiceCreateErrorMsg");
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
//            serviceFacade.edit(selectedService);
//            this.listeServices = this.serviceFacade.findAll();
            msg = bundle.getString("ServiceEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("ServiceEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

}
