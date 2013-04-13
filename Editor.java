package Final;

import java.util.Scanner;

public class Editor {

  //TODO: main loop
    public static void main(String[] args) {
    LinkedList lines = new LinkedList();
    Scanner in = new Scanner(System.in);

    System.out.println("Welcome to the testing release of this BASIC interpreter!");

    while(true) {
      System.out.print("> ");
      String input = in.nextLine();
      
      if (input.equals("RESEQUENCE")) {
        lines.resequence();
      } else if (input.equals("LIST")) {
        System.out.println(lines);
      } else {
        //System.out.println("default case");
        String[] tokens = input.split(" ", 2);
        try {
          Integer.parseInt(tokens[0]);
          lines.insert(input); //to insert a line
        } catch (NumberFormatException e) {
          System.out.println("Invalid input.");
        }
      }
    }


  }
}