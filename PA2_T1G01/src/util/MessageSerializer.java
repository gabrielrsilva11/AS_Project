/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
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
        int sizeOfReg, sizeOfExtra;
        byte[] serializedReg, serializedExtra;
        ByteBuffer buf = null;

        if (data == null) {
            return null;
        }

        try {
            serializedReg = data.getCarReg().getBytes(encoding);
            sizeOfReg = serializedReg.length;
            serializedExtra = data.getExtraInfo().getBytes(encoding);
            sizeOfExtra = serializedExtra.length;
            
            if (data.getExtraInfo() != null) {
                //Alarm / Report type message
                buf = ByteBuffer.allocate(sizeOfReg + 4 + 4 + sizeOfExtra);
            } else {
                //Batch type message
                buf = ByteBuffer.allocate(sizeOfReg + 4 + 4);
            }

            buf.put(serializedReg);
            buf.putInt(data.getTs());
            buf.putInt(data.getType());

            if (data.getExtraInfo() != null) {
                buf.put(serializedExtra);
            }

        } catch (UnsupportedEncodingException ex) {
            System.out.println("Error serializing message.");
        }
        
        return buf.array ();
    }

    
}
