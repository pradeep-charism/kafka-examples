package main.java.kafkasender;

import main.java.domain.Employee;
import main.java.partitioner.EmployeePartitioner;
import main.java.serializer.EmployeeSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;


public class EmployeeSenderWithCustomPartitioner {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EmployeeSerializer.class.getName());
        props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, EmployeePartitioner.class.getName());
        KafkaProducer<String, Employee> producer = new KafkaProducer<>(props);
        for (int i = 1001; i <= 1010; i++) {
            ProducerRecord<String, Employee> emp = new ProducerRecord<String, Employee>("emp-topic",
                    new Employee(i, "Name " + i, "Developer"));
            producer.send(emp);
        }

        for (int i = 1011; i <= 1020; i++) {
            ProducerRecord<String, Employee> emp = new ProducerRecord<String, Employee>("emp-topic",
                    new Employee(i, "Name " + i, "Accountant"));
            producer.send(emp);
        }
        for (int i = 1021; i <= 1030; i++) {
            ProducerRecord<String, Employee> emp = new ProducerRecord<String, Employee>("emp-topic",
                    new Employee(i, "Name " + i, "Architect"));
            producer.send(emp);
        }

        for (int i = 1031; i <= 1040; i++) {
            ProducerRecord<String, Employee> emp = new ProducerRecord<String, Employee>("emp-topic",
                    new Employee(i, "Name " + i, "IT Admin"));
            producer.send(emp);
        }
        for (int i = 1041; i <= 1050; i++) {
            ProducerRecord<String, Employee> emp = new ProducerRecord<String, Employee>("emp-topic",
                    new Employee(i, "Name " + i, "Consultant"));
            producer.send(emp);
        }
        producer.close();

        System.out.println("employee objects sent");
    }

}
