/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import as_project.Sockets;
import as_project.threads.FarmerThread;
import static as_project.util.Constants.*;
import as_project.util.PositionAlgorithm;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    private final Condition collectConditionToWait;
    private String[] positions;
    private Map<String, Integer> collectedCornCob;
    
    public GranaryMonitor(Lock rel, int totalFarmers) {
        this.rel = rel;
        this.totalFarmers = totalFarmers;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        collectConditionToWait = rel.newCondition();
        positions = new String[ROWS];
        collectedCornCob = new HashMap<>();
    }
    
    public void enterTheGranary(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers++;
            Thread.sleep(1000);
            System.out.println(numberOfFarmers);
            positions[getFarmerPosition()] = farmer.getName();
            System.out.println("One go stage 4");
            if(numberOfFarmers == totalFarmers) {
                replyCC();
                // The positions contains the array with farmers to fill the GUI
                //collect();
            }
            conditionToWait.await();
            conditionToWait.signal();
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
    
    public void collectTheCorn(FarmerThread farmer) {
        
        rel.lock();
        try {
            Thread.sleep(1000);
            System.out.println("One go stage 5");
            String farmerName = farmer.getName();
            collectedCornCob.put(farmerName, 0);
            while(true) {
            //while(collectedCornCob.get(farmerName) < CORN_COBS) {
                collectedCornCob.put(farmerName, collectedCornCob.get(farmerName)+1);
                System.out.println("Farmer: " + farmerName);
                System.out.println("collected: " + collectedCornCob.get(farmerName));
                if(collectedCornCob.get(farmerName) == CORN_COBS) {
                    break;
                }
                collectConditionToWait.signal();
                collectConditionToWait.await();
            }
            numberOfFarmers++;
            collectConditionToWait.signal();
            if(numberOfFarmers == totalFarmers) {
                returnToTheBeginning();
            }
            collectConditionToWait.await();
            collectConditionToWait.signal();
            System.out.println("returning: " +  farmer.getName());
            numberOfFarmers--;
            collectedCornCob.remove(farmerName);
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
    
    public void returnToTheBeginning() {
        rel.lock();
        try {
            Thread.sleep(1000);
            collectConditionToWait.signal();
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
    
    private void replyCC(){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeClientConnection();
    }
}
