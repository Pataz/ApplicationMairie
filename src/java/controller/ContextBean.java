/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.AnneeFacade;
import ejb.ConnexionFacade;
import ejb.ContextFacade;
import jpa.ExerciceFonction;
import jpa.Utilisateur;
import util.JsfUtil;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import jpa.Annee;
import org.apache.log4j.Logger;

/**
 *
 * @author Ordinateur
 */
@Named(value = "contextBean")
@SessionScoped
public class ContextBean implements Serializable {

    Logger logger = Logger.getLogger(ContextBean.class);
    @Inject
    ConnexionFacade connexionFacade;
    @Inject
    AnneeFacade anneeFacade;
    @Inject
    ContextFacade contextFacade;
    private Utilisateur currentUser;
    private boolean hasService = true;
    private String cleOPJ;
    private ExerciceFonction currentExerciceFonction;
    private List<ExerciceFonction> listExerciceFonctions;
    private Annee currentYear;
    private Date dateJour, dateDebut, dateFin;

    final static int DEFAULT_PERIOD_MONTHS_BEFORE = 2;
    final static int DEFAULT_PERIOD_MONTHS_AFTER = 2;

    //@PostConstruct
    public void init() {
        //JsfUtil.logInfo(logger, "########### ContextBean.init - Début ###########");
        // *****************
        // Fixe la zone horaire - Raman
        JsfUtil.logInfo(logger, "SYSTEM TIMEZONE (AVANT) : " + System.getProperty("user.timezone") + "\t\tDEFAULT TIMEZONE : " + TimeZone.getDefault().getDisplayName());
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(null);
        JsfUtil.logInfo(logger, "SYSTEM TIMEZONE (APRES) : " + System.getProperty("user.timezone") + "\t\tDEFAULT TIMEZONE : " + TimeZone.getDefault().getDisplayName());
        // ****************
        FacesContext ctx = FacesContext.getCurrentInstance();
        String login = ctx.getExternalContext().getUserPrincipal().getName();
        JsfUtil.logInfo(logger, "login====>" + login);
        currentYear = anneeFacade.findAnnee(JsfUtil.getCurrentYear());
        if (currentYear == null) {
            currentYear = new Annee();
            currentYear.setLibelle(JsfUtil.getCurrentYear());
            currentYear = anneeFacade.createAnnee(currentYear);
        }
        currentUser = connexionFacade.initCurrentUser(login);
        JsfUtil.logInfo(logger, "login====>" + currentUser.getLogin());
        currentExerciceFonction = connexionFacade.initExerciceFonction(currentUser);

        LocalDateTime datedujour = LocalDate.now().atStartOfDay();
        LocalDateTime debut = datedujour.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime fin = datedujour.with(TemporalAdjusters.lastDayOfMonth()).plusDays(1).minusNanos(1);
        dateJour = JsfUtil.convertirEnDate(datedujour);
        dateDebut = JsfUtil.convertirEnDate(debut);
        this.dateFin = JsfUtil.convertirEnDate(fin);
//        setDateDebut(JsfUtil.convertirEnDate(LocalDateTime.now().minusMonths(DEFAULT_PERIOD_MONTHS_BEFORE).with(TemporalAdjusters.firstDayOfMonth())));
//        setDateFin(JsfUtil.convertirEnDate(LocalDateTime.now().plusMonths(DEFAULT_PERIOD_MONTHS_AFTER).with(TemporalAdjusters.lastDayOfMonth())));
//        JsfUtil.logInfo(logger, "########### ContextBean.init - Initialiser les parametres ###########");
//        contextFacade.initParametres();
//        JsfUtil.logInfo(logger, "########### ContextBean.init - Fin Init parametres ###########");

        JsfUtil.logInfo(logger, "########### ContextBean.init - Fin ###########");

    }

    public Date getDateFin() {
        return dateFin;
    }

    public Date getDateDebut() {
        return dateDebut;

    }

    public void setDateDebut(Date dateDebut) {
        JsfUtil.logInfo(logger, "---> contextBean.setDateDebut() - Début - dateDebut : " + this.dateDebut);
        this.dateDebut = dateDebut;
        JsfUtil.logInfo(logger, "---> contextBean.setDateDebut() - Fin - dateDebut : " + this.dateDebut);
    }

