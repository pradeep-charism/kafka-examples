package kafkastreams;

import domain.Employee;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import serde.EmployeeSerde;

import java.util.Properties;

public class EmployeeStreamingAppTopicToCassandra {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, "mysql-cassandra-etl-app");
        props.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class.getName());
        props.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, EmployeeSerde.class.getName());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Employee> mysqlEmpData = builder.stream("mysql-topic-employee");
        KStream<String, Employee> empWithSalaryData = mysqlEmpData.mapValues(emp -> {
            System.out.println("processing employee with id " + emp.getEmp_id());
            Employee employee = new Employee();
            employee.setEmp_id(emp.getEmp_id());
            employee.setEmp_name(emp.getEmp_name());
            String designation = emp.getDesignation();
            employee.setDesignation(designation);
            int salary = 20000;
            if (designation.equals("Developer")) {
                salary = 30000;
            } else if (designation.equals("Accountant")) {
                salary = 25000;
            }
            employee.setSalary(salary);
            return employee;
        });

        empWithSalaryData.to("cassandra-topic-employee");

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
