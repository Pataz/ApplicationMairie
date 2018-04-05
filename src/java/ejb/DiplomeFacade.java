/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Diplome;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class DiplomeFacade extends AbstractFacade<Diplome> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DiplomeFacade() {
        super(Diplome.class);
    }
     
 /**
     * Liste des direction internes
     * @param intExt
     * @return Direction
     */
//   public List<Direction> findDirectionInterneExterne(InterneExterne intExt) {
//        Query q = getEntityManager().createQuery("select D from Direction D WHERE D.internExtern=?1");
//        q.setParameter(1, intExt);
//        List<Direction> list = q.getResultList();
//       return list;
//    }  
}
