package CSP;

public class Variable {
  /**
   * This class models a variable from a CSP.
   */
  // the name of the variable
  private String name;

  public Variable(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Variable) {
      return this.name.equals(((Variable) obj).name);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
