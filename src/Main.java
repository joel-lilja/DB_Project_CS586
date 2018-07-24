import java.sql.*;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String input;

//Get Database Login Information Here
        String conStringC;
        String conStringU;
        String conStringP;

        LoginMenu login = new LoginMenu();

        conStringC = login.getConString("database connection");
        conStringU = login.getConString("database user");
        conStringP = login.getConString("user password");
        System.out.println("Great I've got your login information now lets use the database");
        System.out.println();
        System.out.println();
        System.out.println();
/////////////////////////

//Initial menu

        System.out.println("Do you need to build the database y/n? ");
        System.out.print("Build Database: ");
        if(scan.nextLine() == "y") {
            BuildTable table = new BuildTable(conStringC, conStringU, conStringP);
            table.BuildDatabase();
            System.out.println("The database has been built");
            System.out.println("Continuing to the rest of the program");
        }

        System.out.println();
        System.out.println("Welcome to the baseball database");
        QueryClass query = new QueryClass(conStringC, conStringU, conStringP);
        while(true){
            System.out.println("PRESS 1-20 to view a query");
            System.out.println("press e to exit, o to submit your own query, or i to insert information into the database");
            System.out.print("Selection: ");
            input = scan.nextLine();
            switch(input) {
                case "e":
                    return;
                case "o":
                    System.out.println("You can enter your own sql query below");
                    System.out.println("Please do not drop my tables they are fragile");
                    System.out.print("Statmenet: ");
                    String osql = scan.nextLine();
                    query.AppQuery(osql);
                    break;
                case "i":
                    System.out.println("insert some stuff into the database!");
                    System.out.print("statement: ");
                    String isql = scan.nextLine();
                    query.updateTable(isql);
                    System.out.println();
                    break;
                case "1":
                    query.Query1();
                    break;
                case "2":
                    query.Query2();
                    break;
                case "3":
                    query.Query3();
                    break;
                case "4":
                    query.Query4();
                    break;
                case "5":
                    query.Query5();
                    break;
                case "6":
                    query.Query6();
                    break;
                case "7":
                    query.Query7();
                    break;
                case "8":
                    query.Query8();
                    break;
                case "9":
                    query.Query9();
                    break;
                case "10":
                    query.Query10();
                    break;
                case "11":
                    query.Query11();
                    break;
                case "12":
                    query.Query12();
                    break;
                case "13":
                    query.Query13();
                    break;
                case "14":
                    query.Query14();
                    break;
                case "15":
                    query.Query15();
                    break;
                case "16":
                    query.Query16();
                    break;
                case "17":
                    query.Qeury17();
                    break;
                case "18":
                    query.Qeury18();
                    break;
                case "19":
                    query.Query19();
                    break;
                case "20":
                    query.Query20();
                    break;
                default:
                    System.out.println();
                    System.out.println("AH AH AH YOU DIDN'T SAY THE MAGIC WORD");
                    System.out.println();

            }
        }








    }

}