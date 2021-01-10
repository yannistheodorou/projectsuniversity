import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main{
	
	public static void main(String[] args) {
		
		ArrayList<Message> training = new ArrayList<>();
		ArrayList<Message> testing = new ArrayList<>();
		
		//readMessages("D:\\Users\\Kostas\\Desktop\\lingspam_public\\bare\\part1", training, testing);
		readMessages("C:\\Users\\Kevin\\OneDrive\\Documents\\dont-mind-me\\pa2\\readMails\\training", training, testing);
		//readAttributes(training);
	}
	
	
	/**
	 * A method that reads mail messages from a specific path and saves them in ArrayLists, according to their use
	 * @param directory the path where the mail messages are
	 * @param training the ArrayList containing the messages used to train our algorithm
	 * @param validation the ArrayList containing the messages used to validate our results
	 * @param testing the ArrayList containing the messages used to test our algorithm
	 */
	public static void readMessages(String directory, ArrayList<Message> training, ArrayList<Message> testing) {
		
		// Path to the email messages
		File folder = new File(directory);
		File[] files = folder.listFiles();
		
		// Lists for storing the ham and spam messages
		ArrayList<Message> hamMessages = new ArrayList<>();
		ArrayList<Message> spamMessages = new ArrayList<>();
		
		// Counters for the ham and spam messages
		int hamCount = 0;
		int spamCount = 0;
		
		
		// Scan all files, get their names and content, check if they are ham or spam and store them in the appropriate ArrayList
		for(File f : files) {
			if(f.isFile()) {
				String title = f.getName();
				String body = "";
				try {
					Scanner sc = new Scanner(f);
					sc.skip("Subject: ");
					while(sc.hasNextLine()) {
						body += " " + sc.nextLine();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				body = body.trim();
				//System.out.println(body);
				if(f.getName().startsWith("spmsga")) {
					spamMessages.add(new Message(title, body, Message.Category.SPAM));
					spamCount++;
				}
				else {
					hamMessages.add(new Message(title, body, Message.Category.HAM));
					hamCount++;
				}
			}
		}
		
		
		// Useful metrics
		int totalMessages = spamCount + hamCount;
		System.out.println("Total mail messages: " + totalMessages + ", ham: " + hamCount + ", spam: " + spamCount);
		
		
		// Distribute 75% of the messages for training and 25% for testing
		int trainingTotal = (int)(0.75 * totalMessages);
		int trainingHam = (int)(0.75 * hamCount);
		int trainingSpam = (int)(0.75 * spamCount);
		
		int testingTotal = totalMessages - trainingTotal;
		int testingHam = hamCount - trainingHam;
		int testingSpam = spamCount - trainingSpam;
		
		System.out.println("We are using 75% (" + trainingTotal + ") of our total mails for testing, which are " + trainingHam + " ham mails and " + trainingSpam + " spam mails.");
		System.out.println("We are using 25% (" + testingTotal + ") of our total mails for testing, which are " + testingHam + " ham mails and " + testingSpam + " spam mails.");
		System.out.println();
		
		
		// Add the calculated ham and spam messages in the training arraylist
		for(int i = 0; i < trainingHam; i++) training.add(hamMessages.remove(0));
		for(int i = 0; i < trainingSpam; i++) training.add(spamMessages.remove(0));


		
		// Add the calculated ham and spam messages in the testing arraylist
		for(int i = 0; i < testingHam; i++)	testing.add(hamMessages.remove(0));
		for(int i = 0; i < testingSpam; i++) testing.add(spamMessages.remove(0));

		System.out.println("Spamcount:"+trainingSpam+"\nHamcount:"+trainingHam);
		Message test = testing.get(0);
		System.out.println("Training Bayes...");
		Bayes b = new Bayes();
		b.addTrainingExamples(training);
		b.calcBestAttributes(10);
		System.out.println("Training complete!\nTesting for "+(test.isSpam()?"SPAM":"HAM")+" message.");
		
	}
	
	public static ArrayList<String> readAttributes(ArrayList<Message> messages){
		
		// Use a HashSet for the attributes in order to avoid duplicates
		HashSet<String> totalAttributes = new HashSet<>();
		HashSet<String> hamAttributes = new HashSet<>();
		HashSet<String> spamAttributes = new HashSet<>();
		
		for(Message m : messages) {
			String body = m.getBody().toUpperCase();
			String[] words = body.split("\\s+");
			
			for(String w : words) {
				//System.out.println("Word: "+w);//DEBUG
				totalAttributes.add(w);
				if(m.getCategory().equals(Message.Category.HAM)) hamAttributes.add(w);
				else spamAttributes.add(w);
			}
			
			/*// DEBUG_6
			for(String s : totalAttributes) {
				System.out.println(s + "\n");
			}*/
		}
		return null;
	}
}
