/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Medium;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author TomPC
 */
public class MediumDao {
    
    public void creer(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }
    
    public Medium modifier(Medium medium) {
        return JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
    public Medium chercherParDenomination(String _denom) {
        Medium med;
        String s = "select m from Medium m where m.denomination = :uneDenom";
        try{
            TypedQuery query = JpaUtil.obtenirContextePersistance()
                .createQuery(s, Medium.class);
            query.setParameter("uneDenom", _denom);
            med = (Medium)query.getSingleResult();
        } catch(NoResultException e) {
            med = null;
        }
        return med;
    }
    
    public Medium chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
    public List<Medium> chercherTous(){
        String s = "select m from Medium m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
    
}