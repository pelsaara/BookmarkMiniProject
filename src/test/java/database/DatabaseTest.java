package database;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;


    private Database database;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private static final String CREATE_STMT = "CREATE TABLE Book";

    public DatabaseTest() {
    }

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));

        when(stmt.executeQuery()).thenReturn(rs);
        when(stmt.executeUpdate()).thenReturn(2);
        when(connection.prepareStatement(any(String.class))).thenReturn(stmt);

        database = new Database(() -> connection);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testInit() {
        database.init();
        String output = outContent.toString();
        assertTrue(output.contains(CREATE_STMT));
    }

    @Test
    public void testSetDebugMode() throws SQLException {
        database.setDebugMode(true);
        database.update(CREATE_STMT);
        String output = outContent.toString();
        assertTrue(output.contains("Changed rows: "));
    }

    @Test
    public void testSetDebugMode2() throws SQLException {
        database.setDebugMode(true);
        database.query(CREATE_STMT);
        String output = outContent.toString();
        assertTrue(output.contains("Executing: "));
    }

    @Test
    public void testUpdate() throws Exception {
        int changes = database.update(CREATE_STMT);
        assertEquals(changes, 2);
    }

    @Test
    public void testQuery() throws Exception {
        
    }
}
