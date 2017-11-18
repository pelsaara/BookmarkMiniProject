package ui;

import database.Connector;
import database.Database;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Main class of project, contains logic for command-line interface.
 */
public class CLI {

    public static void main(String[] args) throws SQLException {
        Database database = new Database(new Connector("jdbc:sqlite:test.db"));
        database.init();

        database.update("INSERT INTO BOOK (title, author) VALUES(?, ?);", "Kirjannimi", "Kirjailija");

        Map<String, List<String>> results = database.query("SELECT * FROM Book;");
        for (String col : results.keySet()) {
            System.out.println(col + ": " + results.get(col).get(0));
        }

    }
}
