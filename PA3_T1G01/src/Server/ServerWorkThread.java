/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.logging.Level;
import java.util.logging.Logger;
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

    public ServerWorkThread(Request re, Sockets connection, ConnectionInfo lb_info) {
        this.re = re;
        this.connection = connection;
        this.lb_info = lb_info;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < re.getNI(); i++) {
                Thread.sleep(1000);
            }
            re.setReply(3.1416);
            re.setCode(02);
            replyClient(re);
            //replyLoadBalancer
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void replyClient(Request re) {
        connection.startClient(re.getClient().getIp(), re.getClient().getPort());
        connection.sendMessage(re);
        connection.closeClientConnection();
    }

    private void replyLoadBalancer(Request re) {
        connection.startClient(lb_info.getIp(), lb_info.getPort());
        re.setCode(2);
        connection.sendMessage(re);
        connection.closeClientConnection();
    }
}
