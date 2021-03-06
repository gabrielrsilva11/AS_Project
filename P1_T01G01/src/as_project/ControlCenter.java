package as_project;

import as_project.threads.CCWorker;
import javax.swing.JButton;
import javax.swing.JTextField;


/**
* Control Center - Responsible for sending messages to the Farm Infrastructure
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class ControlCenter{
    /**
    * Sockets class used to send messages to the Farm Infrastructure
    */
    private Sockets serverConnect = null;
    /**
    * String containing the initial parameters to send to the Farm Infrastructure
    */
    private String parameters;
    /**
    * Port variable that starts at 5000 and increments by 1 everytime we send a message to make sure
    * several requests can be handled at the same time.
    */
    private int port;
     /**
     * ControlCenter class constructor
     * 
     * Initiates the client connection and the port number.
     * 
     */
    public ControlCenter(){
        serverConnect = new Sockets();
        serverConnect.startClient("127.0.0.1", 5000);
        port = 5000;
    }
    
     /**
     * Method to set the parameters variable
     * 
     * @param parameters initial parameters defined by the user for the simulation
     */
    public void setParameters(String parameters){
        this.parameters=parameters;
    }
    
     /**
     * Method to get the port variable
     * 
     * @return the last port number that was used
     */
    public int getPort(){
        return port;
    }
    
     /**
     * Method to send a message to the Farm Infrastructure
     * 
     * @param message message to send over
     * @param b1 button to enable
     * @param b2 button to enable
     */
    public void sendMessage(String message, JButton b1, JButton b2){
        port +=1;
        serverConnect.sendMessage(message);
        if("prepare".equals(message)){
            serverConnect.sendMessage(parameters);
        }
        new CCWorker(b1, b2, port).execute();
    }
    
     /**
     * Method to send a message to the Farm Infrastructure
     * 
     * @param message message to send over
     * @param b1 button to enable
     * @param b2 button to enable
     * @param corn field to set the amount of corn collected
     */
    public void sendMessage(String message, JButton b1, JButton b2, JTextField corn){
        port +=1;
        serverConnect.sendMessage(message);
        if("prepare".equals(message)){
            serverConnect.sendMessage(parameters);
        }
        new CCWorker(b1, b2, port, corn).execute();
    }
}

