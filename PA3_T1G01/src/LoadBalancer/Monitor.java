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

    /**
     * The ReentratLock that defines the critical region where the threads
     * operate
     */
    private final Lock rel;

    public Monitor() {
        rel = new ReentrantLock();
        serverConnections = new ConcurrentHashMap<>();
        serverRequest = new ConcurrentHashMap<>();
        serverRequestComplete = new ConcurrentHashMap<>();
    }

    @Override
    public int registerServer(ConnectionInfo connectionInfo) {
        rel.lock();
        int id;
        try {
            id = 1000 + serverConnections.size();
            serverConnections.put(id, connectionInfo);
            serverRequest.put(id, new ArrayList<>());
        } finally {
            rel.unlock();
        }
        return id;
    }

    @Override
    public int generateClientId() {
        rel.lock();
        int clientId;
        try {
            clientId = new Random().nextInt(10000);
        } finally {
            rel.unlock();
        }
        return clientId;
    }

    @Override
    public ChooseServer chooseServer() {
        rel.lock();
        int minRequest = 999;
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
            rel.unlock();
        }
        return new ChooseServer(serverId, serverConnections.get(serverId));
    }

    @Override
    public void addClientRequest(Request re) {
        rel.lock();
        try {
            List<Integer> requests = serverRequest.get(re.getServerID());
            requests.add(re.getRequestID());
            serverRequest.put(re.getServerID(), requests);
        } finally {
            rel.unlock();
        }

    }

    @Override
    public void completeClientRequest(Request re) {
        rel.lock();
        try {
            List<Integer> requests = serverRequest.get(re.getServerID());
            requests.remove(re.getRequestID());
            serverRequest.put(re.getServerID(), requests);
            List<Integer> requestsComplete = serverRequest.get(re.getServerID());
            requestsComplete.add(re.getRequestID());
            serverRequestComplete.put(re.getServerID(), requestsComplete);
        } finally {
            rel.unlock();
        }
    }
}
