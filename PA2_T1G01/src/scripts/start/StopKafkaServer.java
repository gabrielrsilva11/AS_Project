package scripts.start;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.RunScripts;
/**
 * StopKafkaServer - Runs scripts to stop kafka / zookeeper as well as
 * deleting the topics created.
 * 
 * @author Gabriel Silva
 * @author Manuel Marcos
 *
 */
public class StopKafkaServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Thread.sleep of 1 second to give time to Zookeper and Kafka to stop
        try {
            RunScripts run = new RunScripts();
            run.deleteKafkaTopics();
            Thread.sleep(1000);
            run.stopKafkaServer();
            Thread.sleep(1000);
            run.stopZookeeper();
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StopKafkaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
