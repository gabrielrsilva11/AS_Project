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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionInfo;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class ServerConnections implements Runnable {

    private Sockets connection;
    private Socket socketServer;
    private ConnectionInfo lb_info;
        
    public ServerConnections(Sockets connection, ConnectionInfo lb_connection) {
        this.connection = connection;
        this.lb_info = lb_connection;
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
                ServerMessages messageHandler = new ServerMessages(connection, socketServer, lb_info);
                new Thread(messageHandler).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
