package database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for {@link Connector} for testing purposes.
 */
public interface AbstractConnector {

    /**
     * Uses {@link DriverManager} to determine the correct driver and then
     * returns {@link Connection} to database in location {@link #address}.
     *
     * @return {@link Connection} to database in location {@link #address}
     * @throws java.sql.SQLException
     */
    Connection getConnection() throws SQLException;
    
}
