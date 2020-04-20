package entities;

import GUI.BatchGUI;
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
* BatchEntity - Kafka consumer entity to receive and process messages from the BatchTopic
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class BatchEntity {
    
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
    private BatchGUI beGUI = null;
    /**
     * JFrame to display the GUI
     */
    private JFrame gui = null;
    /**
     * JFrame to display the history GUI
     */
    private JFrame history = null;
    /**
     * BatchEntity class constructor
     * 
     * @param topic Kafka topic subscribed
     */
    public BatchEntity(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "BatchEntity");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
        
        beGUI = new BatchGUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(450,250);
        gui.setResizable(true);
        gui.add(beGUI);
        historyButtonListener();
        closeHistoryButtonListener();
    }

    /**
     * Method to consume messages from the subscribed topic and store them in the 
     * referred file
     * 
     */
    public void ConsumeBatch() {
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, Message> records = consumer.poll(100);
            for (ConsumerRecord<String, Message> record : records) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_BATCH, true))) {
                    writer.append(String.format("Car registration: %s, Date: %s, Message type: %d\n", record.value().getCarReg(), new Date(record.value().getTs()), record.value().getType()));
                    beGUI.setMessageText(String.format("Car registration: %s Date: %s", record.value().getCarReg(), new Date(record.value().getTs())));
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + PATH_TO_BATCH);
                }
            }
        }
    }
    
    /**
     * Method that will create the history panel and set its text
     */
    private void historyText() {
        history = new JFrame();
        history.add(beGUI.getHistoryPanel());
        history.setVisible(true);
        history.setSize(450,400);
        history.setResizable(true);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(PATH_TO_BATCH));
            String line;
            while ((line = br.readLine()) != null) {
                beGUI.getHistoryText().append(line+"\n");
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
        JButton historyButton = beGUI.getHistoryButton();

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
        JButton closeButton = beGUI.getCloseHistoryButton();
        
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
        BatchEntity batch = new BatchEntity(KafkaProperties.BATCH_TOPIC);
        batch.ConsumeBatch();
    }
}
