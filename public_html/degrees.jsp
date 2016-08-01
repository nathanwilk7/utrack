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
if (session.getAttribute("login") != null)
{
	%><div class='container'><h3>Degrees of Separation</h3><form action='degrees.jsp'>
		<div class='row'><div class='col-md-3'><h4>Enter Username of Other User:</h4></div></div>
		<div class='row'><div class='col-md-3'><input name='login2' class='form-control'></div></div><br>
		<div class='row'><div class='col-md-3'><input type='submit' class='btn btn-primary'></div></div></form>
	</div><%
	if (request.getParameter("login2") != null)
	{
		String login1 = (String) session.getAttribute("login");
	        String login2 = (String) request.getParameter("login2");
		Connector con = new Connector();
	        int sep = Queries.twoDegreesSep(login1, login2, con.stmt);
		if (sep > 0)
			out.print("<div class='container'><h4>Result: " + sep + " Degree(s) of Seperation between you and "+login2+"</h4></div>");
		else
			out.print("<div class='container'><h4>No connection within two hops between you and "+login2+"</h4></div>");
	}
}
else
{
	out.print("Your session is inactive, you need to login <a href='login.jsp'>here</a>");
}
%>
<div class='container'><h4>Return to <a href='home.jsp'>Home</a></h4></div>
</body>
</html>
