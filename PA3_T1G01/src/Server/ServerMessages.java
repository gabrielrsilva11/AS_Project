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
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ServerMessages(Sockets connection, Socket serverSocket, ConnectionInfo lb_info) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.lb_info = lb_info;
    }

    @Override
    public void run() {
        awaitConnections();
    }

    private void awaitConnections() {
        try {

            while (true) {
                //receber string id ou receber um request
                ObjectInputStream input = null;
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof Request) {
                    Request request = (Request) message;
                    ServerWorkThread messageHandler = new ServerWorkThread(request, connection, lb_info);
                    new Thread(messageHandler).start();
                } else if (message instanceof String) {
                    serverId = (Integer) message;
                }
                //acabou
                //replyServer();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
