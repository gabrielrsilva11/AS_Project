package config;

public class KafkaProperties {
    public static final String TOPIC = "topic1";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";
    public static final String OFFSET_RESET_LATEST = "latest";
    public static final String OFFSET_RESET_EARLIER = "earliest";
    public static final Integer MESSAGE_COUNT = 1000;
    public static final Integer MAX_POLL_RECORDS = 1;        
    public static final Integer MAX_NO_MESSGE_FOUND_COUNT = 100;
    
    private KafkaProperties() {}
}
