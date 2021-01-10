import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class reversi{
    public static void main(String[] args){

        Scanner inputPlayer;
        int maxdepthByUser;
        String userChoice;
        Board b = new Board();
        int turns = 60;//einai ta kena plaisia,den kserw an xreiazetai pragmatika
        boolean turnplay;//true an o paixtis dialeksei prwtos
        boolean sentinelPlay;//xrisimeuei gia na ektelestei i kinisi alla kai na kratisoume to true i false gia peraiterw elegxo xwris epanalipsi tou play
        System.out.println("Welcome to riversi game!!!");
        System.out.print("Please enter an integer for the desired max depth(must be positive or zero):");
        while(true){
            try{
                inputPlayer = new Scanner(System.in);
                maxdepthByUser = inputPlayer.nextInt();
                if(maxdepthByUser<=0 ){
                    throw new Exception();
                }
                break;
            }catch(Exception e){
                System.err.print("The choice is invalid.Please choose a number between 3-7: ");
            }       
        }
        System.out.println();
        System.out.println("The depth defined by the user is: " + maxdepthByUser);
        System.out.println("Now the user must choose whether to play first or not.First player starts always with BLACK.");
        System.out.print("If you wish to play first then press Y (YES), otherwise press N (NO) :");
        while(true){
            try{
                inputPlayer = new Scanner(System.in);
                userChoice = inputPlayer.nextLine();
                if("y".equals(userChoice) || "Y".equals(userChoice) ){
                    System.out.println("You have chosen to play first!!");
                    turnplay = true;
                }
                else if( "n".equals(userChoice) || "N".equals(userChoice)){
                    System.out.println("The AI plays first!!");
                    turnplay = false;
                }
                else{
                    throw new Exception();
                }
                break;
            }catch(Exception e){
                System.err.print("The choice is invalid.Please choose a letter Y or N: ");
            }
        }
        System.out.println();
        //String st = new String();               
        System.out.println(b.toString());
        System.out.println();
        int i1;
        int i2;
        Board beforePlayBoard;
            while(true && turns !=0){
                try{
					//allazoume to turnplay kathe fora gia na mi grapsoume 2o try-catch me to AI na paizei prwto
                    if(turnplay == true){
                        System.out.println("In order to play your turn, type the tile you want to play e.g.: f5");
                        System.out.print("> ");
                        inputPlayer = new Scanner(System.in);
                        String tileInputByUser = inputPlayer.next();
                        //oi i1 kai i2 voithoun wste na na mpoun meta sto play,sto if symperilamvanontai oi elegxoi tou input tou xristi
                        i1 = Character.getNumericValue(tileInputByUser.charAt(0));
                        i2 = Character.getNumericValue(tileInputByUser.charAt(1));
                        if(i1<10 || i1>17 || i2<1 ||i2>8 || tileInputByUser.length() != 2){
                            throw new InputMismatchException();
                        }
                        if(b.isGameOver() == true){
                            throw new Error();
                        }
                        beforePlayBoard = b;//krataei to antigrafo tou board prin to play.se periptwsi sfalmatos gyrnaei sto telefteo image tou board
                        sentinelPlay = b.play(Board.tile.BLACK, (byte)(i1-9), (byte)i2, true);
                        if(sentinelPlay == true){
                            System.out.println(b.toString());
                            turns--;
                        }
                        else{
                            b = beforePlayBoard;
                            throw new IllegalArgumentException();
                        }
                        turnplay = false;
                    }
                    else{
                        System.out.println("Now it is the AI turn to AUTOplay!!!");
						
                        //edw tha einai to autoplay tou AI vasei tou minimax,endeiktika egw evala gia ta aspra
                        System.out.print("> \n");                   
			beforePlayBoard = b;//krataei to antigrafo tou board prin to play.se periptwsi sfalmatos gyrnaei sto telefteo image tou board
                        //sentinelPlay = b.play(Board.tile.WHITE, (byte)(i1-9), (byte)i2, true);
                        
			Board nb = new MinMax(Board.other(b.lastPlayer), 3, b).getMove();
			b= nb;
                        if(b.isGameOver() == true){
                            throw new Error();
                        }
                        if(/*sentinelPlay ==*/ true){
                            System.out.println(b.toString());
                            turns--;
                        }
                        else{
                            b = beforePlayBoard;
                            throw new IllegalArgumentException();
                        }
                        turnplay = true;
                    }
                    
                }catch(IllegalArgumentException e){
                    System.err.println("Movement is illegal!!!\n"+e);
                }catch(InputMismatchException e){
                    System.err.println("Input is invalid or out of bounds!!!");
                }catch(Error e){
                    System.err.println("No more valid moves on the game or end of moves!!!");
                    System.err.println("GAME TERMINATED!!!");
		    String outcome = "";
		    if(Heureticals.h1(b)<0){
			    outcome = "WHITE WON";
		    }else if(Heureticals.h1(b)>0){
			    outcome = "BLACK WON";
		    }else{
			    outcome = "ITS A TIE!";
		    }

                    System.err.println(""+outcome);
                    break;
                }
            }   
    }
}

