<%@ page language='java' import='cs5530.*' %>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
<div class='container'><h3>Add Place of Interest</h3></div>
<%
if (request.getParameter("name") != null)
{
	String name = (String) request.getParameter("name");
        String address = (String) request.getParameter("address");
        String city = (String) request.getParameter("city");
        String state = (String) request.getParameter("state");
        String country = (String) request.getParameter("country");
        String zipcode = (String) request.getParameter("zipcode");
        String url = (String) request.getParameter("url");
        String phone = (String) request.getParameter("phone");
        int year = Integer.parseInt(request.getParameter("year"));
        String hours = (String) request.getParameter("hours");
        double price = Double.parseDouble(request.getParameter("price"));
        String category = (String) request.getParameter("category");
        String description = (String) request.getParameter("description");
        String keywords = (String) request.getParameter("keywords");
	Connector con = new Connector();
	Queries.addPOI(name,address,city,state,country,zipcode,url,phone,year,hours,price,category,description,keywords,con.stmt);
	out.print("<div class=container'><h4>This POI has been added. Return to <a href='home.jsp'>Home</a></h4></div>");
}
else
{
%>
<div class='container'>
<form action='add-poi.jsp'>
	<div class='row'><div class='col-md-3'>Name:</div></div>
	<div class='row'><div class='col-md-3'><input name='name' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Address:</div></div>
	<div class='row'><div class='col-md-3'><input name='address' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>City: </div></div>
	<div class='row'><div class='col-md-3'><input name='city'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>State: </div></div>
	<div class='row'><div class='col-md-3'><input name='state'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Country: </div></div>
	<div class='row'><div class='col-md-3'><input name='country'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Zipcode: </div></div>
	<div class='row'><div class='col-md-3'><input name='zipcode' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>URL: </div></div>
	<div class='row'><div class='col-md-3'><input name='url' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Phone Number: </div></div>
	<div class='row'><div class='col-md-3'><input name='phone' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Year Established (int): </div></div>
	<div class='row'><div class='col-md-3'><input name='year'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Hours: </div></div>
	<div class='row'><div class='col-md-3'><input name='hours'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Price (double): </div></div>
	<div class='row'><div class='col-md-3'><input name='price' class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Category: </div></div>
	<div class='row'><div class='col-md-3'><input name='category'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Description: </div></div>
	<div class='row'><div class='col-md-3'><input name='description'class='form-control'></div></div>
	<div class='row'><div class='col-md-3'>Keywords (CSV): </div></div>
	<div class='row'><div class='col-md-3'><input name='keywords'class='form-control'></div></div><br>
	<div class='row'><div class='col-md-3'><input type='submit'class='btn btn-primary'></div></div>
</form>
</div>
<%
}
%>
</body>
</html>
