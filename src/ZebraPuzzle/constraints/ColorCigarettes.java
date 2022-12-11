package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class ColorCigarettes implements Constraint {
  String cigarettes;
  String color;
  private List<Variable> scope = new ArrayList<>();

  public ColorCigarettes(String cigarettes, String color) {
    this.cigarettes = cigarettes;
    this.color = color;
    scope.add(new Variable(cigarettes));
    scope.add(new Variable(color));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object valueCigarettes = assignment.getAssignment(new Variable(cigarettes));
    Object valueColor = assignment.getAssignment(new Variable(color));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == cigarettes || entry.getValue() == color)
//        .count();

    return valueCigarettes == null || valueColor == null ||
        (valueCigarettes == valueColor);
  }
}
