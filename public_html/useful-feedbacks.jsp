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
if (request.getParameter("pid") != null)
{
	int pid = Integer.parseInt(request.getParameter("pid"));
	Connector con = new Connector();
	int n = 10;
	if (request.getParameter("n") != null)
		n = Integer.parseInt(request.getParameter("n"));
	out.print("<div class='container'><h4>Most Useful Feedbacks for this Place of Interest</h4>");
        out.print(Queries.mostUsefulFeedbackPOIDisplay(pid, n, con.stmt));
	out.print("<h4>How many results do you want to show? (Int)</h4>" +
		"<form action='useful-feedbacks.jsp'>" +
		"<div class='row'><div class='col-md-1'><input type='hidden' name='pid' value='" + pid +
		"'></div></div><div class='row'><div class='col-md-2'><input name='n' class='form-control'><br><input class='btn btn-primary' type='submit'></div></div></form><div class='row'><div class='col-md-3'><h4>Return to <a href='poi.jsp?pid="+pid+"'>POI</a></h4></div></div></div>");
}
%>
</body>
</html>
