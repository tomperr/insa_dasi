import dasi.dasi_projet.dao.ConsultationDao;
import dasi.dasi_projet.dao.JpaUtil;
import dasi.dasi_projet.ihm.console.Saisie;
import dasi.dasi_projet.metier.modele.Astrologue;
import dasi.dasi_projet.metier.modele.Cartomancien;
import dasi.dasi_projet.metier.modele.Client;
import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.Employe;
import dasi.dasi_projet.metier.modele.Medium;
import dasi.dasi_projet.metier.modele.ProfilAstral;
import dasi.dasi_projet.metier.modele.Spirite;
import dasi.dasi_projet.metier.modele.Utilisateur;
import dasi.dasi_projet.metier.service.Service;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tperrillat
 */
public class Main {
    
    public static void displayMenu() {
        System.out.println("0 - Quitter");
        System.out.println("1 - Inscrire client");
        System.out.println("2 - Authentification");
        System.out.println("3 - Créer consultation");
        System.out.println("4 - Commencer consultation");
        System.out.println("5 - Terminer consultation");
        System.out.println("6 - Rechercher client par id");
        System.out.println("7 - Rechercher medium par id");
        System.out.println("8 - Rechercher consultation par id");
        System.out.println("9 - Recuperer tous les mediums");
        System.out.println("10 - Generer predictions");
        System.out.println("11 - Generer stats consultations des mediums");
        System.out.println("12 - Generer stats top 5 mediums");
    }
    
    public static void testerInscriptionClient() throws ParseException, IOException {
        
        /*
        String first_name = Saisie.lireChaine("Prenom : ");
        String last_name = Saisie.lireChaine("Nom : ");
        char gender = Saisie.lireChaine("Genre : ").charAt(0);
        String email = Saisie.lireChaine("Mail : ");
        String pass = Saisie.lireChaine("Mot de passe : ");
        String phone = Saisie.lireChaine("Numero de telephone : ");
        String birth = Saisie.lireChaine("Date de naissance : ");
        String address = Saisie.lireChaine("Adresse : ");
        
        String zodiac = Saisie.lireChaine("Signe du zodiaque : ");
        String chinese = Saisie.lireChaine("Signe chinois : ");
        String color = Saisie.lireChaine("Couleur : ");
        String animal = Saisie.lireChaine("Animal : ");
        */
                
        String first_name = "Tom";
        String last_name = "Perr";
        char gender = 'M';
        String email = "tom.perrillat-collomb@insa-lyon.fr";
        String pass = "password1234";
        String phone = "0642686868";
        String birth = "08/03/2000";
        String address = "33c rue de l'INSA";
        
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = sf.parse(birth);
                
        Service service = new Service();
        
        Client client = service.inscrireClient(first_name, last_name,
                gender, email, pass, phone, birth, address); 
    
        if (client == null) {
            System.out.println("> Echec création");
        } else {
            System.out.println("> Succès création");
            System.out.println(client);
        }
    }
    
    public static void testerAuthentification() {
        String mail = Saisie.lireChaine("Mail : ");
        String pass = Saisie.lireChaine("Mot de passe : ");
        Service service = new Service();
        Utilisateur utilisateur = service.authentifierUtilisateur(
                mail, pass);
        
        if (utilisateur == null) {
            System.out.println("> Echec authentification");
        } else {
            System.out.println("> Succès authentification");
            System.out.println(utilisateur);
        }
    }
    
    public static void testerInscriptionEmploye() throws ParseException {
        
        String first_name = "Bruce";
        String last_name = "WAYNE";
        char gender = 'M';
        String email = "bruce.wayne@insa-lyon.fr";
        String pass = "password";
        String phone = "0642785689";
        
        Service service = new Service();
        
        Employe emp = service.inscrireEmploye(last_name, first_name, gender, email, pass,
                phone, true);
        if (emp == null) {
            System.out.println("> Echec création");
        } else {
            System.out.println("> Succès création");
            System.out.println(emp);
        }
    }
    
    public static void testerInscriptionMedium() {
        
        String denomination = "Professeur Waraba";
        char genre = 'M';
        String presentation = "Résoud tous les problèmes, garantie en 48h";
        String support = "Boule de cristal";
        int promotion = 2011;
        String formation = "DUT Arnaqueur";
        
        int option = 0;
        Service service = new Service();
        
        while (option <= 0 || option > 3) {
            System.out.println("1 - Sprite");
            System.out.println("2 - Cartomancien");
            System.out.println("3 - Astrologue");
            option = Saisie.lireInteger("Choix : ");
            switch (option) {
                case 0: break;
                case 1:
                    Spirite spirite = service.inscrireSpirite(denomination, genre,
                            presentation, support);
                    if (spirite == null) {
                        System.out.println("> Echec création");
                    } else {
                        System.out.println("> Succès création");
                        System.out.println(spirite);
                    }
                    break;
                case 2:
                    Cartomancien cartomancien = service.inscrireCartomancien(denomination,
                            genre, presentation);
                    if (cartomancien == null) {
                        System.out.println("> Echec création");
                    } else {
                        System.out.println("> Succès création");
                        System.out.println(cartomancien);
                    }
                    break;
                case 3:

                    Astrologue astrologue = service.inscrireAstrologue(denomination,
                            genre, presentation, promotion, formation);
                    if (astrologue == null) {
                        System.out.println("> Echec création");
                    } else {
                        System.out.println("> Succès création");
                        System.out.println(astrologue);
                    }
                    break;
                default:
                    System.out.println("Erreur de saisie :(");
                    break;
            }
        }
        System.out.println("Au revoir ! :)");
    }
    
