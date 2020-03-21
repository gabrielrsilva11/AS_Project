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
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

import static as_project.util.Constants.*;

/**
 *
 * @author manuelcura
 */
public class StandingAreaMonitor {
    
    private int numberOfFarmers;
    private int totalFarmers;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[] positions;
    private ArrayList<JTextField> standingAreaFields;
    
    public StandingAreaMonitor(Lock rel, ArrayList<JTextField> standingAreaFields) {
        this.rel = rel;
        numberOfFarmers = 0;
        conditionToWait = rel.newCondition();
        positions = new String[ROWS];
        
        this.standingAreaFields = standingAreaFields;
    }
    
    public void enterStandingArea(FarmerThread farmer) {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            int pos = getFarmerPosition();
            positions[pos] = farmer.getName();
            standingAreaFields.get(pos).setText(farmer.getName());
            if(numberOfFarmers == totalFarmers) {
                replyCC();
                // The positions contains the array with farmers to fill the GUI
                //proceedToThePath();
            }
            conditionToWait.await();
            conditionToWait.signal();
            for(int i = 0; i < positions.length; i++) {
                if(farmer.getName().equals(positions[i])) {
                    standingAreaFields.get(i).setText("");
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
    
    public void setTotalFarmers(int totalFarmers) {
        this.totalFarmers = totalFarmers;
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
