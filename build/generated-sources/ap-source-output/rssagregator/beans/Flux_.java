package rssagregator.beans;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;
import rssagregator.beans.FluxPeriodeCaptation;
import rssagregator.beans.FluxType;
import rssagregator.beans.Item;
import rssagregator.beans.Journal;
import rssagregator.beans.incident.CollecteIncident;
import rssagregator.beans.traitement.ComportementCollecte;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-10-02T15:09:46")
@StaticMetamodel(Flux.class)
public class Flux_ { 

    public static volatile SingularAttribute<Flux, FluxType> typeFlux;
    public static volatile SingularAttribute<Flux, Boolean> estStable;
    public static volatile SingularAttribute<Flux, Flux> parentFlux;
    public static volatile SingularAttribute<Flux, String> nom;
    public static volatile SingularAttribute<Flux, String> url;
    public static volatile SingularAttribute<Flux, Timestamp> modified;
    public static volatile SingularAttribute<Flux, String> htmlUrl;
    public static volatile SingularAttribute<Flux, Journal> journalLie;
    public static volatile SingularAttribute<Flux, Date> created;
    public static volatile ListAttribute<Flux, FluxPeriodeCaptation> periodeCaptations;
    public static volatile ListAttribute<Flux, Item> item;
    public static volatile SingularAttribute<Flux, Long> ID;
    public static volatile SingularAttribute<Flux, Boolean> active;
    public static volatile SingularAttribute<Flux, String> infoCollecte;
    public static volatile SingularAttribute<Flux, ComportementCollecte> mediatorFlux;
    public static volatile ListAttribute<Flux, CollecteIncident> incidentsLie;

}