package com.bloodmate.desktop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Configuration utility for loading application properties.
 * Handles loading of application.properties and database.properties files.
 */
public class ConfigurationManager {
    
    private static final Logger logger = Logger.getLogger(ConfigurationManager.class.getName());
    
    private static ConfigurationManager instance;
    private Properties appProperties;
    private Properties dbProperties;
    
    private ConfigurationManager() {
        loadProperties();
    }
    
    /**
     * Gets the singleton instance of ConfigurationManager.
     * 
     * @return the configuration manager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Loads application and database properties from resource files.
     */
    private void loadProperties() {
        // Load application properties
        appProperties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                appProperties.load(input);
                logger.info("Application properties loaded successfully");
            } else {
                logger.warning("application.properties file not found, using defaults");
            }
        } catch (IOException e) {
            logger.severe("Error loading application properties: " + e.getMessage());
        }
        
        // Load database properties
        dbProperties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("database.properties")) {
            if (input != null) {
                dbProperties.load(input);
                logger.info("Database properties loaded successfully");
            } else {
                logger.warning("database.properties file not found, using defaults");
                loadDefaultDatabaseProperties();
            }
        } catch (IOException e) {
            logger.severe("Error loading database properties: " + e.getMessage());
            loadDefaultDatabaseProperties();
        }
    }
    
    /**
     * Loads default database properties if the properties file is not found.
     */
    private void loadDefaultDatabaseProperties() {
        dbProperties.setProperty("db.url", "jdbc:mysql://localhost:3306/bloodmate");
        dbProperties.setProperty("db.username", "root");
        dbProperties.setProperty("db.password", "");
        dbProperties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        logger.info("Default database properties loaded");
    }
    
    /**
     * Gets an application property value.
     * 
     * @param key the property key
     * @return the property value, or null if not found
     */
    public String getAppProperty(String key) {
        return appProperties.getProperty(key);
    }
    
    /**
     * Gets an application property value with a default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the property value or default value
     */
    public String getAppProperty(String key, String defaultValue) {
        return appProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets a database property value.
     * 
     * @param key the property key
     * @return the property value, or null if not found
     */
    public String getDbProperty(String key) {
        return dbProperties.getProperty(key);
    }
    
    /**
     * Gets a database property value with a default fallback.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the property value or default value
     */
    public String getDbProperty(String key, String defaultValue) {
        return dbProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets a boolean application property.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the boolean property value
     */
    public boolean getAppBooleanProperty(String key, boolean defaultValue) {
        String value = getAppProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    /**
     * Gets an integer application property.
     * 
     * @param key the property key
     * @param defaultValue the default value if property is not found
     * @return the integer property value
     */
    public int getAppIntProperty(String key, int defaultValue) {
        String value = getAppProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            logger.warning("Invalid integer property value for " + key + ": " + value);
            return defaultValue;
        }
    }
    
    /**
     * Checks if a feature is enabled.
     * 
     * @param featureName the feature name (e.g., "ai", "iot", "blockchain")
     * @return true if the feature is enabled, false otherwise
     */
    public boolean isFeatureEnabled(String featureName) {
        return getAppBooleanProperty("features." + featureName + ".enabled", false);
    }
    
    /**
     * Gets the database connection URL.
     * 
     * @return the database URL
     */
    public String getDatabaseUrl() {
        return getDbProperty("db.url", "jdbc:mysql://localhost:3306/bloodmate");
    }
    
    /**
     * Gets the database username.
     * 
     * @return the database username
     */
    public String getDatabaseUsername() {
        return getDbProperty("db.username", "root");
    }
    
    /**
     * Gets the database password.
     * 
     * @return the database password
     */
    public String getDatabasePassword() {
        return getDbProperty("db.password", "");
    }
    
    /**
     * Gets the database driver class name.
     * 
     * @return the database driver class name
     */
    public String getDatabaseDriver() {
        return getDbProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }
    
    /**
     * Gets the application name.
     * 
     * @return the application name
     */
    public String getApplicationName() {
        return getAppProperty("app.name", "BloodMate");
    }
    
    /**
     * Gets the application version.
     * 
     * @return the application version
     */
    public String getApplicationVersion() {
        return getAppProperty("app.version", "1.0.0");
    }
    
    /**
     * Gets the default theme.
     * 
     * @return the default theme name
     */
    public String getDefaultTheme() {
        return getAppProperty("ui.theme.default", "light");
    }
    
    /**
     * Checks if hardware acceleration is enabled.
     * 
     * @return true if hardware acceleration is enabled
     */
    public boolean isHardwareAccelerationEnabled() {
        return getAppBooleanProperty("ui.hardware.acceleration", true);
    }
    
    /**
     * Gets the default window width.
     * 
     * @return the window width in pixels
     */
    public int getWindowWidth() {
        return getAppIntProperty("ui.window.width", 1280);
    }
    
    /**
     * Gets the default window height.
     * 
     * @return the window height in pixels
     */
    public int getWindowHeight() {
        return getAppIntProperty("ui.window.height", 800);
    }
    
    /**
     * Reloads all properties from files.
     */
    public void reload() {
        loadProperties();
        logger.info("Configuration reloaded");
    }
}