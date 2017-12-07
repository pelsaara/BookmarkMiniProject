/**
 *
 */
package bookmarkdb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bookmarkmodels.Video;

/**
 * @author Admin
 *
 */
public class VideoDAO implements AbstractDAO<Video, Integer> {

    private final AbstractDatabase database;

    public VideoDAO(AbstractDatabase db) {
        database = db;
    }

    /**
     * Adds a video to the database table 'Video'
     *
     * @param video to be added
     * @return
     * @throws SQLException
     */
    @Override
    public Video create(Video video) throws SQLException {

        List<Video> allVideos = this.findAll();
        for (Video existingVideo : allVideos) {
            if (video.getURL().equalsIgnoreCase(existingVideo.getURL())) {
                return null;
            }
        }

        database.update("INSERT INTO Video(URL, title) VALUES (?, ?)", video.getURL(), video.getTitle());

        return video;
    }

    @Override
    public Video findOne(Video video) throws SQLException {
        Map<String, List<String>> results = database.query("SELECT * FROM Video WHERE URL=? AND title=?",
                video.getURL(), video.getTitle());

        if (results.get("URL").size() > 0) {
            Video found = new Video();
            for (String col : results.keySet()) {
                if (col.equalsIgnoreCase("URL")) {
                    found.setURL(results.get(col).get(0));
                } else if (col.equalsIgnoreCase("title")) {
                    found.setTitle(results.get(col).get(0));
                } else if (col.equalsIgnoreCase("checked")) {
                    found.setChecked(Integer.parseInt(results.get(col).get(0)));
                }
            }
            return found;
        }
        return null;
    }

    @Override
    public List<Video> findAll() throws SQLException {
        List<Video> videos = new ArrayList<>();
        Map<String, List<String>> results = database.query("SELECT * FROM Video");

        for (int i = 0; i < results.get("URL").size(); i++) {
            Video video = new Video();
            for (String col : results.keySet()) {
                if (col.equalsIgnoreCase("URL")) {
                    video.setURL(results.get(col).get(i));
                } else if (col.equalsIgnoreCase("title")) {
                    video.setTitle(results.get(col).get(i));
                } else if (col.equalsIgnoreCase("checked")) {
                    video.setChecked(Integer.parseInt(results.get(col).get(i)));
                }
            }
            videos.add(video);
        }

        return videos;
    }

    @Override
    public void update(Video oldVideo, Video newVideo) throws SQLException {
        if (oldVideo.getURL() != null) {
            database.update("UPDATE Video SET URL=?, title=? WHERE URL=? AND title=?", newVideo.getURL(),
                    newVideo.getTitle(), oldVideo.getURL(), oldVideo.getTitle());
        }
    }

    @Override
    public boolean delete(Video video) throws SQLException {
        int deleted = database.update("DELETE FROM Video WHERE URL=?", video.getURL());
        return deleted == 1;
    }

    @Override
    public List<Video> findAllWithKeyword(String s) throws SQLException {
        List<Video> videos = new ArrayList<>();
        String keyword = "\'%" + s.toUpperCase() + "\'%";
        Map<String, List<String>> results = database.query("SELECT * FROM Video"
                + " WHERE UPPER(url) LIKE ? OR UPPER(title) LIKE ?", keyword, keyword);

        for (int i = 0; i < results.get("URL").size(); i++) {
            Video video = new Video();
            for (String col : results.keySet()) {
                if (col.equalsIgnoreCase("URL")) {
                    video.setURL(results.get(col).get(i));
                } else if (col.equalsIgnoreCase("title")) {
                    video.setTitle(results.get(col).get(i));
                } else if (col.equalsIgnoreCase("checked")) {
                    video.setChecked(Integer.parseInt(results.get(col).get(i)));
                }
            }
            videos.add(video);
        }

        return videos;
    }

    @Override
    public void marksAsChecked(Video t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
