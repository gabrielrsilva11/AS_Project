/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoadBalancer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import model.Heartbeat;
import utils.ConnectionInfo;

/**
 *
 * @author gabri
 */
public class LB_GUI_Actions {

    private MonitorGUI gui;

    private Map<Integer, ConnectionInfo> serverConnections;

    private Map<Integer, List<Integer>> serverRequest;

    private Map<Integer, List<Integer>> serverRequestComplete;

    private Map<Integer, Heartbeat> serverStatus;

    public LB_GUI_Actions(MonitorGUI gui, Map<Integer, ConnectionInfo> serverConnections, Map<Integer, List<Integer>> serverRequest, Map<Integer, List<Integer>> serverRequestComplete, Map<Integer, Heartbeat> serverStatus) {
        this.gui = gui;
        this.serverConnections = serverConnections;
        this.serverRequest = serverRequest;
        this.serverRequestComplete = serverRequestComplete;
        this.serverStatus = serverStatus;
    }

    public void start() {
        gui.setVisible(true);
        statusButtonListener();
        closeStatusButtonListener();
        searchStatusButtonListener();
        loadButtonListener();
        closeLoadButtonListener();
        searchLoadButtonListener();
        completedButtonListener();
        closeCompletedButtonListener();
        processingButtonListener();
        closeProcessingButtonListener();
        historyButtonListener();
        closeHistoryButtonListener();
        exitButtonListener();
    }

    private void statusButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Status().setVisible(true);
            gui.getFrame_Status().setSize(415, 272);
            gui.getStatus_TextArea().setText("");
            for (int key : serverStatus.keySet()) {
                String statusText = "ServerID: " + key + "||" + serverStatus.get(key).toString() + "\n";
                gui.getStatus_TextArea().append(statusText);
            }
        };
        gui.getButton_Status().addActionListener(actionListener);
    }

    private void closeStatusButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Status().setVisible(false);
        };
        gui.getButton_CloseStatus().addActionListener(actionListener);
    }

    private void searchStatusButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getStatus_TextArea().setText("");
            int serverId = Integer.parseInt(gui.getStatusSearch_Text().getText());
            Heartbeat value = serverStatus.get(serverId);
            if (value == null) {
                gui.getStatus_TextArea().append("There are no servers with that ID\n");
            } else {
                String statusText = "ServerID: " + serverId + "||" + value.toString() + "\n";
                gui.getStatus_TextArea().append(statusText);
            }
        };
        gui.getButton_StatusSearch().addActionListener(actionListener);
    }

    private void loadButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Load().setVisible(true);
            gui.getFrame_Load().setSize(415, 272);
            gui.getLoad_TextArea().setText("");
            for (int key : serverRequest.keySet()) {
                String text = "ServerID: " + key + "|| Number of Requests: " + serverRequest.get(key).size() + "\n";
                gui.getLoad_TextArea().append(text);
            }
        };
        gui.getButton_Load().addActionListener(actionListener);
    }

    private void closeLoadButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Load().setVisible(false);
        };
        gui.getButton_CloseLoad().addActionListener(actionListener);
    }

    private void searchLoadButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            int serverId = Integer.parseInt(gui.getLoadSearch_Text().getText());
            gui.getLoadSearch_Text().setText("");
            List<Integer> results = serverRequest.get(serverId);
            if (results == null) {
                gui.getStatus_TextArea().append("There are no servers with that ID\n");
            } else {
                String text = "ServerID: " + serverId + "|| Number of Requests: " + results.size() + "\n";
                gui.getLoad_TextArea().append(text);
            }
        };
        gui.getButton_LoadSearch().addActionListener(actionListener);
    }

    private void completedButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Completed().setVisible(true);
            gui.getFrame_Completed().setSize(415, 250);
            gui.getCompleted_TextArea().setText("");
            for (int key : serverRequestComplete.keySet()) {
                String text = "ServerID: " + key + "|| Requests Completed: " + serverRequestComplete.get(key).toString() + "\n";
                gui.getCompleted_TextArea().append(text);
            }
        };
        gui.getButton_Completed().addActionListener(actionListener);
    }

    private void closeCompletedButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Completed().setVisible(false);
        };
        gui.getButton_CloseCompleted().addActionListener(actionListener);
    }

    private void processingButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processing().setVisible(true);
            gui.getFrame_Processing().setSize(415, 250);
            gui.getProcessed_TextArea().setText("");
            for (int key : serverRequest.keySet()) {
                String text = "ServerID: " + key + "|| RequestsProcessing: " + serverRequest.get(key).toString() + "\n";
                gui.getProcessed_TextArea().append(text);
            }
        };
        gui.getButton_Processing().addActionListener(actionListener);
    }

    private void closeProcessingButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processing().setVisible(false);
        };
        gui.getButton_CloseProcessed().addActionListener(actionListener);
    }

    private void historyButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(true);
            gui.getFrame_History().setSize(415, 250);
            gui.getHistory_TextArea().setText("");
            for (int key : serverRequest.keySet()) {
                String text = "ServerID: " + key + "|| RequestsProcessing: " + serverRequest.get(key).toString() + "\n";
                gui.getHistory_TextArea().append(text);
            }
            for (int key : serverRequestComplete.keySet()) {
                String text = "ServerID: " + key + "|| Requests Completed: " + serverRequestComplete.get(key).toString() + "\n";
                gui.getHistory_TextArea().append(text);
            }
        };
        gui.getButton_History().addActionListener(actionListener);
    }

    private void closeHistoryButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };
        gui.getButton_CloseHistory().addActionListener(actionListener);
    }
    
    private void exitButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.exit(0);
        };
        gui.getExit_Button().addActionListener(actionListener);
    }
}
