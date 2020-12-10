package main.java.partitioner;

import main.java.domain.Employee;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class EmployeePartitioner implements Partitioner {
	FileInputStream fin;
	Properties props;
	@Override
	public void configure(Map<String, ?> properties) {
		// TODO Auto-generated method stub
		try {
			fin=new FileInputStream("designation.properties");
			props=new Properties();
			props.load(fin);
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		props=null;
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// TODO Auto-generated method stub
		int partition=3;
		Employee emp=(Employee)value;
		String designation=emp.getDesignation();
		if(props.containsKey(designation)) {
			partition=Integer.parseInt(props.getProperty(designation));
		}
		System.out.println("sending employee with designation "+designation+" to partition "+partition);
		return partition;
	}

}
