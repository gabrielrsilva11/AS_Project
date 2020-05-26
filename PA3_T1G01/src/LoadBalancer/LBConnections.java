/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import Server.ServerConnections;
import Server.ServerMessages;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class LBConnections implements Runnable{

    private Sockets connection;
    private Socket socketServer;

    public LBConnections(Sockets connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            System.out.println("Waiting for clients");
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                System.out.println("Connections accepted: " + i);
                LBMessages messageHandler = new LBMessages();
                new Thread(messageHandler).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
