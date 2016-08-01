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
if (request.getParameter("login") == null)
{
	session.invalidate();
%>
	<div class='container'>
	<h1>UTrack</h1>
	<form name='authenticate' method=get action='login.jsp'>
		<div class="form-group">
			<div class='row'><div class='col-md-3'>Login:</div></div>
			<div class='row'><div class='col-md-3'><input name='login' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Password:</div></div>
			<div class='row'><div class='col-md-3'><input type='password' name='password' class="form-control"></div></div><br>
			<div class='row'><div class='col-md-3'><input type='submit' class="btn btn-primary"></div></div>
		</div>
	</form>
	<a href='register.jsp'><h4>Register New User</h4></a>
	</div>
<%
}
else
{
	String login = (String) request.getParameter("login");
	String password = (String) request.getParameter("password");
	Connector con = new Connector();
	if (Queries.authenticate(login, password, con.stmt))
	{
		session.setAttribute("logged-in", "true");
		session.setAttribute("login", request.getParameter("login"));
		ArrayList<Visit> visits = new ArrayList<Visit>();
		session.setAttribute("visits", visits);
		boolean isAdmin = Queries.isAdmin(login, con.stmt);
		out.print(isAdmin);
		if (isAdmin)
			session.setAttribute("isAdmin", "true");
		response.sendRedirect("home.jsp");
	}
	else
	{
		session.invalidate();
		response.sendRedirect("login-fail.jsp");
	}
}
%>
</body>
</html>
