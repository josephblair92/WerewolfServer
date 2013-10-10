package edu.wm.werewolf.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.wm.werewolf.HomeController;

public class PostgresDAO {

	public static Connection establishConnection() {
		// TODO Auto-generated method stub
		 
		try {
 
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e) {
 
			HomeController.logger.info("Failed to load PostgreSQL JDBC Driver");
			e.printStackTrace();
 
		}
		
		Connection connection = null;
		 
		try {
 
			URI dbUri;
			try {
				dbUri = new URI(System.getenv("DATABASE_URL"));
			} catch (URISyntaxException e) {
				HomeController.logger.info("Error retrieving DATABASE_URL");
				e.printStackTrace();
				return null;
			}

		    String username = dbUri.getUserInfo().split(":")[0];
		    String password = dbUri.getUserInfo().split(":")[1];
		    String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

		    connection = DriverManager.getConnection(dbUrl, username, password);
 
		} catch (SQLException e) {
 
			HomeController.logger.info("Connection to database failed");
			e.printStackTrace();
 
		}
 
		if (connection != null) {
			HomeController.logger.info("Database connection successful");
		} else {
			HomeController.logger.info("Connection to database failed");
		}
		
		return connection;
	}
	
	public static ResultSet execQuery(Connection con, String query) {
		try {
			Statement stmt = con.createStatement();
			HomeController.logger.info(query);
			return stmt.executeQuery(query);
		}
		catch (SQLException e) {
			HomeController.logger.info("Query failed: " + e.getMessage());
			return null;
		}

		
	}

}
