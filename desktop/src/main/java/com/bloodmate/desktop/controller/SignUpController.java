package com.bloodmate.desktop.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {
    
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> bloodTypeCombo;
    @FXML private DatePicker dobPicker;
    @FXML private RadioButton genderMale;
    @FXML private RadioButton genderFemale;
    @FXML private RadioButton genderOther;
    @FXML private Button signUpButton;
    @FXML private ToggleGroup genderGroup;
    @FXML private CheckBox termsCheckbox;
    @FXML private Hyperlink loginLink;
    
    @FXML
    private void initialize() {
        // Initialize blood type combo box
        bloodTypeCombo.getItems().addAll(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
        );
        
        // Set up gender toggle group
        genderGroup = new ToggleGroup();
        genderMale.setToggleGroup(genderGroup);
        genderFemale.setToggleGroup(genderGroup);
        genderOther.setToggleGroup(genderGroup);
        
        // Set default selection
        genderMale.setSelected(true);
    }
    
    @FXML
    private void handleSignUp() {
        // Get form values
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String phone = phoneField.getText();
        String bloodType = bloodTypeCombo.getValue();
        String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";
        String gender = getSelectedGender();
        
        // Validation
        if (!validateForm(fullName, email, username, password, confirmPassword, phone, bloodType, dob, gender)) {
            return;
        }
        
        // In a real application, you would save the user to a database here
        // For this demo, we'll just show a success message and redirect to login
        
        showAlert("Success", "Account created successfully! Please log in with your credentials.");
        showLoginForm();
    }
    
    private boolean validateForm(String fullName, String email, String username, 
                               String password, String confirmPassword, String phone, 
                               String bloodType, String dob, String gender) {
        // Basic validation
        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || 
            password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty() || 
            bloodType == null || dob.isEmpty() || gender.isEmpty()) {
            showAlert("Validation Error", "Please fill in all required fields.");
            return false;
        }
        
        // Email validation
        if (!email.contains("@") || !email.contains(".")) {
            showAlert("Validation Error", "Please enter a valid email address.");
            return false;
        }
        
        // Password confirmation
        if (!password.equals(confirmPassword)) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }
        
        // Password strength (basic check)
        if (password.length() < 6) {
            showAlert("Validation Error", "Password must be at least 6 characters long.");
            return false;
        }
        
        // Terms agreement
        if (!termsCheckbox.isSelected()) {
            showAlert("Validation Error", "You must agree to the Terms of Service and Privacy Policy.");
            return false;
        }
        
        return true;
    }
    
    private String getSelectedGender() {
        if (genderMale.isSelected()) return "Male";
        if (genderFemale.isSelected()) return "Female";
        if (genderOther.isSelected()) return "Other";
        return "";
    }
    
    @FXML
    private void handleLoginLink() {
        showLoginForm();
    }
    
    private void showLoginForm() {
        try {
            // Close sign up window
            Stage signUpStage = (Stage) signUpButton.getScene().getWindow();
            signUpStage.close();
            
            // Open login window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/LoginView.fxml"));
            Parent root = loader.load();
            
            Stage loginStage = new Stage();
            Scene scene = new Scene(root, 500, 700);
            
            // Load CSS stylesheet
            String css = getClass().getResource("/styles/main.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            loginStage.setTitle("🩸 BloodMate - Login");
            loginStage.setMinWidth(500);
            loginStage.setMinHeight(700);
            loginStage.setScene(scene);
            loginStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load login form: " + e.getMessage());
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