import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/*Ioannis Theodorou 3150046 4th Semester*/

public class mainApp {
	Scanner keyb = new Scanner(System.in);
	private static CatalogueAvailable item_cat;
	private static CatalogueRented rental_cat;
	int choose;
	int code_of_rental=1;
	boolean done=true;
	String name="";
	String phone="";
	
	private static final int dvdCost1=3; //cost per week for non-latest releases
	private static final int dvdCost2=2; //cost per day for latest releases
	private static final int blueRayCost=3;//per day
	private static final int gameCost=4;//per week
	private static final int overdueCost=1;//per day for every product
	
	
	public static void main (String[] args){
		mainApp program = new mainApp();
		program.Menu();
	}
	
	mainApp(){
		ArrayList<Item> item = new ArrayList<Item>(1);//Arraylist with Products
		ArrayList<Rental> rental = new ArrayList<Rental>(1);//Arraylist with Rentals
		
		item_cat = new CatalogueAvailable(item);
		rental_cat = new CatalogueRented(rental);
		
		//Arraylist with categories for Edge of Tomorrow
		ArrayList<String> categoryMovie1 = new ArrayList<String>(1);
		categoryMovie1.add("Action");
		categoryMovie1.add("Sci-fi");
		 
		//Arraylist with actors for Edge of Tommorow
		ArrayList<String> actorMovie1 = new ArrayList<String>(1);
		actorMovie1.add("Tom Cruise");
		actorMovie1.add("Emily Blunt");
		actorMovie1.add("Bill Paxton");
		
		//Making dvd and blue ray editions of Edge of Tomorrow
		Item movie1 = new Movie("Edge of Tomorrow","Doug Liman",  categoryMovie1, "Christopher McQuarrie", actorMovie1, "Warner Bros", "2014", "113 min", "DVD", false, 2, 2);
		Item movie2 = new Movie("Edge of Tomorrow","Doug Liman",  categoryMovie1, "Christopher McQuarrie", actorMovie1, "Warner Bros", "2014", "113 min", "blue ray", false, 2, 2);
		
		
		//The same with Fast & Furious 7
		ArrayList<String> categoryMovie2 = new ArrayList<String>(1);
		categoryMovie2.add("Action");
		categoryMovie2.add("Crime");
		categoryMovie2.add("Thriller");
		
		ArrayList<String> actorMovie2 = new ArrayList<String>(1);
		actorMovie2.add("Vin Diesel");
		actorMovie2.add("Paul Walker");
		actorMovie2.add("Dwayne Johnson");
		actorMovie2.add("Michelle Rodriguez");

		Item movie3 = new Movie("Fast & Furious 7", "James Wan", categoryMovie2, "Chris Morgan", actorMovie2, "Universal Pictures", "2015", "137 min", "DVD", true, 2, 2);
		Item movie4 = new Movie("Fast & Furious 7", "James Wan", categoryMovie2, "Chris Morgan", actorMovie2, "Universal Pictures", "2015", "137 min", "blue ray", true, 2, 2);
		
		//Adding the movies to CatalogueAvailable
		item_cat.add(movie1);
		item_cat.add(movie2);
		item_cat.add(movie3);
		item_cat.add(movie4);
		
		//The same with the games
		ArrayList<String> categoryGame1 = new ArrayList<String>(1);
		categoryGame1.add("Sports");
		  
		Item game1 = new Game("FIFA 13", "Playstation", categoryGame1, "EA Canada", "2013", 2, 2);
		Item game2 = new Game("FIFA 13", "XBOX", categoryGame1, "EA Canada", "2013", 2, 2);
		Item game3 = new Game("FIFA 13", "Nintendo", categoryGame1, "EA Canada", "2013", 2, 2);
		
		ArrayList<String> categoryGame2 = new ArrayList<String>(1);
		categoryGame2.add("FPS");
		  
		Item game4 = new Game("Battlefield 3", "Playstation", categoryGame2, "DICE", "2011", 2 ,2);
		Item game5 = new Game("Battlefield 3", "XBOX", categoryGame2, "DICE", "2011", 2, 2);
		Item game6 = new Game("Battlefield 3", "Nintendo", categoryGame2, "DICE", "2011", 2, 2);
		  
		item_cat.add(game1);
		item_cat.add(game2);
		item_cat.add(game3);
		item_cat.add(game4);
		item_cat.add(game5);
		item_cat.add(game6);
	}
	
