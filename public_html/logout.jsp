<%@ page language="java" import="cs5530.*" %>
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
if (session.getAttribute("visits") != null && ((ArrayList)session.getAttribute("visits")).size() > 0)
{
	ArrayList<Visit> visits = (ArrayList<Visit>) session.getAttribute("visits");

%>
	<div class='container'>
	<h4>Do you want to confirm these visits?</h4>
	<table class='table table-condensed'><tr><th>POI ID</th><th>Date</th><th>Number of People</th><th>Cost</th></tr>
<%	for (Visit el : visits)
	{
		out.print("<tr><td>"+el.pid+"</td><td>"+el.date+"</td><td>"+el.num_people+"</td><td>"+el.cost+"</td><tr>");
	}
%>	</table>
	<form action='submit-visits.jsp'>
		<input type='submit' value='Confirm' class='btn btn-primary'>
	</form>
	</div>
<%
}
else
{
	session.invalidate();
	out.print("<div class='container'><h3>Logout Successful</h3><h4>There are no saved visits in this session. You're logged out now. Click <a href='login.jsp'>here</a> to Login again.</h4></div>");
}
%>
</body>
</html>
