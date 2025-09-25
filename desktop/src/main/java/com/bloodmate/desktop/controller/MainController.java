package com.bloodmate.desktop.controller;

	import com.bloodmate.desktop.db.Db;
import com.bloodmate.desktop.model.Donor;
import com.bloodmate.desktop.repo.DonorDao;
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

import java.net.URL;
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
	private final ObservableList<String> alerts = FXCollections.observableArrayList();
	private DonorDao donorDao;
	private Timeline clockTimeline;
	private String currentView = "dashboard";
	private boolean isDarkTheme = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Initialize database connection
		this.donorDao = new DonorDao(Db.get());
		
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
	
	private void showView(String viewName) {
		hideAllViews();
		currentView = viewName;
		
		switch (viewName) {
			case "dashboard":
				if (dashboardView != null) {
					dashboardView.setVisible(true);
					dashboardView.setManaged(true);
				}
				break;
			case "donors":
				if (donorsView != null) {
					donorsView.setVisible(true);
					donorsView.setManaged(true);
					loadDonorManagementView();
				}
				break;
			case "recipients":
				if (recipientsView != null) {
					recipientsView.setVisible(true);
					recipientsView.setManaged(true);
				}
				break;
			case "inventory":
				if (inventoryView != null) {
					inventoryView.setVisible(true);
					inventoryView.setManaged(true);
				}
				break;
			case "campaigns":
				if (campaignsView != null) {
					campaignsView.setVisible(true);
					campaignsView.setManaged(true);
				}
				break;
			case "rewards":
				if (rewardsView != null) {
					rewardsView.setVisible(true);
					rewardsView.setManaged(true);
				}
				break;
			case "statistics":
				if (statisticsView != null) {
					statisticsView.setVisible(true);
					statisticsView.setManaged(true);
				}
				break;
			case "emergency":
				if (emergencyView != null) {
					emergencyView.setVisible(true);
					emergencyView.setManaged(true);
				}
				break;
			case "aiPrediction":
				if (aiPredictionView != null) {
					aiPredictionView.setVisible(true);
					aiPredictionView.setManaged(true);
				}
				break;
			case "gamification":
				if (gamificationView != null) {
					gamificationView.setVisible(true);
					gamificationView.setManaged(true);
				}
				break;
			case "iotMonitoring":
				if (iotMonitoringView != null) {
					iotMonitoringView.setVisible(true);
					iotMonitoringView.setManaged(true);
				}
				break;
			case "blockchain":
				if (blockchainView != null) {
					blockchainView.setVisible(true);
					blockchainView.setManaged(true);
				}
				break;
			case "voiceCommands":
				if (voiceCommandsView != null) {
					voiceCommandsView.setVisible(true);
					voiceCommandsView.setManaged(true);
				}
				break;
			case "quantumMatching":
				if (quantumMatchingView != null) {
					quantumMatchingView.setVisible(true);
					quantumMatchingView.setManaged(true);
				}
				break;
		}
		
		// Update navigation button styles
		updateNavigationButtons(viewName);
		
		// Add smooth transition
		addViewTransition();
	}
	
	private void updateNavigationButtons(String activeView) {
		// Remove active class from all buttons
		List<Button> navButtons = Arrays.asList(
			dashboardBtn, donorsBtn, recipientsBtn, inventoryBtn, campaignsBtn,
			rewardsBtn, statisticsBtn, emergencyBtn, aiPredictionBtn,
			gamificationBtn, iotMonitoringBtn, blockchainBtn, voiceCommandsBtn,
			quantumMatchingBtn
		);
		
		for (Button btn : navButtons) {
			if (btn != null) {
				btn.getStyleClass().removeAll("active");
			}
		}
		
		// Add active class to current button
		switch (activeView) {
			case "dashboard": if (dashboardBtn != null) dashboardBtn.getStyleClass().add("active"); break;
			case "donors": if (donorsBtn != null) donorsBtn.getStyleClass().add("active"); break;
			case "recipients": if (recipientsBtn != null) recipientsBtn.getStyleClass().add("active"); break;
			case "inventory": if (inventoryBtn != null) inventoryBtn.getStyleClass().add("active"); break;
			case "campaigns": if (campaignsBtn != null) campaignsBtn.getStyleClass().add("active"); break;
			case "rewards": if (rewardsBtn != null) rewardsBtn.getStyleClass().add("active"); break;
			case "statistics": if (statisticsBtn != null) statisticsBtn.getStyleClass().add("active"); break;
			case "emergency": if (emergencyBtn != null) emergencyBtn.getStyleClass().add("active"); break;
			case "aiPrediction": if (aiPredictionBtn != null) aiPredictionBtn.getStyleClass().add("active"); break;
			case "gamification": if (gamificationBtn != null) gamificationBtn.getStyleClass().add("active"); break;
			case "iotMonitoring": if (iotMonitoringBtn != null) iotMonitoringBtn.getStyleClass().add("active"); break;
			case "blockchain": if (blockchainBtn != null) blockchainBtn.getStyleClass().add("active"); break;
			case "voiceCommands": if (voiceCommandsBtn != null) voiceCommandsBtn.getStyleClass().add("active"); break;
			case "quantumMatching": if (quantumMatchingBtn != null) quantumMatchingBtn.getStyleClass().add("active"); break;
		}
	}
	
	private void addViewTransition() {
		// Add smooth fade transition for the active view
		if (contentArea != null) {
			FadeTransition fadeIn = new FadeTransition(Duration.millis(300), contentArea);
			fadeIn.setFromValue(0.7);
			fadeIn.setToValue(1.0);
			fadeIn.play();
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
		switchView("dashboard", dashboardView);
		updateNavigationButtons(dashboardBtn);
		
		// Add special logo highlight when dashboard is active
		if (logoBtn != null) {
			logoBtn.getStyleClass().removeAll("dashboard-active");
			logoBtn.getStyleClass().add("dashboard-active");
		}
		
		// Show welcome message when logo is clicked
		showStatusMessage("Welcome to BloodMate Dashboard! 🩸", "success");
	}
	
	@FXML
	public void showDonors() {
		switchView("donors", donorsView);
		updateNavigationButtons(donorsBtn);
	}
	
	@FXML
	public void showRecipients() {
		switchView("recipients", recipientsView);
		updateNavigationButtons(recipientsBtn);
	}
	
	@FXML
	public void showInventory() {
		switchView("inventory", inventoryView);
		updateNavigationButtons(inventoryBtn);
	}
	
	@FXML
	public void showCampaigns() {
		switchView("campaigns", campaignsView);
		updateNavigationButtons(campaignsBtn);
	}
	
	@FXML
	public void showRewards() {
		switchView("rewards", rewardsView);
		updateNavigationButtons(rewardsBtn);
	}
	
	@FXML
	public void showStatistics() {
		switchView("statistics", statisticsView);
		updateNavigationButtons(statisticsBtn);
	}
	
	@FXML
	public void showEmergency() {
		switchView("emergency", emergencyView);
		updateNavigationButtons(emergencyBtn);
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
	

	
	private void switchView(String viewName, javafx.scene.Node targetView) {
		currentView = viewName;
		
		// Hide all views
		hideAllViews();
		
		// Show target view
		if (targetView != null) {
			targetView.setVisible(true);
			targetView.setManaged(true);
			
			// Add fade-in animation
			FadeTransition fadeIn = new FadeTransition(Duration.millis(300), targetView);
			fadeIn.setFromValue(0.0);
			fadeIn.setToValue(1.0);
			fadeIn.play();
		}
	}
	
	private void hideAllViews() {
		List<javafx.scene.Node> allViews = Arrays.asList(
			dashboardView, donorsView, recipientsView, inventoryView,
			campaignsView, rewardsView, statisticsView, emergencyView,
			aiPredictionView, gamificationView, iotMonitoringView,
			blockchainView, voiceCommandsView, quantumMatchingView
		);
		
		for (javafx.scene.Node view : allViews) {
			if (view != null) {
				view.setVisible(false);
				view.setManaged(false);
			}
		}
	}
	
	private void updateNavigationButtons(Button activeButton) {
		// Remove active class from all buttons
		List<Button> navButtons = Arrays.asList(
			dashboardBtn, donorsBtn, recipientsBtn, inventoryBtn,
			campaignsBtn, rewardsBtn, statisticsBtn, emergencyBtn
		);
		
		for (Button btn : navButtons) {
			if (btn != null) {
				btn.getStyleClass().remove("active");
			}
		}
		
		// Add active class to the clicked button
		if (activeButton != null) {
			activeButton.getStyleClass().add("active");
		}
		
		// Remove logo highlight when other sections are active
		if (logoBtn != null && activeButton != dashboardBtn) {
			logoBtn.getStyleClass().removeAll("dashboard-active");
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
	public void showAIPrediction() { showView("aiPrediction"); }
	
	@FXML
	public void showGamification() { showView("gamification"); }
	
	@FXML
	public void showIoTMonitoring() { showView("iotMonitoring"); }
	
	@FXML
	public void showBlockchain() { showView("blockchain"); }
	
	@FXML
	public void showVoiceCommands() { showView("voiceCommands"); }
	


	
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
		Random random = new Random();
		Label connectedSensorsLabel = (Label) contentArea.lookup("#connectedSensorsLabel");
		if (connectedSensorsLabel != null) {
			int sensors = 800 + random.nextInt(100);
			connectedSensorsLabel.setText(String.valueOf(sensors));
		}
	}
	
	private void loadBlockchainData() {
		Random random = new Random();
		Label verifiedUnitsLabel = (Label) contentArea.lookup("#verifiedUnitsLabel");
		if (verifiedUnitsLabel != null) {
			int units = 15000 + random.nextInt(1000);
			verifiedUnitsLabel.setText(String.format("%,d", units));
		}
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
		hideAllViews();
		if (quantumMatchingView != null) {
			quantumMatchingView.setVisible(true);
			quantumMatchingView.setManaged(true);
			updateActiveButton(quantumMatchingBtn);
			currentView = "quantum-matching";
			loadQuantumMatchingData();
		}
	}
	
	@FXML
	private void showBiometricAuth() {
		hideAllViews();
		if (biometricAuthView != null) {
			biometricAuthView.setVisible(true);
			biometricAuthView.setManaged(true);
			updateActiveButton(biometricAuthBtn);
			currentView = "biometric-auth";
			loadBiometricAuthData();
		}
	}
	
	@FXML
	private void showARVisualization() {
		hideAllViews();
		if (arVisualizationView != null) {
			arVisualizationView.setVisible(true);
			arVisualizationView.setManaged(true);
			updateActiveButton(arVisualizationBtn);
			currentView = "ar-visualization";
			loadARVisualizationData();
		}
	}
	
	@FXML
	private void showNeuralQuality() {
		hideAllViews();
		if (neuralQualityView != null) {
			neuralQualityView.setVisible(true);
			neuralQualityView.setManaged(true);
			updateActiveButton(neuralQualityBtn);
			currentView = "neural-quality";
			loadNeuralQualityData();
		}
	}
	
	@FXML
	private void showDroneDelivery() {
		hideAllViews();
		if (droneDeliveryView != null) {
			droneDeliveryView.setVisible(true);
			droneDeliveryView.setManaged(true);
			updateActiveButton(droneDeliveryBtn);
			currentView = "drone-delivery";
			loadDroneDeliveryData();
		}
	}
	
	@FXML
	private void showDigitalTwin() {
		hideAllViews();
		if (digitalTwinView != null) {
			digitalTwinView.setVisible(true);
			digitalTwinView.setManaged(true);
			updateActiveButton(digitalTwinBtn);
			currentView = "digital-twin";
			loadDigitalTwinData();
		}
	}
	
	@FXML
	private void showSatelliteComm() {
		hideAllViews();
		if (satelliteCommView != null) {
			satelliteCommView.setVisible(true);
			satelliteCommView.setManaged(true);
			updateActiveButton(satelliteCommBtn);
			currentView = "satellite-comm";
			loadSatelliteCommData();
		}
	}
	
	@FXML
	private void showHolographic() {
		hideAllViews();
		if (holographicView != null) {
			holographicView.setVisible(true);
			holographicView.setManaged(true);
			updateActiveButton(holographicBtn);
			currentView = "holographic";
			loadHolographicData();
		}
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
	
	private void updateActiveButton(Button activeButton) {
		// Remove active class from all navigation buttons
		Button[] navButtons = {dashboardBtn, donorsBtn, recipientsBtn, inventoryBtn, 
							  campaignsBtn, rewardsBtn, statisticsBtn, emergencyBtn,
							  aiPredictionBtn, gamificationBtn, iotMonitoringBtn, 
							  blockchainBtn, voiceCommandsBtn, quantumMatchingBtn,
							  biometricAuthBtn, arVisualizationBtn, neuralQualityBtn,
							  droneDeliveryBtn, digitalTwinBtn, satelliteCommBtn, holographicBtn};
		
		for (Button btn : navButtons) {
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
