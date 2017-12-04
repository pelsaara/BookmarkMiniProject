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
	 * @param video
	 *            to be added
	 * @return
	 * @throws SQLException
	 */
	@Override
	public Video create(Video video) throws SQLException {

		List<Video> allVideos = this.findAll();
		for (Video existingVideo : allVideos) {
			if (video.getURL().equals(existingVideo.getURL())) {
				return null;
			}
		}

		database.update("INSERT INTO Video(URL, title) VALUES (?, ?)", video.getURL(), video.getTitle());

		return video;
	}

	@Override
	public Video findOne(Video b) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public List<Video> findAll() throws SQLException {
		List<Video> videos = new ArrayList<>();
		Map<String, List<String>> results = database.query("SELECT * FROM Video");

		for (int i = 0; i < results.get("URL").size(); i++) {
			Video video = new Video();
			for (String col : results.keySet()) {
				if (col.equals("URL")) {
					video.setURL(results.get(col).get(i));
				} else if (col.equals("title")) {
					video.setTitle(results.get(col).get(i));
				}
			}
			videos.add(video);
		}

		return videos;
	}

	@Override
	public void update(Video b, Video c) throws SQLException {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

	@Override
	public boolean delete(Video video) throws SQLException {
		int deleted = database.update("DELETE FROM Video WHERE URL=?", video.getURL());
		return deleted == 1;
	}
}