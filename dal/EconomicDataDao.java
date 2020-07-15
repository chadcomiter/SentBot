package sentbot.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;
import sentbot.model.Source.Sector;

public class EconomicDataDao extends SourceDao {
	protected ConnectionManager connectionManager;
	
	//Single pattern instantiation is limited to a single object.
	private static EconomicDataDao instance = null;
	protected EconomicDataDao() {
		connectionManager = new ConnectionManager();
	}
	public static EconomicDataDao getInstance() {
		if(instance == null) {
			instance = new EconomicDataDao();
		}
		return instance;
	}
	
	//Create EconomicData
		public EconomicData create(EconomicData economicData) throws SQLException {
			//Insert into the superclass table (Source) first
			create(new Source(economicData.getSourceKey(), economicData.getSentiment(), 
					economicData.getSector()));
			//Then, we can insert the rest of the EconomicData
			String insertEconomicData = "INSERT INTO EconomicData(Description,SourceKey) VALUES(?,?);";
			Connection connection = null;
			PreparedStatement insertStmt = null;
			try {
				connection = connectionManager.getConnection();
				insertStmt = connection.prepareStatement(insertEconomicData);
				insertStmt.setString(1,economicData.getDescription());
				insertStmt.setInt(2, economicData.getSourceKey());
				insertStmt.executeUpdate();
				return economicData;
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
	
	//Update EconomicData Description
		public EconomicData updateDescription(EconomicData economicData, String newDescription) throws SQLException {
			String updateNewDescription = "UPDATE EconomicData SET Description=? WHERE SourceKey=?;";
			Connection connection = null;
			PreparedStatement updateStmt = null;
			try {
				connection = connectionManager.getConnection();
				updateStmt = connection.prepareStatement(updateNewDescription);
				updateStmt.setString(1, newDescription);
				updateStmt.setInt(2, economicData.getSourceKey());
				updateStmt.executeLargeUpdate();
				economicData.setDescription(newDescription);
				return economicData;
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
		
		//Read EconomicData by Sector and Sentiment
		public List<EconomicData> getEconomicDatasBySectorSentiment(Sector sector, String sentiment) throws SQLException {
			List<EconomicData> cEvent = new ArrayList<EconomicData>();
			String selectEconomicDataBySectorSentiment = 
					"SELECT e.SourceKey AS SourceKey, Sentiment, Sector, Description "
							+ "FROM EconomicData AS e INNER JOIN Source AS s ON e.SourceKey = s.SourceKey "
				+ "WHERE Sector=? AND Sentiment=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectEconomicDataBySectorSentiment);
				selectStmt.setString(1, (sector.toString()));
				selectStmt.setString(2, sentiment);
				results = selectStmt.executeQuery();
				while(results.next()) {
					int sKey = results.getInt("SourceKey");
					String sent = results.getString("Sentiment");
					Source.Sector sec = Source.Sector.valueOf(results.getString("Sector"));
					String desc = results.getString("Description");
					EconomicData addEconomicData = new EconomicData(sKey, sent, sec, desc);
					cEvent.add(addEconomicData);
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
		
		
		//Delete EconomicData	
		public EconomicData delete(EconomicData economicData) throws SQLException {
			String deleteNewsHeadline = "DELETE FROM EconomicData WHERE SourceKey=?;";
			Connection connection = null;
			PreparedStatement deleteStmt = null;
			try {
				connection = connectionManager.getConnection();
				deleteStmt = connection.prepareStatement(deleteNewsHeadline);
				deleteStmt.setInt(1, economicData.getSourceKey());
				int affectedRows = deleteStmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("No records available to delete for SourceKey=" + economicData.getSourceKey());
				}
				//Now we also delete the matching record from superclass Source
				//Due to the FK constraint we can simply call super.delete() without needing
				//to delete first from EconomicData.
				super.delete(economicData);
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