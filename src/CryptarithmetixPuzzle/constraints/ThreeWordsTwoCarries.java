package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class ThreeWordsTwoCarries implements Constraint {
  private Variable letter1;
  private Variable letter2;
  private Variable letter3;
  private Variable carry1;
  private Variable carry2;
  private List<Variable> scope;

  public ThreeWordsTwoCarries(Variable letter1, Variable letter2, Variable letter3, Variable carry1, Variable carry2) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.letter3 = letter3;
    this.carry1 = carry1;
    this.carry2 = carry2;
    scope = new ArrayList<>(5);
    scope.add(letter1);
    scope.add(letter2);
    scope.add(letter3);
    scope.add(carry1);
    scope.add(carry2);
  }

  @Override
  public List<Variable> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object valueLetter1 = assignment.getAssignment(letter1);
    Object valueLetter2 = assignment.getAssignment(letter2);
    Object valueLetter3 = assignment.getAssignment(letter3);
    Object valueCarry1 = assignment.getAssignment(carry1);
    Object valueCarry2 = assignment.getAssignment(carry2);


    return valueLetter1 == null || valueLetter2 == null || valueLetter3==null || valueCarry1 == null || valueCarry2==null||
        (Integer)valueLetter1 + (Integer)valueLetter2 + (Integer)valueCarry1 ==
            (Integer)valueLetter3 + 10 * (Integer)valueCarry2;
  }
}
