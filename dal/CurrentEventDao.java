package sentbot.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;
import sentbot.model.Source.Sector;

public class CurrentEventDao extends SourceDao {
	protected ConnectionManager connectionManager;
	
	//Single pattern instantiation is limited to a single object.
	private static CurrentEventDao instance = null;
	protected CurrentEventDao() {
		connectionManager = new ConnectionManager();
	}
	public static CurrentEventDao getInstance() {
		if(instance == null) {
			instance = new CurrentEventDao();
		}
		return instance;
	}
	
	//Create Current Event
		public CurrentEvent create(CurrentEvent currentEvent) throws SQLException {
			//Insert into the superclass table (Source) first
			create(new Source(currentEvent.getSourceKey(), currentEvent.getSentiment(), 
					currentEvent.getSector()));
			//Then, we can insert the rest of the CurrentEvent
			String insertCurrentEvent = "INSERT INTO CurrentEvent(Description,SourceKey) VALUES(?,?);";
			Connection connection = null;
			PreparedStatement insertStmt = null;
			try {
				connection = connectionManager.getConnection();
				insertStmt = connection.prepareStatement(insertCurrentEvent);
				insertStmt.setString(1,currentEvent.getDescription());
				insertStmt.setInt(2, currentEvent.getSourceKey());
				insertStmt.executeUpdate();
				return currentEvent;
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
	
	//Update Current Event Description
		public CurrentEvent updateDescription(CurrentEvent currentEvent, String newDescription) throws SQLException {
			String updateNewDescription = "UPDATE CurrentEvent SET Description=? WHERE SourceKey=?;";
			Connection connection = null;
			PreparedStatement updateStmt = null;
			try {
				connection = connectionManager.getConnection();
				updateStmt = connection.prepareStatement(updateNewDescription);
				updateStmt.setString(1, newDescription);
				updateStmt.setInt(2, currentEvent.getSourceKey());
				updateStmt.executeLargeUpdate();
				currentEvent.setDescription(newDescription);
				return currentEvent;
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
		
		//Read Current Events by Sector and Sentiment
		public List<CurrentEvent> getCurrentEventsBySectorSentiment(Sector sector, String sentiment) throws SQLException {
			List<CurrentEvent> cEvent = new ArrayList<CurrentEvent>();
			String selectCurrentEventBySectorSentiment = 
					"SELECT c.SourceKey AS SourceKey, Sentiment, Sector, Description "
							+ "FROM CurrentEvent AS c INNER JOIN Source AS s ON c.SourceKey = s.SourceKey "
				+ "WHERE Sector=? AND  Sentiment=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectCurrentEventBySectorSentiment);
				selectStmt.setString(1, (sector.toString()));
				selectStmt.setString(2, sentiment);
				results = selectStmt.executeQuery();
				while(results.next()) {
					int sKey = results.getInt("SourceKey");
					String sent = results.getString("Sentiment");
					Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
					String desc = results.getString("Description");
					CurrentEvent addCurrentEvent = new CurrentEvent(sKey, sent, sec, desc);
					cEvent.add(addCurrentEvent);
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
			return cEvent;
		}
		
		
		//Delete CurrentEvent	
		public CurrentEvent delete(CurrentEvent currentEvent) throws SQLException {
			String deleteNewsHeadline = "DELETE FROM CurrentEvent WHERE SourceKey=?;";
			Connection connection = null;
			PreparedStatement deleteStmt = null;
			try {
				connection = connectionManager.getConnection();
				deleteStmt = connection.prepareStatement(deleteNewsHeadline);
				deleteStmt.setInt(1, currentEvent.getSourceKey());
				int affectedRows = deleteStmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("No records available to delete for SourceKey=" + currentEvent.getSourceKey());
				}
				//Now we also delete the matching record from superclass Source
				//Due to the FK constraint we can simply call super.delete() without needing
				//to delete first from CurrentEvent.
				super.delete(currentEvent);
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