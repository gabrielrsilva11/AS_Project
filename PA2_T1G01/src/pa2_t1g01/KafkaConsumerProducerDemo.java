package pa2_t1g01;

import producer.Producer;
import consumer.Consumer;
import config.KafkaProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KafkaConsumerProducerDemo {
    public static void main(String[] args) {
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command("bash", "-c", "ls /Users/manuelcura/Documents/UA/Mestrado/1_ano/2_semestre/AS/pratica/AS_Project/PA2_T1G01/src/jars");
//	try {
//
//		Process process = processBuilder.start();
//
//		StringBuilder output = new StringBuilder();
//
//		BufferedReader reader = new BufferedReader(
//				new InputStreamReader(process.getInputStream()));
//
//		String line;
//		while ((line = reader.readLine()) != null) {
//			output.append(line + "\n");
//		}
//
//		int exitVal = process.waitFor();
//		if (exitVal == 0) {
//			System.out.println("Success!");
//			System.out.println(output);
//			System.exit(0);
//		} else {
//			//abnormal...
//		}
//
//	} catch (IOException e) {
//		e.printStackTrace();
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync);
        producerThread.start();

        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}
