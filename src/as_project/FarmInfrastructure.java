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
import as_project.threads.MonitorLauncher;
import as_project.util.PositionAlgorithm;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

/**
 *
 * @author gabri
 */
public class FarmInfrastructure extends Thread{
    FI_GUI fi = null;
    Sockets sock = null;
    private JFrame gui = null;
    private int numWorkers;
    private int numSteps;
    private int timeout;
    
    /**
    public FarmInfrastructure(){
        fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(450, 700);
        gui.setResizable(true);
        gui.add(fi);
        sock = new Sockets();
        sock.startServer(5000);
        numSteps = 1;
        //getMessages();
    }*/
    
    public int getNumWorkers(){
        return numWorkers;
    }
    
    public int getNumSteps(){
        return numSteps;
    }
//    public static void main(String[] args) {
//        FarmInfrastructure farm = new FarmInfrastructure();
        
        
        //Aqui recebe a chamada da gui com o número de farmers e cria um numero de threads
        int nFarmers = 3;
        //Recebe o número de steps que cada um vai andar
        int nSteps = 2;
        FarmInfrastructure farm = new FarmInfrastructure();
                
        Lock rel = new ReentrantLock();
        Lock rel1 = new ReentrantLock();
        Lock rel2 = new ReentrantLock();
        Lock rel3 = new ReentrantLock();
        StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel);
        StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel1, nFarmers);
        PathMonitor pathMonitor = new PathMonitor(rel2, nSteps, nFarmers);
        GranaryMonitor granaryMonitor = new GranaryMonitor(rel3, nFarmers);
       
        
        //Initialize selectionBox
        //SelectionAlgorithm selectionAlgorithm = new SelectionAlgorithm(nFarmers, nSteps);
        for(int n = 1; n <= 5; n++) {
            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
            farmer.setName("Farmer" + n);
            farmer.start();
        }
        
          
    }
    /*
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
          
//    }
    public void getMessages(){
        ReentrantLock rel = new ReentrantLock();
        StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel);
        StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel, numWorkers);
        PathMonitor pathMonitor = new PathMonitor(rel);
        GranaryMonitor granaryMonitor = new GranaryMonitor(rel);
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try{
        DataInputStream input = null;
        input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            
            String message = "";
            while(!message.equals("0")){
                message = input.readUTF();
                System.out.println(message);
                switch(message){
                    //Prepare
                    case "prepare":
                        message = input.readUTF();
                        String[] splitMessage = message.split(",");
                        numWorkers = Integer.parseInt(splitMessage[0]);
                        numSteps = Integer.parseInt(splitMessage[1]);
                        timeout = Integer.parseInt(splitMessage[2]);
                        System.out.println("Num workers: " + numWorkers);
                        for(int n = 1; n <= numWorkers; n++) {
                            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
                            farmer.setName("Farmer" + n);
                            farmer.start();
                        }
                        MonitorLauncher ml = new MonitorLauncher("prepare", storeHouseMonitor,numWorkers);
                        executor.execute(ml);
                        break;
                    case "start":
                        System.out.println("Caso start");
                        MonitorLauncher ml2 = new MonitorLauncher("start", standingAreaMonitor);
                        executor.execute(ml2);
                        break;
                }
            }
            sock.closeServerConnection();
        }catch(IOException i){
            System.out.println(i);
        }     
    } */

