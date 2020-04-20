package config;

/**
* KafkaProperties - Class that contains constants used to configure Kafka producer and consumer
* 
* @author Gabriel Silva
* @author Manuel Marcos
* 
*/
public class KafkaProperties {
    public static final String BATCH_TOPIC = "BatchTopic";
    public static final String REPORT_TOPIC = "ReportTopic";
    public static final String ALARM_TOPIC = "AlarmTopic";
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String OFFSET_RESET_LATEST = "latest";
    public static final String OFFSET_RESET_EARLIER = "earliest";
    public static final Integer MESSAGE_COUNT = 1000;
    public static final Integer MAX_POLL_RECORDS = 1;        
    public static final Integer MAX_NO_MESSGE_FOUND_COUNT = 100;
    public static final String ACKS_ALL = "all";
    public static final String NO_ACKS = "0";
}
