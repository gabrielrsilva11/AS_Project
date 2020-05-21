/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author gabri
 */
public class Request {

    private int ServerID;
    private int ClientID;
    private String ClientIP;
    private int ClientPort;
    private int RequestID;
    private int Code;
    private float Reply;

    public Request(int ClientID, int Code, String ClientIP, int ClientPort) {
        this.ClientID = ClientID;
        this.Code = Code;
        this.ClientIP = ClientIP;
        this.ClientPort = ClientPort;
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

    public void setReply(float Reply) {
        this.Reply = Reply;
    }

    public int getCode() {
        return Code;
    }

    public float getReply() {
        return Reply;
    }

    public String getFormattedRequest() {
        return String.format("ServerID: %d || ClientID: %d || RequestID: %d || Code: %d || Reply: %f", ServerID, ClientID, RequestID, Code, Reply);
    }
}
