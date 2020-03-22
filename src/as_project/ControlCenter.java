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
    
    public void sendMessage(String message, JButton button1, JButton button2){
        serverConnect.sendMessage(message);
        if("prepare".equals(message)){
            serverConnect.sendMessage(parameters);
        }
        port +=1;
        Sockets sock = new Sockets();
        sock.startServer(port);
        startWorkerThread(sock,button1, button2);

    }
    
    public void Exit(String message){
        Sockets sock = new Sockets();
        sock.startServer(port);
        serverConnect.sendMessage(message);
        serverConnect.sendMessage(Integer.toString(port));
        startWorkerThread(sock, new JButton(), new JButton());
    }
    
    public void startWorkerThread(Sockets sock, JButton button1, JButton button2){
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

