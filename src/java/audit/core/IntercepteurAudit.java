/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audit.core;

import audit.ejb.AuditFacade;
import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.apache.log4j.Logger;

/**
 * audite les appels des méthode qui portent l'annotation {@code @Audit}.
 * @author xess
 */
@Interceptor
@Audit
public class IntercepteurAudit {
    
    @EJB AuditFacade facade;
    static Logger logger = Logger.getLogger("audit.core.IntercepteurAudit");
    /**
     * crée une entrée d'audit pour l'éxécution d'une méthode annotée avec <code>@Audit</code>.
   
   * @param context
     * @return 
     */
    @AroundInvoke
    public Object auditer(InvocationContext context) {
        Method methode = context.getMethod();
        Audit au = methode.getAnnotation(Audit.class);
        throw new RuntimeException();
    }    
}
