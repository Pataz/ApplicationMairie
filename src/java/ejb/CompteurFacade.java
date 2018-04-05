/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.Compteur;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author utilisateur
 */
//@Stateless
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CompteurFacade {

//    @EJB
//    private PlainteFacade plainteFacade;
    @PersistenceContext(unitName = "grhPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteurFacade() {
    }

    //@Lock(LockType.WRITE)
    public String getNext(String t, String prefix) {

        String id;
        String table = t + "-" + prefix;
        Compteur c = find(table);
        if (c == null) {
            id = prefix + Calendar.getInstance().get(Calendar.YEAR) + "000001";
            System.out.println("id dans getnext==>" + id);
            create(new Compteur(table, id));
            return id;
        } else {
            String annee = c.getValeur().substring(prefix.length(), prefix.length() + 4);
            String currannee = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
            if (!annee.equals(currannee)) {
                id = prefix + currannee + "000001";
            } else {
                int val = Integer.valueOf(c.getValeur().substring(prefix.length() + 4)) + 1;
                DecimalFormat f = new DecimalFormat("000000");
                id = prefix + currannee + f.format(val);
            }
            c.setValeur(id);
            edit(c);
            System.out.println(id);
            return id;
        }
    }

    public Compteur find(Object id) {
        return getEntityManager().find(Compteur.class, id);
    }

    public void create(Compteur entity) {
        getEntityManager().persist(entity);
    }

    public void edit(Compteur entity) {
        getEntityManager().merge(entity);
    }

    public void remove(Compteur entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public String generatePL(String table, String cleOPJ, Date dateSaisine) {
        String numeroPL;
        String year = new SimpleDateFormat("yyyy").format(dateSaisine);
        Compteur c = find(table + "-" + year);
        if (c == null) {
//            if (repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()) != null) {
//                DecimalFormat f = new DecimalFormat("000000");
//                long val = repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()).getNumero() + 1;
//                numeroRG = f.format(val) + "/" + year + "/" + codeRG;
//                create(new Compteur(table + "-" + year, numeroRG));
//            } else {
            numeroPL = "000001/" + year + "/" + cleOPJ;
            create(new Compteur(table + "-" + year, numeroPL));
//            }
            return numeroPL;
        } else {
            int val = Integer.valueOf(c.getValeur().substring(0, 6)) + 1;
            DecimalFormat f = new DecimalFormat("000000");
            numeroPL = f.format(val) + "/" + year + "/" + cleOPJ;
            c.setValeur(numeroPL);
            edit(c);
            return numeroPL;
        }
    }
 public String generateNum(String table, String cleOPJ, Date dateSaisine) {
        String numeroPL;
        String year = new SimpleDateFormat("yyyy").format(dateSaisine);
        Compteur c = find(table + "-" + year);
        if (c == null) {
//            if (repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()) != null) {
//                DecimalFormat f = new DecimalFormat("000000");
//                long val = repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()).getNumero() + 1;
//                numeroRG = f.format(val) + "/" + year + "/" + codeRG;
//                create(new Compteur(table + "-" + year, numeroRG));
//            } else {
            numeroPL = "000001/" + year + "/" + cleOPJ;
            create(new Compteur(table + "-" + year, numeroPL));
//            }
            return numeroPL;
        } else {
            int val = Integer.valueOf(c.getValeur().substring(0, 6)) + 1;
            DecimalFormat f = new DecimalFormat("000000");
            numeroPL = f.format(val) + "/" + year + "/" + cleOPJ;
            c.setValeur(numeroPL);
            edit(c);
            return numeroPL;
        }
    }
//    public String generatePV(Plainte plainte, String cleOPJ) {
//        String numeroPL;
//        String year = new SimpleDateFormat("yyyy").format(plainte.getDateSaisine());
//        if (plainte.getCompteurPV() == null) {
//            numeroPL = "000001/" + year + "/" + cleOPJ;
//            plainte.setCompteurPV(numeroPL);
//            plainteFacade.edit(plainte);
//            return numeroPL;
//        } else {
//            int val = Integer.valueOf(plainte.getCompteurPV().substring(0, 6)) + 1;
//            DecimalFormat f = new DecimalFormat("000000");
//            numeroPL = f.format(val) + "/" + year + "/" + cleOPJ;
//            plainte.setCompteurPV(numeroPL);
//            plainteFacade.edit(plainte);
//            return numeroPL;
//        }
//    }
//
//    public String generateNumDec(String table, String codeRG, Date dateAudience) {
//        String numeroDec;
//        String year = new SimpleDateFormat("yyyy").format(dateAudience);
//        Compteur c = find(table + "-" + year + "-" + codeRG);
//        if (c == null) {
//            System.out.println(" c est null ---->  ......................"+c);
//            if (repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()) != null) {
//                DecimalFormat f = new DecimalFormat("00000");
//                long val = repriseNumeroFacade.getRepriseNumeroByServiceAndAnnee(contextBean.getCurrentService(), JsfUtil.getCurrentYear()).getNumero() + 1;
//                numeroDec = table + "/" + year + "/" + f.format(val);
//                create(new Compteur(table + "-" + year + "-" + codeRG, numeroDec));
//            } else {
//                System.out.println(" c est null ---->  ......................"+c);
//                numeroDec = table + "/" + year + "/00001";
//                create(new Compteur(table + "-" + year + "-" + codeRG, numeroDec));
//            }
//            return numeroDec;
//        } else {
//            System.out.println("c ---===>> ..........." + c.getValeur());
//            int val = Integer.valueOf(c.getValeur().substring(10, 15)) + 1;
//            System.out.println("val ---===>> ............" + val);
//            DecimalFormat f = new DecimalFormat("00000");
//            numeroDec = table + "/" + year + "/" + f.format(val);
//            c.setValeur(numeroDec);
//            edit(c);
//            return numeroDec;
//        }
//    }

    public String generateNumApp(String table, String codeRG, Date dateAudience) {
        String numeroDec;
        String year = new SimpleDateFormat("yyyy").format(dateAudience);
        Compteur c = find(table + "-" + year + "-" + codeRG);
        if (c == null) {
            numeroDec = "0001/" + year + "/" + codeRG;
            System.out.println("Num dec ........................................" + numeroDec);
            create(new Compteur(table + "-" + year + "-" + codeRG, numeroDec));
            return numeroDec;
        } else {
            int val = Integer.valueOf(c.getValeur().substring(1, 4)) + 1;
            System.out.println("Voici la valeur d val ------------------------------------> ..." + val);
            DecimalFormat f = new DecimalFormat("0000");
            numeroDec = f.format(val) + "/" + year + "/" + codeRG;
            c.setValeur(numeroDec);
            edit(c);
            return numeroDec;
        }
    }

    public String generateNumOPJ(String table, String codeRG, Date dateAudience) {
        String numeroDec;
        String year = new SimpleDateFormat("yyyy").format(dateAudience);
        Compteur c = find(table + "-" + year + "-" + codeRG);
        if (c == null) {
            numeroDec = "00001/" + year + "/" + codeRG;
            System.out.println("Num dec ........................................" + numeroDec);
            create(new Compteur(table + "-" + year + "-" + codeRG, numeroDec));
            return numeroDec;
        } else {
            int val = Integer.valueOf(c.getValeur().substring(1, 4)) + 1;
            System.out.println("Voici la valeur d val ------------------------------------> ..." + val);
            DecimalFormat f = new DecimalFormat("0000");
            numeroDec = f.format(val) + "/" + year + "/" + codeRG;
            c.setValeur(numeroDec);
            edit(c);
            return numeroDec;
        }
    }
 @Lock(LockType.WRITE)
    public String getGenerateMatricule(String t, String prefix) {
        
        String id;
        String table = t+"-"+prefix;
        System.out.println("ds compteur=>"+table);
        Compteur c = find(table);
        if (c == null) {
            id = "00001";
            create(new Compteur(table, id));
            return id;
        } else {
          int val = Integer.valueOf(c.getValeur()) + 1;
              id=String.valueOf(val);
             c.setValeur(id);
            edit(c);
            System.out.println(id);
            return id;
        }
    }
     @Lock(LockType.WRITE)
    public String getGeneratePassword(String table) {
        
        String id;
       Compteur c = find(table);
        if (c == null) {
            id = "100020170001";
            create(new Compteur(table, id));
            return id;
        } else {
          long val = Long.valueOf(c.getValeur()) + 1;
              id=String.valueOf(val);
             c.setValeur(id);
            edit(c);
            return id;
        }
    }
     @Lock(LockType.WRITE)
    public String getGenerateLogin(String table) {
       DecimalFormat f = new DecimalFormat("00000");
        String id;
       Compteur c = find(table);
        if (c == null) {
            id = "1";
            
            create(new Compteur(table, id));
            return "identifiant"+f.format(1);
        } else {
          long val = Long.valueOf(c.getValeur()) + 1;
              id=String.valueOf(val);
             c.setValeur(id);
            edit(c);
            return "identifiant"+f.format(val);
        }
    }
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DecimalFormat f = new DecimalFormat("00000");
        String id="1";
        System.out.println("identifiant===>"+f.format(1));
      }
}
