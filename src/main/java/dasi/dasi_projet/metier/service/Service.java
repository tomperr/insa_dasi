/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.dasi_projet.metier.service;


import dasi.dasi_projet.dao.MediumDao;
import dasi.dasi_projet.dao.AstrologueDao;
import dasi.dasi_projet.dao.CartomancienDao;
import dasi.dasi_projet.dao.JpaUtil;
import dasi.dasi_projet.dao.ClientDao;
import dasi.dasi_projet.dao.ConsultationDao;
import dasi.dasi_projet.dao.EmployeDao;
import dasi.dasi_projet.dao.SpiriteDao;
import dasi.dasi_projet.dao.UtilisateurDao;
import dasi.dasi_projet.metier.modele.Astrologue;
import dasi.dasi_projet.metier.modele.Cartomancien;
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.ProfilAstral;
import dasi.dasi_projet.metier.modele.Spirite;
import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.service.Message;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

/**
 *
 * @author tperrillat
 */
public class Service {
    
    public void initialisationDonneesClients() throws ParseException, IOException {
        
        // Create Clients
        // Date _date_naissance, String _adresse, String _nom, String _prenom,
        // char _genre, String _mail, String _motdepasse, String _telephone
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        
        inscrireClient("Tom", "PERR", 'M', "tom.perr@insa-lyon.fr",
            "password_hard", "0645788956", "08/03/2000",
            "12 rue de l'INSA");
        
        inscrireClient("Baptiste", "EDOUARD", 'M', "baptiste.edouard@insa-lyon.fr",
            "edd_02022001", "0612562332", "02/02/2001",
            "33c rue des enfants");

    }
    
