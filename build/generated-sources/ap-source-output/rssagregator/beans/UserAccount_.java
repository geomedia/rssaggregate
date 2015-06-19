package rssagregator.beans;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-03-25T09:53:38")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile SingularAttribute<UserAccount, String> mail;
    public static volatile SingularAttribute<UserAccount, String> username;
    public static volatile SingularAttribute<UserAccount, Boolean> adminMail;
    public static volatile SingularAttribute<UserAccount, Boolean> rootAccount;
    public static volatile SingularAttribute<UserAccount, Boolean> adminstatut;
    public static volatile SingularAttribute<UserAccount, Long> ID;
    public static volatile SingularAttribute<UserAccount, String> pass;
    public static volatile SingularAttribute<UserAccount, Timestamp> modified;

}