package rssagregator.beans.incident;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;
import rssagregator.beans.Journal;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(NotificationAjoutFlux.class)
public class NotificationAjoutFlux_ extends AbstrIncident_ {

    public static volatile SingularAttribute<NotificationAjoutFlux, Journal> journal;
    public static volatile ListAttribute<NotificationAjoutFlux, Flux> fluxAjoute;

}