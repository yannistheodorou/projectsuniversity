import java.util.*;


public class Message{
	
	// An e-mail can either be SPAM(1) or HAM(0)
	public enum Category{
		HAM, SPAM
	}
	private HashSet<String> attributes = new HashSet<String>();
	private String title;
	private String body;	// the text of the e-mail
	private Category cat;	// the category (SPAM/HAM) of the email
	
	
	// ******************** Constructor ******************** //
	public Message(String title, String body, Category cat) {
		this.title = title;
		this.body = body;
		this.cat = cat;
		this.attributes = calcAttributes();
	}
	
	
	// ******************** Getters & Setters ******************** //
	public boolean isSpam() {
		return this.cat.equals(Message.Category.SPAM);
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String input) {
		title = input;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String input) {
		body = input;
	}
	
	public Category getCategory() {
		return cat;
	}
	
	public void setCategory(Category input) {
		cat = input;
	}

	public HashSet<String> getAttributes() {
		return this.attributes;
	}

	public HashSet<String> calcAttributes(){
		
		// Use a HashSet for the attributes in order to avoid duplicates
		HashSet<String> totalAttributes = new HashSet<>();
		//HashSet<String> hamAttributes = new HashSet<>();
		//HashSet<String> spamAttributes = new HashSet<>();
		
		//for(Message m : messages) {
			String body = this.getBody().toUpperCase();
			String[] words = body.split("\\s+");
			
			for(String w : words) {
				//System.out.println("Word: "+w);//DEBUG
				totalAttributes.add(w);
				//if(m.getCategory().equals(Message.Category.HAM)) hamAttributes.add(w);
				//else spamAttributes.add(w);
			}
			
			/*// DEBUG_6
			for(String s : totalAttributes) {
				System.out.println(s + "\n");
			}*/
		//}
		return totalAttributes;
	}

}
