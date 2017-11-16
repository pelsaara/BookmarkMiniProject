package database;

import java.sql.SQLException;
import java.util.List;
import tables.Book;

/**
 * Class for accessing database table for bookmarks of type 'Book'.
 */
public class BookDAO implements AbstractDAO<Book, Integer> {
    
    private final AbstractDatabase database;
    
    public BookDAO(AbstractDatabase db) {
        database = db;
    }

    @Override
    public Book create(Book t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Integer key, Book t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}