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



public class StockDao extends ImpactedInvestmentDao {
	
	private static StockDao instance = null;
	protected StockDao() {
		super();
	}
	public static StockDao getInstance() {
		if(instance == null) {
			instance = new StockDao();
		}
		return instance;
	}
	/**
	 * Create Stock instance.
	 * This runs a create statement.
	 */
	public Stock create(Stock stock) throws SQLException {
		// Insert into the superclass table first.
		ImpactedInvestment root = create(new ImpactedInvestment(stock.getInvestmentKey(),stock.getTicker(),stock.getSector(),stock.getSourceKey()));

		String insertStock = "INSERT INTO Stock(CompanyName, InvestmentKey) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertStock);
			insertStmt.setString(1, stock.getCompanyName());
			insertStmt.setInt(2, root.getInvestmentKey());
			insertStmt.executeUpdate();
			stock.setInvestmentKey(root.getInvestmentKey());
			return stock;
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
	 * Update the Sector of the Stock instance.
	 * This runs a UPDATE statement.
	 */
	public Stock updateSector(Stock stock, Sector newSector) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateSector(stock, newSector);
		return stock;
	}
	/**
	 * Update the CompanyName of the Stock instance.
	 * This runs a UPDATE statement.
	 */
	public Stock updateContent(Stock stock, String newCompanyName) throws SQLException {
		String updatestock = "UPDATE Stock SET CompanyName=? WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatestock);
			updateStmt.setString(1, newCompanyName);
			updateStmt.setInt(2, stock.getInvestmentKey());
			updateStmt.executeUpdate();

			// Update the stock param before returning to the caller.
			stock.setCompanyName(newCompanyName);
			return stock;
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
	 * Delete the Stock instance.
	 * This runs a DELETE statement.
	 */
	public Stock delete(Stock stock) throws SQLException {
		String deleteStock = "DELETE FROM Stock WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteStock);
			deleteStmt.setInt(1, stock.getInvestmentKey());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from Stock first.
			super.delete(stock);

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
	 * Read the Stock instance by Ticker .
	 */
	public Stock getStockFromTicker(String ticker)
			throws SQLException {
		String selectStock =
			"SELECT Stock.InvestmentKey AS InvestmentKey, CompanyName, Ticker, Sector, SourceKey " +
			"FROM Stock INNER JOIN ImpactedInvestment " +
			"  ON Stock.InvestmentKey = ImpactedInvestment.InvestmentKey " +
			"WHERE ImpactedInvestment.Ticker=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStock);
			selectStmt.setString(1, ticker);
			results = selectStmt.executeQuery();
			if(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String resultTicker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				String companyName = results.getString("CompanyName");
				int sourceKey = results.getInt("SourceKey");
				Stock resultstock = new Stock(investmentKey, resultTicker, resultSector,companyName, sourceKey);
				return resultstock;
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
	 * Read the Stock instance by Ticker Sector.
	 */
	
	public Stock getStockFromTickerSector(String ticker, Sector sector)
			throws SQLException {
		String selectStock =
			"SELECT Stock.InvestmentKey AS InvestmentKey, CompanyName, Ticker, Sector, SourceKey " +
			"FROM Stock INNER JOIN ImpactedInvestment " +
			"  ON Stock.InvestmentKey = ImpactedInvestment.InvestmentKey " +
			"WHERE ImpactedInvestment.Ticker=? AND  ImpactedInvestment.Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectStock);
			selectStmt.setString(1, ticker);
			selectStmt.setString(2, (sector.toString()));
			results = selectStmt.executeQuery();
			if(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String resultTicker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				String companyName = results.getString("CompanyName");
				int sourceKey = results.getInt("SourceKey");
				Stock resultstock = new Stock(investmentKey, resultTicker, resultSector,companyName, sourceKey);
				return resultstock;
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
 * Read the Stock instance by Sector.
 */
public List<Stock> getStockFromSector(Sector sector)
		throws SQLException {
	List<Stock> stock = new ArrayList<Stock>();
	String selectStock =
		"SELECT Stock.InvestmentKey AS InvestmentKey, CompanyName, Ticker, Sector, SourceKey " +
		"FROM Stock INNER JOIN ImpactedInvestment " +
		"  ON Stock.InvestmentKey = ImpactedInvestment.InvestmentKey " +
		"WHERE ImpactedInvestment.Sector=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectStock);
		selectStmt.setString(1, (sector.toString()));
		results = selectStmt.executeQuery();
		while(results.next()) {
			int investmentKey = results.getInt("InvestmentKey");
			String resultTicker = results.getString("Ticker");
			ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
					results.getString("Sector"));
			String companyName = results.getString("CompanyName");
			int sourceKey = results.getInt("SourceKey");
			Stock resultstock = new Stock(investmentKey, resultTicker, resultSector,companyName, sourceKey);
			stock.add(resultstock);
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
	return stock;
}
}
