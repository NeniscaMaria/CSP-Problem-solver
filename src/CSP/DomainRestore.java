package CSP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import Util.*;

public class DomainRestore {
  /**
   * This class models a reduced domain in a CSP.
   */
  // a list of pairs that have the variable and the domain of said variable
  private List<Pair<Variable, Domain>> savedDomains;
  // the set of variables for which the domain has changed
  private HashSet<Variable> affectedVariables;
  // flag that signals whether an empty domain was found or not
  private boolean emptyDomainObserved;

  public DomainRestore() {
    savedDomains = new ArrayList<>();
    affectedVariables = new HashSet<>();
  }

  public boolean isEmpty() {
    return savedDomains.isEmpty();
  }


  public void storeDomainFor(Variable var, Domain domain) {
    /**
     * This function updates the domain of a variable to the list, if the domain of that variable was
     * not already modified, and adds the variable to the affectedVariale list.
     * INPUT: var = the variable for which we add a domain
     *        domain = the domain of the variable
     */
    if (!affectedVariables.contains(var)) {
      savedDomains.add(new Pair<>(var, domain));
      affectedVariables.add(var);
    }
  }

  public void setEmptyDomainFound(boolean b) {
    emptyDomainObserved = b;
  }

  public DomainRestore compactify() {
    //This function resets the affectedVariables field, and returns the current instance.
    affectedVariables = null;
    return this;
  }

  public boolean isEmptyDomainFound() {
    return emptyDomainObserved;
  }

  public List<Pair<Variable, Domain>> getSavedDomains() {
    return savedDomains;
  }

  public void restoreDomains(CSP csp) {
    /**
     * This function resets the list of domains.
     * INPUT: csp = the CSP from which we want to restore the domains
     */
    for (Pair<Variable, Domain> pair : getSavedDomains())
      csp.setDomain(pair.getFirst(), pair.getSecond());
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Pair<Variable, Domain> pair : savedDomains)
      result.append(pair.getFirst()).append("=").append(pair.getSecond()).append(" ");
    if (emptyDomainObserved)
      result.append("!");
    return result.toString();
  }
}
