package com.bloodmate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMeCheckbox;
    @FXML private Button loginButton;
    @FXML private javafx.scene.image.ImageView bloodDropIconLogin;
    @FXML private Hyperlink signUpLink;
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        // Simple validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Login Error", "Please enter both username and password.");
            return;
        }
        
        // In a real application, you would authenticate against a database or service
        // For demonstration purposes, we'll accept any non-empty credentials
        if (authenticateUser(username, password)) {
            // Login successful - show main application
            showMainApplication();
        } else {
            showAlert("Login Failed", "Invalid username or password. Please try again.");
            // Clear password field
            passwordField.clear();
        }
    }
    
    private boolean authenticateUser(String username, String password) {
        // In a real application, this would check credentials against a database
        // For demo purposes, we'll accept:
        // - Username: admin, Password: admin
        // - Username: user, Password: password
        // Or any non-empty credentials (for testing)
        
        if ("admin".equals(username) && "admin".equals(password)) {
            return true;
        } else if ("user".equals(username) && "password".equals(password)) {
            return true;
        } else {
            // For demo purposes, accept any non-empty credentials
            return !username.isEmpty() && !password.isEmpty();
        }
    }
    
    private void showMainApplication() {
        try {
            // Close login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
            
            // Open main application
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/MainView.fxml"));
            Parent root = loader.load();
            
            Stage mainStage = new Stage();
            Scene scene = new Scene(root, 1200, 800);
            
            // Load CSS stylesheet
            String css = getClass().getResource("/styles/main.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            mainStage.setTitle("🩸 BloodMate - Blood Bank Management System");
            mainStage.setMinWidth(1000);
            mainStage.setMinHeight(700);
            mainStage.setScene(scene);
            mainStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load main application: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleSignUpLink() {
        try {
            // Close login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();
            
            // Open sign up window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/SignUpView.fxml"));
            Parent root = loader.load();
            
            Stage signUpStage = new Stage();
            Scene scene = new Scene(root, 500, 800);
            
            // Load CSS stylesheet
            String css = getClass().getResource("/styles/main.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            signUpStage.setTitle("🩸 BloodMate - Sign Up");
            signUpStage.setMinWidth(500);
            signUpStage.setMinHeight(800);
            signUpStage.setScene(scene);
            signUpStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load sign up form: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}