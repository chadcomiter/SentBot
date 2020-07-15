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



public class CommodityDao extends ImpactedInvestmentDao {
	
	private static CommodityDao instance = null;
	protected CommodityDao() {
		super();
	}
	public static CommodityDao getInstance() {
		if(instance == null) {
			instance = new CommodityDao();
		}
		return instance;
	}
	/**
	 * Create Commodity instance.
	 * This runs a create statement.
	 */
	public Commodity create(Commodity commodity) throws SQLException {
		// Insert into the superclass table first.
		ImpactedInvestment root = create(new ImpactedInvestment(commodity.getInvestmentKey(),commodity.getTicker(),commodity.getSector(),commodity.getSourceKey()));

		String insertCommodity = "INSERT INTO Commodity(FuturesName, InvestmentKey) VALUES(?, ?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCommodity);
			insertStmt.setString(1, commodity.getFuturesName());
			insertStmt.setInt(2, root.getInvestmentKey());
			insertStmt.executeUpdate();
			commodity.setInvestmentKey(root.getInvestmentKey());
			return commodity;
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
	 * Update the Sector of the Commodity instance.
	 * This runs a UPDATE statement.
	 */
	public Commodity updateSector(Commodity commodity, Sector newSector) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateSector(commodity, newSector);
		return commodity;
	}
	/**
	 * Update the FuturesName of the Commodity instance.
	 * This runs a UPDATE statement.
	 */
	public Commodity updateContent(Commodity commodity, String newFuturesName) throws SQLException {
		String updatecommodity = "UPDATE Commodity SET FuturesName=? WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatecommodity);
			updateStmt.setString(1, newFuturesName);
			updateStmt.setInt(2, commodity.getInvestmentKey());
			updateStmt.executeUpdate();

			// Update the commodity param before returning to the caller.
			commodity.setFuturesName(newFuturesName);
			
			return commodity;
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
	 * Delete the Commodity instance.
	 * This runs a DELETE statement.
	 */
	public Commodity delete(Commodity commodity) throws SQLException {
		String deleteCommodity = "DELETE FROM Commodity WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCommodity);
			deleteStmt.setInt(1, commodity.getInvestmentKey());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Commodity first.
			super.delete(commodity);

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
	 * Read the Commodity instance by Ticker .
	 */
	public Commodity getCommodityFromTickerSector(String ticker, Sector sector)
			throws SQLException {
		String selectCommodity =
			"SELECT Commodity.InvestmentKey AS InvestmentKey, FuturesName, Ticker, Sector, SourceKey " +
			"FROM Commodity INNER JOIN ImpactedInvestment " +
			"  ON Commodity.InvestmentKey = ImpactedInvestment.InvestmentKey " +
			"WHERE ImpactedInvestment.Ticker=? AND ImpactedInvestment.Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCommodity);
			selectStmt.setString(1, ticker);
			selectStmt.setString(2, (sector.toString()));
			results = selectStmt.executeQuery();
			if(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String resultTicker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				String futuresName = results.getString("FuturesName");
				int sourceKey = results.getInt("SourceKey");
				Commodity resultcommodity = new Commodity(investmentKey, resultTicker, resultSector,futuresName, sourceKey);
				return resultcommodity;
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
 * Read the Commodity instance by Sector.
 */
public List<Commodity> getCommodityFromSector(Sector sector)
		throws SQLException {
	List<Commodity> commodity = new ArrayList<Commodity>();
	String selectCommodity =
		"SELECT Commodity.InvestmentKey AS InvestmentKey, FuturesName, Ticker, Sector, SourceKey " +
		"FROM Commodity INNER JOIN ImpactedInvestment " +
		"  ON Commodity.InvestmentKey = ImpactedInvestment.InvestmentKey " +
		"WHERE ImpactedInvestment.Sector=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectCommodity);
		selectStmt.setString(1, (sector.toString()));
		results = selectStmt.executeQuery();
		while(results.next()) {
			int investmentKey = results.getInt("InvestmentKey");
			String resultTicker = results.getString("Ticker");
			ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
					results.getString("Sector"));
			String futuresName = results.getString("FuturesName");
			int sourceKey = results.getInt("SourceKey");
			Commodity resultcommodity = new Commodity(investmentKey, resultTicker, resultSector,futuresName, sourceKey);
			commodity.add(resultcommodity);
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
	return commodity;
}
}
