/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
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
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    
    public Sockets(){
    }
    
    public void startServer(int port){
        try{
            server = new ServerSocket(port);
            System.out.println("Server Start");
            System.out.println("Waiting for client");
            
            socket = server.accept();
            System.out.println("Client Accepted");
            
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            
            String message = "";
            while(!message.equals("0")){
                message = input.readUTF();
                if(message.equals("1")){
                    System.out.println("aaaaah");
                }
            }
            closeConnection();
        }catch(IOException i){
            System.out.println(i);
        }        
    }
    
    public void startClient(int port){
        try{
            socket = new Socket("127.0.0.1", 5000);
            System.out.println("Connected");
            output = new DataOutputStream(socket.getOutputStream());
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
            closeConnection();
        }
        else{
            try{
                output.writeUTF(message);
            }catch(IOException i){
                System.out.println(i);
            }
        }
    }
    
    private void closeConnection(){
        System.out.println("Closing connection");
        try{
            socket.close();
            if(output != null){
                output.close();
            }
            if(input!= null){
                input.close();
            }
        }catch(IOException i){
            System.out.println(i);
        }
    }
}
