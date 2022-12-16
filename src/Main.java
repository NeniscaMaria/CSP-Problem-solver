import CSP.CSP;
import CryptarithmetixPuzzle.solver.CryptharihmeticPuzzle;
import Util.PuzzleParser;
import ZebraPuzzle.solver.ZebraPuzzleCSP;

import java.util.Scanner;

public class Main {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  private static String puzzle;

  private static String readChoice() {
    // Reads the puzzle from the keyboard and returns it.
    Scanner myObj = new Scanner(System.in);
    System.out.print("1. Cryptarithmetic puzzle \n");
    System.out.print("2. Zebra puzzle \n");
    System.out.print("3. Exit \n");
    System.out.print("Choice: ");
    return myObj.nextLine();
  }

  private static String readPuzzle() {
    // Reads the puzzle from the keyboard and returns it.
    Scanner myObj = new Scanner(System.in);
    System.out.println("Some cryptarithemtic puzzle examples:");
    System.out.println("to + to = for");
    System.out.println("cp + is = fun");
    System.out.println("two + two = four");
    System.out.println("odd + odd = even");
    System.out.println("usa + ussr = peace");
    System.out.print("Enter puzzle or 'x' for back to menu: ");
    return myObj.nextLine();
  }

  private static void solveUsingBackTracking(CSP solver){
    System.out.println(ANSI_PURPLE + "-------------------");
    System.out.println("Simple Backtracking");
    System.out.println("-------------------" + ANSI_RESET);
    solver.solveSimpleBT();
  }

  private static void solveUsingAC3BackTracking(CSP solver){
    System.out.println(ANSI_PURPLE + "----------------------");
    System.out.println("AC3 + Forward checking");
    System.out.println("----------------------" + ANSI_RESET);
    solver.solveAC3();
  }

  private static void solveUsingFCBackTracking(CSP solver){
    System.out.println(ANSI_PURPLE + "-----------------------------");
    System.out.println("Forward Checking Backtracking");
    System.out.println("-----------------------------" + ANSI_RESET);
    solver.solveFC();
  }

  private static void cryptarithmeticPuzzle(){
    // reading the puzzle from the keyboard
    boolean isPuzzleValid;
    while (true) {
      puzzle = readPuzzle().toLowerCase();
      if (puzzle.equals("x")) {
        break;
      }
      isPuzzleValid = PuzzleParser.validatePuzzle(puzzle);
      if (!isPuzzleValid) {
        System.out.println("Invalid puzzle. Try again!");
      } else {
        CryptharihmeticPuzzle solver = new CryptharihmeticPuzzle(puzzle);
        solver.displayGraphSize();
        solveUsingBackTracking(solver);
        solveUsingAC3BackTracking(solver);
        solveUsingFCBackTracking(solver);
      }
    }
  }

  private static void zebraPuzzle() {
    ZebraPuzzleCSP solver = new ZebraPuzzleCSP();
    solver.displayGraphSize();
    solveUsingBackTracking(solver);
    solveUsingAC3BackTracking(solver);
    solveUsingFCBackTracking(solver);
  }

  public static void main(String[] args) {
    boolean finished = false;
    while (!finished) {
      String choice = readChoice().toLowerCase();
      switch (choice) {
        case "1" -> cryptarithmeticPuzzle();
        case "2" -> zebraPuzzle();
        case "3" -> finished = true;
        default -> System.out.println("Invalid choice");
      }
    }
  }
}
