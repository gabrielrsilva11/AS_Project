package entities;

import config.KafkaProperties;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
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
     * ReportEntity class constructor
     * 
     * @param topic Kafka topic subscribed
     */
    public ReportEntity(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
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
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + PATH_TO_REPORT);
                }
            }
        }
    }

    /**
     * Method to run the program, starts the BatchEntity
     * @param args arguments used when running the program
     */
    public static void main(String[] args) {
        ReportEntity report = new ReportEntity(KafkaProperties.REPORT_TOPIC);
        report.ConsumeReport();
    }
}
