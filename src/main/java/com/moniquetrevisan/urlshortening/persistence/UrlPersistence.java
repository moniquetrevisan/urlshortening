package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;

public class UrlPersistence {

	private static Logger LOGGER = Logger.getLogger(UrlPersistence.class.getName());

	private Connection con = null;

	public UrlPersistence() throws Exception {
		createConnection();
	}

	private void createConnection() throws Exception {
		String url = "jdbc:hsqldb:hsql://localhost/urlshortening";
		String user = "SA";
		String password = "";
		Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
		con = DriverManager.getConnection(url, user, password);
	}

	public int createUrl(Stat stat, String userId) {
		int generatedKey = -1;
		try {
			String query = "insert into url (user_id_fk, hits, url, short_url ) values(?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, userId);
			statement.setInt(2, stat.getHits());
			statement.setString(3, stat.getUrl());
			statement.setString(4, stat.getShortUrl());
			
			generatedKey = statement.executeUpdate();
			return generatedKey;
		} 
		catch (SQLException e) {
			return generatedKey;
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