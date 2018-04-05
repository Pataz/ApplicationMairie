/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Gildasdarex
 */
public class SqlQuery {

    /**
     * Renvoie tous les années de création des dossiers.
     *
     * @author Espérance
     */
    public static String findAllPVAnnees = "SELECT DISTINCT D.* FROM DOCUMENT D INNER JOIN PROGRESSIONDOSSIER P ON D.progressiondossier_id=P.id  WHERE D.typedocument_id=?1 AND P.annee=?2";

    /**
     * Renvoie tous les années de création des dossiers.
     *
     * @author Espérance
     */
    public static String findAllAnneesForDossier = "SELECT DISTINCT A.* FROM ANNEE A INNER JOIN DOSSIER D ON A.code=D.annee_code ORDER BY A.valeur DESC";

    /**
     * @author Espérance
     * @return La liste des dossiers délibérés
     */
    public static String getListePlainteJour() {
        return "SELECT DISTINCT p.* FROM Plainte p INNER JOIN OPJ o ON p.opj_code=o.code "
                + "WHERE p.dateSaisine=?1 AND p.opj_code=?2 ORDER BY p.numeroPL";
    }

    /**
     * @author Justo
     * @return La liste des Plaintes Courantes
     */
    public static String getListePlainteCourantePeriode() {
        return "SELECT DISTINCT p.* FROM Plainte p WHERE p.dateSaisine between ?1 and ?2  ORDER BY p.numeroPL";
    }

    /**
     * @author patrick
     * @return La liste des progressions partie concernant les garde à vue dans
     * une OPJ
     *
     */
    public static String getListProgressionPartiePlainteSQL() {
        return "SELECT p1.*"
                + "FROM ProgressionPartie p1 "
                + "INNER JOIN progressionplainte pp on p1.progressionplainte_id = pp.id "
                + "where pp.statut_code = ?1 "
                + "AND pp.evenement_code = ?2 "
                + "AND  p1.dateCreation BETWEEN ?3 AND ?4 "
                + "ORDER BY p1.dateCreation desc";
    }

    /**
     * Justo
     *
     *
     * @return Liste des progressions liées à cette plainte
     */
    public static String getListeProgressionsPlainteSQL() {
        return "SELECT p1.* "
                + "FROM ProgressionPlainte p1 "
                + "INNER JOIN PLAINTE D ON p1.PLAINTE_ID = D.ID "
                + "WHERE D.numeroPL = ?1 "
                + "AND opj_code = ?2 "
                + "ORDER BY p1.dateCreation DESC";
    }

    public static String getNbrPlainteInOJPPeriode() {
        return "SELECT p1.* FROM ProgressionPlainte p1 INNER JOIN "
                + "(SELECT plainte_id, MAX(dateCreation) lastDateCreation FROM ProgressionPlainte p2 where p2.statut_code= 'NEW' GROUP BY plainte_id) "
                + "p2 ON p1.plainte_id = p2.plainte_id AND p1.dateCreation = p2.lastDateCreation INNER JOIN Plainte d "
                + "ON p1.plainte_id = d.id INNER JOIN opj o on o.code = d.opj_code WHERE  p1.statut_code= 'NEW' "
                + "and d.datesaisine between ?1 and ?2  and d.opj_code=?3 ORDER BY p1.id DESC";
    }

    /**
     * Espérance
     *
     * @return Liste des plaintes nouvellement enregistrées
     */
    public static String getNewPlainteInOJPPeriode() {
        return "SELECT p1.* FROM ProgressionPlainte p1 INNER JOIN "
                + "(SELECT plainte_id, MAX(dateCreation) lastDateCreation FROM ProgressionPlainte GROUP BY plainte_id) "
                + "p2 ON p1.plainte_id = p2.plainte_id AND p1.dateCreation = p2.lastDateCreation INNER JOIN Plainte d "
                + "ON p1.plainte_id = d.id INNER JOIN opj o on o.code = d.opj_code WHERE p1.statut_code= 'NEW' "
                + "and d.datesaisine between ?1 and ?2  and d.opj_code=?3";
    }

