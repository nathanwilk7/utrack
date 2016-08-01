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
if (session.getAttribute("isAdmin") != null)
{	
	Connector con = new Connector();
	int n = 10;
	if (request.getParameter("n") != null)
		n = Integer.parseInt(request.getParameter("n"));
	out.print("<div class='container'><h3>User Awards</h3>");
        out.print("<h4>Most Trusted</h4>"+Queries.mostTrustedDisplay(n, con.stmt));
	out.print("<h4>Most Useful</h4>"+Queries.mostUsefulDisplay(n, con.stmt));
	out.print("<h4>How many results do you want to show?</h4><form action='awards.jsp'><div class='row'><div class='col-md-3'><input class='form-control'name='n'></div></div><br><input class='btn btn-primary'type='submit'></form><div class='row'><div class='col-md-3'><h4>Return to <a href='home.jsp'>Home</a></h4></div></div></div>");
}
%>
</body>
</html>
