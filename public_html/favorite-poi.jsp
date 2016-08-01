<%@ page language='java' import='cs5530.*' %>

<html>
<head>
</head>
<body>
<%
if (request.getParameter("pid") != null)
{
	String login = (String) session.getAttribute("login");
	int pid = Integer.parseInt(request.getParameter("pid"));
	Connector con = new Connector();
	Queries.recordFavoritePOI(pid, login, con.stmt);
	response.sendRedirect("poi.jsp?pid=" + pid+"&favorite=1");
}
%>
</body>
</html>
