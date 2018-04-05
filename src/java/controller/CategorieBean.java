/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import ejb.CategorieBaseFacade;
import ejb.CategorieFacade;
import ejb.SalaireFacade;
import ejb.SousCategorieFacade;
import java.io.File;
import jpa.Categorie;
import util.JsfUtil;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jpa.CategorieBase;
import jpa.Salaire;
import jpa.SousCategorie;
import org.apache.log4j.Logger;

/**
 *
 * @author MJLDH
 */
@Named(value = "categorieBean")
@ViewScoped
public class CategorieBean implements Serializable {

    Logger logger = Logger.getLogger(CategorieBean.class);
    @EJB
    private CategorieFacade categorieFacade;
    @EJB
    private CategorieBaseFacade categorieBaseFacade;
    @EJB
    private SousCategorieFacade sousCategorieFacade;

    private CategorieBase selectedCategorieBase;
    private Categorie selectedCategorie;
    private Categorie newCategorie;
    private CategorieBase newCategorieBase;
    private SousCategorie newSousCategorie;
    private SousCategorie selectedSousCategorie;
    private List<CategorieBase> listeCategorieBases;
    private List<SousCategorie> listeSousCategories;
    private List<Categorie> listeCategories;
    private List<String> echelles;
    private String echelle;
    /**
     * Creates a new instance of CategorieBean
     */
    public CategorieBean() {

    }

    @PostConstruct
    public void init() {
        listeCategorieBases = categorieBaseFacade.findAll();
        listeSousCategories = sousCategorieFacade.findAll();
        listeCategories = categorieFacade.findAll();
        newCategorie = new Categorie();
        selectedCategorie = new Categorie();
        newCategorieBase = new CategorieBase();
        newSousCategorie = new SousCategorie();
        selectedSousCategorie = new SousCategorie();
        lireEchelle();
    }

    public List<String> lireEchelle() {
//        JsfUtil.logInfo(logger, "----------------------lireFichier----------------------------");
        try {
            File filePath = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/echelle.txt"));
//            JsfUtil.logInfo(logger, "filePath ====> " + filePath);
            echelles = Files.readAllLines(filePath.toPath(), StandardCharsets.UTF_8);
//            JsfUtil.logInfo(logger, "recuFichier ======> " + recuFichier);
        }catch (Exception exception) {
            JsfUtil.logError(logger, exception);
        }
        return echelles;
    }

    public SousCategorie getSelectedSousCategorie() {
        return selectedSousCategorie;
    }

    public void setSelectedSousCategorie(SousCategorie selectedSousCategorie) {
        this.selectedSousCategorie = selectedSousCategorie;
    }

    public Categorie getSelectedCategorie() {
        return selectedCategorie;
    }

    public void setSelectedCategorie(Categorie selectedCategorie) {
        this.selectedCategorie = selectedCategorie;
    }

    public List<String> getEchelles() {
        return echelles;
    }

    public void setEchelles(List<String> echelles) {
        this.echelles = echelles;
    }

    public String getEchelle() {
        return echelle;
    }

    public void setEchelle(String echelle) {
        this.echelle = echelle;
    }

    public SousCategorie getNewSousCategorie() {
        return newSousCategorie;
    }

    public void setNewSousCategorie(SousCategorie newSousCategorie) {
        this.newSousCategorie = newSousCategorie;
    }

    public CategorieBase getSelectedCategorieBase() {
        return selectedCategorieBase;
    }

    public void setSelectedCategorieBase(CategorieBase selectedCategorieBase) {
        this.selectedCategorieBase = selectedCategorieBase;
    }

    public CategorieBase getNewCategorieBase() {
        return newCategorieBase;
    }

    public void setNewCategorieBase(CategorieBase newCategorieBase) {
        this.newCategorieBase = newCategorieBase;
    }

    public List<SousCategorie> getListeSousCategories() {
        return listeSousCategories;
    }

