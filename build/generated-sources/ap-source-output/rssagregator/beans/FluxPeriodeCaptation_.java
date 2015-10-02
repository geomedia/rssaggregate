package rssagregator.beans;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;
import rssagregator.beans.traitement.ComportementCollecte;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-10-02T15:09:46")
@StaticMetamodel(FluxPeriodeCaptation.class)
public class FluxPeriodeCaptation_ { 

    public static volatile SingularAttribute<FluxPeriodeCaptation, Flux> flux;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedJeudi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedVendredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statMax;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeSamedi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeVendredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoySamedi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyVendredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedMardi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyDimanche;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedSamedi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartType;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Long> ID;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyMercredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Date> datefin;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeMercredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, ComportementCollecte> comportementDurantLaPeriode;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statMedian;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyLundi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedDimanche;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statQuartilePremier;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeLundi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedLundi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Timestamp> modified;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Date> dateDebut;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statDecilePremier;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> indiceQualiteCaptation;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Long> statSommeItemCapture;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statDecileNeuf;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMedMercredi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statQuartileTrois;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeJeudi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeMardi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyenne;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyJeudi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Integer> statMin;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Float> statMoyMardi;
    public static volatile SingularAttribute<FluxPeriodeCaptation, Double> statEcartTypeDimanche;

}