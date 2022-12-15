package CryptarithmetixPuzzle.simpleBacktracking.utils;

public class Node {
  /**
   * This class is used to represent a (letter, digit) pair in our simple BT algorithm.
   */
  // the digit value that is assigned to the letter
  private int v;
  // the letter for which we assign a digit
  private char c;

  public Node() {
    this.v = -1;
    this.c = ' ';
  }

  public Node(char character, int value) {
    this.c = character;
    this.v = value;
  }

  public char getChar() {
        return this.c;
    }
  public int getValue() {
        return this.v;
    }

  public void setValue(int v) {this.v = v;}
}
