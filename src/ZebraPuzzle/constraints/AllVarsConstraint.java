package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllVarsConstraint implements Constraint {
  private final ArrayList<String> entityNotations = new ArrayList<>(Arrays.asList("N","C","T","D","A"));
  private List<Variable> scope;

  public AllVarsConstraint(List<Variable> scope) {this.scope = scope;}

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    // we have houses
    for(int i=1; i<=5;i++) {
      ArrayList<String> foundEntities = new ArrayList<>();
      int houseNo = i;
      List<Map.Entry<Variable, Object>> characteristicsOfHouse = assignment.getVariableToValue().entrySet().stream()
          .filter(entry -> entry.getValue().equals(houseNo)).collect(Collectors.toList());
      for(Map.Entry<Variable, Object> entry: characteristicsOfHouse){
        String entityName = entry.getKey().getName();
        entityName = entityName.substring(0, entityName.length() - 1);
        Object value = entry.getValue();
        if(value == null){
          return true;
        }
        if(foundEntities.contains(entityName)) {
          // if we find again the same entity assign to the house
          // we have a constraint violation
          return false;
        }
        foundEntities.add(entityName);
      }
    }
    return true;
  }
}


