/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import Server.Server;
import Server.ServerWorkThread;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
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
public class LoadBalancer {

    private Sockets connection;

    private Socket socketServer;

    private MonitorInterface monitor;

    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer();
    }

    public LoadBalancer() {
        connection = new Sockets();
        monitor = new Monitor();
        establishServerConnection();
    }

    private void establishServerConnection() {
        System.out.println("Creating LoadBalancer");
        connection.startServer(80);
        Runnable connectionHandler = new LBConnections(connection, monitor);
        // criar connectionInfo, enviar loadbalancer
        new Thread(connectionHandler).start();
    }
}
