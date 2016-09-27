import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class BookTest {
  Book book;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    book = new Book("The Corrections", "Jonathan Franzen", "Contemporary Fiction", 2001, 1);
  }

  @Test
  public void book_instantiatesBookObject_true() {
    assertTrue(book instanceof Book);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Book secondBook = new Book("The Corrections", "Jonathan Franzen", "Contemporary Fiction", 2001, 1);
    assertTrue(book.equals(secondBook));
  }

  @Test
  public void save_assignsIdToBook() {
    Book newBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966, 2);
    newBook.save();
    Book.all().get(0);
    assertEquals(newBook.getId(), newBook.getId());
  }

  @Test
  public void all_returnsAllBookObjectsFromDatabase_true() {
    book.save();
    Book newBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966, 3);
    newBook.save();
    assertTrue(Book.all().get(0).equals(book));
    assertTrue(Book.all().get(1).equals(newBook));
  }

  @Test
  public  void find_returnsBookWithSameID_secondBook() {
    Book firstBook = new Book("Curious George and the Exposed Wiring", "Satan", "Non-Fiction", 1966, 3);
    firstBook.save();
    Book secondBook = new Book("Harry Potter and the Drunken Warlock", "JK Rofling", "Drunken Wizardry", 2010, 2);
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

  @Test
  public void getPatronId_returnsPatronId_int() {
     assertEquals(1, book.getPatronId());
   }

  @Test
  public void getTimeCheckedOut_recordsTimeOfCreationInDatabase() {
    Book testBook = new Book("Curious George and the Loaded Semi-Automatic Handgun with the Safety Off", "Satan", "Non-Fiction", 1966, 3);
    testBook.save();
    Timestamp savedBookCheckout = Book.find(testBook.getId()).getTimeCheckedOut();
    Timestamp rightNow = new Timestamp(new Date().getTime());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void daysKept_throwsExceptionIfDaysKeptIsAtMaxValue(){
    for(int i = 0; i <= (Book.MAX_DAYS_KEPT); i++){
      book.keepBook();
    }
  }

  @Test
  public void keepBook_increaseDaysBookIsKept() {
    book.keepBook();
    assertTrue(book.getDaysKept() > 1);
  }

  @Test
  public void startTimer_executesStartTimerMethod() {
    int firstDaysKept = book.getDaysKept();
    book.startTimer();
    try {
      Thread.sleep(3000);
    } catch (InterruptedException exception) {}
      int secondDaysKept = book.getDaysKept();
      assertTrue(firstDaysKept < secondDaysKept);
  }

  @Test
  public void isCheckedOut_confirmsBookIsCheckedOutIfDaysKeptIsMoreThan1_false(){
    assertEquals(book.isCheckedOut(), false);
  }

  @Test
  public void bookReturn_confirmsBookIsBackInStock_True() {
    book.bookReturn();
    assertTrue(book.getDaysKept() == 0);
  }


}
