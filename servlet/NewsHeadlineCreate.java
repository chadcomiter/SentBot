package sentbot.servlet;

import sentbot.dal.*;
import sentbot.model.*;
import sentbot.model.Source.Sector;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/newsheadlinecreate")
public class NewsHeadlineCreate extends HttpServlet {
	
	protected NewsHeadlineDao newsheadlineDao;
	protected SourceDao sourceDao;
	
	@Override
	public void init() throws ServletException {
		newsheadlineDao = NewsHeadlineDao.getInstance();
		sourceDao = SourceDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CreateNewsHeadline.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate SourceKey.
        String sourceKey = req.getParameter("sourcekey");
        if (sourceKey == null || sourceKey.trim().isEmpty()) {
            messages.put("success", "Invalid Source Key");
        } else {
        	// Create the NewsHeadline.
        	int sKey = Integer.parseInt(req.getParameter("sourcekey"));
        	String source = req.getParameter("source");
        	String newsheadlines = req.getParameter("headline");
        	String sector = req.getParameter("sector");
        	String sentiment = req.getParameter("sentiment");

	        try {
	        	NewsHeadline newsheadline = new NewsHeadline(sKey, null, null, source, newsheadlines);
	        	newsheadline = newsheadlineDao.create(newsheadline);
	        	messages.put("success", "Successfully created " + sKey);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CreateNewsHeadline.jsp").forward(req, resp);
    }
}