    public static void testerCreationConsultation() {
        
        // getting an client
        Service service = new Service();
        Client client = (Client)service.authentifierUtilisateur("tom.perr@insa-lyon.fr", "password_hard");
        
        Medium medium = service.rechercherMediumParDenom("Laurine Pesquet");
               
        Consultation consultation_return = service.demanderConsultation(client, medium);
        if (consultation_return == null) {
            System.out.println("> Echec création");
        } else {
            System.out.println("> Succès création");
            System.out.println(consultation_return);
        }
        
    }
    
    public static void testerCommencerConsultation() {
        long id_consultation = (long)Saisie.lireInteger("Id de la consultation : ");
        Service service = new Service();
        Consultation consultation = service.rechercherConsultation(id_consultation);
        service.commencerConsultation(consultation);
    }
    
    public static void testerTerminerConsultation() {
        long id_consultation = (long)Saisie.lireInteger("Id de la consultation : ");
        Service service = new Service();
        Consultation consultation = service.rechercherConsultation(id_consultation);
        String commentaire = "C'etait super !";
        service.terminerConsultation(consultation, commentaire);
    }
    
    public static void testerRechercherMedium() {
        long id_medium = (long)Saisie.lireInteger("Id du medium : ");
        Service service = new Service();
        Medium medium = service.rechercherMediumParId(id_medium);
        System.out.println(medium);
    }
    
    public static void testerRecupererTousMedium() {
        Service service = new Service();
        List<Medium> mediums = service.recupererTousMedium();
        for (Medium m : mediums) {
            System.out.println(m);
        }
    }
    
    public static void testerRechercherClient() {
        long id_client = (long)Saisie.lireInteger("Id du client : ");
        Service service = new Service();
        Client client = service.rechercherClient(id_client);
        System.out.println(client);
    }
    
    public static void testerRechercherConsultation() {
        long id_consultation = (long)Saisie.lireInteger("Id de la consultation : ");
        Service service = new Service();
        Consultation consultation = service.rechercherConsultation(id_consultation);
        System.out.println(consultation);
    }
    
    public static void testerGenererPredictions() throws IOException{
        Service service = new Service();
        Client client = (Client)service.authentifierUtilisateur("tom.perr@insa-lyon.fr", "password_hard");
        ProfilAstral pf = client.getProfil_astral();
        List<String> predictions = service.genererPredictions(pf.getCouleur(), pf.getAnimal(),
            1, 2, 1);
        System.out.println(predictions);
    }
    
    public static void testerGenererStatsConsultationsMediums() {
        Service service = new Service();
        Map<Medium, Integer> statsMediums = service.statistiquesMediumsConsultations();
        System.out.println(statsMediums);
    }
    
    public static void testersGenererStatsTopMediums() {
        Service service = new Service();
        List<Medium> statsMediums = service.statistiquesTopMediums();
        
        int count = 1;
        for (Medium med: statsMediums) {
            System.out.println(count + " :");
            System.out.println(med + "\n");
            count++;
        }
    }
    
    public static void main(String[] args) throws ParseException, IOException {
        JpaUtil.init();
        
        // init data
        Service service = new Service();
        //service.initialisationDonneesEmployes();
        //service.initialisationDonneesClients();
        
        service.initDataPresentation();
        
        int option = -1;
        while (option != 0) {
            displayMenu();
            option = Saisie.lireInteger("Choix : ");
            switch (option) {
                case 0: break;
                case 1:
                    testerInscriptionClient();
                    break;
                case 2:
                    testerAuthentification();
                    break;
                case 3:
                    testerCreationConsultation();
                    break;
                case 4:
                    testerCommencerConsultation();
                    break;
                case 5:
                    testerTerminerConsultation();
                    break;
                case 6:
                    testerRechercherClient();
                    break;
                case 7:
                    testerRechercherMedium();
                    break;
                case 8:
                    testerRechercherConsultation();
                    break;
                case 9:
                    testerRecupererTousMedium();
                    break;
                case 10:
                    testerGenererPredictions();
                    break;
                case 11:
                    testerGenererStatsConsultationsMediums();
                    break;
                case 12:
                    testersGenererStatsTopMediums();
                    break;
                default:
                    System.out.println("Erreur de saisie :(");
                    break;
            }
        }
        System.out.println("Au revoir ! :)");
        JpaUtil.destroy();
    }
    
}
