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

@WebServlet("/stockcreate")
public class StockCreate extends HttpServlet {
	
	protected StockDao stockDao;
	protected ImpactedInvestmentDao impactedInvestmentDao;
	
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
        //Just render the JSP.   
        req.getRequestDispatcher("/StockCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate SourceKey.
        String skey = req.getParameter("SourceKey");
        
        if (skey == null || skey.trim().isEmpty()) {
            messages.put("success", "Invalid Source key");
        } else {
        	// Create the Stock.
        	String companyName = req.getParameter("CompanyName");
        	String ticker = req.getParameter("Ticker");
        	int sourceKey = Integer.parseInt(req.getParameter("SourceKey"));
        	ImpactedInvestment.Sector sector = ImpactedInvestment.Sector.valueOf(
					req.getParameter("Sector"));
        	
	        try {
	        	
	        	Stock stock = new Stock(ticker, sector,companyName, sourceKey);
	        	stock = stockDao.create(stock);
	        	messages.put("success", "Successfully created " + stock.getTicker());
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/StockCreate.jsp").forward(req, resp);
    }
}
