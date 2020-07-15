package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sentbot.dal.NewsHeadlineDao;

@WebServlet("/readnewsheadline")
public class ReadNewsHeadline extends HttpServlet {
	
	/**
	 * 
	 */
	protected NewsHeadlineDao newsheadlineDao;
	
	@Override
	public void init() throws ServletException {
		newsheadlineDao = NewsHeadlineDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<NewsHeadline> newsheadline = new ArrayList<NewsHeadline>();
        
        // Retrieve and validate name.
        // sentiment is retrieved from the URL query string.
        String sentiment = req.getParameter("sentiment");
        if (sentiment == null || sentiment.trim().isEmpty()) {
            messages.put("success", "No records found, Please enter a valid sentiment (bullish, bearish, or none).");
        } else {
        	// Retrieve newsheadline, and store as a message.
        	try {
            	newsheadline = newsheadlineDao.getNewsHeadlineBySentiment(sentiment);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + sentiment);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering ReadNewsHeadline.jsp.
        	messages.put("previousSentiment", sentiment);
        }
        req.setAttribute("newsheadline", newsheadline);
        
        req.getRequestDispatcher("/ReadNewsHeadline.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<NewsHeadline> newsheadline = new ArrayList<NewsHeadline>();
        
        // Retrieve and validate sentiment.
        // sentiment is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String sentiment = req.getParameter("sentiment");
        if (sentiment == null || sentiment.trim().isEmpty()) {
            messages.put("success", "Please enter a sentiment (bearish, bullish, or none).");
        } else {
        	// Retrieve Comment, and store as a message.
        	try {
            	newsheadline = newsheadlineDao.getNewsHeadlineBySentiment(sentiment);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + sentiment);
        }
        req.setAttribute("newsheadline", newsheadline);
        
        req.getRequestDispatcher("/ReadNewsHeadline.jsp").forward(req, resp);
    }
}
