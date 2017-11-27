
package cukestests;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.BookDAO;
import database.Connector;
import database.Database;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import tables.Book;
import ui.UI;

public class Stepdefs {

    List<String> inputLines = new ArrayList<>();
    Database database;
    UI ui;
    BufferedReader buffer;
    Book newBook;
    BookDAO bookDao;

    @Given("^command \"([^\"]*)\" is selected$")
    public void command_add_book_selected(String command) throws Throwable {
        inputLines.add(command);
    }

    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" are entered$")
    public void title_and_author_and_ISBN_are_entered(String title, String author, String ISBN) throws Throwable {
//        inputLines.add(title);
//        inputLines.add(author);
//        inputLines.add(ISBN);
//        
//        newBook = new Book(title, author, ISBN);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {
//        inputLines.add("quit");
//        buffer = new BufferedReader(new InputStreamReader((InputStream) inputLines));
//        database = new Database(new Connector("jdbc:sqlite:cukesTest.db"));
//        bookDao = new BookDAO(database);
//        ui = new UI(database, buffer);
//
//        ui.run();
//        
//        List<Book> allBooks = bookDao.findAll();
//        assertEquals(allBooks.size(), 1);
//        assertEquals(allBooks.get(0), newBook);
    }

}
