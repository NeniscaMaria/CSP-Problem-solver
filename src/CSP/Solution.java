package CSP;

import java.util.List;

public abstract class Solution {
  public abstract List<Assignment> solveWithFC(CSP csp);
  public abstract List<Assignment> solveWithAC3(CSP csp);
}
