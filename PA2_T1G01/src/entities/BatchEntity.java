/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import config.KafkaProperties;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import util.Message;
import util.MessageDeserializer;

/**
 *
 * @author manuelcura
 */
public class BatchEntity {
    Consumer<String, Message> consumer;
    String topic;
    
    public BatchEntity(String topic){
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_SERVER_URL + ":" + KafkaProperties.KAFKA_SERVER_PORT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "DemoConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class.getName());
        
        consumer = new KafkaConsumer<String, Message>(props);
        this.topic = topic;
    }
    
    public void ConsumeBatch(){
        consumer.subscribe(Arrays.asList(topic));
        while(true){
            ConsumerRecords<String, Message> records = consumer.poll(100);
            for(ConsumerRecord<String, Message> record : records){
                System.out.println("Supplier id= " + String.valueOf(record.value().getCarReg()));
            }
        }
    }
    
    public static void main(String[] args){
        BatchEntity batch = new BatchEntity(KafkaProperties.TOPIC);
        batch.ConsumeBatch();
    }
}
