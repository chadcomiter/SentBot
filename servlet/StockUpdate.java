package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/stockupdate")
public class StockUpdate extends HttpServlet {
	
	protected StockDao stockDao;
	
	@Override
	public void init() throws ServletException {
		stockDao = StockDao.getInstance();
	}
	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String ticker = req.getParameter("Ticker");
    	if(ticker == null || ticker.trim().isBlank()) {
    		messages.put("success", "No ticker");
    	}
    	else {
    		try {
        		Stock stock = stockDao.getStockFromTicker(ticker);
        		if(stock == null) {
        			messages.put("success", "Stock '" + ticker + "' does not exist.");
        		} else {
        			req.setAttribute("Stock", stock);
        		}
    		}
        	 catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
    	}
    	req.getRequestDispatcher("/StockUpdate.jsp").forward(req, resp);
	}
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        boolean goodSector = false;
        ImpactedInvestment.Sector sector = null;
        try {
        	sector = ImpactedInvestment.Sector.valueOf(
    				req.getParameter("Sector"));
        	goodSector = true;
        }
        catch(Exception ex) {
        	messages.put("success", "Invalid sector");
        }
        if (goodSector) {
        	String ticker = req.getParameter("Ticker");
        	if(ticker == null || ticker.trim().isBlank()) {
        		messages.put("success", "No ticker");
        	}
        	else {
        		try {
            		Stock stock = stockDao.getStockFromTicker(ticker);
            		if(stock == null)
            			messages.put("success", "Stock '" + ticker + "' does not exist.");
            		else {
            			stock = stockDao.updateSector(stock, sector);
            			req.setAttribute("Stock", stock);
            			messages.put("success", "Stock '" + stock.getTicker() + "' updated!");
            		}
            		
            	} catch (SQLException e) {
    				e.printStackTrace();
    				throw new IOException(e);
    	        }
        	}
        }
        
        req.getRequestDispatcher("/StockUpdate.jsp").forward(req, resp);
    }
}
