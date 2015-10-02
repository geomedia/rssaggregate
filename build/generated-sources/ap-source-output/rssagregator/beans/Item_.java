package rssagregator.beans;

import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import rssagregator.beans.DoublonDe;
import rssagregator.beans.Flux;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2015-10-02T15:09:46")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, String> hashContenu;
    public static volatile SingularAttribute<Item, String> link;
    public static volatile SingularAttribute<Item, Date> datePub;
    public static volatile ListAttribute<Item, Flux> listFlux;
    public static volatile SingularAttribute<Item, String> titre;
    public static volatile SingularAttribute<Item, Boolean> imported;
    public static volatile SingularAttribute<Item, Timestamp> modified;
    public static volatile SingularAttribute<Item, String> guid;
    public static volatile SingularAttribute<Item, String> contenu;
    public static volatile SingularAttribute<Item, String> categorie;
    public static volatile SingularAttribute<Item, String> description;
    public static volatile SingularAttribute<Item, Long> ID;
    public static volatile ListAttribute<Item, DoublonDe> doublon;
    public static volatile SingularAttribute<Item, Date> dateRecup;

}