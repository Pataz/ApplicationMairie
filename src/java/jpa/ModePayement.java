/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum ModePayement {
    ESPECE("Espèce"),
    CHEQUE("Chèque"),
    VIREMENT("Virement"),;
    private final String label;

    private ModePayement(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label; 
    }

    public String getLabel() {
        return this.label;
    }

}
