<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Comments</title>
</head>
<body>
	<form action="findcomments" method="post">
		<h1>Search for Comments by Sentiment ("bullish", "bearish", "none")</h1>
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
	<div id="commentCreate"><a href="commentcreate">Create Comment</a></div>
	<div id="commentDelete"><a href="commentdelete">Delete Comment</a></div>
	<div id="commentUpdate"><a href="commentupdate">Update Comment Sentiment</a></div>
	<br/>
	<h1>Matching Comments</h1>
        <table border="1">
            <tr>
                <th>Source Key</th>
                <th>Sentiment</th>
                <th>Sector</th>
                <th>Comment Text</th>
                <th>Ticker</th>
            </tr>
            <c:forEach items="${comment}" var="comment" >
                <tr>
                    <td><c:out value="${comment.getSourceKey()}" /></td>
                    <td><c:out value="${comment.getSentiment()}" /></td>
                    <td><c:out value="${comment.getSector()}" /></td>
                    <td><c:out value="${comment.getCommentText()}" /></td>
                    <td><c:out value="${comment.getTicker()}" /></td>
                </tr>
            </c:forEach>
       </table>
</body>
</html>
