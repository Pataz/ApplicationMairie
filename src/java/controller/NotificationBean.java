/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import alerte.NotificationFacade;
import alerte.entity.Notification;
import ejb.AgentFacade;
import ejb.EvenementFacade;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Agent;
import jpa.ProgressionPoste;
import org.apache.log4j.Logger;
import util.JsfUtil;

/**
 *
 * @author MJLDH
 */
@Named(value = "notificationBean")
@ViewScoped
public class NotificationBean implements Serializable {

    Logger logger = Logger.getLogger(NotificationBean.class);
    @Inject
    ContextBean contextBean;
    @EJB
    NotificationFacade notificationFacade;
    @EJB
    EvenementFacade evenementFacade;
    @EJB
    AgentFacade agentFacade;

    private List<Notification> notificationRetraites;
//    private List<Notification> alerte;

    /**
     * Creates a new instance of NotificationBean
     */
    public NotificationBean() {
    }

    @PostConstruct
    public void init() {
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
    }

    public long zDayCounter(Notification notif) {
        LocalDateTime datedujour = LocalDate.now().atStartOfDay();
        Date dateJour = JsfUtil.convertirEnDate(datedujour);
        return JsfUtil.getDaysBetween(dateJour, notif.getDateLimite());
    }

    public List<Notification> getNotificationRetraiteRecentes() {
        notificationRetraites = notificationFacade.trouverNotificationByEvt("ALTR");
        notificationRetraites = notificationFacade.ordonner(notificationRetraites);
        return notificationRetraites.stream().limit(10).collect(Collectors.toList());
    }

    public List<Notification> getNotificationsRecentes() {
        List<Notification> list = getNotifications();
        System.out.println("list Notifications ==> " + list);
        return list.stream().limit(10).collect(Collectors.toList());
    }

    public List<Notification> getAlertesRecentes() {
        List<Notification> list = getAlertes();
        return list.stream().limit(10).collect(Collectors.toList());
    }

    public List<Notification> getNotifications() {
        try {
            System.out.println("getNotifications");

            List<Notification> list = null;
            if (contextBean.getCurrentExerciceFonction() != null) {
                list = notificationFacade.trouverSimpleNotifUtilisateur(contextBean.getCurrentExerciceFonction());
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            System.out.println("Notifications : " + list.size());
            /*
             Collections.sort(list, (Notification n1, Notification n2) -> {
             int r;
             if (n1 == null && n2 == null) {
             r = 0;
             }
             if (n1 == null) {
             r = -1;
             }
             if (n2 == null) {
             r = 1;
             }

             if (n1.getDateCreation() == null && n2.getDateCreation() == null) {
             r = 0;
             }
             if (n1.getDateCreation() == null) {
             r = -1;
             }
             if (n2.getDateCreation() == null) {
             r = 1;
             }

             r = n1.getDateCreation().compareTo(n2.getDateCreation());
             //                System.out.println("n1 : " + n1.getNumeroRG() + " - " + JsfUtil.convertDate(n1.getDateCreation(), "EEEEE dd MMMM yyyy HH:mm"));
             //                System.out.println("n2 : " + n2.getNumeroRG() + " - " + JsfUtil.convertDate(n2.getDateCreation(), "EEEEE dd MMMM yyyy HH:mm"));

             return -r;
             });
             */
            list = notificationFacade.ordonner(list);
            return list;

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Notification> getAlertes() {
        try {
            System.out.println("getAlerts");

            List<Notification> list = null;
            if (contextBean.getCurrentExerciceFonction() != null) {
                list = notificationFacade.trouverAlerteUtilisateur(contextBean.getCurrentExerciceFonction());
            }
            if (list == null) {
                list = new ArrayList<>();
            }
            System.out.println("Alertes : " + list.size());

            list = notificationFacade.ordonner(list);
            return list;
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public String estAlerteDepuis(Notification notif) {
        Period p;
        if (notif == null) {
            p = Period.ZERO;
        } else {
            LocalDate today = LocalDate.now();
            LocalDate limit = JsfUtil.convertirEnLocalDateTime(notif.getDateLimite()).toLocalDate();
            p = Period.between(limit, today);
        }
        return p.getMonths() > 0 ? p.getMonths() + " mois " + p.getDays() + " jours" : p.getDays() + " jours";
    }

    public String goTo(Notification notif) {
        JsfUtil.logInfo(logger, "getHref : " + notif);
        if (notif != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = ResourceBundle.getBundle("forseti.util.Bundle", context.getViewRoot().getLocale());
            String msg = bundle.getString("MEENotifOrienter");
            if (notif.getDescription().startsWith(msg)) {
                String dateStr = notif.getDescription().replace(msg, "");
                dateStr = dateStr.substring(0, dateStr.indexOf("."));
                Date date = JsfUtil.convertLongDate(dateStr);
                //JsfUtil.logInfo(logger, "Notif - Date (String) : " + dateStr + " - Date : " + date);
                dateStr = JsfUtil.convertDate(date, "yyyyMMddHHmm");

                return "/forseticiv/audience/gestion/index.xhtml?audience=" + dateStr;
            }
        }

        return "#";
    }

}
