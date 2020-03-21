/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import as_project.threads.ControlCenterMessage;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 *
 * @author gabriel
 */
public class ControlCenter{
    private Sockets serverConnect = null;
    private int port;
    private String parameters;
    
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient("127.0.0.1", 5000);
        port = 5001;
    }
    
    public void setParameters(String parameters){
        this.parameters=parameters;
    }
    
    public void sendMessage(String message, JToggleButton button1, JButton button2){
        serverConnect.sendMessage(message);
        if(message == "prepare"){
            serverConnect.sendMessage(parameters);
        }
        ControlCenterMessage await = new ControlCenterMessage(button1, button2, port);
        await.start();
    }
    
    public void Exit(String message){
        serverConnect.sendMessage(message);
        ControlCenterMessage await = new ControlCenterMessage(port);
        await.start();
        while(!await.isAlive()){
            System.out.println("End of Simulation");
            System.exit(0);
        }
    }
    
}

