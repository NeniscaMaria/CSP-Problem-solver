package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TwoLettersOneCarry implements Constraint {
  /**
   * This class formulates the constraint that one digit is equal to carry + another digit
   * ex:  213 + 93 = 306, or 93 + 213 = 306 <=> AB + CIB = BOD.
   * 3 = 2+1 <=> B = C + 1
 */
  private Variable letter1;
  private Variable letter2;
  private Variable carry;
  private HashSet<Variable> scope;

  public TwoLettersOneCarry(Variable letter1, Variable letter2, Variable carry) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.carry = carry;
    scope = new HashSet<>();
    scope.add(letter1);
    scope.add(letter2);
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
    Object valueLetter1 = assignment.getAssignment(letter1);
    Object valueLetter2 = assignment.getAssignment(letter2);
    Object valueCarry = assignment.getAssignment(carry);
    return valueLetter1 == null || valueLetter2 == null || valueCarry==null||
        valueLetter1.equals((Integer)valueCarry + (Integer)valueLetter2);
  }
}
