<%@ page language='java' import='cs5530.*' %>
<%@ page import="java.util.ArrayList" %>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<%
if (session.getAttribute("visits") != null)
{
	ArrayList<Visit> visits = (ArrayList<Visit>) session.getAttribute("visits");
	String login = (String)session.getAttribute("login");
	Connector con = new Connector();
	for (Visit el : visits)
	{
		Queries.recordPOIVisit(el.cost,el.num_people,login,el.pid,el.date,con.stmt);
	}
	session.invalidate();
	out.print("<div class='container'><h3>You're now logged out.</h3><h3>Click <a href='login.jsp'>here</a> to Login again.</h3></div>");
}
else
{
	out.print("<div class='container'><h3>There are no saved visits in this session.</h3><h3>Click <a href='login.jsp'>here</a> to Login again.</h3></div>");
}
%>
</body>
</html>
