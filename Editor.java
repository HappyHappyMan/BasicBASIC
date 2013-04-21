package Final;

import java.util.Scanner;

public class Editor {

  //TODO: main loop
    public static void main(String[] args) {
    LinkedList lines = new LinkedList();
    Scanner in = new Scanner(System.in);
    Dictionary vars = new Dictionary();

    System.out.println("Welcome to the testing release of this BASIC interpreter!");

    while(true) {
      System.out.print("> ");
      String input = in.nextLine();
      String keyword = input.split(" ")[0].toUpperCase();
      
      if (keyword.equals("RESEQUENCE")) {
        lines.resequence();
      } else if (keyword.equals("LIST")) {
        System.out.println(lines);
      } else if (keyword.equals("ACCEPT")) {
          String var = input.split(" ")[1];
          System.out.print(var + "= ");
          String num = in.nextLine();
          vars.put(var, Double.parseDouble(num));
      } else if (keyword.equals("PRINT")) {
          String var = input.split(" ")[1];
          System.out.println(var + " = " + vars.get(var));
      } else {
        //System.out.println("default case");
        String[] tokens = input.split(" ", 2);
        try {
          Integer.parseInt(tokens[0]);
          lines.insert(input);                  //to insert a line
        } catch (NumberFormatException e) {
          //TODO: need to implement direct interfacing. For now, an error msg.
          System.out.println("Invalid input.");
        }
      }
    }


  }
}