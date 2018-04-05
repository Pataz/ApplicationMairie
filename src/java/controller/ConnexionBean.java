/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import jpa.ExerciceFonction;
import jpa.Utilisateur;
import util.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Annee;
import org.apache.log4j.Logger;

/**
 *
 * @author Patrick
 */
@Named(value = "connexionBean")
@SessionScoped
public class ConnexionBean implements Serializable {

    Logger logger = Logger.getLogger(ConnexionBean.class);

    @Inject
    ContextBean contextBean;

    public ConnexionBean() {
    }

    @PostConstruct
    public void init() {
        JsfUtil.logInfo(logger, "------- ConnexionBean PostConstruct init debut---------------");
        contextBean.init();
        JsfUtil.logInfo(logger, "------- ConnexionBean PostConstruct init fin---------------");
    }

    public String convertDate(Date d, String format) {
        return JsfUtil.convertDate(d, format);
    }

    public Long getDureeEnJours(Date debut, Date fin) {
        return JsfUtil.getDaysBetween(debut, fin);
    }

    public String arrondir(Double nbre) {
        try {
            return arrondir(nbre, 2);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String arrondir(Double nbre, int dec) {
        try {
            return String.format("%.2f", nbre);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public String convertDate(Date d) {
        return JsfUtil.convertDate(d);
    }

    public String convertDateHeure(Date d) {
        return JsfUtil.convertDateHeure(d);
    }

    public String logout() {
        return contextBean.logout();
    }

    public ExerciceFonction getCurrentExerciceFonction() {
        return contextBean.getCurrentExerciceFonction();
    }
 public Annee getCurrentYear() {
        return contextBean.getCurrentYear();
    }
    public Utilisateur getCurrentUser() {
        return contextBean.getCurrentUser();
    }

}
