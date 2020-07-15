package sentbot.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;
import sentbot.model.Source.Sector;

public class SourceDao {
	protected ConnectionManager connectionManager;
	
	//Single pattern: instantiation is limited to one object.
	private static SourceDao instance = null;
	protected SourceDao() {
		connectionManager = new ConnectionManager();
	}
	public static SourceDao getInstance() {
		if(instance == null) {
			instance = new SourceDao();
		}
		return instance;
	}
	
	//Create Source
	public Source create(Source source) throws SQLException {
		String insertSource = "INSERT INTO Source(SourceKey, Sentiment, Sector) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSource);
			insertStmt.setInt(1, source.getSourceKey());
			insertStmt.setString(2, source.getSentiment());
			insertStmt.setString(3, source.getSector().name());
			insertStmt.executeUpdate();
			return source;
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
	
	//Read Source by SourceKey
	public Source getSourceBySourceKey(int SourceKey) throws SQLException {
		String selectSource = "SELECT SourceKey, Sentiment, Sector FROM Source WHERE SourceKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSource);
			selectStmt.setInt(1, SourceKey);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int sKey = results.getInt("SourceKey");
				String sent = results.getString("Sentiment");
				Sector sector = Sector.valueOf(results.getString("Sector"));
				Source source = new Source(sKey, sent, sector);
				return source;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(results != null) {
				results.close();
			}
		}
		return null;
	}
	
	//Read Sources By Sector
	public List<Source> getSourcesBySentiment(String Sentiment) throws SQLException {
		List<Source> source = new ArrayList<Source>();
		String selectSource = "SELECT SourceKey, Sentiment, Sector FROM Source WHERE Sentiment=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSource);
			selectStmt.setString(1, Sentiment);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int sKey = results.getInt("SourceKey");
				String sent = results.getString("Sentiment");
				Sector sector = Sector.valueOf(results.getString("Sector"));
				Source sourceAdd = new Source(sKey, sent, sector);
				source.add(sourceAdd);
			}
		}	catch (SQLException e) {
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
			return source;
		}
	
	//Update Source Sentiment
	public Source updateSentiment(Source source, String newSentiment) throws SQLException {
		String updateNewSentiment = "UPDATE Source SET Sentiment=? WHERE SourceKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateNewSentiment);
			updateStmt.setString(1, newSentiment);
			updateStmt.setInt(2, source.getSourceKey());
			updateStmt.executeLargeUpdate();
			source.setSentiment(newSentiment);
			return source;
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
	
	
	//Delete Source
	public Source delete(Source source) throws SQLException {
		String deleteSource = "DELETE FROM Source WHERE SourceKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteSource);
			deleteStmt.setInt(1, source.getSourceKey());
			deleteStmt.executeUpdate();
			//Let's return null so that the caller can no longer operate on Source
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
}