/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.dao;

import java.util.ArrayList;
import java.util.List;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Utilisateur;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.Query;
import java.util.*;

/**
 *
 * @author TomPC
 */
public class EmployeDao {
    
    public void creer(Employe emp) {
        JpaUtil.obtenirContextePersistance().persist(emp);
    }
    
    public Employe modifier(Employe emp) {
        return JpaUtil.obtenirContextePersistance().merge(emp);
    }
    
    public Employe chercherEmployeDispo(char gender) {
        // TODO : selectionner un employe avec le moins de consultations
        Employe emp = null;
        List<Employe> employes = new ArrayList<Employe>();
        
        String jpql = "select e "
                + "from Employe e "
                + "where e.disponible = true " 
                + "and e.genre = :unGenre";
        
        try {
            
            TypedQuery query = JpaUtil.obtenirContextePersistance()
                .createQuery(jpql, Employe.class);
            query.setParameter("unGenre", gender);
            employes = query.getResultList();
            employes.sort(new Comparator<Employe>(){
               @Override
               public int compare(Employe e1, Employe e2)
               {
                   if (e1.getConsultations().size() == e2.getConsultations().size()) {
                       return 0;
                   }
                   return e1.getConsultations().size() < e2.getConsultations().size() ? -1 : 1;
               }
            });
            
            if (employes.size() > 0)
                emp = employes.get(0);

        } catch(NoResultException e) {
            System.out.println(e.getMessage());
            emp = null;
        }
        return emp;
    }
    
    public Map<String,Integer> tousAvecNombreClientsDifferent()
    {
        Map<String,Integer> statistiques = new HashMap<String,Integer>();
        
        String s = "SELECT e.nom, e.prenom, count(DISTINCT cl.id) "
                + "FROM Employe e "
                + "LEFT JOIN e.consultations co "
                + "LEFT JOIN co.client cl "
                + "GROUP BY e.nom, e.prenom";
        
        Query query = JpaUtil.obtenirContextePersistance().createQuery(s);
        
        List<Object[]> results = query.getResultList();
        
        if(results != null)
        {
            for(Object[] row : results)
            {
                statistiques.put(row[0] + " " + row[1], Math.toIntExact((long) row[2]));
            }
        }
        
        return statistiques;
    }
    
}
