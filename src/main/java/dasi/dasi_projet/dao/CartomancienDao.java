/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Cartomancien;

/**
 *
 * @author TomPC
 */
public class CartomancienDao {
    public void creer(Cartomancien cartomancien) {
        JpaUtil.obtenirContextePersistance().persist(cartomancien);
    }
    
    public Cartomancien modifier(Cartomancien cartomancien) {
        return JpaUtil.obtenirContextePersistance().merge(cartomancien);
    }
}
