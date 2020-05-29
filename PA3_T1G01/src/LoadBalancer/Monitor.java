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

    public Monitor() {
        serverConnections = new ConcurrentHashMap<>();
        serverRequest = new ConcurrentHashMap<>();
        serverRequestComplete = new ConcurrentHashMap<>();
    }

    @Override
    public int registerServer(ConnectionInfo connectionInfo) {
        int id = 1000 + serverConnections.size();
        serverConnections.put(id, connectionInfo);
        serverRequest.put(id, new ArrayList<>());
        return id;
    }

    @Override
    public int generateClientId() {
        return new Random().nextInt(10000);
    }

    @Override
    public ChooseServer chooseServer() {
        int minRequest = 999;
        int serverId = 0;

        //Choose best server
        for (Map.Entry<Integer, List<Integer>> entry : serverRequest.entrySet()) {
            if (entry.getValue().size() < minRequest) {
                minRequest = entry.getValue().size();
                serverId = entry.getKey();
            }
        }
        return new ChooseServer(serverId, serverConnections.get(serverId));
    }

    @Override
    public void addClientRequest(Request re) {
        List<Integer> requests = serverRequest.get(re.getServerID());
        requests.add(re.getRequestID());
        serverRequest.put(re.getServerID(), requests);
    }

    @Override
    public void completeClientRequest(Request re) {
        List<Integer> requests = serverRequest.get(re.getServerID());
        requests.remove(re.getRequestID());
        serverRequest.put(re.getServerID(), requests);
        List<Integer> requestsComplete = serverRequest.get(re.getServerID());
        requestsComplete.add(re.getRequestID());
        serverRequestComplete.put(re.getServerID(), requestsComplete);
    }
}
