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
public class Run {
        public static void main(String[] args) {
            CC_GUI cc_gui = new CC_GUI();
            JFrame gui = new JFrame();
            gui.setVisible(true);
            gui.setSize(350, 280);
            gui.setResizable(false);
            gui.add(cc_gui);
    }
}
