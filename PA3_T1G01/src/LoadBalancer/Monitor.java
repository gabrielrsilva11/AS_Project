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
    private Map<Integer, List<Request>> serverRequest;

    public Monitor() {
        serverConnections = new ConcurrentHashMap<>();
        serverRequest = new ConcurrentHashMap<>();
    }

    public int registerServer(ConnectionInfo connectionInfo) {
        int id = 1000 + serverConnections.size();
        serverConnections.put(id, connectionInfo);
        serverRequest.put(id, new ArrayList<>());
        return id;
    }
    
    public int generateClientId() {
        return new Random().nextInt(10000);
    }

    public int chooseServer() {
        int minRequest = 999;
        int serverId = 0;
        
        //Choose best server
        for (Map.Entry<Integer, List<Request>> entry : serverRequest.entrySet()) {
            if (entry.getValue().size() < minRequest) {
                minRequest = entry.getValue().size();
                serverId = entry.getKey();
            }
        }
        return serverId;
    }
}
