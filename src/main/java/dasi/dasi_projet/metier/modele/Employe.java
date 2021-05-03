/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
public class Employe extends Utilisateur {
    
    // ATTRIBUTES
    
    private boolean disponible;
    
    @OneToMany(mappedBy="employe")
    private List<Consultation> consultations;
    
    // GETTERS AND SETTERS

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
    
    // CONSTRUCTORS

    public Employe() {
        
    }
    
    public Employe(String _nom, String _prenom, char _genre, String _mail,
            String _motdepasse, String _telephone, boolean disponible) {
        this.nom = _nom;
        this.prenom = _prenom;
        this.genre = _genre;
        this.mail = _mail;
        this.motdepasse = _motdepasse;
        this.telephone = _telephone;
        this.disponible = disponible;
        this.consultations = new ArrayList<Consultation>();
    }
    
    // Methods
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
        if (consultation.getEmploye() != this) {
            consultation.setEmploye(this);
        }
    }
    
    @Override
    public String toString() {
        String res = "-> Employe: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "nom=" + getNom() + ";" + "\n" +
        "prenom=" + getPrenom() + ";" + "\n" +
        "mail=" + getMail() + ";" + "\n" +
        "motDePasse=" + getMotdepasse() + ";" + "\n" +
        "genre=" + getGenre() + ";" + "\n" +
        "telephone=" + getTelephone() + ";" + "\n" +
        "disponible=" + isDisponible() + ";";
        
        for (Consultation consultation : this.consultations) {
            res += consultation.toString() + "\n";
        }
        
        return res;
    }
    
}
