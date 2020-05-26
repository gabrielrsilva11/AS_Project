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
    private int serverId;
        
    public ServerConnections(Sockets connection, int serverId) {
        this.connection = connection;
        this.serverId = serverId;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            System.out.println("Waiting for clients");
            while (true) {
                System.out.println("yo");
                socketServer = connection.getServer().accept();
                i += 1;
                System.out.println("Connections accepted: " + i);
                ServerMessages messageHandler = new ServerMessages(connection, socketServer, serverId);
                new Thread(messageHandler).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
