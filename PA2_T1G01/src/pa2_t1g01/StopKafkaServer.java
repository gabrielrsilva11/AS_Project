package pa2_t1g01;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.RunScripts;

public class StopKafkaServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Thread.sleep of 1 second to give time to Zookeper and Kafka to stop
        try {
            RunScripts.deleteKafkaTopics();
            Thread.sleep(1000);
            RunScripts.stopKafkaServer();
            Thread.sleep(1000);
            RunScripts.stopZookeeper();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StopKafkaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
