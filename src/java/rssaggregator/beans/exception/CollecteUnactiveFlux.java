/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.beans.exception;

/**
 * Une exception lévée lorsque l'utilisateur tente de relever un flux qui n'est pas actif. Elle ne donne pas lieu a un incident.
 * @author Clément RILLON
 */
public class CollecteUnactiveFlux extends Exception{

    public CollecteUnactiveFlux(String message) {
        super(message);
    }
    
}
