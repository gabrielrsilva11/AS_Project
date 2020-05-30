package LoadBalancer;

import Server.Server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ChooseServer;
import utils.ConnectionInfo;
import utils.Request;
import utils.Sockets;

/**
 * LBMessages - This class handles the messages of the load balancer. A new
 * LBMessages thread is started every time a new connection is made.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class LBMessages implements Runnable {

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
     * Boolean to stop the execution if needed
     */
    private boolean active;

    /**
     * Class constructor
     *
     * @param connection Sockets - server connection
     * @param serverSocket Socket - socket that contains the input stream
     * @param monitor Monitor - monitor used to pick servers and update the maps
     */
    public LBMessages(Sockets connection, Socket serverSocket, MonitorInterface monitor) {
        this.connection = connection;
        this.socketServer = serverSocket;
        this.monitor = monitor;
        this.active = true;
    }

    /**
     * Implementation of the run method to start a new thread. Calls the a
     * function to handle messages.
     */
    @Override
    public void run() {
        awaitConnections();
    }

    /**
     * Waits for new messages to arrives and handles them.
     */
    private void awaitConnections() {
        try {
            while (active) {
                ObjectInputStream input = null;
                input = new ObjectInputStream(new BufferedInputStream(socketServer.getInputStream()));
                Object message = null;
                message = input.readObject();
                if (message instanceof ConnectionInfo) {
                    ConnectionInfo info = (ConnectionInfo) message;
                    //CLIENT
                    if (info.getType() == 1) {
                        System.out.println("Requesting client connection!");
                        int clientId = monitor.generateClientId();
                        System.out.println("ClientId: " + clientId);
                        replyClientId(info, clientId);
                    } //SERVER
                    else if (info.getType() == 2) {
                        System.out.println("Requesting server connection!");
                        int serverId = monitor.registerServer(info);
                        replyServerId(info, serverId);
                    }
                } else if (message instanceof Request) {
                    Request re = (Request) message;
                    // message client - server
                    if (re.getCode() == 1) {
                        System.out.println("New: " + re.getRequestID());
                        ChooseServer chooseMonitor = monitor.chooseServer();
                        re.setServerID(chooseMonitor.getServerId());
                        monitor.addClientRequest(re);
                        sendServerRequest(chooseMonitor.getConnection(), re);
                    } // message server - client
                    else if (re.getCode() == 2) {
                        System.out.println("Complete: " + re.getRequestID());
                        monitor.completeClientRequest(re);
                    }
                } else if (message instanceof String) {
                    String re = (String) message;
                    System.out.println(message);
                    if (re.startsWith("exit:")) {
                        String[] reSplitted = ((String) message).split(":");
                        int serverId = Integer.parseInt(reSplitted[1]);

                        List<Request> requests = monitor.closeServer(serverId);

                        monitor.heartbeatStatus(serverId, -1);

                        ConnectionInfo connectionInfo = monitor.removeServerConnection(serverId);

                        sendServerExitResponse(connectionInfo);
                        for (Request req : requests) {
                            ChooseServer chooseMonitor = monitor.chooseServer();
                            req.setServerID(chooseMonitor.getServerId());
                            monitor.addClientRequest(req);
                            sendServerRequest(chooseMonitor.getConnection(), req);
                        }
                        active = false;
                    } else if (re.startsWith("heartbeat:")) {
                        String[] reSplitted = ((String) message).split(":");
                        int serverId = Integer.parseInt(reSplitted[1]);
                        monitor.heartbeatStatus(serverId, 1);
                    }
                }
            }
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to send the client his ID
     *
     * @param info ConnectionInfo - connection information of the client
     * @param clientId Integer - Id of the client
     */
    private void replyClientId(ConnectionInfo info, int clientId) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage(clientId + "");
    }

    /**
     * Method to send the server his ID
     *
     * @param info ConnectionInfo - connection information of the server
     * @param serverId Integer - Id of the server
     */
    private void replyServerId(ConnectionInfo info, int serverId) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage(serverId + "");
    }

    /**
     * Method to send a Request to the server
     *
     * @param info ConnectionInfo - connection information of the server
     * @param request Request - request for the server to work on
     */
    private void sendServerRequest(ConnectionInfo info, Request request) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage(request);
    }

    /**
     * Method to send the server an exit signal
     *
     * @param info ConnectionInfo - connection information of the server
     */
    private void sendServerExitResponse(ConnectionInfo info) {
        connection.startClient(info.getIp(), info.getPort());
        connection.sendMessage("exit");
    }
}
