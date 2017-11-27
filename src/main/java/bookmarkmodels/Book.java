package bookmarkmodels;

/**
 * Class for representing bookmarks of type 'Book'.
 */
public class Book {
    private String title;
    private String author;
    private String ISBN;

    public Book(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "Book: " + "title: " + title + ", author: " + author + ", ISBN: " + ISBN;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(Book.class)) {
            return false;
        }
        
        Book comp = (Book) o;
        return comp.getAuthor().equals(this.author)
                && comp.getISBN().equals(this.ISBN)
                && comp.getTitle().equals(this.title);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.title.hashCode();
        hash = 79 * hash + this.author.hashCode();
        hash = 79 * hash + this.ISBN.hashCode();
        return hash;
    }
}
