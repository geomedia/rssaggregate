package rssaggregator.beans;

import com.sun.syndication.feed.opml.Attribute;
import com.sun.syndication.feed.opml.Opml;
import com.sun.syndication.feed.opml.Outline;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.poi.util.Beta;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import rssaggregator.beans.exception.DonneeInterneCoherente;
import rssaggregator.beans.incident.AbstrIncident;
import rssaggregator.beans.incident.CollecteIncident;
import rssaggregator.beans.traitement.ComportementVisitor;
import rssaggregator.beans.traitement.ComportementCollecte;
import rssaggregator.dao.DAOFactory;
import rssaggregator.dao.DaoFlux;
//import rssaggregator.services.TacheVerifComportementFLux;

/**
 * Une des entités les plus importantes... Il s'agit d'un flux de syndication (RSS ATOM...). Un flux appartient est lié
 * aux entitées suivante : <ul>
 * <li>{@link Journal} : Un flux peut appartenir à 0 à 1 journal</li>
 * <li>{@link FluxType} : Un flux peut appartenir à 0 à 1 type de flux (international, à la une ...)</li>
 * <li>{@link ComportementCollecte} : Une flux possède un comportement de collecte permettant de collecter les
 * {@link Item}</li>
 * <li>{@link Item} : Un flux possède 0 à N item. </li>
 * <li>{@link CollecteIncident} : un flux peut posséder 0 à N incidents de Collecte.</li>
 * </ul>
 * @author Clément RILLON
 */
