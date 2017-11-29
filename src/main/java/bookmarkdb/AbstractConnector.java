package bookmarkdb;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for {@link Connector} for testing purposes.
 */
public interface AbstractConnector {

    /**
     * @return {@link Connection} to database
     * @throws java.sql.SQLException
     */
    Connection getConnection() throws SQLException;

}
