/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;
import javax.swing.JFrame;

/**
 *
 * @author gabri
 */
public class FarmInfrastructure {
    Sockets clientSocket = null;
    private JFrame gui = null;
    
    public FarmInfrastructure(){
        FI_GUI fi = new FI_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(440, 650);
        gui.setResizable(true);
        gui.add(fi);
        clientSocket = new Sockets();
        clientSocket.startServer(5000);
    }
   
    public static void main(String[] args) {
        FarmInfrastructure farm = new FarmInfrastructure();
    }  
}
