package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.User;

public class UserPersistence {
	
	private static Logger LOGGER = Logger.getLogger(UserPersistence.class.getName());

	private Connection con = null;

	public UserPersistence() throws Exception {
		createConnection();
	}

	private void createConnection() throws Exception {
		String url = "jdbc:hsqldb:hsql://localhost/urlshortening";
		String user = "SA";
		String password = "";
		Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
		con = DriverManager.getConnection(url, user, password);
	}

	public boolean createUser(User user) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert into user (user_id) values(?)");
			preparedStatement.setString(1, user.getId());
			preparedStatement.execute();
			return true;
		} 
		catch (SQLException e) {
			return false; 
		} 
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
}