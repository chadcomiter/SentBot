<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find a Stock</title>
</head>
<body>
	<form action="findstock" method="post">
		<h1>Search for a Stock by Sector</h1>
		<p>
			<label for="Sector">Sector</label>
			<input id="Sector" name="Sector" value="${fn:escapeXml(param.sector)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="stockCreate"><a href="stockcreate">Create Stock</a></div>
	<br/>
	<h1>Matching Stocks</h1>
        <table border="1">
            <tr>
                <th>InvestmentKey</th>
                <th>Ticker</th>
                <th>Sector</th>
                <th>CompanyName</th>
                <th>SourceKey</th>
                <th>Delete Stock</th>
                <th>Update Stock</th>
            </tr>
            <c:forEach items="${Stocks}" var="Stock" >
                <tr>
                    <td><c:out value="${Stock.getInvestmentKey()}" /></td>
                    <td><c:out value="${Stock.getTicker()}" /></td>
                    <td><c:out value="${Stock.getSector().name()}" /></td>
                    <td><c:out value="${Stock.getCompanyName()}" /></td>
                    <td><c:out value="${Stock.getSourceKey()}" /></td>
                    <td><a href="stockdelete?Ticker=<c:out value="${Stock.getTicker()}"/>">Delete</a></td>
                    <td><a href="stockupdate?Ticker=<c:out value="${Stock.getTicker()}"/>">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
