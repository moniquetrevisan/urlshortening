package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

public class StatisticsPersistence {
	
	private static Logger LOGGER = Logger.getLogger(StatisticsPersistence.class.getName());

	private Connection con = null;

	public StatisticsPersistence() throws Exception {
		createConnection();
	}

	private void createConnection() throws Exception {
		String url = "jdbc:hsqldb:hsql://localhost/urlshortening";
		String user = "SA";
		String password = "";
		Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
		con = DriverManager.getConnection(url, user, password);
	}

	/*public int getTotalOfHits() {
		
		try {
			String query = "select count(*) ";
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
	}*/

}
