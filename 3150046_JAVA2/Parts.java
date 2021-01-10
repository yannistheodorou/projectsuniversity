import java.util.ArrayList;

public class Parts extends Item{
	private String motherboards, CPU, RAM, graphics, hardDrive	 
	//Construstor
	Parts(String title, String type, ArrayList<String> category,String publisher, String date_of_production, int numAll, int numAvailable,
	String motherboards,String RAM,String CPU,String graphics,String hardDrive){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type, price);
		this.hardDrive=hardDrive;
		this.CPU=CPU;
		this.RAM=RAM;
		this.graphics=graphics;
	}
	
	//Parts' characteristics are returned in a String
    public String toString () {
		return super.toString()+ 
				"\n";
    }
}
