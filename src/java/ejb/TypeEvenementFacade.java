/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import jpa.Evenement;
import jpa.TypeEvenement;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.SqlQuery;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class TypeEvenementFacade extends AbstractFacade<TypeEvenement> {

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeEvenementFacade() {
        super(TypeEvenement.class);
    }

}
