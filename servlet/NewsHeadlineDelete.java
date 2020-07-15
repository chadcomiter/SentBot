package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/newsheadlinedelete")
public class NewsHeadlineDelete extends HttpServlet {
	
	protected NewsHeadlineDao newsHeadlineDao;
	
	@Override
	public void init() throws ServletException {
		newsHeadlineDao = NewsHeadlineDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete NewsHeadline");        
        req.getRequestDispatcher("/DeleteNewsHeadline.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate sourceKey.
        String sourceKey = req.getParameter("sourcekey");
        if (sourceKey == null || sourceKey.trim().isEmpty()){
            messages.put("title", "Invalid SourceKey");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the NewsHeadline.
	        NewsHeadline newsheadline = new NewsHeadline(Integer.parseInt(sourceKey),null,null,null,null);
	        try {
	        	newsheadline = newsHeadlineDao.delete(newsheadline);
	        	// Update the message.
		        if (newsheadline == null) {
		            messages.put("title", "Successfully deleted " + sourceKey);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + sourceKey);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/DeleteNewsHeadline.jsp").forward(req, resp);
    }
}
