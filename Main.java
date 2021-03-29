import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * Main.java created by Weihang Guo on Mac Air in p0 JavaIOPractice
 * 
 * Author:   Weihang Guo (wguo63@wisc.edu)
 * Date:     01/28/2020
 * 
 * Course:   CS400
 * Semester: Spring 2020
 * Lecture:  001
 */

/**
 * Main - models an ordering system in which the user can order by entering numbers or upload a file
 * @author Weihang Guo
 * 
 */
public class Main {
  
  private static final Scanner scr = new Scanner(System.in);
  //A scanner connected with the keyboard to read user's input
  private final String title = "Name: Weihang Guo\npercentage: wguo63@wisc.edu\nLecture: 001";
  //my Milkal information
  private static final String[] menu = {"Fries", "Chips", "Rice", "salad"};
  //A menu which informs the users about what types of food they can order
  private static final int[] prices = {3, 6, 1, 4};
  //The prices of food
  private int total = 0;
  //to store the total price
  private String order = "nothing";
  //prompts the users of what they have ordered
  private ArrayList<String> food = new ArrayList<>();
  //an arraylist that stores the food types and amount ordered by the user
  
  /**
   * Presents the menu and other operations the users can do
   */
  private void showMenu() {
    System.out.println("\nMENU:");
    for (int i = 0; i < menu.length; i ++) {    
      System.out.println(i+1 + " " + menu[i] + " $" + prices[i]);
    }
    System.out.println("5 Order Online\n6 Quit");
    System.out.println("Please order by entering the number!");
    
  }
  
  /**
   * @param type the food type
   * @param amount the amount of the food
   * @return the price of the food ordered
   * @throws NumberFormatException if the user enters something that is not an integer 
   * or the amount entered is 0
   */
  private int addOrder(String type, String amount) throws NumberFormatException {
    //stores the type and amount of the food in type int
    int typeInt = Integer.parseInt(type);
    int amountInt = Integer.parseInt(amount);
    //prompt the user that the amount can't be 0.
    if (amountInt == 0) {
      throw new NumberFormatException("Amount cannot be 0.");
    }
    //add the order into the ArrayList food
    food.add(amountInt + " " + menu[typeInt - 1]);
    order = food.get(0);//stores the first order in the String order
    //add the rest order to the String order
    for (int i = 1; i < food.size() - 1; i ++) {
      order += ", " + food.get(i);
    }
    if (food.size() != 1) {
      order += " and " + food.get(food.size() - 1);
    }
    //compute the price
    int price = prices[typeInt - 1] * amountInt;
    //add the price to the total price
    total += price;
    return price;
  }
  
  
    
    
  /**
   * Takes the user's command and execute.
   * @return true if the user wants to quit, false if the user want to make other commands
   */
  private boolean order() {
    String number = scr.nextLine();//stores the user's input
    switch(number) {
      //1-4: to choose a certain food from the menu
      case "1":
      case "2":
      case "3":
      case "4":
        System.out.println("Amount:");//prompts the user to enter the amount of the food
        String amount = scr.nextLine();//stores the amount of the food
        int price = addOrder(number, amount);//stores the price and add this order to the order list
        System.out.println("You have ordered " + food.get(food.size() - 1) + 
            ". The price is " + price + " dollars. \nAnything else?");
        //prompts the user
        break;
      //5: to make an online order by entering the order name (which is actually the name of a file)
      case "5":
        System.out.println("Please enter your order name:");//prompts the user
        File onlineOrder = new File(scr.nextLine() + ".txt");//creates a file with the name entered
        try {
          orderOnline(onlineOrder);
        } catch (FileNotFoundException e) {
          System.out.println(e.getMessage());//prompts the user when there is no such order
        }
        break;
      //6: to quit from the ordering system
      case "6":
        System.out.println("You have ordered " + order + ". The total is " + total + 
            " dollars. \nHave a good one!");//prompts the user
        return true;
      default:
        System.out.println("Invalid input. Please enter again.");
        //when user enters something other than the number 1-6, prompts the user to enter again
        break;
    }
    return false;       
  }
  
  /**
   * Read from a file to make an online order. Each line represents one food to be ordered, and each
   * line should contain two numbers which are separated by one or more spaces. The first number is 
   * the type of food, and the second number is the amount of the food.
   * @param file to be read
   * @throws FileNotFoundException if the user's input cannot be found
   */
  private void orderOnline(File file) throws FileNotFoundException {
    if (!file.exists()) {
      throw new FileNotFoundException("Order not found.");
    }
    Scanner input = new Scanner(file);//creates a new scanner to read the file
    PrintWriter pw = new PrintWriter("Order Confirmation.txt");
    //creates a PrintWrtier to write a file
    ArrayList<String> foodOnline = new ArrayList<>();//stores the food ordered in the file
    String orders = "nothing";//the prompt which would tell the users what they have ordered
    int totalPrice = 0;//the total price of the food ordered
    while(input.hasNext()) {
      String onlineOrder = input.nextLine();//Stores the contents of the file line by line.
      try {
        if (onlineOrder.trim().split("\\s+").length != 2) {
          throw new DataFormatException("This order is invalid.");
          //while the file is not written in the right form, throw an exception.
        }
        String type = onlineOrder.trim().split("\\s+")[0];
        //Separates the onlineOrder String and get the first part of it.
        String amount = onlineOrder.trim().split("\\s+")[1];
        //Separates the onlineOrder String and get the second part of it.
        int typeInt = Integer.parseInt(type);
        if (typeInt < 1 || typeInt > 4) {
          throw new DataFormatException("This order is invalid.");
          //throw an exception if the first number is other than integer 1-4.
        }
        int amountInt = Integer.parseInt(amount);     
        foodOnline.add(amountInt + " " + menu[typeInt - 1]);//add to the food list
      //updates the prompt
        orders = foodOnline.get(0);
        for (int i = 1; i < foodOnline.size() - 1; i ++) {
          orders += ", " + foodOnline.get(i);
        }
        if (foodOnline.size() != 1) {
          //the size is not 1, meaning the first and last food aren't the same one
          orders += " and " + foodOnline.get(foodOnline.size() - 1);
        }
        
        totalPrice += prices[typeInt - 1] * amountInt;//updates the total price
      } catch (DataFormatException e) {
        pw.println(e.getMessage());
        //write the exception message if the file is not in the right form
      } catch (NumberFormatException e) {
        pw.println("The order must be written in numbers.");
        //write the exception message if the file is not written in numbers
      }
    }
    pw.println("You have ordered " + orders + ". The total is " + totalPrice + 
        " dollars. \nHave a good one!");//write the prompt
    input.close();
    pw.close();
    System.out.println("Online order received! Anything else?"); //print the prompt to the console
  }
  
  
  /**
   * the driver method.
   */
  private void driver() {
    boolean quit = false;
    while (!quit) {
      showMenu();
      quit = order();
    }
  }
  
  /**
   * Main method of the program
   * 
   * @param args the string arguments from the command line
   */
  public static void main(String[] args) {
    Main restaurant = new Main();//create a new main instance
    System.out.println(restaurant.title);
    restaurant.driver();//starts the ordering system
    
    

  }

}
