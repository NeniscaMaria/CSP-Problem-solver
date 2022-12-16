package ZebraPuzzle.constraints;

import CSP.Assignment;
import CSP.Constraint;
import CSP.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AllVarsConstraint implements Constraint {
  /**
   * This class models the all variable constraints for the zebra puzzle.
   */
  // the variable scope of the constraint
  private List<Variable> scope;

  public AllVarsConstraint(List<Variable> scope) {this.scope = scope;}

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public Integer getNoOfArcs() {
    return scope.size();
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    /**
     * Here we check if each house has only 5 characteristics assigned to it: color, nation,
     * drink, animal, and cigarettes. This way we check that no 2 nations, for example, ar assigned to
     * the same house
     */
    // we have 5  houses
    for(int i=1; i<=5;i++) {
      ArrayList<String> foundEntities = new ArrayList<>();
      int houseNo = i;
      List<Map.Entry<Variable, Object>> characteristicsOfHouse = assignment.getVariableToValue().entrySet().stream()
          .filter(entry -> entry.getValue().equals(houseNo)).collect(Collectors.toList());

      for(Map.Entry<Variable, Object> entry: characteristicsOfHouse){
        // get the notation of the entity. an entity is of form {notation}{number}
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


