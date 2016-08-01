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
	float cost = Float.parseFloat(request.getParameter("cost"));
	int num_people = Integer.parseInt(request.getParameter("num-people"));
	String login = (String) session.getAttribute("login");
	int pid = Integer.parseInt(request.getParameter("pid"));
	String date = request.getParameter("date");
	Connector con = new Connector();
	Visit visit = new Visit();
	visit.cost = cost;
	visit.date = date;
	visit.login = login;
	visit.pid = pid;
	ArrayList<Visit> visits = (ArrayList<Visit>) session.getAttribute("visits");
	if (visits != null)
		visits.add(visit);
	out.print("<div class='container'><h3>Visit saved. When you logout you will be asked to confirm your visits.</h3><h4>Suggested POIs for You to Visit</h4>");
	out.print(Queries.suggestedPOIDisplay(pid, login, con.stmt));
	out.print("<a href='poi.jsp?pid="+pid+"'><h4>Return to POI</h4></a></div>");
}
%>
</body>
</html>
