package pa2_t1g01;

import java.util.ArrayList;
import java.util.List;
import util.RunScripts;

import static config.KafkaProperties.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartKafkaServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Thread.sleep of 1 second to give time to Zookeper and Kafka to start
        try {
            List<String> topics = new ArrayList<>();
            topics.add(BATCH_TOPIC);
            topics.add(REPORT_TOPIC);
            topics.add(ALARM_TOPIC);
            RunScripts run = new RunScripts();
            run.runZookeeper();
            Thread.sleep(1000);
            run.runKafkaServer();
            Thread.sleep(1000);
            List<String> outputTopics = run.listTopics();
            // Create topics
            topics.stream().filter((topic) -> (!outputTopics.contains(topic))).forEachOrdered((topic) -> {
                run.createKafkaTopic(topic);
            });
        } catch (InterruptedException ex) {
            Logger.getLogger(StartKafkaServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
