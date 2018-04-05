/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.List;
import jpa.Categorie;
import jpa.CategorieBase;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import jpa.SousCategorie;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class CategorieFacade extends AbstractFacade<Categorie> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategorieFacade() {
        super(Categorie.class);
    }
    
    /**
     * Liste des Categories en fonction de la categorie de base
     *
     * @param sousCategorie
     * @return Categories
     */
    public List<Categorie> findCategoriesByBase(SousCategorie  sousCategorie) {
        Query q = getEntityManager().createQuery("select C from Categorie C WHERE C.sousCategorie.code = ?1");
        q.setParameter(1 , sousCategorie.getCode());
        List<Categorie> list = q.getResultList();
        return list;
    }
    
}
