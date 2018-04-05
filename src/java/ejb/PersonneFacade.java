package ejb;

import jpa.Personne;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Aaron
 */
@Stateless
public class PersonneFacade extends AbstractFacade<Personne> {
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonneFacade() {
        super(Personne.class);
    }
    
    public List<Personne> findPersonnes() {
        Query q = getEntityManager().createNativeQuery("select p.* from Personne p order by p.name",Personne.class);
        List<Personne> list = q.getResultList();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    } 
     public Personne getPersonnePhysiqueByName(String name) {
        List<Personne> personnes;
        Query jpql = em.createQuery("SELECT p1 FROM Personne p1 WHERE p1.name=?1");
        jpql.setParameter(1, name);
        personnes = jpql.getResultList();
        if (personnes.isEmpty()) {
          return null;
        }else{
          return personnes.get(0);
        }
       
    }
}
