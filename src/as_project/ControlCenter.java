/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JFrame;
/**
 *
 * @author gabriel
 */
public class ControlCenter {
    public Sockets serverConnect = null;

    
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient("127.0.0.1", 5000);
    }
    
    public void waitForMessage(){
        serverConnect.startServer(5001);
        try{
            DataInputStream input = null;
            input = new DataInputStream(new BufferedInputStream(serverConnect.getSocketServer().getInputStream()));
            String message = "";
            message = input.readUTF();
            System.out.println("Operacao terminada");
        
        }catch(IOException i){
            System.out.println(i);
        }     
    }
}
