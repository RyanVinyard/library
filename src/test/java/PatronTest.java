import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;

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

  
}
