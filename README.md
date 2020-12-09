# kafka-examples

## Kafka sender and receiver notes
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

bin\windows\kafka-server-start.bat config\server.properties

kafka-topics.bat --create --topic first-topic --partitions 4 --replication-factor 1 --zookeeper localhost:2181

C:\kafka_2.12-2.5.0\bin\windows>kafka-topics.bat --list --zookeeper localhost:2181
first-topic

C:\kafka_2.12-2.5.0\bin\windows>kafka-topics.bat --describe --topic first-topic --zookeeper localhost:2181


kafka-console-producer.bat --topic first-topic --bootstrap-server localhost:9092



kafka-console-consumer.bat --topic first-topic --bootstrap-server localhost:9092


kafka-console-consumer.bat --topic first-topic --bootstrap-server localhost:9092 --from-beginning


<dependencies>
  <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients -->
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>2.5.0</version>
</dependency>

  </dependencies>


https://drive.google.com/drive/folders/1cZhFXgBj1hmX7CqCt92YB4sB_wxhpM8I?usp=sharing


kafka-console-consumer.bat --topic first-topic --bootstrap-server localhost:9092 --property print.key=true



# DAY 2

package com.jpmc.training.kafkasender;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.jpmc.training.partitioner.MessagePartitioner;


class MyCallback implements Callback{

	@Override
	public void onCompletion(RecordMetadata rmd, Exception e) {
		// TODO Auto-generated method stub
		if(e==null) {
			System.out.println("message delivered to offset "+rmd.offset()+" at partition "+rmd.partition());
		}
	}

}
public class SenderWithCallback {
public static void main(String[] args) {
Properties props=new Properties();
props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, MessagePartitioner.class.getName());

		MyCallback callback=new MyCallback();
		KafkaProducer<String, String> producer=new KafkaProducer<>(props);
		for(int i=1;i<=10;i++) {
			ProducerRecord<String, String> record=new ProducerRecord<String, String>("first-topic", "test-message-1",
					"This is a test message "+i);
			
			producer.send(record,callback);
		}
		
		for(int i=11;i<=20;i++) {
			ProducerRecord<String, String> record=new ProducerRecord<String, String>("first-topic", "test-message-2",
					"This is a test message "+i);
			
			producer.send(record,callback);
		}
		for(int i=21;i<=30;i++) {
			ProducerRecord<String, String> record=new ProducerRecord<String, String>("first-topic", "test-message-3",
					"This is a test message "+i);
			
			producer.send(record,callback);
		}
		for(int i=31;i<=40;i++) {
			ProducerRecord<String, String> record=new ProducerRecord<String, String>("first-topic", "test-message-4",
					"This is a test message "+i);
			
			producer.send(record,callback);
		}
		for(int i=41;i<=50;i++) {
			ProducerRecord<String, String> record=new ProducerRecord<String, String>("first-topic", "test-message-5",
					"This is a test message "+i);
			
			producer.send(record,callback);
		}
		System.out.println("messages sent");
		producer.close();
	}

}



kafka-console-consumer.bat --topic first-topic --bootstrap-server localhost:9092 --property print.key=true





package com.jpmc.training.kafkareceiver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;


public class ReceiverWithOffsetInfo {
public static void main(String[] args) {
Properties props=new Properties();
props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "group-1");

		KafkaConsumer<String, String> consumer=new KafkaConsumer<>(props);
		
		List<String> topics=new ArrayList<>();
		topics.add("first-topic");
		
		consumer.subscribe(topics);
		System.out.println("waiting for messages");
		while(true) {
			ConsumerRecords<String, String> records=consumer.poll(Duration.ofSeconds(10));
			records.forEach(record->System.out.println("key: "+record.key()+" value: "+record.value()+" from partition "+record.partition()
			+" at offset "+record.offset()));
		}
	}

}











package com.jpmc.training.kafkareceiver;

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


package com.jpmc.training.kafkareceiver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


public class ReceiverFromSpecificPartitionAndOffset {
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
		int offset=Integer.parseInt(args[1]);
		
		consumer.assign(partitions);
		consumer.seek(partition, offset);
		System.out.println("waiting for messages from  "+partitionNumber +" at offset "+ offset);
		while(true) {
			ConsumerRecords<String, String> records=consumer.poll(Duration.ofSeconds(10));
			records.forEach(record->System.out.println("key: "+record.key()+" value: "+record.value()+" from partition "+record.partition()));
		}
	}

}






package com.jpmc.training.kafkareceiver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;


public class ReceiverFromSpecificPartitionAndOffset {
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
		int offset=Integer.parseInt(args[1]);
		
		consumer.assign(partitions);
		consumer.seek(partition, offset);
		System.out.println("waiting for messages from  "+partitionNumber +" at offset "+ offset);
		while(true) {
			ConsumerRecords<String, String> records=consumer.poll(Duration.ofSeconds(10));
			records.forEach(record->System.out.println("key: "+record.key()+" value: "+record.value()+" from partition "+record.partition() +" at offset "+record.offset()));
		}
	}

}





package com.jpmc.training.domain;

public class Employee {
private int empId;
private String name;
private String designation;
public int getEmpId() {
return empId;
}
public void setEmpId(int empId) {
this.empId = empId;
}
public String getName() {
return name;
}
public void setName(String name) {
this.name = name;
}
public String getDesignation() {
return designation;
}
public void setDesignation(String designation) {
this.designation = designation;
}
public Employee(int empId, String name, String designation) {
super();
this.empId = empId;
this.name = name;
this.designation = designation;
}
public Employee() {
super();
// TODO Auto-generated constructor stub
}




}


 <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.0</version>
</dependency>



package com.jpmc.training.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.training.domain.Employee;

public class EmployeeSerializer implements Serializer<Employee>{
private ObjectMapper mapper=new ObjectMapper();
@Override
public byte[] serialize(String topic, Employee e) {
// TODO Auto-generated method stub
byte[] array=null;
try {
array=mapper.writeValueAsBytes(e);
} catch (JsonProcessingException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
return array;
}

}

package com.jpmc.training.kafkasender;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import com.jpmc.training.domain.Employee;
import com.jpmc.training.serializer.EmployeeSerializer;

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
				new Employee(1002, "Deepak Sharma", "Account"));
		producer.send(emp1);
		producer.send(emp2);
		
		producer.close();
		
		System.out.println("employee objects sent");
	}

}



kafka-topics.bat --create --topic emp-topic --partitions 4 --replication-factor 1 --zookeeper localhost:2181



kafka-console-consumer.bat --topic emp-topic --bootstrap-server localhost:9092 --property print.key=true


package com.jpmc.training.deserializer;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.training.domain.Employee;

public class EmployeeDeserializer implements Deserializer<Employee> {
private ObjectMapper mapper=new ObjectMapper();
@Override
public Employee deserialize(String topic, byte[] data) {
// TODO Auto-generated method stub
Employee employee=null;

		System.out.println("deserializing "+new String(data));
		try {
			employee=mapper.readValue(data, Employee.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;
	}

}








