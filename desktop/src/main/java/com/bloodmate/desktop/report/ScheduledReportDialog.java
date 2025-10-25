package com.bloodmate.desktop.report;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.time.LocalTime;

/**
 * Dialog for configuring scheduled report generation
 */
public class ScheduledReportDialog extends Dialog<ScheduledReportDialog.ScheduleParameters> {
    
    private ComboBox<String> frequencyCombo;
    private Spinner<Integer> intervalSpinner;
    private CheckBox enableSchedulingCheckBox;
    private Label nextRunLabel;
    private Button startStopButton;
    
    private ScheduledReportManager reportManager;
    
    public ScheduledReportDialog(Window owner, ScheduledReportManager reportManager) {
        this.reportManager = reportManager;
        
        setTitle("Scheduled Reports");
        setHeaderText("Configure automatic report generation");
        
        // Create UI components
        initializeComponents();
        
        // Create layout
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Enable scheduling section
        VBox enableBox = new VBox(5);
        enableBox.getChildren().addAll(enableSchedulingCheckBox);
        
        // Frequency section
        VBox frequencyBox = new VBox(5);
        frequencyBox.getChildren().addAll(new Label("Report Frequency:"), frequencyCombo);
        
        // Interval section
        VBox intervalBox = new VBox(5);
        intervalBox.getChildren().addAll(new Label("Interval (hours):"), intervalSpinner);
        
        // Next run section
        VBox nextRunBox = new VBox(5);
        nextRunBox.getChildren().addAll(new Label("Next scheduled run:"), nextRunLabel);
        
        // Start/Stop button
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(startStopButton);
        
        // Add all sections to content
        content.getChildren().addAll(enableBox, frequencyBox, intervalBox, nextRunBox, buttonBox);
        
        getDialogPane().setContent(content);
        
        // Add OK button
        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        
        // Set result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new ScheduleParameters(
                    enableSchedulingCheckBox.isSelected(),
                    frequencyCombo.getValue(),
                    intervalSpinner.getValue(),
                    nextRunLabel.getText()
                );
            }
            return null;
        });
        
        // Update UI based on current state
        updateUI();
        
        // Add listeners
        enableSchedulingCheckBox.setOnAction(e -> updateUI());
        frequencyCombo.setOnAction(e -> updateNextRunTime());
        intervalSpinner.getValueFactory().valueProperty().addListener((obs, oldVal, newVal) -> updateNextRunTime());
    }
    
    private void initializeComponents() {
        // Enable scheduling checkbox
        enableSchedulingCheckBox = new CheckBox("Enable scheduled report generation");
        enableSchedulingCheckBox.setSelected(reportManager.isRunning());
        
        // Frequency combo
        frequencyCombo = new ComboBox<>();
        frequencyCombo.getItems().addAll("Hourly", "Daily", "Weekly");
        frequencyCombo.setValue("Daily");
        
        // Interval spinner
        intervalSpinner = new Spinner<>(1, 168, 24); // 1 hour to 1 week (168 hours)
        intervalSpinner.setEditable(true);
        intervalSpinner.getValueFactory().setValue(24); // Default to daily
        
        // Next run label
        nextRunLabel = new Label("Not scheduled");
        
        // Start/Stop button
        startStopButton = new Button(reportManager.isRunning() ? "Stop Scheduling" : "Start Scheduling");
        startStopButton.setOnAction(e -> toggleScheduling());
    }
    
    private void updateUI() {
        boolean enabled = enableSchedulingCheckBox.isSelected();
        frequencyCombo.setDisable(!enabled);
        intervalSpinner.setDisable(!enabled);
        
        if (enabled) {
            updateNextRunTime();
        } else {
            nextRunLabel.setText("Not scheduled");
        }
        
        startStopButton.setText(reportManager.isRunning() ? "Stop Scheduling" : "Start Scheduling");
    }
    
    private void updateNextRunTime() {
        if (enableSchedulingCheckBox.isSelected()) {
            int interval = intervalSpinner.getValue();
            String frequency = frequencyCombo.getValue();
            
            // Calculate next run time (simplified)
            String nextRun = "Next " + frequency.toLowerCase() + " at " + 
                           LocalTime.now().plusHours(interval).toString();
            nextRunLabel.setText(nextRun);
        }
    }
    
    private void toggleScheduling() {
        if (reportManager.isRunning()) {
            reportManager.stopScheduledReports();
        } else {
            int interval = intervalSpinner.getValue();
            reportManager.startScheduledReports(interval);
        }
        updateUI();
    }
    
    /**
     * Parameters for scheduled report configuration
     */
    public static class ScheduleParameters {
        private final boolean enabled;
        private final String frequency;
        private final int interval;
        private final String nextRunTime;
        
        public ScheduleParameters(boolean enabled, String frequency, int interval, String nextRunTime) {
            this.enabled = enabled;
            this.frequency = frequency;
            this.interval = interval;
            this.nextRunTime = nextRunTime;
        }
        
        // Getters
        public boolean isEnabled() { return enabled; }
        public String getFrequency() { return frequency; }
        public int getInterval() { return interval; }
        public String getNextRunTime() { return nextRunTime; }
    }
}