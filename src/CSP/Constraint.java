package CSP;

import java.util.List;


public interface Constraint {
  /**
   * This interface models the base of a constraint in a CSP
   */

  // function that gets the variable scope of a constraint.
  List<Variable> getScope();

  // function that get the no of arcs in the constraint graph for this constraint
  Integer getNoOfArcs();

  // function that checks if an assignment satisfies the constraint
  boolean isSatisfiedWith(Assignment assignment);
}