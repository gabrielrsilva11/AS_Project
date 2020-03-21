/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.monitors;

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
public class PathMonitor {
    
    private int numberOfFarmers;
    private int nSteps;
    private int totalFarmers;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[][] positions;
    private Map<String, int[]> previousPositions;
    
    public PathMonitor(Lock rel, int nSteps, int totalFarmers) {
        this.rel = rel;
        this.nSteps = nSteps;
        this.totalFarmers = totalFarmers;
        this.previousPositions = new HashMap<>();
        numberOfFarmers = this.totalFarmers;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS][COLUMNS];
    }
    
    public void walkToThePath(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers = this.totalFarmers;
            Thread.sleep(1000);
            int[] farmerPosition;
            String farmerName;
            do {
                farmerName = farmer.getName();
                farmerPosition = getFarmerPosition(farmerName);
                if (farmerPosition[1] > COLUMNS-1) {
                    System.out.println("One go stage 3");
                    System.out.println(farmerName);
                    positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
                    previousPositions.remove(farmerName);
                    numberOfFarmers--;
                    break;
                } else {
                    positions[farmerPosition[0]][farmerPosition[1]] = farmer.getName();
                }
                System.out.println(farmerName);
                // when the others farmers finish theres no thread to wake the last one, or if theres only one farmer working
                if (numberOfFarmers == 1) {
                    conditionToWait.signal();
                } else {
                    conditionToWait.signal();
                    conditionToWait.await();
                }
            } while(true);
            System.out.println(Arrays.deepToString(positions).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
            proceedToTheGranary();
        } catch (InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
        
    public void walkToThePathReverse(FarmerThread farmer) {

        rel.lock();
        try {
            numberOfFarmers = this.totalFarmers;
            Thread.sleep(1000);
            int[] farmerPosition;
            String farmerName;
            do {
                farmerName = farmer.getName();
                farmerPosition = getFarmerPositionReverse(farmerName);
                if (farmerPosition[1] < 0) {
                    System.out.println("One go stage 3");
                    System.out.println(farmerName);
                    positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
                    previousPositions.remove(farmerName);
                    numberOfFarmers--;
                    break;
                } else {
                    positions[farmerPosition[0]][farmerPosition[1]] = farmer.getName();
                }
                System.out.println(farmerName);
                //System.out.println(Arrays.deepToString(positions).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
                // when the others farmers finish theres no thread to wake the last one, or if theres only one farmer working
                if (numberOfFarmers == 1) {
                    conditionToWait.signal();
                } else {
                    conditionToWait.signal();
                    conditionToWait.await();
                }
            } while(true);
            proceedToTheStoreHouse();
        } catch (InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rel.unlock();
        }
    }
    
    public void proceedToTheGranary() {
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
    
    public void proceedToTheStoreHouse() {
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
    
    private int[] getFarmerPosition(String farmerName) {
       int positionVertical;
       int positionHorizontal;
       int previousHorizontalPosition;
       // first iteration
       if(previousPositions.containsKey(farmerName)) {
           previousHorizontalPosition = previousPositions.get(farmerName)[1];
           positions[previousPositions.get(farmerName)[0]][previousPositions.get(farmerName)[1]] = null;
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
       System.out.println("Steps: " + (positionHorizontal - previousHorizontalPosition));
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
        System.out.println("Steps: " + (positionHorizontal - previousHorizontalPosition));
       return array;
    }
}