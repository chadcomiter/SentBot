package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/commentupdate")
public class CommentUpdate extends HttpServlet {
	
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

        // Retrieve comment and validate.
        String sourceKey = req.getParameter("sourcekey");
        if (sourceKey == null || sourceKey.trim().isEmpty()){
            messages.put("success", "Please enter a valid SourceKey.");
        } else {
        	try {
        		Comment comment = commentDao.getCommentBySourceKey(Integer.parseInt(sourceKey));
        		if(comment == null) {
        			messages.put("success", "Comment does not exist.");
        		}
        		req.setAttribute("comment", comment);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateComment.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve comment and validate.
        String sourceKey = req.getParameter("sourcekey");
        if (sourceKey == null || sourceKey.trim().isEmpty()){
            messages.put("success", "Please enter a valid SourceKey.");
        } else {
        	try {
        		Comment comment = commentDao.getCommentBySourceKey(Integer.parseInt(sourceKey));
        		if(comment == null) {
        			messages.put("success", "Comment does not exist. No update to perform.");
        		} else {
        			String newSentiment = req.getParameter("sentiment");
        			if (newSentiment == null || newSentiment.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Sentiment.");
        	        } else {
        	        	comment = commentDao.updateSentiment(comment, newSentiment);
        	        	messages.put("success", "Successfully updated " + comment);
        	        }
        		}
        		req.setAttribute("comment", comment);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateComment.jsp").forward(req, resp);
    }
}