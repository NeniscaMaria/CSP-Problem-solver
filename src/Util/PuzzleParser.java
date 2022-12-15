package Util;

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

  public ArrayList<String> parsePuzzle() {
    /** This function takes the puzzle we want to solve and extracts the unique letters
     * OUTPUT: the list with unique letters from the puzzle
     */

    ArrayList<String> letters= new ArrayList<>();
    for (int i=0; i<puzzle.length(); i++) {
      String letter = Character.toString(puzzle.charAt(i));
      if (isValidLetter(puzzle.charAt(i)) && !letters.contains(letter)){
        letters.add(letter);
      }
    }

    ArrayList<String> words = getWordsFromPuzzle(puzzle);
    Integer noOfCarries = words.get(2).length() - 2;
    for(int i=1; i<=noOfCarries; i++){
      letters.add("c"+i);
    }
    return letters;
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
    /**
     * This function validates a given puzzle. It checks that the first 2 words have a valid length (!=0)
     * and that the sum word length is not less than max of the first 2 words and that it is not greater than
     * that max +1
     * INPUT: the puzzle string
     * OUTPUT: True, if it is valid. False, otherwise
     */
    ArrayList<String> words = getWordsFromPuzzle(puzzle);
    int word1Len = words.get(0).length();
    int word2Len = words.get(1).length();
    int word3Len = words.get(2).length();
    int maxLengthRightSide = Math.max(word1Len, word2Len);
    return word3Len <= maxLengthRightSide+1 && word1Len != 0 && word2Len != 0 && word3Len != 0;
  }

}
