/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.*;

/**
 *
 * @author gabri
 */
public class Server {

    private Sockets connection;

    private ConnectionInfo connectionInfo;

    private int serverId;

    public static void main(String[] args) {
        Server se = new Server();
    }

    public Server() {
        connection = new Sockets();
        // comunicação loadbalancer envia connectionInfo, recebe id e faz serverconnections
        obtainId();
        establishServerConnection();
    }

    private void establishServerConnection() {
        System.out.println("Creating Server");
        connection.startServer(5000);
        requestServerId();
        System.out.println("Waiting for reply");
        Runnable connectionHandler = new ServerConnections(connection);
        // criar connectionInfo, enviar loadbalancer
        new Thread(connectionHandler).start();
    }

    private void obtainId() {
        connectionInfo = new ConnectionInfo("localhost", 5000, 2);
        //serverId = requestServerId(connectionInfo);
    }

    private void requestServerId() {

        connection.startClient("localhost", 80);
        System.out.println("Connected!");
        //outputStream = connection.getClient().getOutputStream();
        //ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        //objectOutputStream.writeObject(connectionInfo);
        connection.sendMessage(connectionInfo);
        connection.sendMessage(connectionInfo);
    }
}
