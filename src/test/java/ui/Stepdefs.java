
package ui;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import database.Connector;
import database.Database;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class Stepdefs {


    List<String> inputLines = new ArrayList<>();

    @Given("^command add book is selected$")
    public void command_add_book_selected() throws Throwable {
        inputLines.add("add book");
    }
    
    @When("^title \"([^\"]*)\" and author \"([^\"]*)\" and ISBN \"([^\"]*)\" are entered$")
    public void title_and_author_and_ISBN_are_entered(String title, String author, String ISBN) throws Throwable {
        inputLines.add(title);
        inputLines.add(author);
        inputLines.add(ISBN);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String expectedOutput) throws Throwable {
        
    }

}
