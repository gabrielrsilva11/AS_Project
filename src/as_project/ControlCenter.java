/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;
import javax.swing.JFrame;
/**
 *
 * @author gabriel
 */
public class ControlCenter {
    Sockets serverConnect = null;
    private JFrame gui = null;
    
    public ControlCenter(){
        CC_GUI cc = new CC_GUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(350, 280);
        gui.setResizable(false);
        gui.add(cc);
        serverConnect = new Sockets();
        serverConnect.startServer(5000);
    }
}
