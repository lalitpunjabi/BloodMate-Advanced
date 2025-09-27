package com.bloodmate.desktop.report;

import com.bloodmate.desktop.model.*;
import com.bloodmate.desktop.repo.*;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Report generation utility for BloodMate application
 * Supports exporting data to various formats including CSV, PDF, and Excel
 */
public class ReportGenerator {
    
    /**
     * Export blood statistics to CSV format
     */
    public static void exportStatisticsToCSV(List<BloodStatistics> statistics, Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Statistics Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("bloodmate_statistics_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".csv");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    // Write CSV header
                    writer.println("Report Date,Total Donors,Total Recipients,Total Blood Units," +
                            "A+ Units,A- Units,B+ Units,B- Units,AB+ Units,AB- Units,O+ Units,O- Units," +
                            "Expiring Soon,Active Campaigns,Emergency Requests");
                    
                    // Write data rows
                    for (BloodStatistics stat : statistics) {
                        writer.printf("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d%n",
                                stat.getReportDate(),
                                stat.getTotalDonors(),
                                stat.getTotalRecipients(),
                                stat.getTotalBloodUnits(),
                                stat.getAPositiveUnits(),
                                stat.getANegativeUnits(),
                                stat.getBPositiveUnits(),
                                stat.getBNegativeUnits(),
                                stat.getABPositiveUnits(),
                                stat.getABNegativeUnits(),
                                stat.getOPositiveUnits(),
                                stat.getONegativeUnits(),
                                stat.getExpiringSoon(),
                                stat.getActiveCampaigns(),
                                stat.getEmergencyRequests());
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Statistics report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export statistics report: " + e.getMessage());
        }
    }
    
    /**
     * Export donor data to CSV format
     */
    public static void exportDonorsToCSV(List<Donor> donors, Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Donors Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("bloodmate_donors_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".csv");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    // Write CSV header
                    writer.println("ID,Name,Email,Phone,Blood Group,Date of Birth,Gender,Address,City,State,Country," +
                            "Eligibility Status,Last Donation Date,Total Donations,Weight (kg),Height (cm)," +
                            "Medical Conditions,Emergency Contact Name,Emergency Contact Phone,Reward Points,Tier Level," +
                            "Created At,Updated At");
                    
                    // Write data rows
                    for (Donor donor : donors) {
                        writer.printf("%s,%s,%s,%s,%s%n",
                                escapeCSV(donor.getId()),
                                escapeCSV(donor.getName()),
                                escapeCSV(donor.getEmail()),
                                escapeCSV(donor.getPhone()),
                                escapeCSV(donor.getBloodGroup()));
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Donors report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export donors report: " + e.getMessage());
        }
    }
    
    /**
     * Export recipient data to CSV format
     */
    public static void exportRecipientsToCSV(List<Recipient> recipients, Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Recipients Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("bloodmate_recipients_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".csv");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    // Write CSV header
                    writer.println("ID,Patient Name,Blood Group,Medical Facility,Doctor Name,Contact Number,Urgency Level,Status,Units Required,Request Date");
                    
                    // Write data rows
                    for (Recipient recipient : recipients) {
                        writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s%n",
                                escapeCSV(recipient.getId()),
                                escapeCSV(recipient.getPatientName()),
                                escapeCSV(recipient.getBloodGroup()),
                                escapeCSV(recipient.getMedicalFacility()),
                                escapeCSV(recipient.getDoctorName()),
                                escapeCSV(recipient.getContactNumber()),
                                escapeCSV(recipient.getUrgencyLevel()),
                                escapeCSV(recipient.getStatus()),
                                recipient.getUnitsRequired(),
                                recipient.getRequestDate() != null ? recipient.getRequestDate().toString() : "");
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Recipients report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export recipients report: " + e.getMessage());
        }
    }
    
    /**
     * Export blood inventory data to CSV format
     */
    public static void exportInventoryToCSV(List<BloodInventory> inventory, Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Inventory Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("bloodmate_inventory_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".csv");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    // Write CSV header
                    writer.println("ID,Blood Group,Bag ID,Donation Date,Expiry Date,Status,Volume,Location,Temperature,Quality Score,Donor ID,Last Updated,Notes,Is Reserved,Reserved For,Test Results");
                    
                    // Write data rows
                    for (BloodInventory item : inventory) {
                        writer.printf("%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f,%d,%s,%s,%s,%s,%s,%s%n",
                                escapeCSV(item.getId()),
                                escapeCSV(item.getBloodGroup()),
                                escapeCSV(item.getBagId()),
                                item.getDonationDate() != null ? item.getDonationDate().toString() : "",
                                item.getExpiryDate() != null ? item.getExpiryDate().toString() : "",
                                escapeCSV(item.getStatus()),
                                item.getVolume(),
                                escapeCSV(item.getLocation()),
                                item.getTemperature(),
                                item.getQualityScore(),
                                escapeCSV(item.getDonorId()),
                                item.getLastUpdated() != null ? item.getLastUpdated().toString() : "",
                                escapeCSV(item.getNotes()),
                                item.getIsReserved(),
                                escapeCSV(item.getReservedFor()),
                                escapeCSV(item.getTestResults()));
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Inventory report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export inventory report: " + e.getMessage());
        }
    }
    
    /**
     * Export campaign data to CSV format
     */
    public static void exportCampaignsToCSV(List<Campaign> campaigns, Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Campaigns Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            fileChooser.setInitialFileName("bloodmate_campaigns_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".csv");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    // Write CSV header
                    writer.println("ID,Title,Description,Start Date,End Date,Location,City,State,Target Units,Collected Units,Status,Organizer,Contact Number,Created Date,Participant Count,Requirements,Incentives");
                    
                    // Write data rows
                    for (Campaign campaign : campaigns) {
                        writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%d,%d,%s,%s,%s,%s,%d,%s,%s%n",
                                escapeCSV(campaign.getId()),
                                escapeCSV(campaign.getTitle()),
                                escapeCSV(campaign.getDescription()),
                                campaign.getStartDate() != null ? campaign.getStartDate().toString() : "",
                                campaign.getEndDate() != null ? campaign.getEndDate().toString() : "",
                                escapeCSV(campaign.getLocation()),
                                escapeCSV(campaign.getCity()),
                                escapeCSV(campaign.getState()),
                                campaign.getTargetUnits(),
                                campaign.getCollectedUnits(),
                                escapeCSV(campaign.getStatus()),
                                escapeCSV(campaign.getOrganizer()),
                                escapeCSV(campaign.getContactNumber()),
                                campaign.getCreatedDate() != null ? campaign.getCreatedDate().toString() : "",
                                campaign.getParticipantCount(),
                                escapeCSV(campaign.getRequirements()),
                                escapeCSV(campaign.getIncentives()));
                    }
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Campaigns report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export campaigns report: " + e.getMessage());
        }
    }
    
    /**
     * Generate a comprehensive dashboard report in text format
     */
    public static void generateDashboardReport(List<BloodStatistics> statistics, 
                                             List<Donor> donors, 
                                             List<Recipient> recipients, 
                                             List<BloodInventory> inventory, 
                                             List<Campaign> campaigns, 
                                             Window ownerWindow) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Dashboard Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            fileChooser.setInitialFileName("bloodmate_dashboard_report_" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".txt");
            
            File file = fileChooser.showSaveDialog(ownerWindow);
            if (file != null) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    writer.println("========================================");
                    writer.println("         BLOODMATE DASHBOARD REPORT     ");
                    writer.println("========================================");
                    writer.println("Report Generated: " + LocalDate.now());
                    writer.println();
                    
                    // Summary Statistics
                    writer.println("SUMMARY STATISTICS");
                    writer.println("------------------");
                    writer.println("Total Donors: " + donors.size());
                    writer.println("Total Recipients: " + recipients.size());
                    writer.println("Total Blood Units: " + inventory.size());
                    writer.println("Active Campaigns: " + campaigns.size());
                    writer.println();
                    
                    // Blood Group Distribution
                    writer.println("BLOOD GROUP DISTRIBUTION");
                    writer.println("-----------------------");
                    int aPos = 0, aNeg = 0, bPos = 0, bNeg = 0, abPos = 0, abNeg = 0, oPos = 0, oNeg = 0;
                    for (BloodInventory unit : inventory) {
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
                    writer.println("A+: " + aPos + " units");
                    writer.println("A-: " + aNeg + " units");
                    writer.println("B+: " + bPos + " units");
                    writer.println("B-: " + bNeg + " units");
                    writer.println("AB+: " + abPos + " units");
                    writer.println("AB-: " + abNeg + " units");
                    writer.println("O+: " + oPos + " units");
                    writer.println("O-: " + oNeg + " units");
                    writer.println();
                    
                    // Recent Statistics Reports
                    writer.println("RECENT STATISTICS REPORTS");
                    writer.println("-------------------------");
                    if (!statistics.isEmpty()) {
                        for (BloodStatistics stat : statistics.subList(0, Math.min(5, statistics.size()))) {
                            writer.println("Date: " + stat.getReportDate());
                            writer.println("  Donors: " + stat.getTotalDonors());
                            writer.println("  Recipients: " + stat.getTotalRecipients());
                            writer.println("  Blood Units: " + stat.getTotalBloodUnits());
                            writer.println("  Expiring Soon: " + stat.getExpiringSoon());
                            writer.println("  Active Campaigns: " + stat.getActiveCampaigns());
                            writer.println("  Emergency Requests: " + stat.getEmergencyRequests());
                            writer.println();
                        }
                    } else {
                        writer.println("No statistics reports available.");
                        writer.println();
                    }
                    
                    // Campaign Summary
                    writer.println("CAMPAIGN SUMMARY");
                    writer.println("----------------");
                    int activeCampaigns = 0, completedCampaigns = 0, plannedCampaigns = 0;
                    for (Campaign campaign : campaigns) {
                        switch (campaign.getStatus()) {
                            case "ACTIVE": activeCampaigns++; break;
                            case "COMPLETED": completedCampaigns++; break;
                            case "PLANNED": plannedCampaigns++; break;
                        }
                    }
                    writer.println("Active Campaigns: " + activeCampaigns);
                    writer.println("Completed Campaigns: " + completedCampaigns);
                    writer.println("Planned Campaigns: " + plannedCampaigns);
                    writer.println();
                    
                    writer.println("========================================");
                    writer.println("           END OF REPORT                ");
                    writer.println("========================================");
                    
                    showAlert(Alert.AlertType.INFORMATION, "Export Successful", 
                            "Dashboard report exported successfully to: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Export Failed", 
                    "Failed to export dashboard report: " + e.getMessage());
        }
    }
    
    /**
     * Utility method to escape CSV values
     */
    private static String escapeCSV(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
    
    /**
     * Show alert dialog
     */
    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}