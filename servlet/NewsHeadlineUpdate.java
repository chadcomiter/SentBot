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


@WebServlet("/newsheadlineupdate")
public class NewsHeadlineUpdate extends HttpServlet {
	
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

        // Retrieve comment and validate.
        String sourceKey1 = req.getParameter("sourcekey");
        if (sourceKey1 == null || sourceKey1.trim().isEmpty()){
            messages.put("success", "Please enter a valid SourceKey.");
        } else {
        	try {
        		NewsHeadline nh = newsheadlineDao.getNewsHeadlineBySourceKey(Integer.parseInt(sourceKey1));
        		if(nh == null) {
        			messages.put("success", "Comment does not exist.");
        		}
        		req.setAttribute("newsheadline", nh);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateNewsHeadline.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve comment and validate.
        String sourceKey1 = req.getParameter("sourcekey");
        if (sourceKey1 == null || sourceKey1.trim().isEmpty()){
            messages.put("success", "Please enter a valid SourceKey.");
        } else {
        	try {
        		NewsHeadline nh = newsheadlineDao.getNewsHeadlineBySourceKey(Integer.parseInt(sourceKey1));
        		if(nh == null) {
        			messages.put("success", "Comment does not exist. No update to perform.");
        		} else {
        			String newSentiment = req.getParameter("newsheadline");
        			if (newSentiment == null || newSentiment.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid headline.");
        	        } else {
        	        	nh = newsheadlineDao.updateHeadline(nh, newSentiment);
        	        	messages.put("success", "Successfully updated " + nh);
        	        }
        		}
        		req.setAttribute("newsheadline", nh);
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/UpdateNewsHeadline.jsp").forward(req, resp);
    }
}