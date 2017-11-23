package database;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import tables.Book;

@RunWith(MockitoJUnitRunner.class)
public class BookDAOTest {

	private BookDAO bookDAO;
	private Database database;
	private Map<String, List<String>> results;
	private Book newBook;

	@Before
	public void setUp() throws Exception {
		database = mock(Database.class);	
		bookDAO = new BookDAO(database);
		results = new HashMap<String, List<String>>();
		
		newBook = new Book("Title", "Author", "ISBN");
		results.put("title", Arrays.asList("Title"));
		results.put("author", Arrays.asList("Author"));
		results.put("ISBN", Arrays.asList("ISBN"));
	}

	@After
	public void tearDown() throws Exception {
		database.update("DROP TABLE Book");
	}
	
	@Test
	public void testCreate() throws SQLException {
		bookDAO.create(newBook);
		
		verify(database).update(eq("INSERT INTO Book(title, author, ISBN) VALUES (?, ?, ?)"), 
				eq("Title"), eq("Author"), eq("ISBN"));
	}

//	@Test
//	public void testFindOne() {
//		//fail("Not yet implemented");
//	}

	@Test
	public void testFindAll() throws SQLException {		
		Book newerBook = new Book("Title2", "Author2", "ISBN2");
		results.put("title", Arrays.asList("Title2"));
		results.put("author", Arrays.asList("Author2"));
		results.put("ISBN", Arrays.asList("ISBN2"));
		
		bookDAO.create(newBook);
		bookDAO.create(newerBook);
		
		when(database.query("SELECT * FROM Book")).thenReturn(results);
		
		bookDAO.findAll();
		
		verify(database).query(eq("SELECT * FROM Book"));
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
