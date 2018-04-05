/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometrie;

/**
 * les doigts de la main.
 * @author xess
 */
public enum Doigt {
    POUCE(1),
    INDEX(2),
    MAJEUR(3),
    ANNULAIRE(4),
    AURICULAIRE(5);
    
    private final int pos ;
    
    Doigt(int pos) {
        this.pos = pos;
    }
    
    public int getPos() {
        return pos;
    }    
    
}
