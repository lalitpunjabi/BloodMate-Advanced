package com.bloodmate.desktop.util;

import javafx.scene.control.Dialog;
import javafx.stage.Window;

public class ScheduledReportDialog extends Dialog<Void> {
    public static class ScheduleParameters {
        private String frequency;
        private String startTime;
        private String recipients;

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getRecipients() {
            return recipients;
        }

        public void setRecipients(String recipients) {
            this.recipients = recipients;
        }
    }

    public ScheduledReportDialog(Window owner, Object scheduledReportManager) {
        setTitle("Schedule Report");
        setHeaderText("Schedule Report Generation");
        // Implementation would go here
    }
}