package CSP;

import java.util.ArrayList;
import java.util.List;

public class CSPSolver extends Solution {
  /**
   * This class contains the implementations of some algorithms that solve a CSP.
   */

  public List<Assignment> solveWithSimpleBT(CSP csp) {
    /**
     * This function solves a given CSP using simple back tracking
     * INPUT: the csp we want to solve
     * OUtPUT: a list of solutions for the csp
     */
    return simpleBacktracking(csp, new Assignment());
  }


  public List<Assignment> solveWithFC(CSP csp) {
    /**
     * This function solves a given CSP using forward checking back tracking
     * INPUT: the csp we want to solve
     * OUtPUT: a list of solutions for the csp
     */
    Assignment a = new Assignment();
    a.setAssignment(new Variable("t"), 1);
    a.setAssignment(new Variable("w"), 3);
    a.setAssignment(new Variable("o"), 2);
    a.setAssignment(new Variable("f"), 0);
    a.setAssignment(new Variable("u"), 6);
    a.setAssignment(new Variable("r"), 4);
    a.setAssignment(new Variable("c1"), 0);
    a.setAssignment(new Variable("c2"), 0);
    a.setAssignment(new Variable("c3"), 0);
    ArrayList<Constraint> cfailed = new ArrayList<>();
    for(Constraint c: csp.getConstraints()){
      if(!c.isSatisfiedWith(a)){
        cfailed.add(c);
      }
    }
    return recursiveBackTrackingSearch(csp, new Assignment());
  }

  public Assignment solveWithFCOneSolution(CSP csp) {
    /**
     * This function solves a given CSP using forward checking back tracking
     * INPUT: the csp we want to solve
     * OUtPUT: the first solution foun
     */
    return recursiveBackTrackingSearchFirstSolution(csp, new Assignment());
  }

  public List<Assignment> solveWithAC3(CSP csp) {
    /**
     * This function solves a given CSP using AC3 + FC BT
     * INPUT: the csp we want to solve
     * OUtPUT: a list of solutions for the csp
     */
    DomainRestore info = new AC3().reduceDomains(csp);
    if (!info.isEmpty()) {
      if (info.isEmptyDomainFound())
        return null;
    }
    return recursiveBackTrackingSearch(csp, new Assignment());
  }

  private List<Assignment> recursiveBackTrackingSearch(CSP csp, Assignment assignment) {
    /**
     * This function uses recursive Forward Checking backtracking to solve a CSP.
     * It first checks if all variables have been assigned, in which case we have a solution.
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
      for (Object value : orderDomainValues(var, csp)) {
        assignment.setAssignment(var, value);

        // If the assignment is consistent, we try to reduce the domain of the other variables
        if (assignment.isConsistent(csp.getConstraints(var))) {
          DomainRestore info = inference(var, assignment, csp);
          if (!info.isEmpty()) {
          }
          if (!info.isEmptyDomainFound()) {
            Assignment newAssignment = new Assignment(assignment);
            List<Assignment> sols = recursiveBackTrackingSearch(csp, newAssignment);
            // If a solution is found, add it to the list of solutions
            if (!sols.isEmpty()) {
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
    Assignment result = null;
    if (assignment.isComplete(csp.getVariables())) {
      result = assignment;
    } else {
      Variable var = selectUnassignedVariable(assignment, csp);
      for (Object value : orderDomainValues(var, csp)) {
        assignment.setAssignment(var, value);

        if (assignment.isConsistent(csp.getConstraints(var))) {
          DomainRestore info = inference(var, assignment, csp);
          if (!info.isEmpty()) {
          }
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
    /**
     * This function select the first variable that has no value assigned to it
     * INPUT: assignment = the current assignment of the variables
     *        csp = the CSP
     * OUPUT: the variable, if there was an unassigned one found. null, otherwise
     */
    for (Variable var : csp.getVariables()) {
      if (!(assignment.hasAssignmentFor(var)))
        return var;
    }
    return null;
  }

  protected Iterable<?> orderDomainValues(Variable var, CSP csp) {
    return csp.getDomain(var);
  }

  protected DomainRestore inference(Variable var, Assignment assignment, CSP csp) {
    /**
     * This function reduces the domain of a given variable depending on the assignment of a number of variables.
     * INPUT: var = the variable for which we want to reduce the domain
     *        assignment = the assingment of a number of variables
     *        csp = the CSP
     */
    return new AC3().reduceDomains(var, assignment.getAssignment(var), csp, assignment);
  }

  private List<Assignment> simpleBacktracking(CSP csp, Assignment assignment) {
    /**
     * This function implements a simple backtracking algorithm to solve a CSP.
     * INPUT: csp = the csp to be solved
     *        assignment = the current assignment
     * OUTPUT: a list of solutions
     */
    List<Assignment> solutions = new ArrayList<>();
    if (assignment.isComplete(csp.getVariables())) {
      // If all variables are assigned, add the assignment to the list of solutions
      solutions.add(assignment);
    } else {
      // Select an unassigned variable
      Variable var = selectUnassignedVariable(assignment, csp);
      // Try each value in the variable's domain in the order specified by the orderDomainValues function
      for (Object value : orderDomainValues(var, csp)) {
        assignment.setAssignment(var, value);

        // If the assignment is consistent, we continue the backtracking
        if (assignment.isConsistent(csp.getConstraints(var))) {
          Assignment newAssignment = new Assignment(assignment);
          List<Assignment> sols = simpleBacktracking(csp, newAssignment);
          // If a solution is found, we add it to the list of solutions
          if (!sols.isEmpty()) {
            solutions.addAll(new ArrayList<>(sols));
          }
        }
        assignment.removeAssignment(var);
      }
    }
    return solutions;
  }
}
