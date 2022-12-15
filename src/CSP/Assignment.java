package CSP;

import java.util.*;

public class Assignment {
  /**
   * This class models an assignment of a number (or all)
   * variables form a CSP
   */

  // the list of variables that have an assignment
  List<Variable> variables;
  // a dictionary that maps each variable from variables to its assigned value
  Hashtable<Variable, Object> variableToValue;

  public Assignment() {
    variables = new ArrayList<>();
    variableToValue = new Hashtable<>();
  }

  public Assignment(Assignment assignment) {
    this.variables = new ArrayList<>(assignment.getVariables());
    this.variableToValue = new Hashtable<>(assignment.getVariableToValue());

  }

  public Hashtable<Variable, Object> getVariableToValue() {
    return variableToValue;
  }

  public List<Variable> getVariables() {
    return Collections.unmodifiableList(variables);
  }

  public Object getAssignment(Variable var) {
    return variableToValue.get(var);
  }

  public void setAssignment(Variable var, Object value) {
    if (!variableToValue.containsKey(var))
      variables.add(var);
    variableToValue.put(var, value);
  }

  public void removeAssignment(Variable var) {
    if (hasAssignmentFor(var)) {
      variables.remove(var);
      variableToValue.remove(var);
    }
  }

  public boolean hasAssignmentFor(Variable var) {
    return variableToValue.get(var) != null;
  }


  public boolean isConsistent(List<Constraint> constraints) {
    /**
     * THis function that if an assignment is consistent with a number of constraints.
     * INPUT: the list of constraints
     * OUTPUT: True, if all constraints are satisfied. False, otherwise
     */
    for (Constraint cons : constraints)
      if (!cons.isSatisfiedWith(this))
        return false;
    return true;
  }

  public boolean isComplete(List<Variable> vars) {
    /**
     * Ths function checks whether an assignment has a value assigned to all variables.
     * INPUT: the list of variables for which we need ot have values
     * OUPUT: True, if all the variables from vars have values. False, otherwise.
     */
    for (Variable var : vars) {
      if (!hasAssignmentFor(var))
        return false;
    }
    return true;
  }

  public void printSolution(String puzzle) {
    /**
     * This function prints the solution to a Cryptarithmetic puzzle
     * INPUT: the puzzle string.
     */
    String sol = "";
    for (int i = 0; i < puzzle.length(); i++) {
      char c = puzzle.charAt(i);
      if (Character.isLetter(c) && c != ' ' && c != '+' && c != '=') {
        sol += getAssignment(new Variable(Character.toString(c)));
      } else {
        sol += c;
      }
    }
    System.out.println("Solution: "+ sol);
  }

}