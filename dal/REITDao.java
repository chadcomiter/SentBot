package sentbot.dal;
import sentbot.model.*;
import sentbot.model.ImpactedInvestment.Sector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sentbot.model.*;



public class REITDao extends ImpactedInvestmentDao {
	
	private static REITDao instance = null;
	protected REITDao() {
		super();
	}
	public static REITDao getInstance() {
		if(instance == null) {
			instance = new REITDao();
		}
		return instance;
	}
	/**
	 * Create REIT instance.
	 * This runs a create statement.
	 */
	public REIT create(REIT rEIT) throws SQLException {
		// Insert into the superclass table first.
		ImpactedInvestment root = create(new ImpactedInvestment(rEIT.getInvestmentKey(),rEIT.getTicker(),rEIT.getSector(),rEIT.getSourceKey()));

		String insertREIT = "INSERT INTO REIT(Name, ReitType,InvestmentKey) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertREIT);
			insertStmt.setString(1, rEIT.getReitName());
			insertStmt.setString(2, rEIT.getreitType());
			insertStmt.setInt(3, root.getInvestmentKey());
			insertStmt.executeUpdate();
			rEIT.setInvestmentKey(root.getInvestmentKey());
			return rEIT;
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

	/**
	 * Update the Sector of the REIT instance.
	 * This runs a UPDATE statement.
	 */
	public REIT updateSector(REIT rEIT, Sector newSector) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateSector(rEIT, newSector);
		return rEIT;
	}
	/**
	 * Update the reitName of the REIT instance.
	 * This runs a UPDATE statement.
	 */
	public REIT updateContent(REIT rEIT, String newreitName) throws SQLException {
		String updaterEIT = "UPDATE REIT SET Name=? WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updaterEIT);
			updateStmt.setString(1, newreitName);
			updateStmt.setInt(2, rEIT.getInvestmentKey());
			updateStmt.executeUpdate();

			// Update the rEIT param before returning to the caller.
			rEIT.setReitName(newreitName);
			return rEIT;
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
	 * Delete the REIT instance.
	 * This runs a DELETE statement.
	 */
	public REIT delete(REIT rEIT) throws SQLException {
		String deleteREIT = "DELETE FROM REIT WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteREIT);
			deleteStmt.setInt(1, rEIT.getInvestmentKey());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from REIT first.
			super.delete(rEIT);

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
	 * Read the REIT instance by Ticker.
	 */
	public REIT getREITFromTickerSector(String ticker, Sector sector)
			throws SQLException {
		
		String selectREIT =
			"SELECT REIT.InvestmentKey AS InvestmentKey, Name, ReitType,Ticker, Sector, SourceKey " +
			"FROM REIT INNER JOIN ImpactedInvestment " +
			"  ON REIT.InvestmentKey = ImpactedInvestment.InvestmentKey " +
			"WHERE ImpactedInvestment.Ticker=? AND  ImpactedInvestment.Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectREIT);
			selectStmt.setString(1, ticker);
			selectStmt.setString(2, (sector.toString()));
			results = selectStmt.executeQuery();
			if(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String resultTicker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				String reitname = results.getString("Name");
				String reittype = results.getString("ReitType");
				int sourceKey = results.getInt("SourceKey");
				REIT resultrEIT = new REIT(investmentKey, resultTicker, resultSector,reitname,reittype, sourceKey);
				return resultrEIT;
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
		return null;
	}

/**
 * Read the REIT instance by Sector.
 */
public List<REIT> getREITFromSector(Sector sector)
		throws SQLException {
	List<REIT> rEIT = new ArrayList<REIT>();
	String selectREIT =
		"SELECT REIT.InvestmentKey AS InvestmentKey, Name,ReitType, Ticker, Sector, SourceKey " +
		"FROM REIT INNER JOIN ImpactedInvestment " +
		"  ON REIT.InvestmentKey = ImpactedInvestment.InvestmentKey " +
		"WHERE ImpactedInvestment.Sector=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectREIT);
		selectStmt.setString(1, (sector.toString()));
		results = selectStmt.executeQuery();
		while(results.next()) {
			int investmentKey = results.getInt("InvestmentKey");
			String resultTicker = results.getString("Ticker");
			ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
					results.getString("Sector"));
			String reitname = results.getString("Name");
			String reittype = results.getString("ReitType");
			int sourceKey = results.getInt("SourceKey");
			REIT resultrEIT = new REIT(investmentKey, resultTicker, resultSector,reitname,reittype, sourceKey);
			rEIT.add(resultrEIT);
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
	return rEIT;
}
}
