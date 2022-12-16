
package ZebraPuzzle.constraints;

    import CSP.Assignment;
    import CSP.Constraint;
    import CSP.Variable;

    import java.util.ArrayList;
    import java.util.List;

public class FixedOrderHouses implements Constraint {
  /**
   * This class models two neighboring houses constraint: house2 is on the left of house1ß
   * Two characteristics must be in neighboring houses.
   * order(characteristicHouse1) != order(characteristicHouse2) AND
   * order(characteristicHouse1) = order(characteristicHouse2) - 1
   */
  String characteristicHouse1;
  String characteristicHouse2;
  private List<Variable> scope = new ArrayList<>();

  public FixedOrderHouses(String characteristicHouse1, String characteristicHouse2) {
    this.characteristicHouse1 = characteristicHouse1;
    this.characteristicHouse2 = characteristicHouse2;
    scope.add(new Variable(characteristicHouse1));
    scope.add(new Variable(characteristicHouse2));
  }

  @Override
  public List<Variable> getScope() {
    return this.scope;
  }

  @Override
  public Integer getNoOfArcs() {
    // it is a binary constraint, so only one arc
    return 1;
  }

  @Override
  public boolean isSatisfiedWith(Assignment assignment) {
    Object value1 = assignment.getAssignment(new Variable(characteristicHouse1));
    Object value2 = assignment.getAssignment(new Variable(characteristicHouse2));

    return value1 == null || value2 == null || (
        value1 != value2 && ((Integer)value1 == (Integer)value2 - 1));
  }
}


