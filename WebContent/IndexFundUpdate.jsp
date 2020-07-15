<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update a Index Fund</title>
</head>
<body>
	<h1>Update Index Fund</h1>
	<form action="indexfundupdate" method="post">
		<p>
			valid sectors (energy, materials
            industrials, consumerdiscretionary, consumerstaples, healthcare
            financials, informationtechnology, teclecommunicationservices, utilities, realestate)<br>
			<label for="oldsector">old sector</label>
			<input id="oldsector" name="oldsector" value="${fn:escapeXml(param.oldsector)}">
		</p>
		<p>
			<label for="newsector">new sector</label>
			<input id="newsector" name="newsector" value="${fn:escapeXml(param.newsector)}">
		</p>
		<p>
			<label for="ticker">Ticket</label>
			<input id="ticker" name="ticker" value="${fn:escapeXml(param.ticker)}">
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