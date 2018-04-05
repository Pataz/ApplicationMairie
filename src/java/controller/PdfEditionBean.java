package controller;

import ejb.AgentFacade;
import ejb.PlanningCongeFacade;
import ejb.VariableSalaireFacade;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JREmptyDataSource;
import org.primefaces.model.StreamedContent;
import jpa.Agent;
import jpa.Conge;
import jpa.Direction;
import jpa.DetailSalaire;
import jpa.PlanningConge;
import jpa.ProgressionPoste;
import jpa.Salaire;
import jpa.Service;
import jpa.TypeVariable;
import jpa.VariableSalaire;
import util.GeneriqueClasse;
import util.JsfUtil;
import util.Reporter;

/**
 *
 * @author Espérance
 */
@Named(value = "pdfEditionBean")
@ViewScoped
public class PdfEditionBean implements Serializable {

    @Inject
    private MethodeBean methodeBean;
    @Inject
    private ContextBean contextBean;
    @Inject
    private AgentFacade agentFacade;
    @EJB
    private PlanningCongeFacade planningCongeFacade;

   
    private List<GeneriqueClasse> listeGeneriqueClasses;
    private long montantAvantage, cpt, montantApayerD, montantApayerE, montantCasScial, montantApayerG, montantTVerse, montantVerseD, montantRestant, montantSolde;
    String ouiNon;

    private Date date;
    LocalDateTime datedujour = LocalDate.now().atStartOfDay();
    private StreamedContent xls, pdf, word;

    /**
     * Creates a new instance of JuridictionBean
     */
    public PdfEditionBean() {
    }

    public void init() {
//        listeMontantFixes = montantFixeFacade.findAll();
//        listeFiliereOptions = filiereFacade.getFiliereOption();
////        listeInscriptions = new ArrayList<>();
//        listAnneeAcademiques = anneeAcademiqueFacade.getListeAnneeAcademique();
        
    }

    public StreamedContent getPdf() {
        return pdf;
    }

    public void setPdf(StreamedContent pdf) {
        this.pdf = pdf;
    }

    public StreamedContent getXls() {
        return xls;
    }

    public void setXls(StreamedContent xls) {
        this.xls = xls;
    }

    public StreamedContent getWord() {
        return word;
    }

