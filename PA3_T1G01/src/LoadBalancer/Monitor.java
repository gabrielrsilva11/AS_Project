package LoadBalancer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import model.ChooseServer;
import model.Heartbeat;
import utils.ConnectionInfo;
import utils.Request;

/**
 * Monitor - Monitors the server usage and tells the load balancer which servers
 * to send work to. Implements MonitorInterface.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Monitor implements MonitorInterface {

    /**
     * Concurrent map to store ID and Connection information of a server
     */
    private Map<Integer, ConnectionInfo> serverConnections;

    /**
     * Concurrent map to store ID and a list of requestIDs the server is
     * processing
     */
    private Map<Integer, List<Integer>> serverRequest;

    /**
     * Concurrent map to store ID and a list of requestIDs the server has
     * processed
     */
    private Map<Integer, List<Integer>> serverRequestComplete;

    /**
     * Saves each request made.
     */
    private List<Request> clientRequests;

    /**
     * Concurrent map to store serverID and last heartbeat sent
     */
    private Map<Integer, Heartbeat> serverStatus;

    /**
     * Increment to attribute server Ids.
     */
    private int increment;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel1;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel2;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel3;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel4;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel5;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel6;

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel7;

    /**
     * number of connected clients
     */
    private int numClients;

    /**
     * number of connected servers
     */
    private int numServers;

    /**
     * number of requests being processed
     */
    private int numRequests;

    /**
     * number of completedRequests
     */
    private int completedRequests;

    /**
     * GUI for the load balancer / monitor
     */
    private MonitorGUI gui;

    /**
     * Monitor Class Constructor
     */
    public Monitor() {
        rel = new ReentrantLock();
        rel1 = new ReentrantLock();
        rel2 = new ReentrantLock();
        rel3 = new ReentrantLock();
        rel4 = new ReentrantLock();
        rel5 = new ReentrantLock();
        rel6 = new ReentrantLock();
        rel7 = new ReentrantLock();
        serverConnections = new ConcurrentHashMap<>();
        serverRequest = new ConcurrentHashMap<>();
        serverRequestComplete = new ConcurrentHashMap<>();
        clientRequests = new ArrayList<>();
        serverStatus = new ConcurrentHashMap<>();
        increment = 0;
        this.gui = new MonitorGUI();
        LB_GUI_Actions actions = new LB_GUI_Actions(gui, serverRequest, serverRequestComplete, serverStatus);
        actions.start();
        numClients = 0;
        numServers = 0;
        numRequests = 0;
        completedRequests = 0;
    }

    /**
     * Adds a new server to be monitored
     *
     * @param connectionInfo ConnectionInfo - connection information of the
     * server
     * @return Integer - unique server identifier
     */
    @Override
    public int registerServer(ConnectionInfo connectionInfo) {
        rel.lock();
        int id;
        try {
            id = 1000 + increment;
            increment++;
            serverConnections.put(id, connectionInfo);
            serverRequest.put(id, new ArrayList<>());
            numServers += 1;
            gui.getServers_Text().setText(Integer.toString(numServers));
        } finally {
            rel.unlock();
        }
        return id;
    }

    /**
     * Method that removes a server from being monitored when it sends an exit
     * signal.
     *
     * @param serverId Integer - id of the server that's closing
     * @return List<Request> - List of requests to be distributed among the
     * remaining servers
     */
    @Override
    public List<Request> closeServer(int serverId) {
        rel5.lock();
        List<Request> reqs;
        try {
            numServers -= 1;
            gui.getServers_Text().setText(Integer.toString(numServers));
            List<Integer> requests = serverRequest.get(serverId);
            serverRequest.remove(serverId);
            System.out.println(requests);
            reqs = clientRequests.stream().filter(re -> requests.contains(re.getRequestID())).collect(Collectors.toList());
            reqs.stream().forEach(re -> clientRequests.remove(re));
        } finally {
            rel5.unlock();
        }

        return reqs;
    }

    /**
     * Removes a server from the available servers map
     *
     * @param serverId Integer - ID of the server to be removed
     * @return ConnectionInfo - connection information of the removed server.
     */
    @Override
    public ConnectionInfo removeServerConnection(int serverId) {
        rel6.lock();
        ConnectionInfo connectionInfo;
        try {
            connectionInfo = serverConnections.get(serverId);
            serverConnections.remove(serverId);
        } finally {
            rel6.unlock();
        }
        return connectionInfo;
    }

    /**
     * Generates a unique clientId
     *
     * @return Integer - Client id that was generated
     */
    @Override
    public int generateClientId() {
        rel1.lock();
        numClients += 1;
        gui.getClients_Text().setText(Integer.toString(numClients));
        int clientId;
        try {
            clientId = new Random().nextInt(10000);
        } finally {
            rel1.unlock();
        }
        return clientId;
    }

    /**
     * Method to choose the server with the least requests for the load balancer
     * to send work to
     *
     * @return ChooseServer - Id and connection information of the server to get
     * work
     */
    @Override
    public ChooseServer chooseServer() {
        rel2.lock();
        int minRequest = 9999;
        int serverId = 0;
        try {
            //Choose best server
            for (Map.Entry<Integer, List<Integer>> entry : serverRequest.entrySet()) {
                if (entry.getValue().size() < minRequest) {
                    minRequest = entry.getValue().size();
                    serverId = entry.getKey();
                }
            }
        } finally {
            rel2.unlock();
        }
        return new ChooseServer(serverId, serverConnections.get(serverId));
    }

    /**
     * Adds a client request to the list of requests
     *
     * @param re Request - request to be added
     */
    @Override
    public void addClientRequest(Request re) {
        rel3.lock();
        try {
            List<Integer> requests = serverRequest.get(re.getServerID());
            clientRequests.add(re);
            numRequests += 1;
            gui.getProcessing_Text().setText(Integer.toString(numRequests));
            requests.add(re.getRequestID());
            serverRequest.put(re.getServerID(), requests);
        } finally {
            rel3.unlock();
        }

    }

    /**
     * Sets a request by a client as completed
     *
     * @param re Request - request that was completed
     */
    @Override
    public void completeClientRequest(Request re) {
        rel4.lock();
        try {
            List<Integer> requests = serverRequest.get(re.getServerID());
            requests.remove(Integer.valueOf(re.getRequestID()));
            serverRequest.put(re.getServerID(), requests);
            List<Integer> requestsComplete;
            if (serverRequestComplete.get(re.getServerID()) == null || serverRequestComplete.get(re.getServerID()).isEmpty()) {
                requestsComplete = new ArrayList<>();
            } else {
                requestsComplete = serverRequestComplete.get(re.getServerID());
            }
            requestsComplete.add(re.getRequestID());
            serverRequestComplete.put(re.getServerID(), requestsComplete);
            numRequests -= 1;
            gui.getProcessing_Text().setText(Integer.toString(numRequests));
            completedRequests += 1;
            gui.getCompleted_Text().setText(Integer.toString(completedRequests));
        } finally {
            rel4.unlock();
        }
    }

    /**
     * Sets a new heartbeat status for when a new heartbeat message arrives
     *
     * @param serverId Integer - Id of the server that sent the heartbeat
     * @param status Integer - Status of the server when the message was sent
     */
    public void heartbeatStatus(int serverId, int status) {
        rel7.lock();
        try {
            Heartbeat heartbeat = new Heartbeat(status, new Date().getTime());
            serverStatus.put(serverId, heartbeat);
        } finally {
            rel7.unlock();
        }
    }
}
