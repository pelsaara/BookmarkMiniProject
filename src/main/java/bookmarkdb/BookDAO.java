package bookmarkdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import bookmarkmodels.Book;

/**
 * Class for accessing database table for bookmarks of type 'Book'.
 */
public class BookDAO implements AbstractDAO<Book, Integer> {
    
    private final AbstractDatabase database;
    
    public BookDAO(AbstractDatabase db) {
        database = db;
    }
    
    /**
     * Adds a book to the database table 'Book'
     * 
     * @param book to be added
     * @return
     * @throws SQLException 
     */
    @Override
    public Book create(Book book) throws SQLException {

        List<Book> allBooks = this.findAll();
        for (Book existingBook : allBooks) {
            if (book.getAuthor().equals(existingBook.getAuthor())
                    && book.getTitle().equals(existingBook.getTitle())) {
                return null;
            }
        }

        database.update("INSERT INTO Book(title, author, ISBN) VALUES (?, ?, ?)", book.getTitle(), book.getAuthor(), book.getISBN());
        
        return book;
    }

    @Override
    public Book findOne(Book b) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        Map<String, List<String>> results = database.query("SELECT * FROM Book");

        for (int i = 0; i < results.get("title").size(); i++) {
            Book book = new Book();
            for (String col : results.keySet()) {
                if (col.equals("title")) {
                    book.setTitle(results.get(col).get(i));
                } else if (col.equals("author")) {
                    book.setAuthor(results.get(col).get(i));
                } else if (col.equals("ISBN")) {
                    book.setISBN(results.get(col).get(i));
                }
            }
            books.add(book);
        }
        
        return books;
    }

    @Override
    public void update(Book b) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Book book) throws SQLException {
        int deleted = 
                database.update("DELETE FROM Book WHERE author=? AND title=?",
                        book.getAuthor(), book.getTitle());
        return deleted == 1;
    }

}