    /**
     * Espérance
     *
     * @return Liste des plaintes classées
     */
    public static String getPlainteClasseInOJPPeriode() {
        return "SELECT p1.* FROM ProgressionPlainte p1 INNER JOIN "
                + "(SELECT plainte_id, MAX(dateCreation) lastDateCreation FROM ProgressionPlainte GROUP BY plainte_id) "
                + " p2 ON p1.plainte_id = p2.plainte_id AND p1.dateCreation = p2.lastDateCreation INNER JOIN Plainte d "
                + "ON p1.plainte_id = d.id INNER JOIN opj o on o.code = d.opj_code WHERE p1.statut_code= 'CLA' and p1.dateEvenement between ?1 and ?2  and d.opj_code=?3 ORDER BY d.numeropl";
    }

    /**
     * * Justo
     *
     * @return liste de Plaintes en cours dans opj
     *
     *
     */
    public static String getProgressionsPlaintesCourantsOPJSQL() {
        return "SELECT p1.* FROM "
                + "progressionPlainte p1 "
                + "INNER JOIN plainte D ON p1.plainte_id = D.id "
                + "WHERE p1.datecreation >= ALL "
                + "("
                + "SELECT p2.datecreation "
                + "FROM progressionPlainte "
                + "p2 WHERE p2.plainte_id = p1.plainte_id "
                + ") "
                + "AND D.opj_code = ?1  "
                + "AND p1.statut_code IN ('NEW' ,'ENC') "
                + "AND D.datesaisine between ?2 and ?3 "
                + " ORDER BY D.numeroPL";
    }

    /**
     * * Justo
     *
     * @return liste de Plaintes en cours dans opj
     *
     *
     */
    public static String getPlaintesCourantsOPJSQL() {
        return "SELECT D.* FROM "
                + "progressionPlainte p1 "
                + "INNER JOIN plainte D ON p1.plainte_id = D.id "
                + "WHERE p1.datecreation >= ALL "
                + "("
                + "SELECT p2.datecreation "
                + "FROM progressionPlainte "
                + "p2 WHERE p2.plainte_id = p1.plainte_id"
                + ") "
                + "AND D.opj_code = ?1 AND p1.utilisateurDestinataire_login= ?2 "
                + "AND p1.statut_code IN ('NEW' ,'ENC')"
                + "ORDER BY D.datesaisine";
    }

    public static String getPlaintesClasseesOPJSQL() {
        return "SELECT D.* FROM "
                + "progressionPlainte p1 "
                + "INNER JOIN plainte D ON p1.plainte_id = D.id "
                + "WHERE p1.datecreation >= ALL "
                + " ( "
                + "SELECT p2.datecreation "
                + "FROM progressionPlainte "
                + "p2 WHERE p2.plainte_id = p1.plainte_id"
                + " ) "
                + "AND D.opj_code = ?1  "
                + "AND p1.statut_code IN ('CLA')"
                + " D.dateSaisine between ?2 and ?3 "
                + "ORDER BY D.datesaisine";
    }

    /**
     * Espérance
     *
     * @return Liste des utilisateus dans un etabliblissement
     */
    public static String getUtilisateursByEtabliblissement() {
        return "select  DISTINCT u.*\n"
                + "from  exerciceFonction ex\n"
                + "inner join utilisateur u on u.login = ex.utilisateur_login \n"
              + "and ex.etablissement_id=?1 and ex.fonctionexpiree=false";
    }

    /**
     * Espérance
     *
     * @return Liste des plaintes affectées à un utilisateur
     */
    public static String getListePlaintesAffecteesSQL() {
         return "select  DISTINCT e.*\n"
                + "from  AttributionClasse a\n"
                + "inner join Enseignant e on e.id = a.enseignant_id \n"
              + "and ex.etablissement_id=?1 and ex.fonctionexpiree=false";
    }

