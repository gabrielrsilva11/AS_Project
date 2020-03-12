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
            System.out.println("Server Start");
            System.out.println("Waiting for client");

            socketServer = server.accept();
            System.out.println("Client Accepted");
        }catch(IOException e){
            System.out.println(e);
        }       
    }
    
    public void startClient(String ip, int port){
        try{
            socketClient = new Socket(ip, port);
            System.out.println("Connected");
            output = new DataOutputStream(socketClient.getOutputStream());
        }catch(UnknownHostException u){
            System.out.println(u);
        }catch(IOException i){
            System.out.println(i);
        }  
    }
    
    
    
    public void sendMessage(String message){
        if(message.equals("0")){
            try{
                output.writeUTF(message);
            }catch(IOException i){
                System.out.println(i);
            }
            closeAllConnections();
        }
        else{
            try{
                output.writeUTF(message);
            }catch(IOException i){
                System.out.println(i);
            }
        }
    }
    
    public void closeConnection(Socket toClose){
        System.out.println("Closing connection");
        try{
            toClose.close();
        }catch(IOException i){
            System.out.println(i);
        }
    }
    
    public void closeAllConnections(){
        System.out.println("Closing ALL connections");
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
