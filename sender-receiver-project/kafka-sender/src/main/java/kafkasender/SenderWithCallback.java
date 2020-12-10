package main.java.kafkasender;

import main.java.partitioner.MessagePartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

class MyCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata rmd, Exception e) {
        // TODO Auto-generated method stub
        if (e == null) {
            System.out.println("message delivered to offset " + rmd.offset() + " at partition " + rmd.partition());
        }
    }

}

public class SenderWithCallback {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, MessagePartitioner.class.getName());

        MyCallback callback = new MyCallback();
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 1; i <= 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "test-message-1",
                    "This is a test message " + i);

            producer.send(record, callback);
        }

        for (int i = 11; i <= 20; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "test-message-2",
                    "This is a test message " + i);

            producer.send(record, callback);
        }
        for (int i = 21; i <= 30; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "test-message-3",
                    "This is a test message " + i);

            producer.send(record, callback);
        }
        for (int i = 31; i <= 40; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "test-message-4",
                    "This is a test message " + i);

            producer.send(record, callback);
        }
        for (int i = 41; i <= 50; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("first-topic", "test-message-5",
                    "This is a test message " + i);

            producer.send(record, callback);
        }
        System.out.println("messages sent");
        producer.close();
    }

}
