package util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;


/**
* MessageDeserializer - Class to perform message deserialization
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class MessageDeserializer implements Deserializer<Message>{
    @Override
    public void configure(Map<String, ?> configs, boolean isKey){ 
        //nothing to do
    }
    
    /**
     * Method to deserialize a message
     * 
     * @param topic kafka topic
     * @param data message in bytes
     * @return information as a Message object
     */
    @Override
    public Message deserialize(String topic, byte[] data){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        Message message = null;
        try {
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
