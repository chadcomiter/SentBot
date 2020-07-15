package sentbot.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;
import sentbot.model.Source.Sector;

public class CommentDao extends SourceDao {
	protected ConnectionManager connectionManager;
	
	//Single pattern instantiation is limited to a single object.
	private static CommentDao instance = null;
	protected CommentDao() {
		connectionManager = new ConnectionManager();
	}
	public static CommentDao getInstance() {
		if(instance == null) {
			instance = new CommentDao();
		}
		return instance;
	}
	
	
	//Create Comment
			public Comment create(Comment comment) throws SQLException {
				//Insert into the superclass table (Source) first
				create(new Source(comment.getSourceKey(), comment.getSentiment(), 
						comment.getSector()));
				//Then, we can insert the rest of the Comment
				String insertComment = "INSERT INTO Comment(CommentText, Ticker, SourceKey) VALUES(?,?,?);";
				Connection connection = null;
				PreparedStatement insertStmt = null;
				try {
					connection = connectionManager.getConnection();
					insertStmt = connection.prepareStatement(insertComment);
					insertStmt.setString(1,comment.getCommentText());
					insertStmt.setString(2,  comment.getTicker());
					insertStmt.setInt(3, comment.getSourceKey());
					insertStmt.executeUpdate();
					return comment;
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
			
	//Read Comment by Source Key
			public Comment getCommentBySourceKey(int sourceKey) throws SQLException {
				String selectComment = "SELECT c.SourceKey AS SourceKey, Sentiment, Sector, CommentText, Ticker FROM Comment AS c INNER JOIN Source AS s ON c.SourceKey = s.SourceKey WHERE SourceKey=?;";
				Connection connection = null;
				PreparedStatement selectStmt = null;
				ResultSet results = null;
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(selectComment);
					selectStmt.setInt(1, sourceKey);
					results = selectStmt.executeQuery();
					if(results.next()) {
						int sKey = results.getInt("SourceKey");
						String sent = results.getString("Sentiment");
						Sector sector = Sector.valueOf(results.getString("Sector"));
						String cText = results.getString("CommentText");
						String tick = results.getString("Ticker");
						Comment comment = new Comment(sKey, sent, sector, cText, tick);
						return comment;
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
			
			
			//Read Comments by Sentiment
			public List<Comment> getCommentsBySentiment(String sentiment) throws SQLException {
				List<Comment> comment = new ArrayList<Comment>();
				String selectCommentBySentiment = 
					"SELECT c.SourceKey AS SourceKey, Sentiment, Sector, CommentText, Ticker "
					+ "FROM Comment AS c INNER JOIN Source AS s ON c.SourceKey = s.SourceKey "
					+ "WHERE Sentiment=?;";
				Connection connection = null;
				PreparedStatement selectStmt = null;
				ResultSet results = null;
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(selectCommentBySentiment);
					selectStmt.setString(1, sentiment);
					results = selectStmt.executeQuery();
					while(results.next()) {
						int sKey = results.getInt("SourceKey");
						String sent = results.getString("Sentiment");
						Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
						String text = results.getString("CommentText");
						String tick = results.getString("Ticker");
						Comment addComment = new Comment(sKey, sent, sec, text, tick);
						comment.add(addComment);
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
				return comment;
			}
					
	//Read Comments by Ticker & Sentiment
			public List<Comment> getCommentsByTickerSentiment(String ticker, String sentiment) throws SQLException {
				List<Comment> comment = new ArrayList<Comment>();
				String selectCommentByTickerSentiment = 
					"SELECT c.SourceKey AS SourceKey, Sentiment, Sector, CommentText, Ticker "
					+ "FROM Comment AS c INNER JOIN Source AS s ON c.SourceKey = s.SourceKey "
					+ "WHERE Ticker=? AND Sentiment=?;";
				Connection connection = null;
				PreparedStatement selectStmt = null;
				ResultSet results = null;
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(selectCommentByTickerSentiment);
					selectStmt.setString(1, ticker);
					selectStmt.setString(2, sentiment);
					results = selectStmt.executeQuery();
					while(results.next()) {
						int sKey = results.getInt("SourceKey");
						String sent = results.getString("Sentiment");
						Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
						String text = results.getString("CommentText");
						String tick = results.getString("Ticker");
						Comment addComment = new Comment(sKey, sent, sec, text, tick);
						comment.add(addComment);
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
				return comment;
			}
		
	//Read Comments by Sector & Sentiment
			public List<Comment> getCommentsBySectorSentiment(Sector sector, String sentiment) throws SQLException {
				List<Comment> comment = new ArrayList<Comment>();
				String selectCommentBySectorSentiment = 
					"SELECT c.SourceKey AS SourceKey, Sentiment, Sector, CommentText, Ticker "
					+ "FROM Comment AS c INNER JOIN Source AS s ON c.SourceKey = s.SourceKey "
					+ "WHERE Sector=? AND Sentiment=?;";
				Connection connection = null;
				PreparedStatement selectStmt = null;
				ResultSet results = null;
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(selectCommentBySectorSentiment);
					selectStmt.setString(1, (sector.toString()));
					selectStmt.setString(2, sentiment);
					results = selectStmt.executeQuery();
					while(results.next()) {
						int sKey = results.getInt("SourceKey");
						String sent = results.getString("Sentiment");
						Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
						String text = results.getString("CommentText");
						String ticker = results.getString("Ticker");
						Comment addComment = new Comment(sKey, sent, sec, text, ticker);
						comment.add(addComment);
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
				return comment;
			}

			public Comment updateCommentText(Comment comment, String newComment) throws SQLException {
				String updateNewSentiment = "UPDATE Comment SET CommentText=? WHERE SourceKey=?;";
				Connection connection = null;
				PreparedStatement updateStmt = null;
				try {
					connection = connectionManager.getConnection();
					updateStmt = connection.prepareStatement(updateNewSentiment);
					updateStmt.setString(1, newComment);
					updateStmt.setInt(2, comment.getSourceKey());
					updateStmt.executeUpdate();
					comment.setCommentText(newComment);
					return comment;
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
			
			// update sentiment
			public Comment updateSentiment(Comment comment, String newSentiment) throws SQLException {
				Source s = new Source(comment.getSourceKey(), comment.getSentiment(), comment.getSector());
				super.updateSentiment(s, newSentiment);
				comment.setSentiment(newSentiment);
				return comment;
			}
			
	//Delete Comment		
			public Comment delete(Comment Comment) throws SQLException {
				String deleteComment = "DELETE FROM Comment WHERE SourceKey=?;";
				Connection connection = null;
				PreparedStatement deleteStmt = null;
				try {
					connection = connectionManager.getConnection();
					deleteStmt = connection.prepareStatement(deleteComment);
					deleteStmt.setInt(1, Comment.getSourceKey());
					int affectedRows = deleteStmt.executeUpdate();
					if (affectedRows == 0) {
						throw new SQLException("No records available to delete for SourceKey=" + Comment.getSourceKey());
					}
					//Now we also delete the matching record from superclass Source
					//Due to the FK constraint we can simply call super.delete() without needing
					//to delete first from Comment.
					super.delete(Comment);
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
