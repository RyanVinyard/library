import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PatronTest {
  Patron patron;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    patron = new Patron("Ryan", 0.3);
  }

  @Test
  public void patron_instantiatesPatronObject_true() {
    assertTrue(patron instanceof Patron);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Patron secondPatron = new Patron("Ryan", 0.3);
    assertTrue(patron.equals(secondPatron));
  }

  @Test
  public void save_savesObjectToDatabase_true(){
    patron.save();
    Patron secondPatron = new Patron("Ryan", 0.3);
    secondPatron.save();
    assertTrue(patron.equals(secondPatron));
  }

  @Test
  public void getId_returnsIdOfPatron_true() {
    patron.save();
    assertTrue(patron.getId() > 0);
  }

  @Test
  public void all_returnsAllInstancesOfPatron_true(){
    patron.save();
    Patron secondPatron = new Patron("Sara", 0.04);
    secondPatron.save();
    assertTrue(Patron.all().get(0).equals(patron));
    assertTrue(Patron.all().get(1).equals(secondPatron));
  }

  @Test
  public void fetch_returnsPatronGivenId_patron(){
    patron.save();
    assertEquals(patron, Patron.fetch(patron.getId()));
  }



}
