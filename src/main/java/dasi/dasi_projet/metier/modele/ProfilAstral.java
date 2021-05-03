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
@Embeddable
public class ProfilAstral {
    
    // ATTRIBUTES
    
    private String signe_zodiaque;
    private String signe_chinois;
    private String couleur;
    private String animal;
    
    // GETTERS AND SETTERS

    public String getSigne_zodiaque() {
        return signe_zodiaque;
    }

    public void setSigne_zodiaque(String signe_zodiaque) {
        this.signe_zodiaque = signe_zodiaque;
    }

    public String getSigne_chinois() {
        return signe_chinois;
    }

    public void setSigne_chinois(String signe_chinois) {
        this.signe_chinois = signe_chinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
    
    // CONSTRUCTORS
    
    public ProfilAstral() {
        
    }

    public ProfilAstral(String signe_zodiaque, String signe_chinois,
            String couleur, String animal) {
        this.signe_zodiaque = signe_zodiaque;
        this.signe_chinois = signe_chinois;
        this.couleur = couleur;
        this.animal = animal;
    }
    
}
