package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class OrderColor implements Constraint {
  Integer order;
  String color;
  private List<Variable> scope = new ArrayList<>();

  public OrderColor(Integer order, String color) {
    this.order = order;
    this.color = color;
    scope.add(new Variable(color));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value = assignment.getAssignment(new Variable(color));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == color)
//        .count();

    return value == null || value == order;
  }
}
