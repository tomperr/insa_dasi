/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import dasi.dasi_projet.dao.JpaUtil;
import dasi.dasi_projet.metier.modele.Client;
import javax.persistence.TypedQuery;

/**
 *
 * @author tperrillat
 */
public class ClientDao {
    
    public void creer(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public Client modifier(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    } 
    
}
