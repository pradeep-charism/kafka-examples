package main.java.kafkasender;

import main.java.domain.Employee;
import main.java.serializer.EmployeeSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class EmployeeSender {
	
	public static void main(String[] args) {
		Properties props=new Properties();
		props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EmployeeSerializer.class.getName());
		
		KafkaProducer<String, Employee> producer=new KafkaProducer<>(props);
		ProducerRecord<String, Employee> emp1=new ProducerRecord<String, Employee>("emp-topic", "emp-1",
				new Employee(1001, "Rajiv Reddy", "Developer"));
		ProducerRecord<String, Employee> emp2=new ProducerRecord<String, Employee>("emp-topic", "emp-2",
				new Employee(1002, "Deepak Sharma", "Accountant"));
		producer.send(emp1);
		producer.send(emp2);
		
		producer.close();
		
		System.out.println("employee objects sent");
	}

}
