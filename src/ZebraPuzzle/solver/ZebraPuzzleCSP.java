package ZebraPuzzle.solver;
import CSP.*;
import CryptarithmetixPuzzle.constraints.*;
import ZebraPuzzle.constraints.*;
import ZebraPuzzle.constraints.AllVarsConstraint;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ZebraPuzzleCSP extends CSP{
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RED = "\u001B[31m";
  private static final ArrayList<Object> orders = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
  private static final ArrayList<String> nations = new ArrayList<>(Arrays.asList("English", "Danish", "German", "Swedish", "Norwegian"));
  private static final ArrayList<String> animals = new ArrayList<>(Arrays.asList("Zebra", "Horse", "Birds", "Dog", "Cats"));
  private static final ArrayList<String> drinks = new ArrayList<>(Arrays.asList("Coffee", "Tea", "Beer", "Water", "Milk"));
  private static final ArrayList<String> cigarettes = new ArrayList<>(Arrays.asList("Pall Mall", "Blend", "Blue Master", "Prince", "Dunhill"));
  private static final ArrayList<String> colors = new ArrayList<>(Arrays.asList("Red", "Green", "White", "Blue", "Yellow"));
  private static HashMap<String, String> nameMapping = new HashMap<>();
  private static HashMap<String, String> notationMapping = new HashMap<>();
  private Assignment assignments = new Assignment();

  private static List<Variable> collectVariables() {
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
    Domain orders = new Domain(this.orders);
    for (Variable var : getVariables()) {
      setDomain(var, orders);
    }
  }



  private void generateListOfConstraints(){
    this.addConstraint(new NationColor(nameMapping.get("English"), nameMapping.get("Red")));
    this.addConstraint(new NationAnimal(nameMapping.get("Swedish"), nameMapping.get("Dog")));
    this.addConstraint(new NationDrink(nameMapping.get("Danish"), nameMapping.get("Tea")));
    this.addConstraint(new ColorDrink(nameMapping.get("Green"), nameMapping.get("Coffee")));
    this.addConstraint(new AnimalsCigarettes(nameMapping.get("Pall Mall"), nameMapping.get("Birds")));
    this.addConstraint(new ColorCigarettes(nameMapping.get("Dunhill"), nameMapping.get("Yellow")));
    this.addConstraint(new OrderDrink(3, nameMapping.get("Milk")));
    this.addConstraint(new NationOrder(1, nameMapping.get("Norwegian")));
    this.addConstraint(new DrinkCigarettes(nameMapping.get("Blue Master"), nameMapping.get("Beer")));
    this.addConstraint(new NationCigarettes(nameMapping.get("Prince"), nameMapping.get("German")));
    this.addConstraint(new OrderColor(2, nameMapping.get("Blue")));
    this.addConstraint(new AllVarsConstraint(this.getVariables()));


  }

  private void assignVar(String varName, Object value){
    assignments.setAssignment(new Variable(varName), value);

  }


  public ZebraPuzzleCSP() {
    super(collectVariables());
    generateTheDomain();
    generateListOfConstraints();
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
