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


@WebServlet("/commentcreate")
public class CommentCreate extends HttpServlet {
	
	protected CommentDao commentDao;
	protected SourceDao sourceDao;
	
	@Override
	public void init() throws ServletException {
		commentDao = CommentDao.getInstance();
		sourceDao = SourceDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/CreateComment.jsp").forward(req, resp);
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
        	// Create the Comment.
        	int sKey = Integer.parseInt(req.getParameter("sourcekey"));
        	String sentiment = req.getParameter("sentiment");
        	Sector sector = Sector.valueOf(req.getParameter("sector"));
        	String cText = req.getParameter("commenttext");
        	String tick = req.getParameter("ticker");

	        try {
	        	Comment comment = new Comment(sKey, sentiment, sector, cText, tick);
	        	comment = commentDao.create(comment);
	        	messages.put("success", "Successfully created " + sKey);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/CreateComment.jsp").forward(req, resp);
    }
}
