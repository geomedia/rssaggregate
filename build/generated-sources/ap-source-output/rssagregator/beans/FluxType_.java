package rssagregator.beans;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Flux;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(FluxType.class)
public class FluxType_ { 

    public static volatile SingularAttribute<FluxType, String> description;
    public static volatile SingularAttribute<FluxType, Long> ID;
    public static volatile ListAttribute<FluxType, Flux> fluxLie;
    public static volatile SingularAttribute<FluxType, String> codeType;
    public static volatile SingularAttribute<FluxType, String> denomination;
    public static volatile SingularAttribute<FluxType, Timestamp> modified;

}