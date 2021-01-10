import java.util.ArrayList;

public class Movie extends Item{

	private ArrayList<String> actor = new ArrayList<String>(1);//Arraylist with movie's actors/actresses 
	 
	private String director, script_writer, time;
	private boolean latest_release;
	 
	//Constructor
	Movie(String title, String director, ArrayList<String> category,String script_writer,ArrayList<String> actor, String publisher,
	 String date_of_production, String time, String type, boolean latest_release, int numAvailable, int numAll){
		super(title, publisher, date_of_production, category, numAvailable, numAll, type);
		this.actor = actor;
		this.director = director;
		this.script_writer = script_writer;
		this.latest_release = latest_release;
		this.time = time;
	}
	 
	//toString method needed to return additional information about the movie
    public String toString () {
		return super.toString() + 
				"\n" + "Time: " + time +
				"\n" + "Actors: " + actor +
				"\n" + "Director: " + director +
				"\n" + "Script writer: " + script_writer +
				"\n" + "Latest Release: " + Boolean.toString(latest_release) +
				"\n";
    }
    
	//true4yes false4no
    public boolean getLatestRelease(){
		return latest_release;
	}
}
