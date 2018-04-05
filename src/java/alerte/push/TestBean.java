/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alerte.push;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 * help to test JMS need for push functionnality 
 * @author xess
 */
@Named
@SessionScoped
public class TestBean implements Serializable{
    
    final static Logger logger = Logger.getLogger("alerte.push.TestBean");
    
    String sender;    
    String recipient;
    String message;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }   
    
    @EJB
    PushFacade facade;
    
    public void push() {
        facade.push(sender, recipient, message);
        logger.info("push done");
    }
    
    public void get() {
        facade.parcourir();
    }
    
    
    
}
