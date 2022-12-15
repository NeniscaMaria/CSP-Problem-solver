package ZebraPuzzle.solver;
import CSP.*;
import CryptarithmetixPuzzle.simpleBacktracking.Backtracking;
import ZebraPuzzle.constraints.*;
import ZebraPuzzle.constraints.AllVarsConstraint;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ZebraPuzzleCSP extends CSP{
  /**
   * This class models a solver for the following zebra puzzle:
   * There are five houses.
   * The English man lives in the red house.
   * The Swede has a dog.
   * The Dane drinks tea.
   * The green house is immediate to the left of the white house.
   * They drink coffee in the green house.
   * The man who smokes Pall Mall has birds.
   * In the yellow house they smoke Dunhill.
   * In the middle house they drink milk.
   * The Norwegian lives in the first house.
   * The man who smokes Blend lives in the house next to the house with cats.
   * In a house next to the house where they have a horse, they smoke Dunhill.
   * The man who smokes Blue Master drinks beer.
   * The German smokes Prince.
   * The Norwegian lives next to the blue house.
   * They drink water in a house next to the house where they smoke Blend.
   */
  // defining some colors for printing to console
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";
  // defining the puzzle constants
  private static final ArrayList<Object> orders = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
  private static final ArrayList<String> nations = new ArrayList<>(Arrays.asList("English", "Danish", "German", "Swedish", "Norwegian"));
  private static final ArrayList<String> animals = new ArrayList<>(Arrays.asList("Zebra", "Horse", "Birds", "Dog", "Cats"));
  private static final ArrayList<String> drinks = new ArrayList<>(Arrays.asList("Coffee", "Tea", "Beer", "Water", "Milk"));
  private static final ArrayList<String> cigarettes = new ArrayList<>(Arrays.asList("Pall Mall", "Blend", "Blue Master", "Prince", "Dunhill"));
  private static final ArrayList<String> colors = new ArrayList<>(Arrays.asList("Red", "Green", "White", "Blue", "Yellow"));
  // a dictionary that maps each entity name (nations, animals, drinks, cigarettes, colors to its notation
  private static HashMap<String, String> nameMapping = new HashMap<>();
  // a dictionary that maps each notation to the name of the entity
  private static HashMap<String, String> notationMapping = new HashMap<>();

  private static List<Variable> collectVariables() {
    /**
     * This function collects all the variables for the zebra CSP.
     * Each entity is mapped to a notation. Example:
     * - English - N1
     * - Danish - N2,
     * - Zebra - A1
     * - Horse - A2
     * - and so on
     */
    List<Variable> variables = new ArrayList<Variable>();
    for(int i=0; i<nations.size(); i++){
      nameMapping.put(nations.get(i), "N"+i);
      notationMapping.put("N"+i, nations.get(i));
      variables.add(new Variable("N"+i));
    }
    for(int i=0; i<animals.size(); i++){
      nameMapping.put(animals.get(i), "A"+i);
      notationMapping.put("A"+i, animals.get(i));
      variables.add(new Variable("A"+i));
    }
    for(int i=0; i<drinks.size(); i++){
      nameMapping.put(drinks.get(i), "D"+i);
      notationMapping.put("D"+i, drinks.get(i));
      variables.add(new Variable("D"+i));
    }
    for(int i=0; i<cigarettes.size(); i++){
      nameMapping.put(cigarettes.get(i), "T"+i);
      notationMapping.put("T"+i, cigarettes.get(i));
      variables.add(new Variable("T"+i));
    }
    for(int i=0; i<colors.size(); i++){
      nameMapping.put(colors.get(i), "C"+i);
      notationMapping.put("C"+i, colors.get(i));
      variables.add(new Variable("C"+i));
    }

    return variables;
  }

  private void generateTheDomain(){
    /**
     * This function generates the domain of the variables.
     * Each variable can be assigned only the values from the orders list.
     * This means that each entity can be assigned to any house (at the beginning).
     */
    Domain orders = new Domain(this.orders);
    for (Variable var : getVariables()) {
      setDomain(var, orders);
    }
  }



  private void generateListOfConstraints(){
    /**
     * Generating the list of constraints based on the puzzle definition.
     */
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("English"), nameMapping.get("Red")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Swedish"), nameMapping.get("Dog")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Danish"), nameMapping.get("Tea")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Green"), nameMapping.get("Coffee")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Pall Mall"), nameMapping.get("Birds")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Dunhill"), nameMapping.get("Yellow")));
    this.addConstraint(new VariableOrderConstraint(3, nameMapping.get("Milk")));
    this.addConstraint(new VariableOrderConstraint(1, nameMapping.get("Norwegian")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Blue Master"), nameMapping.get("Beer")));
    this.addConstraint(new TwoVariableConstraint(nameMapping.get("Prince"), nameMapping.get("German")));
    this.addConstraint(new VariableOrderConstraint(2, nameMapping.get("Blue")));
    this.addConstraint(new AllVarsConstraint(this.getVariables()));
  }

  public ZebraPuzzleCSP() {
    super(collectVariables());
    generateTheDomain();
    generateListOfConstraints();
  }

  public void solveSimpleBT(){
    /**
     * This function solves the zebra puzzle using a simple backtracking algorithm.
     */
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    CSPSolver solver = new CSPSolver();
    List<Assignment> solutions = solver.solveWithSimpleBT(this.copy());
    printSolution(solutions);
    usedTimeMemory(startTime, runtime);
  }

  public void solveAC3() {
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    CSPSolver solver = new CSPSolver();
    List<Assignment> solutions = solver.solveWithAC3(this.copy());
    printSolution(solutions);
    usedTimeMemory(startTime, runtime);
  }

  public void solveFC() {
    double startTime = System.currentTimeMillis();
    Runtime runtime = Runtime.getRuntime();
    CSPSolver solver = new CSPSolver();
    List<Assignment> solutions = solver.solveWithFC(this.copy());
    printSolution(solutions);
    usedTimeMemory(startTime, runtime);
  }

  private void printLine(Integer houseNo, List<String> resultForHouse){
    System.out.println(houseNo + "   - " +
        resultForHouse.get(0) + " - " +
        resultForHouse.get(1) + " - " +
        resultForHouse.get(2) + " - " +
        resultForHouse.get(3) + " - " +
        resultForHouse.get(4));
  }

  private void printSolution(List<Assignment> solutions){
    if(solutions.size() == 0) {
      System.out.println(ANSI_RED + "No solution found for this puzzle." + ANSI_RESET);
    }else{
      System.out.println(ANSI_GREEN + "Total no. of solutions found: "+solutions.size() + ANSI_RESET);
      System.out.println("Displaying one of them: ");
      Assignment solution = solutions.get(0);
      System.out.println("No. -  Pet  - Color - Drink -  Nation  - Cigarettes");
      System.out.println("---------------------------------------------------");
      for(int i=1; i<=orders.size(); i++){
        int houseNo = i;
        List<Map.Entry<Variable, Object>> characteristicsOfHouse = solution.getVariableToValue()
            .entrySet().stream()
            .filter(entry -> entry.getValue().equals(houseNo)).collect(Collectors.toList());
        List<String> entities = characteristicsOfHouse.stream()
            .map(entry -> entry.getKey().getName()).sorted().collect(Collectors.toList());
        List<String> resultForHouse = entities.stream().map(a -> notationMapping.get(a)).collect(Collectors.toList());
        printLine(houseNo, resultForHouse);
      }
    }

  }


}
