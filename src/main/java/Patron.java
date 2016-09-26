import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Patron {
  private String name;
  private double bac;

  public static final int MAX_CHECKED_BOOKS = 5;

  public Patron(String name, double bac) {
    this.name = name;
    this.bac = bac;
  }

  public String getName() {
    return name;
  }

  public double getBac() {
    return bac;
  }
}
