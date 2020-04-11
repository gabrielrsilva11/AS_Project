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
import java.util.ArrayList;
import javax.swing.JTextField;

import static as_project.util.Constants.*;

/**
* GranaryMonitor - monitor that controls all the granary processes
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class GranaryMonitor {
    /**
    * The number of farmers that are currently in the granary
    */
    private int numberOfFarmers;
    /**
    * The total number of farmers that are in this iteration
    */
    private int totalFarmers;
    /**
    * The timeout defined by GUI to wait to simulate the time each farmer needs
    * to collect one corn cob
    */
    private int timeout;
    /**
    * Port to send socket response
    */
    private int port;
    /**
    * The stopped boolean alerts the monitor that this iteration for some reason 
    * has been stopped
    */
    private boolean stopped;
    /**
    * The ReentratLock that defines the critical region where the threads operate
    */
    private final Lock rel;
    /**
    * Condition to make the threads operate inside de critical region
    */
    private final Condition conditionToWait;
    /**
    * Condition to make the threads operate inside de critical region
    */
    private final Condition collectConditionToWait;
    /**
    * Array that contains the positions of the farmers in the granary
    */
    private String[] positions;
    /**
    * HasMap with the farmer name and the corn cobs collected
    */
    private Map<String, Integer> collectedCornCob;
    /**
    * Array that contains the positions of the farmers in the granary GUI
    */
    private ArrayList<JTextField> granaryFields;
    
    /**
     * GranaryMonitor class constructor
     * 
     * @param rel ReentratLock that defines the critical region
     * @param granaryFields positions of the granary GUI
     */
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
    
    /**
     * Method to stop the farmers and signal the Control Center when they 
     * are all inside the granary
     * 
     * @param farmer farmer thread
     */
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
    
    /**
     * Method to collect the corn and signal the Control Center when they 
     * all finish
     * 
     * @param farmer farmer thread
     */
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
    
    /**
     * Method to release the farmers from the condition
     * 
     */
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

    /** 
     * Method to release the farmers from the condition
     * 
     */
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

    /** 
     * Method to stop operations and release the threads from the conditions
     * 
     */
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
    
    /**
     * Method to set the totalFarmers variable
     * 
     * @param totalFarmers total number of farmers in the current iteration
     */
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
    }
    
    /**
     * Method to set the port variable
     * 
     * @param port port to send the response
     */
    public void setPort(int port){
        this.port = port;
    }
    
    /**
     * Method to set the timeout variable
     * 
     * @param timeout defined by GUI
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
    /**
     * Method to find a empty position for the farmer
     * 
     * @return an empty position
     */
    private int getFarmerPosition() {
       int position;
       do {
           position = PositionAlgorithm.getVerticalPosition();
       } while(positions[position] != null);
       return position;
    }
    
    /**
     * Method to clear all the positions occupied by the farmers
     * 
     * @param farmerName name of the farmer
     */
    private void clearPositions(String farmerName) {
        for(int i = 0; i < positions.length; i++) {
            if(farmerName.equals(positions[i])) {
                granaryFields.get(i).setText("");
                granaryFields.get(i).paintImmediately(granaryFields.get(i).getVisibleRect());
                positions[i] = null;
            }
        }
    }
    
    /**
     * Method to send the message to the ControlCenter
     * 
     * @param message message to send
     */
    private void replyCC(String message){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", port);
        sock.sendMessage(message);
        sock.closeClientConnection();
    }
}
