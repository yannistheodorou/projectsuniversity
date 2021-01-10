import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.ds.katanem.Bus;
import com.ds.katanem.Topic;
import com.ds.katanem.Value;

public class Publisher {
	// Broker connection
	static String[] brokerIp = {"192.168.1.4"};
	static int brokerPort = 1423;
	Socket connection;
	ObjectInputStream brokIn;
	ObjectOutputStream brokOut;
	Topic lastTopic = new Topic();
	
	static Map<String, Topic> topics;
	// metraei posa exw steilei gia kathe topic
	static Map<String, Integer> counter;
	static ArrayList<Value> values;
	
	public Publisher(Socket connection) {
		lastTopic = new Topic();
		this.connection = connection;
		try {
			brokOut = new ObjectOutputStream(connection.getOutputStream());
			brokIn = new ObjectInputStream(connection.getInputStream());
			
			
			sendTopics();
			
			while(true) {
				Topic topic = readTopic();
				if (topic != null) {
					if (!lastTopic.equals(topic)) {
						resetCounter();
						lastTopic = topic;
					}
				}
				sendValues(topic);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void sendTopics() throws IOException {
		brokOut.writeObject(topics);
		brokOut.flush();
	}
	
	public Topic readTopic() throws ClassNotFoundException, IOException {
		return (Topic) brokIn.readObject();
	}
	
	public synchronized void sendValues(Topic topic) throws IOException {
		
		int count = 0;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i).bus.lineCode.equals(topic.lineCode)) {
				if (count <= counter.get(topic.lineCode)) {
					brokOut.writeObject(values.get(i));
					brokOut.flush();
					count++;
				} else {
					counter.put(topic.lineCode, count);
					break;
				}
			}
		}
		
		brokOut.writeObject(null);
		brokOut.flush();
	}
	
	public static void main(String[] args) {
		parseFiles();
		
		for (String broker : brokerIp) {
			new Thread() {
				@Override
				public void run() {
					try {
						new Publisher(new Socket(broker, brokerPort));
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
	
	public static synchronized void resetCounter() {
		for (String key : counter.keySet()) {
			counter.put(key, 0);
		}
		System.out.println("Reseted");
	}
	
	public static synchronized void parseFiles() {
		topics = new HashMap<String, Topic>();
		counter = new HashMap<String, Integer>();
		values = new ArrayList<Value>();
		
		File file = new File("busLinesNew.txt");
		try {
			Scanner fileinput = new Scanner(file);
			while (fileinput.hasNextLine()) {
				String line = fileinput.nextLine();
				String[] substr = line.split(",");
				Topic topic = new Topic();
				topic.lineCode = substr[0];
				topic.lineId = substr[1];
				topic.description = substr[2];
				
				topics.put(topic.lineCode, topic);
				counter.put(topic.lineCode, 0);
			}
			fileinput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		file = new File("busPositionsNew.txt");
		try {
			Scanner fileinput = new Scanner(file);
			fileinput.useDelimiter(",");
			while (fileinput.hasNextLine()) {
				String line = fileinput.nextLine();
				String[] substr = line.split(",");
				String lineCode = substr[0];
				String routeCode = substr[1];
				String vehicleId = substr[2];;
				String latitude = substr[3];;
				String longtitude = substr[4];;
				String timestamp = substr[5];
				
				Topic t = topics.get(lineCode);
				
				Bus b = new Bus();
				b.buslineId = t.lineId;
				b.lineName = t.description;
				b.lineCode = lineCode;
				b.routeCode = routeCode;
				b.vehicleId = vehicleId;
				
				Value v = new Value();
				v.latitude = Double.parseDouble(latitude);
				v.longtitude = Double.parseDouble(longtitude);
				v.timestamp = timestamp;
				v.bus = b;
				
				values.add(v);
				
			}
			fileinput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
