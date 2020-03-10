/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author manuelcura
 */
public class GranaryMonitor {
    
    private final ReentrantLock rel;
    
    public GranaryMonitor(ReentrantLock rel) {
        this.rel = rel;
    }
}
