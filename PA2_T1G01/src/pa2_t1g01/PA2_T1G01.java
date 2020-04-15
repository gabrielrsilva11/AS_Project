/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pa2_t1g01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import util.Message;
import util.MessageDeserializer;
import util.MessageSerializer;

public class PA2_T1G01 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File("src/data/CAR.txt")));
        Message message, message2;
        MessageSerializer huh = new MessageSerializer();
        MessageDeserializer ah = new MessageDeserializer();
        byte[] buf;
        int i =0;
        while((message2 = getRecord(br)) != null){
            buf = huh.serialize("topic1", message2);
            message = ah.deserialize("topic1", buf);
            System.out.println("Message nr: " + i);
            System.out.println(message.getCarReg());
            System.out.println(message.getTs());
            System.out.println(message.getType());
            if(message.getExtraInfo() != null)
                System.out.println(message.getExtraInfo());
            i++;
            
        } 
    } 
    
    public static Message getRecord(BufferedReader br) throws FileNotFoundException {
        Message toSend = null;
        String line;
        String[] fields;
        try {
            if (br.readLine() != null) {
                line = br.readLine();
                fields = line.split(" ");
                
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
    
}
