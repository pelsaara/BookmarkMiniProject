package ui;

import database.Connector;
import database.Database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
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

	// Testi alkaa
            
            database.setDebugMode(true);
            int n = database.update("INSERT INTO Book(title, author, ISBN) VALUES ('kirja', 'Laura', '11-111')");
            ResultSet rs = database.query("SELECT * FROM Book");
            System.out.println("querying SELECT * FROM Book");
            
            while (rs.next()) {
                Object book = rs.getObject("title");
		Object author= rs.getObject("author");

		System.out.println("book : " + (String) book);
		System.out.println("author : " + (String) author);
            }

    }
}
