<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>

	<head>
		<title>Post Writing Page</title>
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>

	<body>
		<p>
		<img src="images/header.png" style="width:500px;height:228px;"></p>
	
<%
	String guestbookName = request.getParameter("guestbookName");
	if (guestbookName == null) {
		guestbookName = "default";
	}
	pageContext.setAttribute("guestbookName", guestbookName);
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
	if (user != null) {
		pageContext.setAttribute("user", user);
%>


<p>Hello, ${fn:escapeXml(user.nickname)}! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>

<p>return to <a href="homework1.jsp">homepage</a></p>

<p>What do you want to say today?</p>

<%
	} else {
%>

<p>Sorry, anonymous users can't make a post :(
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to make a post.</p>

<%
	}
%>


	<form action="/post" method="post">
		<div><textarea name="title" rows="1" cols="60"></textarea></div>
		<div><textarea name="content" rows="5" cols="60"></textarea></div>
		<div><input type="submit" value="Post" /></div>
		<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
	</form>

	</body>
</html>