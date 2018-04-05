/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum Civilite {
    MONSIEUR("M."),
    MADAME("Mme"),
    MADEMOISELLE("Mlle");
    private final String label;

    private Civilite(String label) {
        this.label = label;
    }
    
    public String getLabel(){
        return this.label;
    }

    @Override
    public String toString() {
        return this.label; //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
