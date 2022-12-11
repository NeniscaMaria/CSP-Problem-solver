package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class OrderDrink implements Constraint {
  Integer order;
  String drink;
  private List<Variable> scope = new ArrayList<>();

  public OrderDrink(Integer order, String drink) {
    this.order = order;
    this.drink = drink;
    scope.add(new Variable(drink));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object valueDrink = assignment.getAssignment(new Variable(drink));

//    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == drink)
//        .count();

    return valueDrink == null || valueDrink == order;
  }
}


