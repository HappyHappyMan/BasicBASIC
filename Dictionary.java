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

      byte byte1 = (byte) letter1;
      byte byte2 = (byte) letter2;

      byte1 = (byte) (byte1 << 8); 
      int orredBytes = (byte1 | byte2); 
      total = total + orredBytes;
    }

    total--;
    return total;
  }

  public Double get(String s) {
    int hash = hash(s);
    int key = hash % data.length;

    while (data[hash] != null && data[hash].key != key) {
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
  int key;

  public HashVar(int key, Double value) {
    this.value= value;
    this.key = key;
  }
}