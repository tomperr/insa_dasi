/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
public class Client extends Utilisateur
{
    // ATTRIBUTES
    
    /*
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    */

    @Temporal(TemporalType.DATE)
    private Date date_naissance;
    
    private String adresse;
    
    @OneToMany(mappedBy="client")
    private List<Consultation> consultations;
    
    @Embedded
    private ProfilAstral profil_astral;
    
    // GETTERS AND SETTERS

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public ProfilAstral getProfil_astral() {
        return profil_astral;
    }

    public void setProfil_astral(ProfilAstral profil_astral) {
        this.profil_astral = profil_astral;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
    
    
    
    // CONSTRUCTORS
    
    public Client() {
        
    }

    public Client(Date _date_naissance, String _adresse, String _nom, String _prenom,
            char _genre, String _mail, String _motdepasse, String _telephone) {
        this.date_naissance = _date_naissance;
        this.adresse = _adresse;
        this.nom = _nom;
        this.prenom = _prenom;
        this.genre = _genre;
        this.mail = _mail;
        this.motdepasse = _motdepasse;
        this.telephone = _telephone;
    }
    
    // METHODS
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
        if (consultation.getClient() != this) {
            consultation.setClient(this);
        }
    }
    
    @Override
    public String toString() {
        String res =  "-> Client: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "nom=" + getNom() + ";" + "\n" +
        "prenom=" + getPrenom() + ";" + "\n" +
        "mail=" + getMail() + ";" + "\n" +
        "motDePasse=" + getMotdepasse() + ";" + "\n" +
        "genre=" + getGenre() + ";" + "\n" +
        "telephone=" + getTelephone() + ";" + "\n" +
        "date_naissance=" + getDate_naissance() + ";" + "\n" +
        "adresse=" + getAdresse() + ";" + "\n" +
        "zodiac=" + profil_astral.getSigne_zodiaque() + ";" + "\n" +
        "chnois=" + profil_astral.getSigne_chinois() + ";" + "\n" +
        "couleur=" + profil_astral.getCouleur() + ";" + "\n" +
        "animal=" + profil_astral.getAnimal() + ";" + "\n";
        
        for (Consultation consultation : this.consultations) {
            res += consultation.toString() + "\n";
        }
        
        return res;
    }
    
}
