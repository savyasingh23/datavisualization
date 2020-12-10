package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static final String url = "jdbc:mysql://localhost/datavisualization?autoReconnect=true&useSSL=false";
	private static final String user = "root";
	private static final String pass = "sanyasavya";

	public static Connection connect() throws SQLException {
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		}catch(ClassNotFoundException | InstantiationException cnfe){
			System.err.println("Error: "+cnfe.getMessage());
		} catch(IllegalAccessException iae){
			System.err.println("Error: "+iae.getMessage());
		}

		Connection conn = DriverManager.getConnection(url, user, pass);
		return conn;
	}

}
