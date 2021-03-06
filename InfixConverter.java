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
    tokenizer = new StringTokenizer(input, " +-*/()", true);
    tokenStack = new ArrayDeque<String>();
    doubleQueue = new ArrayDeque<String>();
    hash = hashtable;

  }

  /**
   * Converts an infix expression to postfix by using queues and stacks.
   */
  private void convertExprToPostfix() {
    String eval;
    ArrayDeque<String> tempQueue = new ArrayDeque<String>();

    while (tokenizer.hasMoreTokens()) {
      tempQueue.addLast(tokenizer.nextToken());
    }

    while (!tempQueue.isEmpty()) {
      eval = tempQueue.removeFirst();
      if (eval.equals(" "))
        continue;

      if (eval.equals("(")) {                                                        
        tokenStack.push(eval);
      } else if (Arrays.asList(operators).contains(eval)) {                          
        if (tokenStack.isEmpty() || compareOperators(eval, tokenStack.peek()) > 0) { 
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
            } while(true);

          tokenStack.push(eval);
        }
      } else if (isStringNumeric(eval)) {
        doubleQueue.add(eval);  
      } else {
        doubleQueue.add(Double.toString(hash.get(eval)));
      }

      if (!tokenStack.isEmpty() && tempQueue.peek() != null) {
        if (tempQueue.peek().equals(")")) {
          while (!tokenStack.isEmpty() && !tokenStack.peek().equals("(")) {
            doubleQueue.add(tokenStack.pop());
          }
          tokenStack.pop(); 
        }
      }
    }

    while (!tokenStack.isEmpty()) {
      doubleQueue.add(tokenStack.pop());
    }
  }

  /**
   * Determines whether a string represents a number or not.
   * @param  eval String to check
   * @return      boolean
   */
  private boolean isStringNumeric(String eval) {
    try {
      Double.parseDouble(eval);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Returns -1, 1, or 0 depending on the relative priority of the two
   * operator strings passed in. If op1 is lower priority (order of
   * operations) than op2, returns -1. If greater, 1. Anything else
   * will return 0. Does not check whether parameters are valid, please only
   * pass +,-,*, or / to this, otherwise you WILL get 0 every time.
   * @param  op1 operator 1
   * @param  op2 operator 2
   * @return     int that represents the parameters' relative priority.
   */
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

  /**
   * Evaluates the postfix expression generated by convertInfixToPostfix()
   * and returns a Double object with the answer.
   * @return Result of postfix expression evaluation.
   */
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

  /**
   * Call this to make this class do things.
   * @return result of expression evaluation.
   */
  public Double convert() {
    convertExprToPostfix();
    return evalPostFix();
  }
}