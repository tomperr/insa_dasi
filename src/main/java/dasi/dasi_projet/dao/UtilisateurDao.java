/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Utilisateur;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author tperrillat
 */
public class UtilisateurDao {
    public Utilisateur chercherParMailMotDePasse(String mail, String motDePasse) {
        Utilisateur user;
        String s = "select u from Utilisateur u where u.mail = :unMail and "
                + "u.motdepasse = :unMdp";
        try{
            TypedQuery query = JpaUtil.obtenirContextePersistance()
                .createQuery(s, Utilisateur.class);
            query.setParameter("unMail", mail);
            query.setParameter("unMdp", motDePasse);
            user = (Utilisateur)query.getSingleResult();
        } catch(NoResultException e) {
            user = null;
        }
        return user;
    }
}
