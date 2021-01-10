import java.util.ArrayList;

public class Game extends Item{
		 
	//Construstor
	Game(String title, String type, ArrayList<String> category,String publisher, String date_of_production, int numAll, int numAvailable){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type);
	}
	
	//A game's characteristics are returned in a String
    public String toString () {
		return super.toString()+ 
				"\n";
    }
}
