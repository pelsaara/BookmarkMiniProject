package ui;

import database.Connector;
import database.Database;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class of project, contains logic for command-line interface.
 */
public class CLI {

    public static void main(String[] args) {
        Database database;
        try {
            database = new Database(new Connector("jdbc:sqlite:test.db"));
            database.init();
            
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
        } catch (Exception ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
