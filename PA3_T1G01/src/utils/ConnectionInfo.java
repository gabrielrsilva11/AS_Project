package utils;

import java.io.Serializable;

/**
 * ConnectionInfo - Class used to store Connection Information of a client or
 * server
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class ConnectionInfo implements Serializable {

    /**
     * Variable that store who is created the ConnectionInfo 1 - Created by the
     * client 2 - Created by the server
     */
    private int type;
    /**
     * IP of the connection
     */
    private String ip;
    /**
     * Port of the connection
     */
    private int port;

    /**
     * ConnectionInfo class constructor
     *
     * @param ip IP of the connection
     * @param port Port of the connection
     * @param type Who created the connection 1- Client 2- Server
     */
    public ConnectionInfo(String ip, int port, int type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    /**
     * Getter method for the IP variable
     *
     * @return string with the IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * Getter method for the port variable
     *
     * @return int with the connection port
     */
    public int getPort() {
        return port;
    }

    /**
     * Getter method for the type variable
     *
     * @return int type
     */
    public int getType() {
        return type;
    }
}
