/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import Server.Server;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.ChooseServer;
import utils.ConnectionInfo;
import utils.Request;

/**
 *
 * @author gabri
 */
public class Monitor implements MonitorInterface {

    //Id, connectionInfo
    private Map<Integer, ConnectionInfo> serverConnections;
    //Id, requests
    private Map<Integer, List<Integer>> serverRequest;
    //Id, completeRequests
    private Map<Integer, List<Integer>> serverRequestComplete;

    private List<Request> clientRequests;
    
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

    public Monitor() {
        rel = new ReentrantLock();
        rel1 = new ReentrantLock();
        rel2 = new ReentrantLock();
        rel3 = new ReentrantLock();
        rel4 = new ReentrantLock();
        serverConnections = new ConcurrentHashMap<>();
        serverRequest = new ConcurrentHashMap<>();
        serverRequestComplete = new ConcurrentHashMap<>();
        clientRequests = new ArrayList<>();
        increment = 0;
    }

    @Override
    public int registerServer(ConnectionInfo connectionInfo) {
        rel.lock();
        int id;
        try {
            id = 1000 + increment;
            increment++;
            serverConnections.put(id, connectionInfo);
            serverRequest.put(id, new ArrayList<>());
        } finally {
            rel.unlock();
        }
        return id;
    }

    @Override
    public List<Request> closeServer(int serverId) {
        List<Integer> requests = serverRequest.get(serverId);
        serverRequest.remove(serverId);
        System.out.println(requests);
        List<Request> reqs = clientRequests.stream().filter(re -> requests.contains(re.getRequestID())).collect(Collectors.toList());
        reqs.stream().forEach(re -> clientRequests.remove(re));
        return reqs;
    }
    
    @Override
    public ConnectionInfo removeServerConnection(int serverId) {
        ConnectionInfo connectionInfo = serverConnections.get(serverId);
        serverConnections.remove(serverId);
        return connectionInfo;
    }

    @Override
    public int generateClientId() {
        rel1.lock();
        int clientId;
        try {
            clientId = new Random().nextInt(10000);
        } finally {
            rel1.unlock();
        }
        return clientId;
    }

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

    @Override
    public void addClientRequest(Request re) {
        rel3.lock();
        try {

            List<Integer> requests = serverRequest.get(re.getServerID());
            clientRequests.add(re);
            requests.add(re.getRequestID());
            System.out.println("add");
            System.out.println(re.getServerID());
            System.out.println(serverRequest.get(re.getServerID()).toString());
            serverRequest.put(re.getServerID(), requests);
        } finally {
            rel3.unlock();
        }

    }

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

            System.out.println("Remove");
            System.out.println(re.getServerID());
            System.out.println(serverRequest.get(re.getServerID()).toString());

            serverRequestComplete.put(re.getServerID(), requestsComplete);
            System.out.println("complete");
            System.out.println(re.getServerID());
            System.out.println(serverRequestComplete.get(re.getServerID()).toString());
        } finally {
            rel4.unlock();
        }
    }
}
