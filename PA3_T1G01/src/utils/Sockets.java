package utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

/**
 * Sockets - Responsible for handling the sockets, creating server and client
 * connections as well as sending messages
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class Sockets{

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
     * Creates an instance of Sockets
     */
    public Sockets() {
    }

    /**
     * Method to get the socketServer variable
     *
     * @return socketServer variable
     */
    public Socket getSocketServer() {
        return socketServer;
    }

    public ServerSocket getServer() {
        return server;
    }
    
    /**
     * Starts a server connection in a specified port
     *
     * @param port port in which to start the server
     */
    public boolean startServer(int port) {
        try {
            server = new ServerSocket(port);
            return true;
        } catch (IOException e) {
            System.out.println(e);
            JOptionPane failure = new JOptionPane();
            failure.showMessageDialog(null, "Connection Failed", "Connection", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    /**
     * Starts a client connection in a specified IP and port
     *
     * @param ip IP in which to initialize the client
     * @param port port to connect to
     */
    public void startClient(String ip, int port) {
        try {
            socketClient = new Socket(ip, port);

        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public void sendMessage(String message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socketClient.getOutputStream());
            output.writeObject(message);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Sends a message through to a server through the clientServer socket
     *
     * @param connect connectioninfo object to send
     */
    public void sendMessage(ConnectionInfo connect) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socketClient.getOutputStream());
            output.writeObject(connect);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Sends a message through to a server through the clientServer socket
     *
     * @param info request object to send
     */
    public void sendMessage(Request info) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socketClient.getOutputStream());
            output.writeObject(info);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Closes the connection to the client connection
     */
    public void closeClientConnection() {
        try {
            socketClient.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Closes the connection to the server connection
     */
    public void closeServerConnection() {
        try {
            server.close();
            socketServer.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Closes both the client and the server connection
     */
    public void closeAllConnections() {
        closeClientConnection();
        closeServerConnection();
    }
}
