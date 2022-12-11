package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class NationCigarettes implements Constraint {
  String cigarettes;
  String nation;
  private List<Variable> scope = new ArrayList<>();

  public NationCigarettes(String cigarettes, String nation) {
    this.cigarettes = cigarettes;
    this.nation = nation;
    scope.add(new Variable(cigarettes));
    scope.add(new Variable(nation));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(cigarettes));
    Object value2 = assignment.getAssignment(new Variable(nation));

    // check that no other variables are assigned with the values form here
//    long variablesWithSetValues = assignment.getVariableToValue().entrySet().stream()
//        .filter(entry -> entry.getValue() == cigarettes || entry.getValue() == nation)
//        .count();

    return value1 == null || value2 == null ||
        (value1 == value2 );
  }
}