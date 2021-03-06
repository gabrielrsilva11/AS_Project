package CollectEntity;

import GUI.CollectGUI;
import config.KafkaProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import util.Message;
import util.MessageSerializer;
import static config.KafkaProperties.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import static util.Constants.PATH_TO_DATA;

/**
 * CollectEntity - Kafka producer entity to produce and send messages to the
 * corresponding topics
 *
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class CollectEntity {

    /**
     * Kafka producer that don't lose messages
     */
    private final KafkaProducer<String, Message> producerACK;
    /**
     * Kafka producer that can lose messages
     */
    private final KafkaProducer<String, Message> producer;
    /**
     * Reader to read information from file
     */
    private BufferedReader br;
    /**
     * Kafka topic subscribed
     */
    private String topic;
    /**
     * Variable to store the GUI
     */
    private CollectGUI cGUI;
    /**
     * JFrame to display the main GUI
     */
    private JFrame coll_frame;
    /**
     * JFrame to display the history GUI
     */
    private JFrame history;
    /**
     * Counts the number of rows in a file
     */
    int lineCount;
    /**
     * Generate random values
     */
    private Random random;

    /**
     * CollectEntity class constructor
     *
     */
    public CollectEntity() {
        random = new Random();
        lineCount = 0;
        producer = createProducer(false);
        producerACK = createProducer(true);
        try {
            br = new BufferedReader(new FileReader(new File(PATH_TO_DATA)));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        cGUI = new CollectGUI();
        coll_frame = new JFrame();
        coll_frame.setVisible(true);
        coll_frame.setSize(450, 250);
        coll_frame.setResizable(true);
        coll_frame.add(cGUI);
        historyButtonListener();
        closeHistoryButtonListener();
        sendButtonListener();
        reorderedButtonListener();
    }

    /**
     * Creates two producers, one that don't lose any message, and another that
     * can lost messages
     *
     * @return producer created
     */
    public KafkaProducer createProducer(boolean ackProducer) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "CollectEntity");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());

        if (ackProducer) {
            props.put(ProducerConfig.ACKS_CONFIG, KafkaProperties.ACKS_ALL);
        } else {
            props.put(ProducerConfig.ACKS_CONFIG, KafkaProperties.NO_ACKS);
        }

        return new KafkaProducer<>(props);
    }

    /**
     * Method to send all messages to the corresponding destination Kafka topic
     *
     */
    public void sendRecords() {
        Message toSend = null;
        while ((toSend = getRecord()) != null) {
            try {
                System.out.println("Sending message");
                RecordMetadata metadata = null;
                metadata = producer.send(new ProducerRecord<>(BATCH_TOPIC, "message", toSend)).get();
                switch (toSend.getType()) {
                    case 1:
                        metadata = producer.send(new ProducerRecord<>(ALARM_TOPIC, "message", toSend)).get();
                        break;
                    case 2:
                        metadata = producerACK.send(new ProducerRecord<>(REPORT_TOPIC, "message", toSend)).get();
                        break;
                }
                cGUI.getMessageText().setText(String.format("Car registration: %s, Date: %s, Message type: %d\n",
                        toSend.getCarReg(), new Date(toSend.getTs()), toSend.getType()));
                cGUI.getMessageText().paintImmediately(cGUI.getMessageText().getVisibleRect());
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println("Error sending record" + ex);
            }
        }
        producerACK.flush();
        producer.flush();
        producerACK.flush();
        System.out.println("Producer completed");
    }

    /**
     * Method to get a message from the referred file
     *
     * @return message to be sent
     */
    private Message getRecord() {
        Message toSend = null;
        String line;
        String[] fields;
        try {
            line = br.readLine();
            if (line != null) {
                lineCount++;
                fields = line.split(" ");
                if (Integer.parseInt(fields[5]) == 0) {
                    toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), null);
                } else {
                    toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), fields[7]);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading line from file");
        }
        return toSend;
    }

    /**
     * Method to get a random message from the referred file
     *
     * @return message to be sent
     */
    private void reorderMessage() {

        if (lineCount <= 0) {
            return;
        }

        int lineNumber;
        String line;
        Message toSend = null;
        do {
            lineNumber = random.nextInt(lineCount) + 1;
            String[] fields;
            try (Stream<String> lines = Files.lines(Paths.get(PATH_TO_DATA))) {
                line = lines.skip(lineNumber - 1).findFirst().get();
                if (line != null) {
                    fields = line.split(" ");
                    if (Integer.parseInt(fields[5]) == 0) {
                        toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), null);
                    } else {
                        toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), fields[7]);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error reading line from file");
            }
        } while (toSend == null);
        try {
            RecordMetadata metadata = producer.send(new ProducerRecord<>(BATCH_TOPIC, "message", toSend)).get();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Error sending record" + ex);
        }
        System.out.println("Producer completed");
    }

    /**
     * Method to close producers
     *
     */
    public void closeProducer() {
        producerACK.flush();
        producer.flush();
        producerACK.flush();
        producer.close();
    }

    /**
     * Method that will create the history panel and set its text
     */
    private void historyText() {
        history = new JFrame();
        history.add(cGUI.getHistoryPanel());
        history.setVisible(true);
        history.setSize(450, 400);
        history.setResizable(true);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(PATH_TO_DATA));
            String line;
            while ((line = br.readLine()) != null) {
                cGUI.getHistoryText().append(line + "\n");
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
     * Listener method for the Send All button
     */
    private void sendButtonListener() {
        JButton sendButton = cGUI.getSendButton();
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("Send");
            sendRecords();
        };
        sendButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the history button
     */
    private void historyButtonListener() {
        JButton historyButton = cGUI.getHistoryButton();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            System.out.println("History button");
            historyText();
        };
        historyButton.addActionListener(actionListener);
    }

    /**
     * Listener method for the close button inside the history panel
     */
    private void closeHistoryButtonListener() {
        JButton closeButton = cGUI.getCloseHistoryButton();

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            history.setVisible(false);
        };
        closeButton.addActionListener(actionListener);
    }
    /**
     * Listener method for the send reordered button
     */
    private void reorderedButtonListener(){
        JButton reorderedButton = cGUI.getReorderedButton();
        
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            reorderMessage();
        };
        reorderedButton.addActionListener(actionListener);
    }
    /**
     * Method to run the program, starts the BatchEntity
     *
     * @param args arguments used when running the program
     */
    public static void main(String[] args) {
        CollectEntity collect = new CollectEntity();
    }
}
