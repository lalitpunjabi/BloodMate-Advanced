package com.bloodmate.desktop.controller;

import com.bloodmate.desktop.db.Db;
import com.bloodmate.desktop.model.*;
import com.bloodmate.desktop.repo.*;
import com.bloodmate.desktop.report.*;
import com.bloodmate.desktop.service.*;
import com.bloodmate.desktop.util.SpeechRecognitionUtil;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.Random;

public class MainController implements Initializable {
    
    // Fields for UI components
    @FXML private Button dashboardBtn;
    @FXML private Button donorsBtn;
    @FXML private Button recipientsBtn;
    @FXML private Button inventoryBtn;
    @FXML private Button campaignsBtn;
    @FXML private Button reportsBtn;
    @FXML private Button settingsBtn;
    @FXML private Button themeToggleBtn;
    @FXML private Button logoBtn;
    @FXML private Button rewardsBtn;
    @FXML private Button statisticsBtn;
    @FXML private Button emergencyBtn;
    @FXML private Button voiceCommandsBtn;
    @FXML private Button logoutBtn;
    
    // Dashboard UI components
    @FXML private Label totalDonorsLabel;
    @FXML private Label bloodUnitsLabel;
    @FXML private Label pendingRequestsLabel;
    @FXML private Label emergencyAlertsLabel;
    @FXML private ProgressBar opositiveProgress;
    @FXML private ProgressBar apositiveProgress;
    @FXML private ProgressBar bpositiveProgress;
    @FXML private ProgressBar onegativeProgress;
    @FXML private ProgressBar anegativeProgress;
    @FXML private ProgressBar abpositiveProgress;
    @FXML private ProgressBar bnegativeProgress;
    @FXML private ProgressBar abnegativeProgress;
    @FXML private Label currentDateTime;
    
    // Services and repositories
    private DonorService donorService;
    private RecipientService recipientService;
    private BloodInventoryService inventoryService;
    private CampaignService campaignService;
    private ReportService reportService;
    private VoiceCommandService voiceCommandService;
    private SpeechRecognitionUtil speechRecognitionUtil;
    private EmergencyRequestService emergencyRequestService;
    
    // State variables
    private String currentView = "dashboard";
    private boolean isDarkTheme = false;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Get database connection
            java.sql.Connection connection = com.bloodmate.desktop.util.DatabaseManager.getConnection();
            
            // Initialize DAOs
            DonorDao donorDao = new DonorDao(connection);
            RecipientDao recipientDao = new RecipientDao(connection);
            BloodInventoryDao inventoryDao = new BloodInventoryDao(connection);
            CampaignDao campaignDao = new CampaignDao(connection);
            BloodStatisticsDao statisticsDao = new BloodStatisticsDao(connection);
            
            // Initialize services
            donorService = new DonorService();
            recipientService = new RecipientService();
            inventoryService = new BloodInventoryService();
            campaignService = new CampaignService();
            reportService = new ReportService(donorDao, recipientDao, inventoryDao, campaignDao, statisticsDao);
            voiceCommandService = new VoiceCommandService();
            speechRecognitionUtil = new SpeechRecognitionUtil(voiceCommandService);
            emergencyRequestService = new EmergencyRequestService();
            
            // Set up initial view
            showDashboard();
            updateDashboardData();
            
