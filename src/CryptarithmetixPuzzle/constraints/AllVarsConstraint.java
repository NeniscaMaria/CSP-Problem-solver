package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllVarsConstraint implements Constraint {
  /**
   * Class that represents the all variables constraint for the Cryptarithemtic puzzle/
   * This constraint formulates that all letters (variables) should have a unique
   * digit (value) assigned to them.
   */
  private List<Variable> scope;

  public AllVarsConstraint(List<Variable> scope) {
    this.scope = scope;
  }

  @Override
  public List<Variable> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Set<Object> set = new HashSet<>();
    boolean nullValues = false;
    for(Variable var: this.scope){
      Object value = assignment.getAssignment(var);
      nullValues = value == null;
      set.add(value);
    }
    HashSet<Object> uniqueValues = new HashSet<>(assignment.getVariableToValue().values());

    return (nullValues || scope.size() >= set.size()) && (uniqueValues.size() <= assignment.getVariableToValue().size()  && assignment.getVariableToValue().size() <= uniqueValues.size() +2 );
  }
}
