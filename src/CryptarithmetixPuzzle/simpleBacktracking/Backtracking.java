package CryptarithmetixPuzzle.simpleBacktracking;

import CryptarithmetixPuzzle.simpleBacktracking.utils.Node;
import CryptarithmetixPuzzle.simpleBacktracking.utils.PuzzleParser;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
  /**
   * This class contains the logic for solving a cryptarithmetic puzzle
   * using simple backtracking.
   */
  // the puzzle we want to solve
  private String puzzle;
  // the list of letter nodes we need to assign values to
  private ArrayList<Node> letterNodes;

  private int findFirstLetterUnsolved() {
    /** This function searches for the first letter that does not have a digit assigned to it
     * OUTPUT: the index at which this letter can be found
     *         -1, otherwise
     */
    for (int i = 0; i < letterNodes.size(); i++) {
      if (letterNodes.get(i).getValue() == -1) {
        return i;
      }
    }
    return -1;
  }

  private boolean checkForDigitUniqueness(int digit){
    /**
     * This function check if there is another letter with the same digit.
     * We perform this check because we cannot have 2 letters with the same digit assigned.
     * OUTPUT: True, if there is a no letter with the given digit
     *         False, otherwise
     */
    return letterNodes.stream().noneMatch(node -> digit == node.getValue());
  }

  private boolean assignLetterToDigit(int nodeIndex, int digit) {
    /**
     * This function tries to assign a digit to a letter
     * INPUT: nodeIndex = the index at which the node we want to update is found in letterNodes
     *        digit = the digit we want to assign
     * OUTPUT: True, if we managed to assign the digit
     *         False, if the digit is invalid <0, or of there is already a letter with this digit
     */
    if (digit < 0) return false;
    boolean isValidDigit = checkForDigitUniqueness(digit);

    if(isValidDigit) {
      letterNodes.get(nodeIndex).setValue(digit);
    }
    return isValidDigit;
  }

  private int findDigitAssignedToLetter(char letter){
    /**
     * This function finds the digit assigned to a letter
     * INPUT: letter = the letter we want to find the digit associated to it
     * OUTPUT: digit = the digit assigned to the letter
     */
    return letterNodes.stream()
            .filter(node -> letter == node.getChar())
            .findAny()
            .orElse(new Node()).getValue();
  }

  private boolean checkSolution() {
    /**
     * This function check if the generated solution is valid or not
     * OUTPUT: True, if the solution is valid
     *         False, otherwise
     */
    // initialize the variables
    int firstSumNumber = 0;
    int secondSumNumber = 0;
    int totalSum = 0;

    // we use the below flags to signal if we passed a "+" sign or a '=' sign
    boolean triggerPlus = false;
    boolean triggerEqual = false;

    // we iterate over the puzzle string and compute the 3 numbers of the sum
    for (int i = 0; i < puzzle.length(); i++) {
      char currentLetter = puzzle.charAt(i);
      switch (currentLetter){
        case '+':
          triggerPlus = true;
          break;
        case '=':
          triggerEqual = true;
          break;
        case ' ':
          // if we encounter a space, we do nothing
          break;
        default:
          int assignedDigit = findDigitAssignedToLetter(currentLetter);
          if (assignedDigit == -1) {
            System.out.println("-1 Found");
            return false;
          }
          if (triggerEqual) {
            totalSum = totalSum*10 + assignedDigit;
          }
          else if (triggerPlus) {
            secondSumNumber = secondSumNumber*10 + assignedDigit;
          }
          else {
            firstSumNumber = firstSumNumber*10 + assignedDigit;
          }
          break;
      }
    }
    // After assigning all the numbers, we validate the arithmetic solution
    if (firstSumNumber + secondSumNumber == totalSum) {
      System.out.println(firstSumNumber + " + " + secondSumNumber + " = " + totalSum);
      return true;
    }
    return false;
  }

  public List<List<Node>> runBacktracking() {
    /**
     * This function runs the backtracking to solve the puzzle
     * and prints any solutions found.
     * OUTPUT: A list of solutions
     */
    List<List<Node>> solutions = new ArrayList<>();

    // we search for the first letter that does not have a value assigned to it
    int nodeIndex = findFirstLetterUnsolved();
    // if no such letter was found, we check the solution because this means that all letters have a value
    if (nodeIndex == -1) {
      if (checkSolution()) {
        // If the solution is valid, add it to the list of solutions
        solutions.add(new ArrayList<>(letterNodes));
      }
      return solutions;
    }
    // We start assigning each digit to each the letter and try to solve.
    for (int digit = 0; digit <= 10; digit++) {
      if (assignLetterToDigit(nodeIndex, digit)) {
        // Recursively search for more solutions
        solutions.addAll(runBacktracking());
        // If a solution is found, un-assign the value from the letter
        this.letterNodes.get(nodeIndex).setValue(-1);
      }
    }
    return solutions;
  }

  public Integer solve(String puzzle){
    /**
     * This function begins the solving of a given puzzle.
     * INPUT: puzzle = the string containing the cryptarithmetic puzzle
     * OUTPUT: no of solutions found
     */
    this.puzzle = puzzle;
    // parse the puzzle to obtain the list of letter nodes
    PuzzleParser parser = new PuzzleParser(puzzle);
    this.letterNodes = parser.parsePuzzle();
    return runBacktracking().size();
  }
}
