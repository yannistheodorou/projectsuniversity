import java.util.Date;

public class Rental {
	
	private Date date;
	private String name, phone;
	private Item item;
	private int cost, overdue_cost, code_of_rental, days_of_rental, CatAvIndex, overdue_days = 0;
	private boolean returned;
	
	//Constructor
	Rental(int code_of_rental, Item item, String name, String phone, Date date, int days_of_rental, 
		   int  cost, int overdue_cost, boolean returned, int CatAvIndex){
		this.code_of_rental = code_of_rental;
		this.name = name;
		this.phone = phone;
		this.date = date;
		this.days_of_rental = days_of_rental;
		this.cost = cost;
		this.overdue_cost = overdue_cost;
		this.item = item;
		this.CatAvIndex = CatAvIndex;
	}
	
	//returns Rental's Charecterstics in a String
	public String toString(){
		return "Code of rental: " + code_of_rental +//first name and phone are printed
				"\n" + "Name: " + name +
				"\n" + "Phone: " + phone +
				"\n" + "\n" + "Item of rental: " +//then Item's characteristics are printed
				"\n" + item.toString() +
				"\n" +"\n" + "Date of rental: " + date +//and after that all the other characteristics of a Rental are printed
				"\n" + "Days of rental: " + days_of_rental +
				"\n" + "Overdue days: " + overdue_days +
				"\n" + "Total cost of rental: " + (cost + overdue_cost) +
				"\n" + "Overdue cost of rental: " + overdue_cost +
				"\n" + "Returned: " + returned;
	}
	
	//Getters
	public String getTitle(){
		return item.getTitle();
	}
	
	public String getName(){
		return name;
	}
	
	public int getCode_of_rental(){
		return code_of_rental;
	}
	
	public Date getDate(){
		return date;
	}
	
	public int getDays_of_rental(){
		return days_of_rental;
	}
	
	public boolean getReturned(){
		return returned;
	}
	
	public String getType(){
		return item.getType();
	}
	
	public int getCatAvIndex(){
		return CatAvIndex;
	}
	
	public int getOverdueDays(){
		return overdue_days;
	}
	
	//Setters
	public void setOverdueCost(int ov_cost){
		overdue_cost = ov_cost;
	}
	
	public void setReturned(boolean returned){
		this.returned = returned;
	}
	
	public void setCatAvIndex(int CatAvIndex){
		this.CatAvIndex = CatAvIndex;
	}
	
	public void setOvedueDays(int overdue_days){
		this.overdue_days = overdue_days;
	}
}