    //    public Date getDateFin() {
    //        return dateFin;
    //    }
    public void setDateFin(Date dateFin) {
        JsfUtil.logInfo(logger, "---> contextBean.setDateFin() - Début - dateFin : " + this.dateFin);
        this.dateFin = dateFin;
        JsfUtil.logInfo(logger, "---> contextBean.setDateFin() - Fin - dateFin : " + this.dateFin);
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    public Utilisateur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Utilisateur currentUser) {
        this.currentUser = currentUser;
    }

    public ExerciceFonction getCurrentExerciceFonction() {
        return currentExerciceFonction;
    }

    public void setCurrentExerciceFonction(ExerciceFonction currentExerciceFonction) {
        this.currentExerciceFonction = currentExerciceFonction;
    }

    public List<ExerciceFonction> getListExerciceFonctions() {
        return listExerciceFonctions;
    }

    public void setListExerciceFonctions(List<ExerciceFonction> listExerciceFonctions) {
        this.listExerciceFonctions = listExerciceFonctions;
    }

    public Annee getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Annee currentYear) {
        this.currentYear = currentYear;
    }

//    public boolean isEtablissement() {
//        try {
//            return currentExerciceFonction.getEtablissement() != null;
//        } catch (NullPointerException e) {
//            return false;
//        }
//
//    }
    public String getARCHIVE_URL() {
        return contextFacade.getARCHIVE_URL();
    }

    public String getPathRoot() {
        return contextFacade.getPathRoot();
    }

    public String getCleOPJ() {
        return cleOPJ;
    }

    public void setCleOPJ(String cleOPJ) {
        this.cleOPJ = cleOPJ;
    }

    public String getCODE_JUGI() {
        return contextFacade.getCODE_JUGI();
    }

    public String getCODE_PCR() {
        return contextFacade.getCODE_PCR();
    }

    public String getCODE_PCG() {
        return contextFacade.getCODE_PCG();
    }

    public String getCODE_JUG() {
        return contextFacade.getCODE_JUG();
    }

    public String getCODE_SECPAR() {
        return contextFacade.getCODE_SECPAR();
    }

    public String getCODE_PG() {
        return contextFacade.getCODE_PG();
    }

    public String getCODE_CA() {
        return contextFacade.getCODE_CA();
    }

    public String getDOS_CRE_SERV_EXP() {
        return contextFacade.getDOS_CRE_SERV_EXP();
    }

    public String getDOS_CRE_SERV_DEST() {
        return contextFacade.getDOS_CRE_SERV_DEST();
    }

    public String getDOS_SERV_ARCHIV() {
        return contextFacade.getDOS_SERV_ARCHIV();
    }

    public String getCODE_CHAMBRE_DISTRIBUTION() {
        return contextFacade.getCODE_CHAMBRE_DISTRIBUTION();
    }

    public String getCODE_SERVICE_ENROLEMENT() {
        return contextFacade.getCODE_SERVICE_ENROLEMENT();
    }

    public String getNUMERO_DOSSIER_RP() {
        return contextFacade.getNUMERO_DOSSIER_RP();
    }

    public String getALFRESCO_REPOSITORY() {
        return contextFacade.getALFRESCO_REPOSITORY();
    }

    public String getDEVISE_TOGO() {
        return contextFacade.getDEVISE_TOGO();
    }

    public String getLIBELLE_PAYS() {
        return contextFacade.getLIBELLE_PAYS();
    }

    public String getCURRENTPAYSCODE() {
        return contextFacade.getCURRENTPAYSCODE();
    }

    public String getAPPEL_PATH() {
        return contextFacade.getAPPEL_PATH();
    }

    public String getBIOMETRIE_PATH() {
        return contextFacade.getBIOMETRIE_PATH();
    }

    public String getOPJ_PV_PATH() {
        return contextFacade.getOPJ_PV_PATH();
    }

    public boolean isFEU_ROUGE() {
        return contextFacade.isFEU_ROUGE();
    }

    public String getCmisURL() {
        return contextFacade.getCmisURL();
    }

    public String getCmisUsername() {
        return contextFacade.getCmisUsername();
    }

    public String getCmisPassword() {
        return contextFacade.getCmisPassword();
    }

    public String logout() {
        //ResourceBundle bundle = ResourceBundle.getBundle("util.Bundle", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        //Log.trace(sessionBean.class, bundle.getString("GoodByeDialogTitle"), currentUser, "logout", currentTPI, currentExercer, null);
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
////        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
//        return "/photo/index.xhtml";
    }

    public static String getJsfVersion() {
        return FacesContext.class.getPackage().getImplementationVersion();
    }

}
