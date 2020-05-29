/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import utils.*;

/**
 *
 * @author gabri
 */
public class Server {
    
    private Sockets connection;
    
    private ConnectionInfo connectionInfo;
    
    private ServerGUI gui;
    
    ServerConnections connectionHandler;
    
    ConnectionInfo lbInfo;
    
    public static void main(String[] args) {
        Server se = new Server();
    }
    
    public Server() {
        connection = new Sockets();
        // comunicação loadbalancer envia connectionInfo, recebe id e faz serverconnections
        establishServerConnection();
        gui = new ServerGUI();
        gui.setVisible(true);
        connectButtonListener();
        historyButtonListener();
        exitButtonListener();
    }
    
    private void establishServerConnection() {
        int lb_port = Integer.parseInt(gui.getLBPort_Text().getText());
        String lb_ip = gui.getLBIP_Text().getText();
        int server_port = Integer.parseInt(gui.getServerPort_Text().getText());
        String server_ip = gui.getServerIP_Text().getText();
        obtainId(server_ip, server_port);
        lbInfo = new ConnectionInfo(lb_ip, lb_port, 3);
        
        System.out.println("Creating Server");
        connection.startServer(server_port);
        requestServerId();
        
        System.out.println("Waiting for reply");
        connectionHandler = new ServerConnections(connection, lbInfo);
        // criar connectionInfo, enviar loadbalancer
        new Thread(connectionHandler).start();
    }
    
    private void obtainId(String serverIp, int serverPort) {
        connectionInfo = new ConnectionInfo(serverIp, serverPort, 2);
    }
    
    private void requestServerId() {
        connection.startClient(lbInfo.getIp(),lbInfo.getPort());
        System.out.println("Connected!");
        connection.sendMessage(connectionInfo);
    }
    
    private void connectButtonListener() {
        JButton connectButton = gui.getButton_Connect();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Connect Button");            
            establishServerConnection();
            connectButton.setEnabled(false);
            gui.getButton_Connect().setEnabled(false);
        };
        
        connectButton.addActionListener(actionListener);
    }
    
    private void historyButtonListener() {
        JButton historyButton = gui.getHistory_Button();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History Button");
            //meter aqui as cenas do history
        };
        
        historyButton.addActionListener(actionListener);
    }
    
    private void exitButtonListener() {
        JButton exitButton = gui.getExit_Button();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            //connectionHandeler.exit();
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            connection.sendMessage("exit: " + connectionHandler.getServerId());            
        };
        
        exitButton.addActionListener(actionListener);
    }
}
