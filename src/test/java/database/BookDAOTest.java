package database;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ui.UI;
import tables.Book;

public class BookDAOTest {

	private BookDAO bookDAO;
	private Database database;

	@Before
	public void setUp() throws Exception {
        database = new Database(new Connector("jdbc:sqlite:unitTest.db"));
        database.init();

        bookDAO = new BookDAO(database);
		Book newBook = new Book("Title", "Author", "ISBN");
		bookDAO.create(newBook);
	}

	@After
	public void tearDown() throws Exception {
		database.update("DROP TABLE Book");
	}
	
	@Test
	public void testCreate() throws SQLException {
		Map<String, List<String>> results = database.query("SELECT * FROM Book WHERE title = \"Title\" AND author = \"Author\" AND ISBN = \"ISBN\"");
		
		// The results include each table column as its separate String key and the column's values as the List<String>
		// One row in the table => 3 key-value pairs in total
		assertEquals(results.size(), 3);
		
		assertEquals(results.get("title").get(0), "Title");
		assertEquals(results.get("author").get(0), "Author");
		assertEquals(results.get("ISBN").get(0), "ISBN");
	}

//	@Test
//	public void testFindOne() {
//		//fail("Not yet implemented");
//	}

	@Test
	public void testFindAll() throws SQLException {
		Book newerBook = new Book("Title2", "Author2", "ISBN2");
		bookDAO.create(newerBook);
		
		List<Book> books = bookDAO.findAll();
		
		assertEquals(books.size(), 2);
	}

//	@Test
//	public void testUpdate() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDelete() {
//		fail("Not yet implemented");
//	}
}
