package CSP;

import java.util.ArrayList;
import java.util.List;

public class CSPSolver extends Solution {

  public List<Assignment> solveWithFC(CSP csp) {
    return recursiveBackTrackingSearch(csp, new Assignment());
  }
  public Assignment solveWithFCOneSolution(CSP csp) {
    return recursiveBackTrackingSearchFirstSolution(csp, new Assignment());
  }

  public List<Assignment> solveWithAC3(CSP csp) {
    DomainRestore info = new AC3().reduceDomains(csp);
    if (!info.isEmpty()) {
      if (info.isEmptyDomainFound())
        return null;
    }
    return recursiveBackTrackingSearch(csp, new Assignment());
  }

  private List<Assignment> recursiveBackTrackingSearch(CSP csp,
                                                 Assignment assignment) {
    /**This function uses recursive Forward Checking backtracking to solve a CSP.
     * It first checks if all variables have been assigned, in which case it returns the assignment.
     * Otherwise, it selects an unassigned variable and tries each value in its domain,
     * in the order specified by the orderDomainValues function.
     * For each value, it sets the variable to that value and checks if the assignment
     * is consistent with the constraints. If it is, it uses the inference function
     * to make inferences and reduce the domains of the other variables. It then calls
     * the recursiveBackTrackingSearch function recursively to try and solve the CSP.
     * If a solution is found, it adds it to the solutions. Otherwise, it restores the domains and
     * tries the next value for the variable.
     */
    List<Assignment> solutions = new ArrayList<>();
    if (assignment.isComplete(csp.getVariables())) {
      // If all variables are assigned, add the assignment to the list of solutions
      solutions.add(assignment);
    } else {
      // Select an unassigned variable
      Variable var = selectUnassignedVariable(assignment, csp);
      // Try each value in the variable's domain in the order specified by the orderDomainValues function
      for (Object value : orderDomainValues(var, assignment, csp)) {
        assignment.setAssignment(var, value);

        // If the assignment is consistent, try to solve the CSP
        if (assignment.isConsistent(csp.getConstraints(var))) {
          DomainRestore info = inference(var, assignment, csp);
          if (!info.isEmpty()){}
          if (!info.isEmptyDomainFound()) {
            Assignment newAssignment = new Assignment(assignment);
            List<Assignment> sols = recursiveBackTrackingSearch(csp, newAssignment);
            // If a solution is found, add it to the list of solutions
            if(!sols.isEmpty()){
              solutions.addAll(new ArrayList<>(sols));
            }
          }
          // Restore the domains and try the next value
          info.restoreDomains(csp);
        }
        assignment.removeAssignment(var);
      }
    }
    return solutions;
  }

  private Assignment recursiveBackTrackingSearchFirstSolution(CSP csp, Assignment assignment) {
    // same as above, but returns the first solution found
    // uses Forward Checking
    Assignment result = null;
    if (assignment.isComplete(csp.getVariables())) {
      result = assignment;
    } else {
      Variable var = selectUnassignedVariable(assignment, csp);
      for (Object value : orderDomainValues(var, assignment, csp)) {
        assignment.setAssignment(var, value);

        if (assignment.isConsistent(csp.getConstraints(var))) {
          DomainRestore info = inference(var, assignment, csp);
          if (!info.isEmpty()){}
          if (!info.isEmptyDomainFound()) {
            result = recursiveBackTrackingSearchFirstSolution(csp, assignment);
            if (result != null)
              break;
          }
          info.restoreDomains(csp);
        }
        assignment.removeAssignment(var);
      }
    }
    return result;
  }

  protected Variable selectUnassignedVariable(Assignment assignment, CSP csp) {
    for (Variable var : csp.getVariables()) {
      if (!(assignment.hasAssignmentFor(var)))
        return var;
    }
    return null;
  }

  protected Iterable<?> orderDomainValues(Variable var,
                                          Assignment assignment, CSP csp) {
    return csp.getDomain(var);
  }

  protected DomainRestore inference(Variable var, Assignment assignment,
                                    CSP csp) {
    return new AC3().reduceDomains(var,
            assignment.getAssignment(var), csp, assignment);
  }


}
