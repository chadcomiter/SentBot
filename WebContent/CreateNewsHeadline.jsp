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
	<form action="newsheadlinecreate" method="post">
		<h1>Search for a News Headline by Sector</h1>

		<p>
			<label for="sourcekey">Source Key</label>
			<input id="sourcekey" name="sourcekey">
		</p>
				<p>
			<label for="sentiment">Sentiment</label>
			<input id="sentiment" name="sentiment">
		</p>
		<p>
			<label for="sector">Sector</label>
			<input id="sector" name="sector">
		</p>
				<p>
			<label for="source">Source</label>
			<input id="source" name="source">
		</p>
		
		<p>
			<label for="headline">Headline</label>
			<input id="headline" name="headline" >
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	
</body>
</html>
