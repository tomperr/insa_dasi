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
public class Cartomancien extends Medium {
    
    // CONSTRUCTORS

    public Cartomancien() {
        
    }
    
    public Cartomancien(String _denomination, char _genre,
            String _presentation) {
        super(_denomination, _genre, _presentation);
    }
   
    @Override
    public String toString() {
        return "-> Cartomancien: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "denomination=" + getDenomination() + ";" + "\n" +
        "genre=" + getGenre() + ";" + "\n" +
        "presentation=" + getPresentation() + ";";
    }
    
}