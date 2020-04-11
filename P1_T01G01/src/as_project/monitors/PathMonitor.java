package as_project.monitors;

import as_project.threads.FarmerThread;
import as_project.util.PositionAlgorithm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

import static as_project.util.Constants.*;
import java.util.Arrays;

/**
* PathMonitor - monitor that controls all the path processes
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class PathMonitor {
    
    /**
    * The number of farmers that are currently in the path area
    */
    private int numberOfFarmers;
    /**
    * The maximum number of steps each farmer can walk forward at once
    */
    private int nSteps;
    /**
    * The total number of farmers that are in this iteration
    */
    private int totalFarmers;
    /**
    * The timeout defined by GUI to wait to simulate the time each farmer needs
    * to walk nSteps 
    */
    private int timeout;
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
    * Array that contains the positions of the farmers in the path
    */
    private String[][] positions;
    /**
    * HasMap with the farmer name and the previous position occupied by the farmer
    */
    private Map<String, int[]> previousPositions;
    /**
    * Array that contains the positions of the farmers in the path GUI
    */
    private ArrayList<ArrayList<JTextField>> pathFields;
    
    /**
     * PathMonitor class constructor
     * 
     * @param rel ReentratLock that defines the critical region
     * @param pathFields positions of the path GUI
     */
    public PathMonitor(Lock rel, ArrayList<ArrayList<JTextField>> pathFields) {
        this.rel = rel;
        this.previousPositions = new HashMap<>();
        numberOfFarmers = this.totalFarmers;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS][COLUMNS];
        this.pathFields = pathFields;
    }
    
    /**
     * Method for each farmer walk through the path
     * 
     * @param farmer farmer thread
     */
    public void walkToThePath(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            stopped = false;
            numberOfFarmers = this.totalFarmers;
            int[] farmerPosition;
            String farmerName;
            do {
                farmerName = farmer.getName();
                farmerPosition = getFarmerPosition(farmerName);
                Thread.sleep(timeout);
                if (farmerPosition[1] > COLUMNS-1) {
                    positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
                    previousPositions.remove(farmerName);
                    numberOfFarmers--;
                    break;
                } else {
                    pathFields.get(farmerPosition[1]).get(farmerPosition[0]).setText(farmer.getName());
                    pathFields.get(farmerPosition[1]).get(farmerPosition[0]).paintImmediately(pathFields.get(farmerPosition[1]).get(farmerPosition[0]).getVisibleRect());
                    positions[farmerPosition[0]][farmerPosition[1]] = farmer.getName();
                }
                // when the others farmers finish theres no thread to wake the last one, or if theres only one farmer working
                if (numberOfFarmers == 1) {
                    conditionToWait.signal();
                } else {
                    conditionToWait.signal();
                    conditionToWait.await();
                }
            } while(!stopped);
            proceedToTheGranary();
        } catch (InterruptedException ex) {
            Logger.getLogger(PathMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
        
    /**
     * Method for each farmer walk through the path in a reverse order
     * 
     * @param farmer farmer thread
     */
    public void walkToThePathReverse(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            stopped = false;
            numberOfFarmers = this.totalFarmers;
            int[] farmerPosition;
            String farmerName;
            do {
                farmerName = farmer.getName();
                farmerPosition = getFarmerPositionReverse(farmerName);
                Thread.sleep(timeout);
                if (farmerPosition[1] < 0) {
                    positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
                    pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).setText("");
                    pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).paintImmediately(pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).getVisibleRect());
                    previousPositions.remove(farmerName);
                    numberOfFarmers--;
                    break;
                } else {
                    positions[farmerPosition[0]][farmerPosition[1]] = farmer.getName();
                    pathFields.get(farmerPosition[1]).get(farmerPosition[0]).setText(farmer.getName());
                    pathFields.get(farmerPosition[1]).get(farmerPosition[0]).paintImmediately(pathFields.get(farmerPosition[1]).get(farmerPosition[0]).getVisibleRect());
                }
                // when the others farmers finish theres no thread to wake the last one, or if theres only one farmer working
                if (numberOfFarmers == 1) {
                    conditionToWait.signal();
                } else {
                    conditionToWait.signal();
                    conditionToWait.await();
                }
            } while(!stopped);
            proceedToTheStoreHouse();
        } catch (InterruptedException ex) {
            Logger.getLogger(PathMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    /**
     * Method to release the farmers from the condition
     * 
     */
    public void proceedToTheGranary() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            conditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(PathMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    /**
     * Method to release the farmers from the condition
     * 
     */
    public void proceedToTheStoreHouse() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            conditionToWait.signal();
        } catch(InterruptedException ex) {
            Logger.getLogger(PathMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    /** 
     * Method to stop operations and release the threads from the conditions
     * it also cleans some data for another iteration
     * 
     */
    public void stopped() {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            stopped = true;
            for(Map.Entry<String, int[]> entry : previousPositions.entrySet()) {
                positions[entry.getValue()[0]][entry.getValue()[1]] = null;
                pathFields.get(entry.getValue()[1]).get(entry.getValue()[0]).setText("");
                pathFields.get(entry.getValue()[1]).get(entry.getValue()[0]).paintImmediately(pathFields.get(entry.getValue()[1]).get(entry.getValue()[0]).getVisibleRect());
            }
            Arrays.fill(positions, null);
            previousPositions.clear();
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
     * Method to set the nSteps variable
     * 
     * @param nSteps maximum number of steps each farmer can wall at once
     */
    public void setNumberOfSteps(int nSteps) {
        this.nSteps = nSteps;
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
     * @param farmerName name of the farmer that is asking for a position
     * @return an empty position
     */
    private int[] getFarmerPosition(String farmerName) {
       int positionVertical;
       int positionHorizontal;
       int previousHorizontalPosition;
       // first iteration
       if(previousPositions.containsKey(farmerName)) {
           previousHorizontalPosition = previousPositions.get(farmerName)[1];
           positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
           pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).setText("");
           pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).paintImmediately(pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).getVisibleRect());       
       }
       else {
           previousHorizontalPosition = -1;
       }
       do {
           positionVertical = PositionAlgorithm.getVerticalPosition();
           positionHorizontal = PositionAlgorithm.getHorizontalPosition(nSteps) + previousHorizontalPosition;
       } while(positionHorizontal < COLUMNS && positions[positionVertical][positionHorizontal] != null);
       int array[] = {positionVertical, positionHorizontal};
       if(positionHorizontal < COLUMNS) {
            previousPositions.put(farmerName, array);
       }
       return array;
    }
    
    /**
     * Method to find a empty position for the farmer in a reverse path order
     * 
     * @param farmerName name of the farmer that is asking for a position
     * @return an empty position
     */
    private int[] getFarmerPositionReverse(String farmerName) {
       int positionVertical;
       int positionHorizontal;
       int previousHorizontalPosition;
       // first iteration
       if(previousPositions.containsKey(farmerName)) {
           previousHorizontalPosition = previousPositions.get(farmerName)[1];
           positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
           pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).setText("");
           pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).paintImmediately(pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).getVisibleRect());       
       
       }
       else {
           previousHorizontalPosition = 10;
       }
       do {
           positionVertical = PositionAlgorithm.getVerticalPosition();
           positionHorizontal = previousHorizontalPosition - PositionAlgorithm.getHorizontalPosition(nSteps);
       } while(positionHorizontal >= 0 && positions[positionVertical][positionHorizontal] != null);
       int array[] = {positionVertical, positionHorizontal};
        if(positionHorizontal >= 0) {
            previousPositions.put(farmerName, array);
        }
       return array;
    }
   
}
