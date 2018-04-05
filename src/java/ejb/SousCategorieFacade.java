/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import jpa.Categorie;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.SousCategorie;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class SousCategorieFacade extends AbstractFacade<SousCategorie> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SousCategorieFacade() {
        super(SousCategorie.class);
    }
    
}
