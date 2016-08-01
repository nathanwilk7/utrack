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
%>
	<div class='container'>
	<h1>UTrack</h1>
	<h3>Register New User</h3>
	<form method=get action='register.jsp'>
		<div class="form-group">
                        <div class='row'><div class='col-md-3'>Login:</div></div>
                        <div class='row'><div class='col-md-3'><input name='login' class="form-control"></div></div>
                        <div class='row'><div class='col-md-3'>Password:</div></div>
                        <div class='row'><div class='col-md-3'><input name='password' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Name:</div></div>
			<div class='row'><div class='col-md-3'><input name='name' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Address:</div></div>
			<div class='row'><div class='col-md-3'><input name='address' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>State</div></div>
			<div class='row'><div class='col-md-3'><input name='state' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Country:</div></div>
			<div class='row'><div class='col-md-3'><input name='country' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Zipcode:</div></div>
			<div class='row'><div class='col-md-3'><input name='zipcode' class="form-control"></div></div>
			<div class='row'><div class='col-md-3'>Phone:</div></div>
			<div class='row'><div class='col-md-3'><input name='phone' class="form-control"></div></div>
			<br>
                        <div class='row'><div class='col-md-3'><input type='submit' class="btn btn-primary"></div></div>
                </div>
	</form>
<%
}
else
{
	String login = (String) request.getParameter("login");
	String password = (String) request.getParameter("password");
	String name = (String) request.getParameter("name");
	String address = (String) request.getParameter("address");
	String state = (String) request.getParameter("state");
	String country = (String) request.getParameter("country");
	String zipcode = (String) request.getParameter("zipcode");
	String phone = (String) request.getParameter("phone");
	Connector con = new Connector();
	if (Queries.checkUsernameUnique(login, con.stmt))
	{
		Connector con2 = new Connector();
		Queries.addUserFull(login,password,name,address,state,country,zipcode,phone,con2.stmt);
		session.setAttribute("logged-in", "true");
		session.setAttribute("login", login);
                ArrayList<Visit> visits = new ArrayList<Visit>();
                session.setAttribute("visits", visits);
		response.sendRedirect("home.jsp");
	}
	else
	{
		out.print("<div class='container'><h4>Try a different Username, that one is taken. <a href='register.jsp'>Register New User</a></h4></div>");
	}
}
%>
</body>
</html>
