package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class VariableOrderConstraint implements Constraint {
  /**
   * This class models a variable-order constraint. The value assigned to the
   * variable needs to be equal to the order.
   */
  Integer order;
  String varName;
  private List<Variable> scope = new ArrayList<>();

  public VariableOrderConstraint(Integer order, String varName) {
    this.order = order;
    this.varName = varName;
    scope.add(new Variable(varName));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value = assignment.getAssignment(new Variable(varName));

    return value == null || value == order;
  }
}

