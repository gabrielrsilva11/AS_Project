/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 *
 * @author manuelcura
 */
public class AlarmEntity {
    String message;
    boolean alarm;
    
    public void consume(){
        //fazer as properties aqui que estas estao mal
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093");
        props.put("group.id", 2);
        props.put("group.deserializer", "org.apache.kafka.common.serializationDeserializer");
        props.put("value.deserializer", "AlarmDeserializer");
        
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("AlarmTopic"));
        
        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for(ConsumerRecord<String, String> record:records){
                System.out.println("Consumido");
            }
        }
        
    }
}
