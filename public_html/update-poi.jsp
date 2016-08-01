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
if (request.getParameter("name") != null)
{
	int pid = Integer.parseInt(request.getParameter("pid"));
	String name = (String) request.getParameter("name");
        String address = (String) request.getParameter("street_address");
        String city = (String) request.getParameter("city");
        String state = (String) request.getParameter("state");
        String country = (String) request.getParameter("country");
        String zipcode = (String) request.getParameter("zipcode");
        String url = (String) request.getParameter("url");
        String phone = (String) request.getParameter("phone");
	String year = (String) request.getParameter("year-est");
        String hours = (String) request.getParameter("hours");
	String price = (String) request.getParameter("price");
        String category = (String) request.getParameter("category");
        String description = (String) request.getParameter("description");
	Connector con = new Connector();
	Queries.updateField(pid, "name", name, con.stmt);
	Queries.updateField(pid, "street_address", address, con.stmt);
        Queries.updateField(pid, "city", city, con.stmt);
        Queries.updateField(pid, "state", state, con.stmt);
        Queries.updateField(pid, "country", country, con.stmt);
        Queries.updateField(pid, "zipcode", zipcode, con.stmt);
        Queries.updateField(pid, "url", url, con.stmt);
        Queries.updateField(pid, "phone", phone, con.stmt);
        Queries.updateField(pid, "year_est", year, con.stmt);
        Queries.updateField(pid, "hours", hours, con.stmt);
        Queries.updateField(pid, "price", price, con.stmt);
        Queries.updateField(pid, "category", category, con.stmt);
        Queries.updateField(pid, "description", description, con.stmt);
	out.print("<div class='container'><h3>This POI has been updated. Return to <a href='home.jsp'>Home</a></h3></div>");
}
else if (request.getParameter("pid") == null)
{
	out.print("<div class='container'><form action='update-poi.jsp'><div class='row'><div class='col-md-6'><h3>Enter the Place Of Interest ID (PID)</h3></div></div><div class='row'><div class='col-md-3'><input name='pid'class='form-control'></div></div><br><div class='row'><div class='col-md-3'><input type='submit'class='btn btn-primary'></div></div></form></div>");
	Connector con = new Connector();
	out.print("<div class='container'>"+Queries.displayAllPOITable(con.stmt)+"</div>");
}
else
{
	int pid = Integer.parseInt(request.getParameter("pid"));
	Connector con = new Connector();
	out.print("<div class='container'><h3>Update POI</h3>" + Queries.updatePOIForm(pid, con.stmt) + "</div>");
}
%>
</body>
</html>
