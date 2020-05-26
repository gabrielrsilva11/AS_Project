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
    private int serverId;

    public ServerMessages(Sockets connection, Socket serverSocket, int serverId) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.serverId = serverId;
    }

    @Override
    public void run() {
        awaitConnections();
    }

    private void awaitConnections() {
        try {
            ObjectInputStream input = null;
            while (true) {
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof ConnectionInfo) {
                    System.out.println(message.getClass());
                    ConnectionInfo info = (ConnectionInfo) message;
                    System.out.println(info.getIp());
                    replyClient(info);
                } else if (message instanceof Request) {
                    System.out.println(message.getClass());
                    Request re = (Request) message;
                    re.setServerID(serverId);
                    ServerWorkThread st = new ServerWorkThread(re, connection);
                    new Thread(st).start();
                }
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
