/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import controller.ContextBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class CommonFacade {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @Inject
    private CompteurFacade compteurFacade;

 

   /* public <T> void setId(T entity) {
        try {
            System.out.println("enti " + entity.getClass());
            Field d = entity.getClass().getDeclaredField("id");
            String var = compteurFacade.getNext(entity.getClass().getSimpleName(), "1101");
            System.out.println("var " + var);
            entity.getClass().getMethod("setId").invoke(entity, var);
        } catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        }
    }*/

    public <T> String getId(T entity) {
        try {
            System.out.println("getId : " + entity.getClass());
            
            String id = compteurFacade.getNext(entity.getClass().getSimpleName(),"1100");
            System.out.println("Id : " + id);
            
            return id;
        } catch (Exception e) {
            System.out.println("Erreur --- >>"+e.getMessage());
            return null;
        }
    }
     public String getMatricule() {
        try {
            String id = compteurFacade.getGenerateMatricule("Matricule-", "1012");
              return id;
         } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
      public String getPasswordGenerated() {
        try {
            String id = compteurFacade.getGeneratePassword("Password");
              return id;
         } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
       public String getLoginGenerated() {
        try {
            String id = compteurFacade.getGenerateLogin("Login");
              return id;
         } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
