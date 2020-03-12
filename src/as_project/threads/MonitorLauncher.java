/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.threads;
import as_project.monitors.StoreHouseMonitor;
import as_project.Sockets;
/**
 *
 * @author gabri
 */
public class MonitorLauncher implements Runnable{
    private String message;
    private StoreHouseMonitor shMonitor;
    
    public MonitorLauncher(String message, StoreHouseMonitor shMonitor){
        this.message = message;
        this.shMonitor = shMonitor;
    }
    
    @Override
    public void run(){
        switch(message){
            case "prepare":
                System.out.println("Caso prepare");
                shMonitor.readyToContinue();
                System.out.println("Responder ao CC");
                replyCC();
        }
    }
    
    private void replyCC(){
        Sockets sock = new Sockets();
        sock.startClient("127.0.0.1", 5001);
        sock.sendMessage("terminado");
        sock.closeAllConnections();
    }
}