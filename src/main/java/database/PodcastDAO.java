
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tables.Podcast;

/**
 *  Class for accessing database table for bookmarks of type 'Podcast'.
 */

public class PodcastDAO implements AbstractDAO<Podcast, Integer>{
    
    private final AbstractDatabase database;

    public PodcastDAO(AbstractDatabase database) {
        this.database = database;
    }
    
    

    @Override
    public Podcast create(Podcast podcast) throws SQLException {
        database.update("INSERT INTO Podcast(name, title, author, url) VALUES (?, ?, ?, ?)", podcast.getName(), podcast.getTitle(), podcast.getAuthor(), podcast.getUrl());
        
        return podcast;
    }

    @Override
    public Podcast findOne(Podcast p) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Podcast> findAll() throws SQLException {
        List<Podcast> podcasts = new ArrayList<>();
        Map<String, List<String>> results = database.query("SELECT * FROM Podcast");
        
        for (int i = 0; i < results.get(results.keySet().toArray()[0]).size(); i++) {
            Podcast podcast = new Podcast();
            for (String col : results.keySet()) {
                if (col.equals("name")) {
                    podcast.setName(results.get(col).get(i));
                } else if (col.equals("author")) {
                    podcast.setAuthor(results.get(col).get(i));
                } else if (col.equals("title")) {
                    podcast.setTitle(results.get(col).get(i));
                } else if (col.equals("url")) {
                    podcast.setUrl(results.get(col).get(i));
                }
            }
            podcasts.add(podcast);
        }
        return podcasts;
    }

    @Override
    public void update(Podcast p) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Podcast podcast) throws SQLException {
        database.update("DELETE FROM Podcast WHERE author=? AND title=?", podcast.getAuthor(), podcast.getTitle());
    }
}
