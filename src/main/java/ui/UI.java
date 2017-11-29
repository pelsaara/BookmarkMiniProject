package ui;

import bookmarkdb.BookDAO;
import bookmarkdb.Database;
import bookmarkdb.PodcastDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bookmarkmodels.Book;
import bookmarkmodels.Podcast;

public class UI implements Runnable {

    private Database database;
    private BufferedReader br;
    private BookDAO bookDAO;
    private PodcastDAO podcastDAO;

    public UI(Database database) {
        this.database = database;
        br = new BufferedReader(new InputStreamReader(System.in));
        bookDAO = new BookDAO(database);
        podcastDAO = new PodcastDAO(database);
    }

    public UI(Database database, BufferedReader buff) {
        this.database = database;
        this.br = buff;
        bookDAO = new BookDAO(database);
        podcastDAO = new PodcastDAO(database);
    }

    @Override
    public void run() {

        while (true) {
            System.out.println("\nTo list all your bookmarks type \"browse\".\nTo add a book type \"add book\".\nTo delete a book type \"delete book\".\nTo add a podcast type \"add podcast\".\nTo delete a podcast type \"delete podcast\".\nTo quit the program type \"quit\".\n\nWhat to do?\n");

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
                        if (title.isEmpty() || author.isEmpty()) {
                            System.out.println("\nEither title or author is not valid (cannot be empty)");
                        } else if (bookDAO.create(new Book(title, author, ISBN)) == null) {
                            System.out.println("\nBook has already been added in the library");
                        } else {
                            System.out.println("\nBook added!");
                        }
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
                    System.out.println("");
                    try {
                        List<Podcast> podcasts = podcastDAO.findAll();
                        for (Podcast podcast : podcasts) {
                            System.out.println(podcast);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (command.equals("delete book")) {
                    System.out.println("Title:");
                    String title = br.readLine();
                    System.out.println("Author:");
                    String author = br.readLine();
                    try {
                        if (bookDAO.delete(new Book(title, author))) {
                            System.out.println("\nBook deleted!");
                        } else {
                            System.out.println("\nNon-existent book cannot be deleted");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (command.equals("add podcast")) {
                    String podName;
                    String podAuthor;
                    String podTitle;
                    String podUrl;
                    System.out.println("");
                    System.out.println("Name:");
                    podName = br.readLine();
                    System.out.println("Author:");
                    podAuthor = br.readLine();
                    System.out.println("Title:");
                    podTitle = br.readLine();
                    System.out.println("Url:");
                    podUrl = br.readLine();
                    if (podName.isEmpty() || podAuthor.isEmpty() || podTitle.isEmpty()) {
                        System.out.println("Either name, author or title is invalid (all must be non-empty)");
                    } else {
                        try {
                            if (podcastDAO.create(new Podcast(podName, podAuthor, podTitle, podUrl)) == null) {
                                System.out.println("\nPodcast has already been added in the library");
                            } else {
                                System.out.println("\nPodcast added!");
                            }
                        } catch (SQLException exe) {
                            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, exe);
                        }
                    }
                } else if (command.equals("delete podcast")) {
                    System.out.println("Name:");
                    String name = br.readLine();
                    System.out.println("Author:");
                    String author = br.readLine();
                    System.out.println("Title:");
                    String title = br.readLine();
                    Podcast deletable = new Podcast(author, title);
                    deletable.setName(name);
                    try {
                        if (podcastDAO.delete(deletable)) {
                            System.out.println("\nPodcast deleted!");
                        } else {
                            System.out.println("\nNon-existent podcast cannot be deleted");
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
