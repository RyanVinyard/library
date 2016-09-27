import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class Patron {
  private String name;
  private double bac;
  private int id;

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

  public int getId() {
    return id;
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

  public void save(){
    String sql = "INSERT INTO patrons (name, bac) VALUES (:name, :bac)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("bac", this.bac)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Patron> all(){
   String sql = "SELECT * FROM patrons";
   try(Connection con = DB.sql2o.open()){
     return con.createQuery(sql).executeAndFetch(Patron.class);
   }
  }

  public static Patron fetch(int id){
    String sql = "SELECT * FROM patrons WHERE id=:id;";
    try(Connection con = DB.sql2o.open()){
      Patron patron = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Patron.class);
      return patron;
    }
  }

  public List<Book> getBooks(){
    String sql = "SELECT * FROM books WHERE patron_id=:id;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .addParameter("id", this.id)
      .executeAndFetch(Book.class);
    }
  }


}
