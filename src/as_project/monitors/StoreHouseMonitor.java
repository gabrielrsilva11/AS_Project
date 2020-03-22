/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * 
 */
public class StoreHouseMonitor {
    
    private int numberOfFarmers;
    private int storedCornCobs;
    private boolean storeCorn;
    private final Lock rel;
    private final Condition conditionToWait;
    private String[] positions;
    private ArrayList<JTextField> storeHouseFields;
    private int port;
    
    public StoreHouseMonitor(Lock rel,  ArrayList<JTextField> storeHouseFields) {
        this.rel = rel;
        this.storeCorn = false;
        this.numberOfFarmers = 0;
        this.storedCornCobs = 0;
        this.conditionToWait = rel.newCondition();
        this.positions = new String[ROWS];
        this.storeHouseFields = storeHouseFields;
    }
    
    public void goToStoreHouse(FarmerThread farmer) {

        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            numberOfFarmers++;
            int p = getFarmerPosition();
            positions[p] = farmer.getName();
            storeHouseFields.get(p).setText(farmer.getName());
            storeHouseFields.get(p).paintImmediately(storeHouseFields.get(p).getVisibleRect());
            System.out.println(storeCorn);
            if(storeCorn) {
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
    
    public void prepare(int nFarmers) {
        rel.lock();
        try {
            Thread.sleep(new Random().nextInt(DELAY_BETWEEN_LOCKS));
            storeCorn = true;
            do {
                nFarmers--;
                conditionToWait.signal();
            } while(nFarmers > 0);
        } catch(InterruptedException ex) {
            Logger.getLogger(StoreHouseMonitor.class.getName()).log(Level.SEVERE, null, ex);   
        } finally {
            rel.unlock();
        }
    }
    
    public void setStoreCorn() {
        this.storeCorn = false;
        //When we stop the stored corn cob resets?
        this.storedCornCobs = 0;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    private int getFarmerPosition() {
       int position;
       do {
           position = PositionAlgorithm.getVerticalPosition();
       } while(positions[position] != null);
       return position;
    }
    
    private void replyCCCorn(String numero){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", port);
        sock.sendMessage("coletar");
        sock.sendMessage(numero);
    }
}
