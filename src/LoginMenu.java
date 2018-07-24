import java.util.Scanner;

public class LoginMenu {

    public LoginMenu(){
        scan = new Scanner(System.in);
        System.out.println("Welcome to my baseball database program");
        System.out.println("Lets gather your postgresql database login information");
    };

    public String getConString(String description){
        System.out.println("Please enter the " + description + " below");
        System.out.print("Enter Here: ");
        return scan.nextLine();
    }


   private Scanner scan;

}
