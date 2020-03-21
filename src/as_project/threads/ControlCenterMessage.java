/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.threads;

import as_project.Sockets;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JToggleButton;

/**
 *
 * @author gabri
 */
public class ControlCenterMessage extends Thread{
    private Sockets sock = null;
    private JToggleButton button1;
    private JButton button2;
    
    public ControlCenterMessage(JToggleButton button1, JButton button2, int port){
        sock = new Sockets();
        this.button1 = button1;
        this.button2 = button2;
        sock.startServer(port);
    }
    public ControlCenterMessage(int port){
        sock = new Sockets();
        sock.startServer(port);
    }
    
    @Override
    public void run(){
        try{
            DataInputStream input = null;
            input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            String message = "";
            message = input.readUTF();
            button1.setEnabled(true);
            button2.setEnabled(true);
            sock.closeServerConnection();
            System.out.println("Mensagem Recebida, Butoes toggled");
        }catch(IOException i){
            System.out.println(i);
        }     
    }
}
