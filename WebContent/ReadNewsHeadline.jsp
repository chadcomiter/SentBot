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
	<form action="readnewsheadline" method="post">
		<h1>Search for a News Headline by Sentiment</h1>
		<p>
			<label for="sentiment">Sentiment</label>
			<input id="sentiment" name="sentiment" value="${fn:escapeXml(param.sentiment)}">
		</p>
		<p>
			<input type="submit">
			<br/><br/><br/>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
	</form>
	<br/>
	<div id="newsheadlinecreate"><a href="newsheadlinecreate">Create News Headline</a></div>
	<br/>
	<h1>Matching News Headlines</h1>
        <table border="1">
            <tr>
                <th>Source Key</th>
                <th>Source</th>
                <th>Headline</th>
                <th>Sentiment</th>
                <th>Sector</th>
                <th>Delete</th>
                <th>Update</th>
            </tr>

            <c:forEach items="${newsheadline}" var="newsheadline" >
                <tr>
                <td><c:out value="${newsheadline.getSourceKey()}" /></td>
                    <td><c:out value="${newsheadline.getNewsSource()}" /></td>
                    <td><c:out value="${newsheadline.getHeadline()}" /></td>
                    <td><c:out value="${newsheadline.getSentiment()}" /></td>
                    <td><c:out value="${newsheadline.getSector()}"/></td>
                     <td id="Delete"><a href="newsheadlinedelete">Delete</a></td>
                     <td id="update"><a href="newsheadlineupdate">Update</a></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
