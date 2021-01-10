import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.ds.katanem.Topic;
import com.ds.katanem.Value;

public class Subscriber {
	// message bytes
	final byte BROKER_LIST = 1;
	final byte VALUE_LIST = 2;
	// ip enos broker pou tha mas dwsei tous allous
	final String firstBroker = "127.0.1";
	// port pou xrisimopoioun oi brokers
	final int brokerPort = 1234;
	// xartis me tous broker
	Map<String, ArrayList<Topic>> brokermap;
	ArrayList<Value> values;
	ArrayList<Topic> topics;
	
	Socket socket;
	ObjectInputStream socketIn;
	ObjectOutputStream socketOut;

	public Subscriber() {
		try {
			readTopicsFromFile();
			socket = new Socket(firstBroker, brokerPort);
			socketIn = new ObjectInputStream(socket.getInputStream());
			socketOut = new ObjectOutputStream(socket.getOutputStream());
			
			// steile lathos topic gia na pareis thn lista me tous brokers
			Topic t = new Topic();
			t.lineCode = "";
			t.lineId = "";
			t.description = "";
			requestTopic(t);
			readValues();
			socket.close();
			
			while (true) {
				Topic selection = selectTopic();
				for (String key : brokermap.keySet()) {
					ArrayList<Topic> list = brokermap.get(key);
					if (list.contains(selection)) {
						socket = new Socket(key, brokerPort);
						socketIn = new ObjectInputStream(socket.getInputStream());
						socketOut = new ObjectOutputStream(socket.getOutputStream());
						
						requestTopic(selection);
						if (!readValues())
							System.out.println("Requested data from the wrong broker");
						else
							printValues();
						
						socket.close();
					}
				}
			}
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printValues() {
		if (values == null || values.size() <= 0) {
			System.out.println("No data for this bus line!");
			return;
		}
		
		for (int i = 0; i < values.size(); i++) {
			System.out.println(values.get(i).toString());
		}
	}
	
	public Topic selectTopic() {
		System.out.println("Select a bus line!");
		for (int i = 0; i < topics.size(); i++) {
			System.out.println((i+1) + ". " + topics.get(i).lineId + " " + topics.get(i).description);
		}
		Scanner systemin = new Scanner(System.in);
		int index;
		while((index = systemin.nextInt()) < 1 || index > topics.size()) {
			System.out.println("Enter a valid number from 1 to " + topics.size());
		}
		index--;
		return topics.get(index);
	}
	
	public void requestTopic(Topic topic) throws IOException {
		socketOut.writeObject(topic);
		socketOut.flush();
	}
	
	public boolean readValues() throws IOException, ClassNotFoundException {
		byte message = socketIn.readByte();
		if (message == BROKER_LIST) {
			System.out.println("broker list");
			brokermap = (HashMap<String, ArrayList<Topic>>) socketIn.readObject();
			return false;
		} else if (message == VALUE_LIST) {
			values = (ArrayList<Value>) socketIn.readObject();
			return true;
		}
		return false;
	}
	
	public void readTopicsFromFile() {
		topics = new ArrayList<Topic>();
		
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
				
				topics.add(topic);
				
			}
			fileinput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Subscriber();
		
	}
	
	
}
