package ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

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

        // create single connection
//        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlSqlite);
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD, USER, PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);
//        TableUtils.createTable(connectionSource, Book.class);

        Book book = new Book();
        book.setTitle("Lord of the Rings");
        book.setAuthor("J.R.R Tolkien");
        book.setIsbn("127151");
        book.setAddedDate(new Date());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy/MM/dd");
        String dateString = "2012/11/17";
        Date date = simpleDateFormat.parse(dateString);

        book.setReleasedDate(date);
        book.setRating("5");
        book.setBorrowed(true);
        book.setPrice(34.99);

        Dao<Book, Integer> dao = DaoManager.createDao(connectionSource, Book.class);
        dao.create(book);
        System.out.println("Before Update: " + book);

        book.setTitle("Hobbit");
        dao.update(book);
        System.out.println("After Update: " + book);

        dao.delete(book);
        System.out.println("After Delete: " + book);

        book = dao.queryForId(book.getId());
        System.out.println("After Query: " + book);

        // close connection
        connectionSource.close();
    }
}
