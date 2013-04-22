package Final;

import java.util.Scanner;

public class Editor {

  //TODO: main loop
  //TODO: Implement RUN! Can't believe you forgot to do this...
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
          System.out.print(var + " = ");
          String num = in.nextLine();
          vars.put(var, Double.parseDouble(num));
      } else if (keyword.equals("PRINT")) {
          String var = input.split(" ")[1];
          System.out.println(var + " = " + vars.get(var));
      } else if (keyword.equals("RUN")) {
          //TODO: make it do things

      } else {
        //System.out.println("default case");
        String[] tokens = input.split(" ", 2);
        try {
          Integer.parseInt(tokens[0]);
          lines.insert(input);                  //to insert a line
        } catch (NumberFormatException e) {
          //TODO: need to implement direct interfacing. For now, an error msg.
          //Eventually, this will just take in lines and interpret them straight-up,
          //but this depends on the InfixConverter which isn't quite done yet.
          System.out.println("Invalid input.");
        }
      }
    }
  }
}