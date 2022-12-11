package CSP;

import java.util.*;

public class Assignment {

  List<Variable> variables;
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
    for (Constraint cons : constraints)
      if (!cons.isSatisfiedWith(this))
        return false;
    return true;
  }

  public boolean isComplete(List<Variable> vars) {
    for (Variable var : vars) {
      if (!hasAssignmentFor(var))
        return false;
    }
    return true;
  }


  public boolean isSolution(CSP csp) {
    return isConsistent(csp.getConstraints())
        && isComplete(csp.getVariables());
  }

  public Assignment copy() {
    Assignment copy = new Assignment();
    for (Variable var : variables) {
      copy.setAssignment(var, variableToValue.get(var));
    }
    return copy;
  }

  public void printSolution(String puzzle) {
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

  public void printSolution() {
    boolean comma = false;
    StringBuffer result = new StringBuffer("{");
    for (Variable var : variables) {
      if (comma)
        result.append(", ");
      result.append(var + "=" + variableToValue.get(var));
      comma = true;
    }
    result.append("}");
    System.out.println(result);
  }
}