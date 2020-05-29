/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import utils.*;

/**
 *
 * @author gabri
 */
public class Server {

    private Sockets connection;

    private ConnectionInfo connectionInfo;

    private ServerGUI gui;

    ServerConnections connectionHandler;

    ConnectionInfo lbInfo;

    private int serverId;

    private final ScheduledExecutorService scheduler;

    public static void main(String[] args) {
        Server se = new Server();
    }

    public Server() {
        connection = new Sockets();
        this.scheduler = Executors.newScheduledThreadPool(1);
        // comunicação loadbalancer envia connectionInfo, recebe id e faz serverconnections
        //establishServerConnection();
        gui = new ServerGUI();
        gui.setVisible(true);
        connectButtonListener();
        historyButtonListener();
        exitButtonListener();
    }

    private void establishServerConnection() {
        int lb_port = Integer.parseInt(gui.getLBPort_Text().getText());
        String lb_ip = gui.getLBIP_Text().getText();
        int server_port = Integer.parseInt(gui.getServerPort_Text().getText());
        String server_ip = gui.getServerIP_Text().getText();
        obtainId(server_ip, server_port);
        lbInfo = new ConnectionInfo(lb_ip, lb_port, 3);

        System.out.println("Creating Server");
        connection.startServer(server_port);

        System.out.println("Waiting for reply");
        connectionHandler = new ServerConnections(connection, lbInfo);
        new Thread(connectionHandler).start();

        requestServerId();
        scheduler.scheduleAtFixedRate(heartbeat, 10, 10, TimeUnit.SECONDS);
    }

    private void obtainId(String serverIp, int serverPort) {
        connectionInfo = new ConnectionInfo(serverIp, serverPort, 2);
    }

    private void requestServerId() {
        connection.startClient(lbInfo.getIp(), lbInfo.getPort());
        System.out.println("Connected!");
        connection.sendMessage(connectionInfo);
        //connection.closeClientConnection();
    }

    private void connectButtonListener() {
        JButton connectButton = gui.getButton_Connect();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Connect Button");
            establishServerConnection();
            connectButton.setEnabled(false);
            gui.getButton_Connect().setEnabled(false);
        };

        connectButton.addActionListener(actionListener);
    }

    private void historyButtonListener() {
        JButton historyButton = gui.getHistory_Button();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History Button");
            //meter aqui as cenas do history
        };

        historyButton.addActionListener(actionListener);
    }

    private void exitButtonListener() {
        JButton exitButton = gui.getExit_Button();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            scheduler.shutdown();
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            connection.sendMessage("exit:" + connectionHandler.getServerId());
        };

        exitButton.addActionListener(actionListener);
    }

    final Runnable heartbeat = new Runnable() {
        public void run() {
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            System.out.println("Heartbeat: " + connectionHandler.getServerId());
            connection.sendMessage("heartbeat:" + connectionHandler.getServerId());
        }
    };
}
