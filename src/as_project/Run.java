package as_project;

import as_project.GUI.CC_GUI;
import javax.swing.JFrame;

/**
* Run - Starts the program and puts the control center and the Farm Infrastructure in different threads
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class Run {
        /**
         * Method to run the program, starts the FI and the CC
         * @param args arguments used when running the program
         */
        public static void main(String[] args) {
            FarmInfrastructure fi = new FarmInfrastructure();
            fi.start();
            
            CC_GUI cc_gui = new CC_GUI();
            JFrame cc_frame = new JFrame();
            cc_frame.setVisible(true);
            cc_frame.setSize(463, 280);
            cc_frame.setResizable(false);
            cc_frame.add(cc_gui);
    }
}
