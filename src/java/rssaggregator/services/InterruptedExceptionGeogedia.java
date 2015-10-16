/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.services;

import rssaggregator.services.tache.AbstrTache;

/**
 * Une execption propre au projet géomédia. Lorsqu'un Callable est interrompu, il doit emmettre ce type d'exception
 * @author Clément RILLON
 */
public class InterruptedExceptionGeogedia extends InterruptedException{
    AbstrTache task;

    public InterruptedExceptionGeogedia(AbstrTache task, String s) {
        super(s);
        this.task = task;
    }
    
}
