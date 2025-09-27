package com.bloodmate.desktop.report;

import com.bloodmate.desktop.model.*;
import com.bloodmate.desktop.repo.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Custom report dialog for generating advanced reports in BloodMate
 */
public class CustomReportDialog extends Dialog<CustomReportDialog.ReportParameters> {
    
    private ComboBox<String> reportTypeCombo;
    private ComboBox<String> reportFormatCombo;
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private CheckBox includeChartsCheckBox;
    private CheckBox includeSummaryCheckBox;
    private TextField customTitleField;
    
    public CustomReportDialog(Window owner) {
        setTitle("Generate Custom Report");
        setHeaderText("Create a customized report with specific parameters");
        
        // Create UI components
        initializeComponents();
        
        // Create layout
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Report Type Section
        VBox typeBox = new VBox(5);
        typeBox.getChildren().addAll(new Label("Report Type:"), reportTypeCombo);
        
        // Format Section
        VBox formatBox = new VBox(5);
        formatBox.getChildren().addAll(new Label("Report Format:"), reportFormatCombo);
        
        // Date Range Section
        GridPane dateGrid = new GridPane();
        dateGrid.setHgap(10);
        dateGrid.setVgap(5);
        dateGrid.add(new Label("Start Date:"), 0, 0);
        dateGrid.add(startDatePicker, 1, 0);
        dateGrid.add(new Label("End Date:"), 0, 1);
        dateGrid.add(endDatePicker, 1, 1);
        
        VBox dateBox = new VBox(5);
        dateBox.getChildren().addAll(new Label("Date Range:"), dateGrid);
        
        // Options Section
        VBox optionsBox = new VBox(10);
        optionsBox.getChildren().addAll(includeChartsCheckBox, includeSummaryCheckBox);
        
        // Custom Title Section
        VBox titleBox = new VBox(5);
        titleBox.getChildren().addAll(new Label("Custom Report Title:"), customTitleField);
        
        // Add all sections to content
        content.getChildren().addAll(typeBox, formatBox, dateBox, optionsBox, titleBox);
        
        getDialogPane().setContent(content);
        
        // Add buttons
        ButtonType generateButtonType = new ButtonType("Generate Report", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(generateButtonType, ButtonType.CANCEL);
        
        // Set result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == generateButtonType) {
                return new ReportParameters(
                    reportTypeCombo.getValue(),
                    reportFormatCombo.getValue(),
                    startDatePicker.getValue(),
                    endDatePicker.getValue(),
                    includeChartsCheckBox.isSelected(),
                    includeSummaryCheckBox.isSelected(),
                    customTitleField.getText()
                );
            }
            return null;
        });
        
        // Set default dates (last 30 days)
        LocalDate today = LocalDate.now();
        startDatePicker.setValue(today.minusDays(30));
        endDatePicker.setValue(today);
    }
    
    private void initializeComponents() {
        // Report Type Combo
        reportTypeCombo = new ComboBox<>();
        reportTypeCombo.getItems().addAll(
            "Blood Statistics Summary",
            "Donor Registration Report",
            "Recipient Request Report",
            "Blood Inventory Status",
            "Campaign Performance",
            "Emergency Response Report",
            "Donor Engagement Metrics",
            "Financial Summary Report"
        );
        reportTypeCombo.setValue("Blood Statistics Summary");
        
        // Report Format Combo
        reportFormatCombo = new ComboBox<>();
        reportFormatCombo.getItems().addAll("CSV", "PDF", "Excel", "Text");
        reportFormatCombo.setValue("CSV");
        
        // Date Pickers
        startDatePicker = new DatePicker();
        endDatePicker = new DatePicker();
        
        // Set converters for better date display
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.toString() : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string) : null;
            }
        };
        
        startDatePicker.setConverter(converter);
        endDatePicker.setConverter(converter);
        
        // Checkboxes
        includeChartsCheckBox = new CheckBox("Include Charts and Graphs");
        includeChartsCheckBox.setSelected(true);
        
        includeSummaryCheckBox = new CheckBox("Include Executive Summary");
        includeSummaryCheckBox.setSelected(true);
        
        // Custom Title Field
        customTitleField = new TextField();
        customTitleField.setPromptText("Enter custom report title (optional)");
    }
    
    /**
     * Parameters for custom report generation
     */
    public static class ReportParameters {
        private final String reportType;
        private final String reportFormat;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final boolean includeCharts;
        private final boolean includeSummary;
        private final String customTitle;
        
        public ReportParameters(String reportType, String reportFormat, LocalDate startDate, 
                              LocalDate endDate, boolean includeCharts, boolean includeSummary, 
                              String customTitle) {
            this.reportType = reportType;
            this.reportFormat = reportFormat;
            this.startDate = startDate;
            this.endDate = endDate;
            this.includeCharts = includeCharts;
            this.includeSummary = includeSummary;
            this.customTitle = customTitle;
        }
        
        // Getters
        public String getReportType() { return reportType; }
        public String getReportFormat() { return reportFormat; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public boolean isIncludeCharts() { return includeCharts; }
        public boolean isIncludeSummary() { return includeSummary; }
        public String getCustomTitle() { return customTitle; }
    }
}