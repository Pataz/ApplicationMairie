/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum TypeAvancement {

    CP("Concours professionnel"),
    DA("Demande d'avancement"),
    EP("Examen professionnel");
    private final String label;

    private TypeAvancement(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label; //To change body of generated methods, choose Tools | Templates.
    }

    public String getLabel() {
        return this.label;
    }

}
