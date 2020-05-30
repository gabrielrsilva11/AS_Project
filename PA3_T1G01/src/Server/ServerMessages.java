/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class ServerMessages implements Runnable {

    private Sockets connection;
    private Socket socketServer;
    private ConnectionInfo lb_info;
    private int serverId;
    private boolean active;
    private Map requestsAnswered;
    private Map processingRequests;
    private JTextField requests;
    private JTextField completed;

    public ServerMessages(Sockets connection, Socket serverSocket, ConnectionInfo lb_info, Map requestsAnswered, Map processingRequests,
            JTextField requests, JTextField completed) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.lb_info = lb_info;
        this.active = true;
        this.requestsAnswered = requestsAnswered;
        this.processingRequests = processingRequests;
        this.requests = requests;
        this.completed = completed;
    }

    @Override
    public void run() {
        awaitConnections();
    }

    private void awaitConnections() {
        try {
            while (active) {
                //receber string id ou receber um request
                ObjectInputStream input = null;
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof Request) {
                    Request request = (Request) message;
                    ServerWorkThread messageHandler = new ServerWorkThread(request, connection, lb_info, requests, completed , requestsAnswered, processingRequests);
                    new Thread(messageHandler).start();
                } else if (message instanceof String) {
                    if (message.equals("exit")) {
                        System.exit(0);
                    } else {
                        serverId = Integer.parseInt((String) message);
                        JOptionPane success = new JOptionPane();
                        success.showMessageDialog(null, "Connection Successful", "Connection", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public int getServerID() {
        return serverId;
    }

    public void exit() {
        this.active = false;
    }
}
