/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssagregator.services.tache;

import java.io.File;
import java.util.Date;
import java.util.List;
import rssagregator.beans.StillAlivePOJO;
import rssagregator.beans.incident.AliveIncident;
import rssagregator.beans.incident.IncidentFactory;
import rssagregator.services.crud.AbstrServiceCRUD;
import rssagregator.services.crud.ServiceCRUDFactory;
import rssagregator.utils.PropertyLoader;

/**
 * Cette tache écrit dans un fichier de log propre /var/lib/rssaggregate/log/stillalive. Ce fichier permet de vérifier
 * qu'il n'y a pas de coupure sur le service. La tâche est gérée par le service {@link ServiceServer}. Elle génère des
 * incident de type {@link AliveIncident} 
 *
 * @author Clément RILLON
 */
public class TacheStillAlive extends TacheImpl<TacheStillAlive> {

//    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TacheStillAlive.class);
    /**
     * Le fichier still alive avec lequel il faut travailler
     */
    File file;
    /**
     * *
     * Si la tache constate que le serveur n'a pas inscrit continuellement dans le fichier still alive, cette variable
     * est a true
     */
    Boolean rupture = false;
    
    /***
     * date de début de la dernière rupture de continuité
     */
    Date debutRupture;
    
    /***
     * Date de fin de la dernière rupture de continuité
     */
    Date finRupture;
    
    @Override
    protected void callCorps() throws Exception {


        initialiserTransaction();

        

        // Si le fichier n'est pas configuré, on va le chercher dans la conf
        if (file == null) {
            String varPath = (String) PropertyLoader.returnConfPath() + "stillalive";
            file = new File(varPath);
        }


        StillAlivePOJO alivePOJO = StillAlivePOJO.load(file);
        if (alivePOJO == null) {
            alivePOJO = new StillAlivePOJO();
        }
        rupture = alivePOJO.check(this.timeSchedule + 30);

        // SI il y a rupture on cherche les deux dates correspondat à l'interval d'innactivité
        if (rupture) {

            logger.error("RUPTURE");
            List<Date[]> dates = alivePOJO.getAlive();

            Date[] d1 = dates.get(dates.size() - 1);
            Date[] d2 = dates.get(dates.size() - 2);

            debutRupture = d1[1];
            finRupture = d2[0];

            //----> Enregistrement de l'incident
            AbstrServiceCRUD serviceCrud = ServiceCRUDFactory.getInstance().getServiceFor(AliveIncident.class);
            IncidentFactory<AliveIncident> facto = new IncidentFactory<AliveIncident>();
            
            
            String text = "<p>Il semble que le serveur ait arrété de fonctionner durant un laps de temps. </p>";
            if(alivePOJO.getDebutRupture() != null && alivePOJO.getFinRupture() != null){
                text += "<p>Le fichier de controle n'a pas été modifié entre les dates : <ul>"
                        +"<ul>"
                        +"<li>"+alivePOJO.getDebutRupture()+"</li>"
                        +"<li>"+alivePOJO.getFinRupture()+"</li>"
                        + "</ul></p>";
            }
            
            
            AliveIncident inci = facto.getIncident(AliveIncident.class, text, null);
            inci.setDateDebut(debutRupture);
            inci.setDateFin(debutRupture);

//            serviceCrud.ajouter(inci);
            serviceCrud.ajouter(inci, em);
        }
        alivePOJO.write(file);
    }

    public Boolean getRupture() {
        return rupture;
    }

    public void setRupture(Boolean rupture) {
        this.rupture = rupture;
    }

    /**
     * *
     * début de la période d'innactivité du serveur constatée par la tâche
     *
     * @return
     */
    public Date getDebutRupture() {
        return debutRupture;
    }

    public void setDebutRupture(Date debutRupture) {
        this.debutRupture = debutRupture;
    }

    public Date getFinRupture() {
        return finRupture;
    }

    public void setFinRupture(Date finRupture) {
        this.finRupture = finRupture;
    }
}
