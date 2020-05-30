package LoadBalancer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import model.Heartbeat;

/**
 * LB_GUI_Actions - Class that handles the listeners for the GUI buttons.
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class LB_GUI_Actions {

    /**
     * GUI that contains the buttons to listen to
     */
    private MonitorGUI gui;

    /**
     * Concurrent map that stores ID and a list of requestIDs the server is
     * processing
     */
    private Map<Integer, List<Integer>> serverRequest;

    /**
     * Concurrent map that stores ID and a list of requestIDs the server has
     * processed
     */
    private Map<Integer, List<Integer>> serverRequestComplete;

    /**
     * Concurrent map to store serverID and last heartbeat sent
     */
    private Map<Integer, Heartbeat> serverStatus;

    /**
     * Class constructor
     *
     * @param gui MonitorGUI - variable with a initialized GUI
     * @param serverRequest Map - contains the server requests
     * @param serverRequestComplete Map - contains the completed server requests
     * @param serverStatus Map - contains the server status
     */
    public LB_GUI_Actions(MonitorGUI gui, Map<Integer, List<Integer>> serverRequest, Map<Integer, List<Integer>> serverRequestComplete, Map<Integer, Heartbeat> serverStatus) {
        this.gui = gui;
        this.serverRequest = serverRequest;
        this.serverRequestComplete = serverRequestComplete;
        this.serverStatus = serverStatus;
    }

    /**
     * Initializes all the listeners and sets the GUI visible
     */
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

    /**
     * Listener method for the status button
     */
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

    /**
     * Listener method for the close button of the status panel
     */
    private void closeStatusButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Status().setVisible(false);
        };
        gui.getButton_CloseStatus().addActionListener(actionListener);
    }

    /**
     * Listener method for the search button of the status panel
     */
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

    /**
     * Listener method for the load button
     */
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

    /**
     * Listener method for the close button of the load panel
     */
    private void closeLoadButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Load().setVisible(false);
        };
        gui.getButton_CloseLoad().addActionListener(actionListener);
    }

    /**
     * Listener method for the search button of the load panel
     */
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

    /**
     * Listener method for the completed button
     */
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

    /**
     * Listener method for the close button if the completed panel
     */
    private void closeCompletedButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Completed().setVisible(false);
        };
        gui.getButton_CloseCompleted().addActionListener(actionListener);
    }

    /**
     * Listener method for the processing button
     */
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

    /**
     * Listener method for the close button of the processing panel
     */
    private void closeProcessingButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_Processing().setVisible(false);
        };
        gui.getButton_CloseProcessed().addActionListener(actionListener);
    }

    /**
     * Listener method for the history button
     */
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

    /**
     * Listener method for the close button of the history panel
     */
    private void closeHistoryButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            gui.getFrame_History().setVisible(false);
        };
        gui.getButton_CloseHistory().addActionListener(actionListener);
    }

    /**
     * Listener method for the exit button
     */
    private void exitButtonListener() {
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.exit(0);
        };
        gui.getExit_Button().addActionListener(actionListener);
    }
}
