/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

/**
 *
 * @author xess
 */
public enum Main {
    GAUCHE("gauche"),
    DROITE("droite");
    
    String main;
    
    Main(String main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return main;
    }   
}
