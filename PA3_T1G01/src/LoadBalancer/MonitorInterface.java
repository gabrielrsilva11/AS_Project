/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import utils.ConnectionInfo;

/**
 *
 * @author manuelcura
 */
public interface MonitorInterface {
    public int registerServer(ConnectionInfo connectionInfo);
    public int chooseServer();
    public int generateClientId();
}