    /**
     * Espérance
     *
     * @return Liste des plaintes affectées à un utilisateur
     */
    public static String getListePlaintesAffecteesPeriodeSQL() {
        return "SELECT DISTINCT p1.* FROM ProgressionPlainte p1 "
                + "INNER JOIN plainte D ON p1.plainte_id = D.ID "
                + "WHERE p1.dateCreation >= ALL "
                + "("
                + "SELECT p2.dateCreation "
                + "FROM ProgressionPlainte p2 "
                + "WHERE p2.plainte_id = p1.plainte_id"
                + ")"
                + " AND p1.statut_code !='CLA' "
                + "AND p1.evenement_code = ?1 "
                + "AND D.opj_code = ?2 "
                + "AND p1.utilisateurDestinataire_login = ?3  "
                + "AND p1.dateEvenement between ?4 and ?5 "
                + "order by p1.dateCreation DESC";
    }

    /**
     * Espérance
     *
     * @return Liste des plaintes affectées à un utilisateur
     */
    public static String getListePlaintesAffecteesCommissaireSQL() {
        return "SELECT DISTINCT p1.* FROM ProgressionPlainte p1 "
                + "INNER JOIN plainte D ON p1.plainte_id = D.ID "
                + "WHERE p1.dateCreation >= ALL "
                + "("
                + "SELECT p2.dateCreation "
                + "FROM ProgressionPlainte p2 "
                + "WHERE p2.plainte_id = p1.plainte_id"
                + ")"
                + "AND p1.evenement_code IN('ENR_001','RET_001')"
                + "AND D.opj_code = ?1 "
                + "AND p1.utilisateurDestinataire_login = ?2 "
                + "order by p1.dateCreation DESC";
    }

    public static String getListEvenementbyPlainte() {
        return "SELECT DISTINCT t0.* FROM PROGRESSIONPLAINTE t0 \n"
                + "INNER JOIN PLAINTE d ON t0.PLAINTE_ID = d.ID \n"
                + "INNER JOIN EVENEMENT e ON t0.EVENEMENT_CODE=e.code \n"
                + "WHERE d.ID=?1 \n"
                + "AND e.code IS NOT NULL  ORDER BY t0.DATECREATION ASC";

    }

    public static String getListPVbyPlainte() {
        return "SELECT DISTINCT pv.* FROM PV pv \n"
                + "INNER JOIN PROGRESSIONPLAINTE pr ON pv.PROGRESSIONPLAINTE_ID = pr.ID \n"
                + "INNER JOIN PLAINTE pl ON pr.PLAINTE_ID=pl.ID \n"
                + "WHERE pl.ID=?1 \n"
                + " ORDER BY pv.DATECREATION ASC";
    }

    public static String getListPVbyJour() {
        return "SELECT DISTINCT pv.* FROM PV pv \n"
                + "INNER JOIN PROGRESSIONPLAINTE pr ON pv.PROGRESSIONPLAINTE_ID = pr.ID \n"
                + "INNER JOIN PLAINTE pl ON pr.PLAINTE_ID=pl.ID INNER JOIN OPJ o ON o.code=pl.opj_code \n"
                + "WHERE pr.DATEEVENEMENT=?1 AND pl.opj_code=?2 \n"
                + " ORDER BY pv.DATECREATION ASC";

    }

    public static String getAdminstrationEtablissement() {
        return "SELECT DISTINCT a.* FROM ADMINISTRATIONETABLISSEMENT a \n"
                + "INNER JOIN ETABLISSEMENT e ON e.ID = a.ETABLISSEMENT_ID \n"
                + "WHERE a.ETABLISSEMENT_ID=?1 \n";

    }

    public static String getListDepartementbyPays() {
        return "SELECT D.* FROM DEPARTEMENT D INNER JOIN PAYS P ON D.pays_code=P.code WHERE D.pays_code=?1";
    }

