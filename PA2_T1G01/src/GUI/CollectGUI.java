package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * CollectGUI - GUI to display data from the CollectEntity and send messages.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class CollectGUI extends javax.swing.JPanel {
    JFrame history;
    /**
     * Creates new form CollectEntity
     */
    public CollectGUI() {
        initComponents();
    }
    
    /**
     * Getter method for the Message Text area
     * @return Message Text Area 
     */
    public JTextField getMessageText(){
        return Message_Text;
    }
    
    /**
     * Getter method for the Send Reordered Button
     * @return Send Reordered button 
     */
    public JButton getReorderedButton(){
        return reorderedButton;
    }
       /**
     * Getter method for the History button
     * @return history button
     */
    public JButton getHistoryButton(){
        return historyButton;
    }
    
    /**
     * Getter method for the History Panel
     * @return history panel
     */
    public JPanel getHistoryPanel(){
        return HistoryPanel;
    }
    
    /**
     * Getter method for the History text area
     * @return history text area
     */
    public JTextArea getHistoryText(){
        return Text_History;
    }
    
    /**
     * Getter method for the close button of the history panel
     * @return history panel close button
     */
    public JButton getCloseHistoryButton(){
        return Close_Button;
    }
    
    /**
     * Getter method for the Send All Button
     * @return Send All button 
     */
    public JButton getSendButton(){
        return sendButton;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        HistoryPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Text_History = new javax.swing.JTextArea();
        Close_Button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Message_Text = new javax.swing.JTextField();
        historyButton = new javax.swing.JButton();
        sendButton = new javax.swing.JButton();
        reorderedButton = new javax.swing.JButton();

        Text_History.setColumns(20);
        Text_History.setRows(5);
        jScrollPane1.setViewportView(Text_History);

        Close_Button.setText("Close");
        Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Close_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HistoryPanelLayout = new javax.swing.GroupLayout(HistoryPanel);
        HistoryPanel.setLayout(HistoryPanelLayout);
        HistoryPanelLayout.setHorizontalGroup(
            HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(HistoryPanelLayout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(Close_Button)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HistoryPanelLayout.setVerticalGroup(
            HistoryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HistoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Close_Button))
        );

        jLabel1.setText("Collect");

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Last Message Sent");

        historyButton.setText("History");
        historyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyButtonActionPerformed(evt);
            }
        });

        sendButton.setText("Send All");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        reorderedButton.setText("Send Reordered");
        reorderedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderedButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(137, 137, 137)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Message_Text)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(reorderedButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(historyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Message_Text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(historyButton)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(sendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reorderedButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void historyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyButtonActionPerformed
//        history = new JFrame();
//        history.add(HistoryPanel);
//        history.setVisible(true);
//        history.setSize(380,280);
//        history.setResizable(true);
//        BufferedReader br = null;
//        try{
//            br = new BufferedReader(new FileReader(PATH_TO_DATA));
//            String line;
//            while((line = br.readLine())!= null){
//                Text_History.append(line + "\n");
//            }
//        }catch(IOException e){
//            System.out.println("Error opening file" + e);
//        }finally{
//            try {
//                br.close();
//            } catch (IOException e) {
//                System.out.println("Error closing file" + e);
//            }
//        }
    }//GEN-LAST:event_historyButtonActionPerformed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        //ce.sendRecords(Message_Text);
    }//GEN-LAST:event_sendButtonActionPerformed

    private void Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close_ButtonActionPerformed
        
    }//GEN-LAST:event_Close_ButtonActionPerformed

    private void reorderedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderedButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reorderedButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Close_Button;
    private javax.swing.JPanel HistoryPanel;
    private javax.swing.JTextField Message_Text;
    private javax.swing.JTextArea Text_History;
    private javax.swing.JButton historyButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton reorderedButton;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
