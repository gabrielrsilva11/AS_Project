/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;


/**
 *
 * @author manuelcura
 */
public class MessageSerializer implements Serializer<Message> {

    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //nothing to do
    }

    @Override
    public byte[] serialize(String topic, Message data) {
        byte[] buffer = null;

        ObjectMapper objectMapper = new ObjectMapper();

        try {

            buffer = objectMapper.writeValueAsString(data).getBytes();

        } catch (Exception e) {

            System.out.println("Error serializing object" + e);;

        }

        return buffer;
    }

    
}
