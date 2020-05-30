package utils;

import java.io.Serializable;

/**
 * Request - Class that handles the requests. Is sent by the client to be filled
 * by the server.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Request implements Serializable {

    /**
     * ID of the server that completed the request
     */
    private int ServerID;
    /**
     * ID of the client that requested the work
     */
    private int ClientID;
    /**
     * Client connection information.
     */
    private final ConnectionInfo Client;
    /**
     * ID of the Request
     */
    private int RequestID;
    /**
     * Code to know if the request has been completed or not
     */
    private int Code;
    /**
     * Number of iterations
     */
    private int NI;
    /**
     * Reply set by the server.
     */
    private double Reply;

    /**
     * Request class constructor
     *
     * @param Code Integer - Code that represents the state of the request
     * @param Client ConnectionInfo - Connection information of the client who
     * made the request
     * @param NI Integer - Number of iterations
     * @param RequestID Integer - ID of the request
     * @param ClientID Integer - ID of the client who ordered the request
     */
    public Request(int Code, ConnectionInfo Client, int NI, int RequestID, int ClientID) {
        //01 se não estiver completa //02 se estiver completa
        this.Code = Code;
        this.Client = Client;
        this.NI = NI;
        this.RequestID = RequestID;
        this.ClientID = ClientID;
    }

    /**
     * Getter method for the Client Information
     *
     * @return ConnectionInfo variable with client info
     */
    public ConnectionInfo getClient() {
        return Client;
    }

    /**
     * Setter method for the ServerID variable
     *
     * @param ServerID Integer - ID of the server
     */
    public void setServerID(int ServerID) {
        this.ServerID = ServerID;
    }

    /**
     * Getter method for the ServerID variable
     *
     * @return Integer - ID of the server
     */
    public int getServerID() {
        return ServerID;
    }

    /**
     * Setter method for the RequestID variable
     *
     * @param RequestID Integer - ID of the request
     */
    public void setRequestID(int RequestID) {
        this.RequestID = RequestID;
    }

    /**
     * Getter method for the RequestID variable
     *
     * @return Integer - ID of the request
     */
    public int getRequestID() {
        return RequestID;
    }

    /**
     * Setter method for the code variable
     *
     * @param Code Integer - Code that represents the state of the request
     */
    public void setCode(int Code) {
        this.Code = Code;
    }

    /**
     * Setter method for the Reply variable
     *
     * @param Reply Double - Reply of the server
     */
    public void setReply(double Reply) {
        this.Reply = Reply;
    }

    /**
     * Setter method for the NI variable
     *
     * @param NI Integer - Number of iterations the server will do
     */
    public void setNI(int NI) {
        this.NI = NI;
    }

    /**
     * Getter method for the code variable
     *
     * @return Integer - Code that represents the state of the request
     */
    public int getCode() {
        return Code;
    }

    /**
     * Getter method for the reply variable
     *
     * @return Double - Reply set by the server
     */
    public double getReply() {
        return Reply;
    }

    /**
     * Getter method for the number of iterations
     *
     * @return Integer - Number of iterations to be executed
     */
    public int getNI() {
        return NI;
    }

    /**
     * Method to return a printable format of the Request class.
     *
     * @return String - Printable format of the Request Variable.
     */
    public String getFormattedRequest() {
        if (Code == 1) {
            return String.format("Status -> Processing || ClientID: %d || RequestID: %d\n", ClientID, RequestID);
        } else if (Code == 2) {
            return String.format("Status -> Processed || ServerID: %d || ClientID: %d || RequestID: %d || Reply: %f\n", ServerID, ClientID, RequestID, Reply);
        }
        return "Code Error";
    }
}
