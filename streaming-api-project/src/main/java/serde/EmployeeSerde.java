package serde;

import deserializer.EmployeeDeserializer;
import domain.Employee;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import serializer.EmployeeSerializer;

public class EmployeeSerde implements Serde<Employee> {
    @Override
    public Serializer<Employee> serializer() {
        return new EmployeeSerializer();
    }

    @Override
    public Deserializer<Employee> deserializer() {
        return new EmployeeDeserializer();
    }
}
