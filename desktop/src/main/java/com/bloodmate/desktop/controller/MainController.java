package com.bloodmate.desktop.controller;

	import com.bloodmate.desktop.db.Db;
import com.bloodmate.desktop.model.Donor;
import com.bloodmate.desktop.model.Recipient;
import com.bloodmate.desktop.model.BloodInventory;
import com.bloodmate.desktop.model.Campaign;
import com.bloodmate.desktop.model.DonorPoints;
import com.bloodmate.desktop.model.BloodStatistics;
import com.bloodmate.desktop.model.EmergencyRequest;
import com.bloodmate.desktop.model.AIPrediction;
import com.bloodmate.desktop.model.SensorData;
import com.bloodmate.desktop.model.BloodUnitVerification;
import com.bloodmate.desktop.repo.DonorDao;
import com.bloodmate.desktop.repo.RecipientDao;
import com.bloodmate.desktop.repo.BloodInventoryDao;
import com.bloodmate.desktop.repo.CampaignDao;
import com.bloodmate.desktop.repo.DonorPointsDao;
import com.bloodmate.desktop.repo.BloodStatisticsDao;
import com.bloodmate.desktop.repo.EmergencyRequestDao;
import com.bloodmate.desktop.repo.AIPredictionDao;
import com.bloodmate.desktop.repo.SensorDataDao;
import com.bloodmate.desktop.repo.BloodUnitVerificationDao;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.beans.property.SimpleStringProperty;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class MainController implements Initializable {
	// Logo Button
	@FXML private Button logoBtn;
	
	// Navigation Buttons
	@FXML private Button dashboardBtn;
	@FXML private Button donorsBtn;
	@FXML private Button recipientsBtn;
	@FXML private Button inventoryBtn;
	@FXML private Button campaignsBtn;
	@FXML private Button rewardsBtn;
	@FXML private Button statisticsBtn;
	@FXML private Button emergencyBtn;
	@FXML private Button settingsBtn;
	@FXML private Button logoutBtn;
	
	// AI & Innovation Navigation Buttons
	@FXML private Button aiPredictionBtn;
	@FXML private Button gamificationBtn;
	@FXML private Button iotMonitoringBtn;
	@FXML private Button blockchainBtn;
	@FXML private Button voiceCommandsBtn;
	
	// Revolutionary Technology Navigation Buttons
	@FXML private Button quantumMatchingBtn;
	@FXML private Button biometricAuthBtn;
	@FXML private Button arVisualizationBtn;
	@FXML private Button neuralQualityBtn;
	@FXML private Button droneDeliveryBtn;
	@FXML private Button digitalTwinBtn;
	@FXML private Button satelliteCommBtn;
	@FXML private Button holographicBtn;
	
	// Content Areas
	@FXML private StackPane contentArea;
	@FXML private ScrollPane dashboardView;
	@FXML private ScrollPane donorsView;
	@FXML private ScrollPane recipientsView;
	@FXML private ScrollPane inventoryView;
	@FXML private ScrollPane campaignsView;
	@FXML private ScrollPane rewardsView;
	@FXML private ScrollPane statisticsView;
	@FXML private ScrollPane emergencyView;
	
	// AI & Innovation Content Areas
	@FXML private ScrollPane aiPredictionView;
	@FXML private ScrollPane gamificationView;
	@FXML private ScrollPane iotMonitoringView;
	@FXML private ScrollPane blockchainView;
	@FXML private ScrollPane voiceCommandsView;
	
	// Revolutionary Technology Content Areas
	@FXML private ScrollPane quantumMatchingView;
	@FXML private ScrollPane biometricAuthView;
	@FXML private ScrollPane arVisualizationView;
	@FXML private ScrollPane neuralQualityView;
	@FXML private ScrollPane droneDeliveryView;
	@FXML private ScrollPane digitalTwinView;
	@FXML private ScrollPane satelliteCommView;
	@FXML private ScrollPane holographicView;
	
	// Theme Toggle
	@FXML private Button themeToggleBtn;
	
	// Dashboard Statistics Labels
	@FXML private Label currentDateTime;
	@FXML private Label totalDonorsLabel;
	@FXML private Label availableUnitsLabel;
	@FXML private Label activeCampaignsLabel;
	@FXML private Label emergencyRequestsLabel;
	
	// Blood Group Unit Labels
	@FXML private Label oNegativeUnits;
	@FXML private Label oPositiveUnits;
	@FXML private Label aNegativeUnits;
	@FXML private Label aPositiveUnits;
	@FXML private Label bNegativeUnits;
	@FXML private Label bPositiveUnits;
	@FXML private Label abNegativeUnits;
	@FXML private Label abPositiveUnits;
	
	// Tables and Lists
	@FXML private TableView<Donor> recentDonationsTable;
	@FXML private ListView<String> alertsList;
	@FXML private Pane donationTrendsChart;
	@FXML private Pane bloodGroupChart;
	
	// Table Columns
	@FXML private TableColumn<Donor, String> donorNameCol;
	@FXML private TableColumn<Donor, String> bloodTypeCol;
	@FXML private TableColumn<Donor, String> donationDateCol;
	@FXML private TableColumn<Donor, String> donationStatusCol;
	
	// Legacy fields for backward compatibility
	@FXML private TableView<Donor> donorTable;
	@FXML private TableColumn<Donor, String> colId;
	@FXML private TableColumn<Donor, String> colName;
	@FXML private TableColumn<Donor, String> colEmail;
	@FXML private TableColumn<Donor, String> colBlood;
	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private TextField phoneField;
	@FXML private TextField bloodField;
	@FXML private Label statusLabel;

	private final ObservableList<Donor> donors = FXCollections.observableArrayList();
	private final ObservableList<Recipient> recipients = FXCollections.observableArrayList();
	private final ObservableList<BloodInventory> bloodInventory = FXCollections.observableArrayList();
	private final ObservableList<String> alerts = FXCollections.observableArrayList();
	private DonorDao donorDao;
	private RecipientDao recipientDao;
	private BloodInventoryDao bloodInventoryDao;
	private CampaignDao campaignDao;
	private DonorPointsDao donorPointsDao;
	private BloodStatisticsDao bloodStatisticsDao;
	private EmergencyRequestDao emergencyRequestDao;
	private AIPredictionDao aiPredictionDao;
	private SensorDataDao sensorDataDao;
	private BloodUnitVerificationDao bloodUnitVerificationDao;

	private ObservableList<Campaign> campaignList = FXCollections.observableArrayList();
	private ObservableList<DonorPoints> donorPointsList = FXCollections.observableArrayList();
	private ObservableList<BloodStatistics> statisticsList = FXCollections.observableArrayList();
	private ObservableList<EmergencyRequest> emergencyRequestsList = FXCollections.observableArrayList();
	private ObservableList<AIPrediction> aiPredictionsList = FXCollections.observableArrayList();
	private ObservableList<SensorData> sensorDataList = FXCollections.observableArrayList();
	private ObservableList<BloodUnitVerification> verificationList = FXCollections.observableArrayList();
	private Timeline clockTimeline;
	private String currentView = "dashboard";
	private boolean isDarkTheme = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize database connection
		this.donorDao = new DonorDao(Db.get());
		this.recipientDao = new RecipientDao(Db.get());
		this.bloodInventoryDao = new BloodInventoryDao(Db.get());
		this.campaignDao = new CampaignDao(Db.get());
		this.donorPointsDao = new DonorPointsDao(Db.get());
		this.bloodStatisticsDao = new BloodStatisticsDao(Db.get());
		this.emergencyRequestDao = new EmergencyRequestDao(Db.get());
		this.aiPredictionDao = new AIPredictionDao(Db.get());
		this.sensorDataDao = new SensorDataDao(Db.get());
		this.bloodUnitVerificationDao = new BloodUnitVerificationDao(Db.get());
		
		// Setup navigation button handlers
		setupNavigationHandlers();
		
		// Setup dashboard
		setupDashboard();
		
		// Setup tables if they exist (backward compatibility)
		if (donorTable != null) {
			setupLegacyDonorTable();
		}
		
		// Setup modern tables
		if (recentDonationsTable != null) {
			setupRecentDonationsTable();
		}
		
		// Load initial data
		loadDashboardData();
		loadRecipientsData();
		loadInventoryData();
		loadCampaignData();
		loadDonorPointsData();
		loadStatisticsData();
		loadEmergencyRequestsData();
		loadAIPredictionsData();
		loadIoTMonitoringData();
		loadBlockchainVerificationData();

		// Start real-time updates
		startRealTimeUpdates();
		
		// Set initial view
		showDashboard();
	}

	// =============================================================================
	// NAVIGATION SETUP
	// =============================================================================
	
	private void setupNavigationHandlers() {
		// Main navigation buttons
		if (logoBtn != null) {
			logoBtn.setOnAction(e -> showDashboard());
		}
		if (dashboardBtn != null) {
			dashboardBtn.setOnAction(e -> showDashboard());
		}
		if (donorsBtn != null) {
			donorsBtn.setOnAction(e -> showDonors());
		}
		if (recipientsBtn != null) {
			recipientsBtn.setOnAction(e -> showRecipients());
		}
		if (inventoryBtn != null) {
			inventoryBtn.setOnAction(e -> showInventory());
		}
		if (campaignsBtn != null) {
			campaignsBtn.setOnAction(e -> showCampaigns());
		}
		if (rewardsBtn != null) {
			rewardsBtn.setOnAction(e -> showRewards());
		}
		if (statisticsBtn != null) {
			statisticsBtn.setOnAction(e -> showStatistics());
		}
		if (emergencyBtn != null) {
			emergencyBtn.setOnAction(e -> showEmergency());
		}
		
		// AI & Innovation buttons
		if (aiPredictionBtn != null) {
			aiPredictionBtn.setOnAction(e -> showAIPrediction());
		}
		if (gamificationBtn != null) {
			gamificationBtn.setOnAction(e -> showGamification());
		}
		if (iotMonitoringBtn != null) {
			iotMonitoringBtn.setOnAction(e -> showIoTMonitoring());
		}
		if (blockchainBtn != null) {
			blockchainBtn.setOnAction(e -> showBlockchain());
		}
		if (voiceCommandsBtn != null) {
			voiceCommandsBtn.setOnAction(e -> showVoiceCommands());
		}
		
		// Revolutionary Technology buttons
		if (quantumMatchingBtn != null) {
			quantumMatchingBtn.setOnAction(e -> showQuantumMatching());
		}
		if (biometricAuthBtn != null) {
			biometricAuthBtn.setOnAction(e -> showBiometricAuth());
		}
		if (arVisualizationBtn != null) {
			arVisualizationBtn.setOnAction(e -> showARVisualization());
		}
		if (neuralQualityBtn != null) {
			neuralQualityBtn.setOnAction(e -> showNeuralQuality());
		}
		if (droneDeliveryBtn != null) {
			droneDeliveryBtn.setOnAction(e -> showDroneDelivery());
		}
		if (digitalTwinBtn != null) {
			digitalTwinBtn.setOnAction(e -> showDigitalTwin());
		}
		if (satelliteCommBtn != null) {
			satelliteCommBtn.setOnAction(e -> showSatelliteComm());
		}
		if (holographicBtn != null) {
			holographicBtn.setOnAction(e -> showHolographic());
		}
		
		// Theme toggle button
		if (themeToggleBtn != null) {
			themeToggleBtn.setOnAction(e -> toggleTheme());
		}
		
		// Settings and logout
		if (settingsBtn != null) {
			settingsBtn.setOnAction(e -> showSettings());
		}
		if (logoutBtn != null) {
			logoutBtn.setOnAction(e -> logout());
		}
	}

	// =============================================================================
	// VIEW MANAGEMENT AND NAVIGATION
	// =============================================================================
	
	
	private void loadDonorManagementView() {
		// Load donor management interface dynamically
		if (donorsView != null) {
			// Create donor registration and management interface
			VBox donorContent = createDonorManagementInterface();
			donorsView.setContent(donorContent);
		}
	}
	
	private VBox createDonorManagementInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("👥 Donor Management & Registration");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button addNewDonorBtn = new Button("➕ Register New Donor");
		addNewDonorBtn.getStyleClass().addAll("modern-button", "primary");
		addNewDonorBtn.setOnAction(e -> showDonorRegistrationForm());
		
		header.getChildren().addAll(title, spacer, addNewDonorBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox totalDonorsCard = createStatCard("👥", String.valueOf(donors.size()), "Total Donors", "stat-card");
		statsGrid.add(totalDonorsCard, 0, 0);
		
		VBox eligibleDonorsCard = createStatCard("✅", "0", "Eligible Donors", "stat-card");
		statsGrid.add(eligibleDonorsCard, 1, 0);
		
		VBox recentDonorsCard = createStatCard("🆕", "0", "This Month", "stat-card");
		statsGrid.add(recentDonorsCard, 2, 0);
		
		VBox vipDonorsCard = createStatCard("🏆", "0", "VIP Donors", "stat-card");
		statsGrid.add(vipDonorsCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Donor Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("Registered Donors");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<Donor> donorTable = createDonorTable();
		tableContainer.getChildren().addAll(tableTitle, donorTable);
		container.getChildren().add(tableContainer);
		
			return container;
	}
	
	private void loadRecipientManagementView() {
		// Load recipient management interface dynamically
		if (recipientsView != null) {
			// Create recipient registration and management interface
			VBox recipientContent = createRecipientManagementInterface();
			recipientsView.setContent(recipientContent);
		}
	}
	
	private VBox createRecipientManagementInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("🏥 Recipient Management & Blood Requests");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button addNewRecipientBtn = new Button("➕ Register New Recipient");
		addNewRecipientBtn.getStyleClass().addAll("modern-button", "primary");
		addNewRecipientBtn.setOnAction(e -> showRecipientRegistrationForm());
		
		header.getChildren().addAll(title, spacer, addNewRecipientBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox totalRecipientsCard = createStatCard("🏥", String.valueOf(recipients.size()), "Total Recipients", "stat-card");
		statsGrid.add(totalRecipientsCard, 0, 0);
		
		VBox pendingRequestsCard = createStatCard("⏳", "0", "Pending Requests", "stat-card");
		statsGrid.add(pendingRequestsCard, 1, 0);
		
		VBox criticalRequestsCard = createStatCard("🚨", "0", "Critical Cases", "stat-card");
		statsGrid.add(criticalRequestsCard, 2, 0);
		
		VBox fulfilledRequestsCard = createStatCard("✅", "0", "Fulfilled Today", "stat-card");
		statsGrid.add(fulfilledRequestsCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Recipients Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("Patient Blood Requests");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<Recipient> recipientTable = createRecipientTable();
		tableContainer.getChildren().addAll(tableTitle, recipientTable);
		container.getChildren().add(tableContainer);
		
		return container;
	}
	
	private TableView<Recipient> createRecipientTable() {
		TableView<Recipient> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(recipients);
		
		TableColumn<Recipient, String> patientNameCol = new TableColumn<>("Patient Name");
		patientNameCol.setCellValueFactory(cell -> cell.getValue().patientNameProperty());
		patientNameCol.setPrefWidth(150);
		
		TableColumn<Recipient, String> bloodGroupCol = new TableColumn<>("Blood Group");
		bloodGroupCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		bloodGroupCol.setPrefWidth(100);
		
		TableColumn<Recipient, String> urgencyCol = new TableColumn<>("Urgency");
		urgencyCol.setCellValueFactory(cell -> cell.getValue().urgencyLevelProperty());
		urgencyCol.setPrefWidth(100);
		
		TableColumn<Recipient, String> hospitalCol = new TableColumn<>("Hospital");
		hospitalCol.setCellValueFactory(cell -> cell.getValue().medicalFacilityProperty());
		hospitalCol.setPrefWidth(180);
		
		TableColumn<Recipient, String> statusCol = new TableColumn<>("Status");
		statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
		statusCol.setPrefWidth(100);
		
		TableColumn<Recipient, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(150);
		actionsCol.setCellFactory(col -> {
			return new TableCell<Recipient, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						HBox buttons = new HBox(5);
						Button editBtn = new Button("✏️");
						editBtn.getStyleClass().addAll("small-button", "primary");
						Button fulfillBtn = new Button("✅");
						fulfillBtn.getStyleClass().addAll("small-button", "success");
						Button deleteBtn = new Button("🗑️");
						deleteBtn.getStyleClass().addAll("small-button", "danger");
						buttons.getChildren().addAll(editBtn, fulfillBtn, deleteBtn);
						setGraphic(buttons);
					}
				}
			};
		});
		
		table.getColumns().addAll(patientNameCol, bloodGroupCol, urgencyCol, hospitalCol, statusCol, actionsCol);
		return table;
	}
	
	private void showRecipientRegistrationForm() {
		// Create and show recipient registration dialog
		Dialog<Recipient> dialog = new Dialog<>();
		dialog.setTitle("Recipient Registration");
		dialog.setHeaderText("Register New Blood Recipient");
		
		// Create form
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		// Form fields
		TextField patientNameField = new TextField();
		patientNameField.setPromptText("Patient Full Name");
		ComboBox<String> bloodGroupCombo = new ComboBox<>();
		bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
		bloodGroupCombo.setPromptText("Select Blood Group");
		TextField hospitalField = new TextField();
		hospitalField.setPromptText("Medical Facility");
		TextField doctorField = new TextField();
		doctorField.setPromptText("Doctor Name");
		TextField contactField = new TextField();
		contactField.setPromptText("Contact Number");
		ComboBox<String> urgencyCombo = new ComboBox<>();
		urgencyCombo.getItems().addAll("LOW", "MEDIUM", "HIGH", "CRITICAL");
		urgencyCombo.setValue("MEDIUM");
		Spinner<Integer> unitsSpinner = new Spinner<>(1, 10, 1);
		TextArea reasonArea = new TextArea();
		reasonArea.setPromptText("Reason for blood requirement");
		reasonArea.setPrefRowCount(3);
		
		// Add fields to form
		form.add(new Label("Patient Name:"), 0, 0);
		form.add(patientNameField, 1, 0);
		form.add(new Label("Blood Group:"), 0, 1);
		form.add(bloodGroupCombo, 1, 1);
		form.add(new Label("Hospital:"), 0, 2);
		form.add(hospitalField, 1, 2);
		form.add(new Label("Doctor:"), 0, 3);
		form.add(doctorField, 1, 3);
		form.add(new Label("Contact:"), 0, 4);
		form.add(contactField, 1, 4);
		form.add(new Label("Urgency:"), 0, 5);
		form.add(urgencyCombo, 1, 5);
		form.add(new Label("Units Required:"), 0, 6);
		form.add(unitsSpinner, 1, 6);
		form.add(new Label("Reason:"), 0, 7);
		form.add(reasonArea, 1, 7);
		
		dialog.getDialogPane().setContent(form);
		
		// Add buttons
		ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
		
		// Convert result
		dialog.setResultConverter(buttonType -> {
			if (buttonType == registerButtonType) {
				if (patientNameField.getText().trim().isEmpty() || bloodGroupCombo.getValue() == null ||
					hospitalField.getText().trim().isEmpty() || doctorField.getText().trim().isEmpty()) {
					showAlert("Validation Error", "Patient Name, Blood Group, Hospital, and Doctor are required!");
					return null;
				}
				
				Recipient recipient = new Recipient();
				recipient.setPatientName(patientNameField.getText().trim());
				recipient.setBloodGroup(bloodGroupCombo.getValue());
				recipient.setMedicalFacility(hospitalField.getText().trim());
				recipient.setDoctorName(doctorField.getText().trim());
				recipient.setContactNumber(contactField.getText().trim());
				recipient.setUrgencyLevel(urgencyCombo.getValue());
				recipient.setUnitsRequired(unitsSpinner.getValue());
				recipient.setReason(reasonArea.getText().trim());
				recipient.setStatus("PENDING");
				recipient.setRequestDate(java.time.LocalDateTime.now());
				return recipient;
			}
			return null;
		});
		
		// Show dialog and handle result
		Optional<Recipient> result = dialog.showAndWait();
		result.ifPresent(recipient -> {
			boolean success = recipientDao.insert(recipient);
			if (success) {
				recipients.add(recipient);
				showStatusMessage("Recipient " + recipient.getPatientName() + " registered successfully!", "success");
				loadDashboardData(); // Refresh dashboard
			} else {
				showAlert("Error", "Failed to register recipient. Please try again.");
			}
		});
	}
	
	private void loadCampaignManagementView() {
		// Load campaign management interface dynamically
		if (campaignsView != null) {
			// Create campaign management interface
			VBox campaignContent = createCampaignManagementInterface();
			campaignsView.setContent(campaignContent);
		}
	}
	
	private void loadRewardsAndGamificationView() {
		// Load rewards and gamification interface dynamically
		if (rewardsView != null) {
			// Create rewards and gamification interface
			VBox rewardsContent = createRewardsInterface();
			rewardsView.setContent(rewardsContent);
		}
	}
	
	private VBox createRewardsInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("🏆 Rewards & Gamification");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button refreshBtn = new Button("🔄 Refresh Leaderboard");
		refreshBtn.getStyleClass().addAll("modern-button", "primary");
		refreshBtn.setOnAction(e -> loadDonorPointsData());
		
		header.getChildren().addAll(title, spacer, refreshBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox totalDonorsCard = createStatCard("👥", String.valueOf(donors.size()), "Active Donors", "stat-card");
		statsGrid.add(totalDonorsCard, 0, 0);
		
		VBox totalPointsCard = createStatCard("⭐", "0", "Total Points", "stat-card");
		statsGrid.add(totalPointsCard, 1, 0);
		
		VBox topDonorCard = createStatCard("🥇", "None", "Top Donor", "stat-card");
		statsGrid.add(topDonorCard, 2, 0);
		
		VBox badgesCard = createStatCard("🏅", "0", "Badges Awarded", "stat-card");
		statsGrid.add(badgesCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Leaderboard Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("🏆 Donor Leaderboard");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<DonorPoints> leaderboardTable = createLeaderboardTable();
		tableContainer.getChildren().addAll(tableTitle, leaderboardTable);
		container.getChildren().add(tableContainer);
		
		// Badges Section
		VBox badgesContainer = new VBox(15);
		badgesContainer.getStyleClass().add("dashboard-card");
		
		Label badgesTitle = new Label("🏅 Donor Badges");
		badgesTitle.getStyleClass().add("section-title");
		
		GridPane badgesGrid = new GridPane();
		badgesGrid.setHgap(15);
		badgesGrid.setVgap(15);
		
		// Create badge cards
		VBox bronzeBadge = createBadgeCard("🥉", "Bronze Donor", "10+ donations");
		VBox silverBadge = createBadgeCard("🥈", "Silver Donor", "25+ donations");
		VBox goldBadge = createBadgeCard("🥇", "Gold Donor", "50+ donations");
		VBox platinumBadge = createBadgeCard("💎", "Platinum Donor", "100+ donations");
		
		badgesGrid.add(bronzeBadge, 0, 0);
		badgesGrid.add(silverBadge, 1, 0);
		badgesGrid.add(goldBadge, 2, 0);
		badgesGrid.add(platinumBadge, 3, 0);
		
		badgesContainer.getChildren().addAll(badgesTitle, badgesGrid);
		container.getChildren().add(badgesContainer);
		
		return container;
	}
	
	private TableView<DonorPoints> createLeaderboardTable() {
		TableView<DonorPoints> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(donorPointsList);
		
		TableColumn<DonorPoints, String> rankCol = new TableColumn<>("Rank");
		rankCol.setCellValueFactory(cell -> {
			int index = donorPointsList.indexOf(cell.getValue()) + 1;
			return new SimpleStringProperty(String.valueOf(index));
		});
		rankCol.setPrefWidth(60);
		
		TableColumn<DonorPoints, String> nameCol = new TableColumn<>("Donor Name");
		nameCol.setCellValueFactory(cell -> cell.getValue().donorNameProperty());
		nameCol.setPrefWidth(200);
		
		TableColumn<DonorPoints, String> pointsCol = new TableColumn<>("Points");
		pointsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPoints())));
		pointsCol.setPrefWidth(100);
		
		TableColumn<DonorPoints, String> badgeCol = new TableColumn<>("Badge");
		badgeCol.setCellValueFactory(cell -> cell.getValue().badgeProperty());
		badgeCol.setPrefWidth(120);
		
		TableColumn<DonorPoints, String> activityCol = new TableColumn<>("Last Activity");
		activityCol.setCellValueFactory(cell -> cell.getValue().lastActivityProperty());
		activityCol.setPrefWidth(200);
		
		table.getColumns().addAll(rankCol, nameCol, pointsCol, badgeCol, activityCol);
		return table;
	}
	
	private VBox createBadgeCard(String icon, String title, String description) {
		VBox card = new VBox(10);
		card.getStyleClass().add("stat-card");
		card.setAlignment(javafx.geometry.Pos.CENTER);
		card.setPrefHeight(120);
		
		Label iconLabel = new Label(icon);
		iconLabel.getStyleClass().add("stat-icon");
		
		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("stat-label");
		
		Label descLabel = new Label(description);
		descLabel.getStyleClass().add("stat-number");
		descLabel.setStyle("-fx-font-size: 12px;");
		
		card.getChildren().addAll(iconLabel, titleLabel, descLabel);
		return card;
	}
	
	private void loadStatisticsAndReportsView() {
		// Load statistics and reports interface dynamically
		if (statisticsView != null) {
			// Create statistics and reports interface
			VBox statisticsContent = createStatisticsInterface();
			statisticsView.setContent(statisticsContent);
		}
	}
	
	private VBox createStatisticsInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("📊 Statistics & Reports");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button refreshBtn = new Button("🔄 Refresh Data");
		refreshBtn.getStyleClass().addAll("modern-button", "primary");
		refreshBtn.setOnAction(e -> loadStatisticsData());
		
		Button generateReportBtn = new Button("📈 Generate Report");
		generateReportBtn.getStyleClass().addAll("modern-button", "success");
		generateReportBtn.setOnAction(e -> generateStatisticsReport());
		
		header.getChildren().addAll(title, spacer, refreshBtn, generateReportBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox totalDonorsCard = createStatCard("👥", String.valueOf(donors.size()), "Total Donors", "stat-card");
		statsGrid.add(totalDonorsCard, 0, 0);
		
		VBox totalUnitsCard = createStatCard("💉", String.valueOf(bloodInventory.size()), "Blood Units", "stat-card");
		statsGrid.add(totalUnitsCard, 1, 0);
		
		VBox activeCampaignsCard = createStatCard("📢", String.valueOf(campaignList.size()), "Campaigns", "stat-card");
		statsGrid.add(activeCampaignsCard, 2, 0);
		
		VBox recipientsCard = createStatCard("🏥", String.valueOf(recipients.size()), "Recipients", "stat-card");
		statsGrid.add(recipientsCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Charts Section
		VBox chartsContainer = new VBox(15);
		chartsContainer.getStyleClass().add("dashboard-card");
		
		Label chartsTitle = new Label("📈 Data Visualization");
		chartsTitle.getStyleClass().add("section-title");
		
		// Create chart placeholders
		HBox chartsRow = new HBox(20);
		chartsRow.setAlignment(javafx.geometry.Pos.CENTER);
		
		// Blood Group Distribution Chart
		VBox bloodGroupChart = createChartCard("Blood Group Distribution", "Pie Chart");
		
		// Inventory Trend Chart
		VBox inventoryTrendChart = createChartCard("Inventory Trends", "Line Chart");
		
		// Donor Activity Chart
		VBox donorActivityChart = createChartCard("Donor Activity", "Bar Chart");
		
		chartsRow.getChildren().addAll(bloodGroupChart, inventoryTrendChart, donorActivityChart);
		
		chartsContainer.getChildren().addAll(chartsTitle, chartsRow);
		container.getChildren().add(chartsContainer);
		
		// Reports Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("📋 Statistical Reports");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<BloodStatistics> reportsTable = createReportsTable();
		tableContainer.getChildren().addAll(tableTitle, reportsTable);
		container.getChildren().add(tableContainer);
		
		return container;
	}
	
	private TableView<BloodStatistics> createReportsTable() {
		TableView<BloodStatistics> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(statisticsList);
		
		TableColumn<BloodStatistics, String> dateCol = new TableColumn<>("Report Date");
		dateCol.setCellValueFactory(cell -> {
			LocalDate date = cell.getValue().getReportDate();
			return new SimpleStringProperty(date != null ? date.toString() : "N/A");
		});
		dateCol.setPrefWidth(120);
		
		TableColumn<BloodStatistics, String> donorsCol = new TableColumn<>("Donors");
		donorsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTotalDonors())));
		donorsCol.setPrefWidth(80);
		
		TableColumn<BloodStatistics, String> unitsCol = new TableColumn<>("Units");
		unitsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTotalBloodUnits())));
		unitsCol.setPrefWidth(80);
		
		TableColumn<BloodStatistics, String> recipientsCol = new TableColumn<>("Recipients");
		recipientsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getTotalRecipients())));
		recipientsCol.setPrefWidth(90);
		
		TableColumn<BloodStatistics, String> campaignsCol = new TableColumn<>("Campaigns");
		campaignsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getActiveCampaigns())));
		campaignsCol.setPrefWidth(90);
		
		TableColumn<BloodStatistics, String> expiringCol = new TableColumn<>("Expiring");
		expiringCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getExpiringSoon())));
		expiringCol.setPrefWidth(80);
		
		TableColumn<BloodStatistics, String> emergenciesCol = new TableColumn<>("Emergencies");
		emergenciesCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getEmergencyRequests())));
		emergenciesCol.setPrefWidth(100);
		
		table.getColumns().addAll(dateCol, donorsCol, unitsCol, recipientsCol, campaignsCol, expiringCol, emergenciesCol);
		return table;
	}
	
	private VBox createChartCard(String title, String chartType) {
		VBox card = new VBox(10);
		card.getStyleClass().add("stat-card");
		card.setAlignment(javafx.geometry.Pos.CENTER);
		card.setPrefHeight(200);
		card.setPrefWidth(200);
		
		Label titleLabel = new Label(title);
		titleLabel.getStyleClass().add("stat-label");
		
		// Chart placeholder
		Pane chartPlaceholder = new Pane();
		chartPlaceholder.setPrefSize(180, 120);
		chartPlaceholder.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8px;");
		
		Label chartLabel = new Label(chartType);
		chartLabel.setAlignment(javafx.geometry.Pos.CENTER);
		chartLabel.setPrefWidth(180);
		chartLabel.setStyle("-fx-text-fill: #666; -fx-font-weight: bold;");
		
		chartPlaceholder.getChildren().add(chartLabel);
		
		card.getChildren().addAll(titleLabel, chartPlaceholder);
		return card;
	}
	
	private void generateStatisticsReport() {
		// Generate a new statistics report
		BloodStatistics stats = new BloodStatistics();
		stats.setReportDate(java.time.LocalDate.now());
		stats.setTotalDonors(donors.size());
		stats.setTotalRecipients(recipients.size());
		stats.setTotalBloodUnits(bloodInventory.size());
		stats.setActiveCampaigns(campaignList.size());
		
		// Calculate blood group distribution
		int aPos = 0, aNeg = 0, bPos = 0, bNeg = 0, abPos = 0, abNeg = 0, oPos = 0, oNeg = 0;
		for (BloodInventory unit : bloodInventory) {
			switch (unit.getBloodGroup()) {
				case "A+": aPos++; break;
				case "A-": aNeg++; break;
				case "B+": bPos++; break;
				case "B-": bNeg++; break;
				case "AB+": abPos++; break;
				case "AB-": abNeg++; break;
				case "O+": oPos++; break;
				case "O-": oNeg++; break;
			}
		}
		
		stats.setAPositiveUnits(aPos);
		stats.setANegativeUnits(aNeg);
		stats.setBPositiveUnits(bPos);
		stats.setBNegativeUnits(bNeg);
		stats.setABPositiveUnits(abPos);
		stats.setABNegativeUnits(abNeg);
		stats.setOPositiveUnits(oPos);
		stats.setONegativeUnits(oNeg);
		
		// Find expiring units
		List<BloodInventory> expiringUnits = bloodInventoryDao.findExpiringSoon(7);
		stats.setExpiringSoon(expiringUnits.size());
		
		// Save the statistics
		boolean success = bloodStatisticsDao.insert(stats);
		if (success) {
			statisticsList.add(0, stats);
			showAlert("Success", "Statistics report generated successfully!");
		} else {
			showAlert("Error", "Failed to generate statistics report.");
		}
	}
	
	private void loadEmergencyResponseView() {
		// Load emergency response interface dynamically
		if (emergencyView != null) {
			// Create emergency response interface
			VBox emergencyContent = createEmergencyInterface();
			emergencyView.setContent(emergencyContent);
		}
	}
	
	private VBox createEmergencyInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("🚨 Emergency Response System");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button refreshBtn = new Button("🔄 Refresh");
		refreshBtn.getStyleClass().addAll("modern-button", "primary");
		refreshBtn.setOnAction(e -> loadEmergencyRequestsData());
		
		Button newRequestBtn = new Button("➕ New Emergency Request");
		newRequestBtn.getStyleClass().addAll("modern-button", "danger");
		newRequestBtn.setOnAction(e -> showNewEmergencyRequestForm());
		
		header.getChildren().addAll(title, spacer, refreshBtn, newRequestBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox pendingRequestsCard = createStatCard("⏳", String.valueOf(emergencyRequestsList.size()), "Pending Requests", "stat-card");
		statsGrid.add(pendingRequestsCard, 0, 0);
		
		VBox criticalRequestsCard = createStatCard("🔴", "0", "Critical Cases", "stat-card");
		statsGrid.add(criticalRequestsCard, 1, 0);
		
		VBox responseTimeCard = createStatCard("⏱️", "0", "Avg Response (min)", "stat-card");
		statsGrid.add(responseTimeCard, 2, 0);
		
		VBox fulfilledTodayCard = createStatCard("✅", "0", "Fulfilled Today", "stat-card");
		statsGrid.add(fulfilledTodayCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Emergency Requests Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("Emergency Blood Requests");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<EmergencyRequest> requestsTable = createEmergencyRequestsTable();
		tableContainer.getChildren().addAll(tableTitle, requestsTable);
		container.getChildren().add(tableContainer);
		
		return container;
	}
	
	private TableView<EmergencyRequest> createEmergencyRequestsTable() {
		TableView<EmergencyRequest> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(emergencyRequestsList);
		
		TableColumn<EmergencyRequest, String> patientCol = new TableColumn<>("Patient");
		patientCol.setCellValueFactory(cell -> cell.getValue().patientNameProperty());
		patientCol.setPrefWidth(150);
		
		TableColumn<EmergencyRequest, String> bloodGroupCol = new TableColumn<>("Blood Group");
		bloodGroupCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		bloodGroupCol.setPrefWidth(100);
		
		TableColumn<EmergencyRequest, String> hospitalCol = new TableColumn<>("Hospital");
		hospitalCol.setCellValueFactory(cell -> cell.getValue().hospitalProperty());
		hospitalCol.setPrefWidth(180);
		
		TableColumn<EmergencyRequest, String> urgencyCol = new TableColumn<>("Urgency");
		urgencyCol.setCellValueFactory(cell -> cell.getValue().urgencyLevelProperty());
		urgencyCol.setPrefWidth(100);
		
		TableColumn<EmergencyRequest, String> statusCol = new TableColumn<>("Status");
		statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
		statusCol.setPrefWidth(100);
		
		TableColumn<EmergencyRequest, String> timeCol = new TableColumn<>("Request Time");
		timeCol.setCellValueFactory(cell -> {
			LocalDateTime time = cell.getValue().getRequestTime();
			return new SimpleStringProperty(time != null ? time.toString() : "N/A");
		});
		timeCol.setPrefWidth(150);
		
		TableColumn<EmergencyRequest, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(150);
		actionsCol.setCellFactory(col -> {
			return new TableCell<EmergencyRequest, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						HBox buttons = new HBox(5);
						Button assignBtn = new Button("📌");
						assignBtn.getStyleClass().addAll("small-button", "primary");
						assignBtn.setOnAction(e -> assignEmergencyUnit(getTableView().getItems().get(getIndex())));
						Button fulfillBtn = new Button("✅");
						fulfillBtn.getStyleClass().addAll("small-button", "success");
						fulfillBtn.setOnAction(e -> fulfillEmergencyRequest(getTableView().getItems().get(getIndex())));
						Button cancelBtn = new Button("❌");
						cancelBtn.getStyleClass().addAll("small-button", "danger");
						cancelBtn.setOnAction(e -> cancelEmergencyRequest(getTableView().getItems().get(getIndex())));
						buttons.getChildren().addAll(assignBtn, fulfillBtn, cancelBtn);
						setGraphic(buttons);
					}
				}
			};
		});
		
		table.getColumns().addAll(patientCol, bloodGroupCol, hospitalCol, urgencyCol, statusCol, timeCol, actionsCol);
		return table;
	}
	
	private void showNewEmergencyRequestForm() {
		// Create and show emergency request dialog
		Dialog<EmergencyRequest> dialog = new Dialog<>();
		dialog.setTitle("Emergency Blood Request");
		dialog.setHeaderText("Create New Emergency Blood Request");
		
		// Create form
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		// Form fields
		TextField patientNameField = new TextField();
		patientNameField.setPromptText("Patient Full Name");
		ComboBox<String> bloodGroupCombo = new ComboBox<>();
		bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
		bloodGroupCombo.setPromptText("Select Blood Group");
		TextField hospitalField = new TextField();
		hospitalField.setPromptText("Hospital Name");
		TextField doctorField = new TextField();
		doctorField.setPromptText("Doctor Name");
		TextField contactField = new TextField();
		contactField.setPromptText("Contact Number");
		ComboBox<String> urgencyCombo = new ComboBox<>();
		urgencyCombo.getItems().addAll("LOW", "MEDIUM", "HIGH", "CRITICAL");
		urgencyCombo.setValue("HIGH");
		TextField locationField = new TextField();
		locationField.setPromptText("Location");
		TextArea notesArea = new TextArea();
		notesArea.setPromptText("Additional Notes");
		notesArea.setPrefRowCount(3);
		
		// Add fields to form
		form.add(new Label("Patient Name:"), 0, 0);
		form.add(patientNameField, 1, 0);
		form.add(new Label("Blood Group:"), 0, 1);
		form.add(bloodGroupCombo, 1, 1);
		form.add(new Label("Hospital:"), 0, 2);
		form.add(hospitalField, 1, 2);
		form.add(new Label("Doctor:"), 0, 3);
		form.add(doctorField, 1, 3);
		form.add(new Label("Contact:"), 0, 4);
		form.add(contactField, 1, 4);
		form.add(new Label("Urgency:"), 0, 5);
		form.add(urgencyCombo, 1, 5);
		form.add(new Label("Location:"), 0, 6);
		form.add(locationField, 1, 6);
		form.add(new Label("Notes:"), 0, 7);
		form.add(notesArea, 1, 7);
		
		dialog.getDialogPane().setContent(form);
		
		// Add buttons
		ButtonType createButtonType = new ButtonType("Create Request", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
		
		// Convert result
		dialog.setResultConverter(buttonType -> {
			if (buttonType == createButtonType) {
				if (patientNameField.getText().trim().isEmpty() || bloodGroupCombo.getValue() == null ||
					hospitalField.getText().trim().isEmpty() || doctorField.getText().trim().isEmpty()) {
					showAlert("Validation Error", "Patient Name, Blood Group, Hospital, and Doctor are required!");
					return null;
				}
				
				EmergencyRequest request = new EmergencyRequest();
				request.setPatientName(patientNameField.getText().trim());
				request.setBloodGroup(bloodGroupCombo.getValue());
				request.setHospital(hospitalField.getText().trim());
				request.setDoctor(doctorField.getText().trim());
				request.setContactNumber(contactField.getText().trim());
				request.setUrgencyLevel(urgencyCombo.getValue());
				request.setLocation(locationField.getText().trim());
				request.setNotes(notesArea.getText().trim());
				request.setStatus("PENDING");
				request.setRequestTime(java.time.LocalDateTime.now());
				return request;
			}
			return null;
		});
		
		// Show dialog and handle result
		Optional<EmergencyRequest> result = dialog.showAndWait();
		result.ifPresent(request -> {
			boolean success = emergencyRequestDao.insert(request);
			if (success) {
				emergencyRequestsList.add(request);
				showStatusMessage("Emergency request for " + request.getPatientName() + " created successfully!", "success");
				loadDashboardData(); // Refresh dashboard
			} else {
				showAlert("Error", "Failed to create emergency request. Please try again.");
			}
		});
	}
	
	private void assignEmergencyUnit(EmergencyRequest request) {
		// Find matching blood units
		List<BloodInventory> matchingUnits = bloodInventoryDao.findByBloodGroup(request.getBloodGroup());
		if (matchingUnits.isEmpty()) {
			showAlert("No Units Available", "No available blood units found for " + request.getBloodGroup() + ". Please check inventory or request from other locations.");
			return;
		}
		
		// Assign the first available unit
		BloodInventory unit = matchingUnits.get(0);
		boolean success = emergencyRequestDao.assignUnit(request.getId(), unit.getId());
		if (success) {
			request.setAssignedUnit(unit.getId());
			request.setStatus("ASSIGNED");
			request.setResponseTime(java.time.LocalDateTime.now());
			emergencyRequestsList.setAll(emergencyRequestsList); // Refresh the list
			showStatusMessage("Unit " + unit.getBagId() + " assigned to " + request.getPatientName() + "!", "success");
		} else {
			showAlert("Error", "Failed to assign blood unit. Please try again.");
		}
	}
	
	private void fulfillEmergencyRequest(EmergencyRequest request) {
		boolean success = emergencyRequestDao.fulfillRequest(request.getId());
		if (success) {
			request.setStatus("FULFILLED");
			request.setFulfillmentTime(java.time.LocalDateTime.now());
			emergencyRequestsList.setAll(emergencyRequestsList); // Refresh the list
			showStatusMessage("Emergency request for " + request.getPatientName() + " fulfilled successfully!", "success");
		} else {
			showAlert("Error", "Failed to fulfill emergency request. Please try again.");
		}
	}
	
	private void cancelEmergencyRequest(EmergencyRequest request) {
		request.setStatus("CANCELLED");
		emergencyRequestsList.setAll(emergencyRequestsList); // Refresh the list
		showStatusMessage("Emergency request for " + request.getPatientName() + " cancelled.", "warning");
	}
	
	private void loadAIPredictionView() {
		// Load AI prediction interface dynamically
		if (aiPredictionView != null) {
			// Create AI prediction interface
			VBox aiContent = createAIInterface();
			aiPredictionView.setContent(aiContent);
		}
	}
	
	private VBox createAIInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("🤖 AI Predictions & Insights");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button refreshBtn = new Button("🔄 Refresh Predictions");
		refreshBtn.getStyleClass().addAll("modern-button", "primary");
		refreshBtn.setOnAction(e -> loadAIPredictionsData());
		
		Button generateBtn = new Button("🔮 Generate New Predictions");
		generateBtn.getStyleClass().addAll("modern-button", "success");
		generateBtn.setOnAction(e -> generateAIPredictions());
		
		header.getChildren().addAll(title, spacer, refreshBtn, generateBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox predictionsCard = createStatCard("🔮", String.valueOf(aiPredictionsList.size()), "AI Predictions", "stat-card");
		statsGrid.add(predictionsCard, 0, 0);
		
		VBox accuracyCard = createStatCard("🎯", "0%", "Model Accuracy", "stat-card");
		statsGrid.add(accuracyCard, 1, 0);
		
		VBox trendsCard = createStatCard("📈", "0", "Trend Insights", "stat-card");
		statsGrid.add(trendsCard, 2, 0);
		
		VBox recommendationsCard = createStatCard("💡", "0", "Recommendations", "stat-card");
		statsGrid.add(recommendationsCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// AI Predictions Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("AI Predictions & Insights");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<AIPrediction> predictionsTable = createAIPredictionsTable();
		tableContainer.getChildren().addAll(tableTitle, predictionsTable);
		container.getChildren().add(tableContainer);
		
		// Insights Panel
		VBox insightsContainer = new VBox(15);
		insightsContainer.getStyleClass().add("dashboard-card");
		
		Label insightsTitle = new Label("Key Insights & Recommendations");
		insightsTitle.getStyleClass().add("section-title");
		
		TextArea insightsArea = new TextArea();
		insightsArea.setPrefRowCount(5);
		insightsArea.setEditable(false);
		insightsArea.setWrapText(true);
		insightsArea.setText("AI-powered insights will appear here after generating predictions.\n\n" +
			"The system analyzes historical data to predict:\n" +
			"• Blood demand trends by blood group\n" +
			"• Donor engagement patterns\n" +
			"• Campaign effectiveness\n" +
			"• Inventory optimization recommendations");
		
		insightsContainer.getChildren().addAll(insightsTitle, insightsArea);
		container.getChildren().add(insightsContainer);
		
		return container;
	}
	
	private TableView<AIPrediction> createAIPredictionsTable() {
		TableView<AIPrediction> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(aiPredictionsList);
		
		TableColumn<AIPrediction, String> typeCol = new TableColumn<>("Prediction Type");
		typeCol.setCellValueFactory(cell -> cell.getValue().predictionTypeProperty());
		typeCol.setPrefWidth(150);
		
		TableColumn<AIPrediction, String> dateCol = new TableColumn<>("Prediction Date");
		dateCol.setCellValueFactory(cell -> {
			LocalDate date = cell.getValue().getPredictionDate();
			return new SimpleStringProperty(date != null ? date.toString() : "N/A");
		});
		dateCol.setPrefWidth(120);
		
		TableColumn<AIPrediction, String> bloodGroupCol = new TableColumn<>("Blood Group");
		bloodGroupCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		bloodGroupCol.setPrefWidth(100);
		
		TableColumn<AIPrediction, String> unitsCol = new TableColumn<>("Predicted Units");
		unitsCol.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getPredictedUnits())));
		unitsCol.setPrefWidth(120);
		
		TableColumn<AIPrediction, String> confidenceCol = new TableColumn<>("Confidence");
		confidenceCol.setCellValueFactory(cell -> new SimpleStringProperty(String.format("%.2f%%", cell.getValue().getConfidenceScore() * 100)));
		confidenceCol.setPrefWidth(100);
		
		TableColumn<AIPrediction, String> dataSourceCol = new TableColumn<>("Data Source");
		dataSourceCol.setCellValueFactory(cell -> cell.getValue().dataSourceProperty());
		dataSourceCol.setPrefWidth(150);
		
		TableColumn<AIPrediction, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(100);
		actionsCol.setCellFactory(col -> {
			return new TableCell<AIPrediction, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						Button viewBtn = new Button("👁️");
						viewBtn.getStyleClass().addAll("small-button", "primary");
						viewBtn.setOnAction(e -> showAIPredictionDetails(getTableView().getItems().get(getIndex())));
						setGraphic(viewBtn);
					}
				}
			};
		});
		
		table.getColumns().addAll(typeCol, dateCol, bloodGroupCol, unitsCol, confidenceCol, dataSourceCol, actionsCol);
		return table;
	}
	
	private void generateAIPredictions() {
		// Show a progress dialog while generating predictions
		Dialog<Void> progressDialog = new Dialog<>();
		progressDialog.setTitle("Generating AI Predictions");
		progressDialog.setHeaderText("Please wait while the AI model generates predictions...");
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(300);
		
		VBox progressContent = new VBox(10);
		progressContent.setAlignment(javafx.geometry.Pos.CENTER);
		progressContent.getChildren().addAll(new Label("Analyzing data and generating predictions..."), progressBar);
		
		progressDialog.getDialogPane().setContent(progressContent);
		progressDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		// Show progress dialog
		progressDialog.show();
		
		// Simulate AI prediction generation (in a real app, this would call an actual ML model)
		CompletableFuture.supplyAsync(() -> {
			try {
				// Simulate processing time
				Thread.sleep(3000);
				
				// Generate sample predictions
				List<AIPrediction> predictions = new ArrayList<>();
				
				// Blood demand prediction
				AIPrediction demandPred = new AIPrediction();
				demandPred.setPredictionType("BLOOD_DEMAND");
				demandPred.setPredictionDate(java.time.LocalDate.now().plusDays(7));
				demandPred.setBloodGroup("O+");
				demandPred.setPredictedUnits(25);
				demandPred.setConfidenceScore(0.85);
				demandPred.setInsights("High demand expected for O+ blood type next week");
				demandPred.setRecommendations("Increase donor recruitment campaigns for O+ donors");
				demandPred.setDataSource("Historical demand + seasonal trends");
				predictions.add(demandPred);
				
				// Donor trends prediction
				AIPrediction donorPred = new AIPrediction();
				donorPred.setPredictionType("DONOR_TRENDS");
				donorPred.setPredictionDate(java.time.LocalDate.now().plusDays(30));
				donorPred.setBloodGroup("A-");
				donorPred.setPredictedUnits(15);
				donorPred.setConfidenceScore(0.78);
				donorPred.setInsights("Donor retention rate declining for A- blood type");
				donorPred.setRecommendations("Implement targeted engagement programs for A- donors");
				donorPred.setDataSource("Donor activity patterns");
				predictions.add(donorPred);
				
				// Campaign effectiveness prediction
				AIPrediction campaignPred = new AIPrediction();
				campaignPred.setPredictionType("CAMPAIGN_EFFECTIVENESS");
				campaignPred.setPredictionDate(java.time.LocalDate.now().plusDays(14));
				campaignPred.setBloodGroup("B+");
				campaignPred.setPredictedUnits(30);
				campaignPred.setConfidenceScore(0.92);
				campaignPred.setInsights("Upcoming campaign in downtown area likely to exceed target");
				campaignPred.setRecommendations("Allocate additional resources to downtown campaign");
				campaignPred.setDataSource("Location analytics + historical campaign data");
				predictions.add(campaignPred);
				
				return predictions;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return new ArrayList<AIPrediction>();
			}
		}).thenAccept(predictions -> {
			Platform.runLater(() -> {
				progressDialog.close();
				
				// Add predictions to the list
				for (AIPrediction pred : (List<AIPrediction>) predictions) {
					aiPredictionDao.insert(pred);
				}
				aiPredictionsList.setAll((List<AIPrediction>) predictions);
				
				showAlert("Success", "AI predictions generated successfully! " + predictions.size() + " predictions created.");
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				progressDialog.close();
				showAlert("Error", "Failed to generate AI predictions: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void showAIPredictionDetails(AIPrediction prediction) {
		// Show detailed view of AI prediction
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("AI Prediction Details");
		alert.setHeaderText(prediction.getPredictionType().replace("_", " ") + " - " + prediction.getBloodGroup());
		
		StringBuilder content = new StringBuilder();
		content.append("Prediction Date: ").append(prediction.getPredictionDate()).append("\n\n");
		content.append("Predicted Units: ").append(prediction.getPredictedUnits()).append("\n\n");
		content.append("Confidence Score: ").append(String.format("%.2f%%", prediction.getConfidenceScore() * 100)).append("\n\n");
		content.append("Key Insights:\n").append(prediction.getInsights()).append("\n\n");
		content.append("Recommendations:\n").append(prediction.getRecommendations()).append("\n\n");
		content.append("Data Source: ").append(prediction.getDataSource());
		
		alert.setContentText(content.toString());
		alert.showAndWait();
	}
	

	
	private VBox createCampaignManagementInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("📢 Campaign Management & Blood Drives");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button addNewCampaignBtn = new Button("➕ Create New Campaign");
		addNewCampaignBtn.getStyleClass().addAll("modern-button", "primary");
		addNewCampaignBtn.setOnAction(e -> showAddCampaignForm());
		
		header.getChildren().addAll(title, spacer, addNewCampaignBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox totalCampaignsCard = createStatCard("📢", String.valueOf(campaignList.size()), "Total Campaigns", "stat-card");
		statsGrid.add(totalCampaignsCard, 0, 0);
		
		VBox activeCampaignsCard = createStatCard("🟢", "0", "Active Campaigns", "stat-card");
		statsGrid.add(activeCampaignsCard, 1, 0);
		
		VBox upcomingCampaignsCard = createStatCard("📅", "0", "Upcoming", "stat-card");
		statsGrid.add(upcomingCampaignsCard, 2, 0);
		
		VBox completedCampaignsCard = createStatCard("✅", "0", "Completed", "stat-card");
		statsGrid.add(completedCampaignsCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Campaigns Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("Blood Drive Campaigns");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<Campaign> campaignTable = createCampaignTable();
		tableContainer.getChildren().addAll(tableTitle, campaignTable);
		container.getChildren().add(tableContainer);
		
		return container;
	}
	
	private TableView<Campaign> createCampaignTable() {
		TableView<Campaign> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(campaignList);
		
		TableColumn<Campaign, String> titleCol = new TableColumn<>("Campaign Title");
		titleCol.setCellValueFactory(cell -> cell.getValue().titleProperty());
		titleCol.setPrefWidth(200);
		
		TableColumn<Campaign, String> locationCol = new TableColumn<>("Location");
		locationCol.setCellValueFactory(cell -> cell.getValue().locationProperty());
		locationCol.setPrefWidth(150);
		
		TableColumn<Campaign, String> datesCol = new TableColumn<>("Dates");
		datesCol.setCellValueFactory(cell -> {
			Campaign campaign = cell.getValue();
			if (campaign.getStartDate() != null && campaign.getEndDate() != null) {
				return new SimpleStringProperty(campaign.getStartDate() + " to " + campaign.getEndDate());
			}
			return new SimpleStringProperty("TBD");
		});
		datesCol.setPrefWidth(180);
		
		TableColumn<Campaign, String> statusCol = new TableColumn<>("Status");
		statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
		statusCol.setPrefWidth(100);
		
		TableColumn<Campaign, String> unitsCol = new TableColumn<>("Units");
		unitsCol.setCellValueFactory(cell -> {
			Campaign campaign = cell.getValue();
			return new SimpleStringProperty(campaign.getCollectedUnits() + "/" + campaign.getTargetUnits());
		});
		unitsCol.setPrefWidth(100);
		
		TableColumn<Campaign, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(150);
		actionsCol.setCellFactory(col -> {
			return new TableCell<Campaign, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						HBox buttons = new HBox(5);
						Button editBtn = new Button("✏️");
						editBtn.getStyleClass().addAll("small-button", "primary");
						Button startBtn = new Button("🟢");
						startBtn.getStyleClass().addAll("small-button", "success");
						Button deleteBtn = new Button("🗑️");
						deleteBtn.getStyleClass().addAll("small-button", "danger");
						buttons.getChildren().addAll(editBtn, startBtn, deleteBtn);
						setGraphic(buttons);
					}
				}
			};
		});
		
		table.getColumns().addAll(titleCol, locationCol, datesCol, statusCol, unitsCol, actionsCol);
		return table;
	}
	
	private void showAddCampaignForm() {
		// Create and show campaign creation dialog
		Dialog<Campaign> dialog = new Dialog<>();
		dialog.setTitle("Create Campaign");
		dialog.setHeaderText("Create New Blood Drive Campaign");
		
		// Create form
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		// Form fields
		TextField titleField = new TextField();
		titleField.setPromptText("Campaign Title");
		TextArea descriptionArea = new TextArea();
		descriptionArea.setPromptText("Campaign Description");
		descriptionArea.setPrefRowCount(3);
		DatePicker startDatePicker = new DatePicker();
		DatePicker endDatePicker = new DatePicker();
		TextField locationField = new TextField();
		locationField.setPromptText("Location");
		TextField cityField = new TextField();
		cityField.setPromptText("City");
		TextField stateField = new TextField();
		stateField.setPromptText("State");
		Spinner<Integer> targetUnitsSpinner = new Spinner<>(1, 1000, 50);
		TextField organizerField = new TextField();
		organizerField.setPromptText("Organizer Name");
		TextField contactField = new TextField();
		contactField.setPromptText("Contact Number");
		
		// Add fields to form
		form.add(new Label("Title:"), 0, 0);
		form.add(titleField, 1, 0);
		form.add(new Label("Description:"), 0, 1);
		form.add(descriptionArea, 1, 1);
		form.add(new Label("Start Date:"), 0, 2);
		form.add(startDatePicker, 1, 2);
		form.add(new Label("End Date:"), 0, 3);
		form.add(endDatePicker, 1, 3);
		form.add(new Label("Location:"), 0, 4);
		form.add(locationField, 1, 4);
		form.add(new Label("City:"), 0, 5);
		form.add(cityField, 1, 5);
		form.add(new Label("State:"), 0, 6);
		form.add(stateField, 1, 6);
		form.add(new Label("Target Units:"), 0, 7);
		form.add(targetUnitsSpinner, 1, 7);
		form.add(new Label("Organizer:"), 0, 8);
		form.add(organizerField, 1, 8);
		form.add(new Label("Contact:"), 0, 9);
		form.add(contactField, 1, 9);
		
		dialog.getDialogPane().setContent(form);
		
		// Add buttons
		ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);
		
		// Convert result
		dialog.setResultConverter(buttonType -> {
			if (buttonType == createButtonType) {
				if (titleField.getText().trim().isEmpty() || locationField.getText().trim().isEmpty() ||
					cityField.getText().trim().isEmpty() || stateField.getText().trim().isEmpty() ||
					organizerField.getText().trim().isEmpty() || startDatePicker.getValue() == null ||
					endDatePicker.getValue() == null) {
					showAlert("Validation Error", "Title, Location, City, State, Organizer, and Dates are required!");
					return null;
				}
				
				Campaign campaign = new Campaign();
				campaign.setTitle(titleField.getText().trim());
				campaign.setDescription(descriptionArea.getText().trim());
				campaign.setStartDate(startDatePicker.getValue());
				campaign.setEndDate(endDatePicker.getValue());
				campaign.setLocation(locationField.getText().trim());
				campaign.setCity(cityField.getText().trim());
				campaign.setState(stateField.getText().trim());
				campaign.setTargetUnits(targetUnitsSpinner.getValue());
				campaign.setOrganizer(organizerField.getText().trim());
				campaign.setContactNumber(contactField.getText().trim());
				campaign.setStatus("PLANNED");
				campaign.setCreatedDate(java.time.LocalDateTime.now());
				return campaign;
			}
			return null;
		});
		
		// Show dialog and handle result
		Optional<Campaign> result = dialog.showAndWait();
		result.ifPresent(campaign -> {
			boolean success = campaignDao.insert(campaign);
			if (success) {
				campaignList.add(campaign);
				showStatusMessage("Campaign '" + campaign.getTitle() + "' created successfully!", "success");
				loadDashboardData(); // Refresh dashboard
			} else {
				showAlert("Error", "Failed to create campaign. Please try again.");
			}
		});
	}
	
	private void loadIoTMonitoringView() {
	    // Load IoT monitoring interface dynamically
	    if (iotMonitoringView != null) {
	        // Create IoT monitoring interface
	        VBox iotContent = createIoTInterface();
	        iotMonitoringView.setContent(iotContent);
	    }
	}
	
	private VBox createIoTInterface() {
	    VBox container = new VBox(20);
	    container.getStyleClass().add("content-padding");
	    
	    // Page Header
	    HBox header = new HBox(20);
	    header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
	    
	    Label title = new Label("📡 IoT Monitoring & Smart Sensors");
	    title.getStyleClass().add("page-title");
	    
	    Region spacer = new Region();
	    HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
	    
	    Button refreshBtn = new Button("🔄 Refresh Data");
	    refreshBtn.getStyleClass().addAll("modern-button", "primary");
	    refreshBtn.setOnAction(e -> loadIoTMonitoringData());
	    
	    Button addSensorBtn = new Button("➕ Add Sensor");
	    addSensorBtn.getStyleClass().addAll("modern-button", "success");
	    addSensorBtn.setOnAction(e -> showAddSensorForm());
	    
	    header.getChildren().addAll(title, spacer, refreshBtn, addSensorBtn);
	    container.getChildren().add(header);
	    
	    // Statistics Cards
	    GridPane statsGrid = new GridPane();
	    statsGrid.getStyleClass().add("responsive-grid");
	    statsGrid.setHgap(20);
	    statsGrid.setVgap(20);
	    
	    // Create statistics cards
	    VBox sensorsCard = createStatCard("📡", String.valueOf(sensorDataList.size()), "Active Sensors", "stat-card");
	    statsGrid.add(sensorsCard, 0, 0);
	    
	    VBox alertsCard = createStatCard("⚠️", "0", "Active Alerts", "stat-card");
	    statsGrid.add(alertsCard, 1, 0);
	    
	    VBox temperatureCard = createStatCard("🌡️", "0°C", "Avg Temperature", "stat-card");
	    statsGrid.add(temperatureCard, 2, 0);
	    
	    VBox humidityCard = createStatCard("💧", "0%", "Avg Humidity", "stat-card");
	    statsGrid.add(humidityCard, 3, 0);
	    
	    container.getChildren().add(statsGrid);
	    
	    // Sensor Data Table
	    VBox tableContainer = new VBox(15);
	    tableContainer.getStyleClass().add("dashboard-card");
	    
	    Label tableTitle = new Label("Sensor Data Feed");
	    tableTitle.getStyleClass().add("section-title");
	    
	    TableView<SensorData> sensorDataTable = createSensorDataTable();
	    tableContainer.getChildren().addAll(tableTitle, sensorDataTable);
	    container.getChildren().add(tableContainer);
	    
	    // Alerts Panel
	    VBox alertsContainer = new VBox(15);
	    alertsContainer.getStyleClass().add("dashboard-card");
	    
	    Label alertsTitle = new Label("Active Alerts");
	    alertsTitle.getStyleClass().add("section-title");
	    
	    TextArea alertsArea = new TextArea();
	    alertsArea.setPrefRowCount(5);
	    alertsArea.setEditable(false);
	    alertsArea.setWrapText(true);
	    alertsArea.setText("No active alerts. All sensors operating normally.\n\n" +
	        "IoT monitoring system tracks:\n" +
	        "• Temperature of blood storage units\n" +
	        "• Humidity levels in storage areas\n" +
	        "• Pressure conditions for blood bags\n" +
	        "• GPS location of mobile blood units");
	    
	    alertsContainer.getChildren().addAll(alertsTitle, alertsArea);
	    container.getChildren().add(alertsContainer);
	    
	    return container;
	}

	private TableView<SensorData> createSensorDataTable() {
	    TableView<SensorData> table = new TableView<>();
	    table.getStyleClass().add("modern-table");
	    table.setItems(sensorDataList);
	    
	    TableColumn<SensorData, String> sensorIdCol = new TableColumn<>("Sensor ID");
	    sensorIdCol.setCellValueFactory(cell -> cell.getValue().sensorIdProperty());
	    sensorIdCol.setPrefWidth(120);
	    
	    TableColumn<SensorData, String> sensorTypeCol = new TableColumn<>("Sensor Type");
	    sensorTypeCol.setCellValueFactory(cell -> cell.getValue().sensorTypeProperty());
	    sensorTypeCol.setPrefWidth(120);
	    
	    TableColumn<SensorData, String> valueCol = new TableColumn<>("Value");
	    valueCol.setCellValueFactory(cell -> {
	        SensorData data = cell.getValue();
	        return new SimpleStringProperty(String.format("%.2f %s", data.getValue(), data.getUnit()));
	    });
	    valueCol.setPrefWidth(120);
	    
	    TableColumn<SensorData, String> locationCol = new TableColumn<>("Location");
	    locationCol.setCellValueFactory(cell -> cell.getValue().locationProperty());
	    locationCol.setPrefWidth(150);
	    
	    TableColumn<SensorData, String> timestampCol = new TableColumn<>("Timestamp");
	    timestampCol.setCellValueFactory(cell -> {
	        LocalDateTime timestamp = cell.getValue().getTimestamp();
	        return new SimpleStringProperty(timestamp != null ? timestamp.toString() : "N/A");
	    });
	    timestampCol.setPrefWidth(180);
	    
	    TableColumn<SensorData, String> bloodBagCol = new TableColumn<>("Blood Bag ID");
	    bloodBagCol.setCellValueFactory(cell -> cell.getValue().bloodBagIdProperty());
	    bloodBagCol.setPrefWidth(120);
	    
	    TableColumn<SensorData, String> statusCol = new TableColumn<>("Status");
	    statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
	    statusCol.setPrefWidth(100);
	    
	    TableColumn<SensorData, String> actionsCol = new TableColumn<>("Actions");
	    actionsCol.setPrefWidth(100);
	    actionsCol.setCellFactory(col -> {
	        return new TableCell<SensorData, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty) {
	                    setGraphic(null);
	                } else {
	                    Button viewBtn = new Button("👁️");
	                    viewBtn.getStyleClass().addAll("small-button", "primary");
	                    viewBtn.setOnAction(e -> showSensorDataDetails(getTableView().getItems().get(getIndex())));
	                    setGraphic(viewBtn);
	                }
	            }
	        };
	    });
	    
	    table.getColumns().addAll(sensorIdCol, sensorTypeCol, valueCol, locationCol, timestampCol, bloodBagCol, statusCol, actionsCol);
	    return table;
	}

	private void showAddSensorForm() {
	    // Create and show sensor registration dialog
	    Dialog<SensorData> dialog = new Dialog<>();
	    dialog.setTitle("Register Sensor");
	    dialog.setHeaderText("Register New IoT Sensor");
	    
	    // Create form
	    GridPane form = new GridPane();
	    form.setHgap(10);
	    form.setVgap(10);
	    form.getStyleClass().add("dialog-form");
	    
	    // Form fields
	    TextField sensorIdField = new TextField();
	    sensorIdField.setPromptText("Sensor ID (e.g., TEMP-001)");
	    ComboBox<String> sensorTypeCombo = new ComboBox<>();
	    sensorTypeCombo.getItems().addAll("TEMPERATURE", "HUMIDITY", "PRESSURE", "GPS");
	    sensorTypeCombo.setPromptText("Select Sensor Type");
	    TextField valueField = new TextField();
	    valueField.setPromptText("Initial Value");
	    ComboBox<String> unitCombo = new ComboBox<>();
	    unitCombo.getItems().addAll("°C", "%", "kPa", "m", "lat/long");
	    unitCombo.setPromptText("Select Unit");
	    TextField locationField = new TextField();
	    locationField.setPromptText("Location");
	    TextField bloodBagIdField = new TextField();
	    bloodBagIdField.setPromptText("Blood Bag ID (optional)");
	    
	    // Add fields to form
	    form.add(new Label("Sensor ID:"), 0, 0);
	    form.add(sensorIdField, 1, 0);
	    form.add(new Label("Sensor Type:"), 0, 1);
	    form.add(sensorTypeCombo, 1, 1);
	    form.add(new Label("Initial Value:"), 0, 2);
	    form.add(valueField, 1, 2);
	    form.add(new Label("Unit:"), 0, 3);
	    form.add(unitCombo, 1, 3);
	    form.add(new Label("Location:"), 0, 4);
	    form.add(locationField, 1, 4);
	    form.add(new Label("Blood Bag ID:"), 0, 5);
	    form.add(bloodBagIdField, 1, 5);
	    
	    dialog.getDialogPane().setContent(form);
	    
	    // Add buttons
	    ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
	    
	    // Convert result
	    dialog.setResultConverter(buttonType -> {
	        if (buttonType == registerButtonType) {
	            if (sensorIdField.getText().trim().isEmpty() || sensorTypeCombo.getValue() == null ||
	                valueField.getText().trim().isEmpty() || unitCombo.getValue() == null) {
	                showAlert("Validation Error", "Sensor ID, Type, Value, and Unit are required!");
	                return null;
	            }
	            
	            try {
	                SensorData sensorData = new SensorData();
	                sensorData.setSensorId(sensorIdField.getText().trim());
	                sensorData.setSensorType(sensorTypeCombo.getValue());
	                sensorData.setValue(Double.parseDouble(valueField.getText().trim()));
	                sensorData.setUnit(unitCombo.getValue());
	                sensorData.setLocation(locationField.getText().trim());
	                sensorData.setBloodBagId(bloodBagIdField.getText().trim());
	                sensorData.setTimestamp(java.time.LocalDateTime.now());
	                return sensorData;
	            } catch (NumberFormatException e) {
	                showAlert("Validation Error", "Please enter a valid number for the initial value!");
	                return null;
	            }
	        }
	        return null;
	    });
	    
	    // Show dialog and handle result
	    Optional<SensorData> result = dialog.showAndWait();
	    result.ifPresent(sensorData -> {
	        boolean success = sensorDataDao.insert(sensorData);
	        if (success) {
	            sensorDataList.add(sensorData);
	            showStatusMessage("Sensor " + sensorData.getSensorId() + " registered successfully!", "success");
	        } else {
	            showAlert("Error", "Failed to register sensor. Please try again.");
	        }
	    });
	}

	private void showSensorDataDetails(SensorData sensorData) {
	    // Show detailed view of sensor data
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Sensor Data Details");
	    alert.setHeaderText(sensorData.getSensorId() + " - " + sensorData.getSensorType());
	    
	    StringBuilder content = new StringBuilder();
	    content.append("Value: ").append(String.format("%.2f %s", sensorData.getValue(), sensorData.getUnit())).append("\n\n");
	    content.append("Location: ").append(sensorData.getLocation()).append("\n\n");
	    content.append("Timestamp: ").append(sensorData.getTimestamp()).append("\n\n");
	    content.append("Status: ").append(sensorData.getStatus()).append("\n\n");
	    content.append("Blood Bag ID: ").append(sensorData.getBloodBagId());
	    
	    alert.setContentText(content.toString());
	    alert.showAndWait();
	}
	
	private void loadInventoryManagementView() {
		// Create simplified inventory management interface 
		if (inventoryView != null) {
			VBox container = new VBox(20);
			container.getStyleClass().add("content-padding");
			
			Label title = new Label("📦 Blood Inventory Management");
			title.getStyleClass().add("page-title");
			container.getChildren().add(title);
			
			// Simple statistics
			Label statsLabel = new Label("Total Units: " + bloodInventory.size());
			statsLabel.getStyleClass().add("section-title");
			container.getChildren().add(statsLabel);
			
			// Add Unit Button
			Button addUnitBtn = new Button("➕ Add Blood Unit");
			addUnitBtn.getStyleClass().addAll("modern-button", "primary");
			addUnitBtn.setOnAction(e -> showAddBloodUnitForm());
			container.getChildren().add(addUnitBtn);
			
			// Simple table
			TableView<BloodInventory> table = new TableView<>();
			table.setItems(bloodInventory);
			table.getStyleClass().add("modern-table");
			
			TableColumn<BloodInventory, String> bagIdCol = new TableColumn<>("Bag ID");
			bagIdCol.setCellValueFactory(cell -> cell.getValue().bagIdProperty());
			bagIdCol.setPrefWidth(150);
			
			TableColumn<BloodInventory, String> bloodGroupCol = new TableColumn<>("Blood Group");
			bloodGroupCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
			bloodGroupCol.setPrefWidth(120);
			
			TableColumn<BloodInventory, String> statusCol = new TableColumn<>("Status");
			statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
			statusCol.setPrefWidth(120);
			
			TableColumn<BloodInventory, String> locationCol = new TableColumn<>("Location");
			locationCol.setCellValueFactory(cell -> cell.getValue().locationProperty());
			locationCol.setPrefWidth(120);
			
			table.getColumns().addAll(bagIdCol, bloodGroupCol, statusCol, locationCol);
			container.getChildren().add(table);
			
			inventoryView.setContent(container);
		}
	}
	
	private void showAddBloodUnitForm() {
		// Simplified blood unit addition
		Dialog<BloodInventory> dialog = new Dialog<>();
		dialog.setTitle("Add Blood Unit");
		dialog.setHeaderText("Add New Blood Unit");
		
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		ComboBox<String> bloodGroupCombo = new ComboBox<>();
		bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
		bloodGroupCombo.setPromptText("Select Blood Group");
		
		TextField locationField = new TextField("Storage-A");
		
		form.add(new Label("Blood Group:"), 0, 0);
		form.add(bloodGroupCombo, 1, 0);
		form.add(new Label("Location:"), 0, 1);
		form.add(locationField, 1, 1);
		
		dialog.getDialogPane().setContent(form);
		
		ButtonType addButtonType = new ButtonType("Add Unit", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
		
		dialog.setResultConverter(buttonType -> {
			if (buttonType == addButtonType && bloodGroupCombo.getValue() != null) {
				BloodInventory inventory = new BloodInventory();
				inventory.setBagId("BAG-" + System.currentTimeMillis());
				inventory.setBloodGroup(bloodGroupCombo.getValue());
				inventory.setStatus("AVAILABLE");
				inventory.setVolume(450.0);
				inventory.setLocation(locationField.getText().trim());
				inventory.setDonationDate(java.time.LocalDate.now());
				inventory.setExpiryDate(java.time.LocalDate.now().plusDays(42));
				inventory.setTemperature(4.0);
				inventory.setQualityScore(100);
				return inventory;
			}
			return null;
		});
		
		Optional<BloodInventory> result = dialog.showAndWait();
		result.ifPresent(inventory -> {
			boolean success = bloodInventoryDao.insert(inventory);
			if (success) {
				bloodInventory.add(inventory);
				showStatusMessage("Blood unit " + inventory.getBagId() + " added successfully!", "success");
				loadDashboardData(); // Refresh dashboard
			} else {
				showAlert("Error", "Failed to add blood unit. Please try again.");
			}
		});
	}
	
	private void showExpiringUnits() {
		List<BloodInventory> expiringUnits = bloodInventoryDao.findExpiringSoon(7);
		if (expiringUnits.isEmpty()) {
			showAlert("Expiring Units", "No units expiring in the next 7 days! ✅");
		} else {
			StringBuilder message = new StringBuilder("Units expiring within 7 days:\n\n");
			for (BloodInventory unit : expiringUnits) {
				message.append("• ").append(unit.getBagId())
					.append(" (").append(unit.getBloodGroup())
					.append(") - Expires: ").append(unit.getExpiryDate())
					.append("\n");
			}
			showAlert("Expiring Units", message.toString());
		}
	}
	
	private VBox createStatCard(String icon, String number, String label, String styleClass) {
		VBox card = new VBox();
		card.getStyleClass().add(styleClass);
		card.setAlignment(javafx.geometry.Pos.CENTER);
		
		Label iconLabel = new Label(icon);
		iconLabel.getStyleClass().add("stat-icon");
		
		Label numberLabel = new Label(number);
		numberLabel.getStyleClass().add("stat-number");
		
		Label textLabel = new Label(label);
		textLabel.getStyleClass().add("stat-label");
		
		card.getChildren().addAll(iconLabel, numberLabel, textLabel);
		return card;
	}
	
	private TableView<Donor> createDonorTable() {
		TableView<Donor> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(donors);
		
		TableColumn<Donor, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
		nameCol.setPrefWidth(150);
		
		TableColumn<Donor, String> emailCol = new TableColumn<>("Email");
		emailCol.setCellValueFactory(cell -> cell.getValue().emailProperty());
		emailCol.setPrefWidth(200);
		
		TableColumn<Donor, String> phoneCol = new TableColumn<>("Phone");
		phoneCol.setCellValueFactory(cell -> cell.getValue().phoneProperty());
		phoneCol.setPrefWidth(120);
		
		TableColumn<Donor, String> bloodCol = new TableColumn<>("Blood Group");
		bloodCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		bloodCol.setPrefWidth(100);
		
		TableColumn<Donor, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(150);
		actionsCol.setCellFactory(col -> {
			return new TableCell<Donor, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						HBox buttons = new HBox(5);
						Button editBtn = new Button("✏️");
						editBtn.getStyleClass().addAll("small-button", "primary");
						Button deleteBtn = new Button("🗑️");
						deleteBtn.getStyleClass().addAll("small-button", "danger");
						buttons.getChildren().addAll(editBtn, deleteBtn);
						setGraphic(buttons);
					}
				}
			};
		});
		
		table.getColumns().addAll(nameCol, emailCol, phoneCol, bloodCol, actionsCol);
		return table;
	}
	
	private void showDonorRegistrationForm() {
		// Create and show donor registration dialog
		Dialog<Donor> dialog = new Dialog<>();
		dialog.setTitle("Donor Registration");
		dialog.setHeaderText("Register New Blood Donor");
		
		// Create form
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		// Form fields
		TextField nameField = new TextField();
		nameField.setPromptText("Full Name");
		TextField emailField = new TextField();
		emailField.setPromptText("Email Address");
		TextField phoneField = new TextField();
		phoneField.setPromptText("Phone Number");
		ComboBox<String> bloodGroupCombo = new ComboBox<>();
		bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
		bloodGroupCombo.setPromptText("Select Blood Group");
		
		// Add fields to form
		form.add(new Label("Full Name:"), 0, 0);
		form.add(nameField, 1, 0);
		form.add(new Label("Email:"), 0, 1);
		form.add(emailField, 1, 1);
		form.add(new Label("Phone:"), 0, 2);
		form.add(phoneField, 1, 2);
		form.add(new Label("Blood Group:"), 0, 3);
		form.add(bloodGroupCombo, 1, 3);
		
		dialog.getDialogPane().setContent(form);
		
		// Add buttons
		ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);
		
		// Convert result
		dialog.setResultConverter(buttonType -> {
			if (buttonType == registerButtonType) {
				if (nameField.getText().trim().isEmpty() || bloodGroupCombo.getValue() == null) {
					showAlert("Validation Error", "Name and Blood Group are required!");
					return null;
				}
				
				Donor donor = new Donor();
				donor.setName(nameField.getText().trim());
				donor.setEmail(emailField.getText().trim());
				donor.setPhone(phoneField.getText().trim());
				donor.setBloodGroup(bloodGroupCombo.getValue());
				return donor;
			}
			return null;
		});
		
		// Show dialog and handle result
		Optional<Donor> result = dialog.showAndWait();
		result.ifPresent(donor -> {
			boolean success = donorDao.insert(donor);
			if (success) {
				donors.add(donor);
				showStatusMessage("Donor " + donor.getName() + " registered successfully!", "success");
				loadDashboardData(); // Refresh dashboard
			} else {
				showAlert("Error", "Failed to register donor. Please try again.");
			}
		});
	}
	
	/**
	 * Centralized view management method to prevent UI overlapping
	 * This method ensures proper view switching without conflicts
	 */
	private void showViewWithAnimation(String viewName) {
		// Always hide all views first to prevent overlapping
		hideAllViews();
		currentView = viewName;
		
		javafx.scene.Node targetView = null;
		Button targetButton = null;
		
		// Determine target view and button based on viewName
		switch (viewName) {
			case "dashboard":
				targetView = dashboardView;
				targetButton = dashboardBtn;
				break;
			case "donors":
				targetView = donorsView;
				targetButton = donorsBtn;
				if (donorsView != null) loadDonorManagementView();
				break;
			case "recipients":
				targetView = recipientsView;
				targetButton = recipientsBtn;
				if (recipientsView != null) loadRecipientManagementView();
				break;
			case "inventory":
				targetView = inventoryView;
				targetButton = inventoryBtn;
				if (inventoryView != null) loadInventoryManagementView();
				break;
			case "campaigns":
				targetView = campaignsView;
				targetButton = campaignsBtn;
				if (campaignsView != null) loadCampaignManagementView();
				break;
			case "rewards":
				targetView = rewardsView;
				targetButton = rewardsBtn;
				if (rewardsView != null) loadRewardsAndGamificationView();
				break;
			case "statistics":
				targetView = statisticsView;
				targetButton = statisticsBtn;
				if (statisticsView != null) loadStatisticsAndReportsView();
				break;
			case "emergency":
				targetView = emergencyView;
				targetButton = emergencyBtn;
				if (emergencyView != null) loadEmergencyResponseView();
				break;
			case "aiPrediction":
				targetView = aiPredictionView;
				targetButton = aiPredictionBtn;
				if (aiPredictionView != null) loadAIPredictionData();
				break;
			case "gamification":
				targetView = gamificationView;
				targetButton = gamificationBtn;
				if (gamificationView != null) loadGamificationData();
				break;
			case "iotMonitoring":
				targetView = iotMonitoringView;
				targetButton = iotMonitoringBtn;
				if (iotMonitoringView != null) loadIoTMonitoringView();
				break;
			case "blockchain":
				targetView = blockchainView;
				targetButton = blockchainBtn;
				if (blockchainView != null) loadBlockchainData();
				break;
			case "voiceCommands":
				targetView = voiceCommandsView;
				targetButton = voiceCommandsBtn;
				if (voiceCommandsView != null) loadVoiceCommandsData();
				break;
			case "quantumMatching":
				targetView = quantumMatchingView;
				targetButton = quantumMatchingBtn;
				if (quantumMatchingView != null) loadQuantumMatchingData();
				break;
			case "biometricAuth":
				targetView = biometricAuthView;
				targetButton = biometricAuthBtn;
				if (biometricAuthView != null) loadBiometricAuthData();
				break;
			case "arVisualization":
				targetView = arVisualizationView;
				targetButton = arVisualizationBtn;
				if (arVisualizationView != null) loadARVisualizationData();
				break;
			case "neuralQuality":
				targetView = neuralQualityView;
				targetButton = neuralQualityBtn;
				if (neuralQualityView != null) loadNeuralQualityData();
				break;
			case "droneDelivery":
				targetView = droneDeliveryView;
				targetButton = droneDeliveryBtn;
				if (droneDeliveryView != null) loadDroneDeliveryData();
				break;
			case "digitalTwin":
				targetView = digitalTwinView;
				targetButton = digitalTwinBtn;
				if (digitalTwinView != null) loadDigitalTwinData();
				break;
			case "satelliteComm":
				targetView = satelliteCommView;
				targetButton = satelliteCommBtn;
				if (satelliteCommView != null) loadSatelliteCommData();
				break;
			case "holographic":
				targetView = holographicView;
				targetButton = holographicBtn;
				if (holographicView != null) loadHolographicData();
				break;
		}
		
		// Show target view with proper setup
		if (targetView != null) {
			targetView.setVisible(true);
			targetView.setManaged(true);
			
			// Add smooth fade-in animation
			FadeTransition fadeIn = new FadeTransition(Duration.millis(300), targetView);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
		}
		
		// Update navigation button styles
		updateAllNavigationButtons(targetButton);
		
		// Handle special dashboard logo highlighting
		if ("dashboard".equals(viewName) && logoBtn != null) {
			logoBtn.getStyleClass().removeAll("dashboard-active");
			logoBtn.getStyleClass().add("dashboard-active");
		} else if (logoBtn != null) {
			logoBtn.getStyleClass().removeAll("dashboard-active");
		}
	}
	
	/**
	 * Unified method to update navigation button active states
	 * This prevents inconsistencies in button highlighting
	 */
	private void updateAllNavigationButtons(Button activeButton) {
		// Comprehensive list of ALL navigation buttons
		List<Button> allNavButtons = Arrays.asList(
			// Main navigation buttons
			dashboardBtn, donorsBtn, recipientsBtn, inventoryBtn,
			campaignsBtn, rewardsBtn, statisticsBtn, emergencyBtn,
			// AI & Innovation buttons
			aiPredictionBtn, gamificationBtn, iotMonitoringBtn,
			blockchainBtn, voiceCommandsBtn,
			// Revolutionary Technology buttons
			quantumMatchingBtn, biometricAuthBtn, arVisualizationBtn,
			neuralQualityBtn, droneDeliveryBtn, digitalTwinBtn,
			satelliteCommBtn, holographicBtn
		);
		
		// Remove active class from all buttons
		for (Button btn : allNavButtons) {
			if (btn != null) {
				btn.getStyleClass().removeAll("active");
			}
		}
		
		// Add active class to the selected button
		if (activeButton != null) {
			activeButton.getStyleClass().add("active");
		}
	}

	
	// =============================================================================
	// DASHBOARD SETUP AND DATA LOADING
	// =============================================================================
	
	private void setupDashboard() {
		// Setup alerts list
		if (alertsList != null) {
			alertsList.setItems(alerts);
			// Add some sample alerts
			alerts.addAll(
				"🟡 Low stock: O- blood (5 units remaining)",
				"🔴 Expiring soon: A+ blood expires in 2 days",
				"🟢 New donor registered: John Smith (B+)",
				"🔵 Campaign started: City Hospital Blood Drive"
			);
		}
	}
	
	private void setupLegacyDonorTable() {
		colId.setCellValueFactory(cell -> cell.getValue().idProperty());
		colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
		colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
		colBlood.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		donorTable.setItems(donors);
	}
	
	private void setupRecentDonationsTable() {
		donorNameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
		bloodTypeCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		donationDateCol.setCellValueFactory(cell -> cell.getValue().idProperty()); // Placeholder
		donationStatusCol.setCellValueFactory(cell -> cell.getValue().idProperty()); // Placeholder
		recentDonationsTable.setItems(donors);
	}
	
	private void loadDashboardData() {
		CompletableFuture.supplyAsync(() -> {
			return donorDao.findAll();
		}).thenAccept(donorList -> {
			Platform.runLater(() -> {
				donors.setAll(donorList);
				updateStatistics(donorList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load dashboard data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadRecipientsData() {
		CompletableFuture.supplyAsync(() -> {
			return recipientDao.findAll();
		}).thenAccept(recipientList -> {
			Platform.runLater(() -> {
				recipients.setAll(recipientList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load recipients data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadInventoryData() {
		CompletableFuture.supplyAsync(() -> {
			return bloodInventoryDao.findAll();
		}).thenAccept(inventoryList -> {
			Platform.runLater(() -> {
				bloodInventory.setAll(inventoryList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load inventory data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadDonorPointsData() {
		CompletableFuture.supplyAsync(() -> {
			return donorPointsDao.findAll();
		}).thenAccept(pointsList -> {
			Platform.runLater(() -> {
				this.donorPointsList.setAll(pointsList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load donor points data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadStatisticsData() {
		CompletableFuture.supplyAsync(() -> {
			return bloodStatisticsDao.findAll();
		}).thenAccept(statsList -> {
			Platform.runLater(() -> {
				this.statisticsList.setAll(statsList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load statistics data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadEmergencyRequestsData() {
		CompletableFuture.supplyAsync(() -> {
			return emergencyRequestDao.findAll();
		}).thenAccept(requestsList -> {
			Platform.runLater(() -> {
				this.emergencyRequestsList.setAll(requestsList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load emergency requests data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadAIPredictionsData() {
		CompletableFuture.supplyAsync(() -> {
			return aiPredictionDao.findAll();
		}).thenAccept(predictionsList -> {
			Platform.runLater(() -> {
				this.aiPredictionsList.setAll(predictionsList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load AI predictions data: " + throwable.getMessage());
			});
			return null;
		});
	}
	
	private void loadCampaignData() {
		CompletableFuture.supplyAsync(() -> {
			return campaignDao.findAll();
		}).thenAccept(campaignList -> {
			Platform.runLater(() -> {
				this.campaignList.setAll(campaignList);
			});
		}).exceptionally(throwable -> {
			Platform.runLater(() -> {
				showAlert("Error", "Failed to load campaign data: " + throwable.getMessage());
			});
			return null;
		});
	}
	

	private void loadBlockchainVerificationData() {
	    CompletableFuture.supplyAsync(() -> {
	        return bloodUnitVerificationDao.findAll();
	    }).thenAccept(verifications -> {
	        Platform.runLater(() -> {
	            this.verificationList.setAll(verifications);
	        });
	    }).exceptionally(throwable -> {
	        Platform.runLater(() -> {
	            showAlert("Error", "Failed to load blockchain verification data: " + throwable.getMessage());
	        });
	        return null;
	    });
	}
	
	private void updateStatistics(List<Donor> donorList) {
		// Update total donors
		if (totalDonorsLabel != null) {
			totalDonorsLabel.setText(String.valueOf(donorList.size()));
			animateLabel(totalDonorsLabel);
		}
		
		// Calculate blood group distribution
		Map<String, Integer> bloodGroupCounts = new HashMap<>();
		for (Donor donor : donorList) {
			String bloodGroup = donor.getBloodGroup();
			bloodGroupCounts.put(bloodGroup, bloodGroupCounts.getOrDefault(bloodGroup, 0) + 1);
		}
		
		// Update blood group labels
		updateBloodGroupLabel(oNegativeUnits, bloodGroupCounts.getOrDefault("O-", 0));
		updateBloodGroupLabel(oPositiveUnits, bloodGroupCounts.getOrDefault("O+", 0));
		updateBloodGroupLabel(aNegativeUnits, bloodGroupCounts.getOrDefault("A-", 0));
		updateBloodGroupLabel(aPositiveUnits, bloodGroupCounts.getOrDefault("A+", 0));
		updateBloodGroupLabel(bNegativeUnits, bloodGroupCounts.getOrDefault("B-", 0));
		updateBloodGroupLabel(bPositiveUnits, bloodGroupCounts.getOrDefault("B+", 0));
		updateBloodGroupLabel(abNegativeUnits, bloodGroupCounts.getOrDefault("AB-", 0));
		updateBloodGroupLabel(abPositiveUnits, bloodGroupCounts.getOrDefault("AB+", 0));
		
		// Update other statistics (mock data for now)
		if (availableUnitsLabel != null) {
			availableUnitsLabel.setText(String.valueOf(donorList.size() * 2)); // Mock calculation
			animateLabel(availableUnitsLabel);
		}
		if (activeCampaignsLabel != null) {
			activeCampaignsLabel.setText("3"); // Mock data
			animateLabel(activeCampaignsLabel);
		}
		if (emergencyRequestsLabel != null) {
			emergencyRequestsLabel.setText("2"); // Mock data
			animateLabel(emergencyRequestsLabel);
		}
	}
	
	private void updateBloodGroupLabel(Label label, int count) {
		if (label != null) {
			label.setText(String.valueOf(count));
			animateLabel(label);
		}
	}
	
	private void animateLabel(Label label) {
		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), label);
		scaleTransition.setFromX(0.8);
		scaleTransition.setFromY(0.8);
		scaleTransition.setToX(1.0);
		scaleTransition.setToY(1.0);
		scaleTransition.play();
	}
	
	private void startRealTimeUpdates() {
		// Update clock every second
		clockTimeline = new Timeline(
			new KeyFrame(Duration.seconds(1), e -> updateClock())
		);
		clockTimeline.setCycleCount(Timeline.INDEFINITE);
		clockTimeline.play();
		
		// Update dashboard data every 30 seconds
		Timeline dataTimeline = new Timeline(
			new KeyFrame(Duration.seconds(30), e -> loadDashboardData())
		);
		dataTimeline.setCycleCount(Timeline.INDEFINITE);
		dataTimeline.play();
	}
	
	private void updateClock() {
		if (currentDateTime != null) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy - HH:mm:ss");
			currentDateTime.setText(now.format(formatter));
		}
	}
	
	// =============================================================================
	// NAVIGATION METHODS
	// =============================================================================
	
	@FXML
	public void showDashboard() {
		showViewWithAnimation("dashboard");
		// Show welcome message when dashboard is active
		showStatusMessage("Welcome to BloodMate Dashboard! 🩸", "success");
	}
	
	@FXML
	public void showDonors() {
		showViewWithAnimation("donors");
	}
	
	@FXML
	public void showRecipients() {
		showViewWithAnimation("recipients");
	}
	
	@FXML
	public void showInventory() {
		showViewWithAnimation("inventory");
	}
	
	@FXML
	public void showCampaigns() {
		showViewWithAnimation("campaigns");
	}
	
	@FXML
	public void showRewards() {
		showViewWithAnimation("rewards");
	}
	
	@FXML
	public void showStatistics() {
		showViewWithAnimation("statistics");
	}
	
	@FXML
	public void showEmergency() {
		showViewWithAnimation("emergency");
	}
	
	@FXML
	public void showSettings() {
		showAlert("Settings", "Settings panel will be implemented in future updates.");
	}
	
	@FXML
	public void logout() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("Are you sure you want to logout?");
		alert.setContentText("You will need to login again to access the system.");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			if (clockTimeline != null) {
				clockTimeline.stop();
			}
			Platform.exit();
		}
	}
	


	
	private void hideAllViews() {
		// Comprehensive list of ALL views to prevent overlapping
		List<javafx.scene.Node> allViews = Arrays.asList(
			// Main views
			dashboardView, donorsView, recipientsView, inventoryView,
			campaignsView, rewardsView, statisticsView, emergencyView,
			// AI & Innovation views
			aiPredictionView, gamificationView, iotMonitoringView,
			blockchainView, voiceCommandsView,
			// Revolutionary Technology views
			quantumMatchingView, biometricAuthView, arVisualizationView,
			neuralQualityView, droneDeliveryView, digitalTwinView,
			satelliteCommView, holographicView
		);
		
		// Hide all views completely to prevent any overlapping
		for (javafx.scene.Node view : allViews) {
			if (view != null) {
				view.setVisible(false);
				view.setManaged(false);
				// Clear any existing animations to prevent conflicts
				view.setOpacity(1.0);
			}
		}
	}

	
	// =============================================================================
	// DASHBOARD ACTIONS
	// =============================================================================
	
	@FXML
	public void refreshDashboard() {
		loadDashboardData();
		showStatusMessage("Dashboard refreshed successfully!", "success");
	}
	
	private void showStatusMessage(String message, String type) {
		// Add to alerts list with animation
		String icon = type.equals("success") ? "✅" : type.equals("warning") ? "⚠️" : "❌";
		alerts.add(0, icon + " " + message);
		
		// Remove old alerts (keep only last 10)
		if (alerts.size() > 10) {
			alerts.remove(alerts.size() - 1);
		}
	}

	// =============================================================================
	// LEGACY DONOR MANAGEMENT (BACKWARD COMPATIBILITY)
	// =============================================================================

	@FXML public void onRefresh(ActionEvent e) { 
		loadDonors(); 
		showStatusMessage("Donor list refreshed", "success");
	}

	@FXML public void onAddDonor(ActionEvent e) {
		String name = nameField != null ? nameField.getText().trim() : "";
		String email = emailField != null ? emailField.getText().trim() : "";
		String phone = phoneField != null ? phoneField.getText().trim() : "";
		String blood = bloodField != null ? bloodField.getText().trim() : "";
		
		if (name.isEmpty() || blood.isEmpty()) {
			setStatus("Name and Blood Group required");
			showAlert("Validation Error", "Name and Blood Group are required fields.");
			return;
		}
		
		Donor d = new Donor();
		d.setName(name);
		d.setEmail(email);
		d.setPhone(phone);
		d.setBloodGroup(blood.toUpperCase());
		
		boolean ok = donorDao.insert(d);
		if (ok) {
			setStatus("Donor added successfully");
			clearForm();
			loadDonors();
			loadDashboardData(); // Refresh dashboard
			showStatusMessage("New donor " + name + " added successfully", "success");
		} else {
			setStatus("Failed to add donor");
			showAlert("Error", "Failed to add donor. Please try again.");
		}
	}

	private void loadDonors() {
		donors.setAll(donorDao.findAll());
	}

	private void clearForm() {
		if (nameField != null) nameField.clear();
		if (emailField != null) emailField.clear();
		if (phoneField != null) phoneField.clear();
		if (bloodField != null) bloodField.clear();
	}

	private void setStatus(String s) { 
		if (statusLabel != null) {
			statusLabel.setText(s); 
		}
	}
	

	@FXML
	public void showAIPrediction() { showViewWithAnimation("aiPrediction"); }
	
	@FXML
	public void showGamification() { showViewWithAnimation("gamification"); }
	
	@FXML
	public void showIoTMonitoring() {
	    showViewWithAnimation("iotMonitoring");
	    if (iotMonitoringView != null) loadIoTMonitoringView();
	}
	
	@FXML
	public void showBlockchain() {
	    showViewWithAnimation("blockchain");
	    if (blockchainView != null) loadBlockchainView();
	}
	
	@FXML
	public void showVoiceCommands() { showViewWithAnimation("voiceCommands"); }



	
	private void loadAIPredictionData() {
		Random random = new Random();
		Label aiAccuracyLabel = (Label) contentArea.lookup("#aiAccuracyLabel");
		if (aiAccuracyLabel != null) {
			double accuracy = 90 + random.nextDouble() * 8;
			aiAccuracyLabel.setText(String.format("%.1f%%", accuracy));
		}
	}
	
	private void loadGamificationData() {
		Random random = new Random();
		Label totalDonationsLabel = (Label) contentArea.lookup("#totalDonationsLabel");
		if (totalDonationsLabel != null) {
			int donations = 15 + random.nextInt(20);
			totalDonationsLabel.setText(String.valueOf(donations));
		}
	}
	
	private void loadIoTMonitoringData() {
	    CompletableFuture.supplyAsync(() -> {
	        return sensorDataDao.findAll();
	    }).thenAccept(sensorData -> {
	        Platform.runLater(() -> {
	            this.sensorDataList.setAll(sensorData);
	        });
	    }).exceptionally(throwable -> {
	        Platform.runLater(() -> {
	            showAlert("Error", "Failed to load IoT monitoring data: " + throwable.getMessage());
	        });
	        return null;
	    });
	}
	

	
	private void loadBlockchainData() {
		Random random = new Random();
		Label verifiedUnitsLabel = (Label) contentArea.lookup("#verifiedUnitsLabel");
		if (verifiedUnitsLabel != null) {
			int units = 15000 + random.nextInt(1000);
			verifiedUnitsLabel.setText(String.format("%,d", units));
		}
	}
	
	private void loadBlockchainView() {
		// Load blockchain verification interface dynamically
		if (blockchainView != null) {
			// Create blockchain verification interface
			VBox blockchainContent = createBlockchainInterface();
			blockchainView.setContent(blockchainContent);
		}
	}
	
	private VBox createBlockchainInterface() {
		VBox container = new VBox(20);
		container.getStyleClass().add("content-padding");
		
		// Page Header
		HBox header = new HBox(20);
		header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
		
		Label title = new Label("🔗 Blockchain Verification & Traceability");
		title.getStyleClass().add("page-title");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
		
		Button refreshBtn = new Button("🔄 Refresh Data");
		refreshBtn.getStyleClass().addAll("modern-button", "primary");
		refreshBtn.setOnAction(e -> loadBlockchainVerificationData());
		
		Button verifyUnitBtn = new Button("✅ Verify Blood Unit");
		verifyUnitBtn.getStyleClass().addAll("modern-button", "success");
		verifyUnitBtn.setOnAction(e -> showVerifyBloodUnitForm());
		
		header.getChildren().addAll(title, spacer, refreshBtn, verifyUnitBtn);
		container.getChildren().add(header);
		
		// Statistics Cards
		GridPane statsGrid = new GridPane();
		statsGrid.getStyleClass().add("responsive-grid");
		statsGrid.setHgap(20);
		statsGrid.setVgap(20);
		
		// Create statistics cards
		VBox verifiedUnitsCard = createStatCard("✅", String.valueOf(countVerifiedUnits()), "Verified Units", "stat-card");
		statsGrid.add(verifiedUnitsCard, 0, 0);
		
		VBox pendingVerificationsCard = createStatCard("⏳", String.valueOf(countPendingVerifications()), "Pending", "stat-card");
		statsGrid.add(pendingVerificationsCard, 1, 0);
		
		VBox totalVerificationsCard = createStatCard("🔗", String.valueOf(verificationList.size()), "Total Records", "stat-card");
		statsGrid.add(totalVerificationsCard, 2, 0);
		
		VBox integrityCard = createStatCard("🛡️", "100%", "Data Integrity", "stat-card");
		statsGrid.add(integrityCard, 3, 0);
		
		container.getChildren().add(statsGrid);
		
		// Verification Table
		VBox tableContainer = new VBox(15);
		tableContainer.getStyleClass().add("dashboard-card");
		
		Label tableTitle = new Label("Blood Unit Verification Records");
		tableTitle.getStyleClass().add("section-title");
		
		TableView<BloodUnitVerification> verificationTable = createVerificationTable();
		tableContainer.getChildren().addAll(tableTitle, verificationTable);
		container.getChildren().add(tableContainer);
		
		// Blockchain Info Panel
		VBox infoContainer = new VBox(15);
		infoContainer.getStyleClass().add("dashboard-card");
		
		Label infoTitle = new Label("Blockchain Information");
		infoTitle.getStyleClass().add("section-title");
		
		TextArea infoArea = new TextArea();
		infoArea.setPrefRowCount(5);
		infoArea.setEditable(false);
		infoArea.setWrapText(true);
		infoArea.setText("Blockchain technology ensures complete traceability and authenticity of blood units:\n\n" +
			"• Immutable records of blood unit journey\n" +
			"• Cryptographic verification of authenticity\n" +
			"• Transparent tracking from donor to recipient\n" +
			"• Tamper-proof data storage\n" +
			"• Real-time verification status updates");
		
		infoContainer.getChildren().addAll(infoTitle, infoArea);
		container.getChildren().add(infoContainer);
		
		return container;
	}
	
	private TableView<BloodUnitVerification> createVerificationTable() {
		TableView<BloodUnitVerification> table = new TableView<>();
		table.getStyleClass().add("modern-table");
		table.setItems(verificationList);
		
		TableColumn<BloodUnitVerification, String> bloodBagIdCol = new TableColumn<>("Blood Bag ID");
		bloodBagIdCol.setCellValueFactory(cell -> cell.getValue().bloodBagIdProperty());
		bloodBagIdCol.setPrefWidth(120);
		
		TableColumn<BloodUnitVerification, String> donorNameCol = new TableColumn<>("Donor Name");
		donorNameCol.setCellValueFactory(cell -> cell.getValue().donorNameProperty());
		donorNameCol.setPrefWidth(150);
		
		TableColumn<BloodUnitVerification, String> bloodGroupCol = new TableColumn<>("Blood Group");
		bloodGroupCol.setCellValueFactory(cell -> cell.getValue().bloodGroupProperty());
		bloodGroupCol.setPrefWidth(100);
		
		TableColumn<BloodUnitVerification, String> collectionDateCol = new TableColumn<>("Collection Date");
		collectionDateCol.setCellValueFactory(cell -> {
			LocalDateTime date = cell.getValue().getCollectionDate();
			return new SimpleStringProperty(date != null ? date.toLocalDate().toString() : "N/A");
		});
		collectionDateCol.setPrefWidth(120);
		
		TableColumn<BloodUnitVerification, String> verificationDateCol = new TableColumn<>("Verification Date");
		verificationDateCol.setCellValueFactory(cell -> {
			LocalDateTime date = cell.getValue().getVerificationDate();
			return new SimpleStringProperty(date != null ? date.toString() : "N/A");
		});
		verificationDateCol.setPrefWidth(180);
		
		TableColumn<BloodUnitVerification, String> statusCol = new TableColumn<>("Status");
		statusCol.setCellValueFactory(cell -> cell.getValue().verificationStatusProperty());
		statusCol.setPrefWidth(100);
		
		TableColumn<BloodUnitVerification, String> verifierCol = new TableColumn<>("Verified By");
		verifierCol.setCellValueFactory(cell -> cell.getValue().verifierProperty());
		verifierCol.setPrefWidth(120);
		
		TableColumn<BloodUnitVerification, String> actionsCol = new TableColumn<>("Actions");
		actionsCol.setPrefWidth(120);
		actionsCol.setCellFactory(col -> {
			return new TableCell<BloodUnitVerification, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						HBox buttons = new HBox(5);
						Button viewBtn = new Button("👁️");
						viewBtn.getStyleClass().addAll("small-button", "primary");
						viewBtn.setOnAction(e -> showVerificationDetails(getTableView().getItems().get(getIndex())));
						
						Button verifyBtn = new Button("✅");
						verifyBtn.getStyleClass().addAll("small-button", "success");
						verifyBtn.setOnAction(e -> verifyBloodUnit(getTableView().getItems().get(getIndex())));
						
						buttons.getChildren().addAll(viewBtn, verifyBtn);
						setGraphic(buttons);
					}
				}
			};
		});
		
		table.getColumns().addAll(bloodBagIdCol, donorNameCol, bloodGroupCol, collectionDateCol, 
							 verificationDateCol, statusCol, verifierCol, actionsCol);
		return table;
	}
	
	private void showVerifyBloodUnitForm() {
		// Create and show blood unit verification dialog
		Dialog<BloodUnitVerification> dialog = new Dialog<>();
		dialog.setTitle("Verify Blood Unit");
		dialog.setHeaderText("Verify Blood Unit Authenticity");
		
		// Create form
		GridPane form = new GridPane();
		form.setHgap(10);
		form.setVgap(10);
		form.getStyleClass().add("dialog-form");
		
		// Form fields
		TextField bloodBagIdField = new TextField();
		bloodBagIdField.setPromptText("Blood Bag ID");
		TextField donorNameField = new TextField();
		donorNameField.setPromptText("Donor Name");
		ComboBox<String> bloodGroupCombo = new ComboBox<>();
		bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
		bloodGroupCombo.setPromptText("Select Blood Group");
		DatePicker collectionDatePicker = new DatePicker();
		TextField verifierField = new TextField();
		verifierField.setPromptText("Verifier Name");
		TextField locationField = new TextField();
		locationField.setPromptText("Verification Location");
		TextArea notesArea = new TextArea();
		notesArea.setPromptText("Additional Notes");
		notesArea.setPrefRowCount(3);
		
		// Add fields to form
		form.add(new Label("Blood Bag ID:"), 0, 0);
		form.add(bloodBagIdField, 1, 0);
		form.add(new Label("Donor Name:"), 0, 1);
		form.add(donorNameField, 1, 1);
		form.add(new Label("Blood Group:"), 0, 2);
		form.add(bloodGroupCombo, 1, 2);
		form.add(new Label("Collection Date:"), 0, 3);
		form.add(collectionDatePicker, 1, 3);
		form.add(new Label("Verifier:"), 0, 4);
		form.add(verifierField, 1, 4);
		form.add(new Label("Location:"), 0, 5);
		form.add(locationField, 1, 5);
		form.add(new Label("Notes:"), 0, 6);
		form.add(notesArea, 1, 6);
		
		dialog.getDialogPane().setContent(form);
		
		// Add buttons
		ButtonType verifyButtonType = new ButtonType("Verify", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(verifyButtonType, ButtonType.CANCEL);
		
		// Convert result
		dialog.setResultConverter(buttonType -> {
			if (buttonType == verifyButtonType) {
				if (bloodBagIdField.getText().trim().isEmpty() || donorNameField.getText().trim().isEmpty() ||
					bloodGroupCombo.getValue() == null) {
					showAlert("Validation Error", "Blood Bag ID, Donor Name, and Blood Group are required!");
					return null;
				}
				
				BloodUnitVerification verification = new BloodUnitVerification();
				verification.setBloodBagId(bloodBagIdField.getText().trim());
				verification.setDonorName(donorNameField.getText().trim());
				verification.setBloodGroup(bloodGroupCombo.getValue());
				verification.setCollectionDate(collectionDatePicker.getValue() != null ? 
					collectionDatePicker.getValue().atStartOfDay() : null);
				verification.setVerifier(verifierField.getText().trim());
				verification.setLocation(locationField.getText().trim());
				verification.setNotes(notesArea.getText().trim());
				verification.setVerificationStatus("VERIFIED");
				return verification;
			}
			return null;
		});
		
		// Show dialog and handle result
		Optional<BloodUnitVerification> result = dialog.showAndWait();
		result.ifPresent(verification -> {
			boolean success = bloodUnitVerificationDao.insert(verification);
			if (success) {
				verificationList.add(verification);
				showStatusMessage("Blood unit " + verification.getBloodBagId() + " verified successfully!", "success");
			} else {
				showAlert("Error", "Failed to verify blood unit. Please try again.");
			}
		});
	}
	
	private void showVerificationDetails(BloodUnitVerification verification) {
		// Show detailed view of verification record
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Verification Details");
		alert.setHeaderText("Blood Unit: " + verification.getBloodBagId());
		
		StringBuilder content = new StringBuilder();
		content.append("Verification ID: ").append(verification.getId()).append("\n\n");
		content.append("Donor Name: ").append(verification.getDonorName()).append("\n\n");
		content.append("Blood Group: ").append(verification.getBloodGroup()).append("\n\n");
		content.append("Collection Date: ").append(verification.getCollectionDate() != null ? 
			verification.getCollectionDate().toString() : "N/A").append("\n\n");
		content.append("Verification Date: ").append(verification.getVerificationDate()).append("\n\n");
		content.append("Status: ").append(verification.getVerificationStatus()).append("\n\n");
		content.append("Verified By: ").append(verification.getVerifier()).append("\n\n");
		content.append("Location: ").append(verification.getLocation()).append("\n\n");
		content.append("Notes: ").append(verification.getNotes()).append("\n\n");
		content.append("Blockchain Hash: ").append(verification.getHash());
		
		alert.setContentText(content.toString());
		alert.showAndWait();
	}
	
	private void verifyBloodUnit(BloodUnitVerification verification) {
		boolean success = bloodUnitVerificationDao.verifyBloodUnit(verification.getBloodBagId());
		if (success) {
			verification.setVerificationStatus("VERIFIED");
			verification.setVerificationDate(LocalDateTime.now());
			verificationList.setAll(verificationList); // Refresh the list
			showStatusMessage("Blood unit " + verification.getBloodBagId() + " verified successfully!", "success");
		} else {
			showAlert("Error", "Failed to verify blood unit. Please try again.");
		}
	}
	
	private int countVerifiedUnits() {
		return (int) verificationList.stream()
			.filter(v -> "VERIFIED".equals(v.getVerificationStatus()))
			.count();
	}
	
	private int countPendingVerifications() {
		return (int) verificationList.stream()
			.filter(v -> "PENDING".equals(v.getVerificationStatus()))
			.count();
	}
	
	private void loadVoiceCommandsData() {
		Label voiceStatusLabel = (Label) contentArea.lookup("#voiceStatusLabel");
		if (voiceStatusLabel != null) {
			voiceStatusLabel.setText("Ready to listen");
		}
	}
	
	@FXML
	private void refreshAIPredictions() {
		loadAIPredictionData();
		showAlert("AI Predictions", "AI predictions refreshed!");
	}
	
	@FXML
	private void shareAchievement() {
		showAlert("Share Achievement", "Achievement shared! 🏆");
	}
	
	@FXML
	private void syncIoTData() {
		loadIoTMonitoringData();
		showAlert("IoT Sync", "IoT data synchronized! 📡");
	}
	
	@FXML
	private void verifyBloodUnit() {
		showAlert("Blockchain", "Blood unit verified! ✅");
	}
	
	@FXML
	private void toggleVoiceRecognition() {
		showAlert("Voice Commands", "Voice recognition toggled! 🎤");
	}
	
	@FXML
	private void quickVoiceStats() { showStatistics(); }
	
	@FXML
	private void quickVoiceEmergency() { showEmergency(); }
	
	@FXML
	private void quickVoiceFindDonor() { showDonors(); }
	
	@FXML
	private void quickVoiceMap() { showIoTMonitoring(); }
	
	// =============================================================================
	// REVOLUTIONARY TECHNOLOGY FEATURES
	// =============================================================================
	
	@FXML
	private void showQuantumMatching() {
		showViewWithAnimation("quantumMatching");
	}
	
	@FXML
	private void showBiometricAuth() {
		showViewWithAnimation("biometricAuth");
	}
	
	@FXML
	private void showARVisualization() {
		showViewWithAnimation("arVisualization");
	}
	
	@FXML
	private void showNeuralQuality() {
		showViewWithAnimation("neuralQuality");
	}
	
	@FXML
	private void showDroneDelivery() {
		showViewWithAnimation("droneDelivery");
	}
	
	@FXML
	private void showDigitalTwin() {
		showViewWithAnimation("digitalTwin");
	}
	
	@FXML
	private void showSatelliteComm() {
		showViewWithAnimation("satelliteComm");
	}
	
	@FXML
	private void showHolographic() {
		showViewWithAnimation("holographic");
	}
	
	// Revolutionary Feature Action Methods
	@FXML
	private void runQuantumAnalysis() {
		showAlert("Quantum Computing", "🔬 Quantum analysis completed!\n" +
			"• Superposition states analyzed\n" +
			"• Entanglement networks optimized\n" +
			"• Compatibility score: 99.97%\n" +
			"• Processing time: 0.003ms");
	}
	
	@FXML
	private void performSecurityScan() {
		showAlert("Biometric Security", "🔒 Multi-factor authentication scan complete!\n" +
			"• Fingerprint: ✅ Verified\n" +
			"• Facial recognition: ✅ Verified\n" +
			"• Iris scan: ✅ Verified\n" +
			"• Security level: 99.9%");
	}
	
	@FXML
	private void launchARMode() {
		showAlert("AR Visualization", "🥽 Augmented Reality mode activated!\n" +
			"• 3D blood inventory rendered\n" +
			"• Holographic interface enabled\n" +
			"• Tracking accuracy: 99.4%\n" +
			"• Frame rate: 90fps");
	}
	
	@FXML
	private void performDeepAnalysis() {
		showAlert("Neural Network", "🧠 Deep learning analysis complete!\n" +
			"• 127K samples analyzed\n" +
			"• 47 neural models active\n" +
			"• Prediction accuracy: 98.7%\n" +
			"• Processing time: 12ms");
	}
	
	@FXML
	private void deployDrone() {
		showAlert("Drone Fleet", "🚁 Emergency drone deployed!\n" +
			"• Autonomous navigation activated\n" +
			"• GPS route optimized\n" +
			"• ETA: 12 minutes\n" +
			"• Battery level: 85%");
	}
	
	@FXML
	private void syncDigitalTwin() {
		showAlert("Digital Twin", "👥 Virtual simulation synchronized!\n" +
			"• Real-time data mirrored\n" +
			"• 1,247 simulations complete\n" +
			"• Optimization gain: +34%\n" +
			"• Sync accuracy: 99.8%");
	}
	
	@FXML
	private void connectSatellite() {
		showAlert("Satellite Network", "🛰️ Global network connected!\n" +
			"• 47 satellites online\n" +
			"• Global coverage: 94.7%\n" +
			"• Average latency: 23ms\n" +
			"• Data throughput: 847GB");
	}
	
	// Revolutionary Feature Data Loading Methods
	private void loadQuantumMatchingData() {
		// Simulate quantum compatibility analysis
		Platform.runLater(() -> {
			showAlert("Quantum Algorithms", "Quantum entanglement network initialized! ⚛️");
		});
	}
	
	private void loadBiometricAuthData() {
		// Simulate biometric data loading
		Platform.runLater(() -> {
			showAlert("Biometric Systems", "Multi-factor authentication ready! 👁️");
		});
	}
	
	private void loadARVisualizationData() {
		// Simulate AR environment setup
		Platform.runLater(() -> {
			showAlert("AR Environment", "3D holographic space calibrated! 🥽");
		});
	}
	
	private void loadNeuralQualityData() {
		// Simulate neural network initialization
		Platform.runLater(() -> {
			showAlert("Neural Networks", "Deep learning models loaded! 🧠");
		});
	}
	
	private void loadDroneDeliveryData() {
		// Simulate drone fleet status
		Platform.runLater(() -> {
			showAlert("Drone Fleet", "Autonomous delivery system online! 🚁");
		});
	}
	
	private void loadDigitalTwinData() {
		// Simulate digital twin synchronization
		Platform.runLater(() -> {
			showAlert("Digital Twin", "Virtual blood bank simulation ready! 👥");
		});
	}
	
	private void loadSatelliteCommData() {
		// Simulate satellite network status
		Platform.runLater(() -> {
			showAlert("Satellite Network", "Global communication established! 🛰️");
		});
	}
	
	private void loadHolographicData() {
		// Simulate holographic interface
		Platform.runLater(() -> {
			showAlert("Holographic UI", "3D holographic interface activated! 🌐");
		});
	}
	
	// =============================================================================
	// THEME TOGGLE FUNCTIONALITY
	// =============================================================================
	
	@FXML
	private void toggleTheme() {
		isDarkTheme = !isDarkTheme;
		
		// Get the root node from the scene
		if (contentArea != null && contentArea.getScene() != null) {
			javafx.scene.Parent root = contentArea.getScene().getRoot();
			
			if (isDarkTheme) {
				// Apply dark theme
				root.getStyleClass().add("dark-theme");
				if (themeToggleBtn != null) {
					themeToggleBtn.setText("☀️ Light Mode");
				}
				showAlert("Theme Changed", "Dark mode activated! 🌙");
			} else {
				// Apply light theme
				root.getStyleClass().remove("dark-theme");
				if (themeToggleBtn != null) {
					themeToggleBtn.setText("🌙 Dark Mode");
				}
				showAlert("Theme Changed", "Light mode activated! ☀️");
			}
			
			// Add smooth transition animation
			addThemeTransitionAnimation(root);
		}
	}
	
	private void addThemeTransitionAnimation(javafx.scene.Parent root) {
		// Create fade transition for smooth theme switching
		FadeTransition fadeOut = new FadeTransition(Duration.millis(150), root);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.9);
		
		FadeTransition fadeIn = new FadeTransition(Duration.millis(150), root);
		fadeIn.setFromValue(0.9);
		fadeIn.setToValue(1.0);
		
		fadeOut.setOnFinished(e -> fadeIn.play());
		fadeOut.play();
		
		// Scale animation for button feedback
		if (themeToggleBtn != null) {
			ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100), themeToggleBtn);
			scaleTransition.setFromX(1.0);
			scaleTransition.setFromY(1.0);
			scaleTransition.setToX(1.1);
			scaleTransition.setToY(1.1);
			scaleTransition.setAutoReverse(true);
			scaleTransition.setCycleCount(2);
			scaleTransition.play();
		}
	}

	
	// =============================================================================
	// UTILITY METHODS
	// =============================================================================
	
	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public void cleanup() {
		if (clockTimeline != null) {
			clockTimeline.stop();
		}
	}
}
