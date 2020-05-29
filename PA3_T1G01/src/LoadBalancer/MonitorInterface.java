/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import java.util.List;
import model.ChooseServer;
import utils.ConnectionInfo;
import utils.Request;

/**
 *
 * @author manuelcura
 */
public interface MonitorInterface {
    public int registerServer(ConnectionInfo connectionInfo);
    public List<Request> closeServer(int serverId);
    public ChooseServer chooseServer();
    public int generateClientId();
    public void addClientRequest(Request re);
    public ConnectionInfo removeServerConnection(int serverId);
    public void completeClientRequest(Request re);
}
