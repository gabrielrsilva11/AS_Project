package entities;

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
import static util.Constants.PATH_TO_DATA;


/**
* CollectEntity - Kafka producer entity to produce and send messages to the corresponding topics
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class CollectEntity {

    /**
    * Kafka producer to produce messages
    */
    private final KafkaProducer<String, Message> producer;
    /**
    * Reader to read information from file
    */
    BufferedReader br;
    /**
    * Kafka topic subscribed
    */
    String topic;
    
    /**
     * CollectEntity class constructor
     * 
     */
    public CollectEntity() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "CollectEntity");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());

        producer = new KafkaProducer<>(props);
        try {
            br = new BufferedReader(new FileReader(new File(PATH_TO_DATA)));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    /**
     * Method send a message to the corresponding destination Kafka topic
     * 
     */
    public void sendRecords(){
        int messageNum = 1;
        Message toSend = null;
        while((toSend = getRecord()) != null){
            try {
                switch(toSend.getType()) {
                    case 0:    
                        producer.send(new ProducerRecord<>(BATCH_TOPIC, "message", toSend)).get();
                        break;
                    case 1:
                        producer.send(new ProducerRecord<>(ALARM_TOPIC, "message", toSend)).get();
                        break;
                    case 2:
                        System.out.println("Report");
                        producer.send(new ProducerRecord<>(REPORT_TOPIC, "message", toSend)).get();
                        break;
                }
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println("Error sending record" + ex);
            }
        }
        System.out.println("Producer completed");
        producer.close();
    }
    
    /**
     * Method to get a message from the referred file
     * @return message to be sent 
     */
    private Message getRecord() {
        Message toSend = null;
        String line;
        String[] fields;
        try {
            line = br.readLine();
            if (line != null) {
                fields = line.split(" ");         
                if(fields.length == 7){
                    toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), null);
                }
                else{
                    toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]), fields[7]);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading line from file");
        }
        return toSend;
    }
    
    /**
     * Method to run the program, starts the BatchEntity
     * @param args arguments used when running the program
     */
    public static void main(String[] args){
        CollectEntity collect = new CollectEntity();
        collect.sendRecords();
    }
}
