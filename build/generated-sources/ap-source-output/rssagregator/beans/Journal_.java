package rssagregator.beans;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;
import rssagregator.beans.traitement.ComportementCollecte;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(Journal.class)
public class Journal_ { 

    public static volatile SingularAttribute<Journal, String> information;
    public static volatile SingularAttribute<Journal, Boolean> autoUpdateFlux;
    public static volatile SingularAttribute<Journal, Integer> periodiciteDecouverte;
    public static volatile SingularAttribute<Journal, String> urlHtmlRecapFlux;
    public static volatile SingularAttribute<Journal, Boolean> activerFluxDecouvert;
    public static volatile ListAttribute<Journal, Flux> fluxLie;
    public static volatile SingularAttribute<Journal, String> codeVille;
    public static volatile SingularAttribute<Journal, String> langue;
    public static volatile SingularAttribute<Journal, String> nom;
    public static volatile SingularAttribute<Journal, String> fuseauHorraire;
    public static volatile SingularAttribute<Journal, String> urlAccueil;
    public static volatile SingularAttribute<Journal, Timestamp> modified;
    public static volatile SingularAttribute<Journal, ComportementCollecte> comportementParDefaultDesFlux;
    public static volatile SingularAttribute<Journal, Long> ID;
    public static volatile SingularAttribute<Journal, String> codeJournal;
    public static volatile SingularAttribute<Journal, String> pays;
    public static volatile SingularAttribute<Journal, String> typeJournal;

}