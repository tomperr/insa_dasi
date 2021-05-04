/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Utilisateur {
    
    // ATTRIBUTES
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
     
    protected String nom;
    
    protected String prenom;
    
    protected char genre;
    
    @Column(unique=true)
    protected String mail;
    
    protected String motdepasse;
    
    @Column(length=10)
    protected String telephone;
    
    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    // Constructors
    
    public Utilisateur () {
        
    }
    
    public Utilisateur (String first_name, String last_name, char gender,
            String email, String pass, String phone) {
        this.nom = last_name;
        this.prenom = first_name;
        this.genre = gender;
        this.mail = email;
        this.motdepasse = pass;
        this.telephone = phone;
    }
    
    
}
