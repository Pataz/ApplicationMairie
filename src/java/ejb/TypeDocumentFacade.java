package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.TypeDocument;

/**
 *
 * @author Aaron
 */
@Stateless
public class TypeDocumentFacade extends AbstractFacade<TypeDocument> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeDocumentFacade() {
        super(TypeDocument.class);
    }   
}