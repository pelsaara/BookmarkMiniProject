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

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" are entered$")
    public void title_and_author_and_ISBN_are_entered(String title, String author, String ISBN) throws Throwable {
        addInputLine(title);
        addInputLine(author);
        addInputLine(ISBN);

        newBook = new Book(title, author, ISBN);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {

        addInputLine("quit");
        setIOStreams();

        buffer = new BufferedReader(new InputStreamReader(System.in));
        ui = new UI(database, buffer);
        ui.run();

        List<Book> allBooks = bookDao.findAll();

        assertEquals(allBooks.size(), 1);
        assertEquals(allBooks.get(0), newBook);
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
