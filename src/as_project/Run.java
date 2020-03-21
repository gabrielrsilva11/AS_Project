/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

import as_project.GUI.CC_GUI;
import javax.swing.JFrame;

/**
 *
 * @author gabri
 */
public class Run {
    
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
