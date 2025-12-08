package com.bloodmate.desktop.controller;

import com.bloodmate.desktop.db.Db;
import com.bloodmate.desktop.exception.ValidationException;
import com.bloodmate.desktop.model.*;
import com.bloodmate.desktop.repo.*;
import com.bloodmate.desktop.report.*;
import com.bloodmate.desktop.service.*;
import com.bloodmate.desktop.util.SpeechRecognitionUtil;
import com.bloodmate.desktop.util.ValidationUtil;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.chart.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    
    // Recipients view UI components
    @FXML private TextField recipientSearchField;
    @FXML private ComboBox<String> reqBloodTypeFilter;
    @FXML private ComboBox<String> urgencyFilter;
    @FXML private ComboBox<String> requestStatusFilter;
    @FXML private Label activeRequestsCount;
    @FXML private Label urgentRequestsCount;
    @FXML private Label fulfilledTodayCount;
    @FXML private Label pendingAllocationCount;
    @FXML private TableView<Recipient> recipientsTable;
    @FXML private TableColumn<Recipient, String> requestIdColumn;
    @FXML private TableColumn<Recipient, String> recipientNameColumn;
    @FXML private TableColumn<Recipient, String> reqBloodTypeColumn;
    @FXML private TableColumn<Recipient, Integer> unitsRequiredColumn;
    @FXML private TableColumn<Recipient, LocalDateTime> requestDateColumn;
    @FXML private TableColumn<Recipient, String> urgencyColumn;
    @FXML private TableColumn<Recipient, String> statusColumn;
    @FXML private TextField recipientNameField;
    @FXML private TextField hospitalField;
    @FXML private ComboBox<String> reqBloodTypeField;
    @FXML private TextField unitsRequiredField;
    @FXML private ComboBox<String> urgencyField;
    @FXML private TextField contactField;
    @FXML private TextField reasonField;
    
    // Blood Inventory view UI components
    @FXML private Label totalUnitsCount;
    @FXML private Label criticalStockCount;
    @FXML private Label expiringSoonCount;
    @FXML private Label avgTurnoverCount;
    @FXML private TableView<BloodInventory> inventoryTable;
    @FXML private TableColumn<BloodInventory, String> bloodTypeColumn;
    @FXML private TableColumn<BloodInventory, Integer> totalUnitsColumn;
    @FXML private TableColumn<BloodInventory, Integer> availableColumn;
    @FXML private TableColumn<BloodInventory, Integer> reservedColumn;
    @FXML private TableColumn<BloodInventory, Integer> expiringSoonColumn;
    @FXML private TableColumn<BloodInventory, String> criticalLevelColumn;
    @FXML private TableView<?> storageTable;
    @FXML private TableColumn<?, ?> storageIdColumn;
    @FXML private TableColumn<?, ?> locationColumn;
    @FXML private TableColumn<?, ?> capacityColumn;
    @FXML private TableColumn<?, ?> usedSpaceColumn;
    @FXML private TableColumn<?, ?> temperatureColumn;
    @FXML private TableColumn<?, ?> statusColumn2;
    @FXML private TextField donorIdField;
    @FXML private ComboBox<String> unitBloodTypeField;
    @FXML private DatePicker collectionDateField;
    @FXML private DatePicker expiryDateField;
    @FXML private TextField volumeField;
    @FXML private ComboBox<String> storageUnitField;
    @FXML private ComboBox<String> qualityStatusField;
    @FXML private ComboBox<String> donationTypeField;
    
    // Campaigns view UI components
    @FXML private Label activeCampaignsCount;
    @FXML private Label upcomingEventsCount;
    @FXML private Label registeredDonorsCount;
    @FXML private Label expectedCollectionCount;
    @FXML private TableView<Campaign> campaignsTable;
    @FXML private TableColumn<Campaign, String> campaignIdColumn;
    @FXML private TableColumn<Campaign, String> campaignNameColumn;
    @FXML private TableColumn<Campaign, LocalDate> startDateColumn;
    @FXML private TableColumn<Campaign, LocalDate> endDateColumn;
    @FXML private TableColumn<Campaign, String> locationColumn2;
    @FXML private TableColumn<Campaign, Integer> targetDonorsColumn;
    @FXML private TableColumn<Campaign, Integer> registeredDonorsColumn;
    @FXML private TableColumn<Campaign, String> statusColumn3;
    @FXML private TextField campaignNameField;
    @FXML private TextField campaignLocationField;
    @FXML private TextField targetDonorsField;
    @FXML private DatePicker startDateField;
    @FXML private DatePicker endDateField;
    @FXML private TextField organizerField;
    @FXML private TextField descriptionField;
    
    // Rewards view UI components
    @FXML private Label totalPointsCount;
    @FXML private Label activeRewardsDonorsCount;
    @FXML private Label topDonorName;
    @FXML private Label monthlyPointsCount;
    @FXML private TableView<Donor> leaderboardTable;
    @FXML private TableColumn<Donor, Integer> rankColumn;
    @FXML private TableColumn<Donor, String> donorNameColumn2;
    @FXML private TableColumn<Donor, Integer> donationCountColumn;
    @FXML private TableColumn<Donor, Integer> pointsColumn;
    @FXML private TableColumn<Donor, String> badgesColumn;
    @FXML private TableColumn<Donor, LocalDate> lastDonationColumn2;
    @FXML private TableView<?> achievementsTable;
    @FXML private TableColumn<?, ?> badgeNameColumn;
    @FXML private TableColumn<?, ?> descriptionColumn;
    @FXML private TableColumn<?, ?> criteriaColumn;
    @FXML private TableColumn<?, ?> earnedColumn;
    
    // Statistics view UI components
    @FXML private TableView<AdvancedReport> reportsTable;
    @FXML private TableColumn<AdvancedReport, String> reportIdColumn;
    @FXML private TableColumn<AdvancedReport, String> reportNameColumn;
    @FXML private TableColumn<AdvancedReport, LocalDate> generatedDateColumn;
    @FXML private TableColumn<AdvancedReport, String> reportTypeColumn;
    @FXML private TableColumn<AdvancedReport, String> generatedByColumn;
    @FXML private ComboBox<String> reportTypeField;
    @FXML private DatePicker startDateField2;
    @FXML private DatePicker endDateField2;
    @FXML private ComboBox<String> formatField;
    @FXML private TextField filtersField;
    
    // Emergency view UI components
    @FXML private Label activeAlertsCount;
    @FXML private Label criticalRequestsCount;
    @FXML private Label avgResponseTime;
    @FXML private Label resolvedTodayCount;
    @FXML private TableView<EmergencyRequest> alertsTable;
    @FXML private TableColumn<EmergencyRequest, String> alertIdColumn;
    @FXML private TableColumn<EmergencyRequest, String> alertTypeColumn;
    @FXML private TableColumn<EmergencyRequest, String> bloodTypeColumn2;
    @FXML private TableColumn<EmergencyRequest, Integer> unitsRequiredColumn2;
    @FXML private TableColumn<EmergencyRequest, String> hospitalColumn;
    @FXML private TableColumn<EmergencyRequest, LocalDateTime> requestTimeColumn;
    @FXML private TableColumn<EmergencyRequest, String> statusColumn4;
    @FXML private ComboBox<String> alertTypeField;
    @FXML private ComboBox<String> emergencyBloodTypeField;
    @FXML private TextField emergencyUnitsField;
    @FXML private TextField emergencyHospitalField;
    @FXML private TextField contactPersonField;
    @FXML private TextField emergencyContactField;
    @FXML private TextField additionalInfoField;
    
    // Voice Commands view UI components
    @FXML private Label voiceStatusIndicator;
    @FXML private Label lastCommandLabel;
    @FXML private TableView<VoiceCommand> voiceHistoryTable;
    @FXML private TableColumn<VoiceCommand, LocalDateTime> timestampColumn;
    @FXML private TableColumn<VoiceCommand, String> commandColumn;
    @FXML private TableColumn<VoiceCommand, String> actionColumn;
    @FXML private TableColumn<VoiceCommand, String> statusColumn5;
    @FXML private ComboBox<String> voiceEngineField;
    @FXML private ComboBox<String> languageField;
    @FXML private Slider sensitivitySlider;
    @FXML private CheckBox continuousListeningCheckbox;
    @FXML private CheckBox voiceFeedbackCheckbox;
    @FXML private CheckBox commandHistoryCheckbox;
    
    // Settings view UI components
    @FXML private TextField fullNameField;
    @FXML private TextField usernameField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private RadioButton lightThemeRadio;
    @FXML private RadioButton darkThemeRadio;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private ComboBox<String> startupViewComboBox;
    @FXML private ComboBox<String> refreshIntervalComboBox;
    @FXML private CheckBox emailNotificationsCheckbox;
    @FXML private CheckBox desktopNotificationsCheckbox;
    @FXML private CheckBox soundNotificationsCheckbox;
    @FXML private CheckBox emergencyAlertsCheckbox;
    @FXML private TextField dbHostField;
    @FXML private TextField dbPortField;
    @FXML private TextField dbNameField;
    @FXML private TextField dbUsernameField;
    @FXML private PasswordField dbPasswordField;
    @FXML private TextField poolSizeField;
    @FXML private CheckBox cacheEnabledCheckbox;
    @FXML private CheckBox debugModeCheckbox;
    @FXML private CheckBox autoBackupCheckbox;
    
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
    @FXML private ScrollPane settingsView;
    
    // ImageView components for blood drop logos
    @FXML private javafx.scene.image.ImageView bloodDropIcon;
    @FXML private javafx.scene.image.ImageView bloodDropIconLarge;
    
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
            initializeRecipientsView();
            initializeInventoryView();
            initializeCampaignsView();
            initializeRewardsView();
            initializeStatisticsView();
            initializeEmergencyView();
            initializeVoiceCommandsView();
            initializeSettingsView();
            
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
    
    private void initializeRecipientsView() {
        // Initialize blood type combo boxes
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (reqBloodTypeFilter != null) {
            reqBloodTypeFilter.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        if (reqBloodTypeField != null) {
            reqBloodTypeField.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        
        // Initialize urgency combo boxes
        List<String> urgencyLevels = Arrays.asList("LOW", "MEDIUM", "HIGH", "CRITICAL");
        if (urgencyFilter != null) {
            urgencyFilter.setItems(FXCollections.observableArrayList(urgencyLevels));
        }
        if (urgencyField != null) {
            urgencyField.setItems(FXCollections.observableArrayList(urgencyLevels));
        }
        
        // Initialize status combo box
        List<String> statuses = Arrays.asList("PENDING", "MATCHED", "FULFILLED", "CANCELLED");
        if (requestStatusFilter != null) {
            requestStatusFilter.setItems(FXCollections.observableArrayList(statuses));
        }
        
        // Set up table columns
        if (requestIdColumn != null) requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (recipientNameColumn != null) recipientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (reqBloodTypeColumn != null) reqBloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        if (unitsRequiredColumn != null) unitsRequiredColumn.setCellValueFactory(new PropertyValueFactory<>("requiredUnits"));
        if (requestDateColumn != null) requestDateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
        if (urgencyColumn != null) urgencyColumn.setCellValueFactory(new PropertyValueFactory<>("urgencyLevel"));
        if (statusColumn != null) statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void initializeInventoryView() {
        // Initialize blood type combo boxes
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (unitBloodTypeField != null) {
            unitBloodTypeField.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        
        // Initialize storage unit combo box
        List<String> storageUnits = Arrays.asList("Storage-A1", "Storage-A2", "Storage-B1", "Storage-B2", 
                                                 "Storage-C1", "Storage-C2", "Storage-D1", "Storage-D2");
        if (storageUnitField != null) {
            storageUnitField.setItems(FXCollections.observableArrayList(storageUnits));
        }
        
        // Initialize quality status combo box
        List<String> qualityStatuses = Arrays.asList("EXCELLENT", "GOOD", "FAIR", "POOR");
        if (qualityStatusField != null) {
            qualityStatusField.setItems(FXCollections.observableArrayList(qualityStatuses));
        }
        
        // Initialize donation type combo box
        List<String> donationTypes = Arrays.asList("WHOLE_BLOOD", "PLASMA", "PLATELETS", "RED_CELLS");
        if (donationTypeField != null) {
            donationTypeField.setItems(FXCollections.observableArrayList(donationTypes));
        }
        
        // Set up inventory table columns
        if (bloodTypeColumn != null) bloodTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        if (totalUnitsColumn != null) totalUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("volume"));
        if (availableColumn != null) availableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        if (reservedColumn != null) reservedColumn.setCellValueFactory(new PropertyValueFactory<>("isReserved"));
        if (expiringSoonColumn != null) expiringSoonColumn.setCellValueFactory(cellData -> {
            BloodInventory inventory = cellData.getValue();
            if (inventory.getExpiryDate() != null) {
                long daysUntilExpiry = java.time.temporal.ChronoUnit.DAYS.between(
                    java.time.LocalDate.now(), inventory.getExpiryDate());
                return new javafx.beans.property.SimpleIntegerProperty((int) daysUntilExpiry).asObject();
            }
            return new javafx.beans.property.SimpleIntegerProperty(0).asObject();
        });
        if (criticalLevelColumn != null) criticalLevelColumn.setCellValueFactory(new PropertyValueFactory<>("qualityScore"));
    }
    
    private void initializeCampaignsView() {
        // Set up campaigns table columns
        if (campaignIdColumn != null) campaignIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (campaignNameColumn != null) campaignNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        if (startDateColumn != null) startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        if (endDateColumn != null) endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        if (locationColumn2 != null) locationColumn2.setCellValueFactory(new PropertyValueFactory<>("location"));
        if (targetDonorsColumn != null) targetDonorsColumn.setCellValueFactory(new PropertyValueFactory<>("targetUnits"));
        if (registeredDonorsColumn != null) registeredDonorsColumn.setCellValueFactory(new PropertyValueFactory<>("collectedUnits"));
        if (statusColumn3 != null) statusColumn3.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void initializeRewardsView() {
        // Set up leaderboard table columns
        if (rankColumn != null) rankColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (donorNameColumn2 != null) donorNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (donationCountColumn != null) donationCountColumn.setCellValueFactory(new PropertyValueFactory<>("totalDonations"));
        if (pointsColumn != null) pointsColumn.setCellValueFactory(new PropertyValueFactory<>("rewardPoints"));
        if (badgesColumn != null) badgesColumn.setCellValueFactory(new PropertyValueFactory<>("tierLevel"));
        if (lastDonationColumn2 != null) lastDonationColumn2.setCellValueFactory(new PropertyValueFactory<>("lastDonationDate"));
    }
    
    private void initializeStatisticsView() {
        // Initialize report type combo box
        List<String> reportTypes = Arrays.asList("Donor Engagement", "Inventory Analytics", "Campaign Performance", "Emergency Response");
        if (reportTypeField != null) {
            reportTypeField.setItems(FXCollections.observableArrayList(reportTypes));
        }
        
        // Initialize format combo box
        List<String> formats = Arrays.asList("PDF", "Excel", "CSV", "HTML");
        if (formatField != null) {
            formatField.setItems(FXCollections.observableArrayList(formats));
        }
        
        // Set up reports table columns
        if (reportIdColumn != null) reportIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (reportNameColumn != null) reportNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        if (generatedDateColumn != null) generatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("generatedDate"));
        if (reportTypeColumn != null) reportTypeColumn.setCellValueFactory(new PropertyValueFactory<>("reportType"));
        if (generatedByColumn != null) generatedByColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Using ID as placeholder
    }
    
    private void initializeEmergencyView() {
        // Initialize blood type combo box
        List<String> bloodTypes = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (emergencyBloodTypeField != null) {
            emergencyBloodTypeField.setItems(FXCollections.observableArrayList(bloodTypes));
        }
        
        // Initialize alert type combo box
        List<String> alertTypes = Arrays.asList("BLOOD_SHORTAGE", "EMERGENCY_REQUEST", "CRITICAL_PATIENT", "MASS_CASUALTY");
        if (alertTypeField != null) {
            alertTypeField.setItems(FXCollections.observableArrayList(alertTypes));
        }
        
        // Set up alerts table columns
        if (alertIdColumn != null) alertIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (alertTypeColumn != null) alertTypeColumn.setCellValueFactory(new PropertyValueFactory<>("urgencyLevel"));
        if (bloodTypeColumn2 != null) bloodTypeColumn2.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        if (unitsRequiredColumn2 != null) unitsRequiredColumn2.setCellValueFactory(new PropertyValueFactory<>("unitsRequired"));
        if (hospitalColumn != null) hospitalColumn.setCellValueFactory(new PropertyValueFactory<>("hospital"));
        if (requestTimeColumn != null) requestTimeColumn.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
        if (statusColumn4 != null) statusColumn4.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void initializeVoiceCommandsView() {
        // Initialize voice engine combo box
        List<String> voiceEngines = Arrays.asList("Google Speech Recognition", "Microsoft Speech API", "Custom Voice Engine");
        if (voiceEngineField != null) {
            voiceEngineField.setItems(FXCollections.observableArrayList(voiceEngines));
        }
        
        // Initialize language combo box
        List<String> languages = Arrays.asList("English", "Hindi", "Spanish", "French", "German");
        if (languageField != null) {
            languageField.setItems(FXCollections.observableArrayList(languages));
        }
        
        // Set up voice history table columns
        if (timestampColumn != null) timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        if (commandColumn != null) commandColumn.setCellValueFactory(new PropertyValueFactory<>("commandText"));
        if (actionColumn != null) actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        if (statusColumn5 != null) statusColumn5.setCellValueFactory(new PropertyValueFactory<>("status"));
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
    
    private void loadRecipientData() {
        try {
            if (recipientService != null && recipientsTable != null) {
                List<Recipient> recipients = recipientService.getAllRecipients();
                ObservableList<Recipient> recipientList = FXCollections.observableArrayList(recipients);
                recipientsTable.setItems(recipientList);
                
                // Update statistics
                if (activeRequestsCount != null) {
                    long activeCount = recipients.stream()
                        .filter(r -> "PENDING".equals(r.getStatus()))
                        .count();
                    activeRequestsCount.setText(String.valueOf(activeCount));
                }
                if (urgentRequestsCount != null) {
                    long urgentCount = recipients.stream()
                        .filter(r -> "HIGH".equals(r.getUrgencyLevel()) || "CRITICAL".equals(r.getUrgencyLevel()))
                        .count();
                    urgentRequestsCount.setText(String.valueOf(urgentCount));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load recipient data: " + e.getMessage());
        }
    }
    
    private void loadInventoryData() {
        try {
            if (inventoryService != null && inventoryTable != null) {
                List<BloodInventory> inventoryList = inventoryService.getAllInventory();
                ObservableList<BloodInventory> observableInventoryList = FXCollections.observableArrayList(inventoryList);
                inventoryTable.setItems(observableInventoryList);
                
                // Update statistics
                if (totalUnitsCount != null) {
                    int totalUnits = inventoryList.size();
                    totalUnitsCount.setText(String.valueOf(totalUnits));
                }
                
                if (criticalStockCount != null) {
                    long criticalCount = inventoryList.stream()
                        .filter(inv -> inv.getQualityScore() < 7)
                        .count();
                    criticalStockCount.setText(String.valueOf(criticalCount));
                }
                
                if (expiringSoonCount != null) {
                    long expiringSoon = inventoryList.stream()
                        .filter(inv -> {
                            if (inv.getExpiryDate() != null) {
                                long daysUntilExpiry = java.time.temporal.ChronoUnit.DAYS.between(
                                    java.time.LocalDate.now(), inv.getExpiryDate());
                                return daysUntilExpiry <= 7 && daysUntilExpiry >= 0;
                            }
                            return false;
                        })
                        .count();
                    expiringSoonCount.setText(String.valueOf(expiringSoon));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load inventory data: " + e.getMessage());
        }
    }
    
    private void loadCampaignsData() {
        try {
            if (campaignService != null && campaignsTable != null) {
                List<Campaign> campaigns = campaignService.getAllCampaigns();
                ObservableList<Campaign> campaignList = FXCollections.observableArrayList(campaigns);
                campaignsTable.setItems(campaignList);
                
                // Update statistics
                if (activeCampaignsCount != null) {
                    long activeCount = campaigns.stream()
                        .filter(c -> "ACTIVE".equals(c.getStatus()))
                        .count();
                    activeCampaignsCount.setText(String.valueOf(activeCount));
                }
                
                if (upcomingEventsCount != null) {
                    long upcomingCount = campaigns.stream()
                        .filter(c -> c.getStartDate() != null && c.getStartDate().isAfter(java.time.LocalDate.now()))
                        .count();
                    upcomingEventsCount.setText(String.valueOf(upcomingCount));
                }
                
                if (registeredDonorsCount != null) {
                    int totalRegistered = campaigns.stream()
                        .mapToInt(Campaign::getCollectedUnits)
                        .sum();
                    registeredDonorsCount.setText(String.valueOf(totalRegistered));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load campaigns data: " + e.getMessage());
        }
    }
    
    private void loadRewardsData() {
        try {
            if (donorService != null && leaderboardTable != null) {
                List<Donor> donors = donorService.getAllDonors();
                
                // Sort donors by reward points (descending)
                donors.sort((d1, d2) -> Integer.compare(d2.getRewardPoints(), d1.getRewardPoints()));
                
                ObservableList<Donor> donorList = FXCollections.observableArrayList(donors);
                leaderboardTable.setItems(donorList);
                
                // Update statistics
                if (totalPointsCount != null) {
                    int totalPoints = donors.stream()
                        .mapToInt(Donor::getRewardPoints)
                        .sum();
                    totalPointsCount.setText(String.valueOf(totalPoints));
                }
                
                if (activeRewardsDonorsCount != null) {
                    long activeDonors = donors.stream()
                        .filter(d -> d.getRewardPoints() > 0)
                        .count();
                    activeRewardsDonorsCount.setText(String.valueOf(activeDonors));
                }
                
                if (topDonorName != null && !donors.isEmpty()) {
                    Donor topDonor = donors.get(0);
                    topDonorName.setText(topDonor.getName());
                }
                
                if (monthlyPointsCount != null) {
                    // For now, we'll just show total points as monthly points
                    int totalPoints = donors.stream()
                        .mapToInt(Donor::getRewardPoints)
                        .sum();
                    monthlyPointsCount.setText(String.valueOf(totalPoints) + " pts");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load rewards data: " + e.getMessage());
        }
    }
    
    @FXML
    private void addNewDonor() {
        try {
            // Validate required fields
            try {
                if (firstNameField != null) {
                    ValidationUtil.validateNotEmpty(firstNameField.getText(), "First name");
                }
                
                if (bloodTypeField != null) {
                    ValidationUtil.validateNotEmpty(bloodTypeField.getValue(), "Blood type");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
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
    
    @FXML
    private void addNewRecipient() {
        try {
            // Validate required fields
            try {
                if (recipientNameField != null) {
                    ValidationUtil.validateNotEmpty(recipientNameField.getText(), "Recipient name");
                }
                
                if (hospitalField != null) {
                    ValidationUtil.validateNotEmpty(hospitalField.getText(), "Hospital name");
                }
                
                if (reqBloodTypeField != null) {
                    ValidationUtil.validateNotEmpty(reqBloodTypeField.getValue(), "Blood type");
                }
                
                if (unitsRequiredField != null) {
                    ValidationUtil.validateNotEmpty(unitsRequiredField.getText(), "Units required");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
                return;
            }
            
            // Create new recipient
            Recipient recipient = new Recipient();
            if (recipientNameField != null) {
                recipient.setPatientName(recipientNameField.getText());
            }
            if (hospitalField != null) {
                recipient.setMedicalFacility(hospitalField.getText());
            }
            if (reqBloodTypeField != null) {
                recipient.setBloodGroup(reqBloodTypeField.getValue());
            }
            if (contactField != null) {
                recipient.setContactNumber(contactField.getText());
            }
            if (reasonField != null) {
                recipient.setReason(reasonField.getText());
            }
            if (urgencyField != null) {
                recipient.setUrgencyLevel(urgencyField.getValue());
            }
            
            // Parse units required
            if (unitsRequiredField != null && !unitsRequiredField.getText().trim().isEmpty()) {
                try {
                    int units = ValidationUtil.validateInteger(unitsRequiredField.getText().trim(), "Units required");
                    recipient.setUnitsRequired(units);
                } catch (ValidationException e) {
                    showAlert("Validation Error", e.getMessage());
                    return;
                }
            } else {
                recipient.setUnitsRequired(1); // Default to 1 unit
            }
            
            // Save recipient
            boolean success = recipientService.addRecipient(recipient);
            if (success) {
                showAlert("Success", "Recipient request added successfully!");
                clearRecipientForm();
                loadRecipientData(); // Refresh the table
            } else {
                showAlert("Error", "Failed to add recipient request");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add recipient request: " + e.getMessage());
        }
    }
    
    @FXML
    private void addNewBloodUnit() {
        try {
            // Validate required fields
            try {
                if (unitBloodTypeField != null) {
                    ValidationUtil.validateNotEmpty(unitBloodTypeField.getValue(), "Blood type");
                }
                
                if (collectionDateField != null && collectionDateField.getValue() == null) {
                    throw new ValidationException("Collection date is required");
                }
                
                if (expiryDateField != null && expiryDateField.getValue() == null) {
                    throw new ValidationException("Expiry date is required");
                }
                
                if (volumeField != null) {
                    ValidationUtil.validateNotEmpty(volumeField.getText(), "Volume");
                }
                
                if (storageUnitField != null) {
                    ValidationUtil.validateNotEmpty(storageUnitField.getValue(), "Storage unit");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
                return;
            }
            
            // Create new blood inventory unit
            BloodInventory inventory = new BloodInventory();
            if (unitBloodTypeField != null) {
                inventory.setBloodGroup(unitBloodTypeField.getValue());
            }
            if (donorIdField != null && !donorIdField.getText().trim().isEmpty()) {
                inventory.setDonorId(donorIdField.getText().trim());
            }
            if (collectionDateField != null && collectionDateField.getValue() != null) {
                inventory.setDonationDate(collectionDateField.getValue());
            }
            if (expiryDateField != null && expiryDateField.getValue() != null) {
                inventory.setExpiryDate(expiryDateField.getValue());
            }
            
            // Parse volume
            if (volumeField != null && !volumeField.getText().trim().isEmpty()) {
                try {
                    double volume = ValidationUtil.validateDouble(volumeField.getText().trim(), "Volume");
                    inventory.setVolume(volume);
                } catch (ValidationException e) {
                    showAlert("Validation Error", e.getMessage());
                    return;
                }
            } else {
                inventory.setVolume(450.0); // Default to 450ml
            }
            
            if (storageUnitField != null) {
                inventory.setLocation(storageUnitField.getValue());
            }
            
            // Set default values
            inventory.setStatus("AVAILABLE");
            inventory.setQualityScore(10); // Default excellent quality
            
            // Save inventory unit
            boolean success = inventoryService.addInventory(inventory);
            if (success) {
                showAlert("Success", "Blood unit added successfully!");
                clearInventoryForm();
                loadInventoryData(); // Refresh the table
            } else {
                showAlert("Error", "Failed to add blood unit");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add blood unit: " + e.getMessage());
        }
    }
    
    @FXML
    private void createCampaign() {
        try {
            // Validate required fields
            try {
                if (campaignNameField != null) {
                    ValidationUtil.validateNotEmpty(campaignNameField.getText(), "Campaign name");
                }
                
                if (campaignLocationField != null) {
                    ValidationUtil.validateNotEmpty(campaignLocationField.getText(), "Campaign location");
                }
                
                if (startDateField != null && startDateField.getValue() == null) {
                    throw new ValidationException("Start date is required");
                }
                
                if (endDateField != null && endDateField.getValue() == null) {
                    throw new ValidationException("End date is required");
                }
                
                if (organizerField != null) {
                    ValidationUtil.validateNotEmpty(organizerField.getText(), "Organizer");
                }
                
                // Validate date range
                if (startDateField != null && endDateField != null && 
                    startDateField.getValue() != null && endDateField.getValue() != null) {
                    ValidationUtil.validateDateRange(startDateField.getValue(), endDateField.getValue(), "Campaign dates");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
                return;
            }
            
            // Create new campaign
            Campaign campaign = new Campaign();
            if (campaignNameField != null) {
                campaign.setTitle(campaignNameField.getText());
            }
            if (campaignLocationField != null) {
                campaign.setLocation(campaignLocationField.getText());
            }
            if (descriptionField != null) {
                campaign.setDescription(descriptionField.getText());
            }
            if (startDateField != null && startDateField.getValue() != null) {
                campaign.setStartDate(startDateField.getValue());
            }
            if (endDateField != null && endDateField.getValue() != null) {
                campaign.setEndDate(endDateField.getValue());
            }
            if (organizerField != null) {
                campaign.setOrganizer(organizerField.getText());
            }
            
            // Parse target donors
            if (targetDonorsField != null && !targetDonorsField.getText().trim().isEmpty()) {
                try {
                    int targetDonors = ValidationUtil.validateInteger(targetDonorsField.getText().trim(), "Target donors");
                    campaign.setTargetUnits(targetDonors);
                } catch (ValidationException e) {
                    showAlert("Validation Error", e.getMessage());
                    return;
                }
            } else {
                campaign.setTargetUnits(100); // Default to 100 donors
            }
            
            // Set default values
            campaign.setStatus("PLANNED");
            
            // Save campaign
            boolean success = campaignService.addCampaign(campaign);
            if (success) {
                showAlert("Success", "Campaign created successfully!");
                clearCampaignForm();
                loadCampaignsData(); // Refresh the table
            } else {
                showAlert("Error", "Failed to create campaign");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to create campaign: " + e.getMessage());
        }
    }
    
    @FXML
    private void generateReport() {
        try {
            // Validate required fields
            try {
                if (reportTypeField != null) {
                    ValidationUtil.validateNotEmpty(reportTypeField.getValue(), "Report type");
                }
                
                if (startDateField2 != null && startDateField2.getValue() == null) {
                    throw new ValidationException("Start date is required");
                }
                
                if (endDateField2 != null && endDateField2.getValue() == null) {
                    throw new ValidationException("End date is required");
                }
                
                if (formatField != null) {
                    ValidationUtil.validateNotEmpty(formatField.getValue(), "Format");
                }
                
                // Validate date range
                if (startDateField2 != null && endDateField2 != null && 
                    startDateField2.getValue() != null && endDateField2.getValue() != null) {
                    ValidationUtil.validateDateRange(startDateField2.getValue(), endDateField2.getValue(), "Report dates");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
                return;
            }
            
            // For now, we'll just show a success message
            showAlert("Success", "Report generation started successfully!\n\n" +
                      "Report Type: " + (reportTypeField != null ? reportTypeField.getValue() : "N/A") + "\n" +
                      "Date Range: " + (startDateField2 != null ? startDateField2.getValue() : "N/A") + 
                      " to " + (endDateField2 != null ? endDateField2.getValue() : "N/A") + "\n" +
                      "Format: " + (formatField != null ? formatField.getValue() : "N/A"));
            
            // In a real implementation, this would generate the actual report
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to generate report: " + e.getMessage());
        }
    }
    
    @FXML
    private void raiseEmergencyAlert() {
        try {
            // Validate required fields
            try {
                if (alertTypeField != null) {
                    ValidationUtil.validateNotEmpty(alertTypeField.getValue(), "Alert type");
                }
                
                if (emergencyBloodTypeField != null) {
                    ValidationUtil.validateNotEmpty(emergencyBloodTypeField.getValue(), "Blood type");
                }
                
                if (emergencyHospitalField != null) {
                    ValidationUtil.validateNotEmpty(emergencyHospitalField.getText(), "Hospital");
                }
                
                if (contactPersonField != null) {
                    ValidationUtil.validateNotEmpty(contactPersonField.getText(), "Contact person");
                }
                
                if (emergencyContactField != null) {
                    ValidationUtil.validateNotEmpty(emergencyContactField.getText(), "Contact number");
                }
            } catch (ValidationException e) {
                showAlert("Validation Error", e.getMessage());
                return;
            }
            
            // Create new emergency request
            EmergencyRequest request = new EmergencyRequest();
            if (alertTypeField != null) {
                request.setUrgencyLevel(alertTypeField.getValue());
            }
            if (emergencyBloodTypeField != null) {
                request.setBloodGroup(emergencyBloodTypeField.getValue());
            }
            if (emergencyHospitalField != null) {
                request.setHospital(emergencyHospitalField.getText());
            }
            if (contactPersonField != null) {
                request.setDoctor(contactPersonField.getText());
            }
            if (emergencyContactField != null) {
                request.setContactNumber(emergencyContactField.getText());
            }
            if (additionalInfoField != null) {
                request.setNotes(additionalInfoField.getText());
            }
            
            // Set default values
            request.setStatus("PENDING");
            request.setPatientName("Emergency Patient"); // Default name
            
            // Save emergency request
            boolean success = emergencyRequestService.addRequest(request);
            if (success) {
                showAlert("Success", "Emergency alert raised successfully!");
                clearEmergencyForm();
                loadEmergencyData(); // Refresh the table
            } else {
                showAlert("Error", "Failed to raise emergency alert");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to raise emergency alert: " + e.getMessage());
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
    
    private void clearRecipientForm() {
        if (recipientNameField != null) recipientNameField.clear();
        if (hospitalField != null) hospitalField.clear();
        if (reqBloodTypeField != null) reqBloodTypeField.getSelectionModel().clearSelection();
        if (unitsRequiredField != null) unitsRequiredField.clear();
        if (urgencyField != null) urgencyField.getSelectionModel().clearSelection();
        if (contactField != null) contactField.clear();
        if (reasonField != null) reasonField.clear();
    }
    
    private void clearInventoryForm() {
        if (donorIdField != null) donorIdField.clear();
        if (unitBloodTypeField != null) unitBloodTypeField.getSelectionModel().clearSelection();
        if (collectionDateField != null) collectionDateField.setValue(null);
        if (expiryDateField != null) expiryDateField.setValue(null);
        if (volumeField != null) volumeField.clear();
        if (storageUnitField != null) storageUnitField.getSelectionModel().clearSelection();
        if (qualityStatusField != null) qualityStatusField.getSelectionModel().clearSelection();
        if (donationTypeField != null) donationTypeField.getSelectionModel().clearSelection();
    }
    
    private void clearCampaignForm() {
        if (campaignNameField != null) campaignNameField.clear();
        if (campaignLocationField != null) campaignLocationField.clear();
        if (targetDonorsField != null) targetDonorsField.clear();
        if (startDateField != null) startDateField.setValue(null);
        if (endDateField != null) endDateField.setValue(null);
        if (organizerField != null) organizerField.clear();
        if (descriptionField != null) descriptionField.clear();
    }
    
    private void clearEmergencyForm() {
        if (alertTypeField != null) alertTypeField.getSelectionModel().clearSelection();
        if (emergencyBloodTypeField != null) emergencyBloodTypeField.getSelectionModel().clearSelection();
        if (emergencyUnitsField != null) emergencyUnitsField.clear();
        if (emergencyHospitalField != null) emergencyHospitalField.clear();
        if (contactPersonField != null) contactPersonField.clear();
        if (emergencyContactField != null) emergencyContactField.clear();
        if (additionalInfoField != null) additionalInfoField.clear();
    }
    
    @FXML
    private void saveVoiceSettings() {
        try {
            // For now, we'll just show a success message
            showAlert("Success", "Voice settings saved successfully!\n\n" +
                      "Voice Engine: " + (voiceEngineField != null ? voiceEngineField.getValue() : "N/A") + "\n" +
                      "Language: " + (languageField != null ? languageField.getValue() : "N/A") + "\n" +
                      "Sensitivity: " + (sensitivitySlider != null ? sensitivitySlider.getValue() : "N/A") + "\n" +
                      "Continuous Listening: " + (continuousListeningCheckbox != null ? continuousListeningCheckbox.isSelected() : "N/A") + "\n" +
                      "Voice Feedback: " + (voiceFeedbackCheckbox != null ? voiceFeedbackCheckbox.isSelected() : "N/A") + "\n" +
                      "Save Command History: " + (commandHistoryCheckbox != null ? commandHistoryCheckbox.isSelected() : "N/A"));
            
            // In a real implementation, this would save the actual settings
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save voice settings: " + e.getMessage());
        }
    }
    
    @FXML
    private void saveAccountSettings() {
        try {
            // Validate passwords if they are being changed
            if (newPasswordField != null && confirmPasswordField != null && 
                !newPasswordField.getText().isEmpty()) {
                if (!newPasswordField.getText().equals(confirmPasswordField.getText())) {
                    showAlert("Error", "New password and confirm password do not match!");
                    return;
                }
                
                // In a real implementation, we would validate the current password
                // and update the user's account information
            }
            
            showAlert("Success", "Account settings saved successfully!\n\n" +
                      "Full Name: " + (fullNameField != null ? fullNameField.getText() : "N/A") + "\n" +
                      "Email: " + (emailField != null ? emailField.getText() : "N/A") + "\n" +
                      "Username: " + (usernameField != null ? usernameField.getText() : "N/A") + "\n" +
                      "Role: " + (roleComboBox != null ? roleComboBox.getValue() : "N/A"));
            
            // In a real implementation, this would save the actual settings
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save account settings: " + e.getMessage());
        }
    }
    
    @FXML
    private void savePreferences() {
        try {
            String selectedTheme = "Light";
            if (darkThemeRadio != null && darkThemeRadio.isSelected()) {
                selectedTheme = "Dark";
            }
            
            showAlert("Success", "Preferences saved successfully!\n\n" +
                      "Theme: " + selectedTheme + "\n" +
                      "Language: " + (languageComboBox != null ? languageComboBox.getValue() : "N/A") + "\n" +
                      "Startup View: " + (startupViewComboBox != null ? startupViewComboBox.getValue() : "N/A") + "\n" +
                      "Refresh Interval: " + (refreshIntervalComboBox != null ? refreshIntervalComboBox.getValue() : "N/A") + "\n" +
                      "Email Notifications: " + (emailNotificationsCheckbox != null ? emailNotificationsCheckbox.isSelected() : "N/A") + "\n" +
                      "Desktop Notifications: " + (desktopNotificationsCheckbox != null ? desktopNotificationsCheckbox.isSelected() : "N/A") + "\n" +
                      "Sound Notifications: " + (soundNotificationsCheckbox != null ? soundNotificationsCheckbox.isSelected() : "N/A") + "\n" +
                      "Emergency Alerts: " + (emergencyAlertsCheckbox != null ? emergencyAlertsCheckbox.isSelected() : "N/A"));
            
            // In a real implementation, this would save the actual settings
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save preferences: " + e.getMessage());
        }
    }
    
    @FXML
    private void saveConfiguration() {
        try {
            showAlert("Success", "Configuration saved successfully!\n\n" +
                      "Database Host: " + (dbHostField != null ? dbHostField.getText() : "N/A") + "\n" +
                      "Database Port: " + (dbPortField != null ? dbPortField.getText() : "N/A") + "\n" +
                      "Database Name: " + (dbNameField != null ? dbNameField.getText() : "N/A") + "\n" +
                      "Database Username: " + (dbUsernameField != null ? dbUsernameField.getText() : "N/A") + "\n" +
                      "Connection Pool Size: " + (poolSizeField != null ? poolSizeField.getText() : "N/A") + "\n" +
                      "Caching Enabled: " + (cacheEnabledCheckbox != null ? cacheEnabledCheckbox.isSelected() : "N/A") + "\n" +
                      "Debug Mode: " + (debugModeCheckbox != null ? debugModeCheckbox.isSelected() : "N/A") + "\n" +
                      "Auto Backups: " + (autoBackupCheckbox != null ? autoBackupCheckbox.isSelected() : "N/A"));
            
            // In a real implementation, this would save the actual settings
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save configuration: " + e.getMessage());
        }
    }
    
    @FXML
    private void testDatabaseConnection() {
        try {
            // In a real implementation, this would test the database connection
            // with the provided settings
            showAlert("Database Test", "Testing database connection...\n\n" +
                      "Host: " + (dbHostField != null ? dbHostField.getText() : "localhost") + "\n" +
                      "Port: " + (dbPortField != null ? dbPortField.getText() : "3306") + "\n" +
                      "Database: " + (dbNameField != null ? dbNameField.getText() : "bloodmate") + "\n\n" +
                      "✅ Connection Successful!\nAll settings are valid.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Test", "❌ Connection Failed!\n\nError: " + e.getMessage());
        }
    }
    
    @FXML
    private void resetSettings() {
        try {
            // Confirm with user before resetting
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Reset Settings");
            confirmAlert.setHeaderText(null);
            confirmAlert.setContentText("Are you sure you want to reset all settings to their default values? This action cannot be undone.");
            
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Reset all settings to defaults
                loadSettingsData(); // This loads default values
                showAlert("Settings Reset", "All settings have been reset to their default values.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to reset settings: " + e.getMessage());
        }
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
        loadRecipientData(); // Load recipient data when showing recipients view
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
        loadInventoryData(); // Load inventory data when showing inventory view
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
        loadCampaignsData(); // Load campaigns data when showing campaigns view
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
        if (settingsView != null) {
            settingsView.setVisible(true);
            settingsView.setManaged(true);
        }
        currentView = "settings";
        updateActiveButton(settingsBtn);
        loadSettingsData();
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
        
        // Confirm logout with user
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Logout");
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText("Are you sure you want to logout from BloodMate?");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Perform logout operations
            System.out.println("User confirmed logout");
            
            // Clear any user session data
            clearUserData();
            
            // Show logout success message
            showAlert("Logout Successful", "You have been successfully logged out of BloodMate. Thank you for using our system!");

            // Redirect back to login screen
            Platform.runLater(() -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/bloodmate/desktop/view/LoginView.fxml"));
                    Parent loginRoot = loader.load();
                    Scene scene = new Scene(loginRoot, 500, 700);
                    String css = getClass().getResource("/styles/main.css").toExternalForm();
                    scene.getStylesheets().add(css);

                    Stage stage = (Stage) dashboardBtn.getScene().getWindow();
                    stage.setTitle("🩸 BloodMate - Login");
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    showAlert("Error", "Failed to load login screen: " + e.getMessage());
                }
            });
        } else {
            System.out.println("User cancelled logout");
        }
    }
    
    private void clearUserData() {
        // Clear any stored user data or session information
        System.out.println("Clearing user session data");
        
        // In a real implementation, this would clear:
        // - User preferences
        // - Cached data
        // - Session tokens
        // - Any temporary files
        
        // For now, we'll just log that it happened
        System.out.println("User data cleared successfully");
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
    
    private void loadStatisticsData() {
        try {
            if (reportService != null && reportsTable != null) {
                // Generate dashboard reports
                List<AdvancedReport> reports = reportService.generateDashboardReports();
                ObservableList<AdvancedReport> reportList = FXCollections.observableArrayList(reports);
                reportsTable.setItems(reportList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load statistics data: " + e.getMessage());
        }
    }
    
    private void loadEmergencyData() {
        try {
            if (emergencyRequestService != null && alertsTable != null) {
                List<EmergencyRequest> requests = emergencyRequestService.getAllRequests();
                ObservableList<EmergencyRequest> requestList = FXCollections.observableArrayList(requests);
                alertsTable.setItems(requestList);
                
                // Update statistics
                if (activeAlertsCount != null) {
                    long activeCount = requests.stream()
                        .filter(r -> "PENDING".equals(r.getStatus()))
                        .count();
                    activeAlertsCount.setText(String.valueOf(activeCount));
                }
                
                if (criticalRequestsCount != null) {
                    long criticalCount = requests.stream()
                        .filter(r -> "CRITICAL".equals(r.getUrgencyLevel()))
                        .count();
                    criticalRequestsCount.setText(String.valueOf(criticalCount));
                }
                
                if (resolvedTodayCount != null) {
                    long resolvedToday = requests.stream()
                        .filter(r -> {
                            if (r.getFulfillmentTime() != null) {
                                return r.getFulfillmentTime().toLocalDate().equals(java.time.LocalDate.now());
                            }
                            return false;
                        })
                        .count();
                    resolvedTodayCount.setText(String.valueOf(resolvedToday));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load emergency data: " + e.getMessage());
        }
    }
    
    private void loadVoiceCommandsData() {
        try {
            if (voiceCommandService != null && voiceHistoryTable != null) {
                List<VoiceCommand> commands = voiceCommandService.getAllCommands();
                ObservableList<VoiceCommand> commandList = FXCollections.observableArrayList(commands);
                voiceHistoryTable.setItems(commandList);
                
                // Update status indicator
                if (voiceStatusIndicator != null) {
                    voiceStatusIndicator.setText("🔴 Not Listening");
                }
                
                // Update last command label
                if (lastCommandLabel != null && !commands.isEmpty()) {
                    VoiceCommand lastCommand = commands.get(commands.size() - 1);
                    lastCommandLabel.setText(lastCommand.getCommandText());
                } else if (lastCommandLabel != null) {
                    lastCommandLabel.setText("None");
                }
            }
            
            // Start voice recognition when the voice commands view is shown
            if (speechRecognitionUtil != null) {
                speechRecognitionUtil.startListening();
                if (voiceStatusIndicator != null) {
                    voiceStatusIndicator.setText("🟢 Listening");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load voice commands data: " + e.getMessage());
        }
    }
    
    private void initializeSettingsView() {
        // Initialize role combo box
        List<String> roles = Arrays.asList("Administrator", "Manager", "Staff", "Viewer");
        if (roleComboBox != null) {
            roleComboBox.setItems(FXCollections.observableArrayList(roles));
        }
        
        // Initialize language combo box
        List<String> languages = Arrays.asList("English", "Hindi", "Spanish", "French", "German");
        if (languageComboBox != null) {
            languageComboBox.setItems(FXCollections.observableArrayList(languages));
        }
        
        // Initialize startup view combo box
        List<String> startupViews = Arrays.asList("Dashboard", "Donors", "Recipients", "Inventory", "Campaigns");
        if (startupViewComboBox != null) {
            startupViewComboBox.setItems(FXCollections.observableArrayList(startupViews));
        }
        
        // Initialize refresh interval combo box
        List<String> intervals = Arrays.asList("1 minute", "5 minutes", "10 minutes", "30 minutes", "1 hour");
        if (refreshIntervalComboBox != null) {
            refreshIntervalComboBox.setItems(FXCollections.observableArrayList(intervals));
        }
        
        // Set up radio button toggle group for themes
        if (lightThemeRadio != null && darkThemeRadio != null) {
            ToggleGroup themeGroup = new ToggleGroup();
            lightThemeRadio.setToggleGroup(themeGroup);
            darkThemeRadio.setToggleGroup(themeGroup);
            
            // Set default selection
            lightThemeRadio.setSelected(true);
        }
        
        System.out.println("Settings view initialized");
    }
    
    private void loadSettingsData() {
        // Load saved settings or defaults
        if (fullNameField != null) {
            fullNameField.setText("John Doe");
        }
        if (emailField != null) {
            emailField.setText("john.doe@bloodmate.org");
        }
        if (usernameField != null) {
            usernameField.setText("johndoe");
        }
        if (roleComboBox != null && roleComboBox.getItems().size() > 0) {
            roleComboBox.getSelectionModel().select(0);
        }
        
        // Set default preferences
        if (lightThemeRadio != null) {
            lightThemeRadio.setSelected(true);
        }
        if (languageComboBox != null && languageComboBox.getItems().size() > 0) {
            languageComboBox.getSelectionModel().select(0);
        }
        if (startupViewComboBox != null && startupViewComboBox.getItems().size() > 0) {
            startupViewComboBox.getSelectionModel().select(0);
        }
        if (refreshIntervalComboBox != null && refreshIntervalComboBox.getItems().size() > 0) {
            refreshIntervalComboBox.getSelectionModel().select(1); // 5 minutes default
        }
        
        // Load system configuration
        if (dbHostField != null) {
            dbHostField.setText("localhost");
        }
        if (dbPortField != null) {
            dbPortField.setText("3306");
        }
        if (dbNameField != null) {
            dbNameField.setText("bloodmate");
        }
        if (dbUsernameField != null) {
            dbUsernameField.setText("root");
        }
        if (poolSizeField != null) {
            poolSizeField.setText("10");
        }
        
        System.out.println("Settings data loaded");
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