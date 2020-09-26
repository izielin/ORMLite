package ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import ormlite.example.database.Author;
import ormlite.example.database.Book;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    private static final String JDBC_DRIVER_HD = "jdbc:h2:./databaseH2";
    private static final String USER = "admin";
    private static final String PASS = "admin";
    private static JdbcConnectionSource connectionSource;

    public static void main(String[] args) throws SQLException, IOException, ParseException {

        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD, USER, PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.dropTable(connectionSource, Author.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);
        TableUtils.createTableIfNotExists(connectionSource, Author.class);

        Dao<Book, Integer> booksDao = DaoManager.createDao(connectionSource, Book.class);
        Dao<Author, Integer> authorsDao = DaoManager.createDao(connectionSource, Author.class);

        Author author = new Author();
        author.setAuthorFirstName("John Ronald Reuel");
        author.setAuthorLastName("Tolkien");

        Book book = new Book();
        book.setTitle("Lord of the Rings");
        book.setDescription(null);
        book.setIsbn("127151");
        book.setAddedDate(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd");
        String dateString = "2012/11/17";
        Date date = simpleDateFormat.parse(dateString);

        book.setReleasedDate(date);
        book.setRating("5");
        book.setBorrowed(true);
        book.setPrice(34.99);

        authorsDao.create(author);
        book.setAuthor(author);
        booksDao.create(book);
        authorsDao.refresh(book.getAuthor());

        System.out.println(book);
        System.out.println(author);

        connectionSource.close();
    }
}
