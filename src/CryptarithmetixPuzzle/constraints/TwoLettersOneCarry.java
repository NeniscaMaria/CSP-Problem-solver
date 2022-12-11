package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class TwoLettersOneCarry implements Constraint {
  private Variable letter1;
  private Variable letter2;
  private Variable carry;
  private List<Variable> scope;

  public TwoLettersOneCarry(Variable letter1, Variable letter2, Variable carry) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.carry = carry;
    scope = new ArrayList<Variable>(3);
    scope.add(letter1);
    scope.add(letter2);
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
    Object valueCarry = assignment.getAssignment(carry);
    return valueLetter1 == null || valueLetter2 == null || valueCarry==null||
        valueLetter1.equals((Integer)valueCarry + (Integer)valueLetter2);
  }
}
