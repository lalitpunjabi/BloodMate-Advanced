package com.bloodmate.desktop.report;

import com.bloodmate.desktop.model.BloodStatistics;
import com.bloodmate.desktop.repo.BloodStatisticsDao;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages scheduled report generation for BloodMate application
 */
public class ScheduledReportManager {
    private Timer reportTimer;
    private BloodStatisticsDao statisticsDao;
    private boolean isRunning = false;
    
    public ScheduledReportManager(BloodStatisticsDao statisticsDao) {
        this.statisticsDao = statisticsDao;
    }
    
    /**
     * Start scheduled report generation
     * @param intervalHours Interval in hours between reports
     */
    public void startScheduledReports(int intervalHours) {
        if (isRunning) {
            stopScheduledReports();
        }
        
        reportTimer = new Timer("ReportGenerator", true);
        long intervalMillis = intervalHours * 60 * 60 * 1000L; // Convert hours to milliseconds
        
        reportTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                generateScheduledReport();
            }
        }, 0, intervalMillis); // Start immediately, then repeat at interval
        
        isRunning = true;
        showAlert(Alert.AlertType.INFORMATION, "Scheduled Reports", 
                "Scheduled report generation started. Reports will be generated every " + intervalHours + " hours.");
    }
    
    /**
     * Stop scheduled report generation
     */
    public void stopScheduledReports() {
        if (reportTimer != null) {
            reportTimer.cancel();
            reportTimer = null;
            isRunning = false;
            showAlert(Alert.AlertType.INFORMATION, "Scheduled Reports", 
                    "Scheduled report generation stopped.");
        }
    }
    
    /**
     * Generate a scheduled report
     */
    private void generateScheduledReport() {
        try {
            // In a real implementation, this would gather current data and generate a report
            // For now, we'll just show a notification
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Scheduled Report", 
                        "Scheduled report generated at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            });
        } catch (Exception e) {
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.ERROR, "Scheduled Report Error", 
                        "Failed to generate scheduled report: " + e.getMessage());
            });
        }
    }
    
    /**
     * Check if scheduled reports are running
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Show alert dialog
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }
}