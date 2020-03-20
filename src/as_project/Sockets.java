/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author gabri
 */
public class Sockets {
    private Socket socketServer = null;
    private Socket socketClient = null;
    private ServerSocket server = null;
    private DataOutputStream output = null;
    
    public Sockets(){
    }
    
    public Socket getSocketServer(){
        return socketServer;
    }
        
    public void startServer(int port){
        try{
            server = new ServerSocket(port);

            socketServer = server.accept();
        }catch(IOException e){
            System.out.println(e);
        }       
    }
    
    public void startClient(String ip, int port){
        System.out.println("Starting Client");
        try{
            socketClient = new Socket(ip, port);
            output = new DataOutputStream(socketClient.getOutputStream());
        }catch(UnknownHostException u){
            System.out.println(u);
        }catch(IOException i){
            System.out.println(i);
        }  
    }
    
    
    
    public void sendMessage(String message){
        try{
            output.writeUTF(message);
        }catch(IOException i){
            System.out.println(i);
        }
    }
    
    public void closeClientConnection(){
        try{
            socketClient.close();
            output.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
    
    public void closeServerConnection(){
        try{
            server.close();
            socketServer.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
    
    public void closeAllConnections(){
        try{
            socketServer.close();
            socketClient.close();
            server.close();
            output.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
}
