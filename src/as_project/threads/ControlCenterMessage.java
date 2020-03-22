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
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author gabri
 */
public class ControlCenterMessage extends Thread{
    private Sockets sock = null;
    private JToggleButton button1;
    private JButton button2;
    private JTextField corn;
    
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
    
    public void setCorn(JTextField corn){
        this.corn = corn;
    }
    
    @Override
    public void run(){
        try{
            DataInputStream input = null;
            input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            String message = "";
            message = input.readUTF();
            if("exit".equals(message))
            {
                System.out.println("Simulação terminada");
                System.exit(0);
            }
            else if("".equals(message)){
                String message2=input.readUTF();
                int a = Integer.parseInt(message2);
                int b = Integer.parseInt(corn.getText());
                corn.setText(Integer.toString(a+b));
            }else{
                button1.setEnabled(true);
                button2.setEnabled(true);
            }
            sock.closeServerConnection();
            System.out.println(message);
            System.out.println("Butoes toggled");
        }catch(IOException i){
            System.out.println(i);
        }     
    }
}
