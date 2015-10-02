package rssagregator.beans.incident;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.incident.PeriodeAnormale;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-10-02T15:09:46")
@StaticMetamodel(AnomalieCollecte.class)
public class AnomalieCollecte_ extends CollecteIncident_ {

    public static volatile ListAttribute<AnomalieCollecte, PeriodeAnormale> periodeAnormale;
    public static volatile SingularAttribute<AnomalieCollecte, Boolean> causeTechniqueSiteJournal;
    public static volatile SingularAttribute<AnomalieCollecte, Boolean> causeChangementLigneEditoriale;

}