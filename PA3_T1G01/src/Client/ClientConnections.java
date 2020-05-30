package Client;

import Server.Server;
import Server.ServerConnections;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import utils.Request;
import utils.Sockets;

/**
 * ClientConnections - Responsible for handling the connections to the client.
 * Accepts new connections and launches a thread to handle the messages.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ClientConnections implements Runnable {

    /**
     * Sockets class used to send and receive connections from the load balancer
     * and the server.
     */
    private Sockets connection;
    /**
     * Socket variable where we will accept new connections from
     */
    private Socket socketServer;
    /**
     * Processed request text field to be updated
     */
    private JTextField PRField;
    /**
     * Processed requests counter
     */
    private int requestsProcessed;
    /**
     * Concurrent map that saves the requests that have been completed.
     */
    private Map requestsAnswered;
    /**
     * Concurrent map that saves the requests that are being processed.
     */
    private Map processed;
    /**
     * Variable containing the clientId attributed by the load balancer
     */
    private int clientId;

    /**
     * ClientConnections class constructor.
     *
     * @param connection Sockets - variable created on the Client class.
     * @param requestsAnswered ConcurrentMap - to store the completed requests
     * @param PRField JTextField - to update the number of processed requests
     * @param processed ConcurrentMap - to store the requests being processed
     */
    public ClientConnections(Sockets connection, Map requestsAnswered, JTextField PRField, Map processed) {
        this.connection = connection;
        this.PRField = PRField;
        this.requestsAnswered = requestsAnswered;
        this.processed = processed;
        requestsProcessed = 0;
    }

    /**
     * Method that contains the main loop of the class. Keeps accepting new
     * connections until the program is closed and deals with the messages
     * received.
     */
    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                awaitConnections();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to handle the messages received in the socket. Waits for an input
     * and deals with it appropriately.
     */
    private void awaitConnections() {
        try {
            ObjectInputStream input = null;
            input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
            Object message = null;
            message = input.readObject();
            if (message instanceof String) {
                clientId = Integer.parseInt((String) message);
                JOptionPane success = new JOptionPane();
                success.showMessageDialog(null, "Connection Successful", "Connection", JOptionPane.INFORMATION_MESSAGE);
            } else if (message instanceof Request) {
                System.out.println("Request Complete");
                Request re = (Request) message;
                requestsProcessed += 1;
                PRField.setText(Integer.toString(requestsProcessed));
                processed.remove(re.getRequestID());
                requestsAnswered.put(requestsProcessed, re);
            }
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Getter method for the clientId variable
     *
     * @return Integer clientId
     */
    public int getClientId() {
        return clientId;
    }
}
