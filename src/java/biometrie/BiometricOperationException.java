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
public class BiometricOperationException extends Exception {

    /**
     * Creates a new instance of <code>BiometricOperationException</code>
     * without detail message.
     */
    public BiometricOperationException() {
    }

    /**
     * Constructs an instance of <code>BiometricOperationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public BiometricOperationException(String msg) {
        super(msg);
    }
    
    public BiometricOperationException(Throwable ex) {
        super(ex);
    }
}
