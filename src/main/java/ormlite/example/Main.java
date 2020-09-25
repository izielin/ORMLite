package ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

        Book book2 = new Book();
        book2.setTitle("Hobbit");
        book2.setAuthor("J.R.R Tolkien");
        book2.setIsbn("13327151");
        book2.setAddedDate(new Date());

        String dateString2 = "2009/01/07";
        Date date2 = simpleDateFormat.parse(dateString2);

        book2.setReleasedDate(date2);
        book2.setRating("5");
        book2.setBorrowed(true);
        book2.setPrice(29.99);

        Book book3 = new Book();
        book3.setTitle("Silmarillion");
        book3.setAuthor("J.R.R Tolkien");
        book3.setIsbn("12776151");
        book3.setAddedDate(new Date());

        String dateString3 = "2011/12/11";
        Date date3 = simpleDateFormat.parse(dateString3);

        book3.setReleasedDate(date3);
        book3.setRating("3");
        book3.setBorrowed(false);
        book3.setPrice(24.99);

        Dao<Book, Integer> dao = DaoManager.createDao(connectionSource, Book.class);
        dao.create(book);
        dao.create(book2);
        dao.create(book3);

//        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT * FROM books");
//        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT * FROM books WHERE title = 'Hobbit'");
//        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT MIN(price), MAX(price) FROM books ");
        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT count(*) FROM books WHERE borrowed = true");
        List<String[]> result = rawResults.getResults();
        result.forEach(e->{
            for(String string : e)
                System.out.println(string);
        });

        double maxUnit = dao.queryRawValue("SELECT AVG(price) FROM books");
        System.out.println(maxUnit);

        // close connection
        connectionSource.close();
    }
}
