/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.Server;
import Server.ServerConnections;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class ClientConnections implements Runnable {

    private Sockets connection;
    private Socket socketServer;

    public ClientConnections(Sockets connection) {
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
                awaitConnections();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void awaitConnections() {
        try {
            ObjectInputStream input = null;
            input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
            Object message = null;
            message = input.readObject();
            System.out.println(message.getClass());
            if (message instanceof String) {
                System.out.println(message);
            }else if( message instanceof Request){
                System.out.println("Request Complete");
                Request re = (Request) message;
                System.out.println("Resultado: " + re.getReply());
            }
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
