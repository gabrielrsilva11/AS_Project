/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import javax.swing.JButton;
import utils.*;


/**
 *
 * @author gabri
 */
public class Client {
    private ClientGUI gui;
    private Sockets connection;
    private ConnectionInfo ci = null;
    private int RequestsMade;
    private int RequestsProcessed;
    
    public static void main(String[] args){
        Client cl = new Client();
    }
    
    public Client(){
        gui = new ClientGUI();
        gui.getButton_Request().setEnabled(false);
        gui.setVisible(true);
        connectButtonListener();
        requestButtonListener();
        RequestsMade=0;
        RequestsProcessed=0;
    }
    
    private void connectButtonListener(){
        JButton connectButton = gui.getButton_Connect();
        
        ActionListener actionListener = (ActionEvent actionEvent)-> {
            System.out.println("Connect button");
            establishConnection();
            connectButton.setEnabled(false);
            gui.getButton_Request().setEnabled(true);
        };
        
        connectButton.addActionListener(actionListener);
    }
    
    private void requestButtonListener(){
        JButton requestButton = gui.getButton_Request();
        
        ActionListener actionListener = (ActionEvent actionEvent)-> {
            System.out.println("Request Button");
            System.out.println("Sending Request");
            Request re = getRequestInfo();
            connection.sendMessage(re);
            RequestsMade += 1;
            gui.getPR_Text().setText(Integer.toString(RequestsMade));
        };
        
        requestButton.addActionListener(actionListener);
    }
    
    private Request getRequestInfo(){
        int ni = Integer.parseInt(gui.getNI_Text().getText());
        int code = 1;
        return new Request(code, ci, ni);
    }
    
    private void establishConnection(){
        System.out.println("Establishing Connection");
        String ip = gui.getIP_Text().getText();
        int send_port = Integer.parseInt(gui.getPort_Text().getText());
        int reply_port = Integer.parseInt(gui.getReplyPort_Text().getText());
        
        gui.getIP_Text().setEnabled(false);
        gui.getPort_Text().setEnabled(false);
        gui.getReplyPort_Text().setEnabled(false);
        
        connection = new Sockets();
        System.out.println("Starting Client");
        connection.startClient(ip, send_port);
        System.out.println("Sending Client Info");
        ci = new ConnectionInfo(ip, reply_port, 1);
        connection.sendMessage(ci);
        System.out.println("Waiting for reply");
        connection.startServer(reply_port);
        Runnable connectionHandler = new ClientConnections(connection);
        new Thread(connectionHandler).start();
    }
}
