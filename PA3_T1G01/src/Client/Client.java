package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import utils.*;

/**
 * Client - Responsible for sending Requests to the Load Balancer and getting
 * replies from the server when the request is completed.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Client {

    /**
     * GUI for the client program.
     */
    private ClientGUI gui;
    /**
     * Sockets class used to send and receive connections from the load balancer
     * and the server.
     */
    private Sockets connection;
    /**
     * Variable that stores the client own connection information
     */
    private ConnectionInfo ci = null;
    /**
     * Number of requests the client has made
     */
    private int requestsMade;
    /**
     * Concurrent map that saves the requests that have been completed.
     */
    private Map<Integer, Request> requestsAnswered;
    /**
     * Concurrent map that saves the requests that are being processed.
     */
    private Map<Integer, Request> processed;
    /**
     * Class that deals with accepting new connections to the client.
     */
    private ClientConnections connectionHandler;

    /**
     * Main function of the client process, used to start a new client process.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Client cl = new Client();

    }

    /**
     * Client class constructor.
     *
     * Initializes the GUI and all the necessary variables.
     */
    public Client() {
        requestsAnswered = new ConcurrentHashMap<>();
        processed = new ConcurrentHashMap<>();
        gui = new ClientGUI();
        gui.getButton_Request().setEnabled(false);
        gui.setVisible(true);
        connectButtonListener();
        requestButtonListener();
        historyButtonListener();
        historyCloseButtonListener();
        processedCloseButtonListener();
        processedButtonListener();
        requestsMade = 0;
    }

    /**
     * Method to get information about a new request that will be sent out.
     *
     * @return an initialized Request variable ready to be sent.
     */
    private Request getRequestInfo() {
        int ni = Integer.parseInt(gui.getNI_Text().getText());
        int code = 1;
        int requestId = 1000 * connectionHandler.getClientId() + requestsMade;
        return new Request(code, ci, ni, requestId, connectionHandler.getClientId());
    }

    /**
     * Method that establishes the connection to the load balancer and asks it
     * for a clientID as well as starting the thread to accept new connections.
     * If we try to initialize the client on a port that's already taken an
     * error will pop up asking for a new port.
     */
    private void establishConnection() {
        String ip = gui.getIP_Text().getText();
        int send_port = Integer.parseInt(gui.getPort_Text().getText());
        int reply_port = Integer.parseInt(gui.getReplyPort_Text().getText());

        gui.getIP_Text().setEnabled(false);
        gui.getPort_Text().setEnabled(false);
        gui.getReplyPort_Text().setEnabled(false);
        connection = new Sockets();
        connection.startClient(ip, send_port);
        ci = new ConnectionInfo(ip, reply_port, 1);
        if (connection.startServer(reply_port)) {
            connectionHandler = new ClientConnections(connection, requestsAnswered, gui.getPR_Text(), processed);
            new Thread(connectionHandler).start();
            connection.sendMessage(ci);
        } else {
            gui.getButton_Request().setEnabled(false);
            gui.getButton_Connect().setEnabled(true);
            gui.getIP_Text().setEnabled(true);
            gui.getPort_Text().setEnabled(true);
            gui.getReplyPort_Text().setEnabled(true);
        }
    }

    /**
     * Listener method for the close button on the processed panel
     */
    private void processedCloseButtonListener() {
        JButton closeProcessedButton = gui.getButton_CloseProcessed();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processed().setVisible(false);
        };

        closeProcessedButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the close button on the history panel
     */
    private void historyCloseButtonListener() {
        JButton closeHistoryButton = gui.getButton_CloseHistory();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };

        closeHistoryButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the processed button
     */
    private void processedButtonListener() {
        JButton processedButton = gui.getButton_Processing();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getProcessed_TextArea().setText("");
            gui.getFrame_Processed().setVisible(true);
            gui.getFrame_Processed().setSize(415, 250);
            gui.getProcessed_TextArea().setEditable(false);
            for (int key : processed.keySet()) {
                gui.getProcessed_TextArea().append(processed.get(key).getFormattedRequest());
            }
        };
        processedButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the history button
     */
    private void historyButtonListener() {
        JButton historyButton = gui.getButton_History();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getProcessed_TextArea().setText("");
            gui.getFrame_History().setVisible(true);
            gui.getFrame_History().setSize(415, 250);
            gui.getHistory_TextArea().setEditable(false);
            for (int key : processed.keySet()) {
                gui.getProcessed_TextArea().append(processed.get(key).getFormattedRequest());
            }
            for (int key : requestsAnswered.keySet()) {
                gui.getHistory_TextArea().append(requestsAnswered.get(key).getFormattedRequest());
            }
        };

        historyButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the connect button
     */
    private void connectButtonListener() {
        JButton connectButton = gui.getButton_Connect();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            connectButton.setEnabled(false);
            gui.getButton_Request().setEnabled(true);
            establishConnection();

        };

        connectButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the Request button on the processed panel
     */
    private void requestButtonListener() {
        JButton requestButton = gui.getButton_Request();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Sending Request");
            Request re = getRequestInfo();
            connection.sendMessage(re);
            processed.put(re.getRequestID(), re);
            requestsMade += 1;
            gui.getRM_Text().setText(Integer.toString(requestsMade));
        };

        requestButton.addActionListener(actionListener);
    }
}
