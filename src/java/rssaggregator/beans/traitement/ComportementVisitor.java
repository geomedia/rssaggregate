/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.beans.traitement;

import rssaggregator.beans.Flux;

/**
 * Le {@link MediatorCollecteAction} peuvent être visité pour lancer l'exploitation. C'est le visiteur qui gère ainsi le traitement
 * @author clem
 */
public interface ComportementVisitor {
    
    
    public void visit(Flux flux) throws Exception;
}
