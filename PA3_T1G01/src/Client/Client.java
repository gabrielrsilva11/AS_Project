/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import utils.*;

/**
 *
 * @author gabri
 */
public class Client {

    private ClientGUI gui;
    private Sockets connection;
    private ConnectionInfo ci = null;
    private int requestsMade;
    private Map<Integer, Request> requestsAnswered;
    private ClientConnections connectionHandler;
    
    public static void main(String[] args) {
        Client cl = new Client();

    }

    public Client() {
        requestsAnswered = new ConcurrentHashMap<>();
        gui = new ClientGUI();
        gui.getButton_Request().setEnabled(false);
        gui.setVisible(true);
        connectButtonListener();
        requestButtonListener();
        historyButtonListener();
        historyCloseButtonListener();
        requestsMade = 0;
    }

    private Request getRequestInfo() {
        int ni = Integer.parseInt(gui.getNI_Text().getText());
        int code = 1;
        int requestId = 1000*connectionHandler.getClientId() + requestsMade;
        return new Request(code, ci, ni, requestId, connectionHandler.getClientId());
    }

    private void establishConnection() {
        System.out.println("Establishing Connection");
        String ip = gui.getIP_Text().getText();
        int send_port = Integer.parseInt(gui.getPort_Text().getText());
        int reply_port = Integer.parseInt(gui.getReplyPort_Text().getText());

        gui.getIP_Text().setEnabled(false);
        gui.getPort_Text().setEnabled(false);
        gui.getReplyPort_Text().setEnabled(false);

        connection = new Sockets();
        System.out.println("Starting Client");
        connection.startClient(ip, send_port);
        System.out.println("Sending Client Info");
        ci = new ConnectionInfo(ip, reply_port, 1);
        connection.sendMessage(ci);
        System.out.println("Waiting for reply");
        if (connection.startServer(reply_port)) {
            connectionHandler = new ClientConnections(connection, requestsAnswered, gui.getPR_Text());
            new Thread(connectionHandler).start();
        }else {
            gui.getButton_Request().setEnabled(false);
            gui.getButton_Connect().setEnabled(true);
        }
    }

    private void historyCloseButtonListener() {
        JButton closeHistoryButton = gui.getButton_CloseHistory();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };

        closeHistoryButton.addActionListener(actionListener);
    }

    private void historyButtonListener() {
        JButton historyButton = gui.getButton_History();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History");
            gui.getFrame_History().setVisible(true);
            gui.getFrame_History().setSize(415, 250);
            gui.getHistory_TextArea().setEditable(false);
            for (int key : requestsAnswered.keySet()) {
                gui.getHistory_TextArea().append(requestsAnswered.get(key).getFormattedRequest());
                System.out.println(requestsAnswered.get(key).getFormattedRequest());
            }
        };

        historyButton.addActionListener(actionListener);
    }

    private void connectButtonListener() {
        JButton connectButton = gui.getButton_Connect();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Connect button");
            connectButton.setEnabled(false);
            gui.getButton_Request().setEnabled(true);
            establishConnection();
            
        };

        connectButton.addActionListener(actionListener);
    }

    private void requestButtonListener() {
        JButton requestButton = gui.getButton_Request();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Request Button");
            System.out.println("Sending Request");
            Request re = getRequestInfo();
            connection.sendMessage(re);
            requestsMade += 1;
            gui.getRM_Text().setText(Integer.toString(requestsMade));
        };

        requestButton.addActionListener(actionListener);
    }
}
