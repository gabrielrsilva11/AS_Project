/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import as_project.threads.FarmerThread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuelcura
 */
public class StoreHouseMonitor {
    
    private int numberOfFarmers;
    private final ReentrantLock rel;
    private final Condition condition;
    
    
    
    public StoreHouseMonitor(ReentrantLock rel) {
        this.rel = rel;
        numberOfFarmers = 0;
        condition = rel.newCondition();
    }
    
    public void goToStoreHouse(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers++;
            System.out.println(farmer.getName() +": acquired lock");
            Thread.sleep(1000);
            while(numberOfFarmers != 5) {
                condition.await();
            }
            condition.signal();
        } catch (InterruptedException ex) {
            ex.printStackTrace(); 
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println(farmer.getName() +": released lock");
            rel.unlock();
        }
    }
    
    public void readyToContinue() {
        condition.signalAll();
    }
}
