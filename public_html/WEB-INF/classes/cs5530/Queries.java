/* Author: Nathan Wilkinson
 * CS 5530
 * March 2016
 * Queries.java
 */

package cs5530;

import java.sql.*;
import java.io.*;
import java.util.List;

public class Queries
{
	public static void printOtherUsers(String login, Statement stmt)
	{
		String sql = "select login, name from Users where login<>'"+login+"'";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				System.out.println("User Login: " + rs.getString("login") + ", Name: " + rs.getString("name"));
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static int nameToFid(BufferedReader in, Statement stmt)
	{
		int pid = -1;
		while (true)
		{
			try
			{
				System.out.print("POI Name for which to see Feedback (Wildcard Search): ");
				String name = in.readLine();
				String sql = "select name, pid, street_address, city, state, country, zipcode from POI where name like '%"+name+"%'";
				String output = "";
				ResultSet rs = null;
				rs = stmt.executeQuery(sql);
				int rowCount = 0;
				String poi_name = "";
				while (rs.next())
				{
					pid = Integer.parseInt(rs.getString("pid"));
					output += "POI pid: " + rs.getString("pid") + ", Name: " + rs.getString("name") + ", Address: "
							+ rs.getString("street_address") + ", " + rs.getString("city") + ", " + rs.getString("state") + 
							", " + rs.getString("country") + ", " + rs.getString("zipcode") + "\n";
					rowCount++;
					if (rowCount == 1)
						poi_name = rs.getString("name");
				}
				if (rowCount == 0)
				{
					System.out.println("There are no POIs found with that name. Please try again.");
				}
				else if (rowCount > 1)
				{
					System.out.println("There are multiple POIs with that name, choose the pid that you want.");
					System.out.println(output);
					while (true)
					{
						System.out.print("POI pid: ");
						String input = in.readLine();
						try
						{
							pid = Integer.parseInt(input);
							break;
						}
						catch (Exception e)
						{
							System.out.println("Error: Please enter an int.");
						}
					}
					rs.close();
					break;
				}
				else
				{
					System.out.println("There's only 1 POI with that name.\nName: " + poi_name);
					rs.close();
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		String sql2 = "select fid, login, text, fbdate, score from Feedback where pid="+pid;
		ResultSet rs2 = null;
		try
		{
			boolean has = false;
			System.out.println("Existing Feedbacks for that POI");
			rs2 = stmt.executeQuery(sql2);
			while (rs2.next())
			{
				System.out.println("Feedback fid: " + rs2.getString("fid") + ", Login: " + rs2.getString("login") + 
						", Text: " + rs2.getString("text") + ", Date: " + rs2.getString("fbdate"));
				has = true;
			}
			rs2.close();
			if (!has)
			{
				System.out.println("No results found");
				return -1;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		int fid = 0;
		while (true)
		{
			System.out.print("Feedback fid: ");
			String input = "";
			try
			{
				input = in.readLine();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				fid = Integer.parseInt(input);
				break;
			}
			catch (Exception e)
			{
				System.out.println("Error: Please enter an int.");
			}
		}
		return fid;
	}

	public static int nameToPid(BufferedReader in, Statement stmt)
	{
		int pid = -1;
		while (true)
		{
			try
			{
				System.out.print("POI Name (Wildcard Search): ");
				String name = in.readLine();
				String sql = "select name, pid, street_address, city, state, country, zipcode from POI where name like '%"+name+"%'";
				String output = "";
				ResultSet rs = null;
				rs = stmt.executeQuery(sql);
				int rowCount = 0;
				String poi_name = "";
				while (rs.next())
				{
					pid = Integer.parseInt(rs.getString("pid"));
					output += "POI pid: " + rs.getString("pid") + ", Name: " + rs.getString("name") + ", Address: "
							+ rs.getString("street_address") + ", " + rs.getString("city") + ", " + rs.getString("state") + 
							", " + rs.getString("country") + ", " + rs.getString("zipcode") + "\n";
					rowCount++;
					if (rowCount == 1)
						poi_name = rs.getString("name");
				}
				if (rowCount == 0)
				{
					System.out.println("There are no POIs found with that name. Please try again.");
				}
				else if (rowCount > 1)
				{
					System.out.println("There are multiple POIs with that name, choose the pid that you want.");
					System.out.println(output);
					while (true)
					{
						System.out.print("POI pid: ");
						String input = in.readLine();
						try
						{
							pid = Integer.parseInt(input);
							break;
						}
						catch (Exception e)
						{
							System.out.println("Error: Please enter an int.");
						}
					}
					rs.close();
					break;
				}
				else
				{
					System.out.println("There's only 1 POI with that name.\nName: " + poi_name);
					rs.close();
					break;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return pid;
	}

	public static String mostUseful(int n, Statement stmt)
	{
		String sql = "select avg(R.rating) as u_rating, U.login, U.name from Rate R, Users U "
				+ "where R.login=U.login group by U.login order by u_rating desc limit "+n;
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "Average Usefulness: " + rs.getString("u_rating") + ", Login: " + rs.getString("login") + ", Name: "
						+ rs.getString("name") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static String mostUsefulDisplay(int n, Statement stmt)
        {
                String sql = "select avg(R.rating) as u_rating, U.login, U.name from Rate R, Users U "
                                + "where R.login=U.login group by U.login order by u_rating desc limit "+n;
                String output = "<table class='table table-condensed'><tr><th>Avg. Usefulness</th><th>Login</th><th>Name</th>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                output += "<tr><td>" + rs.getString("u_rating") + "</td><td>" + rs.getString("login") + "</td><td>"
                                                + rs.getString("name") + "</td></tr>";
                        }
                        rs.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String mostTrustedDisplay(int n, Statement stmt)
        {
                String sql = "select sum(T.isTrusted) as trust_score, U.login, U.name from Trusts T, Users U "
                                + "where T.login2=U.login group by T.login2 order by trust_score desc limit "+n+";";
                String output = "<table class='table table-condensed'><tr><th>Trust Score</th><th>Login</th><th>Name</th>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                output += "<tr><td>" + rs.getString("trust_score") + "</td><td>" + rs.getString("login") + "</td><td>"
                                                + rs.getString("name") + "</td></tr>";
                        }
                        rs.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String mostTrusted(int n, Statement stmt)
	{
		String sql = "select sum(T.isTrusted) as trust_score, U.login, U.name from Trusts T, Users U "
				+ "where T.login2=U.login group by T.login2 order by trust_score desc limit "+n+";";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "Trust Score: " + rs.getString("trust_score") + ", Login: " + rs.getString("login") + ", Name: "
						+ rs.getString("name") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}
	public static void updateField(int pid, String field, String value, Statement stmt)
	{
		String sql = "update POI set "+field+"='"+value+"' where pid="+pid+";";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			System.out.println("Error: Your field or value was invalid.");
			e.printStackTrace();
		}
	}
	
	public static String updatePOIForm(int pid, Statement stmt)
        {
                String sql = "select * from POI where pid = "+pid+";";
                String output = "<form action='update-poi.jsp'><input type='hidden' name='pid' value='"+pid+"'>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                                output += "<div class='row'><div class='col-md-3'>Name:</div></div><div class='row'><div class='col-md-3'><input class='form-control' name='name' value='" 
						+ rs.getString("name") + "'></div></div><div class='row'><div class='col-md-3'>Street Address: </div></div><div class='row'><div class='col-md-3'><input  class='form-control' name='street_address' value='"
                                         	+ rs.getString("street_address") + "'></div></div><div class='row'><div class='col-md-3'>City: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='city' value='" + rs.getString("city") + "'></div></div><div class='row'><div class='col-md-3'>State: "
						+ "<input class='form-control' name='state' value='"
                                                + rs.getString("state") + "'></div></div><div class='row'><div class='col-md-3'>Country: </div></div><div class='row'><div class='col-md-3'><input name='country' class='form-control' value='" + rs.getString("country") + "'></div></div><div class='row'><div class='col-md-3'>Zipcode: </div></div><div class='row'><div class='col-md-3'><input class='form-control' "
						+ "name='zipcode' value='"
                                                + rs.getString("zipcode") + "'></div></div><div class='row'><div class='col-md-3'>URL: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='url' value='" + rs.getString("url") + "'></div></div><div class='row'><div class='col-md-3'>Phone Number: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='phone' value='"
                                                + rs.getString("phone") + "'></div></div><div class='row'><div class='col-md-3'>Year Established: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='year-est' value='" + rs.getString("year_est") + "'></div></div><div class='row'><div class='col-md-3'>Hours: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='hours' value='"
                                                + rs.getString("hours") + "'></div></div><div class='row'><div class='col-md-3'>Price: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='price' value='" + rs.getString("price") + "'></div></div><div class='row'><div class='col-md-3'>Category: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='category' value='"
                                                + rs.getString("category") + "'></div></div><div class='row'><div class='col-md-3'>Description: </div></div><div class='row'><div class='col-md-3'><input class='form-control' name='description' value='" + rs.getString("description") + "'></div></div>";
                        }
                        rs.close();
                        output += "<br><div class='row'><div class='col-md-3'><input type='submit'class='btn btn-primary'></div></div></form>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }	
	
	public static String displayAllPOITable(Statement stmt)
        {   
                String sql = "select * from POI";
                String output = "<table class='table table-condensed'><tr><th>PID</th><th>Name</th><th>Address</th><th>City</th><th>State</th><th<Country</th><th>Zipcode</th>" +
                                                "<th>URL</th><th>Phone</th><th>Year Est.</th><th>Hours</th><th>Price</th><th>Category</th><th>Description</th></tr>";
                ResultSet rs = null;
                try 
                {   
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {   
                                                output += "<tr><td>" + rs.getString("pid") + "</td><td>" + rs.getString("name") + "</td><td> "
                                                + rs.getString("street_address") + "</td><td>" + rs.getString("city") + "</td><td>"
                                                + rs.getString("state") + "</td><td>" + rs.getString("country") + "</td><td>"
                                                + rs.getString("zipcode") + "</td><td>" + rs.getString("url") + "</td><td>"
                                                + rs.getString("phone") + "</td><td>" + rs.getString("year_est") + "</td><td>"
                                                + rs.getString("hours") + "</td><td>" + rs.getString("price") + "</td><td>"
                                                + rs.getString("category") + "</td><td>" + rs.getString("description") + "</td></tr>";
                        }   
                        rs.close();
                        output += "</table>";
                }   
                catch (Exception e)
                {   
                        e.printStackTrace();
                }   
                return output;
        }

	public static String displayPOITable(int pid, Statement stmt)
        {
                String sql = "select * from POI where pid = "+pid+";";
                String output = "<div class='container'><table class='table table-condensed'><tr><th>PID</th><th>Name</th><th>Address</th><th>City</th><th>State</th><th>Country</th><th>Zipcode</th>" +
                                                "<th>URL</th><th>Phone</th><th>Year Est.</th><th>Hours</th><th>Price</th><th>Category</th><th>Description</th></tr>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
						output += "<tr><td>" + rs.getString("pid") + "</td><td>" + rs.getString("name") + "</td><td> "
                                                + rs.getString("street_address") + "</td><td>" + rs.getString("city") + "</td><td>"
                                                + rs.getString("state") + "</td><td>" + rs.getString("country") + "</td><td>"
                                                + rs.getString("zipcode") + "</td><td>" + rs.getString("url") + "</td><td>"
                                                + rs.getString("phone") + "</td><td>" + rs.getString("year_est") + "</td><td>"
                                                + rs.getString("hours") + "</td><td>" + rs.getString("price") + "</td><td>"
                                                + rs.getString("category") + "</td><td>" + rs.getString("description") + "</td></tr>";
                        }
                        rs.close();
			output += "</table></div>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String displayPOI(int pid, Statement stmt)
	{
		String sql = "select * from POI where pid = "+pid+";";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "TESTpid: " + rs.getString("pid") + ", name: " + rs.getString("name") + ", street_address: "
						+ rs.getString("street_address") + ", city: " + rs.getString("city") + ", state: "
						+ rs.getString("state") + ", country: " + rs.getString("country") + ", zipcode: "
						+ rs.getString("zipcode") + ", url: " + rs.getString("url") + ", phone: "
						+ rs.getString("phone") + ", year_est: " + rs.getString("year_est") + ", hours: "
						+ rs.getString("hours") + ", price: " + rs.getString("price") + ", category: "
						+ rs.getString("category") + ", description: " + rs.getString("description") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static void addPOI(String name, String address, String city, String state, String country, String zipcode, String url, 
			String phone, int year, String hours, double price, String category, String description, String keywords, Statement stmt)
	{
		String sql1 = "insert into POI (name, street_address, city, state, country, zipcode, url, phone, year_est, hours, price, category, description) "
				+ "values ('"+name+"','"+address+"','"+city+"','"+state+"','"+country+"','"+zipcode+"','"+url+"','"+
				phone+"',"+year+",'"+hours+"',"+price+",'"+category+"','"+description+"');";
		ResultSet rs = null;
		int pid = -1;
		try
		{
			stmt.executeUpdate(sql1, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next())
				pid = rs.getInt(1);
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String[] tokens = keywords.split(",");
		ResultSet rs2 = null;
		for (String el : tokens)
		{
			int wid = -1;
			String sql2 = "select wid from Keywords where word='"+el.trim()+"';";
			try
			{
				rs2 = stmt.executeQuery(sql2);
				if (rs2.next())
					wid = Integer.parseInt(rs2.getString("wid"));
				rs2.close();
				ResultSet rs3 = null;
				if (wid == -1)
				{
					String sql3 = "insert into Keywords (word) values ('"+el.trim()+"');";
					stmt.executeUpdate(sql3, Statement.RETURN_GENERATED_KEYS);
					rs3 = stmt.getGeneratedKeys();
					if (rs3.next())
						wid = rs3.getInt(1);
					rs3.close();
				}
				String sql4 = "insert into HasKeywords values ("+pid+","+wid+");";
				stmt.executeUpdate(sql4);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static String expensivePOIsDisplay(int n, Statement stmt)
        {
                String sql1 = "select distinct category from POI order by category";
                String output = "<table class='table table-condensed'><tr><th>Category</th><th>Avg. Cost</th><th>PID</th><th>Name</th></tr>";
                ResultSet rs1 = null;
                try
                {
                        Connector c = new Connector();
                        rs1 = stmt.executeQuery(sql1);
                        while (rs1.next())
                        {
                                String category = rs1.getString("category");
                                String sql2 = "select sum(cost)/sum(num_people) as avg_cost, P.pid, P.name, P.category from VisEvents E, Visits V, POI P "
                                                + "where E.vid=V.vid and V.pid=P.pid and P.category='"+category+"'group by P.pid order by avg_cost desc limit "+n;
                                ResultSet rs2 = c.stmt.executeQuery(sql2);
                                while (rs2.next())
                                {
                                        output += "<tr><td>" + rs2.getString("category") + "</td><td>" + rs2.getString("avg_cost") + "</td><td>" +
                                                        rs2.getString("pid") + "</td><td>" + rs2.getString("name") + "</td></tr>";
                                }
                                rs2.close();
                        }
                        rs1.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String expensivePOIs(int n, Statement stmt)
	{
		String sql1 = "select distinct category from POI order by category";
		String output = "";
		ResultSet rs1 = null;
		try
		{
			Connector c = new Connector();
			rs1 = stmt.executeQuery(sql1);
			while (rs1.next())
			{
				String category = rs1.getString("category");
				String sql2 = "select sum(cost)/sum(num_people) as avg_cost, P.pid, P.name, P.category from VisEvents E, Visits V, POI P "
						+ "where E.vid=V.vid and V.pid=P.pid and P.category='"+category+"'group by P.pid order by avg_cost desc limit "+n;
				ResultSet rs2 = c.stmt.executeQuery(sql2);
				while (rs2.next())
				{
					output += "Category: " + rs2.getString("category") + ", Average Cost per Head: " + rs2.getString("avg_cost") + ", POI pid: " + 
							rs2.getString("pid") + ", Name: " + rs2.getString("name") + "\n";
				}
				rs2.close();
			}
			rs1.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}

	public static String topRatedPOIsDisplay(int n, Statement stmt)
        {
                String sql1 = "select distinct category from POI order by category";
                String output = "<table class='table table-condensed'><tr><th>Category</th><th>Score</th><th>PID</th><th>Name</th></tr>";
                ResultSet rs1 = null;
                try
                {
                        Connector c = new Connector();
                        rs1 = stmt.executeQuery(sql1);
                        while (rs1.next())
                        {
                                String category = rs1.getString("category");
                                String sql2 = "select avg(F.score) as f_score, P.pid, P.name, P.category from Feedback F, POI P where F.pid=P.pid and P.category='"+category+"' group by P.pid order by f_score desc limit "+n;
                                ResultSet rs2 = c.stmt.executeQuery(sql2);
                                while (rs2.next())
                                {
                                        output += "<tr><td>" + rs2.getString("category") + "</td><td>" + rs2.getString("f_score") + "</td><td>" + rs2.getString("pid") +
                                                        "</td><td>" + rs2.getString("name") + "</td></tr>";
                                }
                                rs2.close();
                        }
                        rs1.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String topRatedPOIs(int n, Statement stmt)
	{
		String sql1 = "select distinct category from POI order by category";
		String output = "";
		ResultSet rs1 = null;
		try
		{
			Connector c = new Connector();
			rs1 = stmt.executeQuery(sql1);
			while (rs1.next())
			{
				String category = rs1.getString("category");
				String sql2 = "select avg(F.score) as f_score, P.pid, P.name, P.category from Feedback F, POI P where F.pid=P.pid and P.category='"+category+"' group by P.pid order by f_score desc limit "+n;
				ResultSet rs2 = c.stmt.executeQuery(sql2);
				while (rs2.next())
				{
					output += "Category: " + rs2.getString("category") + ", Average Rating: " + rs2.getString("f_score") + ", POI pid: " + rs2.getString("pid") + 
							", Name: " + rs2.getString("name") + "\n";
				}
				rs2.close();
			}
			rs1.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}

	public static String popularPOIsDisplay(int n, Statement stmt)
        {
                String sql1 = "select distinct category from POI order by category";
                String output = "<table class='table table-condensed'><tr><th>Category</th><th>Visits</th><th>PID</th><th>Name</th><th>Address</th><th>City</th><th>State</th><th>Country</th><th>Zipcode</th><th>URL</th><th>Phone</th><th>Year est.</th><th>Hours</th><th>Price</th><th>Description</th></tr>";
                ResultSet rs1 = null;
                try
                {
                        Connector c = new Connector();
                        rs1 = stmt.executeQuery(sql1);
                        while (rs1.next())
                        {
                                String category = rs1.getString("category");
                                String sql2 = "select count(V.vid) as num_vis, P.* from Visits V, POI P where V.pid=P.pid and P.category='"+category+"' group by P.pid order by num_vis desc limit "+n+";";
                                ResultSet rs2 = c.stmt.executeQuery(sql2);
                                while (rs2.next())
                                {
                                        output += "<tr><td>" + rs2.getString("category") + "</td><td>" + rs2.getString("num_vis") + 
						"</td><td>" + rs2.getString("pid") + "</td><td>" + rs2.getString("name") + "</td><td>"
                                                + rs2.getString("street_address") + "</td><td>" + rs2.getString("city") + "</td><td>" + rs2.getString("state") + "</td><td>"
                                                + rs2.getString("country") + "</td><td>" + rs2.getString("zipcode") + "</td><td>" + rs2.getString("url")
                                                 + "</td><td>" + rs2.getString("phone") + "</td><td>" + rs2.getString("year_est")
                                                  + "</td><td>" + rs2.getString("hours") + "</td><td>" + rs2.getString("price")
                                                   +  "</td><td>" + rs2.getString("description") + "</td></tr>";
                                }
                                rs2.close();
                        }
                        rs1.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String popularPOIs(int n, Statement stmt)
	{
		String sql1 = "select distinct category from POI";
		String output = "";
		ResultSet rs1 = null;
		try
		{
			Connector c = new Connector();
			rs1 = stmt.executeQuery(sql1);
			while (rs1.next())
			{
				String category = rs1.getString("category");
				String sql2 = "select count(V.vid) as num_vis, P.* from Visits V, POI P where V.pid=P.pid and P.category='"+category+"' group by P.pid order by num_vis desc limit "+n+";";
				ResultSet rs2 = c.stmt.executeQuery(sql2);
				while (rs2.next())
				{
					output += "Category: " + rs2.getString("category") + ", Number of Visits: " + rs2.getString("num_vis") + ", POI pid: " + rs2.getString("pid") + 
							", Name: " + rs2.getString("name") + ", Street Address: " + rs2.getString("street_address") + ", City: " + rs2.getString("city") + 
							", State: " + rs2.getString("state") + ", Country: " + rs2.getString("country") + ", zipcode: " + 
							rs2.getString("zipcode") + ", URL: " + rs2.getString("url") + ", Phone: " + rs2.getString("phone") + 
							", Year Established: " + rs2.getString("year_est")
							  + ", Hours: " + rs2.getString("hours") + ", Price: " + rs2.getString("price")
							  + ", Description: " + rs2.getString("description") + "\n";
				}
				rs2.close();
			}
			rs1.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}
	public static int twoDegreesSep(String login1, String login2, Statement stmt)
	{
		String sql1 = "select * from Favorites F1, Favorites F2 where F1.pid=F2.pid and F1.login='"+login1+"' and F2.login='"+login2+"'";
		ResultSet rs1 = null;
		try
		{
			rs1 = stmt.executeQuery(sql1);
			if (rs1.next())
			{
				return 1;
			}
			rs1.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} // TODO: Optimize Query
		String sql2 = "select * from Favorites F1, Favorites F2, Favorites F3, Favorites F4 "
				+ "where F1.pid=F2.pid and F3.pid=F4.pid and F1.login='"+login1+"' and F4.login='"+login2+"' and F2.login=F3.login;";
		ResultSet rs2 = null;
		try
		{
			rs2 = stmt.executeQuery(sql2);
			if (rs2.next())
			{
				return 2;
			}
			rs2.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	public static String suggestedPOIDisplay(int pid, String login, Statement stmt)
        {
                String sql = "select count(distinct V2.vid) as num_vis, p.* from Visits V1, Visits V2, POI p "
                                + "where V1.pid="+pid+" and V1.login=V2.login and V2.pid<>"+pid+" and V1.login<>'"+login+"' and V2.pid=p.pid "
                                + "group by V2.pid order by num_vis desc limit 5;";
                String output = "<table class='table table-condensed'><tr><th>Visits</th><th>PID</th><th>Name</th><th>Address</th><th>City</th><th>State</th><th>Country</th><th>Phone</th><th>Year est.</th><th>Hours</th><th>Price</th><th>Category</th><th>Description</th></tr>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                output += "<tr><td>" + rs.getString("num_vis") + "</td><td>" + rs.getString("pid") + "</td><td>" + rs.getString("name") + "</td><td>"
                                                + rs.getString("street_address") + "</td><td>" + rs.getString("city") + "</td><td>" + rs.getString("state") + "</td><td>"
                                                + rs.getString("country") + "</td><td>" + rs.getString("zipcode") + "</td><td>" + rs.getString("url")
                                                 + "</td><td>" + rs.getString("phone") + "</td><td>" + rs.getString("year_est")
                                                  + "</td><td>" + rs.getString("hours") + "</td><td>" + rs.getString("price")
                                                   + "</td><td>" + rs.getString("category") + "</td><td>" + rs.getString("description") + "</td></tr>";
                        }
                        rs.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String suggestedPOIs(int pid, String login, Statement stmt)
	{
		String sql = "select count(distinct V2.vid) as num_vis, p.* from Visits V1, Visits V2, POI p "
				+ "where V1.pid="+pid+" and V1.login=V2.login and V2.pid<>"+pid+" and V1.login<>'"+login+"' and V2.pid=p.pid "
				+ "group by V2.pid order by num_vis desc limit 5;";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "Number of Visits: " + rs.getString("num_vis") + ", POI pid: " + rs.getString("pid") + ", Name: " + rs.getString("name") + ", Street Address: "
						+ rs.getString("street_address") + ", City: " + rs.getString("city") + ", State: " + rs.getString("state") + ", Country: "
						+ rs.getString("country") + ", zipcode: " + rs.getString("zipcode") + ", URL: " + rs.getString("url")
						 + ", Phone: " + rs.getString("phone") + ", Year Established: " + rs.getString("year_est")
						  + ", Hours: " + rs.getString("hours") + ", Price: " + rs.getString("price")
						   + ", Category: " + rs.getString("category") + ", Description: " + rs.getString("description") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static String mostUsefulFeedbackPOIDisplay (int pid, int n, Statement stmt)
        {
                String sql = "select avg(rating) as sum_useful, f.* from Feedback f, Rate r where f.pid = "+pid+" and f.fid = r.fid group by f.fid order by sum_useful desc limit "+n+";";
                String output = "<table class='table table-condensed'><tr><th>Usefulness</th><th>FID</th><th>PID</th><th>Login</th><th>Text</th><th>Date</th><th><Score></th></tr>";
                ResultSet rs = null;
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                output += "<tr><td>" + rs.getString("sum_useful") + "</td><td>" + rs.getString("fid") + "</td><td>" + rs.getString("pid") + "</td><td>"
                                                + rs.getString("login") + "</td><td>" + rs.getString("text") + "</td><td>"
                                                + rs.getString("fbdate") + "</td><td>" + rs.getString("score") + "</td></tr>";
                        }
                        rs.close();
			output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static String mostUsefulFeedbackPOI (int pid, int n, Statement stmt)
	{
		String sql = "select sum(rating) as sum_useful, f.* from Feedback f, Rate r where f.pid = "+pid+" and f.fid = r.fid group by f.pid order by sum_useful desc limit "+n+";";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "Average Usefulness: " + rs.getString("sum_useful") + ", Feedback fid: " + rs.getString("fid") + ", POI pid: " + rs.getString("pid") + ", User Login: "
						+ rs.getString("login") + ", Description: " + rs.getString("text") + ", Date: "
						+ rs.getString("fbdate") + ", Score: " + rs.getString("score") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static String customQuery (String sql, List<String> labels, Statement stmt)
	{
		String output = "";
		try
		{
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numCols = rsmd.getColumnCount();
			while (rs.next())
			{
				for (int i = 1; i <= numCols; i++)
					output += labels.get(i - 1) + rs.getString(i);
				output += "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static ResultSet browsePOIsWeb(String where, Statement stmt)
	{
		String sql = "select * from POI " + where + ";";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			return rs;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	public static String browsePOIs (String sort, Statement stmt)
	{
		String sql = "select * from POI " + sort + ";";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "POI pid: " + rs.getString("pid") + ", Name: " + rs.getString("name") + ", Street Address: "
						+ rs.getString("street_address") + ", City: " + rs.getString("city") + ", State: "
						+ rs.getString("state") + ", Country: " + rs.getString("country") + ", Zipcode: "
						+ rs.getString("zipcode") + ", URL: " + rs.getString("url") + ", Phone Number: "
						+ rs.getString("phone") + ", Year Established: " + rs.getString("year_est") + ", Hours: "
						+ rs.getString("hours") + ", Price: " + rs.getString("price") + ", Category: "
						+ rs.getString("category") + ", Description: " + rs.getString("description") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found\n";
		return output;
	}

	public static String browsePOIs (String where, String sort, Statement stmt)
	{
		String sql = "select * from POI " + where + " " + sort + ";";
		String output = "";
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "POI pid: " + rs.getString("pid") + ", Name: " + rs.getString("name") + ", Street Address: "
						+ rs.getString("street_address") + ", State: " + rs.getString("state") + ", Country: "
						+ rs.getString("country") + ", Zipcode: " + rs.getString("zipcode") + ", URL: "
						+ rs.getString("url") + ", Phone Number: " + rs.getString("phone") + ", Year Established: "
						+ rs.getString("year_est") + ", Hours: " + rs.getString("hours") + ", Price: "
						+ rs.getString("price") + ", Category: " + rs.getString("category") + ", Description: "
						+ rs.getString("description") + "\n";
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (output.equals(""))
			output = "No results found";
		return output;
	}

	public static String displayUsers(Statement stmt)
        {
                String sql = "select * from Users";
                ResultSet rs = null;
                String output = "<table class='table table-condensed'><tr><th>Login</th><th>Name</th><th>Trust</th><th>Distrust</th></tr>";
                try
                {
                        rs = stmt.executeQuery(sql);
                        while (rs.next())
                        {
                                output += "<tr><td>"+rs.getString("login")+"</td><td>"+rs.getString("name")+
				"</td><td><a href='trust-users.jsp?login="+rs.getString("login")+"&trust=1'>Trust</a></td><td>"+
				"<a href='trust-users.jsp?login="+rs.getString("login")+"&trust=-1'>Distrust</a></td></tr>";
                        }
                        output += "</table>";
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                return output;
        }

	public static void trustDistrustUser (String login1, String login2, int trust, Statement stmt)
	{
		String sql = "insert into Trusts values ('" + login1 + "','" + login2 + "'," + trust + ");";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			if (e instanceof SQLIntegrityConstraintViolationException)
				 System.err.println("\nError: That User Login does not exist or you've already trusted/distrusted them.\n");
			else
				e.printStackTrace();
		}
	}

	public static String displayFeedback(int pid, Statement stmt)
	{
		String sql = "select * from Feedback where pid="+pid;
		ResultSet rs = null;
		String output = "<table class='table table-condensed'><tr><th>FID</th><th>PID</th><th>Login</th><th>Text</th><th>Date</th><th>Score</th><th>Rate This Feedback</th></tr>";
		try
		{
			rs = stmt.executeQuery(sql);
			while (rs.next())
			{
				output += "<tr><td>"+rs.getString("fid")+"</td><td>"+rs.getString("pid")+"</td><td>"+rs.getString("login")+"</td><td>"+rs.getString("text")+
				"</td><td>"+rs.getString("fbdate")+"</td><td>"+rs.getString("score")+"</td><td><a href='rate-feedback.jsp?fid="+rs.getString("fid")+
				"&pid="+pid+"'>Rate</a></td></tr>";
			}
			output += "</table>";
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return output;
	}

	public static void usefulnessFeedback (String login, int fid, int score, Statement stmt)
	{
		String sql1 = "select login from Feedback where fid="+fid;
		ResultSet rs = null;
		boolean allowed = true;
		try
		{
			rs = stmt.executeQuery(sql1);
			while (rs.next())
			{
					String test = rs.getString("login");
					if (test.equals(login))
						allowed = false;
			}
			rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (allowed)
			{
			String sql = "insert into Rate values ('" + login + "'," + fid + "," + score + ");";
			try
			{
				stmt.executeUpdate(sql);
			}
			catch (Exception e)
			{
				if (e instanceof SQLIntegrityConstraintViolationException)
					 System.err.println("\nError: That Feedback fid does not exists.");
				else
					e.printStackTrace();
			}
		}
		else
		{
			System.out.println("You can't rate the usefulness of your own feedback.");
		}
	}

	public static void recordPOIFeedback (int pid, String login, int score, Statement stmt)
	{
		String sql = "insert into Feedback (pid, login, fbdate, score) values (" + pid + ",'" + login + "',curdate(),"
				+ score + ");";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void recordPOIFeedback (int pid, String login, int score, String description, Statement stmt)
	{
		String sql = "insert into Feedback (pid, login, text, fbdate, score) values (" + pid + ",'" + login + "','"
				+ description + "',curdate()," + score + ");";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void recordFavoritePOI (int pid, String login, Statement stmt)
	{
		String sql = "insert into Favorites values (" + pid + ",'" + login + "',curdate());";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			if (e instanceof SQLIntegrityConstraintViolationException)
				 System.err.println("\nError: You've favorited that POI. You can only do so once per POI.");
			else
				e.printStackTrace();
		}
	}

	public static void recordPOIVisit (double cost, int num_people, String login, int pid, String date, Statement stmt)
	{
		String sql = "insert into VisEvents (cost, num_people) values (" + cost + "," + num_people + ");";
		int vid = -1;
		ResultSet rs = null;
		try
		{
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				vid = rs.getInt(1);
			}
			else
		rs.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
		String sql2 = "insert into Visits values ('" + login + "'," + pid + "," + vid + ",'" + date + "');";
		try
		{
			stmt.executeUpdate(sql2);
		}
		catch (Exception e)
		{
			if (e instanceof SQLIntegrityConstraintViolationException)
				 System.err.println("\nError: The POI pid you specified ("+pid+") does not exist. This visit was not added successfully.");
			else
				e.printStackTrace();
			return;
		}
	}
	
	public static boolean isAdmin (String login, Statement stmt)
	{
		ResultSet result;
		boolean isAdmin = false;
		String sql = "select user_type from Users where login like '"+login + "';";
		try
		{
			result = stmt.executeQuery(sql);
			while (result.next())
			{
				isAdmin = result.getBoolean("user_type");
			}
			result.close();
			return isAdmin;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static boolean authenticate(String login, String password, Statement stmt)
	{
		ResultSet result;
		String sql = "select login from Users where login like'"+login+"' and password like '"+password+"';";
		try
		{
			result = stmt.executeQuery(sql);
			boolean worked = result.next();
			result.close();
			return worked;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
		

	public static User verifyLogin (String login, String password, Statement stmt)
	{
		ResultSet result;
		boolean isAdmin = false;
		User user = new User();
		String sql = "select user_type from Users where login like '" + login + "' and password like '" + password
				+ "';";
		try
		{
			result = stmt.executeQuery(sql);
			while (result.next())
			{
				isAdmin = result.getBoolean("user_type");
				user.query_worked = true;
			}
			result.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new User();
		}
		user.login = login;
		user.isAdmin = isAdmin;
		return user;
	}
	
	public static boolean addUserFull(String login, String password, String name, String address, String state, String country, String zipcode, String phone, Statement stmt)
	{
		String sql = "insert into Users (login,password,name,street_address,state,country,zipcode,phone) values ('"+login+"','"+password+"','"+name+"','"+address+"','"+state+"','"+country+"','"+zipcode+"','"+phone+"');";
                try
                {
                        stmt.executeUpdate(sql);
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                        return false;
                }
                return true;
	}

	public static boolean addUser (String login, String password, Statement stmt)
	{
		String sql = "insert into Users (login, password) values ('" + login + "','" + password + "');";
		try
		{
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean checkUsernameUnique (String login, Statement stmt)
	{
		ResultSet result;
		boolean exists = false;
		String sql = "select login from Users where login like '" + login + "';";
		try
		{
			result = stmt.executeQuery(sql);
			exists = result.next();
			result.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return !exists;
	}

}

