package LoadBalancer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * MonitorGUI - Contains the GUI to be used by the monitor and the load
 * balancer as well as the getters for each relevant component.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class MonitorGUI extends javax.swing.JFrame {

    /**
     * Creates new form MonitorGUI
     */
    public MonitorGUI() {
        initComponents();
    }

    public JButton getButton_History() {
        return Button_History;
    }

    public JTextField getCompleted_Text() {
        return Completed_Text;
    }

    public JTextField getProcessing_Text() {
        return Processing_Text;
    }

    public JButton getButton_Completed() {
        return Button_Completed;
    }

    public JButton getButton_Processing() {
        return Button_Processing;
    }

    public JButton getButton_CloseCompleted() {
        return Button_CloseCompleted;
    }

    public JButton getButton_CloseHistory() {
        return Button_CloseHistory;
    }

    public JButton getButton_CloseLoad() {
        return Button_CloseLoad;
    }

    public JButton getButton_CloseProcessed() {
        return Button_CloseProcessed;
    }

    public JButton getButton_CloseStatus() {
        return Button_CloseStatus;
    }

    public JButton getButton_Load() {
        return Button_Load;
    }

    public JButton getButton_LoadSearch() {
        return Button_LoadSearch;
    }

    public JButton getButton_Status() {
        return Button_Status;
    }

    public JButton getButton_StatusSearch() {
        return Button_StatusSearch;
    }

    public JTextField getClients_Text() {
        return Clients_Text;
    }

    public JTextArea getCompleted_TextArea() {
        return Completed_TextArea;
    }

    public JButton getExit_Button() {
        return Exit_Button;
    }

    public JFrame getFrame_Completed() {
        return Frame_Completed;
    }

    public JFrame getFrame_History() {
        return Frame_History;
    }

    public JFrame getFrame_Load() {
        return Frame_Load;
    }

    public JFrame getFrame_Processing() {
        return Frame_Processing;
    }

    public JFrame getFrame_Status() {
        return Frame_Status;
    }

    public JTextArea getHistory_TextArea() {
        return History_TextArea;
    }

    public JTextField getLoadSearch_Text() {
        return LoadSearch_Text;
    }

    public JTextArea getLoad_TextArea() {
        return Load_TextArea;
    }

    public JTextArea getProcessed_TextArea() {
        return Processed_TextArea;
    }

    public JTextField getServers_Text() {
        return Servers_Text;
    }

    public JTextField getStatusSearch_Text() {
        return StatusSearch_Text;
    }

    public JTextArea getStatus_TextArea() {
        return Status_TextArea;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Frame_Processing = new javax.swing.JFrame();
        jScrollPane3 = new javax.swing.JScrollPane();
        Processed_TextArea = new javax.swing.JTextArea();
        Button_CloseProcessed = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        Frame_History = new javax.swing.JFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        History_TextArea = new javax.swing.JTextArea();
        Button_CloseHistory = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        Frame_Completed = new javax.swing.JFrame();
        jScrollPane4 = new javax.swing.JScrollPane();
        Completed_TextArea = new javax.swing.JTextArea();
        Button_CloseCompleted = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        Frame_Status = new javax.swing.JFrame();
        jScrollPane5 = new javax.swing.JScrollPane();
        Status_TextArea = new javax.swing.JTextArea();
        Button_CloseStatus = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        StatusSearch_Text = new javax.swing.JTextField();
        Button_StatusSearch = new javax.swing.JButton();
        Frame_Load = new javax.swing.JFrame();
        jScrollPane6 = new javax.swing.JScrollPane();
        Load_TextArea = new javax.swing.JTextArea();
        Button_CloseLoad = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        Button_LoadSearch = new javax.swing.JButton();
        LoadSearch_Text = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        Exit_Button = new javax.swing.JButton();
        Button_History = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Clients_Text = new javax.swing.JTextField();
        Servers_Text = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Completed_Text = new javax.swing.JTextField();
        Processing_Text = new javax.swing.JTextField();
        Button_Status = new javax.swing.JButton();
        Button_Load = new javax.swing.JButton();
        Button_Completed = new javax.swing.JButton();
        Button_Processing = new javax.swing.JButton();

        Processed_TextArea.setEditable(false);
        Processed_TextArea.setColumns(20);
        Processed_TextArea.setRows(5);
        jScrollPane3.setViewportView(Processed_TextArea);

        Button_CloseProcessed.setText("Close");

        jLabel10.setText("Requests Processing");

        javax.swing.GroupLayout Frame_ProcessingLayout = new javax.swing.GroupLayout(Frame_Processing.getContentPane());
        Frame_Processing.getContentPane().setLayout(Frame_ProcessingLayout);
        Frame_ProcessingLayout.setHorizontalGroup(
            Frame_ProcessingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_ProcessingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Frame_ProcessingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(Frame_ProcessingLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Frame_ProcessingLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Button_CloseProcessed)
                .addGap(171, 171, 171))
        );
        Frame_ProcessingLayout.setVerticalGroup(
            Frame_ProcessingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_ProcessingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_CloseProcessed)
                .addContainerGap())
        );

        History_TextArea.setColumns(20);
        History_TextArea.setRows(5);
        jScrollPane2.setViewportView(History_TextArea);

        Button_CloseHistory.setText("Close");

        jLabel9.setText("History");

        javax.swing.GroupLayout Frame_HistoryLayout = new javax.swing.GroupLayout(Frame_History.getContentPane());
        Frame_History.getContentPane().setLayout(Frame_HistoryLayout);
        Frame_HistoryLayout.setHorizontalGroup(
            Frame_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_HistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Frame_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(Frame_HistoryLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Frame_HistoryLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(Button_CloseHistory)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame_HistoryLayout.setVerticalGroup(
            Frame_HistoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_HistoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_CloseHistory)
                .addContainerGap())
        );

        Completed_TextArea.setEditable(false);
        Completed_TextArea.setColumns(20);
        Completed_TextArea.setRows(5);
        jScrollPane4.setViewportView(Completed_TextArea);

        Button_CloseCompleted.setText("Close");

        jLabel11.setText("Requests Completed");

        javax.swing.GroupLayout Frame_CompletedLayout = new javax.swing.GroupLayout(Frame_Completed.getContentPane());
        Frame_Completed.getContentPane().setLayout(Frame_CompletedLayout);
        Frame_CompletedLayout.setHorizontalGroup(
            Frame_CompletedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_CompletedLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Frame_CompletedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addGroup(Frame_CompletedLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Frame_CompletedLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(Button_CloseCompleted)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Frame_CompletedLayout.setVerticalGroup(
            Frame_CompletedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_CompletedLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_CloseCompleted)
                .addContainerGap())
        );

        Status_TextArea.setEditable(false);
        Status_TextArea.setColumns(20);
        Status_TextArea.setRows(5);
        jScrollPane5.setViewportView(Status_TextArea);

        Button_CloseStatus.setText("Close");

        jLabel12.setText("Server Status");

        StatusSearch_Text.setText("ServerID to look up");

        Button_StatusSearch.setText("Search");

        javax.swing.GroupLayout Frame_StatusLayout = new javax.swing.GroupLayout(Frame_Status.getContentPane());
        Frame_Status.getContentPane().setLayout(Frame_StatusLayout);
        Frame_StatusLayout.setHorizontalGroup(
            Frame_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_StatusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Frame_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Frame_StatusLayout.createSequentialGroup()
                        .addComponent(StatusSearch_Text)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Button_StatusSearch))
                    .addGroup(Frame_StatusLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Frame_StatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Button_CloseStatus)
                .addGap(174, 174, 174))
        );
        Frame_StatusLayout.setVerticalGroup(
            Frame_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_StatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Frame_StatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StatusSearch_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_StatusSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Button_CloseStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Load_TextArea.setEditable(false);
        Load_TextArea.setColumns(20);
        Load_TextArea.setRows(5);
        jScrollPane6.setViewportView(Load_TextArea);

        Button_CloseLoad.setText("Close");

        jLabel13.setText("Server Load");

        Button_LoadSearch.setText("Search");

        LoadSearch_Text.setText("ServerID to look up");

        javax.swing.GroupLayout Frame_LoadLayout = new javax.swing.GroupLayout(Frame_Load.getContentPane());
        Frame_Load.getContentPane().setLayout(Frame_LoadLayout);
        Frame_LoadLayout.setHorizontalGroup(
            Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_LoadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Frame_LoadLayout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addComponent(Button_CloseLoad)
                        .addGap(182, 182, 182))
                    .addGroup(Frame_LoadLayout.createSequentialGroup()
                        .addGroup(Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Frame_LoadLayout.createSequentialGroup()
                                .addGroup(Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Frame_LoadLayout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(LoadSearch_Text))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Button_LoadSearch)
                                .addGap(6, 6, 6))
                            .addComponent(jScrollPane6))
                        .addContainerGap())))
        );
        Frame_LoadLayout.setVerticalGroup(
            Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Frame_LoadLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(8, 8, 8)
                .addGroup(Frame_LoadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LoadSearch_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_LoadSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Button_CloseLoad)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Load balancer / Monitor GUI");

        Exit_Button.setText("Exit");
        Exit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_ButtonActionPerformed(evt);
            }
        });

        Button_History.setText("History");

        jLabel2.setText("Number of Clients");

        jLabel3.setText("Number of Servers");

        Clients_Text.setEditable(false);
        Clients_Text.setText("0");

        Servers_Text.setEditable(false);
        Servers_Text.setText("0");

        jLabel4.setText("Requests Processing");

        jLabel5.setText("Requests Completed");

        Completed_Text.setEditable(false);
        Completed_Text.setText("0");

        Processing_Text.setEditable(false);
        Processing_Text.setText("0");

        Button_Status.setText("Server Status");

        Button_Load.setText("Server Load");

        Button_Completed.setText("Completed");

        Button_Processing.setText("Processing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Button_Load, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Button_Processing, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(Exit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Button_Status, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Button_Completed, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(Button_History, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Clients_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Servers_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Processing_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Completed_Text, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Clients_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(Completed_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Servers_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(Processing_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_Status)
                    .addComponent(Button_Completed)
                    .addComponent(Button_History))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Button_Load)
                    .addComponent(Button_Processing)
                    .addComponent(Exit_Button))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Exit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_ButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Exit_ButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonitorGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MonitorGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_CloseCompleted;
    private javax.swing.JButton Button_CloseHistory;
    private javax.swing.JButton Button_CloseLoad;
    private javax.swing.JButton Button_CloseProcessed;
    private javax.swing.JButton Button_CloseStatus;
    private javax.swing.JButton Button_Completed;
    private javax.swing.JButton Button_History;
    private javax.swing.JButton Button_Load;
    private javax.swing.JButton Button_LoadSearch;
    private javax.swing.JButton Button_Processing;
    private javax.swing.JButton Button_Status;
    private javax.swing.JButton Button_StatusSearch;
    private javax.swing.JTextField Clients_Text;
    private javax.swing.JTextField Completed_Text;
    private javax.swing.JTextArea Completed_TextArea;
    private javax.swing.JButton Exit_Button;
    private javax.swing.JFrame Frame_Completed;
    private javax.swing.JFrame Frame_History;
    private javax.swing.JFrame Frame_Load;
    private javax.swing.JFrame Frame_Processing;
    private javax.swing.JFrame Frame_Status;
    private javax.swing.JTextArea History_TextArea;
    private javax.swing.JTextField LoadSearch_Text;
    private javax.swing.JTextArea Load_TextArea;
    private javax.swing.JTextArea Processed_TextArea;
    private javax.swing.JTextField Processing_Text;
    private javax.swing.JTextField Servers_Text;
    private javax.swing.JTextField StatusSearch_Text;
    private javax.swing.JTextArea Status_TextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    // End of variables declaration//GEN-END:variables
}
