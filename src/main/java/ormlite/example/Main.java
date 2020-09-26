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
        book.setAuthor(author);
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

        booksDao.create(book);
        System.out.println("After save in db: " + book);

        Book bookQuery = booksDao.queryForId(1);
        System.out.println("After query: " + bookQuery);

        bookQuery.getAuthor().setAuthorFirstName("Author");
        bookQuery.getAuthor().setAuthorLastName("Unknown");
        authorsDao.createOrUpdate(bookQuery.getAuthor());
        bookQuery = booksDao.queryForId(1);
        System.out.println("After author change: " + bookQuery);


        author = authorsDao.queryForId(1);
        author.getBooks().forEach(e->{
            e.setTitle("Bogurodzica");
            try {
                booksDao.createOrUpdate(e);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        authorsDao.refresh(author);
        author.getBooks().forEach(e->{
            System.out.println("Title change to: " + e.getTitle());
        });


        Book book3 = new Book();
        book3.setTitle("Witcher: Blood of Elves");
        book3.setDescription("Here description is unnecessary");
        book3.setIsbn("532251525234");
        book3.setAddedDate(new Date());

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString3 = "2012/11/11";
        Date date3 = sdf3.parse(dateInString3);

        book3.setReleasedDate(date3);
        book3.setRating("1");
        book3.setBorrowed(true);
        book3.setPrice(33.99);

        author.getBooks().add(book3);

        connectionSource.close();
    }
}
