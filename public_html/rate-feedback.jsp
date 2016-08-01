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
if (request.getParameter("score") != null)
{
	String login = (String) session.getAttribute("login");
	int fid = Integer.parseInt(request.getParameter("fid"));
	int pid = Integer.parseInt(request.getParameter("pid"));
	int score = Integer.parseInt(request.getParameter("score"));
	Connector con = new Connector();
	Queries.usefulnessFeedback(login, fid, score, con.stmt);
	response.sendRedirect("poi.jsp?pid="+pid);
}
else
{
	int pid = Integer.parseInt(request.getParameter("pid"));
	%>
	<div class='container'>
	<h4>Rate This Feedback: 0 is bad, 2 is good</h4>
	<form action='rate-feedback.jsp'><input type='hidden' name='fid' value='<%=request.getParameter("fid")%>'>
		<div class="form-group">
			<div class='row'><div class='col-md-3'><input type='hidden' name='pid' value='<%=pid%>'></div></div>
			<div class='row'><div class='col-md-3'>Score (0-2): <input name='score' class='form-control'></div></div><br>
			<div class='row'><div class='col-md-3'><input class='btn btn-primary' type='submit'></div></div>
		</div>
	</form>
	</div>
	<%
}
%>
</body>
</html>
