/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import utils.Sockets;

/**
 *
 * @author gabri
 */
public class LoadBalancer {

    private Sockets connection;

    private MonitorInterface monitor;

    private MonitorGUI gui;
    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer();
    }

    public LoadBalancer() {
        connection = new Sockets();
        gui = new MonitorGUI();
        monitor = new Monitor(gui);
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
