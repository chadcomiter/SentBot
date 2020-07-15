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


@WebServlet("/commentdelete")
public class CommentDelete extends HttpServlet {
	
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Comment");        
        req.getRequestDispatcher("/DeleteComment.jsp").forward(req, resp);
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
        	// Delete the Comment.
	        Comment comment = new Comment(Integer.parseInt(sourceKey),null,null,null,null);
	        try {
	        	comment = commentDao.delete(comment);
	        	// Update the message.
		        if (comment == null) {
		            messages.put("title", "Successfully deleted Comment " + sourceKey);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete Comment " + sourceKey);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/DeleteComment.jsp").forward(req, resp);
    }
}
