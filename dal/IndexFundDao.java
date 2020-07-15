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



public class IndexFundDao extends ImpactedInvestmentDao {
	
	private static IndexFundDao instance = null;
	protected IndexFundDao() {
		super();
	}
	public static IndexFundDao getInstance() {
		if(instance == null) {
			instance = new IndexFundDao();
		}
		return instance;
	}
	/**
	 * Create IndexFund instance.
	 * This runs a create statement.
	 */
	public IndexFund create(IndexFund indexFund) throws SQLException {
		// Insert into the superclass table first.
		ImpactedInvestment root = create(new ImpactedInvestment(indexFund.getInvestmentKey(),indexFund.getTicker(),indexFund.getSector(),indexFund.getSourceKey()));

		String insertIndexFund = "INSERT INTO IndexFund(IndexFundName, InvestmentKey) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertIndexFund);
			insertStmt.setString(1, indexFund.getIndexFundName());
			insertStmt.setInt(2, root.getInvestmentKey());
			insertStmt.executeUpdate();
			indexFund.setInvestmentKey(root.getInvestmentKey());
			return indexFund;
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
	 * Update the Sector of the IndexFund instance.
	 * This runs a UPDATE statement.
	 */
	public IndexFund updateSector(IndexFund indexFund, Sector newSector) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateSector(indexFund, newSector);
		return indexFund;
	}
	/**
	 * Update the IndexFundName of the IndexFund instance.
	 * This runs a UPDATE statement.
	 */
	public IndexFund updateContent(IndexFund indexFund, String newIndexFundName) throws SQLException {
		String updateindexFund = "UPDATE IndexFund SET IndexFundName=? WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateindexFund);
			updateStmt.setString(1, newIndexFundName);
			updateStmt.setInt(2, indexFund.getInvestmentKey());
			updateStmt.executeUpdate();

			// Update the indexFund param before returning to the caller.
			indexFund.setIndexFundName(newIndexFundName);
			return indexFund;
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
	 * Delete the IndexFund instance.
	 * This runs a DELETE statement.
	 */
	public IndexFund delete(IndexFund indexFund) throws SQLException {
		String deleteIndexFund = "DELETE FROM IndexFund WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteIndexFund);
			deleteStmt.setInt(1, indexFund.getInvestmentKey());
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from IndexFund first.
			super.delete(indexFund);

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
	
	
	public IndexFund delete(int investmentKey) throws SQLException {
		String deleteIndexFund = "DELETE FROM IndexFund WHERE InvestmentKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteIndexFund);
			deleteStmt.setInt(1, investmentKey);
			deleteStmt.executeUpdate();

			// Then also delete from the superclass.
			// Note: due to the fk constraint (ON DELETE CASCADE), we could simply call
			// super.delete() without even needing to delete from IndexFund first.
			super.delete(investmentKey);

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
	 * Read the IndexFund instance by Ticker.
	 */
	public IndexFund getIndexFundFromTickerSector(String ticker, Sector sector)
			throws SQLException {
		String selectIndexFund =
			"SELECT IndexFund.InvestmentKey AS InvestmentKey, IndexFundName, Ticker, Sector, SourceKey " +
			"FROM IndexFund INNER JOIN ImpactedInvestment " +
			"  ON IndexFund.InvestmentKey = ImpactedInvestment.InvestmentKey " +
			"WHERE ImpactedInvestment.Ticker=? AND  ImpactedInvestment.Sector=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectIndexFund);
			selectStmt.setString(1, ticker);
			selectStmt.setString(2, (sector.toString()));
			results = selectStmt.executeQuery();
			if(results.next()) {
				int investmentKey = results.getInt("InvestmentKey");
				String resultTicker = results.getString("Ticker");
				ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
						results.getString("Sector"));
				String indexFundName = results.getString("IndexFundName");
				int sourceKey = results.getInt("SourceKey");
				IndexFund resultindexFund = new IndexFund(investmentKey, resultTicker, resultSector,indexFundName, sourceKey);
				return resultindexFund;
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
 * Read the IndexFund instance by Sector.
 */
public List<IndexFund> getIndexFundFromSector(Sector sector)
		throws SQLException {
	List<IndexFund> indexFund = new ArrayList<IndexFund>();
	String selectIndexFund =
		"SELECT IndexFund.InvestmentKey AS InvestmentKey, IndexFundName, Ticker, Sector, SourceKey " +
		"FROM IndexFund INNER JOIN ImpactedInvestment " +
		"  ON IndexFund.InvestmentKey = ImpactedInvestment.InvestmentKey " +
		"WHERE ImpactedInvestment.Sector=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectIndexFund);
		selectStmt.setString(1, (sector.toString()));
		results = selectStmt.executeQuery();
		while(results.next()) {
			int investmentKey = results.getInt("InvestmentKey");
			String resultTicker = results.getString("Ticker");
			ImpactedInvestment.Sector resultSector = ImpactedInvestment.Sector.valueOf(
					results.getString("Sector"));
			String indexFundName = results.getString("IndexFundName");
			int sourceKey = results.getInt("SourceKey");
			IndexFund resultindexFund = new IndexFund(investmentKey, resultTicker, resultSector,indexFundName, sourceKey);
			indexFund.add(resultindexFund);
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
	return indexFund;
}
}
