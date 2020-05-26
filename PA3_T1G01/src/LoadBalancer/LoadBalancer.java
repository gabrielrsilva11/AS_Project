/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import Server.Server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionInfo;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class LoadBalancer {

    private Sockets connection;

    public static void main(String[] args) {
        Server se = new Server();
    }

    public LoadBalancer() {
        connection = new Sockets();
        establishServerConnection();
        awaitConnections();
    }

    private void establishServerConnection() {
        System.out.println("Creating Server");
        connection.startServer(5000);
    }

    private void awaitConnections() {
        try {
            ObjectInputStream input = null;
            input = new ObjectInputStream(new BufferedInputStream(connection.getSocketServer().getInputStream()));
            Object message = null;
            while (true) {
                message = input.readObject();
                ConnectionInfo info = (ConnectionInfo) message;
                System.out.println(info.getIp());
                replyClient(info);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void replyClient(ConnectionInfo info) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage("ok");
        connection.closeClientConnection();
    }
}
