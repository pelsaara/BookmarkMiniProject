
package ui;

import database.BookDAO;
import database.Database;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tables.Book;

public class UI implements Runnable {
    private Database database;
    private BufferedReader br;
    private BookDAO bookDAO;

    public UI(Database database) {
        this.database = database;
        br = new BufferedReader(new InputStreamReader(System.in));
        bookDAO = new BookDAO(database);
    }

    public UI(Database database, BufferedReader buff) {
        this.database = database;
        this.br = buff;
        bookDAO = new BookDAO(database);        
    }

    @Override
    public void run() {
        
        while (true) {
            System.out.println("\nTo list all your bookmarks type \"browse\".\nTo add a book type \"add book\".\nTo quit the program type \"quit\".\n\nWhat to do?\n");
            
            String command;
            try {
                command = br.readLine();
                if (command.equals("quit")) {
                    break;
                } else if (command.equals("add book")) {
                    String author;
                    String title;
                    String ISBN;
                    System.out.println("");
                    System.out.println("Title:");
                    title = br.readLine();
                    System.out.println("Author:");
                    author = br.readLine();
                    System.out.println("ISBN:");
                    ISBN = br.readLine();
                    try {
                        bookDAO.create(new Book(title, author, ISBN));
                        System.out.println("\nBook added!");
                    } catch (SQLException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (command.equals("browse")) {
                    System.out.println("");
                    try {
                        List<Book> books = bookDAO.findAll();
                        for (Book book : books) {
                            System.out.println(book);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
