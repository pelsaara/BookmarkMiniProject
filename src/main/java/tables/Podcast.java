
package tables;

public class Podcast {
    private String name;
    private String author;
    private String title;
    private String url;

    public Podcast(String name, String author, String title, String url) {
        this.name = name;
        this.author = author;
        this.title = title;
        this.url = url;
    }

    public Podcast() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Podcast: " + "name: " + name + ", title: " + title + ", author: " + author + ", url: " + url;
    }
    
    
}
