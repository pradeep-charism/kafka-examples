package main.java.sender;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

class MyCallback implements Callback {

    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        System.out.println("Callback is invoked");
        if (e == null) {
            System.out.println("Message is delived to offset'" + recordMetadata.offset() + "'at partition: '" + recordMetadata.partition() + "'");
        }
    }
}

public class SimpleSenderWithCallback {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, MessagePartitioner.class.getName());

        MyCallback callback = new MyCallback();

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        sendMessageFor(producer, "KEY-1", callback);
        sendMessageFor(producer, "KEY-2", callback);
        sendMessageFor(producer, "KEY-3", callback);

        producer.close();
    }

    private static void sendMessageFor(KafkaProducer<String, String> producer, String key, MyCallback callback) {
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("first-topic", key,
                    "this is a test message for: " + i);
            producer.send(record, callback);
        }
    }
}
