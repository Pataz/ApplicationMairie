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
import jpa.Annee;
import jpa.ExerciceFonction;
import jpa.Utilisateur;
import org.apache.log4j.Logger;

/**
 *
 * @author Gildasdarex
 */
@Stateless
public class ExerciceFonctionFacade extends AbstractFacade<ExerciceFonction> {

    Logger logger = Logger.getLogger(ExerciceFonctionFacade.class);

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
//    @Inject
//    private ContextBean contextBean;
//      @Inject
//    private CompteurFacade compteurFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExerciceFonctionFacade() {
        super(ExerciceFonction.class);
    }

    /**
     * trouve la fonction par defaut et non expirée d'un utilisateur
     *
     * @param utilisateur
     * @return ExerciceFonction
     */
    public ExerciceFonction findExercicefonction(Utilisateur utilisateur) {
        Query q = getEntityManager().createQuery("select e from ExerciceFonction e where e.utilisateur.login=:login and e.defaultexe=true and e.fonctionExpiree=false");
        q.setParameter("login", utilisateur.getLogin());
      List<ExerciceFonction> exercice = q.getResultList();
        if (exercice.isEmpty()) {
            return null;
        } else {
            return exercice.get(0);
        }

    }

    /**
     * trouve les fonctions non expirées d'un utilisateur
     *
     * @param utilisateur
     * @return ExerciceFonction
     */
    public List<ExerciceFonction> findAllExercicefonction(Utilisateur utilisateur) {
        Query q = getEntityManager().createQuery("select e from ExerciceFonction e where e.utilisateur.login=:login and e.fonctionExpiree=false");
        q.setParameter("login", utilisateur.getLogin());
         return q.getResultList();
    }

    /**
     * trouve les fonctions non expirées d'un utilisateur
     *
     * @param utilisateur
     * @return ExerciceFonction
     */
    public List<ExerciceFonction> findCurrentExercicefonction(Utilisateur utilisateur) {
        Query q = getEntityManager().createQuery("select e from ExerciceFonction e where e.utilisateur.login=:login  and e.fonctionExpiree=false");
        q.setParameter("login", utilisateur.getLogin());
        return q.getResultList();
    }
}
