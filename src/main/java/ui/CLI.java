package ui;

import database.Connector;
import database.Database;
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
        } catch (Exception ex) {
            Logger.getLogger(CLI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
