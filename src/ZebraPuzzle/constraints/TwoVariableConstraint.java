package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class TwoVariableConstraint implements Constraint {
  /**
   * This class models a two variable constraint. The value assigned to the two
   * variables needs to be the same.
   */
  String varName1;
  String varName2;
  private List<Variable> scope = new ArrayList<>();

  public TwoVariableConstraint(String varName1, String varName2) {
    this.varName1 = varName1;
    this.varName2 = varName2;
    scope.add(new Variable(varName1));
    scope.add(new Variable(varName2));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public Integer getNoOfArcs() {
    // it is a binary constraint, so only one arc
    return 1;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(varName1));
    Object value2 = assignment.getAssignment(new Variable(varName2));

    return value1 == null || value2 == null || (value1 == value2);
  }
}

