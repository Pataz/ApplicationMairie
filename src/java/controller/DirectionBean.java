/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.DirectionFacade;
import jpa.Direction;
import util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author MJLDH
 */
@Named(value = "directionBean")
@ViewScoped
public class DirectionBean implements Serializable {

    @EJB
    private DirectionFacade directionFacade;
    
    private Direction selectedDirection;
    private Direction newDirection;
    private List<Direction> listeDirections;

    /**
     * Creates a new instance of DirectionBean
     */
    public DirectionBean() {
        
    }

    @PostConstruct
    public void init() {
        listeDirections = directionFacade.findAll();
        selectedDirection = new Direction();
    }

    public DirectionFacade getDirectionFacade() {
        return directionFacade;
    }

    public void setDirectionFacade(DirectionFacade directionFacade) {
        this.directionFacade = directionFacade;
    }

    public Direction getSelectedDirection() {
        return selectedDirection;
    }

    public void setSelectedDirection(Direction selectedDirection) {
        this.selectedDirection = selectedDirection;
    }

    public List<Direction> getListeDirections() {
        return listeDirections;
    }

    public void setListeDirections(List<Direction> listeDirections) {
        this.listeDirections = listeDirections;
    }

    public Direction getNewDirection() {
        if (newDirection == null) {
            newDirection = new Direction();
        }
        return newDirection;
    }

    public void setNewDirection(Direction newDirection) {
        this.newDirection = newDirection;
    }

    public void passItem(Direction item) {
        this.selectedDirection = item;
    }

    public void doCreate(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.directionFacade.create(newDirection);
            msg = bundle.getString("DirectionCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newDirection = new Direction();
            this.listeDirections = this.directionFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("DirectionCreateErrorMsg");
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
            directionFacade.edit(selectedDirection);
            this.listeDirections = this.directionFacade.findAll();
            msg = bundle.getString("DirectionEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("DirectionEditErrorMsg");
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
            directionFacade.remove(selectedDirection);
            msg = bundle.getString("DirectionDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            this.listeDirections.remove(this.selectedDirection);
            this.listeDirections = this.directionFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("DirectionDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

 
}
