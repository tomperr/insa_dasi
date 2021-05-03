/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
public class Astrologue extends Medium {
    
    // ATTRIBUTES
    
    private int promotion;
    
    private String formation;
    
    // GETTERS AND SETTERS

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }
    
    // CONSTRUCTORS

    public Astrologue() {
        
    }
    
    public Astrologue(String _denomination, char _genre,
            String _presentation, int _promotion, String _formation) {
        this.denomination = _denomination;
        this.genre = _genre;
        this.presentation = _presentation;
        this.promotion = _promotion;
        this.formation = _formation;
        this.consultations = new ArrayList<Consultation>();
    }
    
    @Override
    public String toString() {
        return "-> Astrologue: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "denomination=" + getDenomination() + ";" + "\n" +
        "genre=" + getGenre() + ";" + "\n" +
        "presentation=" + getPresentation() + ";" + "\n" +
        "promotion=" + getPromotion() + ";" + "\n" +
        "formation=" + getFormation() + ";";
    }

}
