package as_project;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
* Sockets - Responsible for handling the sockets, creating server and client connections as well
* as sending messages
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class Sockets {
    /**
     * Accepts connections to the server socket
     */
    private Socket socketServer = null;
    /**
     * Socket to start client connections
     */
    private Socket socketClient = null;
    /**
     * Creates a server socket
     */
    private ServerSocket server = null;
    /**
     * Stream used to send messages
     */
    private DataOutputStream output = null;
    /**
     * Creates an instance of Sockets
     */
    public Sockets(){
    }
    /**
     * Method to get the socketServer variable
     * @return socketServer variable 
     */
    public Socket getSocketServer(){
        return socketServer;
    }
    /**
     * Starts a server connection in a specified port 
     * @param port port in which to start the server 
     */
    public void startServer(int port){
        try{
            server = new ServerSocket(port);
            socketServer = server.accept();
        }catch(IOException e){
            System.out.println(e);
        }
    }
    /**
     * Starts a client connection in a specified IP and port
     * @param ip IP in which to initialize the client
     * @param port port to connect to
     */
    public void startClient(String ip, int port){
        try{
            socketClient = new Socket(ip, port);
            output = new DataOutputStream(socketClient.getOutputStream());
        }catch(UnknownHostException u){
            System.out.println(u);
        }catch(IOException i){
            System.out.println(i);
        }  
    }
    /**
     * Sends a message through to a server through the clientServer socket
     * @param message message to send
     */
    public void sendMessage(String message){
        try{    
            output.writeUTF(message);
        }catch(IOException i){
            System.out.println(i);
        }
    }
    /**
     * Closes the connection to the client connection
     */
    public void closeClientConnection(){
        try{
            socketClient.close();
            output.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
    /**
     * Closes the connection to the server connection
     */
    public void closeServerConnection(){
        try{
            server.close();
            socketServer.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
    /**
     * Closes both the client and the server connection
     */
    public void closeAllConnections(){
        closeClientConnection();
        closeServerConnection();
    }
}
