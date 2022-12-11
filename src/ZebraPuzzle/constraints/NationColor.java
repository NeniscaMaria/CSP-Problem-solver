package ZebraPuzzle.constraints;
import CSP.*;
import Util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NationColor implements Constraint{
  String nation;
  String color;
  private List<Variable> scope = new ArrayList<>();

  public NationColor(String nation, String color) {
    this.nation = nation;
    this.color = color;
    scope.add(new Variable(nation));
    scope.add(new Variable(color));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(nation));
    Object value2 = assignment.getAssignment(new Variable(color));

//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == nation || entry.getValue() == color)
//        .count();

    return value1 == null || value2 == null ||
        (value1 ==  value2);
  }
}
