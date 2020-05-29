/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Server.Server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class ServerConnections implements Runnable {

    private Sockets connection;
    private Socket socketServer;
    private ConnectionInfo lb_info;
    ServerMessages messageHandler;
    private int serverId;
    private JTextField requests;
    private JTextField completed;
    private Map requestsAnswered;

    private Map processingRequests;

    public ServerConnections(Sockets connection, ConnectionInfo lb_connection, Map requestsAnswered, Map processingRequests,
            JTextField requests, JTextField completed) {
        this.connection = connection;
        this.lb_info = lb_connection;
        this.requestsAnswered = requestsAnswered;
        this.processingRequests = processingRequests;
        this.requests = requests;
        this.completed = completed;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            System.out.println("Server waiting for clients");
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                System.out.println("Connections accepted: " + i);
                messageHandler = new ServerMessages(connection, socketServer, lb_info, requestsAnswered, processingRequests, requests, completed);
                new Thread(messageHandler).start();
                if (i == 1) {
                    Thread.sleep(1000);
                    serverId = messageHandler.getServerID();
                    System.out.println(serverId);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getServerId() {
        return serverId;
    }

    public void exit() {
        messageHandler.exit();
    }
}
