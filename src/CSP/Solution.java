package CSP;

import java.util.List;

public abstract class Solution {
  /**
   * This class models the skeleton of a solver class.
   */

  public abstract List<Assignment> solveWithSimpleBT(CSP csp);
  public abstract List<Assignment> solveWithFC(CSP csp);
  public abstract List<Assignment> solveWithAC3(CSP csp);
}
