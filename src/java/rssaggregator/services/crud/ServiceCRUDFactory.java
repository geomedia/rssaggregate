/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.services.crud;

import rssaggregator.beans.Flux;
import rssaggregator.beans.FluxType;
import rssaggregator.beans.Item;
import rssaggregator.beans.Journal;
import rssaggregator.beans.UserAccount;
import rssaggregator.beans.incident.AbstrIncident;
import rssaggregator.beans.traitement.ComportementCollecte;

/**
 * Cette factory est chargé de délivrer les instances services crud.
 *
 * @author Clément RILLON
 */
public class ServiceCRUDFactory {

    private static ServiceCRUDFactory instance = new ServiceCRUDFactory();
    ServiceCRUDFlux serviceCRUDFlux = new ServiceCRUDFlux();
    ServiceCRUDBeansBasique serviceCRUDBeansBasique = new ServiceCRUDBeansBasique();
    ServiceCRUDBeansSynchro serviceCRUDBeansSynchro = new ServiceCRUDBeansSynchro();
    ServiceCRUDComportement serviceCRUDComportement = new ServiceCRUDComportement();
    ServiceCRUDJournal serviceCRUDJournal = new ServiceCRUDJournal();
    ServiceCrudIncident serviceCrudIncident = new ServiceCrudIncident();
//    ServiceCRUDBeansBasiqueLock serviceCRUDBeansBasiqueLock = new ServiceCRUDBeansBasiqueLock();

    private ServiceCRUDFactory() {
   
    }

    public static ServiceCRUDFactory getInstance() {
        if (instance == null) {
            instance = new ServiceCRUDFactory();
        }
        return instance;
    }

    /**
     * *
     * Retourne le service permettant de gérer le beans envoyé en argument
     *
     * @param beans un beans, exemple un flux une item....
     * @return = le service permetant de gérer le beans envoyé. 
     * @throws Si il n'est pas possible de trouver le service approprié pour gérer le beans, on emmet une UnsupportedOperationException
     */
    public AbstrServiceCRUD getServiceFor(Class beans) throws UnsupportedOperationException{
        AbstrServiceCRUD serviceCrud = null;
        if (beans == null) {
            throw new UnsupportedOperationException("Pas de service CRUD pour un beans null");
        } else {
            if (beans.equals(Flux.class)) {
                serviceCrud = serviceCRUDFlux;
            } else if (beans.equals(Journal.class)) {
                serviceCrud = serviceCRUDJournal;
            } else if (beans.equals(FluxType.class)) {
                serviceCrud = serviceCRUDBeansSynchro;
            } else if (beans.equals(UserAccount.class)) {
                serviceCrud = serviceCRUDBeansSynchro;
            } else if (beans.equals(Item.class)) {
                serviceCrud = serviceCRUDBeansBasique;
            } else if (AbstrIncident.class.isAssignableFrom(beans)) {
                serviceCrud = serviceCrudIncident;
            } else if (beans.equals(ComportementCollecte.class)) {
                serviceCrud = serviceCRUDComportement;
            }
//            else if (beans.equals(ServeurSlave.class)) {
//                serviceCrud = serviceCRUDBeansBasique;
//            }
        }

        if (serviceCrud != null) {
            return serviceCrud;
        } else {
            throw new UnsupportedOperationException("Il faut configurer le service approrié pour le type de beans " + beans.getClass());
        }
    }
}
