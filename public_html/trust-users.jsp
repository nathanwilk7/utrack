<%@ page language='java' import='cs5530.*' %>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<%
if (request.getParameter("login") != null)
{
String login1 = (String) session.getAttribute("login");
String login2 = (String) request.getParameter("login");
int trust = Integer.parseInt(request.getParameter("trust"));
Connector con = new Connector();
Queries.trustDistrustUser(login1, login2, trust, con.stmt);
response.sendRedirect("home.jsp");
}
else
{
Connector con = new Connector();
out.print("<div class='container'><h3>Trust/Distrust Users</h3>" + Queries.displayUsers(con.stmt)+"</div>");
}
%>
</body>
</html>
