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
import java.util.ArrayList;
import javax.swing.JTextField;

/**
 *
 * @author manuelcura
 */
public class GranaryMonitor {
    
    private int numberOfFarmers;
    private int totalFarmers;
    private int timeout;
    private boolean stopped;
    private final Lock rel;
    private final Condition conditionToWait;
    private final Condition collectConditionToWait;
    private String[] positions;
    private Map<String, Integer> collectedCornCob;
    private ArrayList<JTextField> granaryFields;
    private int port;
    
    public GranaryMonitor(Lock rel, ArrayList<JTextField> granaryFields) {
        this.rel = rel;
        numberOfFarmers = 0;
        stopped = false;
        conditionToWait = rel.newCondition();
        collectConditionToWait = rel.newCondition();
        positions = new String[ROWS];
        collectedCornCob = new HashMap<>();
        this.granaryFields = granaryFields;
    }
    
    public void enterTheGranary(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            int p = getFarmerPosition();
            positions[p] = farmer.getName();
            granaryFields.get(p).setText(farmer.getName());
            granaryFields.get(p).paintImmediately(granaryFields.get(p).getVisibleRect());
            if(numberOfFarmers == totalFarmers) {
                replyCC("Start terminado");
            }
            conditionToWait.await();
            conditionToWait.signal();
            if(stopped) {
                clearPositions(farmer.getName()); 
            }
            numberOfFarmers--;
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
            stopped = false;
            String farmerName = farmer.getName();
            collectedCornCob.put(farmerName, 0);
            while(!stopped) {
                collectedCornCob.put(farmerName, collectedCornCob.get(farmerName)+1);
                Thread.sleep(timeout);
                if(collectedCornCob.get(farmerName) == CORN_COBS) {
                    break;
                } if (numberOfFarmers == 0) {
                    collectConditionToWait.signal();
                }
                collectConditionToWait.await();   
            }
            numberOfFarmers++;
            if(numberOfFarmers == totalFarmers) {
                replyCC("Collect Terminado");
            } else {
                collectConditionToWait.signal();
            }
            collectConditionToWait.await();
            collectConditionToWait.signal();
            numberOfFarmers--;
            clearPositions(farmer.getName());
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
    
    public void stopped() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            stopped = true;
            conditionToWait.signalAll();
            collectConditionToWait.signalAll();

        } catch(InterruptedException ex) {
            Logger.getLogger(StandingAreaMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
    }
    public void setPort(int port){
        this.port = port;
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
    
    private void clearPositions(String farmerName) {
        for(int i = 0; i < positions.length; i++) {
            if(farmerName.equals(positions[i])) {
                granaryFields.get(i).setText("");
                granaryFields.get(i).paintImmediately(granaryFields.get(i).getVisibleRect());
                positions[i] = null;
            }
        }
    }
    
    private void replyCC(String message){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", port);
        sock.sendMessage(message);
        sock.closeClientConnection();
    }
}
