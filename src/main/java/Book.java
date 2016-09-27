import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Book {
  private String title;
  private String author;
  private String subject;
  private int copyright_year;
  private int patron_id;
  private Timestamp time_checked_out;
  private int id;
  private int daysKept;
  private Timer timer;

  public static final int MAX_DAYS_KEPT = 7;

  public Book(String title, String author, String subject, int copyright_year, int patron_id) {
    this.title = title;
    this.author = author;
    this.subject = subject;
    this.copyright_year = copyright_year;
    this.patron_id = patron_id;
    daysKept = 1;
    timer = new Timer();
  }

  public int getPatronId() {
    return patron_id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getSubject() {
    return subject;
  }

  public int getCopyrightYear() {
    return copyright_year;
  }

  public int getId() {
    return id;
  }

  public Timestamp getTimeCheckedOut() {
    return time_checked_out;
  }

  public int getDaysKept() {
    return daysKept;
  }

  @Override
  public boolean equals(Object otherBook) {
    if(!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) && this.getAuthor().equals(newBook.getAuthor()) && this.getSubject().equals(newBook.getSubject()) && this.getCopyrightYear() == (newBook.getCopyrightYear());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title, author, subject, copyright_year, patron_id, time_checked_out) VALUES(:title, :author, :subject, :copyright_year, :patron_id, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("author", this.author)
        .addParameter("subject", this.subject)
        .addParameter("copyright_year", this.copyright_year)
        .addParameter("patron_id", this.patron_id)
        .executeUpdate()
        .getKey();
      }
    }

  public static List<Book> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books;";
        return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE id=:id;";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
        return book;
    }
  }

  public void updateTitle(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title=:title WHERE id=:id;";
        con.createQuery(sql)
          .addParameter("id", id)
          .addParameter("title", title)
          .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void keepBook() {
    if (daysKept >= MAX_DAYS_KEPT) {
      throw new UnsupportedOperationException("Your book is overdue!");
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET time_checked_out = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      }
    daysKept++;
  }

  public void startTimer(){
    Book currentBook = this;
    TimerTask timerTask = new TimerTask(){
      @Override
      public void run() {
        if (currentBook.isCheckedOut() == false){
          cancel();
        }
        keepBook();
      }
    };
    this.timer.schedule(timerTask, 0, 600);
  }

  public boolean isCheckedOut() {
    if (daysKept > 1) {
      return true;
    } else {
      return false;
    }
  }

  public void bookReturn() {
    if (daysKept == 0) {
      throw new IllegalArgumentException("This book is not yet checked out!");
    } else if (daysKept > 0 && daysKept <= MAX_DAYS_KEPT) {
      daysKept = 0;
      //run a book return bit here. Actually, maybe this is all that needs to be done.
    } else {
      daysKept = 0;
      //run a late book method that emails or something idk
    }

  }
}




// String[] updateWords = {"title", "author", "subject", "copyright_year"};
// public static update(String) {
//   try(Connection con = DB.sql2o.open()) {
//     for(i=0, i<updateWords, i++) {
//       String sql = "UPDATE books SET " + updateWords[i] + "=:" + updateWords[i] + " WHERE id=:id;";
//       con.createQuery(sql)
//         .addParameter("id", id)
//         .addParameter("updateWords[i]", updateWords[i])
//         .executeUpdate();
//       }
//     }
//   }
