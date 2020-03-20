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

import static as_project.util.Constants.*;
import as_project.util.PositionAlgorithm;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

/**
 *
 * 
 */
public class StoreHouseMonitor {
    
    private int numberOfFarmers;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[] positions;
    
    public StoreHouseMonitor(Lock rel) {
        this.rel = rel;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS];
    }
    
    public void goToStoreHouse(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers++;
            Thread.sleep(1000);
            System.out.println(numberOfFarmers);
            positions[getFarmerPosition()] = farmer.getName();
            if(numberOfFarmers == ROWS) {
                // signal the CC to enable the Prepare button
                prepare(3);
            }
            conditionToWait.await();
            System.out.println("One go stage 1");
        } catch (InterruptedException ex) {
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
            rel.unlock();
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
