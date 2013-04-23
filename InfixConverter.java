package Final;

import java.util.ArrayDeque;
import java.util.Arrays;

public class InfixConverter {

  ArrayDeque<String> tokenQueue;
  ArrayDeque<String> doubleQueue;
  String line;
  String[] operators = {"+", "-", "*", "/"};
  String[] tokens;
  Dictionary hash;

  public InfixConverter(String input, Dictionary hashtable) {

    tokens = input.split(" +-*/()");
    tokenQueue = new ArrayDeque<String>(tokens.length);
    hash = hashtable;

  }

public void convertExprToPostfix() {
    String eval;
    ArrayDeque<String> tempQueue = new ArrayDeque<String>();
    ArrayDeque<String> tokenStack = new ArrayDeque<String>();

    for (int i = 0; i < tokens.length; i++) {
      tempQueue.addLast(tokens[i]);
    }

    while (!tempQueue.isEmpty()) {
      eval = tempQueue.removeFirst();
      if (eval.equals("(")) {                                                        // if token is a ({
        tokenStack.push(eval);
      } 

      else if (Arrays.asList(operators).contains(eval)) {                            // if the token is an operator
        if (tokenStack.isEmpty() || compareOperators(eval, tokenStack.peek()) > 0) { // if stack is empty, or the if the priority of the stack has a lower priority than that of the token
          tokenStack.push(eval);
        }
        else {
          do {
            if (tokenStack.isEmpty())
              break;
            if (tokenStack.peek().equals("("))
              break;
            if (compareOperators(eval, tokenStack.peek()) > 0)
              break;
            doubleQueue.add(tokenStack.pop());
          } while(true);

          tokenStack.push(eval);
        }
      }
      else if (isStringNumeric(eval)) {                                              // if it is a constant, enqueue{
        doubleQueue.add(eval);  
      } else {
        doubleQueue.add(Double.toString(hash.get(eval)));                            //Ten bucks this doesn't work
      }

      if (!tokenStack.isEmpty() && tempQueue.peek() != null) {
        if (tempQueue.peek().equals(")")) {
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("(")) {
            doubleQueue.add(tokenStack.pop());
          }
          tokenStack.pop(); 
        }
      }
      System.out.println("queue holds " + doubleQueue);
      System.out.println("stack holds " + tokenStack);
    }

    while (!tokenStack.isEmpty()) {
      doubleQueue.add(tokenStack.pop());
    }

    System.out.println(doubleQueue);
  }

  private boolean isStringNumeric(String eval) {
    try {
      Double.parseDouble(eval);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  private int compareOperators(String op1, String op2) {
    if ( (op1.equals("+") || op1.equals("-")) && ( (op2.equals("*") || op2.equals("/") ) ) ) {
      return -1;
    }
    else if ( (op2.equals("+") || op2.equals("-")) && ( (op1.equals("*") || op1.equals("/") ) ) ) {
      return 1;
    }
    else {
      return 0;
    }
  }

  private Double evalPostFix() {
    ArrayDeque<Double> numbers = new ArrayDeque<Double>();
    Double num1, num2;
    String[] input = null;

    input = doubleQueue.toArray(input);

    for (int i = 0; i < input.length; i++) {
      String eval = input[i];

      if (Arrays.asList(operators).contains(eval)) {
        num2 = numbers.pop();
        num1 = numbers.pop();

        if (eval.equals("+")) {
          numbers.push(num1 + num2);
        } else if (eval.equals("-")) {
          numbers.push(num1 - num2);
        } else if (eval.equals("*")) {
          numbers.push(num1 * num2);
        } else if (eval.equals("/")) {
          numbers.push(num1 / num2);
        }
      } else if (isStringNumeric(eval)) {
        numbers.push(Double.parseDouble(eval));
      }
    }

    return numbers.pop();
  }

  public Double convert() {
    convertExprToPostfix();
    return evalPostFix();
  }
}