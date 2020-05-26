/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import utils.*;

/**
 *
 * @author gabri
 */
public class Server {

    private Sockets connection;

    public static void main(String[] args) {
        Server se = new Server();
    }

    public Server() {
        connection = new Sockets();
        establishServerConnection();
    }

    private void establishServerConnection() {
        System.out.println("Creating Server");
        connection.startServer(5001);
        Runnable connectionHandler = new ServerConnections(connection, 1);
        new Thread(connectionHandler).start();
    }
}
