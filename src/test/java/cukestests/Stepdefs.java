package cukestests;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import bookmarkdb.BookDAO;
import bookmarkdb.Connector;
import bookmarkdb.Database;
import bookmarkdb.PodcastDAO;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.sql.SQLException;
import static org.junit.Assert.*;
import java.util.List;
import bookmarkmodels.Book;
import bookmarkmodels.Podcast;
import ui.UI;

public class Stepdefs {

    Database database;
    UI ui;
    BufferedReader buffer;
    BookDAO bookDao;
    PodcastDAO podcastDao;
    ByteArrayInputStream inputStream;
    ByteArrayOutputStream outputStream;
    PrintStream standardOut;
    InputStream standardIn;
    String input;

    /**
     * Before test execution 
     * 1 standard system input and output are stored in class variables
     * {@link #standardIn} and {@link #standardOut}, respectively
     * 2 class variable {@link #input}, which is later used to simulate input, is
     * set to empty string
     * 3 new database file is created, a new {@link Database} object is
     * initialised with it and stored in variable {@link #database}.
     */
    @Before
    public void setUp() {
        input = "";
        standardIn = System.in;
        standardOut = System.out;
        database = new Database(new Connector("jdbc:sqlite:cukesTest.db"));
        database.init();
        bookDao = new BookDAO(database);
        podcastDao = new PodcastDAO(database);
    }

    /**
     * After test execution system input and output are restored to their
     * original values and test database file is deleted.
     */
    @After
    public void tearDown() throws IOException {
        System.setIn(standardIn);
        System.setOut(standardOut);
        Files.deleteIfExists(FileSystems.getDefault().getPath("cukesTest.db"));
    }

    @Given("^command \"([^\"]*)\" is selected$")
    public void command_selected(String command) throws Throwable {
        addInputLine(command);
    }
    
    @Given("^book with title \"([^\"]*)\", author \"([^\"]*)\" and ISBN \"([^\"]*)\" has been added$")
    public void book_has_been_added(String title, String author, String ISBN) throws Throwable {
        addInputLine("add book");
        addInputLine(title);
        addInputLine(author);
        addInputLine(ISBN);
    }
    

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" are entered$")
    public void title_and_author_and_ISBN_are_entered(String title, String author, String ISBN) throws Throwable {
        addInputLine(title);
        addInputLine(author);
        addInputLine(ISBN);
    }
    
    @When("^name \"([^\"]*)\" and author \"([^\"]*)\" and title \"([^\"]*)\" and URL \"([^\"]*)\" are entered$")
    public void name_and_title_and_author_and_URL_are_entered(String name, String author, String title, String URL) throws Throwable {
    	addInputLine(name);
    	addInputLine(author);
    	addInputLine(title);
        addInputLine(URL);
    }

    @Then("^new book is added with title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\"$")
    public void new_book_is_added(String title, String author, String ISBN) throws Throwable {
        Book addedBook = new Book(title, author, ISBN);

        addInputLine("quit");
        setIOStreams();

        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();

        List<Book> allBooks = bookDao.findAll();

        assertEquals(allBooks.size(), 1);
        assertEquals(allBooks.get(0), addedBook);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Book added!"));
    }

    @Then("^new podcast is added with name \"([^\"]*)\" and author \"([^\"]*)\" and title \"([^\"]*)\" and URL \"([^\"]*)\"$")
    public void new_podcast_is_added(String name, String author, String title, String URL) throws Throwable {
        Podcast addedPodcast = new Podcast(name, author, title, URL);

        addInputLine("quit");
        setIOStreams();

        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();

        List<Podcast> allPodcasts = podcastDao.findAll();

        assertEquals(allPodcasts.size(), 1);
        assertEquals(allPodcasts.get(0), addedPodcast);
        
        String output = outputStream.toString();
        assertTrue(output.contains("Podcast added!"));
    }
    
    @Then("^only one book is added with title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\"$")
    public void only_one_book_is_added(String title, String author, String ISBN) throws Throwable {
        Book addedBook = new Book(title, author, ISBN);

        addInputLine("quit");
        setIOStreams();

        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();

        List<Book> allBooks = bookDao.findAll();

        assertEquals(allBooks.size(), 1);
        assertEquals(allBooks.get(0), addedBook);
        String output = outputStream.toString();
        
        assertTrue(output.contains("Book added!"));
        assertTrue(output.contains("Book has already been added in the library"));
    }

    @Then("^book with title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" is not added$")
    public void book_without_title_or_author_is_not_added(String title, String author, String ISBN) throws Throwable {
        Book notAddedBook = new Book(title, author, ISBN);
        addInputLine("quit");
        setIOStreams();

        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();

        List<Book> allBooks = bookDao.findAll();
        assertFalse(allBooks.contains(notAddedBook));

        String output = outputStream.toString();
        assertTrue(output.contains("Either title or author is not valid (cannot be empty)"));
    }
    
    @Then("^empty list of books is printed$")
    public void empty_list_of_books_is_printed() throws Throwable {
        addInputLine("quit");
        setIOStreams();
        
        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();
        
        String output = outputStream.toString();
        assertTrue(!output.contains("Book:"));
    }
    
    @Then("^book with title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" is printed$")
    public void book_is_printed(String title, String author, String ISBN) throws Throwable {
        Book book = new Book(title, author, ISBN);
        addInputLine("quit");
        setIOStreams();
        
        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();
        
        String output = outputStream.toString();
        assertTrue(output.contains(book.toString()));
    }
    
    @Then("^book with title \"([^\"]*)\" and book with title \"([^\"]*)\" is printed$")
    public void two_books_are_printed(String title1, String title2) throws Throwable {
        addInputLine("quit");
        setIOStreams();
        
        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();
        
        String output = outputStream.toString();
        assertTrue(output.contains(title1) && output.contains(title2));
    }

    /**
     * Sets system input as a byte array, which represents variable
     * {@link #input} and output as variable {@link #outputStream}.
     */
    private void setIOStreams() {
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Adds a line to the input, which simulates same behaviour as typing text
     * and pressing enter in the command line when it is read.
     */
    private void addInputLine(String string) {
        input += System.getProperty("line.separator") + string;
    }
}
