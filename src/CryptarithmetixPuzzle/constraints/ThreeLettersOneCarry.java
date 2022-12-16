package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ThreeLettersOneCarry implements Constraint {
  /**
 * This class formulates the constraint for the sum of 2 digits must be equal to the 3rd digit + 10*carry.
 * ex: TWO+TWO=FOUR <=> 132+132=0264
 * This constraint stipulates that W+W=U+10*C1 <=> 3+3 = 6+10*0
 */
  private Variable letter1;
  private Variable letter2;
  private Variable letter3;
  private Variable carry;
  private HashSet<Variable> scope;

  public ThreeLettersOneCarry(Variable letter1, Variable letter2, Variable letter3, Variable carry) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.letter3 = letter3;
    this.carry = carry;
    scope = new HashSet<>();
    scope.add(letter1);
    scope.add(letter2);
    scope.add(letter3);
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
    Object valueLetter3 = assignment.getAssignment(letter3);
    Object valueCarry = assignment.getAssignment(carry);

    return valueLetter1 == null || valueLetter2 == null || valueLetter3==null || valueCarry==null||
        (Integer)valueLetter1 + (Integer) valueLetter2==(Integer)valueLetter3 + 10*(Integer)valueCarry;
  }
}
