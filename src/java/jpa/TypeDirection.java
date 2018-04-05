/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa;

/**
 *
 * @author SI-MJLDH
 */
public enum TypeDirection {

    Cabinet("Cabinet du Ministre"),
    Centrale("Direction Centrale"),
    Technique("Direction Technique"),
    Structure("Structure sous tutelle"),
    Secretariat("Secrétariat Général du Ministre");
    private final String label;

    private TypeDirection(String label) {
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
