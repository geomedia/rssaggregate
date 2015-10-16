/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rssaggregator.services;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Clément RILLON
 */
public class ThreadFactoryPrioitaire implements ThreadFactory{
     private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

    public ThreadFactoryPrioitaire() {
        SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "pool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
    }

        
        
//        
//        DefaultThreadFactory() {
//            SecurityManager s = System.getSecurityManager();
//            group = (s != null) ? s.getThreadGroup() :
//                                  Thread.currentThread().getThreadGroup();
//            namePrefix = "pool-" +
//                          poolNumber.getAndIncrement() +
//                         "-thread-";
//        }

     @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.MAX_PRIORITY) {
                t.setPriority(Thread.MAX_PRIORITY);
            }
            return t;
        }
}