	public void Menu() {
		do{
			System.out.println ("         \n            " );
			System.out.println ("         MENU          ");
	        System.out.println ("[0] Movie Overview     ");
	        System.out.println ("[1] Game Overview      ");
	        System.out.println ("[2] Rental Overview    ");
	        if(!done){
	        	System.out.println ("[3] Logout/Switch User ");
	        }
	        System.out.println ("[4] Exit				");
            ChooseOption();
		    switch (choose){
	        	case 0: MovieOverview (); break;          
		    	case 1: GameOverview (); break;
		        case 2: RentalOverview (); break;
		        case 3: Logout(); break;
		    	}
		    }while (choose != 4);
		System.out.println("\n" +"Bye Bye!!");
	}
	
	public void MovieOverview(){ //CASE {0}
		do{
			System.out.println("Choose betweed DVD and blue ray: ");
			System.out.println("[0] Cancel  ");
			System.out.println("[1] DVD     ");
			System.out.println("[2] blue ray");
			ChooseOption();
				switch (choose){
					case 1: Overview("DVD"); break;
					case 2: Overview("blue ray"); break;
				}
		}while(choose!=0);
	}
	
	public void GameOverview(){//CASE{1}
		do{
			System.out.println("Please choose your desired console");
			System.out.println("[0] Cancel                        ");
			System.out.println("[1] Playstation                   ");
			System.out.println("[2] XBOX                          ");
			System.out.println("[3] Nintendo                      ");            
			ChooseOption();
				switch (choose){
					case 1: Overview("Playstation"); break;
					case 2: Overview("XBOX"); break;
					case 3: Overview("Nintendo"); break;
				}
		}while(choose!=0);
	}
	
	//key=type of product (DVD, blue ray, Playstation (game),...)
	public void Overview(String key){
		int k = 1 ;
		System.out.println("[0] Cancel");
		
		for(int i=0; i<item_cat.getSize();i++){
			if (key==item_cat.getType(i)){
				System.out.print("["+k+"] ");
				item_cat.PrintTitle(i);//if user's choice (of type) matches the type of the product, then the title is printed
				k++;
			}
		}
		
		System.out.println("Please select the code of the product you want to see or Cancel!");
		ChooseOption();
		
		if(choose!=0){
			k=1;
			int index = 0;
			for(int i=0; i<item_cat.getSize(); i++){
				if(key==item_cat.getType(i)){
					if(k == choose){
						item_cat.Print(i);//all info regarding the desired product
						index = i;
						break;
					}
					k++;
				}
			}
			
			if(item_cat.getNumAvailable(index) > 0){
				do{
					System.out.println("Would you like to proceed with renting this product?");
					System.out.println("[0] NO");
					System.out.println("[1] YES");
					ChooseOption();
				}while(choose!=0 && choose!=1);
			}else{
				System.out.println("This product is unavailable.");
				choose = 0;
			}
			
			if(choose==1){
				Rent(index);
			}
		}
		
		choose = 0; //Every method is terminated and the program returns to main menu.
	}
	
