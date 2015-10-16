/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.services.tache;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.velocity.VelocityContext;
import rssaggregator.beans.incident.CollecteIncident;
import rssaggregator.dao.DAOFactory;
import rssaggregator.dao.DAOIncident;
import rssaggregator.services.ServiceMailNotifier;
import rssaggregator.services.mailtemplate.VelocityTemplateLoad;

/**
 * Cette tâche est lancée toute les jours. Elle doit envoyer un mail récapitulant tout les incidents pour lesquel l'administrateur doit intervenir. CAD : <ul>
 * <li>Incident ne possédant pas de date de fin </li>
 * <li></li>
 * 
 * </ul>
 * si besoin.
 *
 * @author Clément RILLON
 */
public class TacheVerifFluxNotificationMail extends TacheImpl<TacheVerifFluxNotificationMail> {

    private String corps;
    private String objet;
//    private InternetAddress[] address;


    @Override
    protected void callCorps() throws Exception {
        // On récupère la liste des Incidents 
        DAOIncident dao = DAOFactory.getInstance().getDAOIncident();

        dao.setClos(false);
        List<CollecteIncident> incidents = dao.findAllOpenIncident();

        if (!incidents.isEmpty()) {

            objet = "";

            ServiceMailNotifier serviceMail = ServiceMailNotifier.getInstance();
            TacheEnvoyerMail mailSendTask = (TacheEnvoyerMail) TacheFactory.getInstance().getNewTask(TacheEnvoyerMail.class, false);//new TacheEnvoyerMail(serviceMail);

            mailSendTask.setPropertiesMail(serviceMail.getPropertiesMail());
            mailSendTask.setToMailAdresses(serviceMail.returnMailAdmin());
            mailSendTask.setSubject("Récapitulatif des Incidents");
                                    VelocityContext vCtxt = new VelocityContext();
            vCtxt.put("incidents", incidents);
            vCtxt.put("titreMail", "Récapitulatif des incidents et évènements");
            vCtxt.put("descMail", "Ce mail est un récapitulatif journalier des incidents et évènements survenus sur le serveur. Il est envoyé toutes les "+this.printSchedule()+ ". Veillez a résondre chacun des cas. Lorsque votre travail de maintenance sera terminé, vous pouvez clore manuellement les incidents afin d'empêcher que ceux-ci se renotifient") ;
            
            String txtMail = VelocityTemplateLoad.rendu("rssaggregator/services/mailtemplate/MailAlertTemplate.vsl", vCtxt);
            mailSendTask.setContent(txtMail);
            
            ServiceMailNotifier.getInstance().getTacheProducteur().produireMaintenant(mailSendTask);
            synchronized(mailSendTask){
                mailSendTask.wait(30*1000);
            }
            

//            Future<TacheEnvoyerMail> fut = serviceMail.submit(mailSendTask); // On envoi le mail en lui laissant 30 secondes
//            fut.get(30, TimeUnit.SECONDS);
            System.out.println("FIN");
        }
    }

   
    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }
}
