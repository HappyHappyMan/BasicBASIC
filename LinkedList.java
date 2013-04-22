package Final;
import java.util.Iterator;

/**
 * A LinkedList class meant to be used to store lines of BASIC code.
 * Author: Sri Ramanujam
 * Version: 0.01
 */

public class LinkedList implements Iterable<ListElement> {
  ListElement head;
  ListElement tail;
  
/**Constructor generates an empty linked list
 */
  public LinkedList() {
    this.head = new ListElement();
    this.tail = this.head;
  }

  /**
   * Inserts a String at the head of the LL.
   * @param data String to be inserted.
   */
  public void insertAtHead(String data) {
    ListElement temp = this.head.link;
    this.head.link = new ListElement(data);
    this.head.link.link = temp;
    if (temp == null)
      this.tail = temp;
  }

  public LLIterator iterator() {
    return new LLIterator(head);
  }

  /**
   * Inserts a data item after the specified list element
   * @param where The list element after which the data is inserted
   * @param newLE the new list element containing the new data
   */
  private void insertAfter(ListElement where, ListElement newLE) {
    if(where.link == null)
    {
      where.link = newLE;
      tail = where.link;
    }
    else
    {
      ListElement temp = where.link;
      where.link = newLE;
      newLE.link = temp;
    }
  }

  public ListElement getHead() {
    return this.head;
  }

  /**
   * Inserts a ListElement in sorted order.
   * @param val the String to be inserted.
   */
  public void insert(String val) {
    ListElement newLE = new ListElement(val);
    recInsert(newLE, this.head);
  }

  /**
   * Implements insert().
   */
  private void recInsert(ListElement newLE, ListElement where) {
    if (where.link == null) {
      this.insertAfter(where, newLE);
    } else if (newLE.num < where.link.num) {
      this.insertAfter(where, newLE);
    } else if (newLE.num == where.num) {
      where = newLE;
    } else {
      recInsert(newLE, where.link);
    }
  }

  /**
   * Resequences the data to adhere to BASIC standards.
   * Before:
   * > 10 Hello
   * > 15 world
   * > 22 this is arbitrary
   * After:
   * > 10 Hello
   * > 20 world
   * > 30 this is arbitrary
   */
  public void resequence() {
    ListElement change = this.head.link; 
    for (int i = 10; change != null; i=i+10) {
      change.num = i;
      change = change.link;
    }
  }

  /**
   * Inserts a List Element at the tail of the list.
   * @param data the String to be inserted.
   */
  public void insertAtTail(String data) {
    this.tail.link = new ListElement(data);
    tail = this.tail.link;
  }

  /**
   * Checks to see whether the list is empty
   * @return returns true if the list is empty, false otherwise
   */
  public boolean isEmpty()
  {
    return this.head.link == null;
  }

  /**
   * Deletes the data item at the beginning of the list
   * @return Returns the deleted String.
   */
  public String deleteHead()
  {
    String retval = null;
    if (this.isEmpty())
      throw new LLException("Attempt to delete from empty list.");
    else
    {
      retval = this.head.link.data;
      this.head.link = this.head.link.link;
    }
    return retval;
  }

  /**
   * Prints out the elements of the linked list in order.
   * @return A String of all the data elements.
   */
  public String toString()
  {
    String retval = "";
    retval += recprint(head.link, retval);
    return retval;
  }

  /**
   * Implements toString().
   */
  private String recprint(ListElement where, String s)
  {
    if (where == null)
      return s;
    else
    {
      return s + "\n" + recprint(where.link, where.num + " " + where.data.toString());
    }
  }
}

class LLIterator implements Iterator<ListElement> {

  private ListElement le;

  public LLIterator(ListElement head) {
    le = head;
  }

  public LLIterator(LinkedList l) {
    this(l.getHead());
  }

  public boolean hasNext() {
    if (le.link == null) {
      return false;
    }
    return true;
  }

  public ListElement next() {
    le = le.link;
    return le.link;
  }

  public void remove() {

  }
}


//TODO: catch exceptions from Integer.parseInt
class ListElement {
  String data;
  int num;
  ListElement link;

  ListElement() {
    this.num = 0;
    this.data = null;
    this.link = null;
  }

  ListElement(String val) {
    String[] tokens = val.split(" ", 2);
    this.num = Integer.parseInt(tokens[0]);
    this.data = tokens[1];
    this.link = null;
  }

  ListElement(String val, ListElement link) {
    String[] tokens = val.split(" ", 2);
    this.num = Integer.parseInt(tokens[0]);
    this.data = tokens[1];
    this.link = link;
  }
}