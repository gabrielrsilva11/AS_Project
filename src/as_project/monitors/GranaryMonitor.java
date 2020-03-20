/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import as_project.threads.FarmerThread;
import static as_project.util.Constants.ROWS;
import as_project.util.PositionAlgorithm;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manuelcura
 */
public class GranaryMonitor {
    
private int numberOfFarmers;
    private int totalFarmers;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[] positions;
    
    public GranaryMonitor(Lock rel, int totalFarmers) {
        this.rel = rel;
        this.totalFarmers = totalFarmers;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS];
    }
    
    public void collectTheCorn(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers++;
            Thread.sleep(1000);
            System.out.println(numberOfFarmers);
            positions[getFarmerPosition()] = farmer.getName();
            System.out.println("One go stage 4");
            if(numberOfFarmers == totalFarmers) {
                // signal the CC to enable the collect button
                // The positions contains the array with farmers to fill the GUI
                collect();
            }
            conditionToWait.await();
            conditionToWait.signal();
            System.out.println("Collecting: " +  farmer.getName());
            //conditionToWait.signal();
        } catch (InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    public void collect() {
        rel.lock();
        try {
            Thread.sleep(1000);
            conditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
        public void prepare(int nFarmers) {
        // Evoke when the prepare button is used
        rel.lock();
        try {
            Thread.sleep(1000);
            do {
                nFarmers--;
                conditionToWait.signal();
            } while(nFarmers > 0);
        } catch(InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.lock();
        }
    }
    
    private int getFarmerPosition() {
       int position;
       do {
           position = PositionAlgorithm.getVerticalPosition();
       } while(positions[position] != null);
       return position;
    }
}
