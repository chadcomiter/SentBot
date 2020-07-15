<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a User</title>
</head>
<body>
	<form action="newsheadlineupdate" method="post">
		<h1>Update News Headline</h1>
			<p>
			<label for="sourcekey">Source Key</label>
			<input id="sourcekey" name="sourcekey">
		</p>
		<p>
			<label for="newsheadline">NewsHeadline</label>
			<input id="newsheadline" name="newsheadline" value="${fn:escapeXml(param.newsheadline)}">
		</p>

		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
		<a href="readnewsheadline">Back to Search</a> 
	</form>
</body>
</html>
