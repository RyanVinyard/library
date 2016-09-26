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

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return newPatron.getName().equals(this.getName()) && newPatron.getBac() == this.getBac();
    }
  }

}
