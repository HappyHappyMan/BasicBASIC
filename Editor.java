package Final;

import java.util.Scanner;
import java.util.ArrayList;

public class Editor {

  //TODO: main loop
  //TODO: Implement RUN! Can't believe you forgot to do this...
  //      As far as implementing RUN goes, have a private method
  //      in here that handles the logic around IFs and LETs and
  //      whatever else. This means that this Class is the one that handles
  //      passing stuff to InfixConverter, which merely returns a double 
    static LinkedList lines = new LinkedList();
    static Dictionary vars = new Dictionary();
    static Scanner in = new Scanner(System.in);
  //      representing the output of the line. I think that's all we need.
  public static void main(String[] args) {

    System.out.println("Welcome to the testing release of this BASIC interpreter!");

    while(true) {
      System.out.print("> ");
      String input = in.nextLine();
      heavyLifting(input);

    }
  }

  private static void heavyLifting(String input) {
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
        print(input);
    } else if (keyword.equals("RUN")) {
        //TODO: make it do things
        LLIterator iterator = lines.iterator();
        while (iterator.hasNext()) {
          ListElement le = iterator.next();
          String[] data = le.data.split(" ", 2);
          String command = data[0].toUpperCase();
          if (command.equals("LET")) {
            let(data[1]);
          } else if (command.equals("PRINT")) {
            print(data[1]);
          } else if (command.equals("IF")) {
            ifloop(data[1]);
          } else if (command.equals("FOR")) {
            forloop(data[1], iterator);
          } else if (command.equals("GOTO")) {
            //TODO
            LLIterator iter2 = lines.iterator();
            ListElement next = null;
            while (iter2.hasNext()) {
              next = iterator.next();
              if (next.num == Integer.parseInt(data[1])) {
                break;
              }
            }
            heavyLifting(next.data);
          } else {
            System.out.println("Invalid entry at line " + le.num);
          }
        }

    } else {
      //System.out.println("default case");
      String[] tokens = input.split(" ", 2);
      try {
        Integer.parseInt(tokens[0]);
        lines.insert(input);                  //to insert a line
      } catch (NumberFormatException e) {
        if (tokens[0].equals("LET")) {
          let(tokens[1]);
        } else {
          InfixConverter converter = new InfixConverter(input, vars);
          System.out.println(converter.convert());
        }
      }
    }
  }

  private static void print(String input) {
    String var = input.split(" ", 2)[1];
    InfixConverter con_1 = new InfixConverter(var, vars);
    System.out.println(var + " = " + con_1.convert());
  }

  private static void let(String data) {
    String[] tokens = data.split("=");

    try {
      InfixConverter convert = new InfixConverter(tokens[1].replaceAll("\\s", ""), vars);
      Double num = convert.convert();
      vars.put(tokens[0].replaceAll("\\s", ""), num);
    } catch (NumberFormatException e1) {
      System.out.println("Invalid input, try again.");
    }    
  }

  private static void ifloop(String input) {
    String[] s = input.split(") ");
    String lhs = s[0].replaceAll("(", "");
    String rhs = s[1];

    InfixConverter lhsConverter = new InfixConverter(lhs, vars);

    if (lhsConverter.convert() >= 0.0) {
      heavyLifting(rhs);
    } else {
      return;
    }
  }

  private static void forloop(String input, LLIterator iterator) {
    ArrayList<ListElement> listy = new ArrayList<ListElement>();
    String[] s = input.split(", ");

    String[] expr1 = s[0].replaceAll(" ", "").split("=");
    String expr2 = s[1].replaceAll(" ", "");
    
    String var = expr1[0];
    InfixConverter expr1Converter = new InfixConverter(expr1[1], vars);
    Double varNum = expr1Converter.convert();
    vars.put(var, varNum);
    InfixConverter expr2Converter = new InfixConverter(expr2, vars);
    Double condition = expr2Converter.convert();

    while (iterator.hasNext() && iterator.peek().data.split(" ")[0].toUpperCase() != "NEXT") {
      listy.add(iterator.next());
    }

    for (Double i = varNum; i <= condition; i++) {
      for (int j = 0; j < listy.size(); j++) {
        heavyLifting(listy.get(j).data);
      }
      vars.put(var, i);
    }

    iterator.next(); //takes care of the NEXT command.
  }

}