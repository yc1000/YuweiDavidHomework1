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
		<title>Blog list</title>
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

<p>Create a post <a href="post.jsp">here</a>!</p>

<%
	} else {
%>

<p>Hello!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to make a post.</p>

<%
	}
%>

<p>return to <a href="homework1.jsp">homepage</a></p>

<%
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);

	Query query = new Query("Greeting", guestbookKey).addSort("date", Query.SortDirection.DESCENDING);
	List<Entity> greetings = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1000));

	if (greetings.isEmpty()) {
        %>

        <p>Guestbook '${fn:escapeXml(guestbookName)}' has no messages.</p>

        <%
	} else {
        %>

		<p>Messages in Guestbook '${fn:escapeXml(guestbookName)}'.</p>
		
        <%
		for (Entity greeting : greetings) {
			pageContext.setAttribute("post_title",greeting.getProperty("title"));
			pageContext.setAttribute("post_content",greeting.getProperty("content"));
			pageContext.setAttribute("post_date",greeting.getProperty("date"));
			pageContext.setAttribute("greeting_user",greeting.getProperty("user"));
                %>  

                <p><b>${fn:escapeXml(greeting_user.nickname)}</b> wrote on 
                <b>${fn:escapeXml(post_date)}</b>:</p>
				
				<p style="font-family:Arial;color:green;font-size:30px"><a href="singleBlog.jsp?title= + ${fn:escapeXml(post_title)"><b>${fn:escapeXml(post_title)}</b></p>
            	<p>${fn:escapeXml(post_content)}</p>
            	
                <%
		
        }
	}
%>


	</body>
</html>