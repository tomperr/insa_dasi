package dasi.dasi_projet.ihm.console;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DASI Team
 */
public class Saisie {

    public static String lireChaine(String invite) {
        String chaineLue = null;
        System.out.print(invite);
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            chaineLue = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
        return chaineLue;
    }
    
    /*
    public static String lireMotdepasse(String invite) {
        String chaineLue = null;
        Console console = System.console();
        while (chaineLue == null) {
            try {
                chaineLue = new String(console.readPassword(invite));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return chaineLue;
    }
*/

    public static Integer lireInteger(String invite) {
        Integer valeurLue = null;
        while (valeurLue == null) {
            try {
                valeurLue = Integer.parseInt(lireChaine(invite));
            } catch (NumberFormatException ex) {
                System.out.println("/!\\ Erreur de saisie - Nombre entier attendu /!\\");
            }
        }
        return valeurLue;
    }

    public static Integer lireInteger(String invite, List<Integer> valeursPossibles) {
        Integer valeurLue = null;
        while (valeurLue == null) {
            try {
                valeurLue = Integer.parseInt(lireChaine(invite));
            } catch (NumberFormatException ex) {
                System.out.println("/!\\ Erreur de saisie - Nombre entier attendu /!\\");
            }
            if (!(valeursPossibles.contains(valeurLue))) {
                System.out.println("/!\\ Erreur de saisie - Valeur non-autorisée /!\\");
                valeurLue = null;
            }
        }
        return valeurLue;
    }
    
    public static void pause() {
        lireChaine("--PAUSE--");
    }

    public static void main(String[] args) {
        
        System.out.println("Bonjour !");
        
        String nom = Saisie.lireChaine("Entrez votre nom: ");
        System.out.println("Bonjour, " + nom + " !");
        
        Integer age = Saisie.lireInteger("Entrez votre âge: ");
        System.out.println("Vous avez " + age + " ans.");
        
        Integer annee = Saisie.lireInteger("Entrez votre année au Département IF (3,4,5): ", Arrays.asList(3,4,5));
        System.out.println("Vous êtes en " + annee + "IF.");

        Saisie.pause();
        
        System.out.println("Au revoir !");
    }
}
