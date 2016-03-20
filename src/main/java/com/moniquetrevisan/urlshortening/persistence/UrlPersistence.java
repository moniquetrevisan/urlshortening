package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.moniquetrevisan.urlshortening.jsonobject.Stat;

public class UrlPersistence {

	private static Logger LOGGER = Logger.getLogger(UrlPersistence.class.getName());

	private Connection con = null;

	public UrlPersistence() {
		
	}

	private void createConnection() {
		String url = "jdbc:hsqldb:hsql://localhost/urlshortening";
		String user = "SA";
		String password = "";
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
			con = DriverManager.getConnection(url, user, password);
		} 
		catch (InstantiationException 
				| IllegalAccessException 
				| ClassNotFoundException 
				| SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} 
	}
	
	public boolean incrementHits(Stat stat){
		String query = " update url "
				 + " set hits = ? "
				 + " where url_id = ?";
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, stat.getHits() + 1);
			statement.setInt(2, stat.getId());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
			}
		}
		return true;
	}
	
	public Stat getOriginalUrl(int urlId){
		String query = " select url_id, user_id_fk, hits, url, short_url "
					 + " from url "
					 + " where url_id = ?";
		Stat stat = null;
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(query);
			statement.setInt(1, urlId);
			
			ResultSet rs = statement.executeQuery();
			if(null != rs && rs.next()){
				stat = new Stat();
				stat.setId(urlId);
				stat.setHits(rs.getInt("hits"));
				stat.setUrl(rs.getString("url"));
				stat.setShortUrl(rs.getString("short_url"));
			} else{
				return null;
			}
			return stat;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return null;
	}

	public int createUrl(Stat stat, String userId) {
		int generatedKey = -1;
		String query = "insert into url (user_id_fk, hits, url, short_url ) values(?, ?, ?, ?)";
		try {
			createConnection();
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
	
	public boolean deleteUrl(int urlId) {
		String deleteFromUrl = "delete from url where url_id = ? ";
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(deleteFromUrl);
			statement.setInt(1, urlId);
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