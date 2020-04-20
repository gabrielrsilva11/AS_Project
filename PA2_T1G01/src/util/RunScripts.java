package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* RunScripts - Class to execute sripts
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class RunScripts {
    
    /**
     * Method to start Zookeeper
     * 
     */
    public static void runZookeeper() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/zookeeper-server-start.sh", "src/scripts/config/zookeeper.properties");
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Method to stop Zookeeper
     * 
     */
    public static void stopZookeeper() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/zookeeper-server-stop.sh");
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to start Kafka server
     * 
     */
    public static void runKafkaServer() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/kafka-server-start.sh", "src/scripts/config/server.properties");
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to stop Kafka server
     * 
     */
    public static void stopKafkaServer() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/kafka-server-stop.sh");
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to create one Kafka topic
     * 
     */
    public static void createKafkaTopic(String topic) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/kafka-topics.sh", "--create", "--zookeeper", "localhost:2181", "--replication-factor 1", "--partitions 1", "--topic " + topic);
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method to list all topics and create the one we need if missing
     * 
     * @return list with all the topics that exist
     */
    public static List<String> listTopics() {
        List<String> output = new ArrayList<>();
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/kafka-topics.sh", "--list", "--zookeeper", "localhost:2181");
        try {

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
    
    /**
     * Method to delete all Kafka topics
     * 
     */
    public static void deleteKafkaTopics() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("src/scripts/bin/kafka-topics.sh", "--zookeeper", "localhost:2181", "--delete", "--topic", "'.*'");
        try {
            Process process = processBuilder.start();
        } catch (IOException ex) {
            Logger.getLogger(RunScripts.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
