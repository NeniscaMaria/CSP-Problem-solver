package CSP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class CSP {
  /**
   * This function models the representation of a CSP problem.
   */
  // defining some colors for printing to console
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLUE = "\u001B[34m";
  // the list of variables of the CSP
  private List<Variable> variables;
  // the list of variable domains of the CSP
  private List<Domain> domains;
  // obs about the above fields: domain[i] = the domain of variable variables[i]
  // the list of constraints of the CSP
  private List<Constraint> constraints;

  // a dictionary mapping each variable to its index in the variables list
  private Hashtable<Variable, Integer> varIndexHash;
  // a dictionary that maps each variable to a list of constraints that have it in their scope
  private Hashtable<Variable, List<Constraint>> cnet;

  private CSP() {}

  public CSP(List<Variable> vars) {
    // initialize a CSP just from a list of variables and assign empty domains to them
    variables = new ArrayList<>(vars.size());
    domains = new ArrayList<>(vars.size());
    constraints = new ArrayList<>();
    varIndexHash = new Hashtable<>();
    cnet = new Hashtable<>();
    Domain emptyDomain = new Domain(new ArrayList<>(0));
    int index = 0;
    for (Variable var : vars) {
      variables.add(var);
      domains.add(emptyDomain);
      varIndexHash.put(var, index++);
      cnet.put(var, new ArrayList<>());
    }
  }

  public List<Variable> getVariables() {
    return Collections.unmodifiableList(variables);
  }

  public int indexOf(Variable var) {
    return varIndexHash.get(var);
  }

  public Domain getDomain(Variable var) {
    return domains.get(varIndexHash.get(var));
  }

  public void setDomain(Variable var, Domain domain) {
    domains.set(indexOf(var), domain);
  }

  public void removeValueFromDomain(Variable var, Object value) {
    /**
     * This function removes a values from the domain of a variable.
     * INPUT: var = the variable for which we want to modify the domain
     *        value = the value we want to remove
     */
    Domain currDomain = getDomain(var);
    List<Object> values = new ArrayList<>(currDomain.size());
    for (Object v : currDomain)
      if (!v.equals(value))
        values.add(v);
    setDomain(var, new Domain(values));
  }

  public List<Constraint> getConstraints() {return constraints;}

  public List<Constraint> getConstraints(Variable var) {
    /**
     * This function returns all the constraints that have a given variable in their scope.
     * INPUT: var = the variable for which we want to get the constraints
     * OUPUT: a list of constraints that concern the given variable.
     */
    return cnet.get(var);
  }

  public void addConstraint(Constraint constraint) {
    constraints.add(constraint);
    for (Variable var : constraint.getScope()) {
      cnet.get(var).add(constraint);
    }
  }


  public Variable getNeighbor(Variable var, Constraint constraint) {
    /**
     * This function gets the other variable from the scope of a binary constraint.
     * INPUT: var = one variable from the scope of a binary constraint
     *        constraint = the (binary) constraint
     * OUPUT: the other variable from the scope of the constraint, if there is one
     */
    List<Variable> scope = constraint.getScope();
    if (scope.size() == 2) {
      if (var.getName().equals(scope.get(0).getName()))
        return scope.get(1);
      else if (var.getName().equals(scope.get(1).getName()))
        return scope.get(0);
    }
    return null;
  }


  public CSP copy() {
    // creates a copy of the current CSP
    CSP result = new CSP();
    result.variables = variables;
    result.domains = new ArrayList<>(domains.size());
    result.domains.addAll(domains);
    result.constraints = constraints;
    result.varIndexHash = varIndexHash;
    result.cnet = cnet;
    return result;
  }

  public void usedTimeMemory(double startTime, Runtime rt){
    /**
     * This function collects a number of statistics for the run of a CSP algorithm:
     * - the time it took to run the algorithm
     * - the used memory
     * INPUT: startTime =  the start time of the algoritm
     *        rt = a runtime instance
     */
    float mb = (1024*1024);
    //Print used memory
    DecimalFormat df = new DecimalFormat();
    df.setMinimumFractionDigits(3);
    float currentUsedMemory = (rt.totalMemory() - rt.freeMemory())/mb;
    System.out.println(ANSI_BLUE + "Used Memory: "
        + df.format(currentUsedMemory) + " Mb");
    rt.runFinalization();
    //.Print computation time
    double endTime = System.currentTimeMillis();

    double time = (endTime - startTime)/1000;
    System.out.println("Time: " + df.format(time) + " s" + ANSI_RESET);
    System.out.println();
  }

  public void displayGraphSize(){
    /**
     * Function that computes and displays the size of the constraint graph.
     */
    Integer size = this.getConstraints().stream().map(c -> c.getNoOfArcs()).reduce(0, Integer::sum);
    System.out.println("The size of the constraint graph is: " + size);
  }

  public void solveFC() {}

  public void solveAC3() {}

  public void solveSimpleBT() {}
}