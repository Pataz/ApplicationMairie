/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import util.JsfUtil;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.log4j.Logger;

/**
 *
 * @author Gildasdarex
 */
@Singleton
@Startup
public class ContextFacade {

    Logger logger = Logger.getLogger(ContextFacade.class);
  
    
      private String archiveURL;
    //private Matiere commerciale;
    private String pathRoot; // le chemin de la racine
    private String cleOPJ;

    private String CODE_JUGI, CODE_PCR, CODE_PCG, CODE_JUG, CODE_SECPAR, CODE_PG, CODE_CA, DOS_CRE_SERV_EXP, DOS_CRE_SERV_DEST, DOS_SERV_ARCHIV, CODE_CHAMBRE_DISTRIBUTION, CODE_SERVICE_ENROLEMENT, NUMERO_DOSSIER_RP;
    private String ALFRESCO_REPOSITORY;
    private String DEVISE_TOGO, LIBELLE_PAYS, CURRENTPAYSCODE;
    private String ARCHIVE_URL;
    private String APPEL_PATH;
    private String BIOMETRIE_PATH;
    private String OPJ_PV_PATH;
    private boolean FEU_ROUGE;
    
  /**
     * nom du paramètre de l'URL du service d'entrepôt CMIS dans la base de
     * données.
     */
    static final String cmisSvcUrlParam = "cmis.service.url";
    /**
     * L'URL du service CMIS
     */
    String cmisURL;

    /**
     * nom d'utilisateur de connexion.
     */
    String cmisUsername;

