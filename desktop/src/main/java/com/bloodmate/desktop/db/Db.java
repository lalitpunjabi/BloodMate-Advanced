package com.bloodmate.desktop.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Db {
	private static Connection connection;

	private Db() {}

	public static synchronized Connection get() {
		if (connection == null) connection = create();
		return connection;
	}

	private static Connection create() {
		try (InputStream in = Db.class.getResourceAsStream("/db.properties")) {
			Properties p = new Properties();
			if (in != null) p.load(in);
			String url = p.getProperty("db.url", "jdbc:mysql://localhost:3306/bloodmate");
			String user = p.getProperty("db.user", "root");
			String pass = p.getProperty("db.password", "");
			return DriverManager.getConnection(url, user, pass);
		} catch (IOException | SQLException e) {
			throw new RuntimeException("Failed to connect to DB: " + e.getMessage(), e);
		}
	}
}
