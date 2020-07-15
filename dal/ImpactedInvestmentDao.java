package sentbot.dal;

import java.sql.Connection;
import sentbot.model.ImpactedInvestment.Sector;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sentbot.model.*;


public class ImpactedInvestmentDao {
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ImpactedInvestmentDao instance = null;
	protected ImpactedInvestmentDao() {
		connectionManager = new ConnectionManager();
	}
	public static ImpactedInvestmentDao getInstance() {
		if(instance == null) {
			instance = new ImpactedInvestmentDao();
		}
		return instance;
	}

	/**
	 * Create ImpactedInvestment instance.
	 * This runs a INSERT statement.
	 */
	public ImpactedInvestment create(ImpactedInvestment impactedInvestment) throws SQLException {
		String insertImpactedInvestment = "INSERT INTO ImpactedInvestment(Ticker,Sector,SourceKey) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertImpactedInvestment,
					Statement.RETURN_GENERATED_KEYS);
			
	
			insertStmt.setString(1, impactedInvestment.getTicker());
			insertStmt.setString(2, impactedInvestment.getSector().name());
			insertStmt.setInt(3, impactedInvestment.getSourceKey());
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int investmentKey = -1;
			if(resultKey.next()) {
				investmentKey = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			impactedInvestment.setInvestmentKey(investmentKey);
			return impactedInvestment;
	
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
				if(resultKey != null) {
					resultKey.close();
				}
			}
		}
	}

	/**
	 * Update the Sector of the ImpactedInvestment instance.
	 * This runs a UPDATE statement.
	 */
	public ImpactedInvestment updateSector(ImpactedInvestment impactedInvestment, Sector newSector) throws SQLException {
		String updateImpactedInvestment = "UPDATE ImpactedInvestment SET Sector=? WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateImpactedInvestment);
			updateStmt.setString(1, newSector.name());
			updateStmt.setInt(2, impactedInvestment.getInvestmentKey());
			updateStmt.executeUpdate();
			
			// Update the impactedInvestment param before returning to the caller.
			impactedInvestment.setSector(newSector);
			return impactedInvestment;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}

	/**
	 * Delete the ImpactedInvestment instance.
	 * This runs a DELETE statement.
	 */
	public ImpactedInvestment delete(ImpactedInvestment impactedInvestment) throws SQLException {
		String deleteImpactedInvestment = "DELETE FROM ImpactedInvestment WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteImpactedInvestment);
			deleteStmt.setInt(1, impactedInvestment.getInvestmentKey());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the ImpactedInvestment instance.
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
	
	
	public ImpactedInvestment delete(int investmentKey) throws SQLException {
		String deleteImpactedInvestment = "DELETE FROM ImpactedInvestment WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteImpactedInvestment);
			deleteStmt.setInt(1, investmentKey);
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the ImpactedInvestment instance.
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

	

	/**
	 * Read the ImpactedInvestment by Sector
	 */
	public List<ImpactedInvestment> getImpactedInvestmentFromSector(Sector sector) throws SQLException {
		List<ImpactedInvestment> impactedInvestment = new ArrayList<ImpactedInvestment>();
		String selectImpactedInvestment =
			"SELECT InvestmentKey,Ticker,Sector,SourceKey FROM ImpactedInvestment WHERE Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectImpactedInvestment);
			selectStmt.setString(1, (sector.toString()));
			results = selectStmt.executeQuery();
			while(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String ticker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				int sourceKey = results.getInt("SourceKey");
				ImpactedInvestment impactedInvestmentresult = new ImpactedInvestment(investmentKey,ticker, resultSector, sourceKey);
				impactedInvestment.add(impactedInvestmentresult);
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
		return impactedInvestment;
	}
}
