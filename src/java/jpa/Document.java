package jpa;

//mport com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
//import jpa.Ged;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
public class Document implements Serializable {

    @Id
    private String id;
    private String submittedFileName;
    private String fileName;
    private String mimeType;
    private String taille;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    private boolean sens;
    @Transient
    private boolean checkbox;
    @Version
    private Timestamp version;
    @ManyToOne
    private TypeDocument typeDocument;
    private String refDocument;
    private String description;
    @ManyToOne
    private ProgressionAgent progressionAgent;
    @ManyToOne
    private Annee anneecivile;
    @Transient
    private byte[] contenu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Annee getAnneecivile() {
        return anneecivile;
    }

    public void setAnneecivile(Annee anneecivile) {
        this.anneecivile = anneecivile;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    @PrePersist
    public void initDateCreation() {
        dateCreation = new Date();
    }

    public boolean isSens() {
        return sens;
    }

    public void setSens(boolean sens) {
        this.sens = sens;
    }

    public TypeDocument getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(TypeDocument typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getSubmittedFileName() {
        return submittedFileName;
    }

    public void setSubmittedFileName(String submittedFileName) {
        this.submittedFileName = submittedFileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public String getRefDocument() {
        return refDocument;
    }

    public void setRefDocument(String refDocument) {
        this.refDocument = refDocument;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getContenu() {
        return contenu;
    }

    public void setContenu(byte[] contenu) {
        this.contenu = contenu;
    }

    public ProgressionAgent getProgressionAgent() {
        return progressionAgent;
    }

    public void setProgressionAgent(ProgressionAgent progressionAgent) {
        this.progressionAgent = progressionAgent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "{id : " + this.id + " - fileName : " + this.fileName + " - description : " + this.description + "}";
    }

    public void reset() {
        this.id = "";
        this.submittedFileName = "";
        this.sens = false;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

}