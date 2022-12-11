package CryptarithmetixPuzzle.simpleBacktracking.utils;

import CryptarithmetixPuzzle.simpleBacktracking.utils.Node;

import java.util.ArrayList;

public class PuzzleParser {
  /**
   * This class contains the logic for parsing any given cryptarithmetic puzzle.
   */
  // the puzzle to be parsed
  private String puzzle;

  public PuzzleParser(String puzzle) {
    this.puzzle = puzzle;
  }

  private boolean isValidLetter(char c){
    // Determines whether the letter is a valid one in the puzzle or not
    return Character.isLetter(c) && c != ' ' && c != '+' && c != '=';
  }

  private boolean contains(ArrayList<Node> letterNodes, char letter) {
    /**
     * This function checks if an array of letter nodes contains a node with a given letter
     * INPUT: letterNodes = the array of letter nodes in which we need to check
     *        letter = the letter to be found
     * OUTPUT: True, if the given letter is found in letterNodes
     *         False, otherwise
     */
    return letterNodes.stream().anyMatch(node -> letter == node.getChar());
  }

  public ArrayList<Node> parsePuzzle() {
    /** This function takes the puzzle we want to solve and extracts the unique letters into nodes
     * OUTPUT: the list of letter nodes with unique letters from the puzzle
     */

    ArrayList<Node> letterNodes = new ArrayList<>();
    for (int i=0; i<puzzle.length(); i++) {
      char letter = puzzle.charAt(i);
      if (isValidLetter(letter) && !(contains(letterNodes, letter))){
        letterNodes.add(new Node(puzzle.charAt(i), -1));
      }
    }
    return letterNodes;
  }

  public static ArrayList<String> getWordsFromPuzzle(String puzzle) {
    /** This function takes the puzzle we want to solve and extracts the words
     * OUTPUT: the list of the words from the puzzle
     */

    ArrayList<String> words = new ArrayList<>();
    String word1 = puzzle.substring(0, puzzle.indexOf("+"));
    words.add(word1.trim());

    String word2 = puzzle.substring(puzzle.indexOf("+") + 1, puzzle.indexOf("="));
    words.add(word2.trim());

    String sumResult = puzzle.substring(puzzle.indexOf("=") + 1);
    words.add(sumResult.trim());
    return words;
  }

  public static boolean validatePuzzle(String puzzle){
    ArrayList<String> words = getWordsFromPuzzle(puzzle);
    int word1Len = words.get(0).length();
    int word2Len = words.get(1).length();
    int word3Len = words.get(2).length();
    int maxLengthRightSide = Math.max(word1Len, word2Len);
    return word3Len <= maxLengthRightSide+1 && word1Len != 0 && word2Len != 0 && word3Len != 0;
  }

  public String getUniqueLetters(){
    ArrayList<Node> parsed = parsePuzzle();
    String uniques = "";
    for(Node node:parsed){
      uniques+=node.getChar();
    }
    return uniques;
  }
}
