/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerte;

/**
 *
 * @author xess
 */
public class NotificationException extends RuntimeException {

    /**
     * Creates a new instance of <code>NotificationException</code> without
     * detail message.
     */
    public NotificationException() {
    }

    /**
     * Constructs an instance of <code>NotificationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NotificationException(String msg) {
        super(msg);
    }
    
    public NotificationException(String msg, Throwable ex) {
        super(msg, ex);        
    }
}
