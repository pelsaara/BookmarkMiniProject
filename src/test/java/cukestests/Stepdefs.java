package cukestests;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.BookDAO;
import database.Connector;
import database.Database;
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
import tables.Book;
import ui.UI;

public class Stepdefs {

    Database database;
    UI ui;
    BufferedReader buffer;
    Book newBook;
    BookDAO bookDao;
    ByteArrayInputStream inputStream;
    ByteArrayOutputStream outputStream;
    PrintStream standardOut;
    InputStream standardIn;
    String input;

    @Before
    public void setUp() {
        input = "";
        standardIn = System.in;
        standardOut = System.out;
        database = new Database(new Connector("jdbc:sqlite:cukesTest.db"));
        database.init();
        bookDao = new BookDAO(database);
    }

    @After
    public void tearDown() throws IOException {
        System.setIn(standardIn);
        System.setOut(standardOut);
        Files.deleteIfExists(FileSystems.getDefault().getPath("cukesTest.db"));
    }

    @Given("^command \"([^\"]*)\" is selected$")
    public void command_add_book_selected(String command) throws Throwable {
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

    private void setIOStreams() {
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    private void addInputLine(String string) {
        input += System.getProperty("line.separator") + string;
    }
}
