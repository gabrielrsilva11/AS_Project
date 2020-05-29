/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import utils.ConnectionInfo;

/**
 *
 * @author manuelcura
 */
public class ChooseServer {
    
    private int serverId;
    private ConnectionInfo connection;

    public ChooseServer(int serverId, ConnectionInfo connection) {
        this.serverId = serverId;
        this.connection = connection;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public ConnectionInfo getConnection() {
        return connection;
    }

    public void setConnection(ConnectionInfo connection) {
        this.connection = connection;
    }
    
    
    
}
