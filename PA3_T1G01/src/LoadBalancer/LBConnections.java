package LoadBalancer;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Sockets;

/**
 * LBConnections - Handles the connections to the load balancer and launches a
 * new thread to handle the messages
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class LBConnections implements Runnable {

    /**
     * Server connection
     */
    private Sockets connection;
    /**
     * socketServer that contains the input stream
     */
    private Socket socketServer;
    /**
     * Monitor variable used to select servers and update new clients / server /
     * request maps
     */
    private MonitorInterface monitor;

    /**
     * Class constructor
     *
     * @param connection Sockets - server connection
     * @param monitor Monitor - monitor used to pick servers and update the maps
     */
    public LBConnections(Sockets connection, MonitorInterface monitor) {
        this.connection = connection;
        this.monitor = monitor;
    }

    /**
     * Implementation of the run method that is called when the thread starts.
     * Accepts new connections and launches a new thread to handle messages
     */
    @Override
    public void run() {
        int i = 0;
        try {
            System.out.println("Waiting for connections");
            while (true) {
                socketServer = connection.getServer().accept();
                i += 1;
                LBMessages messageHandler = new LBMessages(connection, socketServer, monitor);
                new Thread(messageHandler).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(LBConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
