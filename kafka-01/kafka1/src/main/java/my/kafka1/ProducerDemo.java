package my.kafka1;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {

	public static void main(String[] args) {
		
		// Create Producer properties
		Properties prop = new Properties();
		String bootstrapServer = "192.168.56.35:9092";
		String topic = "topic1";
		
		String defaultMessage = "study";
		String value;
		
		if (args.length > 0)
			value = args[0];
		else
			value = defaultMessage;

		prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		// Create the producer
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(prop);
		
		// Create a producer record
		ProducerRecord record = new ProducerRecord(topic, value);
		
		// Send data - asynchronous
		producer.send(record);
		
		// flush data
		producer.flush();
		
	}

}
