package bookmarkdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connector implements AbstractConnector {

    private final String address;

    public Connector(String address) {
        this.address = address;
    }

    /**
     * Uses {@link DriverManager} to determine the correct driver and then
     * returns {@link Connection} to database in location {@link #address}.
     *
     * @return {@link Connection} to database in location {@link #address}
     * @throws java.sql.SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection(address, "sa", "");
    }

}
