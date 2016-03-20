package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.common.base.Strings;
import com.moniquetrevisan.urlshortening.jsonobject.Stat;

public class StatisticsPersistence {

	private static Logger LOGGER = Logger.getLogger(StatisticsPersistence.class.getName());

	private Connection con = null;

	public StatisticsPersistence() {
	
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

	public JSONObject getTotalOfHitsAndUrlCount(JSONObject jsonResponse, String userId) {
		StringBuffer queryBuffer = new StringBuffer(); 
		queryBuffer.append(" select sum(hits) as total_hits, ");
		queryBuffer.append(" count(*) as urlCount ");
		queryBuffer.append(" from url ");
		if(!Strings.isNullOrEmpty(userId)){
			queryBuffer.append(" where user_id_pk = ? ");
		}
		
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(queryBuffer.toString());
			if(!Strings.isNullOrEmpty(userId)){
				statement.setString(1, userId);
			}
			ResultSet rs = statement.executeQuery();
			if(null != rs && rs.next()){
				jsonResponse.accumulate("hits", rs.getInt("total_hits"));
				jsonResponse.accumulate("urlCount", rs.getInt("urlCount"));
			}
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}  finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return jsonResponse;
	}
	
	public JSONObject getTopUrls(JSONObject jsonResponse, String userId) {
		List<Stat> stats = new ArrayList<Stat>();
		StringBuffer queryBuffer = new StringBuffer(); 
		queryBuffer.append(" select * "); 
		queryBuffer.append(" from url ");
		if(!Strings.isNullOrEmpty(userId)){
			queryBuffer.append(" where user_id_pk = ? ");
		}
		queryBuffer.append(" order by hits desc ");
		queryBuffer.append(" limit 10 ");
		
		try {
			createConnection();
			PreparedStatement statement = con.prepareStatement(queryBuffer.toString());
			if(!Strings.isNullOrEmpty(userId)){
				statement.setString(1, userId);
			}
			ResultSet rs = statement.executeQuery();
			if(null != rs){
				while(rs.next()){
					Stat stat = new Stat();
					stat.setId(rs.getInt("url_id"));
					stat.setHits(rs.getInt("hits"));
					stat.setUrl(rs.getString("url"));
					stat.setShortUrl(rs.getString("short_url"));
					stats.add(stat);
				}
				jsonResponse.accumulate("topUrls", stats);
			}
		} catch (SQLException | JSONException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}  finally {
			try {
				con.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return jsonResponse;
	}

}