    public void setListeSousCategories(List<SousCategorie> listeSousCategories) {
        this.listeSousCategories = listeSousCategories;
    }

    public List<Categorie> getListeCategories() {
        return listeCategories;
    }

    public void setListeCategories(List<Categorie> listeCategories) {
        this.listeCategories = listeCategories;
    }

    public List<CategorieBase> getListeCategorieBases() {
        return listeCategorieBases;
    }

    public void setListeCategorieBases(List<CategorieBase> listeCategorieBases) {
        this.listeCategorieBases = listeCategorieBases;
    }

    public Categorie getNewCategorie() {
        return newCategorie;
    }

    public void setNewCategorie(Categorie newCategorie) {
        this.newCategorie = newCategorie;
    }

    public void passItem(Categorie item) {
        System.out.println();
        this.selectedCategorie = item;
    }

    public void passItemBase(CategorieBase item) {
        this.selectedCategorieBase = item;
    }

    public void passItemSous(SousCategorie item) {
        System.out.println("===="+item);
        this.selectedSousCategorie = item;
    }

    /**
     * **
     *
     * @param event
     */
    public void doCreate(ActionEvent event){
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            if(echelle != null){
                newCategorie.setEchelle(Integer.parseInt(echelle));
            }
            newCategorie.setLibelle(newCategorie.getCode() + "-" + echelle);
            categorieFacade.create(newCategorie);
            msg = bundle.getString("CategorieCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newCategorie = new Categorie();
            echelle = null;
            this.listeCategories = this.categorieFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CategorieCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateCategorieBase(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.categorieBaseFacade.create(newCategorieBase);
            msg = bundle.getString("CategorieBaseCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newCategorieBase = new CategorieBase();
            this.listeCategorieBases = this.categorieBaseFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CategorieBaseCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doCreateSousCategorie(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            this.sousCategorieFacade.create(newSousCategorie);
            msg = bundle.getString("SousCategorieCreateSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            newSousCategorie = new SousCategorie();
            this.listeSousCategories = this.sousCategorieFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("SousCategorieCreateErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEdit(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            categorieFacade.edit(selectedCategorie);
            this.listeCategories = this.categorieFacade.findAll();
            msg = bundle.getString("CategorieEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("CategorieEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doEditBase(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            categorieBaseFacade.edit(selectedCategorieBase);
            this.listeCategorieBases = categorieBaseFacade.findAll();
            msg = bundle.getString("CategorieBaseEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("CategorieBaseEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
    
    public void doEditSous(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            sousCategorieFacade.edit(selectedSousCategorie);
            this.listeSousCategories = sousCategorieFacade.findAll();
            msg = bundle.getString("SousCategorieEditSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            msg = bundle.getString("SousCategorieEditErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
    
    public void doDel(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            categorieFacade.remove(selectedCategorie);
            msg = bundle.getString("CategorieDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            listeCategories.remove(selectedCategorie);
            listeCategories = categorieFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CategorieDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void doDelBase(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            categorieBaseFacade.remove(selectedCategorieBase);
            msg = bundle.getString("CategorieBaseDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            listeCategorieBases.remove(selectedCategorieBase);
            listeCategorieBases = categorieBaseFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("CategorieBaseDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
    
    public void doDelSous(ActionEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle;
        bundle = ResourceBundle.getBundle(
                "util.Bundle", context.getViewRoot().getLocale());
        String msg;
        try {
            sousCategorieFacade.remove(selectedSousCategorie);
            msg = bundle.getString("SousCategorieDelSuccessMsg");
            JsfUtil.addSuccessMessage(msg);
            listeSousCategories.remove(selectedSousCategorie);
            listeSousCategories = sousCategorieFacade.findAll();
        } catch (Exception e) {
            msg = bundle.getString("SousCategorieDelErrorMsg");
            JsfUtil.addErrorMessage(msg);
        }
    }
}
