package database;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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

    @Mock
    private Statement statement;


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
        when(connection.createStatement()).thenReturn(statement);

        database = new Database(() -> connection);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testInitCallsExecuteUpdateAndPrintsOutProcessedStatements()
            throws SQLException {
        database.init();
        String output = outContent.toString();

        verify(statement).executeUpdate(any(String.class));
        assertTrue(output.contains(CREATE_STMT));
    }

    @Test
    public void testDebugModeSetToTrue() throws SQLException {
        database.setDebugMode(true);
        database.query(CREATE_STMT);
        database.update(CREATE_STMT);
        String output = outContent.toString();

        assertTrue(output.contains("Executing: "));
        assertTrue(output.contains("Changed rows: "));
    }

    @Test
    public void testDebugModeSetToFalse() throws SQLException {
        database.setDebugMode(false);
        database.query(CREATE_STMT);
        database.update(CREATE_STMT);
        String output = outContent.toString();

        assertFalse(output.contains("Executing: "));
        assertFalse(output.contains("Changed rows: "));
    }

    @Test
    public void testUpdateWithoutParams() throws Exception {
        int changes = database.update(CREATE_STMT);

        verify(stmt, never()).setObject(any(Integer.class), any(String.class));
        assertEquals(changes, 2);
    }

    @Test
    public void testUpdateWithTwoParams() throws Exception {
        String param = "Param";
        int param2 = 4;
        database.update(CREATE_STMT, param, param2);

        verify(stmt).setObject(1, param);
        verify(stmt).setObject(2, param2);
    }

    @Test
    public void testQueryWithoutParams() throws Exception {
        database.query(CREATE_STMT);

        verify(stmt, never()).setObject(any(Integer.class), any(String.class));
        verify(stmt).executeQuery();
    }

    @Test
    public void testQueryWithTwoParams() throws Exception {
        String param = "Param";
        int parami = 5;
        database.query(CREATE_STMT, param, parami);

        verify(stmt).setObject(1, param);
        verify(stmt).setObject(2, parami);
    }
}
