package pa2_t1g01;

import util.RunScripts;

public class StopKafkaServer {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RunScripts.stopKafkaServer();
        RunScripts.stopZookeeper();
    }  
}
