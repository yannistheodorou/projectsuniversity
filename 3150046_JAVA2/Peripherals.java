import java.util.ArrayList;

public class Peripherals extends Item{

	//private ArrayList<String> actor = new ArrayList<String>(1);//Arraylist with peripherals' actors/actresses 
	 
	private String displays, keyboard, mouse, printer;
	
	 
	//Constructor
	Peripherals(String title,String displays,String keyboard,String mouse ,String printer, String publisher,
	 String date_of_production, String time, String type, int numAvailable, int numAll){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type);
		//this.actor = actor;
		this.displays = displays;
		this.keyboard = keyboard;
		this.mouse= mouse;
		this.printer=printer;
	}
	 
	//toString method needed to return additional information about the peripherals
    public String toString () {
		return super.toString() + 
				"\n" + "Displays: " + displays +
				"\n" + "Keyboard: " + keyboard +
				"\n" + "Mouse: " + mouse +
				"\n" + "Printer: " + printer +
				"\n";
    }
    
	//true4yes false4no
   // public boolean getLatestRelease()
		//return latest_release;
	//
}
