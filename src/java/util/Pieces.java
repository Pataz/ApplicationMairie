/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import javax.servlet.http.Part;
import jpa.TypeDocument;

/**
 *
 * @author D O N A T I E N
 */
public class Pieces {
    private TypeDocument typeDocument;
    private String nomFichier;
    private Long taille;
    private Part file;

    public Pieces() {
    }

    public Pieces(TypeDocument typeDocument, String nomFichier, Long taille,Part file) {
        this.typeDocument = typeDocument;
        this.nomFichier = nomFichier;
        this.taille = taille;
        this.file=file;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
    

    public Long getTaille() {
        return taille;
    }

    public void setTaille(Long taille) {
        this.taille = taille;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
   public static void main(String[] args) throws ParseException{
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    Date datenaiss=sdf.parse("06/07/1958");
       LocalDateTime datedujour = LocalDate.now().atStartOfDay();
        Date dateJour = JsfUtil.convertirEnDate(datedujour);
         Date dateBefore = null;
          Date dateRetraite = null;
//        for(Agent agent: listeAgents){
           dateBefore=JsfUtil.differenceFunction(datenaiss, 59);
            dateRetraite=JsfUtil.differenceFunction(datenaiss, 60);
            System.out.println("dateBefore==>"+JsfUtil.convertDate(dateBefore, "dd MMMM yyy"));
             System.out.println("dateRetraite==>"+JsfUtil.convertDate(dateRetraite, "dd MMMM yyy"));
              System.out.println("JsfUtil.getDaysBetween(dateJour,dateBefore)==>"+JsfUtil.getDaysBetween(dateJour,dateBefore));
              System.out.println("JsfUtil.getDaysBetween(dateJour, dateRetraite)==>"+JsfUtil.getDaysBetween(dateJour, dateRetraite));
//              System.out.println("JsfUtil.getDaysBetween(dateJour,dateBefore)==>"+JsfUtil.getDaysBetween(dateJour,dateBefore));
//              System.out.println("JsfUtil.getDaysBetween(dateJour, dateRetraite)==>"+JsfUtil.getDaysBetween(dateJour, dateRetraite));

//           if(JsfUtil.getDaysBetween(dateJour,dateBefore)>=0&&JsfUtil.getDaysBetween(dateJour, dateRetraite) <=0)
//        }   
   } 
}
