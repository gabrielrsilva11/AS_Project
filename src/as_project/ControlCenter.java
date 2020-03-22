/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import as_project.threads.ControlCenterMessage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingWorker;

/**
 *
 * @author gabriel
 */
public class ControlCenter{
    private Sockets serverConnect = null;
    private final int port;
    private String parameters;
    private JTextField corn;
    private int numFarmers;
    
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient("127.0.0.1", 5000);
        port = 5001;
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
    
    public void sendMessage(String message, JToggleButton button1, JButton button2){
        System.out.println("Message to send: " + message);
        serverConnect.sendMessage(message);
        if(message == "prepare"){
            serverConnect.sendMessage(parameters);
        }
        startWorkerThread(button1, button2);

    }
    
    public void Exit(String message){
        serverConnect.sendMessage(message);
        startWorkerThread(new JToggleButton(), new JButton());
    }
    
    public void startWorkerThread(JToggleButton button1, JButton button2){
        Sockets sock = new Sockets();
        sock.startServer(5001);
        SwingWorker sw1 = new SwingWorker(){
            @Override
            protected String doInBackground() throws Exception{
                try{
                    DataInputStream input;
                    input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
                    String message = "";
                    message = input.readUTF();
                    System.out.println(message);
                    if("exit".equals(message))
                    {
                        System.out.println("Simulação terminada");
                        System.exit(0);
                    }
                    else if("coletar".equals(message)){
                        String message2=input.readUTF();
                        corn.setText(message2);
                        button1.setEnabled(true);
                        button2.setEnabled(true);
                    }else{
                        button1.setEnabled(true);
                        button2.setEnabled(true);
                    }
                }catch(IOException i){
                    System.out.println(i);
                }
                sock.closeServerConnection();
                return "worker done";
            }
            @Override
            protected void done(){
                System.out.println("Butoes toggled");
            }
        };
        sw1.execute();
    }
}

