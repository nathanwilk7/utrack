<%@ page language='java' import='cs5530.*' %>
<%@ page import='java.sql.*' %>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<%
if (session.getAttribute("logged-in") != null && session.getAttribute("logged-in").equals("true"))
{
	%>
	<div class='container'>
		<div class='row'><h1>UTrack</h1></div>
			<div class='col-md-6'><h2>Browse Places of Interest</h2></div>
			<div class='col-md-6'>
				<h2>Actions</h2>
			</div>
		</div>
		<form name='search-poi'><div class="form-group">
			<div class='row'><div class='col-md-1'></div><div class='col-md-5'><h4>Search By Name (Leave Empty to Show All)</h4></div><div class='col-md-6'><a href='trust-users.jsp'>Trust/Distrust User</a></div></div>
			<div class='row'><div class='col-md-1'></div><div class='col-md-5'><input name='poi-name' class="form-control"></div><div class='col-md-6'><a href='degrees.jsp'>Degress of Separation</a></div></div>
			<div class='row'><div class='col-md-1'></div><div class='col-md-5'><a data-toggle="collapse" data-target="#demo">More Browsing Options</a></div><div class='col-md-6'><a href='statistics.jsp'>Statistics</a></div></div>
			<div id="demo" class="collapse">
				<div class='row'><div class='col-md-1'></div><div class='col-md-5'><h5>Price Range</h5></div></div>
				<div class='row'><div class='col-md-1'></div><div class='col-md-2'>Min:<input name='min-price' class="form-control"> </div><div class='col-md-2'>Max:<input name='max-price' class="form-control"></div></div>
				<div class='row'><div class='col-md-1'></div><div class='col-md-5'><h5>Address</h5></div></div>
				<div class='row'><div class='col-md-1'></div><div class='col-md-2'>State:<input name='state' class='form-control'></div><div class='col-md-2'>City:<input name='city' class='form-control'></div></div>
				<div class='row'><div class='col-md-1'></div><div class='col-md-5'><h5>Category:</h5></div></div>
				<div class='row'><div class='col-md-1'></div><div class='col-md-4'><input name='category' class='form-control'></div></div>
			</div>
		<br>
		<div class='row'><div class='col-md-1'></div><div class='col-md-5'><input type='submit' class='btn btn-primary'></div></div>
		</form>
	</div>
	<%
	if (request.getParameter("poi-name") != null)
	{
		String poiName = request.getParameter("poi-name");
		String minPrice = request.getParameter("min-price");
		String maxPrice = request.getParameter("max-price");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String category = request.getParameter("category");
		String where = " where name like '%"+poiName+"%' ";
		if (minPrice.length() > 0)
			where += " and price >= " + minPrice;
		if (maxPrice.length() > 0)
			where += " and price <= " + maxPrice;
		if (state.length() > 0)
			where += " and state like '%" + state + "%' ";
		if (city.length() > 0)
			where += " and city like '%" + city + "%' ";
		if (category.length() > 0)
			where += " and category like '%" + category + "%' ";
		Connector con = new Connector();
		ResultSet results = Queries.browsePOIsWeb(where, con.stmt);
		%>
		<div class='container'><table class='table table-condensed'><tr><th>POI ID</th><th>Name</th><th>Address</th><th>City</th><th>State</th><th>Country</th><th>Zipcode</th><th>URL</th><th>Phone Number</th>
		<th>Year Est.</th><th>Hours</th><th>Price</th><th>Category</th><th>Description</th></tr>
		<%
		while (results.next())
		{
			String rows = "<tr>";
			rows += "<td>" + results.getString("pid") + "</td>";
			rows += "<td><a href=poi.jsp?pid="+ results.getString("pid") + ">" + results.getString("name") + "</a></td>";
			rows += "<td>" + results.getString("street_address") + "</td>";
			rows += "<td>" + results.getString("city") + "</td>";
			rows += "<td>" + results.getString("state") + "</td>";
			rows += "<td>" + results.getString("country") + "</td>";
			rows += "<td>" + results.getString("zipcode") + "</td>";
			rows += "<td>" + results.getString("url") + "</td>";
			rows += "<td>" + results.getString("phone") + "</td>";
			rows += "<td>" + results.getString("year_est") + "</td>";
			rows += "<td>" + results.getString("hours") + "</td>";
			rows += "<td>" + results.getString("price") + "</td>";
			rows += "<td>" + results.getString("category") + "</td>";
			rows += "<td>" + results.getString("description") + "</td>";
			rows += "</tr>";
			out.print(rows);	
		}
		%></table></div><%
		try { results.close(); }
		catch (Exception e) { e.printStackTrace(); }
	}
        if (session.getAttribute("isAdmin") != null && session.getAttribute("isAdmin").equals("true"))
        {
        	out.print("<div class='container'><div class='row'><div class='col-md-6'><h2>Admin</h2><a href='add-poi.jsp'>Add POI</a><br>" +
	        "<a href='update-poi.jsp'>Update POI</a><br>" +
	        "<a href='awards.jsp'>User Awards</a></div></div></div>");
        }
	%>
	<br>
	<div class='container'><div class='row'><div class='col-md-6'><a href='logout.jsp'>Logout</a></div></div></div>
	<%
}
else
{
	out.print("You need to login at <a href='login.jsp'>login.jsp</a>");
}
%>
</body>
</html>
