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
    
    public static void main(String[] args) {
        Server se = new Server();
    }
    
    public Server() {
        connection = new Sockets();
        // comunicação loadbalancer envia connectionInfo, recebe id e faz serverconnections
        establishServerConnection();
        gui = new ServerGUI();
        gui.setVisible(true);
    }
    
    private void establishServerConnection() {
        int lb_port = Integer.parseInt(gui.getLBPort_Text().getText());
        String lb_ip = gui.getLBIP_Text().getText();
        int server_port = Integer.parseInt(gui.getServerPort_Text().getText());
        String server_ip = gui.getServerIP_Text().getText();
        obtainId(server_ip, server_port);
        ConnectionInfo lb_info = new ConnectionInfo(lb_ip, lb_port, 3);
        System.out.println("Creating Server");
        connection.startServer(server_port);
        requestServerId(lb_ip, lb_port);
        System.out.println("Waiting for reply");
        Runnable connectionHandler = new ServerConnections(connection, lb_info);
        // criar connectionInfo, enviar loadbalancer
        new Thread(connectionHandler).start();
    }
    
    private void obtainId(String serverIp, int serverPort) {
        connectionInfo = new ConnectionInfo(serverIp, serverPort, 2);
    }
    
    private void requestServerId(String lbIp, int lbPort) {
        connection.startClient(lbIp, lbPort);
        System.out.println("Connected!");
        connection.sendMessage(connectionInfo);
        connection.closeClientConnection();
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
}
