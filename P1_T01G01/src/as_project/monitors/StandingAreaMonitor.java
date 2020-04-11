package as_project.monitors;

import as_project.Sockets;
import as_project.threads.FarmerThread;
import as_project.util.PositionAlgorithm;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

import static as_project.util.Constants.*;

/**
* StandingAreaMonitor - monitor that controls all the standing area processes
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class StandingAreaMonitor {
    
    /**
    * The number of farmers that are currently in the standing area
    */
    private int numberOfFarmers;
    /**
    * The total number of farmers that are in this iteration
    */
    private int totalFarmers;
    /**
    * Port to send socket response
    */
    private int port;
    /**
    * The ReentratLock that defines the critical region where the threads operate
    */
    private final Lock rel;
    /**
    * Condition to make the threads operate inside de critical region
    */
    private final Condition conditionToWait;
    /**
    * Array that contains the positions of the farmers in the standing area
    */
    private String[] positions;
    /**
    * Array that contains the positions of the farmers in the standing area GUI
    */
    private ArrayList<JTextField> standingAreaFields;
    
    /**
     * StandingAreaMonitor class constructor
     * 
     * @param rel ReentratLock that defines the critical region
     * @param standingAreaFields positions of the standing area GUI
     */
    public StandingAreaMonitor(Lock rel, ArrayList<JTextField> standingAreaFields) {
        this.rel = rel;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS];
        
        this.standingAreaFields = standingAreaFields;
    }
    
    /**
     * Method to stop the farmers and signal the Control Center when they 
     * are all inside the standing area
     * 
     * @param farmer farmer thread
     */
    public void enterStandingArea(FarmerThread farmer) {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            int pos = getFarmerPosition();
            positions[pos] = farmer.getName();
            standingAreaFields.get(pos).setText(farmer.getName());
            standingAreaFields.get(pos).paintImmediately(standingAreaFields.get(pos).getVisibleRect());
            if(numberOfFarmers == totalFarmers) {
                replyCC("Standing terminado");
            }
            conditionToWait.await();
            conditionToWait.signal();
            for(int i = 0; i < positions.length; i++) {
                if(farmer.getName().equals(positions[i])) {
                    standingAreaFields.get(i).setText("");
                    standingAreaFields.get(i).paintImmediately(standingAreaFields.get(i).getVisibleRect());
                    positions[i] = null;
                    numberOfFarmers--;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StandingAreaMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    /**
     * Method to release the farmers from the condition
     * 
     */
    public void proceedToThePath() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            conditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(StandingAreaMonitor.class.getName()).log(Level.SEVERE, null, ex);   
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
            numberOfFarmers = 0;
            conditionToWait.signalAll();
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
    private void replyCC(String message){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", port);
        sock.sendMessage(message);
        sock.closeClientConnection();
    }
}
