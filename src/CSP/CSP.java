package CSP;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class CSP {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLUE = "\u001B[34m";
  private List<Variable> variables;
  private List<Domain> domains;
  private List<Constraint> constraints;

  private Hashtable<Variable, Integer> varIndexHash;
  private Hashtable<Variable, List<Constraint>> cnet;

  private CSP() {
  }

  public CSP(List<Variable> vars) {
    variables = new ArrayList<>(vars.size());
    domains = new ArrayList<>(vars.size());
    constraints = new ArrayList<>();
    varIndexHash = new Hashtable<>();
    cnet = new Hashtable<>();
    Domain emptyDomain = new Domain(new ArrayList<>(0));
    int index = 0;
    for (Variable var : vars) {
      variables.add(var);
      domains.add(emptyDomain);
      varIndexHash.put(var, index++);
      cnet.put(var, new ArrayList<>());
    }
  }

  public List<Variable> getVariables() {
    return Collections.unmodifiableList(variables);
  }

  public int indexOf(Variable var) {
    return varIndexHash.get(var);
  }

  public Domain getDomain(Variable var) {
    return domains.get(varIndexHash.get(var));
  }

  public void setDomain(Variable var, Domain domain) {
    domains.set(indexOf(var), domain);
  }


  public void removeValueFromDomain(Variable var, Object value) {
    Domain currDomain = getDomain(var);
    List<Object> values = new ArrayList<Object>(currDomain.size());
    for (Object v : currDomain)
      if (!v.equals(value))
        values.add(v);
    setDomain(var, new Domain(values));
  }

  public List<Constraint> getConstraints() {
    return constraints;
  }

  public List<Constraint> getConstraints(Variable var) {
    return cnet.get(var);
  }

  public void addConstraint(Constraint constraint) {
    constraints.add(constraint);
    for (Variable var : constraint.getScope()) {
      cnet.get(var).add(constraint);
    }
  }


  public Variable getNeighbor(Variable var, Constraint constraint) {
    List<Variable> scope = constraint.getScope();
    if (scope.size() == 2) {
      if (var.getName().equals(scope.get(0).getName()))
        return scope.get(1);
      else if (var.getName().equals(scope.get(1).getName()))
        return scope.get(0);
    }
    return null;
  }


  public CSP copy() {
    CSP result = new CSP();
    result.variables = variables;
    result.domains = new ArrayList<>(domains.size());
    result.domains.addAll(domains);
    result.constraints = constraints;
    result.varIndexHash = varIndexHash;
    result.cnet = cnet;
    return result;
  }

  public void usedTimeMemory(double startTime, Runtime rt){
    float mb = (1024*1024);
    //Print used memory
    DecimalFormat df = new DecimalFormat();
    df.setMinimumFractionDigits(3);
    float currentUsedMemory = (rt.totalMemory() - rt.freeMemory())/mb;
    System.out.println(ANSI_BLUE + "Used Memory: "
        + df.format(currentUsedMemory) + " Mb");
    rt.runFinalization();
    //.Print computation time
    double endTime = System.currentTimeMillis();

    double time = (endTime - startTime)/1000;
    System.out.println("Time: " + df.format(time) + " s" + ANSI_RESET);
    System.out.println();
  }

  public void solveFC() {}

  public void solveAC3() {}

  public void solveSimpleBT() {}
}