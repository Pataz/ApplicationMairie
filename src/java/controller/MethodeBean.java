/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author MJLDH
 */
@Named(value = "methodeBean")
@ViewScoped
public class MethodeBean implements Serializable {
private int cpt, n;
private List l1;
    public MethodeBean() {

    }

    @PostConstruct
    public void init() {

    }

    public String Valeur_Virgule(String str) {
        String deb = "";
        if (estDouble(str) == true) {
            for (int i = 0; i < str.trim().length(); i++) {
                if (str.charAt(i) == '.') {
                    deb = deb + ",";
                } else {
                    deb += str.charAt(i);
                }
            }
        } else {
            deb = str;
        }
        return deb;
    }

    public String CompleZero(String str) {
        double de = 0;
        String ss = "";
        if (estDouble(str) == true) {
            de = Double.parseDouble(str);
            str = "" + de;
            if ((str.trim().length() >= 2 && str.trim().length() < 5)) {
                if (de < 10) {
                    ss = "0" + str.trim();
                    if (ss.length() == 4) {
                        ss = ss + "0";
                    }
                } else {
                    ss = str.trim() + "0";
                }
            } else {
                ss = str;
            }
        } else {
            ss = str;
        }
        return ss;
    }

    public boolean estDouble(Object entree) {
        try {
            Double.parseDouble(entree.toString());
            return true;
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return false;
    }
 public String DebutM(String str) {
        String deb = "", rec = "", rest = "";

        deb = "" + str.charAt(0);
        deb = deb.trim().toUpperCase();
        rec = deb;
        for (int i = 1; i < str.trim().length(); i++) {
            rest = "" + str.charAt(i);
            rest = rest.toLowerCase();
            rec = rec + rest;
        }

        return rec;
    }
    public String DebutMajuscule(String str) {
        String deb = "", rec = "", rest = "";
        cpt = 1;
        str = str.toLowerCase();
        deb = "" + str.charAt(0);
        deb = deb.trim().toUpperCase();
        rec = deb;
        for (int i = 1; i < str.trim().length(); i++) {
            cpt++;
            if (str.charAt(i) == ' ' || str.charAt(i) == '/' || str.charAt(i) == '-' || str.charAt(i) == '\\' || str.charAt(i) == '\'') {
                rec += str.charAt(i);
                i++;
                rest = "" + str.charAt(i);
                rest = rest.trim().toUpperCase();
                rec = rec + rest;
            } else {
                rec += str.charAt(i);
            }
        }
        return rec;
    }

    public String ajusterMajuscule(String str) {
        String rec = "";
        n = 0;
        cpt = 1;
        l1 = new LinkedList();
        for (int i = 0; i < str.trim().length(); i++) {
            cpt++;
            rec += str.charAt(i);
            if (str.charAt(i) == ' ') {
                rec = rec.substring(0, rec.length() - 1);
                l1.add(rec);
                rec = "";
            } else if (i == str.length() - 1) {
                l1.add(rec);
                rec = "";
            }

        }
        for (Object li1 : l1) {
            n++;
            if (n == 1) {
                if (li1.toString().length() > 2) {

                    rec += DebutM(li1.toString());
                } else {

                    rec += DebutM(li1.toString());
                }
            } else {
                if (li1.toString().length() > 2) {
                    rec += " ";
                    rec += DebutMajuscule(li1.toString());
                } else {
                    rec += " ";
                    rec += li1.toString();
                }
            }
        }
        l1 = new LinkedList();
        return rec;
    }
   
}
