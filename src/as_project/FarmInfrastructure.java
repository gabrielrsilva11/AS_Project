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
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;
import java.util.concurrent.locks.Condition;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    
    public FarmInfrastructure(){
        fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(379, 581);
        gui.setResizable(true);
        gui.add(fi);
        
    }
    
    @Override
    public void run(){
        sock = new Sockets();
        sock.startServer(5000);
        getMessages();
    }
    
    public int getNumWorkers(){
        return numWorkers;
    }
    
    public int getNumSteps(){
        return numSteps;
    }
//    public static void main(String[] args) {
//        FarmInfrastructure farm = new FarmInfrastructure();
        
        
        //Aqui recebe a chamada da gui com o número de farmers e cria um numero de threads
        //int nFarmers = 3;
        //Recebe o número de steps que cada um vai andar
        //int nSteps = 2;
        //FarmInfrastructure farm = new FarmInfrastructure();
                
        //Lock rel = new ReentrantLock();
        //Lock rel1 = new ReentrantLock();
        //Lock rel2 = new ReentrantLock();
        //Lock rel3 = new ReentrantLock();
        //StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel);
        //StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel1, nFarmers);
        //PathMonitor pathMonitor = new PathMonitor(rel2, nSteps, nFarmers);
        //GranaryMonitor granaryMonitor = new GranaryMonitor(rel3, nFarmers);
       
        
        //Initialize selectionBox
        //SelectionAlgorithm selectionAlgorithm = new SelectionAlgorithm(nFarmers, nSteps);
        //for(int n = 1; n <= 5; n++) {
        //    FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
        //    farmer.setName("Farmer" + n);
        //    farmer.start();
        //}
        
          
    //}
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
    public void getMessages() {
        ReentrantLock rel = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Condition conditionToWait = rel.newCondition();
        StoreHouseMonitor storeHouseMonitor = null;
        StandingAreaMonitor standingAreaMonitor = null;
        PathMonitor pathMonitor = null;
        GranaryMonitor granaryMonitor = null;
        ArrayList<FarmerThread> Farmers_Array = new ArrayList<FarmerThread>();
        
        try{
        DataInputStream input = null;
        input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            
            String message = "";
            while(!message.equals("0")){
                message = input.readUTF();
                System.out.println(message);
                switch(message){
                    //Prepare
                    case "initial":
                        message = input.readUTF();
                        String[] splitMessage = message.split(",");
                        numWorkers = Integer.parseInt(splitMessage[0]);
                        numSteps = Integer.parseInt(splitMessage[1]);
                        timeout = Integer.parseInt(splitMessage[2]);
                        
                        storeHouseMonitor = new StoreHouseMonitor(rel);
                        standingAreaMonitor = new StandingAreaMonitor(rel, numWorkers);
                        pathMonitor = new PathMonitor(rel, numWorkers, numSteps);
                        granaryMonitor = new GranaryMonitor(rel, numWorkers);
                        replyCC();
                        break;
                    case "prepare":
                        for(int n = 1; n <= 5; n++) {
                            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
                            Farmers_Array.add(farmer);
                            farmer.setName("Farmer" + n);
                            farmer.start();
                        }
                        MonitorLauncher mlPrepare = new MonitorLauncher("prepare",storeHouseMonitor,numWorkers);
                        executor.execute(mlPrepare);
                        break; 
                    case "start":
                        MonitorLauncher mlStart = new MonitorLauncher("start", standingAreaMonitor);
                        executor.execute(mlStart);
                        break;
                    case "collect":
                        MonitorLauncher mlCollect = new MonitorLauncher("collect", pathMonitor);
                        executor.execute(mlCollect);
                        break;
                    case "return":
                        MonitorLauncher mlReturn = new MonitorLauncher("return", granaryMonitor);
                        executor.execute(mlReturn);
                        break;
                    case "stop":
                        break;
                    case "exit":
                        if(!Farmers_Array.isEmpty()){
                            for(FarmerThread farmer: Farmers_Array){
                                farmer.current.interrupt();
                            }
                        }
                        System.out.println("End of Farmer");
                        replyCC();
                        break;
                }
            }
            sock.closeServerConnection();
        }catch(IOException i){
            System.out.println(i);
        }     
    }
    private void replyCC(){
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeClientConnection();
    }
}
