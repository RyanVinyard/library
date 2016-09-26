import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;


public class BookTest {
  Book book;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    book = new Book("The Corrections", "Jonathan Franzen", "Contemporary Fiction", 2001);
  }

  @Test
  public void book_instantiatesBookObject_true() {
    assertTrue(book instanceof Book);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Book secondBook = new Book("The Corrections", "Jonathan Franzen", "Contemporary Fiction", 2001);
    assertTrue(book.equals(secondBook));
  }

  @Test
  public void save_assignsIdToBook() {
    Book newBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966);
    newBook.save();
    Book.all().get(0);
    assertEquals(newBook.getId(), newBook.getId());
  }

  @Test
  public void all_returnsAllBookObjectsFromDatabase_true() {
    book.save();
    Book newBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966);
    newBook.save();
    assertTrue(Book.all().get(0).equals(book));
    assertTrue(Book.all().get(1).equals(newBook));
  }

  @Test
  public  void find_returnsBookWithSameID_secondBook() {
    Book firstBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966);
    firstBook.save();
    Book secondBook = new Book("Harry Potter and the Drunken Warlock", "JK Rofling", "Drunken Wizardry", 2010);
    secondBook.save();
    assertEquals(Book.find(secondBook.getId()), secondBook);
  }

  @Test
  public void update_updatesAnyParameterInDatabaseForBook_string() {
    book.save();
    book.updateTitle("Corrections");
    assertEquals("Corrections", Book.find(book.getId()).getTitle());
  }

  @Test
  public void delete_removesEntryFromTable_null() {
    book.save();
    book.delete();
    assertEquals(null, Book.find(book.getId()));
  }


}
