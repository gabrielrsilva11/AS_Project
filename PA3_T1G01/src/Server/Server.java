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
 * Server - This class send a request to the LoadBalancer to receive a ServerId
 * and wait to further requests
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Server {

    /**
     * Server connection
     */
    private Sockets connection;

    /**
     * Server connection information (ip, port)
     */
    private ConnectionInfo connectionInfo;

    /**
     * Server GUI
     */
    private ServerGUI gui;

    /**
     * Accept connections
     */
    private ServerConnections connectionHandler;

    /**
     * LoadBalancer connection information (ip, port)
     */
    private ConnectionInfo lbInfo;

    /**
     * Server identification
     */
    private int serverId;

    /**
     * Server requests that where requested and answered
     */
    private Map<Integer, Request> requestsAnswered;

    /**
     * Server processing requests
     */
    private Map<Integer, Request> processingRequests;

    /**
     * Server scheduler to send heartbeat
     */
    private final ScheduledExecutorService scheduler;

    /**
     * Method to run the program, starts the Server
     *
     * @param args arguments used when running the program (not used)
     */
    public static void main(String[] args) {
        Server se = new Server();
    }

    /**
     * Server class constructor
     */
    public Server() {
        requestsAnswered = new ConcurrentHashMap<>();
        processingRequests = new ConcurrentHashMap<>();
        connection = new Sockets();
        this.scheduler = Executors.newScheduledThreadPool(1);
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

    /**
     * Establish server - LoadBalancer connection and request serverId on
     * connection Heartbeat scheduler start
     */
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

    /**
     * Create server connection information
     */
    private void obtainId(String serverIp, int serverPort) {
        connectionInfo = new ConnectionInfo(serverIp, serverPort, 2);
    }

    /**
     * Request server identification to the LoadBalancer
     */
    private void requestServerId() {
        connection.startClient(lbInfo.getIp(), lbInfo.getPort());
        System.out.println("Connected!");
        connection.sendMessage(connectionInfo);
    }

    /**
     * Listener method for the connect button
     */
    private void connectButtonListener() {
        JButton connectButton = gui.getButton_Connect();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            connectButton.setEnabled(false);
            establishServerConnection();
        };

        connectButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the history button
     */
    private void historyButtonListener() {
        JButton historyButton = gui.getHistory_Button();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
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

    /**
     * Listener method for the processing button
     */
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

    /**
     * Listener method for the completed button
     */
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

    /**
     * Listener method for the exit button
     */
    private void exitButtonListener() {
        JButton exitButton = gui.getExit_Button();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            scheduler.shutdown();
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            connection.sendMessage("exit:" + connectionHandler.getServerId());
        };

        exitButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the close button
     */
    private void historyCloseButtonListener() {
        JButton closeHistoryButton = gui.getButton_CloseHistory();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };

        closeHistoryButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the processing close button
     */
    private void processingCloseButtonListener() {
        JButton closeProcessingButton = gui.getButton_CloseProcessing();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processing().setVisible(false);
        };

        closeProcessingButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the completed close button
     */
    private void completedCloseButtonListener() {
        JButton closeCompletedButton = gui.getButton_CloseCompleted();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Completed().setVisible(false);
        };

        closeCompletedButton.addActionListener(actionListener);
    }

    /**
     * Runnable to send heartbeat message
     */
    final Runnable heartbeat = new Runnable() {
        public void run() {
            connection.startClient(lbInfo.getIp(), lbInfo.getPort());
            System.out.println("Heartbeat: " + connectionHandler.getServerId());
            connection.sendMessage("heartbeat:" + connectionHandler.getServerId());
        }
    };
}
