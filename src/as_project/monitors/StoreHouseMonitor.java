package as_project.monitors;

import as_project.Sockets;
import as_project.threads.FarmerThread;
import as_project.util.PositionAlgorithm;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JTextField;

import static as_project.util.Constants.*;

/**
* StoreHouseMonitor - monitor that controls all the store house processes
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class StoreHouseMonitor {
    
    /**
    * The number of farmers that are currently in the store house
    */
    private int numberOfFarmers;
    /**
    * The number of stored corn cobs
    */
    private int storedCornCobs;
    /**
    * Port to send socket response
    */
    private int port;
    /**
    * Flag to store corn cobs ate the end of each iteration
    */
    private boolean storeCorn;
    /**
    * The ReentratLock that defines the critical region where the threads operate
    */
    private final Lock rel;
    /**
    * Condition to make the threads operate inside de critical region
    */
    private final Condition conditionToWait;
    /**
    * Array that contains the positions of the farmers in the store house
    */
    private String[] positions;
    /**
    * Array that contains the positions of the farmers in the store house GUI
    */
    private ArrayList<JTextField> storeHouseFields;
    
    /**
     * StoreHouseMonitor class constructor
     * 
     * @param rel ReentratLock that defines the critical region
     * @param storeHouseFields positions of the store house GUI
     */
    public StoreHouseMonitor(Lock rel,  ArrayList<JTextField> storeHouseFields) {
        this.rel = rel;
        this.storeCorn = false;
        this.numberOfFarmers = 0;
        this.storedCornCobs = 0;
        this.conditionToWait = rel.newCondition();
        this.positions = new String[ROWS];
        this.storeHouseFields = storeHouseFields;
    }
    
    /**
     * Method to stop the farmers and signal the Control Center when they 
     * are all inside the store house
     * 
     * @param farmer farmer thread
     */
    public void goToStoreHouse(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            int p = getFarmerPosition();
            positions[p] = farmer.getName();
            storeHouseFields.get(p).setText(farmer.getName());
            storeHouseFields.get(p).paintImmediately(storeHouseFields.get(p).getVisibleRect());
            if(storeCorn) {
                System.out.println("hello");
                storedCornCobs += CORN_COBS;
                //System.out.println(storedCornCobs);
                if(numberOfFarmers == ROWS) {
                //Reply to enable prepare button
                //replyCC();
                    replyCCCorn(Integer.toString(storedCornCobs));
                }
            }
            conditionToWait.await();
            for(int i = 0; i < positions.length; i++) {
                if(farmer.getName().equals(positions[i])) {
                    storeHouseFields.get(i).setText("");
                    positions[i] = null;
                    numberOfFarmers--;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    /**
     * Method to release the farmers from the condition
     * 
     * @param nFarmers total number of farmers in the current iteration
     */
    public void prepare(int nFarmers) {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            do {
                conditionToWait.signal();
                nFarmers--;
            } while(nFarmers > 0);
        } catch(InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    /**
     * Method to set the store corn flag
     * 
     * @param storeCorn flag to indicate if we want to store corns
     */
    public void setStoreCorn(boolean storeCorn) {
        this.storeCorn = storeCorn;

    }
    
    /**
     * Method to reset the stored corn variable
     * 
     */
    public void resetStoredCornCobs() {
        //When we stop the stored corn cob resets?
        this.storedCornCobs = 0;
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
     * Method to send the message to the ControlCenter
     * 
     * @param message message to send
     */
    private void replyCCCorn(String numero){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", port);
        sock.sendMessage("coletar");
        sock.sendMessage(numero);
    }
}
