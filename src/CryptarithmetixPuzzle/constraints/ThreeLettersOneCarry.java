package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class ThreeLettersOneCarry implements Constraint {
  private Variable letter1;
  private Variable letter2;
  private Variable letter3;
  private Variable carry;
  private List<Variable> scope;

  public ThreeLettersOneCarry(Variable letter1, Variable letter2, Variable letter3, Variable carry) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.letter3 = letter3;
    this.carry = carry;
    scope = new ArrayList<>(4);
    scope.add(letter1);
    scope.add(letter2);
    scope.add(letter3);
    scope.add(carry);
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
    Object valueCarry = assignment.getAssignment(carry);

    return valueLetter1 == null || valueLetter2 == null || valueLetter3==null || valueCarry==null||
        (Integer)valueLetter1 + (Integer) valueLetter2==(Integer)valueLetter3 + 10*(Integer)valueCarry;
  }
}
