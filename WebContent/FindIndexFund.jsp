<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search index fund</title>
</head>
<body>
	<form action="findindexfund" method="post">
		<h1>Search for index fund by Sector </h1>
		<p>
			<label for="sector">Sector</label>
			<input id="sector" name="sector" value="${fn:escapeXml(param.sector)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>

	<br/>
	<div id="indexfundCreate"><a href="indexfundcreate">Create Index Fund</a></div>
		<!--   -->
	<div id="indexfundDelete"><a href="indexfunddelete">Delete Index Fund</a></div>
			
	<div id="indexfundUpdate"><a href="indexfundupdate">Update Index Fund Sector</a></div>

	<br/>

	<h1>Matching Comments</h1>
        <table border="1">
            <tr>
            	<th>Investment Key</th>
                <th>Source Key</th>
                <th>Sector</th>
                <th>Name</th>
                <th>Ticker</th>
            </tr>
            <c:forEach items="${comment}" var="comment" >
                <tr>
                
                    <td><c:out value="${comment.getInvestmentKey()}" /></td>
                    <td><c:out value="${comment.getSourceKey()}" /></td>
                    <td><c:out value="${comment.getSector()}" /></td>
                    <td><c:out value="${comment.getIndexFundName()}" /></td>
                    <td><c:out value="${comment.getTicker()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
