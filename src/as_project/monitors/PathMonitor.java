/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

import as_project.Sockets;
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
 *
 * @author manuelcura
 */
public class PathMonitor {
    
    private int numberOfFarmers;
    private int nSteps;
    private int totalFarmers;
    private int timeout;
    private boolean stopped;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[][] positions;
    private Map<String, int[]> previousPositions;
    private ArrayList<ArrayList<JTextField>> pathFields;
    
    public PathMonitor(Lock rel, ArrayList<ArrayList<JTextField>> pathFields) {
        this.rel = rel;
        this.previousPositions = new HashMap<>();
        numberOfFarmers = this.totalFarmers;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS][COLUMNS];
        this.pathFields = pathFields;
    }
    
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
                    pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).setText("");
                    pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).paintImmediately(pathFields.get(previousPositions.get(farmerName)[1]).get(previousPositions.get(farmerName)[0]).getVisibleRect());
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
    
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
    }
    
    public void setNumberOfSteps(int nSteps) {
        this.nSteps = nSteps;
    }
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    
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
    
    private void replyCC(){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeClientConnection();
    }
}
