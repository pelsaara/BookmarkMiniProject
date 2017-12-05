package ui;

import bookmarkdb.BookDAO;
import bookmarkdb.Database;
import bookmarkdb.PodcastDAO;
import bookmarkdb.VideoDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import bookmarkmodels.Book;
import bookmarkmodels.Podcast;
import bookmarkmodels.Video;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UI implements Runnable {

    private BufferedReader br;
    private BookDAO bookDAO;
    private PodcastDAO podcastDAO;
    private VideoDAO videoDAO;
    private Desktop desktop;

    public UI(Database database) {
        br = new BufferedReader(new InputStreamReader(System.in));
        bookDAO = new BookDAO(database);
        podcastDAO = new PodcastDAO(database);
        videoDAO = new VideoDAO(database);
        desktop = Desktop.getDesktop();
    }

    public UI(Database database, BufferedReader buff, Desktop dt) {
        this.br = buff;
        bookDAO = new BookDAO(database);
        podcastDAO = new PodcastDAO(database);
        videoDAO = new VideoDAO(database);
        desktop = dt;
    }

    @Override
    public void run() {

        while (true) {
            System.out.println("\nTo list all your bookmarks type \"browse\".\n"
            		+ "To add a book type \"add book\".\n"
                        + "To edit a book type \"edit book\".\n"
            		+ "To delete a book type \"delete book\".\n"
            		+ "To add a podcast type \"add podcast\".\n"
                        + "To edit a podcast type \"edit podcast\".\n"
            		+ "To delete a podcast type \"delete podcast\".\n"
                        + "To add a video type \"add video\".\n"
                        + "To edit a video type \"edit video\".\n"
            		+ "To delete a video type \"delete video\".\n"
            		+ "To quit the program type \"quit\".\n\n"
            		+ "What to do?\n");

            String command;
            try {
                command = br.readLine();
                if (command.equals("quit")) {
                    break;
                } else if (command.equals("browse")) {
                	commandBrowse();
                } else if (command.equals("add book")) {
                	commandAddBook();
                } else if (command.equals("edit book")) {
                	commandEditBook();
                } else if (command.equals("delete book")) {
                	commandDeleteBook();
                } else if (command.equals("add podcast")) {
                	commandAddPodcast();
                } else if (command.equals("edit podcast")) {
                	commandEditPodcast();
                } else if (command.equals("delete podcast")) {
                	commandDeletePodcast();
                } else if (command.equals("add video")) {
                	commandAddVideo();
                } else if (command.equals("edit video")) {
                	commandEditVideo();
                } else if (command.equals("delete video")) {
                	commandDeleteVideo();
                } else if (command.equals("open video")) {
                        commandOpenVideoURL();
                }
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void commandBrowse() throws IOException {
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
        System.out.println("");
        try {
            List<Video> videos = videoDAO.findAll();
            for (Video video : videos) {
                System.out.println(video);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void commandAddBook() throws IOException {
        String author;
        String title;
        String ISBN;
        System.out.println("");
        System.out.println("Title:");
        title = br.readLine();
        while (title.isEmpty()) {
            System.out.println("Title cannot be empty. Enter title again:");
            title = br.readLine();
        }
        System.out.println("Author:");
        author = br.readLine();
        while (author.isEmpty()) {
            System.out.println("Author cannot be empty. Enter author again:");
            author = br.readLine();
        }
        System.out.println("ISBN:");
        ISBN = br.readLine();
        try {
            if (bookDAO.create(new Book(title, author, ISBN)) == null) {
                System.out.println("\nBook has already been added in the library");
            } else {
                System.out.println("\nBook added!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void commandEditBook() throws IOException {
        System.out.println("Which book do you want to edit?");
        System.out.println("Title:");
        String title = br.readLine();
        System.out.println("Author");
        String author = br.readLine();
        Book toEdit = new Book(title, author);
        try {
            if (title.isEmpty() || author.isEmpty()) {
                System.out.println("\nEither title or author is not valid (cannot be empty)");
            } else if (bookDAO.findOne(toEdit) == null) {
                System.out.println("There is no such book in the database.");
            } else {
                editBook(toEdit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editBook(Book book) throws IOException {
        System.out.println("Enter new title (leave empty if no need to edit):");
        String title = br.readLine();
        if (title.isEmpty()) {
            title = book.getTitle();
        }
        System.out.println("Enter new author (leave empty if no need to edit):");
        String author = br.readLine();
        if (author.isEmpty()) {
            author = book.getAuthor();
        }
        System.out.println("Enter new ISBN (leave empty if no need to edit):");
        String ISBN = br.readLine();
        if (ISBN.isEmpty()) {
            ISBN = book.getISBN();
        }
        try {
            bookDAO.update(book, new Book(title, author, ISBN));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void commandDeleteBook() throws IOException {
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
    }

    private void commandAddPodcast() throws IOException {
        String podName;
        String podAuthor;
        String podTitle;
        String podUrl;
        System.out.println("");
        System.out.println("Name:");
        podName = br.readLine();
        while (podName.isEmpty()) {
            System.out.println("Name cannot be empty. Enter name again:");
            podName = br.readLine();
        }
        System.out.println("Author:");
        podAuthor = br.readLine();
        while (podAuthor.isEmpty()) {
            System.out.println("Author cannot be empty. Enter author again:");
            podAuthor = br.readLine();
        }
        System.out.println("Title:");
        podTitle = br.readLine();
        while (podTitle.isEmpty()) {
            System.out.println("Title cannot be empty. Enter title again:");
            podTitle = br.readLine();
        }
        System.out.println("Url:");
        podUrl = br.readLine();
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

    private void commandEditPodcast() throws IOException {
        System.out.println("Which podcast do you want to edit?");
        System.out.println("Name:");
        String name = br.readLine();
        System.out.println("Author:");
        String author = br.readLine();
        System.out.println("Title:");
        String title = br.readLine();
        Podcast toEdit = new Podcast(name, author, title);
        try {
            if (name.isEmpty() || title.isEmpty() || author.isEmpty()) {
                System.out.println("\nEither name, title or author is not valid (cannot be empty)");
            } else if (podcastDAO.findOne(toEdit) == null) {
                System.out.println("There is no such podcast in the database.");
            } else {
                editPodcast(toEdit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editPodcast(Podcast podcast) throws IOException {
        System.out.println("Enter new name (leave empty if no need to edit):");
        String name = br.readLine();
        if (name.isEmpty()) {
            name = podcast.getName();
        }
        System.out.println("Enter new author (leave empty if no need to edit):");
        String author = br.readLine();
        if (author.isEmpty()) {
            author = podcast.getAuthor();
        }
        System.out.println("Enter new title (leave empty if no need to edit):");
        String title = br.readLine();
        if (title.isEmpty()) {
            title = podcast.getTitle();
        }
        System.out.println("Enter new url (leave empty if no need to edit):");
        String url = br.readLine();
        if (url.isEmpty()) {
            url = podcast.getUrl();
        }
        try {
            podcastDAO.update(podcast, new Podcast(name, author, title, url));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void commandDeletePodcast() throws IOException {
        System.out.println("Name:");
        String name = br.readLine();
        System.out.println("Author:");
        String author = br.readLine();
        System.out.println("Title:");
        String title = br.readLine();
        Podcast deletable = new Podcast(name, author, title);
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
    
    private void commandOpenVideoURL() {
        System.out.println("");
        try {
            List<Video> videos = videoDAO.findAll();
            for (int i = 0; i < videos.size(); i++) {
                System.out.println((i + 1) + " " + videos.get(i));
            }
            System.out.println("\nWhich video would you like to open?"
                    + "\nPlease enter a row number or \"cancel\" to return to main menu: ");
            int index = getRowNumber(videos.size());

            if (index == -1) {
                return;
            }

            String url = videos.get(index - 1).getURL();
            url = formatURL(url);

            URI uri = new URI(url);
            desktop.browse(uri);
        } catch (SQLException | URISyntaxException | IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int getRowNumber(int maxRowNumber) throws IOException {
        boolean noRowNumber = true;
        int index = -1;
        while (noRowNumber) {
            try {
                String input = br.readLine();
                if (input.equals("cancel")) {
                    return -1;
                }
                index = Integer.parseInt(input);
                if (index <= 0 || index > maxRowNumber) {
                    System.out.println("\nThe row number entered was invalid");
                } else {
                    noRowNumber = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a number");
            }
        }
        return index;
    }

    private String formatURL(String url) {
        if (url.startsWith("https://") || url.startsWith("http://")) {
            return url;
        }
        return "https://" + url;
    }

    private void commandAddVideo() throws IOException {
        String url;
        String name;
        System.out.println("");
        System.out.println("Url:");
        url = br.readLine();
        while (url.isEmpty()) {
            System.out.println("Url cannot be empty. Enter URL again:");
            url = br.readLine();
        }
        System.out.println("Name:");
        name = br.readLine();
        try {
            if (videoDAO.create(new Video(url, name)) == null) {
                System.out.println("\nVideo has already been added in the library");
            } else {
                System.out.println("\nVideo added!");
            }
        } catch (SQLException exe) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, exe);
        }
        
    }

    private void commandEditVideo() throws IOException {
        System.out.println("Which video do you want to edit?");
        System.out.println("Title:");
        String title = br.readLine();
        System.out.println("Url:");
        String url = br.readLine();
        Video toEdit = new Video(title, url);
        try {
            if (title.isEmpty() || url.isEmpty()) {
                System.out.println("\nEither name, title or author is not valid (cannot be empty)");
            } else if (videoDAO.findOne(toEdit) == null) {
                System.out.println("There is no such podcast in the database.");
            } else {
                editVideo(toEdit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    private void editVideo(Video video) throws IOException {
        System.out.println("Enter new title (leave empty if no need to edit):");
        String title = br.readLine();
        if (title.isEmpty()) {
            title = video.getTitle();
        }
        System.out.println("Enter new url (leave empty if no need to edit):");
        String url = br.readLine();
        if (url.isEmpty()) {
            url = video.getURL();
        }
        try {
            videoDAO.update(video, new Video(title, url));
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    private void commandDeleteVideo() throws IOException {
        System.out.println("Url:");
        String url = br.readLine();
        Video deletable = new Video(url);
        try {
            if (videoDAO.delete(deletable)) {
                System.out.println("\nVideo deleted!");
            } else {
                System.out.println("\nNon-existent video cannot be deleted");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
