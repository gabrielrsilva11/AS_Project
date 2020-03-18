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
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

/**
 *
 * @author gabri
 */
public class FarmInfrastructure {
    Sockets clientSocket = null;
    private JFrame gui = null;
    
    public FarmInfrastructure(){
        FI_GUI fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(440, 650);
        gui.setResizable(true);
        gui.add(fi);
        clientSocket = new Sockets();
        clientSocket.startServer(5000);
    }
   
    public static void main(String[] args) {
        //FarmInfrastructure farm = new FarmInfrastructure();
      
        
        //Aqui recebe a chamada da gui com o número de farmers e cria um numero de threads
        int nFarmers = 3;
        //Recebe o número de steps que cada um vai andar
        int nSteps = 2;
        
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
    }  
}
