/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssagregator.beans.incident;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import rssagregator.beans.exception.CollecteUnactiveFlux;
import rssagregator.beans.exception.UnIncidableException;
import rssagregator.services.tache.AbstrTache;
import rssagregator.utils.ExceptionTool;

/**
 * Permet de créer un incident. Il est possible de générer un incident à partir d'une tache {@link #createIncidentFromTask(rssagregator.services.tache.AbstrTache, java.lang.String)
 * } ou en spécifiant directement le type d'incident que l'on souhaite créer {@link #getIncident(java.lang.Class, java.lang.String, java.lang.Throwable)
 * }
 *
 * @author Clément RILLON
 */
public class IncidentFactory<T extends AbstrIncident> {

    protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(IncidentFactory.class);

    /**
     * *
     * Retourne un incident en utilisant la généricité. Les champs dateDebut message et log sont complétés.
     *
     * @param typeRetourne : la class de l'incident
     * @param message : le message à destination des administrateurs
     * @param tw : l'exeption levée par la tâche
     * @return
     */
    public T getIncident(Class<T> typeRetourne, String message, Throwable tw) {
        T incid = null;

        try {
            incid = typeRetourne.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(IncidentFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(IncidentFactory.class.getName()).log(Level.SEVERE, null, ex);
        }

        configurerIncident(incid, message, tw);
        return incid;
    }

    /**
     * *
     * Crée un incident à partir de la tache envoyée en paramettre. Pour cela il faut que la tâche envoyé soit incidable
     *
     * @param tache :
     * @param message
     */
    public T createIncidentFromTask(AbstrTache tache, String message) throws InstantiationException, IllegalAccessException, UnIncidableException {
        AbstrIncident incid;

        // On vérifie que la tache est incidable;
        if (Incidable.class.isAssignableFrom(tache.getClass())) {
            if (tache.getExeption().getClass().equals(CollecteUnactiveFlux.class)) {
                return null;
            }
            logger.debug("c'est une tâche incidable");

            Incidable cast = (Incidable) tache;
            Class c = cast.getTypeIncident();

            Object o = c.newInstance();
            incid = (AbstrIncident) o;
            configurerIncident(incid, message, tache.getExeption());
//            incid.setMessageEreur(message);
//            incid.setNombreTentativeEnEchec(1);
//            incid.setDateDebut(new Date());
            return (T) o;
        } else {
            throw new UnIncidableException("La tache envoyée en paramettre n'implémente pas l'interface incidable.");
        }
    }

    /**
     * *
     * Cette méthode permet de gérer la configuration d'un incident. Elle est utilisée par les deux méthodes permettant
     * d'obtenir un indident. Elle complete les champs date de début nombre de tentative ainsi que message (en utilisant
     * le message envoyé en argument).
     *
     * @param incid : l'incident qu'il faut configurer
     * @param message : le message qui doit êter inscrit dans l'incident.
     * @param tw L'exception java a l'origine de l'incident
     */
    private static void configurerIncident(AbstrIncident incid, String message, Throwable tw) {
        if (message != null && !message.isEmpty()) {
            incid.setMessageEreur(message);
        }
        incid.setNombreTentativeEnEchec(1); // L'incident vient d'être créé, il faut donc 1 en nombre de tentative en échec

        incid.setDateDebut(new Date());

//        if (incid.getClass().equals(MailIncident.class)) {
//            incid.setNotificationImperative(false);
//        } else {
//            incid.setNotificationImperative(true);
//        }

        if (tw != null) {
            incid.setLogErreur(ExceptionTool.stackTraceToString(tw));

        }
    }
}
