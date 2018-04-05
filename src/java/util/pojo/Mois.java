/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.pojo;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 *
 * @author D O N A T I E N
 */
public class Mois {

    private int valeur;
    private String libelle;

    public Mois() {
    }

    public Mois(int valeur) {
        this.valeur = valeur;
        this.libelle = Month.of(valeur).getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase();
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

}
