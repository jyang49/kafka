package my.kafka1;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoLoop {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(ProducerDemoLoop.class);
		// Create Producer properties
		Properties prop = new Properties();
		
		
		String topic = null;
		String bootstrapServer = null;
		int loop = 0;
		String message = null;

		
		if (args.length == 4) {
			topic = args[0];
			bootstrapServer = args[1];
			loop = Integer.parseInt(args[2]);
			message = args[3];
		}	
		else {
			System.out.println("------------------------------------------------------------------");
			System.out.println("need 4 arguments: topic_name bootstrapServer loop message");
			System.out.println("example: topic1 192.168.56.35 10 study");
			System.out.println("------------------------------------------------------------------");
			System.exit(1);
		}

		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer + ":9092");
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
				
		for (int i = 0; i < loop; i++) {
			String value = message + "-" + Integer.toString(i);
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);
			
			// Send data - asynchronous
			producer.send(record, new Callback() {
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// executes every time a record is successfully sent or an exception is thrown
					if (exception == null) {
						logger.info("Received new metadata. \n" +
						"Topic: " + metadata.topic() + "\n" + 
						"Partition: " + metadata.partition() + "\n" +
						"Offset: " + metadata.offset() + "\n" +
						"Timestamp: " + metadata.timestamp());
					} else {
						logger.error("Error while producing", exception);
					}
					
				}
				
			});
		}
		
		// flush data
		producer.flush();
		producer.close();
		
	}

}
