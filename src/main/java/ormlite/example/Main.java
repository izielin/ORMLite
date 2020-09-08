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
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2, "admin", "admin");

        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource,Account.class);
        TableUtils.createTable(connectionSource, Account.class);

        Account account = new Account();
        account.setName("Jim Halsey Jr.");
        account.setPassword("_secret_password");
        accountDao.create(account);

        // close connection
        connectionSource.close();
    }
}
