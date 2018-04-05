/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum TypeVariable {
    PrimeFixe("Prime Fixe"),
    PrimeVariable("Prime Variable"),
    Impôt("Impôt"),
    HeureSuple("Heure Supplémentaire"),
    CNSS("Cotisation CNSS");
    private final String label;

    private TypeVariable(String label) {
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
