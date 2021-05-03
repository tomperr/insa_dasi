/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.modele;

import java.util.List;
import javax.persistence.*;

/**
 *
 * @author tperrillat
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Medium {
    
    // ATTRIBUTES
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    
    protected String denomination;
    
    protected char genre;
    
    protected String presentation;
    
    @OneToMany(mappedBy="medium")
    protected List<Consultation> consultations;
    
    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public char getGenre() {
        return genre;
    }

    public void setGenre(char genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }
    
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
        if (consultation.getMedium() != this) {
            consultation.setMedium(this);
        }
    }
    
}
