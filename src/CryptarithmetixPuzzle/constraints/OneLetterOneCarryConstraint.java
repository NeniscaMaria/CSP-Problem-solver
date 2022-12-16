package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OneLetterOneCarryConstraint implements Constraint {
  /**
   * This class formulates the constraint for the sum of one digit and one carry.
   * 9+9=18 <=> A + A = BC.
   * This constraint stipulates that B = C1, where C1 is the carry from A+A.
   */
  private Variable letter;
  private Variable carry;
  private HashSet<Variable> scope;

  public OneLetterOneCarryConstraint(Variable letter, Variable carry) {
    /**
     * This class formulates the following constraint:
     * ex: TO + TO = FOR. When we get to F, we only need to add the value of F + a possible carry
     */
    this.letter = letter;
    this.carry = carry;
    scope = new HashSet<>();
    scope.add(letter);
    scope.add(carry);
  }

  @Override
  public List<Variable> getScope() {
    return new ArrayList<>(scope);
  }

  @Override
  public Integer getNoOfArcs(){
    return this.scope.size();
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object valueLetter = assignment.getAssignment(letter);
    Object valueCarry = assignment.getAssignment(carry);
    return valueLetter == null || valueCarry == null ||
        (valueLetter).equals(valueCarry);
  }
}
