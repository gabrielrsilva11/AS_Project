package Server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 * ServerMessages - Class that handles the messages for a connection. Created
 * has a separate thread so we can answer multiple connections at the same time.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ServerMessages implements Runnable {

    /**
     * Sockets variable that allows a reply to the client and Load Balancer
     */
    private Sockets connection;
    /**
     * Socket variable where we will get the input stream from
     */
    private Socket socketServer;
    /**
     * Connection information of the load balancer
     */
    private ConnectionInfo lb_info;
    /**
     * Identifier of the server
     */
    private int serverId;
    /**
     * Boolean to stop the execution
     */
    private boolean active;
    /**
     * ConcurrentMap with the requests that have been answered
     */
    private Map requestsAnswered;
    /**
     * ConcurrentMap with the requests that are being processed
     */
    private Map processingRequests;
    /**
     * Text field to display number of requests being processed
     */
    private JTextField requests;
    /**
     * Text field to display number of requests that were completed
     */
    private JTextField completed;

    /**
     * ServerMessages class constructor
     *
     * @param connection Sockets - variable to allows us to reply to the client
     * and load balancer
     * @param serverSocket Socket - variable to get the input stream from
     * @param lb_info ConnectionInfo - Connection information of the load
     * balancer
     * @param requestsAnswered Map - stores the requests completed
     * @param processingRequests Map - stores the requests being processed
     * @param requests JTextField - displays number of requests being processed
     * @param completed JTextField - displays the number of requests completed
     */
    public ServerMessages(Sockets connection, Socket serverSocket, ConnectionInfo lb_info, Map requestsAnswered, Map processingRequests,
            JTextField requests, JTextField completed) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.lb_info = lb_info;
        this.active = true;
        this.requestsAnswered = requestsAnswered;
        this.processingRequests = processingRequests;
        this.requests = requests;
        this.completed = completed;
    }

    /**
     * Run method to be called when the thread starts. Calls a method that
     * handles messages for a connection and starts worker threads to answer
     * requests.
     */
    @Override
    public void run() {
        awaitConnections();
    }

    /**
     * Method that handles messages for this thread connections.
     */
    private void awaitConnections() {
        try {
            while (active) {
                //receber string id ou receber um request
                ObjectInputStream input = null;
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof Request) {
                    Request request = (Request) message;
                    ServerWorkThread messageHandler = new ServerWorkThread(request, connection, lb_info, requests, completed, requestsAnswered, processingRequests);
                    new Thread(messageHandler).start();
                } else if (message instanceof String) {
                    if (message.equals("exit")) {
                        System.exit(0);
                    } else {
                        serverId = Integer.parseInt((String) message);
                        JOptionPane success = new JOptionPane();
                        success.showMessageDialog(null, "Connection Successful", "Connection", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Getter method for the server identifier
     *
     * @return Integer - server identifier
     */
    public int getServerID() {
        return serverId;
    }

    /**
     * Method called when the server closes. Stops the execution loop.
     */
    public void exit() {
        this.active = false;
    }
}
