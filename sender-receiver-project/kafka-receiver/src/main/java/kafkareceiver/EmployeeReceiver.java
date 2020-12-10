package main.java.kafkareceiver;

import main.java.deserializer.EmployeeDeserializer;
import main.java.domain.Employee;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class EmployeeReceiver {
	public static void main(String[] args) {
		Properties props=new Properties();
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EmployeeDeserializer.class.getName());
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-1");
		
		KafkaConsumer<String, Employee> consumer=new KafkaConsumer<>(props);
		
		List<String> topics=new ArrayList<>();
		topics.add("emp-topic");
		
		consumer.subscribe(topics);
		System.out.println("waiting for messages");
		while(true) {
			ConsumerRecords<String, Employee> records=consumer.poll(Duration.ofSeconds(10));
			records.forEach(record->
			{
				System.out.println("key: "+record.key());
				System.out.println("value ");
				Employee employee=record.value();
				System.out.println("Id: "+employee.getEmpId());
				System.out.println("Name: "+employee.getName());
				System.out.println("Designation: "+employee.getDesignation());
			});
		}
	}

}
