/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.Agent;
import jpa.DetailVariable;
import jpa.TypeVariable;

@Stateless
public class DetailVariableFacade extends AbstractFacade<DetailVariable> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetailVariableFacade() {
        super(DetailVariable.class);
    }
    
    @Override
    public void create(DetailVariable entity) {
        em.persist(entity);
    }
    
    /***
     * 
     * @param type
     * @return 
     */
   public List<DetailVariable> findVariablesByAgent(Agent agent) {
        Query q = getEntityManager().createQuery("select D from DetailVariable D WHERE D.agent =?1");
        q.setParameter(1, agent);
        List<DetailVariable> list = q.getResultList();
       return list;
    }
   
   /***
    * 
     * @param agent
    * @param type
    * @return 
    */
   public List<DetailVariable> findVariablesByAgentANDType(Agent agent , TypeVariable type) {
        Query q = getEntityManager().createQuery("select D from DetailVariable D WHERE D.agent =?1 AND D.variableSalaire.typeVariable=?2");
        q.setParameter(1, agent);
        q.setParameter(2, type);
        List<DetailVariable> list = q.getResultList();
       return list;
    }
}
