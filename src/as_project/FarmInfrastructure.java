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
import javax.swing.JTextField;

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
    ArrayList<JTextField> storeFields;
    ArrayList<JTextField> standingFields;
    ArrayList<ArrayList<JTextField>> pathFields;
    ArrayList<JTextField> granaryFields;    
    
    public FarmInfrastructure(){
        fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(450, 560);
        gui.setResizable(false);
        gui.add(fi);
        getGUIFields();
    }
    
    @Override
    public void run(){
        sock = new Sockets();
        sock.startServer(5000);
        handleMessages();
    }
    
    public int getNumWorkers(){
        return numWorkers;
    }
    
    public int getNumSteps(){
        return numSteps;
    }

    public void handleMessages() {
        ReentrantLock rel = new ReentrantLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StoreHouseMonitor storeHouseMonitor = new StoreHouseMonitor(rel, storeFields);
        StandingAreaMonitor standingAreaMonitor = new StandingAreaMonitor(rel, standingFields);
        PathMonitor pathMonitor = new PathMonitor(rel, pathFields);
        GranaryMonitor granaryMonitor = new GranaryMonitor(rel, granaryFields);
        ArrayList<FarmerThread> Farmers_Array = new ArrayList<FarmerThread>();
        
        for(int n = 1; n <= 5; n++) {
            FarmerThread farmer = new FarmerThread(storeHouseMonitor, standingAreaMonitor, pathMonitor, granaryMonitor);
            Farmers_Array.add(farmer);
            farmer.setName("Farmer" + n);
            farmer.start();
        }
        
        try{
            DataInputStream input = null;
            input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            
            String message = "";
            while(!message.equals("0")){
                message = input.readUTF();
                System.out.println("Case:" + message);
                switch(message){
                    //Prepare
                    case "prepare":
                        message = input.readUTF();
                        String[] splitMessage = message.split(",");
                        numWorkers = Integer.parseInt(splitMessage[0]);
                        numSteps = Integer.parseInt(splitMessage[1]);
                        timeout = Integer.parseInt(splitMessage[2]);
                        standingAreaMonitor.setTotalFarmers(numWorkers);
                        pathMonitor.setTotalFarmers(numWorkers);
                        pathMonitor.setNumberOfSteps(numSteps);
                        pathMonitor.setTimeout(timeout);
                        granaryMonitor.setTotalFarmers(numWorkers);
                        
                        MonitorLauncher mlPrepare = new MonitorLauncher("prepare",storeHouseMonitor,numWorkers);
                        executor.execute(mlPrepare);
                        break;
                    case "start":
                        MonitorLauncher mlStart = new MonitorLauncher("start", standingAreaMonitor);
                        executor.execute(mlStart);
                        break;
                    case "collect":
                        MonitorLauncher mlCollect = new MonitorLauncher("collect", granaryMonitor);
                        executor.execute(mlCollect);
                        break;
                    case "return":
                        MonitorLauncher mlReturn = new MonitorLauncher("return", granaryMonitor);
                        executor.execute(mlReturn);
                        break;
                    case "stop":
                        for(FarmerThread farmer: Farmers_Array){
                                farmer.setStopped(true);
                        }
                        break;
                    case "exit":
                        if(!Farmers_Array.isEmpty()){
                            for(FarmerThread farmer: Farmers_Array){
                                farmer.current.interrupt();
                            }
                        }
                        System.out.println("End of Farmer");
                        replyCC("exit");
                        break;
                }
            }
            sock.closeServerConnection();
        }catch(IOException i){
            System.out.println(i);
        }     
    }
    private void replyCC(String message){
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage(message);
        sock.closeClientConnection();
    }
    
    private void getGUIFields(){
        storeFields = new ArrayList<JTextField>();
        standingFields = new ArrayList<JTextField>();
        pathFields = new ArrayList<ArrayList<JTextField>>();
        ArrayList <JTextField> paths_column1 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column2 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column3 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column4 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column5 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column6 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column7 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column8 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column9 = new ArrayList<JTextField>();
        ArrayList <JTextField> paths_column10 = new ArrayList<JTextField>();
        granaryFields = new ArrayList<JTextField>();
        
        storeFields.add(fi.storeField_1);
        storeFields.add(fi.storeField_2);
        storeFields.add(fi.storeField_3);
        storeFields.add(fi.storeField_4);
        storeFields.add(fi.storeField_5);
        
        standingFields.add(fi.standingField_1);
        standingFields.add(fi.standingField_2);
        standingFields.add(fi.standingField_3);
        standingFields.add(fi.standingField_4);
        standingFields.add(fi.standingField_5);
        
        granaryFields.add(fi.granaryField_1);
        granaryFields.add(fi.granaryField_2);
        granaryFields.add(fi.granaryField_3);
        granaryFields.add(fi.granaryField_4);
        granaryFields.add(fi.granaryField_5);
        
        paths_column1.add(fi.pathField_0_1);
        paths_column1.add(fi.pathField_0_2);
        paths_column1.add(fi.pathField_0_3);
        paths_column1.add(fi.pathField_0_4);
        paths_column1.add(fi.pathField_0_5);
        
        paths_column2.add(fi.pathField_1_1);
        paths_column2.add(fi.pathField_1_2);
        paths_column2.add(fi.pathField_1_3);
        paths_column2.add(fi.pathField_1_4);
        paths_column2.add(fi.pathField_1_5);
        
        paths_column3.add(fi.pathField_2_1);
        paths_column3.add(fi.pathField_2_2);
        paths_column3.add(fi.pathField_2_3);
        paths_column3.add(fi.pathField_2_4);
        paths_column3.add(fi.pathField_2_5);
        
        paths_column4.add(fi.pathField_3_1);
        paths_column4.add(fi.pathField_3_2);
        paths_column4.add(fi.pathField_3_3);
        paths_column4.add(fi.pathField_3_4);
        paths_column4.add(fi.pathField_3_5);
        
        paths_column5.add(fi.pathField_4_1);
        paths_column5.add(fi.pathField_4_2);
        paths_column5.add(fi.pathField_4_3);
        paths_column5.add(fi.pathField_4_4);
        paths_column5.add(fi.pathField_4_5);
        
        paths_column6.add(fi.pathField_5_1);
        paths_column6.add(fi.pathField_5_2);
        paths_column6.add(fi.pathField_5_3);
        paths_column6.add(fi.pathField_5_4);
        paths_column6.add(fi.pathField_5_5);
        
        paths_column7.add(fi.pathField_6_1);
        paths_column7.add(fi.pathField_6_2);
        paths_column7.add(fi.pathField_6_3);
        paths_column7.add(fi.pathField_6_4);
        paths_column7.add(fi.pathField_6_5);
       
        paths_column8.add(fi.pathField_7_1);
        paths_column8.add(fi.pathField_7_2);
        paths_column8.add(fi.pathField_7_3);
        paths_column8.add(fi.pathField_7_4);
        paths_column8.add(fi.pathField_7_5);
        
        paths_column9.add(fi.pathField_8_1);
        paths_column9.add(fi.pathField_8_2);
        paths_column9.add(fi.pathField_8_3);
        paths_column9.add(fi.pathField_8_4);
        paths_column9.add(fi.pathField_8_5);
           
        paths_column10.add(fi.pathField_9_1);
        paths_column10.add(fi.pathField_9_2);
        paths_column10.add(fi.pathField_9_3);
        paths_column10.add(fi.pathField_9_4);
        paths_column10.add(fi.pathField_9_5);
        
        pathFields.add(paths_column1);
        pathFields.add(paths_column2);
        pathFields.add(paths_column3);
        pathFields.add(paths_column4);
        pathFields.add(paths_column5);
        pathFields.add(paths_column6);
        pathFields.add(paths_column7);
        pathFields.add(paths_column8);
        pathFields.add(paths_column9);
        pathFields.add(paths_column10);
    }
}
