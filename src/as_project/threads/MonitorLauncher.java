/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.threads;
import as_project.monitors.*;
import as_project.Sockets;
/**
 *
 * @author gabri
 */
public class MonitorLauncher implements Runnable{
    private String message = null;
    private StoreHouseMonitor shMonitor = null;
    private StandingAreaMonitor saMonitor = null;
    private PathMonitor paMonitor = null;
    private GranaryMonitor grMonitor = null;
    private int numFarmers;
    
    public MonitorLauncher(String message, StoreHouseMonitor shMonitor, int numFarmers){
        this.message = message;
        this.shMonitor = shMonitor;
    }
    
    public MonitorLauncher(String message, StandingAreaMonitor saMonitor){
        this.message = message;
        this.saMonitor = saMonitor;
    }
    
    public MonitorLauncher(String message, PathMonitor paMonitor){
        this.message = message;
        this.paMonitor = paMonitor;
    }
    
    public MonitorLauncher(String message, GranaryMonitor grMonitor){
        this.message = message;
        this.grMonitor = grMonitor;
    }
    
    @Override
    public void run(){
        switch(message){
            case "prepare":
                System.out.println("Caso prepare");
                shMonitor.prepare(numFarmers);
                replyCC();
                break;
            case "start":
                System.out.println("Caso start");
                saMonitor.proceedToThePath();
                replyCC();
                break;
            case "collect":
                System.out.println("Caso collect");
                paMonitor.proceedToTheGranary();
                replyCC();
                break;
            case "return":
                System.out.println("Caso return");
                grMonitor.collect();
                break;
        }
    }
    
    private void replyCC(){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeAllConnections();
    }
}