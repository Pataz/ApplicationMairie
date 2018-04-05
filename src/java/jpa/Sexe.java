/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum Sexe {
     F("Féminin"),
    M("Masculin");
    private final String label;

    private Sexe(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label; //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getLabel(){
        return this.label;
    }
    
}
