/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class ConnectionInfo implements Serializable {

    //1 para client, 2 para servidor
    private int type;
    private String ip;
    private int port;

    public ConnectionInfo(String ip, int port, int type) {
        this.ip = ip;
        this.port = port;
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getType() {
        return type;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setType(int type) {
        this.type = type;
    }
}
