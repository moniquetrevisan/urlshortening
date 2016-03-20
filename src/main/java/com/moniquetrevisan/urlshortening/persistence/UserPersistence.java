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

	public UserPersistence() {
		// nothing..
	}

	private void createConnection() {
		String url = "jdbc:hsqldb:hsql://localhost/urlshortening";
		String user = "SA";
		String password = "";
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public boolean createUser(User user) {
		String query = "insert into user (user_id) values(?)";
		try {
			createConnection();
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, user.getId());
			preparedStatement.execute();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	public boolean deleteUser(User user) {
		String deleteFromUrl = "delete from url where user_id_fk = ? ";
		String deleteFromUser = "delete from user where user_id = ? ";
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(deleteFromUrl);
			statement.setString(1, user.getId());
			statement.execute();
			
			statement = con.prepareStatement(deleteFromUser);
			statement.setString(1, user.getId());
			statement.execute();
			return true;
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
}