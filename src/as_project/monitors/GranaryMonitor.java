/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import as_project.Sockets;
import as_project.threads.FarmerThread;
import as_project.util.PositionAlgorithm;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;

import static as_project.util.Constants.*;

/**
 *
 * @author manuelcura
 */
public class GranaryMonitor {
    
    private int numberOfFarmers;
    private int totalFarmers;
    private int timeout;
    private final Lock rel;
    private final Condition conditionToWait;
    private final Condition collectConditionToWait;
    private String[] positions;
    private Map<String, Integer> collectedCornCob;
    
    public GranaryMonitor(Lock rel) {
        this.rel = rel;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        collectConditionToWait = rel.newCondition();
        positions = new String[ROWS];
        collectedCornCob = new HashMap<>();
    }
    
    public void enterTheGranary(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            positions[getFarmerPosition()] = farmer.getName();
            if(numberOfFarmers == totalFarmers) {
                replyCC();
            }
            conditionToWait.await();
            conditionToWait.signal();
            for(int i = 0; i < positions.length; i++) {
                if(farmer.getName().equals(positions[i])) {
                    positions[i] = null;
                    numberOfFarmers--;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(GranaryMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    public void collectTheCorn(FarmerThread farmer) {
        
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            String farmerName = farmer.getName();
            collectedCornCob.put(farmerName, 0);
            while(true) {
                collectedCornCob.put(farmerName, collectedCornCob.get(farmerName)+1);
                Thread.sleep(timeout);
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
            numberOfFarmers--;
            collectedCornCob.remove(farmerName);
        } catch (InterruptedException ex) {
            Logger.getLogger(GranaryMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    public void collect() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            conditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(GranaryMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    public void returnToTheBeginning() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            collectConditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(GranaryMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
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