@Entity()
@Table(name = "flux")
@XmlRootElement
public class Flux extends Bean implements Observer, Serializable, Cloneable {
    public static final String PROP_ACTIVE = "active";
    
    
    public static final String PROP_MEDIATORFLUX = "mediatorFlux";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    
    /**
     * *
     * Le beans flux possède un ChangeLisner {@link FluxChangeLisner} chargé d'effectuer des modification dans le beans
     * lors de l'activation ou de la désactivation. Ce changeLisner est enregistré auprès de ce PropertyChangeSupport
     */
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    /**
     * URL du flux rss sous la forme http://url/rep.
     */
    @Column(name = "url", length = 2000, nullable = false, unique = true)
    private String url;
    /**
     * Permet de déterminer si le flux doit être collecté ou non
     */
//    protected Boolean active;
    @Column(name = "active")
    private Boolean active;
    /**
     * L'url de la rubrique du flux, il s'agit de la page HTML d'entrée de la rubrique. Cette adresse peut être utilisé
     * pour faire de l'auto discovery.
     */
    @Column(name = "htmlUrl", length = 2000, nullable = true)
    private String htmlUrl;
    
    
    /**
     *
     * Liste des Item du flux. La relation est possédée par l'item !! Il faut passer par la DAO pour obtenir la liste
     * des items. Item
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "listFlux")
    private List<Item> item = new ArrayList<Item>();
    
    /**
     * Un objet flux peut posséder différents incidents. Un incident ne possède qu'un flux.
     *
     * @element-type CollecteIncident
     */
    @OneToMany(mappedBy = "fluxLie", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
    protected List<CollecteIncident> incidentsLie;

    /**
     * *
     * Un champs informatif dans lequel les administrateurs peuvent saisir des commentaires sur le flux.
     */
    @Column(name = "infoCollecte", columnDefinition = "text")
    private String infoCollecte;
    
    /**
     * Le type du flux (international, a la une etc...). Les types de flux sont des beans. ils sont persisté dans la
     * base de données
     */
    @OneToOne(fetch = FetchType.EAGER)
    private FluxType typeFlux;
    /**
     * Un flux peut appratenir à un journal. Un journal peut contenir plusieurs flux
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Journal journalLie;
    
    /**
     * Le mediator flux permet d'assigner un flux un comportement de collecte. Un médiator est une configuration de
     * parseur Raffineur etc. IL s4AGIT DU COMPTEMENT DE COLLECTE ACTUELLE. Si auparavant d'autres comportement ont été
     * utilisé, il sont visibles dans les période de captations.
     */
    @ManyToOne()
    private ComportementCollecte mediatorFlux;
    
    /**
     * *
     * Date de création du flux
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date created;

    /***
     * Date de dernière modification de l'entitée. Permet de faire fonctionner l'optimitic looking de JPA
     */
    @Version
    Timestamp modified;
    
    /**
     * *
     * Un flux peut être le sous flux d'un autre, exemple Europe est un sous flux de international. Si null, il s'agit
     * d'un flux racine
     */
    @OneToOne
    @Beta
    Flux parentFlux;
    
    
    /**
     * *
     * Un nom pour le flux. Ce champ est utilisé par la méthode toString. Si ce champ est vide tostring va chercher le
     * journal et le type de flux. Sinon il va montrer l'url
     */
    @Column(name = "nom")
    private String nom;
 
    
    /**
     * *
     * Liste des périodes durant lesquel un flux à été capturé (boolean active à true). Les période de captation sont
     * des entitées persisté dans la base de données possédant deux dates. Durant sa période de captation, un flux peut
     * être en échec (posséder des incident {@link CollecteIncident}
     */
    @OneToMany(mappedBy = "flux", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<FluxPeriodeCaptation> periodeCaptations = new ArrayList<FluxPeriodeCaptation>();
    
    
    /**
     * *
     * Variable qui permet à l'utilisateur de qualifié le flux de stable. On considère qu'il est stable si le flux ne
     * subit pas trop d'anomalie et qu'il renvoie un nombre d'item assez régulier. Les flux qualifié de stable son sujet
     * a être vérifier par les tache de verification de comportement ({@link TacheVerifComportementFLux). On peut qualifier un flux de non stable pour éviter les
     * notification abusive. Un flux non stable continu tout de même à être
     * revevé
     */
    protected Boolean estStable;


    public Flux() {
        propertyChangeSupport = new PropertyChangeSupport(this);
        FluxChangeLisner changeLisner = new FluxChangeLisner();
        propertyChangeSupport.addPropertyChangeListener(changeLisner);
        incidentsLie = new ArrayList<CollecteIncident>();

    }

    public Flux(String url) {
        this();
        this.url = url;
    }

    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Get the value of active
     *
     * @return the value of active
     */
        public Boolean getActive() {
        return active;
    }

    /**
     * Set the value of active
     *
     * @param active new value of active
     */
    public void setActive(Boolean active) {
        Boolean oldActive = this.active;
        this.active = active;
        propertyChangeSupport.firePropertyChange(PROP_ACTIVE, oldActive, active);
    }

    /**
     * Get the value of mediatorFlux
     *
     * @return the value of mediatorFlux
     */
    public ComportementCollecte getMediatorFlux() {
        return mediatorFlux;
    }

    /**
     * Set the value of mediatorFlux
     *
     * @param mediatorFlux new value of mediatorFlux
     */
    public void setMediatorFlux(ComportementCollecte mediatorFlux) {
        ComportementCollecte oldMediatorFlux = this.mediatorFlux;
        this.mediatorFlux = mediatorFlux;
        propertyChangeSupport.firePropertyChange(PROP_MEDIATORFLUX, oldMediatorFlux, mediatorFlux);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlRubrique() {
        return htmlUrl;
    }

    public void setUrlRubrique(String urlRubrique) {
        this.htmlUrl = urlRubrique;
    }

    @XmlTransient
    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> items) {
        this.item = items;
    }

        public FluxType getTypeFlux() {
        return typeFlux;
    }

    public void setTypeFlux(FluxType typeFlux) {
        this.typeFlux = typeFlux;
    }

    public Journal getJournalLie() {
        return journalLie;
    }

    public void setJournalLie(Journal journalLie) {
        this.journalLie = journalLie;
    }

    public Flux getParentFlux() {
        return parentFlux;
    }

        public void setParentFlux(Flux parentFlux) {
        this.parentFlux = parentFlux;
    }

        public String getNom() {
        return nom;
    }

        public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public List<FluxPeriodeCaptation> getPeriodeCaptations() {
        return periodeCaptations;
    }

    public String getInfoCollecte() {
        return infoCollecte;
    }


    public void setInfoCollecte(String infoCollecte) {
        this.infoCollecte = infoCollecte;
    }

    public void setPeriodeCaptations(List<FluxPeriodeCaptation> periodeCaptations) {
        this.periodeCaptations = periodeCaptations;
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getID() {
        return ID;
    }

    @Override
    public void setID(Long ID) {
        this.ID = ID;
    }

    @XmlTransient
    public List<CollecteIncident> getIncidentsLie() {
        return incidentsLie;
    }

    public void setIncidentsLie(List<CollecteIncident> incidentsLie) {
        this.incidentsLie = incidentsLie;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Date getCreated() {
        return created;
    }    /**
     * *
     * Retourne le nom du journal ainsi que le type du flux. Si ces variables ne sont pas définient, on retourne l'url.
     */
    public void setCreated(Date created) {
        this.created = created;
    }

        public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

        public Boolean getEstStable() {
        return estStable;
    }

        public void setEstStable(Boolean estStable) {
        this.estStable = estStable;
    }

    /**
     * *
     * Parcours les incidents et retourne ceux qui ne sont pas clos, cad ceux qui n'ont pas de date de fin
     *
     * @return    /**
     * renvoie toujours true
     */
    public List<CollecteIncident> getIncidentEnCours() {
        List<CollecteIncident> incid = getIncidentsLie();
        List<CollecteIncident> retour = new ArrayList<CollecteIncident>();

        for (int i = 0; i < incid.size(); i++) {
            CollecteIncident fluxIncident = incid.get(i);
            if (fluxIncident.getDateFin() == null) {
                retour.add(fluxIncident);
            }
        }
        return retour;
    }

    /**
     * *
     * Parcours les incident en cours et retourne le premier incident ouvert du même type que la class envoyé en
     * argument.
     *
     * @param c
     * @return L'incident ouvert du même type que Classc ou null si rien n'a été trouvé
     */
    public CollecteIncident getIncidentOuverType(Class c) {
        List<CollecteIncident> list = this.getIncidentEnCours();
        int i;
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).getClass().equals(c)) {
                return list.get(i);
            }
        }
        return null;
    }

    /**
     * *
     * Retourne le nom du journal ainsi que le type du flux. Si ces variables ne sont pas définient, on retourne l'url.
     */
    @Override
    public String toString() {

        // Si on a un nom on le retourne en priorité
        if (nom != null && !nom.trim().isEmpty()) {
            return nom.trim();
        } else if (this.getJournalLie() != null && this.getTypeFlux() != null) {
            return this.getJournalLie().getNom() + " - " + this.getTypeFlux().getDenomination();
        } else if (this.url != null && !this.url.isEmpty()) {
            return this.url;
        } else if (this.ID != null) {
            return "Flux n°" + this.ID;
        } else {
            return "FLUX ";
        }
    }

    @Override
    public String getReadURL() {
        Conf c = DAOFactory.getInstance().getDAOConf().getConfCourante();
        String url = c.getServurl();
        //On rajoute un / a la fin de l'url si besoin est
        if (url != null && url.length() > 1) {
            Character ch = url.charAt(url.length() - 1);
            if (!ch.equals(new Character('/'))) {
                url += "/";
            }
        }
        String retour = url + "flux/read?id=" + ID.toString();
        return retour;
    }

    void addItem(Item nouvellesItems) {
        this.item.add(nouvellesItems);
    }

    /**
     * Retourne un Opml du flux contenant ses sous flux
     * @return
     */
    @Beta
    public Opml getOpml() {
        // On doit commencer par rechercher la liste des flux enfant
        DaoFlux daoFlux = DAOFactory.getInstance().getDAOFlux();
        Opml opml = new Opml();
        opml.setTitle(this.toString());

        Outline outline = this.getOpmlOutline();

        List<Outline> listoutline = new ArrayList<Outline>();
        listoutline.add(outline);

        opml.setOutlines(listoutline);
        return opml;
    }

    @Beta
    public Outline getOpmlOutline() {


        URL xmlUrl = null;
        try {
            xmlUrl = new URL(this.url);
        } catch (Exception e) {
        }

        URL outlinehtmlUrl = null;
        try {
            outlinehtmlUrl = new URL(this.htmlUrl);
        } catch (Exception e) {
        }


        Outline outline = new Outline(this.toString(), xmlUrl, outlinehtmlUrl);

        // On chercher les flux enfant;
        DaoFlux daoFlux = DAOFactory.getInstance().getDAOFlux();
        int i;
        List<Flux> fluxs = daoFlux.findChildren(this);
        for (i = 0; i < fluxs.size(); i++) {
            Outline subOutline = fluxs.get(i).getOpmlOutline();

            outline.getChildren().add(subOutline);
        }

        if (this.getTypeFlux() != null) {
            // On ajoute un attribut non conventionnelle pour préciser le type de flux 
            Attribute att = new Attribute("typeFlux", this.getTypeFlux().getDenomination());

            List<Attribute> listAtt = new ArrayList<Attribute>();
            listAtt.add(att);
            outline.getAttributes().add(att);

        }
        return outline;
    }


    /***
     * Retourne la dernière période de captation du flux
     * @return La dernière période de captation ou null si le flux ne possède pas de période de captation
     */
    public FluxPeriodeCaptation returnDerniereFluxPeriodeCaptation() {
        FluxPeriodeCaptation last = null;
        for (int i = 0; i < periodeCaptations.size(); i++) {
            FluxPeriodeCaptation period = periodeCaptations.get(i);
            if (last == null) {
                last = period;
            }
            if (period.getDateDebut().after(last.getDateDebut())) {
                last = period;
            }
        }
        return last;
    }

    /**
     * Retourne la durée totale de captation du flux en s'appuyant sur les entites FluxPeriodeCaptation
     */
    public Long returnCaptationDuration() throws DonneeInterneCoherente {
        Long duration = new Long(0);

        int nbrPeriodeouverte = 0;

        for (int i = 0; i < periodeCaptations.size(); i++) {
            FluxPeriodeCaptation periode = this.periodeCaptations.get(i);
            if (periode.getDateDebut() != null & periode.getDatefin() != null) {
                DateTime dtDebut = new DateTime(periode.getDateDebut());
                DateTime dtFin = new DateTime(periode.getDatefin());
                Duration dur = new Duration(dtDebut, dtFin);
                duration += dur.getStandardSeconds();
            }
            if (periode.getDateDebut() != null && periode.getDatefin() == null) {
                DateTime dtDebut = new DateTime(periode.getDateDebut());
                DateTime dtFin = new DateTime(periode.getDatefin());
                Duration dur = new Duration(dtDebut, dtFin);
                duration += dur.getStandardSeconds();
                nbrPeriodeouverte++;
            }
        }
        if (nbrPeriodeouverte > 1) {
            throw new DonneeInterneCoherente("Il y a deux période de captation ouverte pour le flux");
        } else {
            return duration;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Flux clone = (Flux) super.clone();

        return clone;

    }
    
    
    
    public List<AbstrIncident> returnIncidentDurantLaPeride(FluxPeriodeCaptation period){
                List<AbstrIncident> returnList = new ArrayList<AbstrIncident>();
        DateTime dt1 = new DateTime(period.getDateDebut());
        DateTime dt2;
        if(period.getDatefin() == null){
            dt2 = new DateTime(period.getDatefin());
        }
        else{
            dt2 = new DateTime();
        }
        
        Interval intev = new Interval(dt1, dt2);

        for (Iterator<CollecteIncident> it = incidentsLie.iterator(); it.hasNext();) {
            CollecteIncident collecteIncident = it.next();
//            
           DateTime dtIncid = new DateTime(collecteIncident.getDateDebut());
  
           if(intev.contains(dtIncid)){
               returnList.add(collecteIncident);
           }
        }
        return returnList;
    }
    
     public long returnIncidentDurationDurantLaPeride(FluxPeriodeCaptation period){
         
         List<AbstrIncident> listIncid =returnIncidentDurantLaPeride(period);
         long time =0;
         
         for (Iterator<AbstrIncident> it = listIncid.iterator(); it.hasNext();) {
             AbstrIncident abstrIncident = it.next();
             DateTime dtdebut = new DateTime(abstrIncident.getDateDebut());
             DateTime dtFin = new DateTime(abstrIncident.getDateFin());
             Duration dur = new Duration(dtdebut, dtFin);
             time = time + dur.getStandardSeconds();
         }
         return time;
     }
    
    
    /***
     * Permet au flux d'être exploité par un visiteurs.
     * @param visitor
     * @throws Exception 
     */
        public void accept(ComportementVisitor visitor) throws Exception{
        visitor.visit(this);
    }

    /**
     * Lors de l'activation désactivation ou du changement de comportement, ce lisner est chargé de modifier des
     * paramètres du FLUX
     */
    public class FluxChangeLisner implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Flux flux = (Flux) evt.getSource();


            //============================================================================
            //..................GESTION DE L'ACTIVATION ET DESACTIVATION DU FLUX
            //============================================================================
            /**
             * *
             * L'activation et la désactivation des flux entraine la création de nouvelles période de captation.
             */
            if (evt.getPropertyName().equals(PROP_ACTIVE)) {
                Boolean oldValue = (Boolean) evt.getOldValue();
                Boolean newValue = (Boolean) evt.getNewValue();


                if (oldValue == null) {
                    oldValue = false;
                }
                if (newValue == null) {
                    newValue = false;
                }
                //Si c'est une activation 
                if (oldValue == false && newValue == true) {
                    if (!flux.getPeriodeCaptations().isEmpty()) {

                        FluxPeriodeCaptation lastperiode = flux.getPeriodeCaptations().get(flux.getPeriodeCaptations().size() - 1);
                        // Si la date de din n'est pas null  on ajoute une période de captation
                        if (lastperiode.getDatefin() != null) {
                            FluxPeriodeCaptation nPeriode = new FluxPeriodeCaptation();
                            nPeriode.setDateDebut(new Date());
                            nPeriode.setFlux(flux);
                            nPeriode.setComportementDurantLaPeriode(mediatorFlux);
                            flux.getPeriodeCaptations().add(nPeriode);

                        }
                        // Si on n'a pas de période de captation
                    } else if (flux.getPeriodeCaptations().isEmpty()) {
                        FluxPeriodeCaptation nP = new FluxPeriodeCaptation();
                        nP.setDateDebut(new Date());
                        nP.setFlux(flux);
                        nP.setComportementDurantLaPeriode(mediatorFlux);
                        flux.getPeriodeCaptations().add(nP);
                    }

                } else if (oldValue == true && newValue == false) {
                    if (!flux.getPeriodeCaptations().isEmpty()) {
                        // On récupère la derniere
                        FluxPeriodeCaptation lastperiode = flux.getPeriodeCaptations().get(flux.getPeriodeCaptations().size() - 1);
                        // On ferme la dernière période de captation si nécessaire
                        if (lastperiode.getDatefin() == null) {
                            lastperiode.setDatefin(new Date());
                        }
                    }
                }

            } //============================================================================
            //.................GESTION DES CHANGEMENTS DE COMPORTEMENT
            //============================================================================
            /**
             * *
             * Lors de changement de comportement, il est nécessaire de fermer et ouvrir des période de captation. C'est
             * le rôle de ce bloc.
             */
            else if (evt.getPropertyName().equals(PROP_MEDIATORFLUX)) {
                ComportementCollecte oldValue = (ComportementCollecte) evt.getOldValue();
                ComportementCollecte newValue = (ComportementCollecte) evt.getNewValue();

                //Si il s'agit d'(un changement de comportement
                if (oldValue != null && newValue != null) {
                    if (!oldValue.equals(newValue)) {
                        // Il est nécessaire d'ouvrir une nouvelle période de captation, si le flux est actif
                        if (flux.active) {
                            flux.setActive(false);
                            flux.setActive(true);
                        }

                        //On place la nouvelle valeur de période de captation dans la dernière période de captation (si période de captation il y a).
                        if (!flux.periodeCaptations.isEmpty()) {
                            flux.periodeCaptations.get(flux.periodeCaptations.size() - 1).setComportementDurantLaPeriode(newValue);
                        }
                    }
                } //Si auparavant il n'y avait pas de comportement (exemple création nouvelle d'un flux actif)
                else if (oldValue == null && newValue != null) {
//                    int lastIndex = 0;
                    if (!flux.periodeCaptations.isEmpty()) {
                        flux.periodeCaptations.get(flux.periodeCaptations.size() - 1).setComportementDurantLaPeriode(newValue);
                    }
                }
            }
        }
    }

    public class Test extends DescriptorEventAdapter {

        public Test() {
        }

        @Override
        public void postClone(DescriptorEvent event) {
            Object source = event.getSource();
        }

        @Override
        public void postMerge(DescriptorEvent event) {
            Object source = event.getSource();

        }
    }

    public class CutoClem extends ClassDescriptor {
    }
}