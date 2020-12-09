package main.java.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.domain.Employee;
import org.apache.kafka.common.serialization.Serializer;

public class EmployeeSerializer implements Serializer<Employee> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Employee e) {
        // TODO Auto-generated method stub
        byte[] array = null;
        try {
            array = mapper.writeValueAsBytes(e);
        } catch (JsonProcessingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return array;
    }

}
