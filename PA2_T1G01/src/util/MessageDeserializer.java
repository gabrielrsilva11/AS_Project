/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.kafka.common.serialization.Deserializer;


/**
 *
 * @author gabri
 */
public class MessageDeserializer implements Deserializer<Message>{
    @Override
    public void configure(Map<String, ?> configs, boolean isKey){ 
    }
    
    @Override
    public Message deserialize(String topic, byte[] data){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        Message message = null;
        try {
            System.out.println(data);
            message = mapper.readValue(data, Message.class);
        } catch (Exception ex) {
            System.out.println("Error deserializing " + ex);
        }
        return message;
    }
    
    @Override
    public void close(){
    }
}
