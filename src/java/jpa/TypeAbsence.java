/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum TypeAbsence {

    DECLARATION("Déclaration d'absence"),
    INJUSTIFIE("Absence injustifiée");
    private final String label;

    private TypeAbsence(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    @Override
    public String toString() {
        return this.label; //To change body of generated methods, choose Tools | Templates.
    }

}