    public Client inscrireClient(String first_name, String last_name,
            char gender, String email, String pass, String phone, String birth,
            String address) throws IOException, ParseException {
   
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sf.parse(birth);
        
        Client client = new Client(d, address, last_name, first_name, gender,
            email, pass, phone);
        
        // generate profil astral
        AstroNetApi ana = new AstroNetApi();
        List<String> response = ana.getProfil(client.getPrenom(),
                client.getDate_naissance());
        ProfilAstral pa = new ProfilAstral(response.get(0), response.get(1),
                response.get(2), response.get(3));
        
        // update client with generated profil astral
        client.setProfil_astral(pa);
    
        ClientDao clientDao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clientDao.creer(client);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
            // send confirmation email
            Message.envoyerMail("admin.dasi@insa-lyon.fr", client.getMail(),
                    "Confirmation inscription", "Bienvenue chez nous !");
            
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            Message.envoyerMail("admin.dasi@insa-lyon.fr", client.getMail(),
                    "Erreur lors de l'inscription", "Nous sommes désolé, mais"
                            + " votre inscription n'a pu être validée.");
            JpaUtil.annulerTransaction();
            client = null;
            // todo: send error mail
        } finally {
            JpaUtil.fermerContextePersistance();      
        }
        return client;
    }
    
    //public Consultation creerConsultation(Consultation consultation) {
    public Consultation demanderConsultation(Client client, Medium medium) {

        Consultation consultation = null;
        
        if (client != null && medium != null) {
        
            consultation = new Consultation();
            consultation.setClient(client);
            consultation.setMedium(medium);

            EmployeDao employeDao = new EmployeDao();        
            ConsultationDao consultationDao = new ConsultationDao();
            ClientDao clientDao = new ClientDao();

            try {

                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();

                char medium_gender = medium.getGenre();

                Employe emp = employeDao.chercherEmployeDispo(medium_gender);

                if (emp != null) {

                    // add consultation to employee
                    emp.addConsultation(consultation);
                    emp.setDisponible(false);
                    employeDao.modifier(emp);

                    // add consultation to medium
                    MediumDao mediumDao = new MediumDao();
                    medium.addConsultation(consultation);
                    mediumDao.modifier(medium);

                    // add consultation to client
                    client = consultation.getClient();
                    client.addConsultation(consultation);
                    clientDao.modifier(client);

                    // add employee to consultation
                    consultation.setEmploye(emp);
                    consultationDao.creer(consultation);
                    //System.out.println("Consultation cree");

                    JpaUtil.validerTransaction();
                    Logger.getAnonymousLogger().log(Level.INFO, "succès");
                } else {
                    consultation = null;
                }


            } catch (Exception e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur creation consultation !");
                System.out.println(e.getMessage());
                JpaUtil.annulerTransaction();
                consultation = null;
            } finally {
                JpaUtil.fermerContextePersistance();
            }  
        }
        
        return consultation;
    }
    
    public boolean commencerConsultation(Consultation consultation) {
        
        boolean started = false;
        
        if (consultation != null) {
            consultation.setDate_debut(new Date());
            ConsultationDao consultationDao = new ConsultationDao();

            try {
                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                consultationDao.modifier(consultation);
                JpaUtil.validerTransaction();
                Message.envoyerNotification(consultation.getClient().getTelephone(),
                    "Numéro à appeler : " + consultation.getEmploye().getTelephone());
                Logger.getAnonymousLogger().log(Level.INFO, "succès");
                started = true;
            } catch (Exception e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
                e.printStackTrace(); 
                JpaUtil.annulerTransaction();
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        
        return started;
        
    }
    
    public boolean terminerConsultation(Consultation consultation, String commentaire) {

        boolean finished = false;
        
        if (consultation != null) {
            Employe emp = consultation.getEmploye();   
            ConsultationDao consultationDao = new ConsultationDao();
            EmployeDao employeDao = new EmployeDao();

            try {

                emp.setDisponible(true);
                consultation.setDate_fin(new Date());
                consultation.setCommentaire(commentaire);

                JpaUtil.creerContextePersistance();
                JpaUtil.ouvrirTransaction();
                consultationDao.modifier(consultation); // update consultation
                employeDao.modifier(emp); // update employe
                JpaUtil.validerTransaction();
                Logger.getAnonymousLogger().log(Level.INFO, "succès");
                finished = true;
            } catch (Exception e) {
                Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
                e.printStackTrace(); 
                JpaUtil.annulerTransaction();
            } finally {
                JpaUtil.fermerContextePersistance();
            }
        }
        
        return finished;

    }
    
    public Utilisateur authentifierUtilisateur(String mail, String motDePasse) {
        UtilisateurDao utilisateurDao = new UtilisateurDao();
        Utilisateur utilisateur;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            utilisateur = utilisateurDao.chercherParMailMotDePasse(mail,
                    motDePasse);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            e.printStackTrace(); 
            JpaUtil.annulerTransaction();
            utilisateur = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return utilisateur;
    }
    
    public Consultation rechercherConsultation(long id) {
        Consultation consultation;
        ConsultationDao consultationDao = new ConsultationDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            consultation = consultationDao.chercherParId(id);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            consultation = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return consultation;
    }
    
    public Client rechercherClient(long id) {
        Client client;
        ClientDao clientDao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            client = clientDao.chercherParId(id);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }
    
    public void initialisationDonneesEmployes() {
        
        // Create Employe
        inscrireEmploye("Pierre", "Paul", 'M', "pierre.paul@insa-lyon.fr", "password_secure", "0645897878", true);
        inscrireEmploye("Jean", "Marie", 'M', "jean.marie@insa-lyon.fr", "r3llY_sTr0ng", "0778784512", true);
        inscrireEmploye("Marie", "Mathilde", 'F', "marie.mathilde@insa-lyon.fr", "1234batman", "0450122356", true);

        // Create Spirites
        inscrireSpirite("Bruce Wayne", 'M', "Je résouds vos problèmes avec la justice", "Batmobile");
        inscrireSpirite("Stephane Ricco", 'M', "Simple, efficace", "Boule de crystal");
        
        // Create Astrologues
        inscrireAstrologue("Marie Space", 'F', "Vers l'infini et l'au-dela", 2000, "DUT Espace");
        inscrireAstrologue("Laurine Pesquet", 'F', "Je retourne l'espace", 2010, "DUT Arnaqueuse");
        
        // Create Cartomanciens
        inscrireCartomancien("Lutti Lutti", 'M', "Top deck ton avenir");
        inscrireCartomancien("Best Marmotte", 'M', "Toujours les bonnes paires");
        
    }
    
    public Employe inscrireEmploye(String nom, String prenom, char genre, 
            String mail, String motdepasse, String telephone, boolean disponible) {
             
        Employe employe = new Employe(nom, prenom, genre, mail, motdepasse, telephone, disponible);
         
        EmployeDao employeDao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            employeDao.creer(employe);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            employe = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return employe;
    }
    
    public Spirite inscrireSpirite(String denomination, char genre,
            String presentation, String support) {
        
        Spirite spirite = new Spirite(denomination, genre, presentation, support);
        
        SpiriteDao spiriteDao = new SpiriteDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            spiriteDao.creer(spirite);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            spirite = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return spirite;
    }
    
    public Cartomancien inscrireCartomancien(String denomination, char genre,
            String presentation) {

        Cartomancien cartomancien = new Cartomancien(denomination, genre, 
            presentation);
        
        CartomancienDao cartomancienDao = new CartomancienDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            cartomancienDao.creer(cartomancien);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            cartomancien = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return cartomancien;
    }
    
    public Astrologue inscrireAstrologue(String denomination, char genre,
            String presentation, int promotion, String formation) {
        
        Astrologue astrologue = new Astrologue(denomination, genre,
                presentation, promotion, formation);
        
        AstrologueDao astrologueDao = new AstrologueDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            astrologueDao.creer(astrologue);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            astrologue = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return astrologue;
    }
    
    public Medium rechercherMediumParDenom(String _denomination) {
        Medium medium;
        MediumDao MediumDao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            medium = MediumDao.chercherParDenomination(_denomination);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            medium = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return medium;
    }
    
    public List<Medium> recupererTousMedium() {
        List<Medium> mediums = new ArrayList<Medium>();
        MediumDao MediumDao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediums = MediumDao.chercherTous();
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            mediums = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return mediums;
    }
    
    public Medium rechercherMediumParId(long id) {
        Medium medium;
        MediumDao MediumDao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            medium = MediumDao.chercherParId(id);
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
            medium = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return medium;
    }
    
    public List<String> genererPredictions(String couleur, String animal,
            int amour, int sante, int travail) throws IOException {
        List<String> predictions = new ArrayList<>();
        AstroNetApi pred_api = new AstroNetApi();
        try {
            predictions = pred_api.getPredictions(couleur, animal, amour, sante, travail);
        } catch (Exception e) {
            // silence is golden
        }
        return predictions;
    }
    
    public Map<Medium, Integer> statistiquesMediumsConsultations() {
        
        MediumDao mediumDao = new MediumDao();
        List<Medium> mediums = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediums = mediumDao.chercherTous();
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        Map<Medium, Integer> statsMediums = new HashMap<Medium, Integer>();
        for (Medium med: mediums) {
            statsMediums.put(med, med.getConsultations().size());
        }
        return statsMediums;
    }
    
    public List<Medium> statistiquesTopMediums() {
        
        MediumDao mediumDao = new MediumDao();
        List<Medium> mediums = null;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            mediums = mediumDao.chercherTous();
            JpaUtil.validerTransaction();
            Logger.getAnonymousLogger().log(Level.INFO, "succès");
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Erreur !");
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        
        mediums.sort(new Comparator<Medium>(){
            @Override
            public int compare(Medium m1, Medium m2)
            {
                if (m1.getConsultations().size() == m2.getConsultations().size()) {
                    return 0;
                }
                return m1.getConsultations().size() > m2.getConsultations().size() ? -1 : 1;
            }
        });
        
        // maximum 5 mediums in list
        if (mediums.size() > 5) {
            mediums = mediums.subList(0, 5);
        }
        
        return mediums;
        
    }
    
    public Map<String,Integer> calculerRepartitionClientsParEmployes()
    {
        Map<String,Integer> statistiques = new HashMap<String,Integer>();
        EmployeDao employeDao = new EmployeDao();
        try
        {
            JpaUtil.creerContextePersistance();
            statistiques = employeDao.tousAvecNombreClientsDifferent();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        
        return statistiques;
    }

    
}
