package Server;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 * ServerWorkThread - Class that does the server work. Implements runnable so it
 * can be called as a new thread
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ServerWorkThread implements Runnable {

    /**
     * Request to work on
     */
    private Request re;
    /**
     * Sockets variable that allows a reply to the client and Load Balancer
     */
    private Sockets connection;
    /**
     * Connection information of the load balancer
     */
    private ConnectionInfo lb_info;
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
     * Class constructor
     *
     * @param re Request - Request to be worked on
     * @param connection Sockets - variable to allows us to reply to the client
     * and load balancer
     * @param lb_info ConnectionInfo - Connection information of the load
     * balancer
     * @param requests JTextField - displays number of requests being processed
     * @param completed JTextField - displays the number of requests completed
     * @param requestsAnswered Map - stores the requests completed
     * @param processingRequests Map - stores the requests being processed
     */
    public ServerWorkThread(Request re, Sockets connection, ConnectionInfo lb_info, JTextField requests,
            JTextField completed, Map requestsAnswered, Map processingRequests) {
        this.re = re;
        this.connection = connection;
        this.lb_info = lb_info;
        this.requestsAnswered = requestsAnswered;
        this.processingRequests = processingRequests;
        this.requests = requests;
        this.completed = completed;
    }

    /**
     * Run method to be called when the thread starts. Does the server work and
     * replies to both the client and load balancer as well as settings the text
     * fields to the correct values.
     */
    @Override
    public void run() {
        try {
            processingRequests.put(re.getRequestID(), re);
            completed.setText(Integer.toString(processingRequests.size()));
            for (int i = 0; i < re.getNI(); i++) {
                Thread.sleep(1000);
            }
            re.setReply(3.1416);
            re.setCode(2);
            processingRequests.remove(re.getRequestID());
            completed.setText(Integer.toString(processingRequests.size()));
            requestsAnswered.put(re.getRequestID(), re);
            requests.setText(Integer.toString(requestsAnswered.size()));
            replyClient(re);
            replyLoadBalancer(re);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sends a reply to the client
     *
     * @param re Request - request that has been completed
     */
    private void replyClient(Request re) {
        connection.startClient(re.getClient().getIp(), re.getClient().getPort());
        connection.sendMessage(re);
        //connection.closeClientConnection();
    }

    /**
     * Sends a reply to the load balancer
     *
     * @param re Request - request that has been completed
     */
    private void replyLoadBalancer(Request re) {
        connection.startClient(lb_info.getIp(), lb_info.getPort());
        connection.sendMessage(re);
        //connection.closeClientConnection();
    }
}
