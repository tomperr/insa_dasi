/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Spirite;

/**
 *
 * @author TomPC
 */
public class SpiriteDao {
    public void creer(Spirite spirite) {
        JpaUtil.obtenirContextePersistance().persist(spirite);
    }
    
    public Spirite modifier(Spirite spirite) {
        return JpaUtil.obtenirContextePersistance().merge(spirite);
    }
}
