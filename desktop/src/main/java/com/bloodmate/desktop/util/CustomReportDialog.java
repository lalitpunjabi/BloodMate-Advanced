package com.bloodmate.desktop.util;

import javafx.scene.control.Dialog;
import javafx.stage.Window;

public class CustomReportDialog extends Dialog<Void> {
    public static class ReportParameters {
        private String reportType;
        private String reportFormat;
        private String startDate;
        private String endDate;

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getReportFormat() {
            return reportFormat;
        }

        public void setReportFormat(String reportFormat) {
            this.reportFormat = reportFormat;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
    }

    public CustomReportDialog(Window owner) {
        setTitle("Custom Report");
        setHeaderText("Generate Custom Report");
        // Implementation would go here
    }
}