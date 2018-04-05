package audit.core;

import audit.ejb.AuditFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author xess
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/*"})
public class AuthFilter implements Filter {
    
    @PersistenceContext EntityManager mgr;
    @EJB  AuditFacade facade;    
    public final static String loginPath = "login.xhtml";
    final static String AUDIT_INFO = "auditInfo";
           
    /**
     * on ajoute les infos d'audit à la session si c'est une nouvelle.
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException 
     */    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        //Assurer l'uniformité de l'encodage dans le l'application
        request.setCharacterEncoding("UTF-8");        
        chain.doFilter(request, response);     
    }    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}    
}
