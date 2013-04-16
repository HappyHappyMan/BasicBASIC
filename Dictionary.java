package Final;

public class Dictionary {

  private HashVar[] data;


  public Dictionary() {
    data = new HashVar[103];

    for (int i = 0; i < data.length; i++) {
      data[i] = null;
    }

  }

  public void put(String s, Double d) {

    int hash = hash(s);
    int key = hash % data.length;

    if (data[key].equals(null)) {
      data[key] = new HashVar(hash, d);
    } else {
      HashVar hashElement = data[key];
      while (hashElement.link != null && hashElement.hash != hash) {
        hashElement = hashElement.link;
      }
      if (hashElement.hash == hash) { //it should compare ints properly.
        HashVar temp = hashElement.link;
        hashElement = new HashVar(hash, d);
        hashElement.link = temp;
      } else {
        hashElement.link = new HashVar(hash, d);
      }
    }
  }

  private int hash(String s) {
    int j = 0;
    int total = 0;

    if (s.length() % 2 == 0) {
      j = s.length();
    } else {
      j = s.length() - 1;
    }

    for (int i = 0; i < j; i=i+2) {
      char letter1 = s.charAt(i);
      char letter2 = s.charAt(i+1);

      int num1 = (int) letter1;
      int num2 = (int) letter2;

      num1 = num1 - 8; 
      int orredBytes = num1 + num2; 
      total = total + orredBytes;
    }

    total--;
    return total;
  }

  public Double get(String s) {
    int hash = hash(s);
    int key = hash % data.length;

    while (data[key] != null && data[key].hash != hash) {
      hash = (hash + 1) % data.length;
    }
    if (data[hash] == null) {
      return -1.0;
    } else {
      return data[hash].value;
    }
  }
}

class HashVar {

  Double value;
  int hash;
  HashVar link;

  public HashVar(int hash, Double value) {
    this.value= value;
    this.hash = hash;
    link = null;
  }
}