<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="homework1.UserSubscriptionList" %>
<%@ page import="homework1.UserSubList" %>

<html>

	<head>
		<title>Subscription Page</title>
		<link rel="stylesheet" type="text/css" href="style.css">
	</head>

	<body>
		<p><img src="images/header.png" style="width:500px;height:228px;"></p>

<%
	UserSubscriptionList hi = new UserSubscriptionList();
	boolean performedAdd = hi.ulist.editList();
	System.out.println(hi.ulist.userList);
	if(performedAdd) {	
%>

		<p>User successfully added</p>

<%
	} else {
%>

		<p>User successfully removed</p>

<% 
	}
%>

		<p></p>
		<p>Return to <a href="homework1.jsp">homepage</a></p>
	</body>
</html>
