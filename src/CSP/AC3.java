package CSP;
import Util.*;

public class AC3 {
  /**
   * This class contains methods that help transform a CSP into a AC CSP
   * using the AC3 algorithm presented at the course.
   * This algorithm examines only those binary-constraints which could be affected by the removal of values.
   */

  public DomainRestore reduceDomains(CSP csp) {
    /**
     * This function reduces the domain of a given CSP using AC3.
     * INPUT: a CSP
     * OUTPUT: the reduced domain of the given CSP
     */
    DomainRestore result = new DomainRestore();
    // initialize the queue of variables to be processed
    FIFOQueue<Variable> queue = new FIFOQueue<Variable>();
    // add all the variables to the queue
    queue.addAll(csp.getVariables());
    // run the algorithm
    reduceDomains(queue, csp, result);
    // return the reduced domain
    return result.compactify();
  }

  public DomainRestore reduceDomains(Variable var, Object value, CSP csp, Assignment assignment) {
    /**
     * This function reduces the domain of a given var from a given CSP.
     * INPUT: var = the variable for which we want to reduce the domain
     *        value = a potential value for the var
     *        csp = the CSP
     *        assignment = an assignment of the variables from the csp
     * OUTPUT: the reduced domain of the given CSP
   */
    DomainRestore result = new DomainRestore();
    Domain domain = csp.getDomain(var);
    if (domain.contains(value)) {
      if (domain.size() > 1) {
        FIFOQueue<Variable> queue = new FIFOQueue<Variable>();
        queue.add(var);
        result.storeDomainFor(var, domain);
        csp.setDomain(var, new Domain(new Object[] { value }));
        reduceDomains(queue, csp, result, assignment);
      }
    } else {
      result.setEmptyDomainFound(true);
    }
    return result.compactify();
  }

  private void reduceDomains(FIFOQueue<Variable> queue, CSP csp,
                             DomainRestore info) {
    /**
     * This function implements the AC3 algorithm on a given CSP.
     * INPUT: queue = the queue of variables to be examined
     *        csp = the csp to be processed
     *        info = the new reduced domain
     */
    while (!queue.isEmpty()) {
      Variable var = queue.pop();
      for (Constraint constraint : csp.getConstraints(var)) {
        // we want to consider only the binary constraints
        // i.e. those that have in their scope only 2 variables
        if (constraint.getScope().size() == 2) {
          // we get the "neighbor" variable of the current one
          // in the current constraint
          Variable neighbor = csp.getNeighbor(var, constraint);
          if (revise(neighbor, var, constraint, csp, info)) {
            if (csp.getDomain(neighbor).isEmpty()) {
              info.setEmptyDomainFound(true);
              // if we get to an empty domain for a variable, we exit the algorithm
              return;
            }
            // we add the neighbor to the queue, because it needs to be processed again
            queue.push(neighbor);
          }
        }
      }
    }
  }

  private void reduceDomains(FIFOQueue<Variable> queue, CSP csp,
                             DomainRestore info, Assignment assignment) {
    /**
     * This function reduces the domain of a CSP, based on an assignment of the variables.
     * INPUT: queue = the queue of variables to be examined
     *        csp = the CSP
     *        info = the reduced domain
     *        assignment = the assignment
     */
    while (!queue.isEmpty()) {
      Variable var = queue.pop();
      for (Constraint constraint : csp.getConstraints(var)) {
        if (constraint.getScope().size() == 2) {
          Variable neighbor = csp.getNeighbor(var, constraint);
          // we want to continue with the domain reduction, only
          // if there is a value assigned to the neighbor
          if (!assignment.hasAssignmentFor(neighbor)){
            if (revise(neighbor, var, constraint, csp, info)) {
              if (csp.getDomain(neighbor).isEmpty()) {
                info.setEmptyDomainFound(true);
                return;
              }
              queue.push(neighbor);
            }
          }
        }
      }
    }
  }

  private boolean revise(Variable xi, Variable xj, Constraint constraint,
                         CSP csp, DomainRestore info) {
    /**
     * This function revises the domain of a csp.
     * INPUT: xi, xj = 2 affected variables
     *        constraint = a constraint that has as scope the 2 variables
     *        csp = the csp
     *        info = the reduced domain
     * OUPUT: True, if the domain was changed; False, otherwise
     */
    boolean revised = false;
    Assignment assignment = new Assignment();
    // checking each domain value of the first variable
    for (Object iValue : csp.getDomain(xi)) {
      assignment.setAssignment(xi, iValue);
      boolean consistentExtensionFound = false;
      // checking if there exists a jValue in the domain of the second variable
      // that satisfies the given constraint together with iValue
      for (Object jValue : csp.getDomain(xj)) {
        assignment.setAssignment(xj, jValue);
        if (constraint.isSatisfiedWith(assignment)) {
          consistentExtensionFound = true;
          break;
        }
      }
      if (!consistentExtensionFound) {
        // if there is no pair of values from the domain of the 2 variables that satisfy the given
        // constraint, we remove the value iValue from the domain of the first variable
        info.storeDomainFor(xi, csp.getDomain(xi));
        csp.removeValueFromDomain(xi, iValue);
        revised = true;
      }
    }
    return revised;
  }
}
