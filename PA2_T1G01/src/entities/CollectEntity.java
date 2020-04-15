/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import config.KafkaProperties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import util.Message;
import util.MessageSerializer;

/**
 *
 * @author manuelcura
 */
public class CollectEntity {

    private final KafkaProducer<String, Message> producer;
    BufferedReader br;
    String topic;
    
    public CollectEntity(String topic) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "DemoProducer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());

        producer = new KafkaProducer<String, Message>(props);

        try {
            br = new BufferedReader(new FileReader(new File("src/data/CAR.txt")));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        this.topic = topic;
    }

    private Message getRecord() {
        Message toSend = null;
        String line;
        String[] fields;
        try {
            if (br.readLine() != null) {
                line = br.readLine();
                fields = line.split(" ");
                System.out.println(fields);
                
                if(fields.length == 7){
                    toSend = new Message(fields[1], Integer.parseInt(fields[3]), Integer.parseInt(fields[5]));
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

    public void sendRecords(){
        int messageNum = 1;
        Message toSend = null;
        while((toSend = getRecord()) != null){
            try {
                producer.send(new ProducerRecord<String, Message>(topic, "test", toSend)).get();
            } catch (Exception ex) {
                System.out.println("Error sending record" + ex);
            }
        }
        System.out.println("Producer completed");
        producer.close();
    }
    
    public static void main(String[] args){
        CollectEntity collect = new CollectEntity(KafkaProperties.TOPIC);
        collect.sendRecords();
    }
}
