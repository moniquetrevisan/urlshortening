package com.moniquetrevisan.urlshortening.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.moniquetrevisan.urlshortening.jsonobject.User;

public class UserPersistence {

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

	public boolean createUser(User user) throws Exception {
		PreparedStatement preparedStatement = con.prepareStatement("insert into user (user_id) values(?)");
		preparedStatement.setString(1, user.getId());
		return preparedStatement.execute();
	}

}