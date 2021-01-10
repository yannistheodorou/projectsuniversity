import java.util.*;
import java.io.PrintStream;
public String DNAPalindrome(){
public static void main(String[]args){
    Scanner dna = new Scanner(System.in);  
    System.out.println("Enter a dna sequence: ");
    char dna  = reader.nextInt(); 
    Scanner reader = new Scanner(System.in);
    System.out.println("Insert DNA Sequence: ");
    String DNA = reader.nextLine();
    reader.close();
    boolean hasLower = false;
    boolean hasSpace = false;
    boolean hasSpecial=false;
 
    for (int i = 0; i < DNA.lenght(); i++){
    ch = DNA.charAt(i);
    if ( !Character.isLowerCase(ch))
    {
        System.out.println("Invalid DNA sequence.");
        hasLower=true;
    }//if
 
    if ( !Character.contains(" "))
    {
        System.out.println("Invalid DNA sequence.");
        hasSpace=true;
    }//if
 
    if ( !Character.contains("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
    {
        System.out.println("Invalid DNA sequence.");
        hasSpecial=true;
    }//if
 
}//for

    StringBuilder builder = new StringBuilder();
    for(int i=0;i<dna.length();i++){
        char c = dna.charAt(i);
        if(dna.charAt(i) == 'T'){
            builder.addLast('A');
        }
        if(dna.charAt(i) == 'A'){
            builder.addLast('T');
        }
        if(dna.charAt(i) == 'C'){
            builder.addLast('G');
        }
        if(dna.charAt(i) == 'G'){
            builder.addLast('T');
        }   
    }
    return builder.toString();



if(builder.equals(dna)){
    return ("It's correct.\n"+ builder);
}else{
    return("It's incorrect.");
	}
}	
}