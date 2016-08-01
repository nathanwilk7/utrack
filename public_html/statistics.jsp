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
	Connector con = new Connector();
	int n = 10;
	if (request.getParameter("n") != null)
		n = Integer.parseInt(request.getParameter("n"));
	out.print("<div class='container'><h3>Statistics</h3><h4>Most Popular Places of Interest By Category</h4>");
        out.print(Queries.popularPOIsDisplay(n, con.stmt));
	out.print("<h4>Top Rated Places of Interest By Category</h4>"+Queries.topRatedPOIsDisplay(n, con.stmt));
	out.print("<h4>Most Expensive Places of Interest By Category</h4>"+Queries.expensivePOIsDisplay(n, con.stmt));
	out.print("<h4>How many results per category do you want to show?</h4><form action='statistics.jsp'><div class='row'><div class='col-md-3'><input class='form-control' name='n'></div></div><br><div class='row'><div class='col-md-3'><input type='submit' class='btn btn-primary'></div></div></form><class='row'><div class='col-md-3'><h4>Return to <a href='home.jsp'>Home</a></h4></div></div></div>");
%>
</body>
</html>
