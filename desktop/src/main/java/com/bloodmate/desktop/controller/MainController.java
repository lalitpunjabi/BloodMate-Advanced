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
    
    // Donors view UI components
    @FXML private TextField donorSearchField;
    @FXML private ComboBox<String> bloodTypeFilter;
    @FXML private ComboBox<String> donorStatusFilter;
    @FXML private ComboBox<String> locationFilter;
    @FXML private Label totalDonorsCount;
    @FXML private Label activeDonorsCount;
    @FXML private Label newDonorsCount;
    @FXML private Label eligibleDonorsCount;
    @FXML private TableView<Donor> donorsTable;
    @FXML private TableColumn<Donor, String> donorIdColumn;
    @FXML private TableColumn<Donor, String> donorNameColumn;
    @FXML private TableColumn<Donor, String> donorBloodTypeColumn;
    @FXML private TableColumn<Donor, Integer> donorAgeColumn;
    @FXML private TableColumn<Donor, String> donorPhoneColumn;
    @FXML private TableColumn<Donor, String> donorEmailColumn;
    @FXML private TableColumn<Donor, String> donorLocationColumn;
    @FXML private TableColumn<Donor, LocalDate> donorLastDonationColumn;
    @FXML private TableColumn<Donor, String> donorStatusColumn;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ComboBox<String> bloodTypeField;
    @FXML private DatePicker dobField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private ComboBox<String> genderField;
    @FXML private TextField locationField;
    @FXML private TextField addressField;
    @FXML private DatePicker lastDonationField;
    @FXML private ComboBox<String> frequencyField;
    
    // View components
    @FXML private ScrollPane dashboardView;
    @FXML private ScrollPane donorsView;
    @FXML private ScrollPane recipientsView;
    @FXML private ScrollPane inventoryView;
    @FXML private ScrollPane campaignsView;
    @FXML private ScrollPane rewardsView;
    @FXML private ScrollPane statisticsView;
    @FXML private ScrollPane emergencyView;
    @FXML private ScrollPane voiceCommandsView;
    
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
    private ObservableList<Donor> donorList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Initialize services
            donorService = new DonorService();
            recipientService = new RecipientService();
            inventoryService = new BloodInventoryService();
            campaignService = new CampaignService();
            // Initialize other services as needed
            
            // Initialize voice command service
            voiceCommandService = new VoiceCommandService();
            speechRecognitionUtil = new SpeechRecognitionUtil(voiceCommandService);
            emergencyRequestService = new EmergencyRequestService();
            
            // Initialize UI components
            initializeDonorsView();
            
            // Set up initial view
            showDashboard();
            updateDashboardData();
            
            System.out.println("MainController initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to initialize application: " + e.getMessage());
        }
    }
    
    private void initializeDonorsView() {
        // Initialize blood type combo boxes
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (bloodTypeFilter != null) {
            bloodTypeFilter.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        if (bloodTypeField != null) {
            bloodTypeField.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        
        // Initialize gender combo box
        List<String> genders = Arrays.asList("MALE", "FEMALE", "OTHER");
        if (genderField != null) {
            genderField.setItems(FXCollections.observableArrayList(genders));
        }
        
        // Initialize frequency combo box
        List<String> frequencies = Arrays.asList("Once", "Twice a year", "Quarterly", "Monthly");
        if (frequencyField != null) {
            frequencyField.setItems(FXCollections.observableArrayList(frequencies));
        }
        
        // Initialize status combo box
        List<String> statuses = Arrays.asList("ELIGIBLE", "TEMPORARILY_INELIGIBLE", "PERMANENTLY_INELIGIBLE");
        if (donorStatusFilter != null) {
            donorStatusFilter.setItems(FXCollections.observableArrayList(statuses));
        }
        
        // Set up table columns
        if (donorIdColumn != null) donorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (donorNameColumn != null) donorNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (donorBloodTypeColumn != null) donorBloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        if (donorAgeColumn != null) donorAgeColumn.setCellValueFactory(cellData -> {
            Donor donor = cellData.getValue();
            if (donor.getDateOfBirth() != null) {
                int age = LocalDate.now().getYear() - donor.getDateOfBirth().getYear();
                return new javafx.beans.property.SimpleIntegerProperty(age).asObject();
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });
        if (donorPhoneColumn != null) donorPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        if (donorEmailColumn != null) donorEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (donorLocationColumn != null) donorLocationColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        if (donorLastDonationColumn != null) donorLastDonationColumn.setCellValueFactory(new PropertyValueFactory<>("lastDonationDate"));
        if (donorStatusColumn != null) donorStatusColumn.setCellValueFactory(new PropertyValueFactory<>("eligibilityStatus"));
    }
    
    private void loadDonorData() {
        try {
            if (donorService != null && donorsTable != null) {
                List<Donor> donors = donorService.getAllDonors();
                donorList = FXCollections.observableArrayList(donors);
                donorsTable.setItems(donorList);
                
                // Update statistics
                if (totalDonorsCount != null) {
                    totalDonorsCount.setText(String.valueOf(donors.size()));
                }
                if (eligibleDonorsCount != null) {
                    long eligibleCount = donors.stream()
                        .filter(d -> "ELIGIBLE".equals(d.getEligibilityStatus()))
                        .count();
                    eligibleDonorsCount.setText(String.valueOf(eligibleCount));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load donor data: " + e.getMessage());
        }
    }
    
    @FXML
    private void addNewDonor() {
        try {
            // Validate required fields
            if (firstNameField != null && (firstNameField.getText() == null || firstNameField.getText().trim().isEmpty())) {
                showAlert("Validation Error", "First name is required");
                return;
            }
            
            if (bloodTypeField != null && (bloodTypeField.getValue() == null || bloodTypeField.getValue().isEmpty())) {
                showAlert("Validation Error", "Blood type is required");
                return;
            }
            
            // Create new donor
            Donor donor = new Donor();
            donor.setName((firstNameField != null ? firstNameField.getText() : "") + 
                (lastNameField != null && lastNameField.getText() != null ? " " + lastNameField.getText() : ""));
            if (bloodTypeField != null) {
                donor.setBloodGroup(bloodTypeField.getValue());
            }
            if (phoneField != null) {
                donor.setPhone(phoneField.getText());
            }
            if (emailField != null) {
                donor.setEmail(emailField.getText());
            }
            if (genderField != null) {
                donor.setGender(genderField.getValue());
            }
            if (locationField != null) {
                donor.setCity(locationField.getText());
            }
            if (addressField != null) {
                donor.setAddress(addressField.getText());
            }
            
            if (dobField != null && dobField.getValue() != null) {
                donor.setDateOfBirth(dobField.getValue());
            }
            
            if (lastDonationField != null && lastDonationField.getValue() != null) {
                donor.setLastDonationDate(lastDonationField.getValue());
            }
            
            // Save donor
            boolean success = donorService.addDonor(donor);
            if (success) {
                showAlert("Success", "Donor added successfully!");
                clearDonorForm();
                loadDonorData(); // Refresh the table
            } else {
                showAlert("Error", "Failed to add donor");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add donor: " + e.getMessage());
        }
    }
    
    private void clearDonorForm() {
        if (firstNameField != null) firstNameField.clear();
        if (lastNameField != null) lastNameField.clear();
        if (bloodTypeField != null) bloodTypeField.getSelectionModel().clearSelection();
        if (dobField != null) dobField.setValue(null);
        if (phoneField != null) phoneField.clear();
        if (emailField != null) emailField.clear();
        if (genderField != null) genderField.getSelectionModel().clearSelection();
        if (locationField != null) locationField.clear();
        if (addressField != null) addressField.clear();
        if (lastDonationField != null) lastDonationField.setValue(null);
        if (frequencyField != null) frequencyField.getSelectionModel().clearSelection();
    }
    
    // Navigation methods
    @FXML
    private void showDashboard() {
        hideAllViews();
        if (dashboardView != null) {
            dashboardView.setVisible(true);
            dashboardView.setManaged(true);
        }
        currentView = "dashboard";
        updateActiveButton(dashboardBtn);
        updateDashboardData();
        System.out.println("Dashboard view shown");
    }
    
    @FXML
    private void showDonors() {
        hideAllViews();
        if (donorsView != null) {
            donorsView.setVisible(true);
            donorsView.setManaged(true);
        }
        currentView = "donors";
        updateActiveButton(donorsBtn);
        loadDonorData(); // Load donor data when showing donors view
        System.out.println("Donors view shown");
    }
    
    @FXML
    private void showRecipients() {
        hideAllViews();
        if (recipientsView != null) {
            recipientsView.setVisible(true);
            recipientsView.setManaged(true);
        }
        currentView = "recipients";
        updateActiveButton(recipientsBtn);
        System.out.println("Recipients view shown");
    }
    
    @FXML
    private void showInventory() {
        hideAllViews();
        if (inventoryView != null) {
            inventoryView.setVisible(true);
            inventoryView.setManaged(true);
        }
        currentView = "inventory";
        updateActiveButton(inventoryBtn);
        System.out.println("Inventory view shown");
    }
    
    @FXML
    private void showCampaigns() {
        hideAllViews();
        if (campaignsView != null) {
            campaignsView.setVisible(true);
            campaignsView.setManaged(true);
        }
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
        if (rewardsView != null) {
            rewardsView.setVisible(true);
            rewardsView.setManaged(true);
        }
        currentView = "rewards";
        updateActiveButton(rewardsBtn);
        System.out.println("Rewards view shown");
        loadRewardsData();
    }
    
    @FXML
    private void showStatistics() {
        hideAllViews();
        if (statisticsView != null) {
            statisticsView.setVisible(true);
            statisticsView.setManaged(true);
        }
        currentView = "statistics";
        updateActiveButton(statisticsBtn);
        System.out.println("Statistics view shown");
        loadStatisticsData();
    }
    
    @FXML
    private void showEmergency() {
        hideAllViews();
        if (emergencyView != null) {
            emergencyView.setVisible(true);
            emergencyView.setManaged(true);
        }
        currentView = "emergency";
        updateActiveButton(emergencyBtn);
        System.out.println("Emergency view shown");
        loadEmergencyData();
    }
    
    // Voice Commands method
    @FXML
    private void showVoiceCommands() {
        hideAllViews();
        if (voiceCommandsView != null) {
            voiceCommandsView.setVisible(true);
            voiceCommandsView.setManaged(true);
        }
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
            if (speechRecognitionUtil != null) {
                speechRecognitionUtil.startListening();
            }
            
            showAlert("Voice Commands", "Voice command interface activated! 🎤\n\nTry saying commands like:\n- Show dashboard\n- Find donor with blood type O+\n- Check emergency requests\n- Display inventory statistics\n\nIn a real implementation, this would connect to a speech recognition service.");
        });
    }
    
    // Simulate voice command execution
    @FXML
    private void simulateVoiceCommand() {
        if (speechRecognitionUtil != null) {
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
    }
    
    // Helper methods
    private void hideAllViews() {
        if (dashboardView != null) {
            dashboardView.setVisible(false);
            dashboardView.setManaged(false);
        }
        if (donorsView != null) {
            donorsView.setVisible(false);
            donorsView.setManaged(false);
        }
        if (recipientsView != null) {
            recipientsView.setVisible(false);
            recipientsView.setManaged(false);
        }
        if (inventoryView != null) {
            inventoryView.setVisible(false);
            inventoryView.setManaged(false);
        }
        if (campaignsView != null) {
            campaignsView.setVisible(false);
            campaignsView.setManaged(false);
        }
        if (rewardsView != null) {
            rewardsView.setVisible(false);
            rewardsView.setManaged(false);
        }
        if (statisticsView != null) {
            statisticsView.setVisible(false);
            statisticsView.setManaged(false);
        }
        if (emergencyView != null) {
            emergencyView.setVisible(false);
            emergencyView.setManaged(false);
        }
        if (voiceCommandsView != null) {
            voiceCommandsView.setVisible(false);
            voiceCommandsView.setManaged(false);
        }
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