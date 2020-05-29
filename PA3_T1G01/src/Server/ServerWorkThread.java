/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 *
 * @author gabri
 */
public class ServerWorkThread implements Runnable {

    private Request re;
    private Sockets connection;
    private ConnectionInfo lb_info;
    private Map requestsAnswered;
    private Map processingRequests;
    private JTextField requests;
    private JTextField completed;

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

    private void replyClient(Request re) {
        connection.startClient(re.getClient().getIp(), re.getClient().getPort());
        connection.sendMessage(re);
        //connection.closeClientConnection();
    }

    private void replyLoadBalancer(Request re) {
        connection.startClient(lb_info.getIp(), lb_info.getPort());
        connection.sendMessage(re);
        //connection.closeClientConnection();
    }
}
