/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import Server.Server;
import Server.ServerConnections;
import Server.ServerMessages;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class LBMessages implements Runnable {

    private Sockets connection;
    private Socket socketServer;
    //private int serverId;
    private MonitorInterface monitor;

    public LBMessages(Sockets connection, Socket serverSocket, MonitorInterface monitor) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.monitor = monitor;
        //this.serverId = serverId;
    }

    @Override
    public void run() {
        awaitConnections();
    }

    private void awaitConnections() {
        try {
            while (true) {
                ObjectInputStream input = null;
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof ConnectionInfo) {
                    ConnectionInfo info = (ConnectionInfo) message;
                    //CLIENT
                    if (info.getType() == 1) {
                        System.out.println("Requesting client connection!");
                        int clientId = monitor.generateClientId();
                        replyClientId(info, clientId);

                    } //SERVER
                    else if (info.getType() == 2) {
                        System.out.println("Requesting server connection!");
                        int serverId = monitor.registerServer(info);
                        replyServerId(info, serverId);
                    }
                    //replyClient(info);
                    // case client, atribuir id e responder(id)
                    //server guardar o connectionInfo e atribuir id 
                    //enviar id para o server
                } else if (message instanceof Request) {
                    System.out.println(message.getClass());
                    Request re = (Request) message;
                    re.setServerID(monitor.chooseServer());
                    //replyMonitor();
                    //ServerWorkThread st = new ServerWorkThread(re, connection);
                    //new Thread(st).start();
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

    private void replyClientId(ConnectionInfo info, int clientId) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage(clientId + "");
        connection.closeClientConnection();
    }

    private void replyServerId(ConnectionInfo info, int serverId) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage(serverId + "");
        connection.closeClientConnection();
    }

    //mesmo que o server messages, comunicar com o monitor para decrementar o uso dos servidores
}
