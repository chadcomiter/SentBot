<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Comment</title>
</head>
<body>
	<h1>Create Comment</h1>
	<form action="commentcreate" method="post">
		<p>
			<label for="sourcekey">Enter Source Key</label>
			<input id="sourcekey" name="sourcekey" value="">
		</p>
		<p>
			<label for="sentiment">Enter Sentiment (lowercase)</label>
			<input id="sentiment" name="sentiment" value="">
		</p>
		<p>
			<label for="sector">Enter Sector</label>
			<input id="sector" name="sector" value="">
		</p>
		<p>
			<label for="commenttext">Enter Comment Text</label>
			<input id="commenttext" name="commenttext" value="">
		</p>
		<p>
			<label for="ticker">Enter Affected Ticker</label>
			<input id="ticker" name="ticker" value="">
		<p>
			<input type="submit">
		</p>
	</form>
	<br/><br/>
	<p>
		<span id="successMessage"><b>${messages.success}</b></span>
	</p>
</body>
</html>