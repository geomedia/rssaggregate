/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.beans.exception;

/**
 * Exception pouvant être levée pour dire que l'action demandée n'a pas été effectuée
 * @author Clément RILLON
 */
public class ActionNonEffectuee extends Exception{

    public ActionNonEffectuee() {
    }

    public ActionNonEffectuee(String message) {
        super(message);
    }
}
