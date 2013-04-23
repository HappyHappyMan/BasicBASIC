package Final;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class InfixConverter {

  ArrayDeque<String> tokenStack;
  ArrayDeque<String> doubleQueue;
  String line;
  String[] operators = {"+", "-", "*", "/"};
  Dictionary hash;
  StringTokenizer tokenizer;

  public InfixConverter(String input, Dictionary hashtable) {
    //System.out.println("input is " + input);
    tokenizer = new StringTokenizer(input, " +-*/()", true);
    tokenStack = new ArrayDeque<String>();
    doubleQueue = new ArrayDeque<String>();
    hash = hashtable;

  }

public void convertExprToPostfix() {
    String eval;
    ArrayDeque<String> tempQueue = new ArrayDeque<String>();
    //ArrayDeque<String> tokenStack = new ArrayDeque<String>();

    while (tokenizer.hasMoreTokens()) {
      tempQueue.addLast(tokenizer.nextToken());
    }

    while (!tempQueue.isEmpty()) {
      eval = tempQueue.removeFirst();
      //System.out.println("eval is " + eval);
      if (eval.equals(" "))
        continue;

      if (eval.equals("(")) {                                                        // if token is a ({
        tokenStack.push(eval);
      } else if (Arrays.asList(operators).contains(eval)) {                            // if the token is an operator
        //System.out.println("We have found an operator."); 
        if (tokenStack.isEmpty() || compareOperators(eval, tokenStack.peek()) > 0) { // if stack is empty, or the if the priority of the stack has a lower priority than that of the token
          tokenStack.push(eval);
        } else {
            do {
              if (tokenStack.isEmpty())
                break;
              if (tokenStack.peek().equals("("))
                break;
              if (compareOperators(eval, tokenStack.peek()) > 0)
                break;
              doubleQueue.add(tokenStack.pop());
              //System.out.println("tokenStack is " + tokenStack);
            } while(true);

          tokenStack.push(eval);
        }
      } else if (isStringNumeric(eval)) {                                              // if it is a constant, enqueue{
        //System.out.println(eval);
        doubleQueue.add(eval);  
      } else {
        //System.out.println("we have variable!");
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
      //System.out.println("queue holds " + doubleQueue);
      //System.out.println("stack holds " + tokenStack);
    }

    while (!tokenStack.isEmpty()) {
      doubleQueue.add(tokenStack.pop());
    }

    //System.out.println(doubleQueue);
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
    Object[] input = null;

    input = doubleQueue.toArray();

    for (int i = 0; i < input.length; i++) {
      Object eval = input[i];

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
      } else if (isStringNumeric((String) eval)) {
        numbers.push(Double.parseDouble((String) eval));
      }
    }

    return numbers.pop();
  }

  public Double convert() {
    convertExprToPostfix();
    return evalPostFix();
  }
}