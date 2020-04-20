package entities;

import GUI.AlarmGUI;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import util.Message;
import util.MessageDeserializer;

import static util.Constants.*;

/**
 * AlarmEntity - Kafka consumer entity to receive and process messages from the
 * AlarmTopic
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class AlarmEntity {

    /**
     * Kafka consumer to receive messages
     */
    private Consumer<String, Message> consumer;
    /**
     * Kafka topic subscribed
     */
    private String topic;
    /**
    * Structure containing generated alarms
    */
    private Map<String, String> generatedAlarms;
    /**
    * Structure containing received messages
    */
    Set<Integer> receivedMessages;
    /**
     * Variable to store the GUI
     */
    private AlarmGUI aeGUI = null;
    /**
     * JFrame to display the main GUI
     */
    private JFrame gui = null;
    
    /**
     * JFrame to display the history GUI
     */
    private JFrame history = null;
    /**
     * JFrame to display the alarm GUI
     */
    private JFrame alarm = null;
    /**
     * AlarmEntity class constructor
     *
     * @param topic Kafka topic subscribed
     */
    public AlarmEntity(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "AlarmEntity");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());

        generatedAlarms = new ConcurrentHashMap<>();
        receivedMessages = new HashSet<>();
        consumer = new KafkaConsumer<>(props);
        this.topic = topic;

        aeGUI = new AlarmGUI();
        gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(450,250);
        gui.setResizable(true);
        gui.add(aeGUI);
        historyButtonListener();
        closeHistoryButtonListener();
        alarmButtonListener();
        closeAlarmButtonListener();
    }

    /**
     * Method to consume messages from the subscribed topic and store them in
     * the referred file
     *
     */
    public void ConsumeAlarm() {
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<String, Message> records = consumer.poll(100);
            for (ConsumerRecord<String, Message> record : records) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_ALARM, true))) {
                    String status = processSpeed(record.value().getCarReg(), Integer.parseInt(record.value().getExtraInfo()));
                    writer.append(String.format("Car registration: %s, Date: %s, Message type: %d, Speed: %s, Status: %s\n",
                            record.value().getCarReg(), new Date(record.value().getTs()), record.value().getType(), record.value().getExtraInfo(), status));

                    aeGUI.setMessageText(String.format("Car registration: %s Date: %s Speed: %s", record.value().getCarReg(), new Date(record.value().getTs()), record.value().getExtraInfo()));
                    aeGUI.setAlarmText(status);
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + PATH_TO_ALARM);
                }
            }
        }
    }

    /**
     * Method to process the speed of a car and generate an alarm if exceeds the
     * maximum allowed speed
     *
     * @param regist Car registration number
     * @param speed Car current speed
     *
     */
    private String processSpeed(String regist, int speed) {
        String status;
        if (speed > MAX_ALLOWED_SPEED) {
            generatedAlarms.put(regist, "ON");
            status = "ON";
        } else {
            if (generatedAlarms.containsKey(regist)) {
                generatedAlarms.remove(regist);
            }
            status = "OFF";
        }
        return status;
    }
    
    /**
     * Method that will create the history panel and set its text
     */
    private void historyText() {
        history = new JFrame();
        history.add(aeGUI.getHistoryPanel());
        history.setVisible(true);
        history.setSize(450,400);
        history.setResizable(true);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(PATH_TO_ALARM));
            String line;
            while ((line = br.readLine()) != null) {
                aeGUI.getHistoryText().append(line+"\n");
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
        JButton historyButton = aeGUI.getHistoryButton();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History button");
            historyText();
        };
        
        historyButton.addActionListener(actionListener);
    }

    /**
     * Method that fills the text area with the current ON alarms
     */
    private void alarmStatusText() {
        alarm = new JFrame();
        alarm.add(aeGUI.getAlarmPanel());
        alarm.setVisible(true);
        alarm.setSize(450,400);
        alarm.setResizable(true);
        
        generatedAlarms.entrySet().forEach((entry) -> {
            aeGUI.getAlarmArea().append(String.format("Car Registration: %s\tStatus:%s", entry.getKey(), entry.getValue()));
        }); 
    }

    /**
     * Listener method for the Alarm Status button on the GUI
     */
    private void alarmButtonListener() {
        JButton alarmButton = aeGUI.getAlarmButton();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println(actionEvent.getActionCommand());
            alarmStatusText();
        };
        alarmButton.addActionListener(actionListener);
    }
    /**
     * Listener method for the close button on the history panel
     */
    private void closeHistoryButtonListener() {
        JButton closeButton = aeGUI.getCloseHistoryButton();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            history.setVisible(false);
        };
        closeButton.addActionListener(actionListener);
    }
    /**
     * Listener method for the close button on the alarm panel
     */
    private void closeAlarmButtonListener() {
        JButton alarmButton = aeGUI.getCloseAlarmButton();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            alarm.setVisible(false);
        };
        alarmButton.addActionListener(actionListener);
    }

    /**
     * Method to run the program, starts the AlarmEntity
     *
     * @param args arguments used when running the program
     */
    public static void main(String[] args) {
        AlarmEntity alarm = new AlarmEntity(KafkaProperties.ALARM_TOPIC);
        alarm.ConsumeAlarm();
    }
}
