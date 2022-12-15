package CryptarithmetixPuzzle.solver;

import CSP.*;
import CryptarithmetixPuzzle.constraints.*;
import CryptarithmetixPuzzle.simpleBacktracking.Backtracking;
import Util.PuzzleParser;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CryptharihmeticPuzzle extends CSP {
  /**
   * This class contains multiple methods that help solving a Cryptarithmetic puzzle.
   * All solve methods display some statistics as well.
   * Methods included:
   * - AC3
   * - simple BT
   * - forward checking BT
   */
  // color declarations for printing in console
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";
  // the puzzle to be solved
  private String puzzle;

  public static List<Variable> collectVariables(String puzzle) {
    /**
     * This function collects the variables for the CSP
     * INPUT: the puzzle string
     * OUTPUT: a list of variables containing the unique letters from the puzzle
     */
    // parsing the puzzle to get the unique letters
    PuzzleParser parser = new PuzzleParser(puzzle);
    ArrayList<String>uniqueLetters = parser.parsePuzzle();
    // getting the variables
    List<Variable> variables = new ArrayList<Variable>();

    for (String letter: uniqueLetters) {
      variables.add(new Variable(letter));
    }
    return variables;
  }

  private void generateTheDomain(){
    /**
     * This function generates the domain of the CSP.
     */
    // defining the domains for the digits and for the carries
    // each digit has all the values 0-9, but the carries can only be 0 or 1
    Object[] digits = new Object[9];
    Object[] carryDigits = new Object[2];
    for (int i = 0; i < 9; i++) {
      digits[i] = i;
      if(i<2){
        carryDigits[i] = i;
      }
    }
    Domain numbers = new Domain(digits);
    Domain carries = new Domain(carryDigits);

    // assigning the domain to each variable, depending on its name
    // if the name of a variable contains a digit, it is a carry.
    Pattern p = Pattern.compile("\\d+");
    for (Variable var : getVariables()) {
      Matcher m = p.matcher(var.getName());
      if(m.find()){
        setDomain(var, carries);
      }else {
        setDomain(var, numbers);
      }
    }
  }

  private Variable getVariable(String name){
    /**
     * This function returns the variable with the given name from the CSP.
     * INPUT: the variable name
     * OUTPUT: the variable with the given name from the CSP
     */
    return this.getVariables().stream().
        filter(var -> var.getName().equals(name)).collect(Collectors.toList()).get(0);
  }

  private void getListOfConstraints(ArrayList<String> puzzleWords){
    /**
     * This function generates the constraints for our cryptarithmetic puzzle.
     */
    // reverse the puzzle words
    StringBuilder word1 = new StringBuilder();
    word1.append(puzzleWords.get(0));
    word1.reverse();
    StringBuilder word2 = new StringBuilder();
    word2.append(puzzleWords.get(1));
    word2.reverse();
    StringBuilder word3 = new StringBuilder();
    word3.append(puzzleWords.get(2));
    word3.reverse();

    // the number of carries in a sum is equal to the number of digits
    // in the total value - 2
    int noOfCarries = word3.length() - 2;
    /**
     * After we reversed the words of the puzzle, now it looks like this:
     * OWT +  <-- first word
     * OWT    <-- second word
     * ----
     * ROUF   <-- third word
     * We assume that the third word (i.e. the sum result) is the longest word.
     */
    // in carryNumber we keep the current carry that we are processing
    int carryNumber = 1;
    for(int i=0; i<word3.length(); i++) {
      /* Ee get the current letter form each word. It is possible that we will have an
      index that is larger than some words' length, and we need to treat this case
       */
      Variable word1Letter = null;
      Variable word2Letter = null;
      // we will always have the index in bounds for the third word
      Variable word3Letter = getVariable(Character.toString(word3.charAt(i)));
      // initializing the variable scope for the current constraint
      ArrayList<Variable> variableScope = new ArrayList<>();
      variableScope.add(word3Letter);

      if(i<word1.length()){
        // and letter from word1 to scope only if i is in bounds for word1
        String letter = Character.toString(word1.charAt(i));
        word1Letter = getVariable(letter);
        if(!variableScope.contains(word1Letter)){
          variableScope.add(word1Letter);
        }
      }
      if(i<word2.length()){
        // and letter from word2 to scope only if i is in bounds for word2
        String letter = Character.toString(word2.charAt(i));
        word2Letter = getVariable(letter);
        if(!variableScope.contains(word2Letter)){
          variableScope.add(word2Letter);
        }
      }

      // we add the carries to the variableScope
      if(carryNumber > noOfCarries){
        variableScope.add(getVariable("c" + noOfCarries));
      }else {
        for (int j = 1; j <= carryNumber; j++) {
          variableScope.add(getVariable("c" + j));
        }
      }

      //define the current constraint
      Constraint constraint;
      if(i == word3.length()){
        if(word1Letter==null && word2Letter==null) {
          // case TO + TO = FOR. When we get to F, we only need to add the value of F + a possible carry
          constraint = new OneLetterOneCarryConstraint(word3Letter, getVariable("c" + noOfCarries));
        }else{
          // case 213 + 93 = 306, or 93 + 213 = 306. Here we need to add the carry to the remaining
          // digit from the left side, which it needs to equal the remaining digit form the right side
          Variable lambdaLetter = word1Letter == null ? word2Letter : word1Letter;
          constraint =  new TwoLettersOneCarry(word3Letter, lambdaLetter,getVariable("c" + noOfCarries) );
        }
      }else {
        if (carryNumber == 1) {
          // if we are at the first carry, we only need to take into account one carry number in the constraint
          int lambdaCarry = carryNumber;
          constraint = new ThreeLettersOneCarry(word1Letter, word2Letter, word3Letter, getVariable("c" + lambdaCarry));
        } else {
          // if the carry is different from noOfCarries, we need to take into account noOfCarries carries
          // when we get to higher indexes in the final word sum, we may not have a letter from the first 2 words
          // we need to treat this here also. ex: 291 + 9 = 300, or 9 + 291 = 300
          int carry = Math.min(carryNumber, noOfCarries);
          int lambdaCarry1 = carry - 1;
          if (word1Letter != null && word2Letter != null) {
            constraint = new ThreeWordsTwoCarries(word1Letter, word2Letter, word3Letter, getVariable("c" + lambdaCarry1), getVariable("c" + carry));
          } else {
            if (word1Letter == null && word2Letter == null) {
              constraint = new OneLetterOneCarryConstraint(word3Letter, getVariable("c"+carry));
            } else {
              Variable lambdaLetter = word1Letter == null ? word2Letter : word1Letter;
              constraint = new TwoLettersTwoCarries(lambdaLetter, word3Letter, getVariable("c" + lambdaCarry1), getVariable("c" + carry));
            }
          }
        }
      }

      this.addConstraint(constraint);
      carryNumber+=1;
    }
    // after the sum constraints are processed, we add the constraint involving all the variables
    this.addConstraint(new AllVarsConstraint(getVariables()));
  }

  public CryptharihmeticPuzzle(String puzzle) {
    super(collectVariables(puzzle));
    this.puzzle = puzzle;
    generateTheDomain();
    getListOfConstraints(PuzzleParser.getWordsFromPuzzle(puzzle));
  }

  private void printSolutions(List<Assignment> solutions){
    /**
     * This function displays the solutions of a puzzle.
     * INPUT: the list with solutions
     */
    if(solutions.size() != 0){
      for(Assignment solution: solutions) {
        solution.printSolution(puzzle);
      }
      System.out.println(ANSI_GREEN + "No. of solutions: "+ solutions.size() + ANSI_RESET);
    }
    else {
      System.out.println(ANSI_RED + "No solution found for this puzzle." + ANSI_RESET);
    }
  }

  public void solveAC3() {
    /**
     * This function solves the current cryptarithmetic puzzle using AC3.
     */
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    CSPSolver solver = new CSPSolver();
    List<Assignment> solutions = solver.solveWithAC3(this.copy());
    printSolutions(solutions);
    usedTimeMemory(startTime, runtime);
  }

  public void solveFC() {
    /**
     * This function solves the current cryptarithmetic puzzle using forward checking.
     */
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    CSPSolver solver = new CSPSolver();
    List<Assignment> solutions = solver.solveWithFC(this.copy());
    printSolutions(solutions);
    usedTimeMemory(startTime, runtime);
  }

  public void solveSimpleBT(){/**
   * This function solves the current cryptarithmetic puzzle using a simple backtracking algorithm.
   */
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    Backtracking backtracking = new Backtracking();
    Integer noOfSolutions = backtracking.solve(puzzle);
    if(noOfSolutions == 0) {
      System.out.println(ANSI_RED + "No solution found for this puzzle." + ANSI_RESET);
    }else{
      System.out.println(ANSI_GREEN + "No. of solutions: "+ noOfSolutions + ANSI_RESET);
    }
    this.usedTimeMemory(startTime, runtime);
  }
}