/*
 * Authors: Nathan Wilkinson and Fei Fei Li
 * CS 5530
 * March 2016
 * Connector.java
 */

package cs5530;

import java.sql.*;

public class Connector
{
	public Connection con;
	public Statement stmt;

	public Connector() throws Exception
	{
		try
		{
			String userName = "cs5530u83";
			String password = "skakrnfq";
			String url = "jdbc:mysql://georgia.eng.utah.edu/cs5530db83";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection(url, userName, password);
			stmt = con.createStatement();
		}
		catch (Exception e)
		{
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
			System.err.println(e.getMessage());
			throw (e);
		}
	}

	public void closeConnection () throws Exception
	{
		con.close();
	}
}

