package rssagregator.beans;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.Item;
import rssagregator.beans.traitement.AbstrRaffineur;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(DoublonDe.class)
public class DoublonDe_ { 

    public static volatile SingularAttribute<DoublonDe, Item> itemDoublon;
    public static volatile SingularAttribute<DoublonDe, AbstrRaffineur> raffineurEmploye;
    public static volatile SingularAttribute<DoublonDe, Item> itemRef;
    public static volatile SingularAttribute<DoublonDe, Long> ID;

}