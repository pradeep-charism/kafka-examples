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







