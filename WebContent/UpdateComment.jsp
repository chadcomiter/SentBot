<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a Comment</title>
</head>
<body>
	<h1>Update Comment</h1>
	<form action="commentupdate" method="post">
		<p>
			<label for="sourcekey">Source Key</label>
			<input id="sourcekey" name="sourcekey" value="${fn:escapeXml(param.sourcekey)}">
		</p>
		<p>
			<label for="sentiment">New Sentiment</label>
			<input id="sentiment" name="sentiment" value="${fn:escapeXml(param.sentiment)}">
		</p>
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