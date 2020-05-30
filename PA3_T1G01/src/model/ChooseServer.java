package model;

import utils.ConnectionInfo;

/**
 * ChooseServer - Class used to store the connection information of each server
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ChooseServer {

    /**
     * ID of the server
     */
    private int serverId;
    /**
     * Connection Information of the server
     */
    private ConnectionInfo connection;

    public ChooseServer(int serverId, ConnectionInfo connection) {
        this.serverId = serverId;
        this.connection = connection;
    }

    /**
     * Getter method for the serverId
     *
     * @return Integer - Id of the server
     */
    public int getServerId() {
        return serverId;
    }

    /**
     * Setter method for the serverId
     *
     * @param serverId Integer - Id of the server
     */
    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    /**
     * Getter method for the connection variable
     *
     * @return ConnectionInfo - connection information of the server
     */
    public ConnectionInfo getConnection() {
        return connection;
    }

    /**
     * Setter method for the connection variable
     *
     * @param connection ConnectionInfo - connection information of the server
     */
    public void setConnection(ConnectionInfo connection) {
        this.connection = connection;
    }

}
