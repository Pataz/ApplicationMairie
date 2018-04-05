/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.VariableSalaire;
import jpa.TypeVariable;

/**
 *
 * @author Gildasdarex
 */

@Stateless
public class VariableSalaireFacade extends AbstractFacade<VariableSalaire> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VariableSalaireFacade() {
        super(VariableSalaire.class);
    }
     
    /****
     * 
     * @param type
     * @return 
     */
    public List<VariableSalaire> findVariablesByType(TypeVariable type) {
        Query q = getEntityManager().createQuery("select V from VariableSalaire V WHERE  V.typeVariable=?1");
        q.setParameter(1, type);
        List<VariableSalaire> list = q.getResultList();
        if(list.isEmpty()){
            list =  null;
        }
       return list;
    }   
}