    public void setWord(StreamedContent word) {
        this.word = word;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getMontantAvantage() {
        return montantAvantage;
    }

    public void setMontantAvantage(long montantAvantage) {
        this.montantAvantage = montantAvantage;
    }

    public long getMontantApayerD() {
        return montantApayerD;
    }

    public void setMontantApayerD(long montantApayerD) {
        this.montantApayerD = montantApayerD;
    }

    public long getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(long montantRestant) {
        this.montantRestant = montantRestant;
    }

    public long getMontantSolde() {
        return montantSolde;
    }

    public void setMontantSolde(long montantSolde) {
        this.montantSolde = montantSolde;
    }

    public String getOuiNon() {
        return ouiNon;
    }

    public void setOuiNon(String ouiNon) {
        this.ouiNon = ouiNon;
    }

    public long getMontantApayerE() {
        return montantApayerE;
    }

    public PlanningConge getPlanningConge(Agent agent) {
        return planningCongeFacade.findDefaultPlanningByAgent(agent, contextBean.getCurrentYear());
    }

    public PlanningConge getFinalPlanningConge(Agent agent) {
        return planningCongeFacade.findFinalPlanningByAgent(agent, contextBean.getCurrentYear());
    }

    public ProgressionPoste getProgressionPoste(Agent agent) {
        return agentFacade.findProgressionPosteById(agent.getLastProgressionPoste());
    }

    /**
     * ***
     *
     * @param listeAgent
     * @param direction
     * @param service
     * @return
     * @throws JRException
     * @throws IOException
     */
    public String planningDirectionReport(List<Agent> listeAgent, Direction direction, Service service) throws JRException, IOException {
        cpt = 0;
        HashMap hm = new HashMap();
        listeGeneriqueClasses = new ArrayList<>();
//        listeInscriptions = inscriptionFacade.getInscriptionsByClasseAndYear(an, classe);
        for (Agent agent : listeAgent) {
            cpt++;
            GeneriqueClasse pdfclasse = new GeneriqueClasse();
            pdfclasse.setCpt(cpt);
            pdfclasse.setMatricule(agent.getMatricule());
            pdfclasse.setNomPrenom(agent.getPersonne().getName());
            pdfclasse.setPoste(getProgressionPoste(agent).getPoste().getLibelle());
            if (getPlanningConge(agent) != null) {
                pdfclasse.setDate1(JsfUtil.convertDate(getPlanningConge(agent).getDateDebut(), "dd MMMM yyyy"));
                pdfclasse.setDate2(JsfUtil.convertDate(getPlanningConge(agent).getDateFin(), "dd MMMM yyyy"));
            } else {
                pdfclasse.setDate1("Non définie");
                pdfclasse.setDate2("Non définie");
            }
            pdfclasse.setObservation("");
            listeGeneriqueClasses.add(pdfclasse);
        }
        hm.put("data1", listeGeneriqueClasses);
        hm.put("ANNEE", contextBean.getCurrentYear().getLibelle());
        hm.put("DIRECTION1", direction.getLibelle());
        if (service != null) {
            hm.put("SERVICE", service.getLibelle());
        }
        hm.put("TITRE", "FICHE DU PLANNING DES CONGES D'UNE DIRECTION");

        hm.put("MINISTERE", "MINISTERE DE L'ECONOMIE NUMERIQUE ET DE LA COMMUNICATION");
        hm.put("DIRECTION", "DIRECTION DE L'ADMINISTRATION ET DES FINANCES ");
        String pathOutput = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/pdfModeles/");
        File file = new File(pathOutput);
        JasperPrint jasperPrint = null;
        if (service != null) {
            jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "planningService.jasper")), hm, new JREmptyDataSource());
        }
        if (service == null) {
            jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "planningDirection.jasper")), hm, new JREmptyDataSource());
        }
        return Reporter.exportReportToPdf(jasperPrint);
    }

    public String planningFinalDirectionReport(List<Agent> listeAgent, Direction direction, Service service) throws JRException, IOException {
        cpt = 0;
        HashMap hm = new HashMap();
        System.out.println("listeAgent ==>" + listeAgent);
        System.out.println("direction ==>" + direction);
        System.out.println("service ==>" + service);
        listeGeneriqueClasses = new ArrayList<>();
        for (Agent agent : listeAgent) {
            cpt++;
            GeneriqueClasse pdfclasse = new GeneriqueClasse();
            pdfclasse.setCpt(cpt);
            pdfclasse.setMatricule(agent.getMatricule());
            pdfclasse.setNomPrenom(agent.getPersonne().getName());
            pdfclasse.setPoste(getProgressionPoste(agent).getPoste().getLibelle());
            if (getFinalPlanningConge(agent) != null) {
                pdfclasse.setDate1(JsfUtil.convertDate(getPlanningConge(agent).getDateDebut(), "dd MMMM yyyy") + "\n    au \n" + JsfUtil.convertDate(getPlanningConge(agent).getDateFin(), "dd MMMM yyyy"));
                pdfclasse.setDate2(JsfUtil.convertDate(getFinalPlanningConge(agent).getDateDebut(), "dd MMMM yyyy") + "\n    au \n" + JsfUtil.convertDate(getFinalPlanningConge(agent).getDateFin(), "dd MMMM yyyy"));
            } else {
                if (getPlanningConge(agent) != null) {
                    pdfclasse.setDate1(JsfUtil.convertDate(getPlanningConge(agent).getDateDebut(), "dd MMMM yyyy") + "\n          au \n" + JsfUtil.convertDate(getPlanningConge(agent).getDateFin(), "dd MMMM yyyy"));
                    pdfclasse.setDate2("Non définie");
                } else {
                    pdfclasse.setDate1("Non définie");
                    pdfclasse.setDate2("Non définie");
                }

            }
            pdfclasse.setObservation("");
            listeGeneriqueClasses.add(pdfclasse);
        }

        System.out.println("11111 ==>");

        hm.put("data1", listeGeneriqueClasses);
        hm.put("ANNEE", contextBean.getCurrentYear().getLibelle());
        hm.put("DIRECTION1", direction.getLibelle());
        if (service != null) {
            hm.put("SERVICE", service.getLibelle());
        }
        hm.put("TITRE", "FICHE DU PLANNING RETENU DES CONGES D'UNE DIRECTION");

        hm.put("MINISTERE", "MINISTERE DE L'ECONOMIE NUMERIQUE ET DE LA COMMUNICATION");
        hm.put("DIRECTION", "DIRECTION DE L'ADMINISTRATION ET DES FINANCES ");

        System.out.println("222222 ==>");

        String pathOutput = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/pdfModeles/");
        File file = new File(pathOutput);
        System.out.println("3333 ==>");
        JasperPrint jasperPrint = null;
        if (service != null) {
            jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "planningFinalService.jasper")), hm, new JREmptyDataSource());
        }
        if (service == null) {
            jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "planningFinalDirection.jasper")), hm, new JREmptyDataSource());
        }
        return Reporter.exportReportToPdf(jasperPrint);
    }

    public String listeAgentDirectionReport(List<Agent> listeAgent, Direction direction) throws JRException, IOException {
        cpt = 0;
        HashMap hm = new HashMap();
        listeGeneriqueClasses = new ArrayList<>();
//        listeInscriptions = inscriptionFacade.getInscriptionsByClasseAndYear(an, classe);
        for (Agent agent : listeAgent) {
            cpt++;
            GeneriqueClasse pdfclasse = new GeneriqueClasse();
            pdfclasse.setCpt(cpt);
            pdfclasse.setMatricule(agent.getMatricule());
            pdfclasse.setNomPrenom(agent.getPersonne().getName());
            pdfclasse.setPoste(getProgressionPoste(agent).getPoste().getLibelle());
            pdfclasse.setDate1(JsfUtil.convertDate(agent.getDebutFonction(), "dd MMM yyyy"));
            pdfclasse.setNature(getProgressionPoste(agent).getNatureAgent().name());
            pdfclasse.setCategorie(getProgressionPoste(agent).getCategorie().getSousCategorie().getLibelle() + "-" + getProgressionPoste(agent).getCategorie().getEchelle());
            pdfclasse.setObservation("");
            listeGeneriqueClasses.add(pdfclasse);
        }
        hm.put("data1", listeGeneriqueClasses);
        hm.put("ANNEE", contextBean.getCurrentYear().getLibelle());
        hm.put("DIRECTION1", direction.getLibelle());

        hm.put("TITRE", "FICHE DE PRESENCE DES AGENTS D'UNE DIRECTION");

        hm.put("MINISTERE", "MINISTERE DE L'ECONOMIE NUMERIQUE ET DE LA COMMUNICATION");
        hm.put("DIRECTION", "DIRECTION DE L'ADMINISTRATION ET DES FINANCES ");
        String pathOutput = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/pdfModeles/");
        File file = new File(pathOutput);
        JasperPrint jasperPrint = null;

        jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "agentDirection.jasper")), hm, new JREmptyDataSource());

        return Reporter.exportReportToPdf(jasperPrint);
    }

    public String fichePaieReport(Agent agent, Direction direction, Service service,List<VariableSalaire> listeVariablePrimes) throws JRException, IOException {
        cpt = 0;
        HashMap hm = new HashMap();
        listeGeneriqueClasses = new ArrayList<>();
//        if(salaire != null) {
        for (VariableSalaire variable : listeVariablePrimes) {
            cpt++;
            GeneriqueClasse pdfclasse = new GeneriqueClasse();
            pdfclasse.setCpt(cpt);
            pdfclasse.setVariable(variable.getLibelle());
            pdfclasse.setNbre("-");
            pdfclasse.setBase("-");
            pdfclasse.setTaux("-");
            pdfclasse.setMontant("-");
            pdfclasse.setTaux1("-");
            pdfclasse.setMontant1("-");
            listeGeneriqueClasses.add(pdfclasse);
        }
//        }
        hm.put("data1", listeGeneriqueClasses);
        hm.put("ANNEE", contextBean.getCurrentYear().getLibelle());
        hm.put("MOIS", "");
        hm.put("DIRECTION1", getProgressionPoste(agent).getDirection().getLibelle());
        if (service != null) {
            hm.put("SERVICE", getProgressionPoste(agent).getService().getLibelle());
        }
        hm.put("TITRE", "FICHE DE PAIE");
        hm.put("MINISTERE", "RAISON SOCIALE DE L'ETREPRISE");
        hm.put("DIRECTION", "ADRESSE ");
        hm.put("NAMEAGENT",  agent.getPersonne().getCivilite()+" "+agent.getPersonne().getName());
        hm.put("POSTEAGENT", getProgressionPoste(agent).getPoste().getLibelle());
        hm.put("CATEGORIE", getProgressionPoste(agent).getCategorie().getLibelle());
        
        String pathOutput = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/pdfModeles/");
        File file = new File(pathOutput);
        JasperPrint jasperPrint = null;
//        if (service != null) {
        jasperPrint = JasperFillManager.fillReport(new FileInputStream(new File(file, "fichePaie.jasper")), hm, new JREmptyDataSource());
//        }
        return Reporter.exportReportToPdf(jasperPrint);
    }
}
