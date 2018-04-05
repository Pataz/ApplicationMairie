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
import jpa.Direction;
import jpa.InterneExterne;
import jpa.TypeDirection;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class DirectionFacade extends AbstractFacade<Direction> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DirectionFacade() {
        super(Direction.class);
    }
    
    /**
     * Liste des direction en fonction du type de direction entré en paramètre
     * @param type
     * @return Direction
     */
   public List<Direction> findDirectionByType(TypeDirection type) {
        Query q = getEntityManager().createQuery("select D from Direction D WHERE D.typeDirection=?1");
        q.setParameter(1, type);
        List<Direction> list = q.getResultList();
       return list;
    }   
 /**
     * Liste des direction internes
     * @param intExt
     * @return Direction
     */
   public List<Direction> findDirectionInterneExterne(InterneExterne intExt) {
        Query q = getEntityManager().createQuery("select D from Direction D WHERE D.internExtern=?1");
        q.setParameter(1, intExt);
        List<Direction> list = q.getResultList();
       return list;
    }  
}
