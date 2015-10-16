/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.beans.exception;

import rssaggregator.services.tache.TacheDecouverteAjoutFlux;

/**
 * Un incident levé par la tâche {@link TacheDecouverteAjoutFlux} lorsqu'après avoir parcouru la page d'un journal, elle
 * ne découvre aucun flux
 *
 * @author Clément RILLON
 */
public class AucunFluxDecouvert extends Exception {
}
