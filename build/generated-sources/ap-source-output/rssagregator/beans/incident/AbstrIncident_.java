package rssagregator.beans.incident;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(AbstrIncident.class)
public abstract class AbstrIncident_ { 

    public static volatile SingularAttribute<AbstrIncident, String> messageEreur;
    public static volatile SingularAttribute<AbstrIncident, Integer> nombreTentativeEnEchec;
    public static volatile SingularAttribute<AbstrIncident, Date> lastNotification;
    public static volatile SingularAttribute<AbstrIncident, String> logErreur;
    public static volatile SingularAttribute<AbstrIncident, Date> dateFin;
    public static volatile SingularAttribute<AbstrIncident, Long> ID;
    public static volatile SingularAttribute<AbstrIncident, String> noteIndicent;
    public static volatile SingularAttribute<AbstrIncident, Date> dateDebut;
    public static volatile SingularAttribute<AbstrIncident, Timestamp> modified;

}