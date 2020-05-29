/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import model.ChooseServer;
import utils.ConnectionInfo;
import utils.Request;

/**
 *
 * @author manuelcura
 */
public interface MonitorInterface {
    public int registerServer(ConnectionInfo connectionInfo);
    public ChooseServer chooseServer();
    public int generateClientId();
    public void addClientRequest(Request re);
    public void completeClientRequest(Request re);
}
