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
    private int numFarmers;
    
    public MonitorLauncher(String message, StoreHouseMonitor shMonitor, int numFarmers){
        this.message = message;
        this.shMonitor = shMonitor;
        this.numFarmers = numFarmers;
    }
    
    public MonitorLauncher(String message, StandingAreaMonitor saMonitor){
        this.message = message;
        this.saMonitor = saMonitor;
    }
    
    @Override
    public void run(){
        System.out.println(message);
        switch(message){
            case "prepare":
                shMonitor.prepare(numFarmers);
                System.out.println("Responder ao CC");
                replyCC();
                break;
            case "start":
                saMonitor.proceedToThePath();
                System.out.println("Responder ao CC");
                replyCC();
                break;
        }
    }
    
    private void replyCC(){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeClientConnection();
    }
}