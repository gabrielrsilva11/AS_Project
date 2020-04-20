package entities;

import GUI.ReportGUI;
import config.KafkaProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import util.Message;
import util.MessageDeserializer;

import static util.Constants.*;

/**
* ReportEntity - Kafka consumer entity to receive and process messages from the ReportTopic
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class ReportEntity {

    /**
    * Kafka consumer to receive messages
    */
    Consumer<String, Message> consumer;
    /**
    * Kafka topic subscribed
    */
    String topic;
    /**
     * Variable to store the GUI
     */
    private ReportGUI reGUI = null;
    /**
     * JFrame to display the main GUI
     */
    private JFrame gui = null;
    /**
     * JFrame to display the history GUI
     */
    private JFrame history = null;
    /**
     * ReportEntity class constructor
     * 
     * @param topic Kafka topic subscribed
     */
    public ReportEntity(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "ReportEntity");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
        reGUI = new ReportGUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(450,200);
        gui.setResizable(true);
        gui.add(reGUI);
        historyButtonListener();
        closeHistoryButtonListener();
    }

    /**
     * Method to consume messages from the subscribed topic and store them in the 
     * referred file
     * 
     */
    public void ConsumeReport() {
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, Message> records = consumer.poll(100);
            for (ConsumerRecord<String, Message> record : records) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_REPORT, true))) {
                    writer.append(String.format("Car registration: %s, Date: %s, Message type: %d, Information: %s\n",
                            record.value().getCarReg(), new Date(record.value().getTs()), record.value().getType(), record.value().getExtraInfo()));
                    reGUI.setMessageText(String.format("Car registration: %s Date: %s", record.value().getCarReg(), new Date(record.value().getTs())));
                    reGUI.setStatusText(record.value().getExtraInfo());
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + PATH_TO_REPORT);
                }
            }
        }
    }
    /**
     * Method that will create the history panel and set its text
     */
    private void historyText() {
        history = new JFrame();
        history.add(reGUI.getHistoryPanel());
        history.setVisible(true);
        history.setSize(450,400);
        history.setResizable(true);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(PATH_TO_REPORT));
            String line;
            while ((line = br.readLine()) != null) {
                reGUI.getHistoryText().append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error opening file" + e);
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Error closing file" + e);
            }
        }
    }
    /**
     * Listener method for the History button
     */
    private void historyButtonListener() {
        JButton historyButton = reGUI.getHistoryButton();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History button");
            historyText();
        };
        
        historyButton.addActionListener(actionListener);
    }
    /**
     * Listener method for the close button on the history panel
     */
    private void closeHistoryButtonListener() {
        JButton closeButton = reGUI.getCloseHistoryButton();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            history.setVisible(false);
        };
        closeButton.addActionListener(actionListener);
    }
    /**
     * Method to run the program, starts the BatchEntity
     * @param args arguments used when running the program
     */
    public static void main(String[] args) {
        ReportEntity report = new ReportEntity(KafkaProperties.REPORT_TOPIC);
        //report.ConsumeReport();
    }
}
