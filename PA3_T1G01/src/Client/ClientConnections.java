/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author gabri
 */
public class ClientConnections implements Runnable {

    private Sockets connection;
    private Socket socketServer;
    private JTextField PRField;
    private int requestsProcessed;
    private Map requestsAnswered;

    public ClientConnections(Sockets connection, Map requestsAnswered, JTextField PRField) {
        this.connection = connection;
        this.PRField = PRField;
        this.requestsAnswered = requestsAnswered;
        requestsProcessed = 0;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            System.out.println("Waiting for clients");
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                System.out.println("Connections accepted: " + i);
                awaitConnections();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void awaitConnections() {
        try {
            ObjectInputStream input = null;
            input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
            Object message = null;
            message = input.readObject();
            System.out.println(message.getClass());
            if (message instanceof String) {
                if (message.equals("ok")) {
                    JOptionPane success = new JOptionPane();
                    success.showMessageDialog(null, "Connection Successful", "Connection", JOptionPane.INFORMATION_MESSAGE);
                }else if(message.equals("port")){
                    JOptionPane failure = new JOptionPane();
                    failure.showMessageDialog(null, "Connection Failed", "Connection", JOptionPane.ERROR_MESSAGE);
                }
            } else if (message instanceof Request) {
                System.out.println("Request Complete");
                Request re = (Request) message;
                requestsProcessed += 1;
                PRField.setText(Integer.toString(requestsProcessed));
                requestsAnswered.put(requestsProcessed, re);
                System.out.println("Resultado: " + re.getReply());
            }
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
