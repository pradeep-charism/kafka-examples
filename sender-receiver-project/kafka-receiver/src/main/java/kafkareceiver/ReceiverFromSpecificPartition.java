package main.java.kafkareceiver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


public class ReceiverFromSpecificPartition {
	public static void main(String[] args) {
		Properties props=new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		
		KafkaConsumer<String, String> consumer=new KafkaConsumer<>(props);
		
		List<TopicPartition> partitions=new ArrayList<>();
		int partitionNumber=Integer.parseInt(args[0]);
		TopicPartition partition=new TopicPartition("first-topic", partitionNumber);
		partitions.add(partition);
		
		consumer.assign(partitions);
		System.out.println("waiting for messages "+partitionNumber);
		while(true) {
			ConsumerRecords<String, String> records=consumer.poll(Duration.ofSeconds(10));
			records.forEach(record->System.out.println("key: "+record.key()+" value: "+record.value()+" from partition "+record.partition()));
		}
	}

}
