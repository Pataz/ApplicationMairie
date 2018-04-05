/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.math.BigInteger;
import java.security.MessageDigest;
import jpa.ExerciceFonction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.ejb.EJB;
import jpa.Annee;
import jpa.Groupe;
import jpa.GroupeUtilisateur;
import org.apache.log4j.Logger;

/**
 *
 * @author utilisateur
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    Logger logger = Logger.getLogger(UtilisateurFacade.class);

    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;
    @EJB
    private ExerciceFonctionFacade exerciceFonctionFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    /**
     * trouve les exercices fonctions de l'utilisateur
     *
     *
     * @return liste des utilisateurs
     */
    public List<Utilisateur> getAllUsers() {
        Query q = getEntityManager().createQuery("select U from Utilisateur U order by U.nomPrenoms");
        // set parameters
        List<Utilisateur> suggestions = q.getResultList();

        // avoid returing null to managed beans
        if (suggestions == null) {
            suggestions = new ArrayList<>();
        }

        // return the suggestions
        return suggestions;

    }

    public String cryptPassword(String password) {
        String outPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes(), 0, password.length());
            outPassword = new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {

        }
        return outPassword;
    }

    public void createGroupeUtilisateur(GroupeUtilisateur groupe) {
        getEntityManager().persist(groupe);
    }

    public Groupe getGroupe(String grp) {
        Query q = getEntityManager().createQuery("select G from Groupe G WHERE G.cn=?1");
        q.setParameter(1, grp);
        List<Groupe> grpL = q.getResultList();
        if (grpL.isEmpty()) {
            return null;
        } else {
            return grpL.get(0);
        }
    }

//    public void createSystemUserToPersonne(Personne personne, Fonction fonction, Etablissement etablissement, String login, String password, CommonFacade commonFacade) {
//        Utilisateur newUtilisateur = new Utilisateur();
//        GroupeUtilisateur newGroupe = new GroupeUtilisateur();
//        ExerciceFonction newExerciceFonction = new ExerciceFonction();
//        newUtilisateur.setMdp(cryptPassword(password));
//        newUtilisateur.setLogin(login);
//        newUtilisateur.setPersonne(personne);
//        create(newUtilisateur);
//        newGroupe.setId(commonFacade.getId(newGroupe));
//        newGroupe.setGroupe(getGroupe("Common"));
//        newGroupe.setUtilisateur(newUtilisateur);
//        createGroupeUtilisateur(newGroupe);
//        newExerciceFonction.setId(commonFacade.getId(newExerciceFonction));
//        newExerciceFonction.setUtilisateur(newUtilisateur);
//        newExerciceFonction.setEtablissement(etablissement);
//        newExerciceFonction.setFonction(fonction);
//        newExerciceFonction.setDefaultexe(true);
//        exerciceFonctionFacade.create(newExerciceFonction);
//    }
}