    /**
     * mot de passe de connexion.
     */
    String cmisPassword;

//    @PostConstruct
//    public void init() {
//        initParametres();
//    }
   
//    public void initParametres() {
//     
//           
//    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    public String getArchiveURL() {
        return archiveURL;
    }

    public void setArchiveURL(String archiveURL) {
        this.archiveURL = archiveURL;
    }

    public String getPathRoot() {
        return pathRoot;
    }

    public void setPathRoot(String pathRoot) {
        this.pathRoot = pathRoot;
    }

    public String getCleOPJ() {
        return cleOPJ;
    }

    public void setCleOPJ(String cleOPJ) {
        this.cleOPJ = cleOPJ;
    }


    public String getCODE_JUGI() {
        return CODE_JUGI;
    }

    public void setCODE_JUGI(String CODE_JUGI) {
        this.CODE_JUGI = CODE_JUGI;
    }

    public String getCODE_PCR() {
        return CODE_PCR;
    }

    public void setCODE_PCR(String CODE_PCR) {
        this.CODE_PCR = CODE_PCR;
    }

    public String getCODE_PCG() {
        return CODE_PCG;
    }

    public void setCODE_PCG(String CODE_PCG) {
        this.CODE_PCG = CODE_PCG;
    }

    public String getCODE_JUG() {
        return CODE_JUG;
    }

    public void setCODE_JUG(String CODE_JUG) {
        this.CODE_JUG = CODE_JUG;
    }

    public String getCODE_SECPAR() {
        return CODE_SECPAR;
    }

    public void setCODE_SECPAR(String CODE_SECPAR) {
        this.CODE_SECPAR = CODE_SECPAR;
    }

    public String getCODE_PG() {
        return CODE_PG;
    }

    public void setCODE_PG(String CODE_PG) {
        this.CODE_PG = CODE_PG;
    }

    public String getCODE_CA() {
        return CODE_CA;
    }

    public void setCODE_CA(String CODE_CA) {
        this.CODE_CA = CODE_CA;
    }

    public String getDOS_CRE_SERV_EXP() {
        return DOS_CRE_SERV_EXP;
    }

    public void setDOS_CRE_SERV_EXP(String DOS_CRE_SERV_EXP) {
        this.DOS_CRE_SERV_EXP = DOS_CRE_SERV_EXP;
    }

    public String getDOS_CRE_SERV_DEST() {
        return DOS_CRE_SERV_DEST;
    }

    public void setDOS_CRE_SERV_DEST(String DOS_CRE_SERV_DEST) {
        this.DOS_CRE_SERV_DEST = DOS_CRE_SERV_DEST;
    }

    public String getDOS_SERV_ARCHIV() {
        return DOS_SERV_ARCHIV;
    }

    public void setDOS_SERV_ARCHIV(String DOS_SERV_ARCHIV) {
        this.DOS_SERV_ARCHIV = DOS_SERV_ARCHIV;
    }

    public String getCODE_CHAMBRE_DISTRIBUTION() {
        return CODE_CHAMBRE_DISTRIBUTION;
    }

    public void setCODE_CHAMBRE_DISTRIBUTION(String CODE_CHAMBRE_DISTRIBUTION) {
        this.CODE_CHAMBRE_DISTRIBUTION = CODE_CHAMBRE_DISTRIBUTION;
    }

    public String getCODE_SERVICE_ENROLEMENT() {
        return CODE_SERVICE_ENROLEMENT;
    }

    public void setCODE_SERVICE_ENROLEMENT(String CODE_SERVICE_ENROLEMENT) {
        this.CODE_SERVICE_ENROLEMENT = CODE_SERVICE_ENROLEMENT;
    }

    public String getNUMERO_DOSSIER_RP() {
        return NUMERO_DOSSIER_RP;
    }

    public void setNUMERO_DOSSIER_RP(String NUMERO_DOSSIER_RP) {
        this.NUMERO_DOSSIER_RP = NUMERO_DOSSIER_RP;
    }

    public String getALFRESCO_REPOSITORY() {
        return ALFRESCO_REPOSITORY;
    }

    public void setALFRESCO_REPOSITORY(String ALFRESCO_REPOSITORY) {
        this.ALFRESCO_REPOSITORY = ALFRESCO_REPOSITORY;
    }

    public String getDEVISE_TOGO() {
        return DEVISE_TOGO;
    }

    public void setDEVISE_TOGO(String DEVISE_TOGO) {
        this.DEVISE_TOGO = DEVISE_TOGO;
    }

    public String getLIBELLE_PAYS() {
        return LIBELLE_PAYS;
    }

    public void setLIBELLE_PAYS(String LIBELLE_PAYS) {
        this.LIBELLE_PAYS = LIBELLE_PAYS;
    }

    public String getCURRENTPAYSCODE() {
        return CURRENTPAYSCODE;
    }

    public void setCURRENTPAYSCODE(String CURRENTPAYSCODE) {
        this.CURRENTPAYSCODE = CURRENTPAYSCODE;
    }

    public String getARCHIVE_URL() {
        return ARCHIVE_URL;
    }

    public void setARCHIVE_URL(String ARCHIVE_URL) {
        this.ARCHIVE_URL = ARCHIVE_URL;
    }

    public String getAPPEL_PATH() {
        return APPEL_PATH;
    }

    public void setAPPEL_PATH(String APPEL_PATH) {
        this.APPEL_PATH = APPEL_PATH;
    }

    public String getBIOMETRIE_PATH() {
        return BIOMETRIE_PATH;
    }

    public void setBIOMETRIE_PATH(String BIOMETRIE_PATH) {
        this.BIOMETRIE_PATH = BIOMETRIE_PATH;
    }

    public String getOPJ_PV_PATH() {
        return OPJ_PV_PATH;
    }

    public void setOPJ_PV_PATH(String OPJ_PV_PATH) {
        this.OPJ_PV_PATH = OPJ_PV_PATH;
    }

    public boolean isFEU_ROUGE() {
        return FEU_ROUGE;
    }

    public void setFEU_ROUGE(boolean FEU_ROUGE) {
        this.FEU_ROUGE = FEU_ROUGE;
    }

    public String getCmisURL() {
        return cmisURL;
    }

    public void setCmisURL(String cmisURL) {
        this.cmisURL = cmisURL;
    }

    public String getCmisUsername() {
        return cmisUsername;
    }

    public void setCmisUsername(String cmisUsername) {
        this.cmisUsername = cmisUsername;
    }

    public String getCmisPassword() {
        return cmisPassword;
    }

    public void setCmisPassword(String cmisPassword) {
        this.cmisPassword = cmisPassword;
    }
    
}
