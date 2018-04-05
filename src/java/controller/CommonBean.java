/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CommonFacade;
import ejb.ProgressionAgentFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jpa.Absence;
import util.Docx;
import util.JsfUtil;
import util.barcode.BarCode;
import util.barcode.SimpleBarCodeGenerator;
import ejb.AgentFacade;
import fr.opensagres.xdocreport.core.XDocReportException;
import java.io.IOException;
import java.util.Date;
import javax.ejb.EJB;
import jpa.Agent;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author MJLDH
 */

@Named(value = "commonBean")
@ViewScoped
public class CommonBean implements Serializable {

    SimpleDateFormat sdf;
    SimpleDateFormat sdfhm;
    SimpleDateFormat sdfhmj;   
    SimpleDateFormat heure;   
    String pathModeles;
    String pathOutput;
    
    @EJB
    private AgentFacade agentFacade;
    
    @Inject
    private ContextBean contextBean;
    @Inject
    private ProgressionAgentFacade progressionAgentFacade;
    @Inject
    private CommonFacade commonFacade;
    /**
     * Creates a new instance of CommonBean
     */
    public CommonBean() {
    }

    @PostConstruct
    public void init() {
        sdf = new SimpleDateFormat("EEEEE dd MMM yyyy");
        sdfhm = new SimpleDateFormat("EEEEE dd MMM yyyy 'A' HH:mm:ss");
        sdfhmj = new SimpleDateFormat("EEEEE dd MMM yyyy HH:mm");
        heure = new SimpleDateFormat("HH:mm:ss");

        pathModeles = "/resources/modeles";
        pathOutput = "/resources/output";
    }

    public CommonFacade getCommonFacade() {
        return commonFacade;
    }

    public void setCommonFacade(CommonFacade commonFacade) {
        this.commonFacade = commonFacade;
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }

    public SimpleDateFormat getSdfhm() {
        return sdfhm;
    }

    public void setSdfhm(SimpleDateFormat sdfhm) {
        this.sdfhm = sdfhm;
    }

    public SimpleDateFormat getSdfhmj() {
        return sdfhmj;
    }

    public void setSdfhmj(SimpleDateFormat sdfhmj) {
        this.sdfhmj = sdfhmj;
    }

    public String getPathModeles() {
        return pathModeles;
    }

    public void setPathModeles(String pathModeles) {
        this.pathModeles = pathModeles;
    }

    public String getPathOutput() {
        return pathOutput;
    }

    public void setPathOutput(String pathOutput) {
        this.pathOutput = pathOutput;
    }

    public ProgressionAgentFacade getProgressionAgentFacade() {
        return progressionAgentFacade;
    }

    public void setProgressionAgentFacade(ProgressionAgentFacade progressionAgentFacade) {
        this.progressionAgentFacade = progressionAgentFacade;
    }

//    public void universalGenerateTrameFunctionPG(Object[] parameters, Object[] parameterValue, Agent selectedAgent) {
//        try {
//            HashMap param = new HashMap();
//            
//            try {
//                param.put("CIVILITEACT", allInfo.getPersonnePhysique().getCivilite().getLabel());
//            } catch (Exception e) {
//                param.put("CIVILITEACT", "-");
//            }
//
//            try {
//                param.put("DATELIEUINFRA", JsfUtil.convertDate(selectedPlainte.getDateForfait()) + " à " + selectedPlainte.getLieuForfait());
//            } catch (Exception e) {
//                param.put("DATELIEUINFRA", "-");
//            }
//
//            try {
//                if (selectedPlainte.getInfractionsPlaintes().size() > 1) {
//                    param.put("INFRADOSSIER", selectedPlainte.getInfractionsPlaintes().get(0).getInfraction().getLibelle() + " et consort");
//                }
//                if (selectedPlainte.getInfractionsPlaintes().size() == 1) {
//                    param.put("INFRADOSSIER", selectedPlainte.getInfractionsPlaintes().get(0).getInfraction().getLibelle());
//                }
//
//            } catch (NullPointerException e) {
//                param.put("INFRADOSSIER", "-");
//            }
//
//            try {
//
//                param.put("PROFESSIONAUT", allInfo.getPersonnePhysique().getProfession().getLibelle());
//
//            } catch (NullPointerException e) {
//                param.put("PROFESSIONAUT", "-");
//            }
//
//            if (parameters.length == parameterValue.length) {
//                for (int i = 0; i < parameters.length; i++) {
//                    try {
//                        param.put(parameters[i], parameterValue[i]);
//                    } catch (NullPointerException | ClassCastException e) {
//                        param.put(parameters[i], "-");
//                    }
//                }
//                Docx docx = new Docx(pathModeles, pathOutput);
//                docx.generate(trameName, param);
//            }
//        } catch (Exception e) {
//        }
//    }
    
