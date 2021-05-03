package dasi.dasi_projet.metier.modele;

import dasi.dasi_projet.metier.modele.Consultation;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2021-04-28T17:42:58")
@StaticMetamodel(Employe.class)
public class Employe_ extends Utilisateur_ {

    public static volatile ListAttribute<Employe, Consultation> consultations;
    public static volatile SingularAttribute<Employe, Boolean> disponible;

}