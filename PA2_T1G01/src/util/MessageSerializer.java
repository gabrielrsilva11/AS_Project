package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;


/**
* MessageSerializer - Class to perform message serialization
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class MessageSerializer implements Serializer<Message> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        //nothing to do
    }

    /**
     * Method to serialize a message
     * 
     * @param topic kafka topic
     * @param data message
     * @return message information in bytes
     */
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
