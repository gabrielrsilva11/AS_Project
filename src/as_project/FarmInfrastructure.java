/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;
import as_project.GUI.FI_GUI;
import as_project.monitors.GranaryMonitor;
import as_project.monitors.PathMonitor;
import as_project.monitors.StandingAreaMonitor;
import as_project.monitors.StoreHouseMonitor;
import as_project.threads.FarmerThread;
import static as_project.util.Constants.*;
import as_project.threads.MonitorLauncher;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

/**
 *
 * @author gabri
 */
public class FarmInfrastructure {
    Sockets sock = null;
    private JFrame gui = null;
    private int numWorkers;
    private int numSteps;
    
    public FarmInfrastructure(){
        FI_GUI fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(440, 650);
        gui.setResizable(true);
        gui.add(fi);
        sock = new Sockets();
        sock.startServer(5000);
        numSteps = 1;
        getMessages();
    }
    
    public int getNumWorkers(){
        return numWorkers;
    }
    
    public int getNumSteps(){
        return numSteps;
    }
    public static void main(String[] args) {
        //FarmInfrastructure farm = new FarmInfrastructure();
      
        
        //Aqui recebe a chamada da gui com o número de farmers e cria um numero de threads
        int nFarmers = 3;
        //Recebe o número de steps que cada um vai andar
        int nSteps = 2;
        FarmInfrastructure farm = new FarmInfrastructure();
                
//        ReentrantLock rel = new ReentrantLock();
//        StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel);
//        StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel);
//        PathMonitor pathMonitor = new PathMonitor(rel);
//        GranaryMonitor granaryMonitor = new GranaryMonitor(rel);
//        
//        //Aqui recebe a chamada da gui com o número de farmers e cria um numero de threads
//        int nFarmers = 5;
//        //Recebe o número de steps que cada um vai andar
//        int nSteps = 2;
//        
//        //Initialize selectionBox
//        SelectionAlgorithm selectionAlgorithm = new SelectionAlgorithm(nFarmers, nSteps);
//        for(int n = 1; n <= nFarmers; n++) {
//            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
//            farmer.setName("Farmer" + n);
//            farmer.start();
//        }
//        
//        storeHouseMonitor.readyToContinue();
          
    }
    
    public void getMessages(){
        // Farmers to go to standing area
        int nFarmers = 3;
        ReentrantLock rel = new ReentrantLock();
        StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel);
        StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel, nFarmers);
        PathMonitor pathMonitor = new PathMonitor(rel);
        GranaryMonitor granaryMonitor = new GranaryMonitor(rel);
        
        //Initialize selectionBox
        //SelectionAlgorithm selectionAlgorithm = new SelectionAlgorithm(nFarmers, nSteps);
        for(int n = 1; n <= ROWS; n++) {
            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
            farmer.setName("" + n);
            farmer.start();
        }        
    
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try{
        DataInputStream input = null;
        input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            
            String message = "";
            while(!message.equals("0")){
                message = input.readUTF();
                switch(message){
                    //Prepare
                    case "1":
                        message = input.readUTF();
                        System.out.println("Hey");
                        numWorkers = Integer.parseInt(message);
                        System.out.println(numWorkers);
                        for(int n = 1; n <= numWorkers; n++) {
                            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
                            farmer.setName("Farmer" + n);
                            farmer.start();
                        }
                        MonitorLauncher ml = new MonitorLauncher("prepare", storeHouseMonitor);
                        executor.execute(ml);
                }
            }
         
            sock.closeConnection(sock.getSocketServer());
        }catch(IOException i){
            System.out.println(i);
        }     
    } 
}