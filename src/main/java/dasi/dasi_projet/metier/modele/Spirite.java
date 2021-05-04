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
public class Spirite extends Medium {
    
    // ATTRIBUTES
    
    private String support;
    
    // GETTERS AND SETTERS

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
    
    // CONSTRUCTORS

    public Spirite() {
        
    }
    
    public Spirite(String _denomination, char _genre, String _presentation,
            String support) {
        super(_denomination, _genre, _presentation);
        this.support = support;
    }
    
    @Override
    public String toString() {
        return "-> Spirite: " + "\n" +
        "id=" + getId() + ";" + "\n" +
        "denomination=" + getDenomination() + ";" + "\n" +
        "genre=" + getGenre() + ";" + "\n" +
        "presentation=" + getPresentation() + ";" + "\n" +
        "support=" + getSupport() + ";";
    }
    
}
