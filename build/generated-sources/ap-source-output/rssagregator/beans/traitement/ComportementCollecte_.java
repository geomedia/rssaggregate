package rssagregator.beans.traitement;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;
import rssagregator.beans.traitement.AbstrDedoublonneur;
import rssagregator.beans.traitement.AbstrParseur;
import rssagregator.beans.traitement.AbstrRaffineur;
import rssagregator.beans.traitement.AbstrRequesteur;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-10-02T15:09:46")
@StaticMetamodel(ComportementCollecte.class)
public class ComportementCollecte_ { 

    public static volatile SingularAttribute<ComportementCollecte, AbstrDedoublonneur> dedoubloneur;
    public static volatile SingularAttribute<ComportementCollecte, Integer> periodiciteCollecte;
    public static volatile ListAttribute<ComportementCollecte, Flux> listeFlux;
    public static volatile SingularAttribute<ComportementCollecte, String> description;
    public static volatile SingularAttribute<ComportementCollecte, AbstrParseur> parseur;
    public static volatile SingularAttribute<ComportementCollecte, Timestamp> dateUpdate;
    public static volatile SingularAttribute<ComportementCollecte, Long> ID;
    public static volatile ListAttribute<ComportementCollecte, AbstrRaffineur> raffineur;
    public static volatile SingularAttribute<ComportementCollecte, AbstrRequesteur> requesteur;
    public static volatile SingularAttribute<ComportementCollecte, String> nom;

}