package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class AnimalsCigarettes implements Constraint {
  String cigarettes;
  String animal;
  private List<Variable> scope = new ArrayList<>();

  public AnimalsCigarettes(String cigarettes, String animal) {
    this.cigarettes = cigarettes;
    this.animal = animal;
    scope.add(new Variable(cigarettes));
    scope.add(new Variable(animal));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(cigarettes));
    Object value2 = assignment.getAssignment(new Variable(animal));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == cigarettes || entry.getValue() == animal)
//        .count();

    return value1 == null || value2 == null ||
        (value1 == value2);
  }
}

