/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Consultation;

/**
 *
 * @author tperrillat
 */
public class ConsultationDao {
    
    public void creer(Consultation consultation) {
        JpaUtil.obtenirContextePersistance().persist(consultation);
    }
    
    public Consultation modifier(Consultation consultation) {
        return JpaUtil.obtenirContextePersistance().merge(consultation);
    }
    
    public Consultation chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    } 
   
}
