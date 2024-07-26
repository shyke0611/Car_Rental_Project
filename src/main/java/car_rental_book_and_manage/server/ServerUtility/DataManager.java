package car_rental_book_and_manage.Server.ServerUtility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * Utility class for managing database connections using HikariCP. source:
 * https://www.baeldung.com/hikaricp
 */
public class DataManager {

    private static final DataSource dataSource;

    // Static block to initialize the data source
    static {
        HikariConfig config = new HikariConfig();
        String decryptedUrl = ConfigManager.decryptProperty("db.url");
        String decryptedUsername = ConfigManager.decryptProperty("db.user");
        String decryptedPassword = ConfigManager.decryptProperty("db.password");

        config.setJdbcUrl(decryptedUrl);
        config.setUsername(decryptedUsername);
        config.setPassword(decryptedPassword);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    /**
     * Gets a connection from the data source.
     *
     * @return a connection to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Closes the given connection if it is not null.
     *
     * @param connection the connection to close
     */
    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}