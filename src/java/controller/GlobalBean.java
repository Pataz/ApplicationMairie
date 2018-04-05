/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jpa.Civilite;
import jpa.InterneExterne;
import jpa.NatureAgent;
import jpa.Personnel;
import jpa.Sexe;
import jpa.SituationMat;
import jpa.TypeAbsence;
import jpa.TypeAvancement;
import jpa.TypeDirection;
import jpa.TypeEntree;
import jpa.TypeVariable;
import jpa.ModePayement;
import jpa.Renumeration;

/**
 *
 * @author MJLDH
 */
@Named(value = "globalBean")
@ViewScoped
public class GlobalBean implements Serializable {

    public List<Civilite> getCiviliteItems() {
        return Arrays.asList(Civilite.values());
    }

    public List<SituationMat> getSituationMatItems() {
        return Arrays.asList(SituationMat.values());
    }

    public List<Sexe> getSexeItems() {
        return Arrays.asList(Sexe.values());
    }

    public List<TypeEntree> getTypeEntreeItems() {
        return Arrays.asList(TypeEntree.values());
    }

    public List<TypeAbsence> getTypeAbsenceItems() {
        return Arrays.asList(TypeAbsence.values());
    }

    public List<InterneExterne> getInterneExterneItems() {
        return Arrays.asList(InterneExterne.values());
    }

    public List<TypeAvancement> getTypeAvancementItems() {
        return Arrays.asList(TypeAvancement.values());
    }

    public List<NatureAgent> getNatureAgentItems() {
        return Arrays.asList(NatureAgent.values());
    }

    public List<TypeDirection> getTypeDirectionItems() {
        return Arrays.asList(TypeDirection.values());
    }

    public List<Personnel> getPersonnelItems() {
        return Arrays.asList(Personnel.values());
    }

    public List<TypeVariable> getTypeVAriableItems() {
        return Arrays.asList(TypeVariable.values());
    }

    public List<ModePayement> getModePayementItems() {
        return Arrays.asList(ModePayement.values());
    }
    
    public List<Renumeration> getRenumerationItems() {
        return Arrays.asList(Renumeration.values());
    }
}
