package deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Employee;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class EmployeeDeserializer implements Deserializer<Employee> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Employee deserialize(String topic, byte[] data) {
        // TODO Auto-generated method stub
        Employee employee = null;

        System.out.println("deserializing " + new String(data));
        try {
            employee = mapper.readValue(data, Employee.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return employee;
    }

}
