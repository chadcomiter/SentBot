<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create a Stock</title>
</head>
<body>
	<h1>Create Stock</h1>
	<form action="stockcreate" method="post">
		<p>
			<label for="InvestmentKey">InvestmentKey</label>
			<input id="InvestmentKey" name="InvestmentKey" value="">
		</p>
		<p>
			<label for="Ticker">Ticker</label>
			<input id="Ticker" name="Ticker" value="">
		</p>
		<p>
			<label for="Sector">Sector</label>
			<input id="Sector" name="Sector" value="">
		</p>
		<p>
			<label for="CompanyName">CompanyName</label>
			<input id="CompanyName" name="CompanyName" value="">
		</p>
		<p>
			<label for="SourceKey">SourceKey</label>
			<input id="SourceKey" name="SourceKey" value="">
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