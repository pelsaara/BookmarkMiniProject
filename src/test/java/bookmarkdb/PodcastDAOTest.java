package bookmarkdb;

import bookmarkdb.Database;
import bookmarkdb.PodcastDAO;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;
import bookmarkmodels.Podcast;
import org.mockito.internal.verification.Times;

@RunWith(MockitoJUnitRunner.class)
public class PodcastDAOTest {

    private PodcastDAO podDAO;
    private Database database;
    private Map<String, List<String>> results;
    private Podcast newPodcast;

    @Before
    public void setup() throws Exception {
        database = mock(Database.class);
        podDAO = new PodcastDAO(database);
        results = new HashMap<String, List<String>>();

        newPodcast = new Podcast("Name", "Author", "Title", "Url");
        results.put("name", Arrays.asList("Name"));
        results.put("author", Arrays.asList("Author"));
        results.put("title", Arrays.asList("Title"));
        results.put("url", Arrays.asList("Url"));
    }

    @After
    public void tearDown() throws Exception {
        database.update("DROP TABLE Podcast");
    }

    @Test
    public void testCreate() throws SQLException {
        podDAO.create(newPodcast);

        verify(database).update(eq("INSERT INTO Podcast(name, title, author, url) VALUES (?, ?, ?, ?)"),
                eq("Name"), eq("Title"), eq("Author"), eq("Url"));
    }

    @Test
    public void testDelete() throws SQLException {
        podDAO.create(newPodcast);
        Podcast newerPodcast = new Podcast("Name2", "Author2", "Title2", "Url2");
        podDAO.create(newerPodcast);

        podDAO.delete(newerPodcast);
        verify(database).update("DELETE FROM Podcast WHERE author=? AND title=? VALUES (?, ?, ?, ?)", eq("Author2"), eq("Title2"));
    }

//    @Test
//    public void testFindOne() {
//        
//    }
    @Test
    public void testFindAll() throws SQLException {
        Podcast newerPodcast = new Podcast("Name2", "Author2", "Title2", "Url2");
        results.put("name", Arrays.asList("Name2"));
        results.put("author", Arrays.asList("Author2"));
        results.put("title", Arrays.asList("Title2"));
        results.put("url", Arrays.asList("Url2"));

        podDAO.create(newPodcast);
        podDAO.create(newerPodcast);

        when(database.query("SELECT * FROM Podcast")).thenReturn(results);

        podDAO.findAll();

        verify(database).query(eq("SELECT * FROM Podcast"));
    }

//    @Test
//    public void testUpdate()  {
//        
//    }
}
