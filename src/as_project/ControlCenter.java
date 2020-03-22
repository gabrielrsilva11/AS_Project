/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import javax.swing.JTextField;


/**
 *
 * @author gabriel
 */
public class ControlCenter{
    private Sockets serverConnect = null;
    private String parameters;
    private JTextField corn;
    private int numFarmers;
    private int port;
    
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient("127.0.0.1", 5000);
        System.out.println("Client Started");
        port = 5000;
    }
    
    public void setCorn(JTextField corn){
        this.corn = corn;
    }
    
    public void setNumFarmers(int numFarmers){
        this.numFarmers = numFarmers;
    }
    public void setParameters(String parameters){
        this.parameters=parameters;
    }
    public int getPort(){
        return port;
    }
    
    public void sendMessage(String message){
        port +=1;
        serverConnect.sendMessage(message);
        if("prepare".equals(message)){
            serverConnect.sendMessage(parameters);
        }
    }
    
    
    public void Exit(String message){
        port += 1;
        Sockets sock = new Sockets();
        sock.startServer(port);
        serverConnect.sendMessage(message);
        serverConnect.sendMessage(Integer.toString(port));
        
    }
}

