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
public class Request implements Serializable {

    private int ServerID;
    private int ClientID;
    private final ConnectionInfo Client;
    private int RequestID;
    private int Code;
    private int NI;
    private double Reply;

    public Request(int Code, ConnectionInfo Client, int NI) {
        this.Code = Code;
        this.Client = Client;
        this.NI = NI;
    }

    public ConnectionInfo getClient() {
        return Client;
    }

    public void setServerID(int ServerID) {
        this.ServerID = ServerID;
    }

    public void setRequestID(int RequestID) {
        this.RequestID = RequestID;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public void setReply(double Reply) {
        this.Reply = Reply;
    }

    public void setNI(int NI) {
        this.NI = NI;
    }

    public int getCode() {
        return Code;
    }

    public double getReply() {
        return Reply;
    }

    public int getNI() {
        return NI;
    }

    public String getFormattedRequest() {
        return String.format("ServerID: %d || ClientID: %d || RequestID: %d || Code: %d || Reply: %f", ServerID, ClientID, RequestID, Code, Reply);
    }
}
