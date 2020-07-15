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

import sentbot.dal.CommentDao;

@WebServlet("/findcomments")
public class FindComment extends HttpServlet {
	
	/**
	 * 
	 */
	protected CommentDao commentDao;
	
	@Override
	public void init() throws ServletException {
		commentDao = CommentDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Comment> comment = new ArrayList<Comment>();
        
        // Retrieve and validate name.
        // sentiment is retrieved from the URL query string.
        String sentiment = req.getParameter("sentiment");
        if (sentiment == null || sentiment.trim().isEmpty()) {
            messages.put("success", "No records found, Please enter a valid sentiment (bullish, bearish, or none).");
        } else {
        	// Retrieve comment, and store as a message.
        	try {
            	comment = commentDao.getCommentsBySentiment(sentiment);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + sentiment);
        	// Save the previous search term, so it can be used as the default
        	// in the input box when rendering FindComment.jsp.
        	messages.put("previousSentiment", sentiment);
        }
        req.setAttribute("comment", comment);
        
        req.getRequestDispatcher("/FindComment.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Comment> comment = new ArrayList<Comment>();
        
        // Retrieve and validate sentiment.
        // sentiment is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindUsers.jsp).
        String sentiment = req.getParameter("sentiment");
        if (sentiment == null || sentiment.trim().isEmpty()) {
            messages.put("success", "Please enter a sentiment (bearish, bullish, or none).");
        } else {
        	// Retrieve Comment, and store as a message.
        	try {
            	comment = commentDao.getCommentsBySentiment(sentiment);
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
        	messages.put("success", "Displaying results for " + sentiment);
        }
        req.setAttribute("comment", comment);
        
        req.getRequestDispatcher("/FindComment.jsp").forward(req, resp);
    }
}
