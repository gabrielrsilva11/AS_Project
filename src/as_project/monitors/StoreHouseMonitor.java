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
    private int storedCornCobs;
    private boolean storeCorn;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[] positions;
    
    public StoreHouseMonitor(Lock rel) {
        this.rel = rel;
        this.storeCorn = false;
        this.numberOfFarmers = 0;
        this.storedCornCobs = 0;
        this.conditionToWait = rel.newCondition();
        this.positions = new String[ROWS];
    }
    
    public void goToStoreHouse(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers++;
            Thread.sleep(1000);
            System.out.println(numberOfFarmers);
            positions[getFarmerPosition()] = farmer.getName();
            // If the iteration ends we need to set this boolean to false
            if(storeCorn) {
                storedCornCobs += CORN_COBS;
            }
            if(numberOfFarmers == ROWS) {
                // signal the CC to enable the Prepare button
                prepare(3);
            }
            conditionToWait.await();
            System.out.println("Corn cobs: " + storedCornCobs);
            System.out.println("One go stage 1");
            System.out.println(Arrays.toString(positions));
            for(int i = 0; i < positions.length; i++) {
                if(farmer.getName().equals(positions[i])) {
                    positions[i] = null;
                    numberOfFarmers--;
                }
            }
            System.out.println(Arrays.toString(positions));
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
            storeCorn = true;
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
