/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project.GUI;

import as_project.ControlCenter;

/**
 *
 * @author gabri
 */
public class CC_GUI extends javax.swing.JPanel {
    
    private ControlCenter cc = null;
    /**
     * Creates new form CC_GUI
     */
    public CC_GUI() {
        initComponents();
        setAllButtons(false);
        Button_Prepare.setEnabled(true);
        Button_Stop.setEnabled(true);
        Text_Collected.setEditable(false);
        cc = new ControlCenter(); 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Dropdown_NumFarmers = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Dropdown_NumSteps = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        Dropdown_Timeout = new javax.swing.JComboBox<>();
        Button_Prepare = new javax.swing.JToggleButton();
        Button_Start = new javax.swing.JToggleButton();
        Button_Collect = new javax.swing.JToggleButton();
        Button_Return = new javax.swing.JToggleButton();
        Button_Stop = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Text_Collected = new javax.swing.JTextField();
        Button_Exit = new javax.swing.JButton();

        setName("Control Center"); // NOI18N
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Control Center");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Dropdown_NumFarmers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5" }));
        Dropdown_NumFarmers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dropdown_NumFarmersActionPerformed(evt);
            }
        });
        add(Dropdown_NumFarmers, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 58, -1));

        jLabel2.setText("Farmers");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabel3.setText("Steps");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, -1, -1));

        Dropdown_NumSteps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        add(Dropdown_NumSteps, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 58, -1));

        jLabel4.setText("Timeout");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, -1, -1));

        Dropdown_Timeout.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "100", "250", "500", "1000" }));
        add(Dropdown_Timeout, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        Button_Prepare.setText("Prepare");
        Button_Prepare.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_PrepareMouseClicked(evt);
            }
        });
        Button_Prepare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_PrepareActionPerformed(evt);
            }
        });
        add(Button_Prepare, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 80, -1));

        Button_Start.setText("Start");
        Button_Start.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_StartMouseClicked(evt);
            }
        });
        Button_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_StartActionPerformed(evt);
            }
        });
        add(Button_Start, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 80, -1));

        Button_Collect.setText("Collect");
        Button_Collect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_CollectMouseClicked(evt);
            }
        });
        add(Button_Collect, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, 80, -1));

        Button_Return.setText("Return");
        Button_Return.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_ReturnMouseClicked(evt);
            }
        });
        Button_Return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ReturnActionPerformed(evt);
            }
        });
        add(Button_Return, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, 80, -1));

        Button_Stop.setText("Stop");
        Button_Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_StopActionPerformed(evt);
            }
        });
        add(Button_Stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, 80, -1));

        jLabel5.setText("Corn Cobs Collected");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        Text_Collected.setText("0");
        Text_Collected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Text_CollectedActionPerformed(evt);
            }
        });
        add(Text_Collected, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 80, -1));

        Button_Exit.setText("Exit");
        Button_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Button_ExitMouseClicked(evt);
            }
        });
        Button_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ExitActionPerformed(evt);
            }
        });
        add(Button_Exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 60, -1));

        getAccessibleContext().setAccessibleName("Control Center");
    }// </editor-fold>//GEN-END:initComponents

    private void Dropdown_NumFarmersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dropdown_NumFarmersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dropdown_NumFarmersActionPerformed
    private void setAllButtons(boolean estado){
        Button_Collect.setEnabled(estado);
        Button_Prepare.setEnabled(estado);
        Button_Start.setEnabled(estado);
        Button_Return.setEnabled(estado);
        Button_Stop.setEnabled(estado);
    }
    
    private void setAllDropdowns(boolean estado){
        Dropdown_NumFarmers.setEnabled(estado);
        Dropdown_NumSteps.setEnabled(estado);
        Dropdown_Timeout.setEnabled(estado);
    }
    
    private String getDropdownValues(){
        String numFarmers = Dropdown_NumFarmers.getSelectedItem().toString();
        String numSteps = Dropdown_NumSteps.getSelectedItem().toString();
        String timeout = Dropdown_NumSteps.getSelectedItem().toString();
        
        String message = numFarmers + ',' + numSteps + ',' + timeout;
        return message;
    }
    
    private void Button_PrepareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_PrepareActionPerformed

    }//GEN-LAST:event_Button_PrepareActionPerformed

    private void Button_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_StartActionPerformed
        
    }//GEN-LAST:event_Button_StartActionPerformed

    private void Button_ReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ReturnActionPerformed

    }//GEN-LAST:event_Button_ReturnActionPerformed

    private void Text_CollectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Text_CollectedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Text_CollectedActionPerformed

    private void Button_StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_StopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Button_StopActionPerformed

    private void Button_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ExitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Button_ExitActionPerformed

    private void Button_StartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_StartMouseClicked
        Button_Start.setEnabled(false);
        Button_Stop.setEnabled(false);
        cc.sendMessage("start", Button_Collect, Button_Stop);
    }//GEN-LAST:event_Button_StartMouseClicked

    private void Button_PrepareMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_PrepareMouseClicked
        cc.setParameters(getDropdownValues());
        Button_Prepare.setEnabled(false);
        cc.sendMessage("prepare", Button_Start, Button_Stop);
    }//GEN-LAST:event_Button_PrepareMouseClicked

    private void Button_CollectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_CollectMouseClicked
        Button_Collect.setEnabled(false);
        Button_Stop.setEnabled(false);
        cc.sendMessage("collect", Button_Return, Button_Stop);
    }//GEN-LAST:event_Button_CollectMouseClicked

    private void Button_ReturnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_ReturnMouseClicked
        Button_Return.setEnabled(false);
        Button_Stop.setEnabled(false);
        cc.sendMessage("return", Button_Prepare, Button_Stop);
        setAllDropdowns(true);
    }//GEN-LAST:event_Button_ReturnMouseClicked

    private void Button_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Button_ExitMouseClicked
        cc.Exit("exit");
    }//GEN-LAST:event_Button_ExitMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Button_Collect;
    private javax.swing.JButton Button_Exit;
    private javax.swing.JToggleButton Button_Prepare;
    private javax.swing.JToggleButton Button_Return;
    private javax.swing.JToggleButton Button_Start;
    private javax.swing.JButton Button_Stop;
    private javax.swing.JComboBox<String> Dropdown_NumFarmers;
    private javax.swing.JComboBox<String> Dropdown_NumSteps;
    private javax.swing.JComboBox<String> Dropdown_Timeout;
    private javax.swing.JTextField Text_Collected;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