	/*  
	 * method used to rent a product, name, phone number and time of rental are given by the user and then the cost of the rental is calculated depending on the product and our prices :P
	 */
	public void Rent(int i){
		int days_of_rental=0;
		int weeks_of_rental=0;
		if (done){
			System.out.print("Please type in your full name: ");
			keyb.nextLine();
			name = keyb.nextLine();
			System.out.print("Please type in a valid phone number: ");
			phone=keyb.nextLine();
			
			done=false;
			
			System.out.println("You are now logged in");
			System.out.println("Logout can be achieved from the main menu (Option [3])");
		}
		//per day (that includes movies that are blue rays and latest release DVDs)
		if(item_cat.MovieOrGame(i) && item_cat.getType(i)=="blue ray" || item_cat.MovieOrGame(i) &&  item_cat.getType(i)=="DVD" && item_cat.getLatestRelease(i)){
			do{				
				System.out.println("This product is rented per day. How many days will the rental last?");
				ChooseOption();
			}while(choose < 0);// it is allowed to rent something for 0 weeks or days just to be able to check if overdue cost is working
		days_of_rental=choose;
		//per week (games and non latest release DVDs)
		}else if(!item_cat.MovieOrGame(i) || item_cat.MovieOrGame(i) &&  item_cat.getType(i)=="DVD" && !item_cat.getLatestRelease(i)){
			do{
				System.out.println("This product is rented per week. How many weeks will the rental last?");
				ChooseOption();
			}while(choose < 0);
		weeks_of_rental=choose;
		days_of_rental=weeks_of_rental*7;
		}
		
		//calculation of the cost
		int cost = 0;
		if(item_cat.MovieOrGame(i) && item_cat.getType(i)=="DVD"){ //DVDmovie
			if (item_cat.getLatestRelease(i)) cost=dvdCost2*days_of_rental;//latest release
			else cost=dvdCost1*weeks_of_rental;//old release
		}
		else if (item_cat.MovieOrGame(i) && item_cat.getType(i)=="blue ray") cost=blueRayCost*days_of_rental;
		else if (!item_cat.MovieOrGame(i)) cost=gameCost*weeks_of_rental;
		
		//Creation of a new Rental that is inserted into CatalogueRented (also the number of available copies in CatalogueAvailable is decreased by 1)
		Date date = new Date();
		Rental rent = new Rental(code_of_rental, item_cat.getItem(i), name, phone, date, days_of_rental, cost, overdueCost, false, i);
		
		rental_cat.add(rent);
		item_cat.setNumAvailable(i, -1);
		
		code_of_rental++; //ascending number
		System.out.println("Product succesfully rented!");
	}
	
	public void RentalOverview(){//CASE{2}
		System.out.println("Rentals will appear here");
		System.out.println("[0] EXIT");
		for(int i=0; i<rental_cat.getSize(); i++){
			rental_cat.PrintRent(i);//All Rentals's codes,titles and user's name Are printed.
		}
		do{
			System.out.println("Please select the code of the rental you want to see or EXIT!");//A specific rental is chosen
			ChooseOption();
		}while(choose<0 || choose>code_of_rental);
		
		if(choose!=0){
			int i = choose - 1;
			
			int diff = DateDifference(i)-rental_cat.getDays_of_rental(i); //date difference in days between the time the product was rented and current time (when the user tries to return it)
			if ( diff < 0 ){ //if the product is returned before the time margin expires
				diff=0;
			}
			else{
				rental_cat.setOverdueDays(i, diff);
			}
			
			int ov_cost = diff*overdueCost;//overdue cost = days overdue * overdue cost
			rental_cat.setOverdueCost(i, ov_cost);
			
			rental_cat.Print(i);//All the characteristics of the Rental
			
			if(!rental_cat.getReturned(i)){ //if the product is not returned an option to return it is given
				System.out.println("Would you like to return this product?");
				System.out.println("[0] NO");
				System.out.println("[1] YES");
				do{
					ChooseOption();
				}while(choose!=0 && choose!=1);
				
				if(choose==1 ){
					item_cat.setNumAvailable(rental_cat.getCatAvIndex(i), 1); //CatalugueAvailable's number of copies for the selected product is increased by 1
					rental_cat.setReturned(i, true);//CatalogueRented is updated to show that the movie is actually returned
					System.out.println("Product succesfully returned!");
				}
			}else{
				System.out.println("This product is already returned!");
			}
		}		
	}
	public void Logout(){
		done=true;
		System.out.println("Logout succesful!");
	}
	
	public int DateDifference(int i){
		Date date = new Date();
		long diff = date.getTime() - rental_cat.getDate(i).getTime();
		if (diff%(1000 * 60 * 60 * 24)==0){ //if the product is late exactly 1,2 ... days
			return (int) diff/(1000 * 60 * 60 * 24);
		}
		else if (diff%(1000 * 60 * 60 * 24)>0){
			return (int) diff/(1000 * 60 * 60 * 24) + 1; //if the product is late 1,2 .. days plus some milliseconds
		}
		else return 0;		
	}
	
	public void ChooseOption (){
        System.out.print ("\n" + "Choose a number and press 'Enter' to continue: ");
        choose = keyb.nextInt();
        System.out.println();
    }

}
