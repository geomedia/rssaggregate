/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.beans.exception;

/**
 * Cette execption peut être levée pour signaler une incohérence de la donnée. Exemple : un flux ayant deux période de
 * @author Clément RILLON
 *
 * @author clem
 */
public class DonneeInterneCoherente extends Exception {

    public DonneeInterneCoherente(String message) {
        super(message);
    }
}
