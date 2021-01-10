import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ds.katanem.Topic;
import com.ds.katanem.Value;

public class Broker{
	// message bytes
	static final byte BROKER_LIST = 1;
	static final byte VALUE_LIST = 2;
	// pinakas me tis ip twn brokers xwris auton pou trexoume
	static String[] brokers = {"192.168.1.4"};
	// ip gia auton ton broker
	static final String myIp = "192.168.1.4";
	static ArrayList<Topic> topics;
	static Map<String, ArrayList<Topic>> brokermap;
	static Map<Topic, ArrayList<Value>> topicMap;
	
	// publisher
	static ArrayList<Socket> publishers;
	static ArrayList<ObjectOutputStream> pubOut;
	static ArrayList<ObjectInputStream> pubIn;
	
	// subscriber
	Socket subscriber;
	ObjectOutputStream socketOutput;
	ObjectInputStream socketInput;
	
	public Broker(Socket subscriber) {
		this.subscriber = subscriber;
		try {
			socketOutput = new ObjectOutputStream(subscriber.getOutputStream());
			socketInput = new ObjectInputStream(subscriber.getInputStream());
			
			while (subscriber.isConnected()) {
				readFromSubscriber();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void readFromSubscriber() throws ClassNotFoundException, IOException {
		Topic topic = (Topic) socketInput.readObject();
		if (checkTopic(topic))
			sendDataToSubscriber(topic);
		else
			sendBrokerList();
	}
	
	public boolean checkTopic(Topic topic) {
			if (brokermap.get(myIp).contains(topic))
				return true;
			else
				return false;
	}
	
	public void sendDataToSubscriber(Topic topic) throws IOException {
		requestDataFromPublisher(topic);
		socketOutput.writeByte(VALUE_LIST);
		System.out.println(topicMap.containsKey(topic));
		ArrayList<Value> val = topicMap.get(topic);
		socketOutput.writeObject(val);
		socketOutput.flush();
	}
	
	public void sendBrokerList() throws IOException {
			socketOutput.writeByte(BROKER_LIST);
			socketOutput.writeObject(brokermap);
			socketOutput.flush();
	}
	
	public static void main(String[] args) {
		try {
			publishers = new ArrayList<Socket>();
			pubOut = new ArrayList<ObjectOutputStream>();
			pubIn = new ArrayList<ObjectInputStream>();
			
			topics = new ArrayList<Topic>();
			topicMap = new HashMap<Topic, ArrayList<Value>>();
			// server gia tous subscribers
			ServerSocket server = new ServerSocket(1234);
			
			// server gia tous publishers
			ServerSocket pubServer = new ServerSocket(1423);
			new Thread() {
				public void run() {
					Socket connection;
					try {
						connection = pubServer.accept();
						addPublisher(connection);
						requestDataFromPublisher();
						calculateKeys();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}.start();
			
			

			topics = new ArrayList<Topic>();
			
			topicMap = new HashMap<Topic, ArrayList<Value>>();
			calculateKeys();
			
			
			while (true) {
				// perimene gia sundesi me subscriber kai ksekina neo thread
				Socket connection = server.accept();
				new Thread() {
					public void run() {
						new Broker(connection);
					}
				}.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static synchronized void addPublisher(Socket connection) {
		publishers.add(connection);
		try {
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			pubOut.add(out);
			pubIn.add(in);
			Map<String, Topic> t = (HashMap<String, Topic>) in.readObject();
			topics.addAll(t.values());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static synchronized void calculateKeys() {
		/* Palios kwdikas
		// sortare tous brokers ana hash
		for (int i = 0; i < brokers.length - 1; i++) {
			int firsthash = brokers[i].hashCode();
			for (int j = i; j < brokers.length; j++) {
				int secondhash = brokers[i].hashCode();
				if (secondhash < firsthash) {
					String temp = brokers[i];
					brokers[i] = brokers[j];
					brokers[j] = temp;
				}
			}
		}
		
		// topothetise ta topics se ena map me ton katallilo broker
		brokermap = new HashMap<String, ArrayList<Topic>>();
		boolean[] used = new boolean[topics.size()];
		for (int i = 0; i < brokers.length; i++) {
			ArrayList<Topic> brokerTopics = new ArrayList<Topic>();
			for (int j = 0; j < topics.size(); j++) {
				if (used[j])
					continue;
				if (hashCode(topics.get(j).lineId).compareTo(hashCode(brokers[i])) < 0) {
					used[j] = true;
					brokerTopics.add(topics.get(j));
					System.out.print(brokers[i]);
					System.out.println(" " + topics.get(j));
				}
			}
			
			brokermap.put(brokers[i], brokerTopics);
		}
		*/
		
		brokermap = new HashMap<String, ArrayList<Topic>>();
		int counter = 0;
		for (int i = 0; i < brokers.length; i++) {
			ArrayList<Topic> brokerTopics = new ArrayList<Topic>();
			for (int j = counter; j < topics.size() * (i + 1) / brokers.length; j++) {
				brokerTopics.add(topics.get(j));
				System.out.print(brokers[i]);
				System.out.println(" " + topics.get(j));
				counter++;
			}
			brokermap.put(brokers[i], brokerTopics);
		}
		System.out.println(brokermap.toString());
	}
	
	public static String hashCode(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(input.getBytes());
			return new String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static synchronized void requestDataFromPublisher() {
		for (int j = 0; j < publishers.size(); j++) {
			try {
				
				
				for (int i = 0; i < topics.size(); i++) {
					pubOut.get(j).writeObject(topics.get(i));
					pubOut.get(j).flush();
					
					ArrayList<Value> array = new ArrayList<Value>();
					
					Value values = (Value) pubIn.get(j).readObject();
					while (values != null) {
						array.add(values);
						values = (Value) pubIn.get(j).readObject();
					}
					
					if (!topicMap.containsKey(topics.get(i))) {
						topicMap.put(topics.get(i), array);
					} else {
						for (int k = 0; k < array.size(); k++)
							topicMap.get(topics.get(i)).add(array.get(k));
					}
								
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	public static synchronized void requestDataFromPublisher(Topic t) {
		for (int j = 0; j < publishers.size(); j++) {
			try {
				
					pubOut.get(j).writeObject(t);
					pubOut.get(j).flush();
					
					ArrayList<Value> array = new ArrayList<Value>();
					
					Value values = (Value) pubIn.get(j).readObject();
					while (values != null) {
						array.add(values);
						values = (Value) pubIn.get(j).readObject();
					}
					
					if (!topicMap.containsKey(t)) {
						topicMap.put(t, array);
					} else {
						for (int k = 0; k < array.size(); k++)
							topicMap.get(t).add(array.get(k));
					}
								
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}
