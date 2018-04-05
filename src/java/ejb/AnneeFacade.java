/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import jpa.Annee;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class AnneeFacade extends AbstractFacade<Annee> {

    @Inject
    private CommonFacade commonFacade;
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnneeFacade() {
        super(Annee.class);
    }

    public Annee findAnnee(String annee) {
        Query q = getEntityManager().createNativeQuery("select A.* from Annee A where A.libelle=?1 ", Annee.class);
        q.setParameter(1, annee);
        List<Annee> list = q.getResultList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public Annee createAnnee(Annee entity) {
        entity.setId(commonFacade.getId(entity));
        getEntityManager().persist(entity);
        return entity;
    }
}
