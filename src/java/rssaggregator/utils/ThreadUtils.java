/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.utils;

/**
 *
 * @author Clément RILLON
 */
public class ThreadUtils {
    
    
    /***
     * Test si la thread courrante est interrompu et leve une {@link InterruptedException} le ca échéant
     */
    public static void interruptCheck() throws InterruptedException{
        if(Thread.currentThread().isInterrupted()){
            throw new InterruptedException();
        }
    }
}