    public static String getListCommuneByDepartement() {
        return "SELECT C.* FROM COMMUNE C INNER JOIN DEPARTEMENT D ON C.departement_code=D.code WHERE C.departement_code=?1";
    }

    public static String getListArrondissementByCommune() {
        return "SELECT A.* FROM ARRONDISSEMENT A INNER JOIN COMMUNE C ON A.commune_code=C.code WHERE A.commune_code=?1";
    }

    public static String getListQuartierByArrondissement() {
        return "SELECT Q.* FROM QUARTIER Q INNER JOIN ARRONDISSEMENT A ON Q.arrondissement_code=A.code WHERE Q.arrondissement_code=?1";
    }

    public static String getListQuartierByCommune() {
        return "SELECT Q.* FROM QUARTIER Q INNER JOIN ARRONDISSEMENT A ON Q.arrondissement_code=A.code INNER JOIN COMMUNE C ON A.commune_code=C.code WHERE A.commune_code=?1";
    }

    public static String getLastEquipeDirectionByFonction() {
        return "SELECT DISTINCT e.* FROM EquipeDirection e \n"
                + "INNER JOIN Fonction f ON e.fonction_code = f.code \n"
                + "WHERE e.fonction_code=?1 order by e.id desc limit 1 \n";
    }

    public static String getInscriptionsByClasseAndYear() {
        return "SELECT DISTINCT I.* FROM Inscription I \n"
                + "WHERE I.anneescolaire_code=?1 AND I.classe_id=?2 \n";
    }
     /**
     * Renvoie tous les enseignants dans un etablissement par année scolaire.
     *
     * @author Espérance
     * @return 
     */
     public static String getEnseignantsByYear() {
        return "SELECT DISTINCT P.* FROM Enseignant P \n"
                + "INNER JOIN ClasseEnseigner C ON P.id = C.enseignant_id \n"
                + "INNER JOIN ClasseDisponible Cd ON Cd.id = C.classe_id \n"
                + "INNER JOIN Etablissement E ON E.id = Cd.etablissement_id \n"
                + "INNER JOIN AnneeScolaire A ON C.anneescolaire_code = A.code \n"
                + "WHERE C.anneescolaire_code=?1 AND Cd.etablissement_id=?2 \n";
    }
       /**
     * Renvoie tous les enseignants dans un etablissement par année scolaire et par classe.
     *
     * @author Espérance
     * @return 
     */
     public static String getEnseignantsByYearAndClasse() {
        return "SELECT DISTINCT P.* FROM Enseignant P \n"
                + "INNER JOIN ClasseEnseigner C ON P.id = C.enseignant_id \n"
                + "INNER JOIN ClasseDisponible Cd ON Cd.id = C.classe_id \n"
                + "INNER JOIN Etablissement E ON E.id = Cd.etablissement_id \n"
                + "INNER JOIN AnneeScolaire A ON C.anneescolaire_code = A.code \n"
                + "WHERE C.anneescolaire_code=?1 AND Cd.etablissement_id=?2 and C.classe_id=?3\n";
    }
     /**
     * Renvoie toutes les classes d'un enseignant dans un etablissement par année scolaire.
     *
     * @author Espérance
     * @return 
     */
     public static String getClasseEnseignantsByYear() {
        return "SELECT DISTINCT C.* FROM Enseignant P \n"
                + "INNER JOIN ClasseEnseigner C ON P.id = C.enseignant_id \n"
                + "INNER JOIN ClasseDisponible Cd ON Cd.id = C.classe_id \n"
                + "INNER JOIN Etablissement E ON E.id = Cd.etablissement_id \n"
                + "INNER JOIN AnneeScolaire A ON C.anneescolaire_code = A.code \n"
                + "WHERE C.anneescolaire_code=?1 AND Cd.etablissement_id=?2 and C.enseignant_id=?3 \n";
    }
     
}
