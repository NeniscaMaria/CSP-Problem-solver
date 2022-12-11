package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;

public class OneLetterOneCarryConstraint implements Constraint {
  private Variable letter;
  private Variable carry;
  private List<Variable> scope;

  public OneLetterOneCarryConstraint(Variable letter, Variable carry) {
    this.letter = letter;
    this.carry = carry;
    scope = new ArrayList<Variable>(2);
    scope.add(letter);
    scope.add(carry);
  }

  @Override
  public List<Variable> getScope() {
    return scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object valueLetter = assignment.getAssignment(letter);
    Object valueCarry = assignment.getAssignment(carry);
    return valueLetter == null || valueCarry == null ||
        (valueLetter).equals(valueCarry);
  }
}
