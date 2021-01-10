import java.util.ArrayList;

public abstract class Item {
	private int numAvailable, numAll;
	private String publisher, dateOfProduction, title, type;
	private double price;
	
	private ArrayList<String> category = new ArrayList<String>(1);//Arraylist with product's catergory/-ies
	
	//Constructor
	Item(String title, String publisher, String dateOfProduction, ArrayList<String> category,double price, int numAvailable, int numAll, String type) {
		this.title = title;
		this.publisher = publisher;
		this.dateOfProduction = dateOfProduction;
		this.category = category;
		this.numAll = numAll;
		this.numAvailable = numAvailable;
		this.type = type;
		this.price = price;
	}
	
	//returns Item's Charecterstics in a String
    public String toString () {
		return "Title: " + title +
				"\n" + "Publisher: " + publisher +
				"\n" + "Date of Production: " + dateOfProduction +
				"\n" + "Category: " + category + 
				"\n" + "Price: " + price +
				"\n" + "Number of All Copies: " + numAll +
				"\n" + "Number of Available Copies: " + numAvailable +
				"\n" + "Type of Product: " + type;
    }
	
	//Getters
	public String getPublisher(){
		return publisher;
	}
	
	public String getDate(){
		return dateOfProduction;
	}
	public String getType(){
		return type;
	}
	public int getNumAvailable(){
		return numAvailable;
	}
	public String getTitle(){
		return title;
	}
	
	public int getNumAll(){
		return numAll;
	}
	
	public double getPrice(){
		return price;
	}
	
	//Setter
	public void setNumAvailable(int numAvailable){
		this.numAvailable = numAvailable;
	}
}
