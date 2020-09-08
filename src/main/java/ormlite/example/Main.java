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
        // this uses h2 but you can change it to match your database
        String databaseUrl = "jdbc:sqlite:db.sqlite";
        // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);

        // instantiate the DAO to handle Account with String id
        Dao<Account,String> accountDao =
                DaoManager.createDao(connectionSource, Account.class);

// if you need to create the 'accounts' table make this call
        TableUtils.createTable(connectionSource, Account.class);

        // create an instance of Account
        String name = "Jim Smith";
        Account account = new Account();
        account.setName(name);
        account.setPassword("_secret");

// persist the account object to the database
        accountDao.create(account);

        // retrieve the account
        Account account2 = accountDao.queryForId(name);
// show its password
        System.out.println("Account: " + account2.getPassword());

// close the connection source
        connectionSource.close();
    }
}
