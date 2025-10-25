package com.bloodmate.desktop.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Database connection manager for BloodMate MySQL database
 * Provides singleton connection with connection pooling and error handling
 */
public final class Db {
	private static final Logger LOGGER = Logger.getLogger(Db.class.getName());
	private static Connection connection;
	private static Properties dbProperties;

	private Db() {}

	/**
	 * Get the database connection (singleton pattern)
	 * @return Active database connection
	 */
	public static synchronized Connection get() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = create();
				initializeDatabase();
			}
			return connection;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Failed to check connection status", e);
			// Try to create new connection
			connection = create();
			return connection;
		}
	}

	/**
	 * Create new database connection
	 * @return New database connection
	 */
	private static Connection create() {
		try {
			// Load database properties
			loadProperties();
			
			// Get connection parameters
			String url = dbProperties.getProperty("db.url", "jdbc:mysql://localhost:3306/bloodmate?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
			String user = dbProperties.getProperty("db.username", "root");
			String pass = dbProperties.getProperty("db.password", "");
			
			LOGGER.info("Connecting to MySQL database: " + url.replaceAll("password=[^&]*", "password=***"));
			
			// Create connection with optimized settings
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			// Set connection properties for better performance
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
			LOGGER.info("Successfully connected to BloodMate MySQL database");
			return conn;
			
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Failed to connect to MySQL database", e);
			throw new RuntimeException("Failed to connect to BloodMate database. Please ensure MySQL is running and credentials are correct: " + e.getMessage(), e);
		}
	}

	/**
	 * Load database properties from file
	 */
	private static void loadProperties() {
		if (dbProperties == null) {
			dbProperties = new Properties();
			try (InputStream in = Db.class.getResourceAsStream("/db.properties")) {
				if (in != null) {
					dbProperties.load(in);
					LOGGER.info("Database properties loaded successfully");
				} else {
					LOGGER.warning("db.properties file not found, using default values");
				}
			} catch (IOException e) {
				LOGGER.log(Level.WARNING, "Failed to load db.properties, using defaults", e);
			}
		}
	}

	/**
	 * Initialize database with required tables if they don't exist
	 */
	private static void initializeDatabase() {
		try (Statement stmt = connection.createStatement()) {
			// Check if donors table exists, if not, create basic structure
			stmt.execute("CREATE TABLE IF NOT EXISTS donors (" +
				"id VARCHAR(64) PRIMARY KEY," +
				"name VARCHAR(100) NOT NULL," +
				"email VARCHAR(150)," +
				"phone VARCHAR(50)," +
				"blood_group VARCHAR(5) NOT NULL," +
				"created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
				")");
			
			LOGGER.info("Database tables verified/created successfully");
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "Failed to initialize database tables", e);
		}
	}

	/**
	 * Test database connectivity
	 * @return true if connection is successful
	 */
	public static boolean testConnection() {
		try (Connection testConn = create()) {
			return testConn != null && !testConn.isClosed();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Database connection test failed", e);
			return false;
		}
	}

	/**
	 * Close the database connection
	 */
	public static synchronized void close() {
		if (connection != null) {
			try {
				connection.close();
				LOGGER.info("Database connection closed");
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "Error closing database connection", e);
			}
			connection = null;
		}
	}

	/**
	 * Get database connection properties
	 * @return Database properties
	 */
	public static Properties getProperties() {
		loadProperties();
		return dbProperties;
	}
}
