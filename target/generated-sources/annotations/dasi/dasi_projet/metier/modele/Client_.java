package dasi.dasi_projet.metier.modele;

import dasi.dasi_projet.metier.modele.Consultation;
import dasi.dasi_projet.metier.modele.ProfilAstral;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-05-04T21:57:27")
@StaticMetamodel(Client.class)
public class Client_ extends Utilisateur_ {

    public static volatile SingularAttribute<Client, ProfilAstral> profil_astral;
    public static volatile SingularAttribute<Client, String> adresse;
    public static volatile ListAttribute<Client, Consultation> consultations;
    public static volatile SingularAttribute<Client, Date> date_naissance;

}