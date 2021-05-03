/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
public class Consultation {
    
    // ATTRIBUTES
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String commentaire;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_debut;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_fin;
    
    @ManyToOne
    private Employe employe;
    
    @ManyToOne
    private Client client;
    
    @ManyToOne
    private Medium medium;

    // GETTERS AND SETTERS
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }
    
    // CONSTRUCTORS

    public Consultation() {

    }
    
    public Consultation(String commentaire, Date date_debut, Date date_fin) {
        this.commentaire = commentaire;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        
    }
    
    @Override
    public String toString() {
        String res = "-> Consultation: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "commentaire=" + getCommentaire() + ";" + "\n" +
        "date_debut=" + getDate_debut() + ";" + "\n" +
        "date_fin=" + getDate_fin() + ";" + "\n";
        
        return res;
    }
    
}
