package pa2_t1g01;

import util.RunScripts;

public class StartKafkaServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RunScripts.runZookeeper();
        RunScripts.runKafkaServer();
        RunScripts.listTopics();  
    }
}
