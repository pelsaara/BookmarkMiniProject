package database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for {@link Database}.
 */
public interface AbstractDatabase {

    void init();

    ResultSet query(String query, Object... params) throws SQLException;

    void setDebugMode(boolean d);

    int update(String updateQuery, Object... params) throws SQLException;
    
}