            System.out.println("MainController initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to initialize application: " + e.getMessage());
        }
    }
    
    // Navigation methods
    @FXML
    private void showDashboard() {
        hideAllViews();
        currentView = "dashboard";
        updateActiveButton(dashboardBtn);
        updateDashboardData();
        System.out.println("Dashboard view shown");
    }
    
    @FXML
    private void showDonors() {
        hideAllViews();
        currentView = "donors";
        updateActiveButton(donorsBtn);
        System.out.println("Donors view shown");
    }
    
    @FXML
    private void showRecipients() {
        hideAllViews();
        currentView = "recipients";
        updateActiveButton(recipientsBtn);
        System.out.println("Recipients view shown");
    }
    
    @FXML
    private void showInventory() {
        hideAllViews();
        currentView = "inventory";
        updateActiveButton(inventoryBtn);
        System.out.println("Inventory view shown");
    }
    
    @FXML
    private void showCampaigns() {
        hideAllViews();
        currentView = "campaigns";
        updateActiveButton(campaignsBtn);
        System.out.println("Campaigns view shown");
    }
    
    @FXML
    private void showReports() {
        hideAllViews();
        currentView = "reports";
        updateActiveButton(reportsBtn);
        System.out.println("Reports view shown");
    }
    
    @FXML
    private void showSettings() {
        hideAllViews();
        currentView = "settings";
        updateActiveButton(settingsBtn);
        System.out.println("Settings view shown");
    }
    
    @FXML
    private void showRewards() {
        hideAllViews();
        currentView = "rewards";
        updateActiveButton(rewardsBtn);
        System.out.println("Rewards view shown");
        loadRewardsData();
    }
    
    @FXML
    private void showStatistics() {
        hideAllViews();
        currentView = "statistics";
        updateActiveButton(statisticsBtn);
        System.out.println("Statistics view shown");
        loadStatisticsData();
    }
    
    @FXML
    private void showEmergency() {
        hideAllViews();
        currentView = "emergency";
        updateActiveButton(emergencyBtn);
        System.out.println("Emergency view shown");
        loadEmergencyData();
    }
    
    // Voice Commands method
    @FXML
    private void showVoiceCommands() {
        hideAllViews();
        currentView = "voice-commands";
        updateActiveButton(voiceCommandsBtn);
        System.out.println("Voice Commands view shown");
        loadVoiceCommandsData();
    }
    
    // Logout method
    @FXML
    private void logout() {
        System.out.println("Logout requested");
        showAlert("Logout", "You have been logged out successfully.");
    }
    
    // Refresh dashboard method
    @FXML
    private void refreshDashboard() {
        updateDashboardData();
        showAlert("Refresh", "Dashboard data refreshed successfully.");
    }
    
    private void updateDashboardData() {
        // Update current date/time
        if (currentDateTime != null) {
            currentDateTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
        }
        
        // Update statistics (simulated data)
        if (totalDonorsLabel != null) {
            totalDonorsLabel.setText("1,247");
        }
        if (bloodUnitsLabel != null) {
            bloodUnitsLabel.setText("3,456");
        }
        if (pendingRequestsLabel != null) {
            pendingRequestsLabel.setText("23");
        }
        if (emergencyAlertsLabel != null) {
            emergencyAlertsLabel.setText("2");
        }
        
        // Update progress bars (simulated data)
        if (opositiveProgress != null) opositiveProgress.setProgress(0.38);
        if (apositiveProgress != null) apositiveProgress.setProgress(0.34);
        if (bpositiveProgress != null) bpositiveProgress.setProgress(0.09);
        if (onegativeProgress != null) onegativeProgress.setProgress(0.07);
        if (anegativeProgress != null) anegativeProgress.setProgress(0.06);
        if (abpositiveProgress != null) abpositiveProgress.setProgress(0.04);
        if (bnegativeProgress != null) bnegativeProgress.setProgress(0.02);
        if (abnegativeProgress != null) abnegativeProgress.setProgress(0.01);
    }
    
    private void loadRewardsData() {
        Platform.runLater(() -> {
            showAlert("Rewards & Leaderboard", "Rewards and leaderboard data would be displayed here.\n\nThis section shows donor achievements, points, and community rankings.");
        });
    }
    
    private void loadStatisticsData() {
        Platform.runLater(() -> {
            showAlert("Statistics & Reports", "Statistics and reports data would be displayed here.\n\nThis section shows comprehensive analytics, charts, and exportable reports.");
        });
    }
    
    private void loadEmergencyData() {
        Platform.runLater(() -> {
            showAlert("Emergency Response", "Emergency response data would be displayed here.\n\nThis section shows critical blood requests and emergency protocols.");
        });
    }
    
    private void loadVoiceCommandsData() {
        Platform.runLater(() -> {
            // Start voice recognition when the voice commands view is shown
            speechRecognitionUtil.startListening();
            
            showAlert("Voice Commands", "Voice command interface activated! 🎤\n\nTry saying commands like:\n- Show dashboard\n- Find donor with blood type O+\n- Check emergency requests\n- Display inventory statistics\n\nIn a real implementation, this would connect to a speech recognition service.");
        });
    }
    
    // Simulate voice command execution
    @FXML
    private void simulateVoiceCommand() {
        // Simulate recognizing a voice command
        speechRecognitionUtil.recognizeSpeech(new byte[0]).thenAccept(recognizedText -> {
            String action = speechRecognitionUtil.processCommand(recognizedText, "user-123");
            speechRecognitionUtil.executeAction(action);
            
            Platform.runLater(() -> {
                showAlert("Voice Command Recognized", 
                    "Recognized: \"" + recognizedText + "\"\n" +
                    "Action: " + action + "\n\n" +
                    "In a real implementation, this would navigate to the appropriate view.");
            });
        });
    }
    
    // Helper methods
    private void hideAllViews() {
        System.out.println("Hiding all views");
    }
    
    private void updateActiveButton(Button button) {
        // Reset all buttons
        List<Button> buttons = Arrays.asList(
            dashboardBtn, donorsBtn, recipientsBtn, 
            inventoryBtn, campaignsBtn, reportsBtn, settingsBtn,
            rewardsBtn, statisticsBtn, emergencyBtn, voiceCommandsBtn
        );
        
        for (Button btn : buttons) {
            if (btn != null) {
                btn.getStyleClass().remove("active");
            }
        }
        
        // Set active button
        if (button != null) {
            button.getStyleClass().add("active");
        }
    }
    
    // Utility methods
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    
    private void showStatusMessage(String message, String type) {
        System.out.println("Status: " + message + " (Type: " + type + ")");
    }
    
    // Theme toggle
    @FXML
    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        
        if (isDarkTheme) {
            if (themeToggleBtn != null) {
                themeToggleBtn.setText("☀️ Light Mode");
            }
            showAlert("Theme Changed", "Dark mode activated! 🌙");
        } else {
            if (themeToggleBtn != null) {
                themeToggleBtn.setText("🌙 Dark Mode");
            }
            showAlert("Theme Changed", "Light mode activated! ☀️");
        }
    }
}