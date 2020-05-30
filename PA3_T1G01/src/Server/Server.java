/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    private Map<Integer, Request> requestsAnswered;

    private Map<Integer, Request> processingRequests;
    private final ScheduledExecutorService scheduler;

    public static void main(String[] args) {
        Server se = new Server();
    }

    public Server() {
        requestsAnswered = new ConcurrentHashMap<>();
        processingRequests = new ConcurrentHashMap<>();
        connection = new Sockets();
        this.scheduler = Executors.newScheduledThreadPool(1);
        // comunicação loadbalancer envia connectionInfo, recebe id e faz serverconnections
        //establishServerConnection();
        gui = new ServerGUI();
        gui.setVisible(true);
        gui.getProcessing_Text().setEnabled(false);
        gui.getCompleted_Text().setEnabled(false);
        connectButtonListener();
        historyButtonListener();
        exitButtonListener();
        historyCloseButtonListener();
        processingButtonListener();
        completedButtonListener();
        processingCloseButtonListener();
        completedCloseButtonListener();
    }

    private void establishServerConnection() {
        int lb_port = Integer.parseInt(gui.getLBPort_Text().getText());
        gui.getLBPort_Text().setEnabled(false);
        String lb_ip = gui.getLBIP_Text().getText();
        gui.getLBIP_Text().setEnabled(false);
        int server_port = Integer.parseInt(gui.getServerPort_Text().getText());
        gui.getServerPort_Text().setEnabled(false);
        String server_ip = gui.getServerIP_Text().getText();
        gui.getServerIP_Text().setEnabled(false);
        obtainId(server_ip, server_port);
        lbInfo = new ConnectionInfo(lb_ip, lb_port, 3);

        if (connection.startServer(server_port)) {
            System.out.println("Waiting for reply");
            connectionHandler = new ServerConnections(connection, lbInfo, requestsAnswered, processingRequests, gui.getCompleted_Text(), gui.getProcessing_Text());
            new Thread(connectionHandler).start();

            requestServerId();
            scheduler.scheduleAtFixedRate(heartbeat, 10, 10, TimeUnit.SECONDS);
        } else {
            gui.getLBPort_Text().setEnabled(true);
            gui.getLBIP_Text().setEnabled(true);
            gui.getServerPort_Text().setEnabled(true);
            gui.getServerIP_Text().setEnabled(true);
            gui.getButton_Connect().setEnabled(true);
        }
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
            connectButton.setEnabled(false);
            establishServerConnection();
        };

        connectButton.addActionListener(actionListener);
    }

    private void historyButtonListener() {
        JButton historyButton = gui.getHistory_Button();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History Button");
            gui.getHistory_TextArea().setText("");
            gui.getFrame_History().setVisible(true);
            gui.getFrame_History().setSize(415, 250);
            gui.getHistory_TextArea().setEditable(false);
            for (int key : requestsAnswered.keySet()) {
                gui.getHistory_TextArea().append(requestsAnswered.get(key).getFormattedRequest());
            }
            for (int key : processingRequests.keySet()) {
                gui.getHistory_TextArea().append(processingRequests.get(key).getFormattedRequest());
            }
        };
        historyButton.addActionListener(actionListener);
    }

    private void processingButtonListener() {
        JButton processingButton = gui.getButton_Processing();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getProcessing_TextArea().setText("");
            gui.getFrame_Processing().setVisible(true);
            gui.getFrame_Processing().setSize(415, 250);
            gui.getProcessing_TextArea().setEditable(false);
            for (int key : processingRequests.keySet()) {
                gui.getProcessing_TextArea().append(processingRequests.get(key).getFormattedRequest());
            }
        };

        processingButton.addActionListener(actionListener);
    }

    private void completedButtonListener() {
        JButton completedButton = gui.getButton_Completed();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getCompleted_Text().setText("");
            gui.getFrame_Completed().setVisible(true);
            gui.getFrame_Completed().setSize(415, 250);
            gui.getCompleted_TextArea().setEditable(false);
            for (int key : requestsAnswered.keySet()) {
                gui.getCompleted_TextArea().append(requestsAnswered.get(key).getFormattedRequest());
            }
        };

        completedButton.addActionListener(actionListener);

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

    private void historyCloseButtonListener() {
        JButton closeHistoryButton = gui.getButton_CloseHistory();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };

        closeHistoryButton.addActionListener(actionListener);
    }

    private void processingCloseButtonListener() {
        JButton closeProcessingButton = gui.getButton_CloseProcessing();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processing().setVisible(false);
        };

        closeProcessingButton.addActionListener(actionListener);
    }

    private void completedCloseButtonListener() {
        JButton closeCompletedButton = gui.getButton_CloseCompleted();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Completed().setVisible(false);
        };

        closeCompletedButton.addActionListener(actionListener);
    }
    final Runnable heartbeat = new Runnable() {
        public void run() {
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            System.out.println("Heartbeat: " + connectionHandler.getServerId());
            connection.sendMessage("heartbeat:" + connectionHandler.getServerId());
        }
    };
}
