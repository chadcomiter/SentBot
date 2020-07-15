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

@WebServlet("/findstock")
public class FindStock extends HttpServlet {
	
	protected StockDao stockDao;
	
	@Override
	public void init() throws ServletException {
		stockDao = StockDao.getInstance();
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Stock> stock = new ArrayList<Stock>();
        
        // Retrieve and validate name.
        // Sector is retrieved from the URL query string.
        String sector = req.getParameter("Sector");
        if (sector == null || sector.trim().isEmpty()) {
            messages.put("success", "Please enter a valid sector.");
        } else {
        	try {
            	stock = stockDao.getStockFromSector(ImpactedInvestment.Sector.valueOf(sector.toString()));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + sector);
        	
        	messages.put("previousSector", sector);
        }
        req.setAttribute("Stocks", stock);
        
        req.getRequestDispatcher("/FindStock.jsp").forward(req, resp);
	}
}	
	
