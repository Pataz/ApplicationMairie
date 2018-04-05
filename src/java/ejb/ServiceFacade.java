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
import jpa.Service;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ServiceFacade extends AbstractFacade<Service> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ServiceFacade() {
        super(Service.class);
    }

    /**
     * Liste des services en fonction de la direction passée en paramètre
     *
     * @param direction
     * @return Direction
     */
    public List<Service> findServiceByDirection(Direction direction) {
        Query q = getEntityManager().createQuery("select S from Service S WHERE S.direction=?1");
        q.setParameter(1, direction);
        List<Service> list = q.getResultList();
        return list;
    }
}
