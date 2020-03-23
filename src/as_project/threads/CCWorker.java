package as_project.threads;

import as_project.Sockets;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
* CCWorker - Waits and handles the Farm Infrastructure responses
* 
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class CCWorker extends SwingWorker<Integer, Integer>{
    JButton b1;
    JButton b2;
    int port;
    JTextField corn;
    /**
     * CCWorker class constructor
     * @param b1 button to enable after the FI response
     * @param b2 button to enable after the FI response
     * @param port port in which to wait for the reply
     */
    public CCWorker(JButton b1, JButton b2, int port){
        this.b1 = b1;
        this.b2 = b2;
        this.port = port;
    }
    /**
     * CCWorker class constructor with corn parameter
     * @param b1 button to enable after the FI response
     * @param b2 button to enable after the FI response
     * @param port port in which to wait for the reply
     * @param corn Text area to set the number of collected corn cobs
     */
    public CCWorker(JButton b1, JButton b2, int port, JTextField corn){
        this.b1 = b1;
        this.b2 = b2;
        this.port = port;
        this.corn = corn;
    }
    /**
     * Method to wait and handle the farm infrastructure response
     * @return successful execution
     * @throws Exception 
     */
    @Override
    protected Integer doInBackground() throws Exception{
        Sockets sock = new Sockets();
        sock.startServer(port);
        try{
            DataInputStream input;
            input = new DataInputStream(new BufferedInputStream(sock.getSocketServer().getInputStream()));
            String message = "";
            message = input.readUTF();
            System.out.println(message);
            if("exit".equals(message))
            {
                System.out.println("Simulação terminada");
                System.exit(0);
            }
            else if("coletar".equals(message)){
                String message2=input.readUTF();
                corn.setText(message2);
                b1.setEnabled(true);
                b2.setEnabled(true);
            }else{
                b1.setEnabled(true);
                b2.setEnabled(true);
            }
        }catch(IOException i){
                System.out.println(i);
        }
                sock.closeServerConnection();
        return 1;
    }
    /**
     * Method that is executed when {@link doInBackground} finishes
    */
    @Override
    protected void done(){
        System.out.println("Butoes toggled");
    }
}
