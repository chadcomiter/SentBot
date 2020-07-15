package sentbot.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;
import sentbot.model.Source.Sector;

public class NewsHeadlineDao extends SourceDao {
	protected ConnectionManager connectionManager;
	
	//Single pattern instantiation is limited to a single object.
	private static NewsHeadlineDao instance = null;
	protected NewsHeadlineDao() {
		connectionManager = new ConnectionManager();
	}
	public static NewsHeadlineDao getInstance() {
		if(instance == null) {
			instance = new NewsHeadlineDao();
		}
		return instance;
	}
	
	//Create NewsHeadline
	public NewsHeadline create(NewsHeadline newsHeadline) throws SQLException {
		//Insert into the superclass table (Source) first
		create(new Source(newsHeadline.getSourceKey(), newsHeadline.getSentiment(), 
				newsHeadline.getSector()));
		//Then, we can insert the rest of the NewsHeadline
		String insertNewsHeadline = "INSERT INTO NewsHeadline(NewsSource,Headline,SourceKey) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertNewsHeadline);
			insertStmt.setString(1, newsHeadline.getNewsSource());
			insertStmt.setString(2, newsHeadline.getHeadline());
			insertStmt.setInt(3, newsHeadline.getSourceKey());
			insertStmt.executeUpdate();
			return newsHeadline;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
		}
	}
	
	
	//Read NewsHeadlines by Sector, NewsSource, AND Sentiment
	public List<NewsHeadline> getNewsHeadlineBySectorSourceSentiment(Sector sector, String sentiment, String newsSource) throws SQLException {
		List<NewsHeadline> newsHeadline = new ArrayList<NewsHeadline>();
		String selectNewsHeadlineByFactors = 
			"SELECT nh.SourceKey AS SourceKey, Sentiment, Sector, NewsSource, Headline "
			+ "FROM NewsHeadline AS nh INNER JOIN Source AS s ON nh.SourceKey = s.SourceKey "
			+ "WHERE Sector=? AND Sentiment=? AND NewsSource=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNewsHeadlineByFactors);
			selectStmt.setString(1, (sector.toString()));
			selectStmt.setString(2, sentiment);
			selectStmt.setString(3, newsSource);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int sKey = results.getInt("SourceKey");
				String sent = results.getString("Sentiment");
				Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
				String nSource = results.getString("NewsSource");
				String hLine = results.getString("Headline");
				NewsHeadline newsHeadlineAdd = new NewsHeadline(sKey, sent, sec, nSource, hLine);
				newsHeadline.add(newsHeadlineAdd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return newsHeadline;
}
	
	
	//Read NewsHeadlines by Sector, NewsSource, AND Sentiment
		public NewsHeadline getNewsHeadlineBySourceKey(int sourcekey) throws SQLException {
			NewsHeadline newsHeadlineAdd;
			String selectNewsHeadlineByFactors = 
				"SELECT nh.SourceKey AS SourceKey, Sentiment, Sector, NewsSource, Headline "
				+ "FROM NewsHeadline AS nh INNER JOIN Source AS s ON nh.SourceKey = s.SourceKey "
				+ "WHERE nh.SourceKey = ? ;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectNewsHeadlineByFactors);
				selectStmt.setInt(1, (sourcekey));
				results = selectStmt.executeQuery();
					int sKey = results.getInt("SourceKey");
					String sent = results.getString("Sentiment");
					Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
					String nSource = results.getString("NewsSource");
					String hLine = results.getString("Headline");
					newsHeadlineAdd = new NewsHeadline(sKey, sent, sec, nSource, hLine);
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return newsHeadlineAdd;
	}
	
	

	//Read NewsHeadlines by Sector and Sentiment
	public List<NewsHeadline> getNewsHeadlineBySectorSentiment(Sector sector, String sentiment) throws SQLException {
		List<NewsHeadline> newsHeadline = new ArrayList<NewsHeadline>();
		String selectNewsHeadlineByFactors = 
			"SELECT nh.SourceKey AS SourceKey, Sentiment, Sector, NewsSource, Headline "
			+ "FROM NewsHeadline AS nh INNER JOIN Source AS s ON nh.SourceKey = s.SourceKey "
			+ "WHERE Sector=? AND Sentiment=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNewsHeadlineByFactors);
			selectStmt.setString(1, (sector.toString()));
			selectStmt.setString(2, sentiment);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int sKey = results.getInt("SourceKey");
				String sent = results.getString("Sentiment");
				Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
				String nSource = results.getString("NewsSource");
				String hLine = results.getString("Headline");
				NewsHeadline newsHeadlineAdd = new NewsHeadline(sKey, sent, sec, nSource, hLine);
				newsHeadline.add(newsHeadlineAdd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return newsHeadline;
}


	//Read NewsHeadlines by Sector 
	public List<NewsHeadline> getNewsHeadlineBySentiment(String sector) throws SQLException {
		List<NewsHeadline> newsHeadline = new ArrayList<NewsHeadline>();
		String selectNewsHeadlineByFactors = 
			"SELECT nh.SourceKey AS SourceKey, Sentiment, Sector, NewsSource, Headline "
			+ "FROM NewsHeadline AS nh INNER JOIN Source AS s ON nh.SourceKey = s.SourceKey "
			+ "WHERE Sentiment=? ;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNewsHeadlineByFactors);
			selectStmt.setString(1, (sector));
			results = selectStmt.executeQuery();
			while(results.next()) {
				int sKey = results.getInt("SourceKey");
				String sent = results.getString("Sentiment");
				Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
				String nSource = results.getString("NewsSource");
				String hLine = results.getString("Headline");
				NewsHeadline newsHeadlineAdd = new NewsHeadline(sKey, sent, sec, nSource, hLine);
				newsHeadline.add(newsHeadlineAdd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(selectStmt != null) {
				selectStmt.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return newsHeadline;
}


	

		
		public Source updateSentiment(NewsHeadline newsHeadline, String newSentiment) throws SQLException {
			String updateNewSentiment = "UPDATE NewsHeadline SET Headline=? WHERE SourceKey=?;";
			Connection connection = null;
			PreparedStatement updateStmt = null;
			try {
				connection = connectionManager.getConnection();
				updateStmt = connection.prepareStatement(updateNewSentiment);
				updateStmt.setString(1, newSentiment);
				updateStmt.setInt(2, newsHeadline.getSourceKey());
				updateStmt.executeLargeUpdate();
				newsHeadline.setHeadline(newSentiment);
				return newsHeadline;
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(updateStmt !=null) {
					updateStmt.close();
				}
			}
		}
		
		
		
		

	
	//Delete NewsHeadline	
	public NewsHeadline delete(NewsHeadline newsHeadline) throws SQLException {
		
		String deleteNewsHeadline = "DELETE FROM NewsHeadline WHERE SourceKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteNewsHeadline);
			deleteStmt.setInt(1, newsHeadline.getSourceKey());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for SourceKey=" + newsHeadline.getSourceKey());
			}
			//Now we also delete the matching record from superclass Source
			//Due to the FK constraint we can simply call super.delete() without needing
			//to delete first from NewsHeadline.
			//super.delete(newsHeadline.getSourceKey());
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			} 
			if (deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	
}
