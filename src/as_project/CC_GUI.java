/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package as_project;

/**
 *
 * @author gabri
 */
public class CC_GUI extends javax.swing.JPanel {

    /**
     * Creates new form CC_GUI
     */
    public CC_GUI() {
        initComponents();
        Button_Start.setEnabled(false);
        Button_Collect.setEnabled(false);
        Button_Return.setEnabled(false);
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
        jComboBox1 = new javax.swing.JComboBox<>();
        Button_Prepare = new javax.swing.JToggleButton();
        Button_Start = new javax.swing.JToggleButton();
        Button_Collect = new javax.swing.JToggleButton();
        Button_Return = new javax.swing.JToggleButton();
        Button_Stop = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Text_Collected = new javax.swing.JTextField();

        setName("Control Center"); // NOI18N
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Control Center");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 18, -1, -1));

        Dropdown_NumFarmers.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "5" }));
        Dropdown_NumFarmers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Dropdown_NumFarmersActionPerformed(evt);
            }
        });
        add(Dropdown_NumFarmers, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 67, 58, -1));

        jLabel2.setText("Farmers");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 72, -1, -1));

        jLabel3.setText("Steps");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 110, -1, -1));

        Dropdown_NumSteps.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2" }));
        add(Dropdown_NumSteps, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 105, 58, -1));

        jLabel4.setText("Timeout");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 148, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "100", "250", "500", "1000" }));
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 143, -1, -1));

        Button_Prepare.setText("Prepare");
        Button_Prepare.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_PrepareActionPerformed(evt);
            }
        });
        add(Button_Prepare, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 46, -1, -1));

        Button_Start.setText("Start");
        Button_Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_StartActionPerformed(evt);
            }
        });
        add(Button_Start, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 86, 72, -1));

        Button_Collect.setText("Collect");
        add(Button_Collect, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 123, 72, -1));

        Button_Return.setText("Return");
        Button_Return.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_ReturnActionPerformed(evt);
            }
        });
        add(Button_Return, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 163, 71, -1));

        Button_Stop.setText("Stop");
        add(Button_Stop, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 199, 71, -1));

        jLabel5.setText("Corn Cobs Collected");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(132, 187, -1, -1));

        Text_Collected.setText("0");
        Text_Collected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Text_CollectedActionPerformed(evt);
            }
        });
        add(Text_Collected, new org.netbeans.lib.awtextra.AbsoluteConstraints(253, 181, 58, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void Dropdown_NumFarmersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Dropdown_NumFarmersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dropdown_NumFarmersActionPerformed

    private void Button_PrepareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_PrepareActionPerformed
        // TODO add your handling code here:
        Button_Prepare.setEnabled(false);
        Button_Start.setEnabled(true);
    }//GEN-LAST:event_Button_PrepareActionPerformed

    private void Button_StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_StartActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_Button_StartActionPerformed

    private void Button_ReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_ReturnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Button_ReturnActionPerformed

    private void Text_CollectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Text_CollectedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Text_CollectedActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Button_Collect;
    private javax.swing.JToggleButton Button_Prepare;
    private javax.swing.JToggleButton Button_Return;
    private javax.swing.JToggleButton Button_Start;
    private javax.swing.JButton Button_Stop;
    private javax.swing.JComboBox<String> Dropdown_NumFarmers;
    private javax.swing.JComboBox<String> Dropdown_NumSteps;
    private javax.swing.JTextField Text_Collected;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