    public void generateTrame(Absence absence) {
        System.out.println("==== Je suis ds le common");
        try {
            HashMap param = new HashMap();
            System.out.println("==== Je suis ds le common");
            try {
                param.put("NAME", absence.getProgressionAgent().getAgent().getPersonne().getName());
            } catch (Exception e) {
                param.put("NAME", "-");
            }
            try {
                param.put("DATEDEBUT",JsfUtil.convertDate(absence.getDateDebut(), "EEEE dd MMMM yyyy") );
            } catch (Exception e) {
                param.put("DATEDEBUT", "-");
            }
            try {
                param.put("DATEFIN",JsfUtil.convertDate(absence.getDateFin(), "EEEE dd MMMM yyyy"));
            } catch (Exception e) {
                param.put("DATEFIN", "-");
            }
                        
            try {
                param.put("RAISON", absence.getProgressionAgent().getEvenement().getLibelle());
            } catch (Exception e) {
                param.put("RAISON", "-");
            }
                        
            try {
                param.put("DATEFVT",JsfUtil.convertDate(absence.getProgressionAgent().getDateProgression(), "dd MM yyyy"));
            } catch (Exception e) {
                param.put("DATEFVT", "-");
            }
                        
            try {
                param.put("POSTE", agentFacade.findProgressionPosteById(absence.getProgressionAgent().getAgent().getLastProgressionPoste()));
            } catch (Exception e) {
                param.put("POSTE", "-");
            }
            
            SimpleBarCodeGenerator gen = new SimpleBarCodeGenerator("code39", "image/x-png", 150);
            BarCode b = gen.generateBarCode(absence.getProgressionAgent().getAgent().getMatricule());
            Docx docx = new Docx(pathModeles, pathOutput);
            docx.generate("AUTORISATIONABSENCE.docx", param, absence.getProgressionAgent().getAgent(), b.getData());
        } catch (Exception e) { System.out.println(e);}
    }
      public void universalGenerateTrameFunction(Object[] parameters, Object[] parameterValue, Agent selectedAgent, String trameName) {
        try {
            HashMap param = new HashMap();
            if (parameters.length == parameterValue.length) {
                try {
                    param.put("DATEEVT", JsfUtil.convertDate(new Date(),
                            "dd MMMMM yyyy"));
                } catch (NullPointerException e) {
                    param.put("DATEEVT", "-");
                }
                 
                try {
                    param.put("CIVILITE", selectedAgent.getPersonne().getCivilite().getLabel());
                } catch (NullPointerException e) {
                    param.put("CIVILITE", "-");
                }
                 try {
                    param.put("NAME", selectedAgent.getPersonne().getPrenom()+" "+selectedAgent.getPersonne().getNom());
                } catch (NullPointerException e) {
                    param.put("NAME", "-");
                }
                try {
                    param.put("POSTE", agentFacade.findProgressionPosteById(selectedAgent.getLastProgressionPoste()).getPoste().getLibelle());
                } catch (NullPointerException e) {
                    param.put("POSTE", "-");
                }
                 try {
                    param.put("ANNEE", contextBean.getCurrentYear().getLibelle());
                } catch (NullPointerException e) {
                    param.put("ANNEE", "-");
                }
                try {
                    param.put("ORDONNATEUR", WordUtils.capitalize(contextBean.getCurrentUser().getPersonne().getPrenom()+" "+contextBean.getCurrentUser().getPersonne().getNom()));
                } catch (NullPointerException | ClassCastException e) {
                    param.put("ORDONNATEUR", "-");
                }

               
                for (int i = 0; i < parameters.length; i++) {
                    try {
                        param.put(parameters[i], parameterValue[i]);
                    } catch (NullPointerException e) {
                        param.put(parameters[i], "-");
                    }
                }
                Docx docx = new Docx(pathModeles, pathOutput);
                docx.generate(trameName, param);
            }

        } catch (IOException | XDocReportException e) {
        }
    }
}
