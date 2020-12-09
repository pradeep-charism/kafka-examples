package main.java.sender;

import main.java.partitioner.MessagePartitioner;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleSenderWithCustomParitioner {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, MessagePartitioner.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        sendMessageFor(producer, "KEY-1");
        sendMessageFor(producer, "KEY-2");
        sendMessageFor(producer, "KEY-3");

        producer.close();
    }

    private static void sendMessageFor(KafkaProducer<String, String> producer, String key) {
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("first-topic", key,
                    "this is a test message for: " + i);
            producer.send(record);
        }
    }
}
