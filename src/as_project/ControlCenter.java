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
    public Sockets serverConnect = null;

    
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient(5000);
    }
}
