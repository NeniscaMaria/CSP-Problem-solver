package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class DrinkCigarettes implements Constraint {
  String cigarettes;
  String drink;
  private List<Variable> scope = new ArrayList<>();

  public DrinkCigarettes(String cigarettes, String drink) {
    this.cigarettes = cigarettes;
    this.drink = drink;
    scope.add(new Variable(cigarettes));
    scope.add(new Variable(drink));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(cigarettes));
    Object value2 = assignment.getAssignment(new Variable(drink));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == cigarettes || entry.getValue() == drink)
//        .count();

    return value1 == null || value2 == null ||
        (value1 == value2);
  }
}



