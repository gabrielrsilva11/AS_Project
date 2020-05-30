package LoadBalancer;

import utils.Sockets;

/**
 * LoadBalancer - Class that receives requests and sends work to the servers.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class LoadBalancer {

    /**
     * Variable used to start servers and clients
     */
    private Sockets connection;
    /**
     * Monitor to tell which server to send work to
     */
    private MonitorInterface monitor;

    /**
     * Method to run the program, starts the load balancer and monitor
     *
     * @param args arguments used when running the program (not used)
     */
    public static void main(String[] args) {
        LoadBalancer lb = new LoadBalancer();
    }

    /**
     * Class Constructor
     */
    public LoadBalancer() {
        connection = new Sockets();

        monitor = new Monitor();
        establishServerConnection();
    }

    /**
     * Starts the server that will be used by new connections and starts a
     * thread to handle connections to the server.
     */
    private void establishServerConnection() {
        System.out.println("Creating LoadBalancer");
        connection.startServer(80);
        Runnable connectionHandler = new LBConnections(connection, monitor);
        // criar connectionInfo, enviar loadbalancer
        new Thread(connectionHandler).start();
    }
}
