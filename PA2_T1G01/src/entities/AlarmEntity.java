package entities;

import config.KafkaProperties;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
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

    Set<Integer> recievedMessages;

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
        recievedMessages = new HashSet<>();
        consumer = new KafkaConsumer<>(props);
        this.topic = topic;
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
                                            System.out.println("fora");

                    // Avoid process messages already processed
                    if (!recievedMessages.contains(record.value().getTs())) {
                        System.out.println("ENTROU");
                        String status = processSpeed(record.value().getCarReg(), Integer.parseInt(record.value().getExtraInfo()));
                        writer.append(String.format("Car registration: %s, Date: %s, Message type: %d, Speed: %s, Status: %s\n",
                                record.value().getCarReg(), new Date(record.value().getTs()), record.value().getType(), record.value().getExtraInfo(), status));
                    }
                    recievedMessages.add(record.value().getTs());
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
     * Method to run the program, starts the AlarmEntity
     *
     * @param args arguments used when running the program
     */
    public static void main(String[] args) {
        AlarmEntity alarm = new AlarmEntity(KafkaProperties.ALARM_TOPIC);
        alarm.ConsumeAlarm();
    }
}
