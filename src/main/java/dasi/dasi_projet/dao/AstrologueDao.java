/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Astrologue;

/**
 *
 * @author TomPC
 */
public class AstrologueDao {
    public void creer(Astrologue astrologue) {
        JpaUtil.obtenirContextePersistance().persist(astrologue);
    }
    
    public Astrologue modifier(Astrologue astrologue) {
        return JpaUtil.obtenirContextePersistance().merge(astrologue);
    }
}
