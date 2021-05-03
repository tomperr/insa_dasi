package dasi.dasi_projet.metier.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author DASI Team
 *
 */

public class AstroNetApi {


    /* Méthode de test pour l'utilisation de l'API */

    public static void main(String[] args) throws ParseException, IOException {

        AstroNetApi astroApi = new AstroNetApi();

        // Pratique pour construire (et afficher) des objets Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        String prenom = "Ada";
        Date dateNaissance = simpleDateFormat.parse("10/12/1815");
        
        System.out.println("");
        System.out.println("~<[ Personne ]>~");
        System.out.println("Prénom: " + prenom);
        System.out.println("Date de Naissance: " + simpleDateFormat.format(dateNaissance));
        System.out.println("");

        List<String> profil = astroApi.getProfil(prenom, dateNaissance);

        String signeZodiaque = profil.get(0);
        String signeChinois = profil.get(1);
        String couleur = profil.get(2);
        String animal = profil.get(3);

        System.out.println("");
        System.out.println("~<[ Profil ]>~");
        System.out.println("[Profil] Signe du Zodiaque: " + signeZodiaque);
        System.out.println("[Profil] Signe Chinois: " + signeChinois);
        System.out.println("[Profil] Couleur porte-bonheur: " + couleur);
        System.out.println("[Profil] Animal-totem: " + animal);
        System.out.println("");

        // Niveaux entre 1 (mauvais) et 4 (excellent)
        int niveauAmour = 4;
        int niveauSante = 1;
        int niveauTravail = 2;

        List<String> predictions = astroApi.getPredictions(couleur, animal, niveauAmour, niveauSante, niveauTravail);

        System.out.println("");
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + predictions.get(0));
        System.out.println("[ Santé ] " + predictions.get(1));
        System.out.println("[Travail] " + predictions.get(2));
        System.out.println("");

        // Niveaux entre 1 (mauvais) et 4 (excellent)
        niveauAmour = 1;
        niveauSante = 3;
        niveauTravail = 4;

        predictions = astroApi.getPredictions(couleur, animal, niveauAmour, niveauSante, niveauTravail);

        System.out.println("");
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + predictions.get(0));
        System.out.println("[ Santé ] " + predictions.get(1));
        System.out.println("[Travail] " + predictions.get(2));
        System.out.println("");
    }

    
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final String ASTRO_API_URL = "https://servif-cocktail.insa-lyon.fr/WebDataGenerator/Astro";
    public static final String ASTRO_API_KEY = "ASTRO-01-M0lGLURBU0ktQVNUUk8tQjAx";

    /*
     * Constructeur
     */
    public AstroNetApi() {
    }

    /*
     * Méthode pour appeler le Service Web Profil
     */
    public List<String> getProfil(String prenom, Date dateNaissance) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        JsonObject response = this.post(ASTRO_API_URL,
                new BasicNameValuePair("service", "profil"),
                new BasicNameValuePair("key", ASTRO_API_KEY),
                new BasicNameValuePair("prenom", prenom),
                new BasicNameValuePair("date-naissance", JSON_DATE_FORMAT.format(dateNaissance))
        );

        JsonObject profil = response.get("profil").getAsJsonObject();

        result.add(profil.get("signe-zodiaque").getAsString());
        result.add(profil.get("signe-chinois").getAsString());
        result.add(profil.get("couleur").getAsString());
        result.add(profil.get("animal").getAsString());

        return result;
    }

    /*
     * Méthode pour appeler le Service Web Prédictions
     */
    public List<String> getPredictions(String couleur, String animal, int amour, int sante, int travail) throws IOException {

        ArrayList<String> result = new ArrayList<>();

        JsonObject response = this.post(ASTRO_API_URL,
                new BasicNameValuePair("service", "predictions"),
                new BasicNameValuePair("key", ASTRO_API_KEY),
                new BasicNameValuePair("couleur", couleur),
                new BasicNameValuePair("animal", animal),
                new BasicNameValuePair("niveau-amour", Integer.toString(amour)),
                new BasicNameValuePair("niveau-sante", Integer.toString(sante)),
                new BasicNameValuePair("niveau-travail", Integer.toString(travail))
        );

        //System.out.println(response.get("profil-valide").getAsBoolean());
        result.add(response.get("prediction-amour").getAsString());
        result.add(response.get("prediction-sante").getAsString());
        result.add(response.get("prediction-travail").getAsString());

        return result;
    }

    /*
     * Méthode interne pour réaliser un appel HTTP et interpréter le résultat comme Objet JSON
     */
    protected JsonObject post(String url, NameValuePair... parameters) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();

        JsonElement responseElement = null;

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(parameters), ENCODING_UTF8));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                JsonReader jsonReader = new JsonReader(new InputStreamReader(entity.getContent(), ENCODING_UTF8));
                try {

                    JsonParser parser = new JsonParser();
                    responseElement = parser.parse(jsonReader);

                } finally {
                    jsonReader.close();
                }
            }

        } finally {
            response.close();
        }

        httpclient.close();

        JsonObject responseContainer = null;
        try {
            if (responseElement != null) {
                responseContainer = responseElement.getAsJsonObject();
            }
        } catch (IllegalStateException ex) {
            throw new IOException("Wrong HTTP Response Format - not a JSON Object (bad request?)", ex);
        }

        return responseContainer;
    }

}
