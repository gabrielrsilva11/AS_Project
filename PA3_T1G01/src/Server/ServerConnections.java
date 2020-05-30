package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Sockets;

/**
 * ServerConnections - Responsible for handling the connections to the server.
 * Accepts new connections and launches a thread to handle the messages.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ServerConnections implements Runnable {

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
     * Connection information of the load balancer
     */
    private ConnectionInfo lb_info;
    /**
     * Class that handles the messages after a new connection is made
     */
    ServerMessages messageHandler;
    /**
     * Identifier of the server
     */
    private int serverId;
    /**
     * Text field to display number of requests being processed
     */
    private JTextField requests;
    /**
     * Text field to display number of requests that were completed
     */
    private JTextField completed;
    /**
     * ConcurrentMap with the requests that have been answered
     */
    private Map requestsAnswered;
    /**
     * ConcurrentMap with the requests that are being processed
     */
    private Map processingRequests;

    /**
     * ServerConnections class constructor
     *
     * @param connection Sockets - variable to allows us to accept new
     * connections to the server
     * @param lb_connection ConnectionInfo - Connection information of the load
     * balancer
     * @param requestsAnswered Map - stores the requests completed
     * @param processingRequests Map - stores the requests being processed
     * @param requests JTextField - displays number of requests being processed
     * @param completed JTextField - displays the number of requests completed
     */
    public ServerConnections(Sockets connection, ConnectionInfo lb_connection, Map requestsAnswered, Map processingRequests,
            JTextField requests, JTextField completed) {
        this.connection = connection;
        this.lb_info = lb_connection;
        this.requestsAnswered = requestsAnswered;
        this.processingRequests = processingRequests;
        this.requests = requests;
        this.completed = completed;
    }

    /**
     * Run method to be called by the server class to handle new connections.
     * Accepts new connections and creates a new thread to handle the messages
     * for each connection.
     */
    @Override
    public void run() {
        int i = 0;
        try {
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                messageHandler = new ServerMessages(connection, socketServer, lb_info, requestsAnswered, processingRequests, requests, completed);
                new Thread(messageHandler).start();
                if (i == 1) {
                    Thread.sleep(1000);
                    serverId = messageHandler.getServerID();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns the server identifier
     *
     * @return Integer - server identifier
     */
    public int getServerId() {
        return serverId;
    }

    /**
     * Method called when the server closes. Stops the execution loop.
     */
    public void exit() {
        messageHandler.exit();
    }
}
