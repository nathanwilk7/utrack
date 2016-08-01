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
if (request.getParameter("pid") != null)
{
	ArrayList<Visit> visits = null;
	if (session.getAttribute("visits") != null)
		visits = (ArrayList<Visit>) session.getAttribute("visits");
	String output = "<div class='container'><h2>Place Of Interest</h2></div>";
	Connector con = new Connector();
	int pid = Integer.parseInt(request.getParameter("pid"));
	output += Queries.displayPOITable(pid, con.stmt);
	out.print(output);
%>
	<div class='container'>
	<h4>Record Visit</h4>
	<form action='visit-poi.jsp'>
		<div class="form-group">
			<div class='row'><div class='col-md-3'><input class="form-control" type='hidden' name='pid' value='<%=request.getParameter("pid") %>'></div></div>
		        <div class='row'><div class='col-md-3'>Cost (Float): </div></div>
			<div class='row'><div class='col-md-3'><input name='cost' class="form-control"></div></div>
	        	<div class='row'><div class='col-md-3'>Number of People (Int): </div></div>
			<div class='row'><div class='col-md-3'><input  name='num-people' class="form-control"></div></div>
		        <div class='row'><div class='col-md-3'>Date (YYYY-MM-DD): </div></div>
			<div class='row'><div class='col-md-3'><input name='date' class="form-control"></div></div><br>
	        	<div class='row'><div class='col-md-3'><input type='submit' class='btn btn-primary'></div></div>
		</div>
	</form>
	</div>
	<div class='container'>
	<a href='favorite-poi.jsp?pid=<%=request.getParameter("pid") %>'><h4>Record this Place of Interest as a Favorite</h4></a><br>
	<% if (request.getParameter("favorite") != null)
	{
        	out.print("Favorite Recorded.<br>");
	}
		
 %>
	<h4>Record Feedback for this Place of Interest</h4>
	<form action='poi.jsp'>
		<div class="form-group">
		<div class='row'><div class='col-md-3'><input type='hidden' name='pid' value='<%=request.getParameter("pid") %>' class="form-control"></div></div>
		<div class='row'><div class='col-md-3'>Score (0-10): </div></div>
		<div class='row'><div class='col-md-3'><input name='score' class="form-control"></div></div>
		<div class='row'><div class='col-md-3'>Description: </div></div>
		<div class='row'><div class='col-md-3'><input name='description' class="form-control"></div></div><br>
		<div class='row'><div class='col-md-3'><input type='submit' class='btn btn-primary'></div></div>
		</div>
	</form>
	</div>
<%
	if (request.getParameter("score") != null)
	{
		String login = (String) session.getAttribute("login");
		int score = Integer.parseInt(request.getParameter("score"));
		String description = (String) request.getParameter("description");
		Connector con2 = new Connector();
		Queries.recordPOIFeedback(pid,login,score,description,con2.stmt);
		out.print("<div class='container'>Feedback Recorded</div>");
	}
	Connector con3 = new Connector();
	out.print("<div class='container'><h4>Feedbacks for this Place of Interest</h4>" + Queries.displayFeedback(pid, con3.stmt) + "</div>");
%>
	<div class='container'>
		<a href='useful-feedbacks.jsp?pid=<%=request.getParameter("pid")%>'><h4>Most Useful Feedbacks for this Place of Interest</h4></a><br>
		<a href='home.jsp'><h4>Return to Home</h4></a>
	</div>
<%
}
else
{
	out.print("Return to <a href='home.jsp'>home.jsp</a>, you need to specify a pid.");
}
%>
</body>
</html>

