package CryptarithmetixPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TwoLettersTwoCarries implements Constraint {
  private Variable letter1;
  private Variable letter2;
  private Variable carry1;
  private Variable carry2;
  private HashSet<Variable> scope;

  public TwoLettersTwoCarries(Variable letter1, Variable letter2,  Variable carry1, Variable carry2) {
    this.letter1 = letter1;
    this.letter2 = letter2;
    this.carry1 = carry1;
    this.carry2 = carry2;
    scope = new HashSet<>();
    scope.add(letter1);
    scope.add(letter2);
    scope.add(carry1);
    scope.add(carry2);
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
    Object valueCarry1 = assignment.getAssignment(carry1);
    Object valueCarry2 = assignment.getAssignment(carry2);


    return valueLetter1 == null || valueLetter2 == null || valueCarry1 == null || valueCarry2==null||
        (Integer) valueLetter1 + (Integer)valueCarry1 == (Integer)valueLetter2 + 10 * (Integer)valueCarry2;
  }
}
