package car_rental_book_and_manage.Utility;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/** Utility class for managing database connections using HikariCP. */
public class DataManager {

  private static final DataSource dataSource;

  // Static block to initialize the data source
  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:mysql://sql.freedb.tech:3306/freedb_carrentalproject");
    config.setUsername("freedb_hshi270");
    config.setPassword("qA3e$g%u*YCGJTB");
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
