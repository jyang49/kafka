package my.kafka1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoKeys {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);
		// Create Producer properties
		Properties prop = new Properties();
		String bootstrapServer = "192.168.56.35:9092";
		String topic = "topic1";

		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
		
		// Create a producer record
		
		for (int i = 0; i < 10; i++) {
			String key = "key_" + Integer.toString(1);
			String value = "Learning Kafka " + Integer.toString(i);
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, value);
			
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
