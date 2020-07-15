package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;

import java.io.IOException;
import java.sql.SQLException;
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


@WebServlet("/stockdelete")
public class StockDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Stock");
        if(req.getParameter("Ticker") != null) {
        	req.setAttribute("Ticker", req.getParameter("Ticker"));
        }
        
        req.getRequestDispatcher("/StockDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String ticker = req.getParameter("Ticker");
        if (ticker == null || ticker.trim().isEmpty()) {
            messages.put("success", "Invalid Ticker");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the Stock.
	        try {
	        	Stock stock = stockDao.getStockFromTicker(ticker);
	        	stock = stockDao.delete(stock);
	        	// Update the message.
		        if (stock == null) {
		            messages.put("success", "Successfully deleted " + ticker);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("success", "Failed to delete " + ticker);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockDelete.jsp").forward(req, resp);
    }
}
