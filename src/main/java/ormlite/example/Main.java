package ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        String databaseUrlSqlite = "jdbc:sqlite:sqliteDatabase";
        String databaseUrlH2 = "jdbc:h2:./H2DataBase";

        // create single connection
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlSqlite);
//        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2, "admin", "admin");

        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.createTableIfNotExists(connectionSource,Book.class);
//        TableUtils.createTable(connectionSource, Book.class);

        // close connection
        connectionSource.close();
    }
}
