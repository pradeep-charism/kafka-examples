package main.java.partitioner;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class MessagePartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
	//initialization logic	
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		//clean up code
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		// TODO Auto-generated method stub
		int partition=3;
		if(key.equals("test-message-1")) {
			partition=0;
		}
		else if(key.equals("test-message-2")) {
			partition=1;
		}
		else if(key.equals("test-message-3")) {
			partition=2;
		}
		System.out.println("sending message with key "+key+" to partition "+partition);
		return partition;
	}

}
