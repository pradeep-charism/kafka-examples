package kafkastreams;

import domain.Employee;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import serde.EmployeeSerde;

import java.util.Properties;

public class EmployeeStreamingAggregationApp {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "mysql-cassandra-etl-app");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EmployeeSerde.class.getName());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Employee> mysqlEmpData = builder.stream("mysql-topic1-employee");
        KStream<String, Employee> empWithKeyStream = mysqlEmpData.selectKey((key, value) -> value.getDesignation());
        empWithKeyStream.peek((k,v) -> System.out.println(k +"-"+ v));

        KGroupedStream<String, Employee> groupedByDesignation = empWithKeyStream.groupByKey();
        KTable<String, Long> count = groupedByDesignation.count();
        count.toStream().to("count-employee", Produced.with(Serdes.String(), Serdes.Long()));

        Topology topogy = builder.build();
        KafkaStreams streams = new KafkaStreams(topogy, props);

        streams.start();


        try {
            Thread.sleep(5 * 60 * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
