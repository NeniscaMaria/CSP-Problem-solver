package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class NationOrder implements Constraint {
  Integer order;
  String nation;
  private List<Variable> scope = new ArrayList<>();

  public NationOrder(Integer order, String nation) {
    this.order = order;
    this.nation = nation;
    scope.add(new Variable(nation));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value = assignment.getAssignment(new Variable(nation));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == nation)
//        .count();

    return value == null || value == order;
  }
}



