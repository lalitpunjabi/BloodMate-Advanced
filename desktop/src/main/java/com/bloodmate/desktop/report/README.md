# BloodMate Reporting System

## Overview
The BloodMate Reporting System provides comprehensive data export and analytics capabilities for the BloodMate Blood Bank Management System. This system enables users to generate detailed reports, export data in multiple formats, and schedule automatic report generation.

## Features

### 1. Data Export Capabilities
- **CSV Export**: Export all data types to CSV format for easy analysis in spreadsheet applications
- **Individual Data Exports**: Export specific data types (donors, recipients, inventory, campaigns) separately
- **Dashboard Reports**: Generate comprehensive summary reports in text format
- **Multiple Format Support**: CSV, Text (additional formats like PDF and Excel in future versions)

### 2. Report Types
- **Statistics Reports**: Blood statistics and inventory analytics
- **Donor Reports**: Donor registration and engagement data
- **Recipient Reports**: Patient requests and blood matching data
- **Inventory Reports**: Blood unit tracking and expiration monitoring
- **Campaign Reports**: Blood drive performance and participation metrics
- **Dashboard Summary**: Comprehensive overview of all system metrics

### 3. Advanced Features
- **Custom Report Generation**: Create reports with specific parameters and date ranges
- **Scheduled Reports**: Automatically generate reports at regular intervals
- **Advanced Analytics**: Detailed insights and recommendations based on data trends
- **Executive Summaries**: High-level overviews for management review

## Implementation Details

### ReportGenerator Class
The `ReportGenerator` class provides static methods for exporting data to various formats:

```java
// Export statistics to CSV
ReportGenerator.exportStatisticsToCSV(statisticsList, window);

// Export donors to CSV
ReportGenerator.exportDonorsToCSV(donorsList, window);

// Generate dashboard report
ReportGenerator.generateDashboardReport(statistics, donors, recipients, inventory, campaigns, window);
```

### CustomReportDialog Class
The `CustomReportDialog` class provides a user interface for creating custom reports with specific parameters:

```java
CustomReportDialog dialog = new CustomReportDialog(ownerWindow);
Optional<CustomReportDialog.ReportParameters> result = dialog.showAndWait();
```

### ScheduledReportManager Class
The `ScheduledReportManager` class handles automatic report generation:

```java
ScheduledReportManager manager = new ScheduledReportManager(statisticsDao);
manager.startScheduledReports(24); // Generate reports every 24 hours
```

## Usage

### Exporting Data
1. Navigate to the Statistics & Reports section
2. Click on the desired export button:
   - "Export CSV" for statistics data
   - "Donors CSV" for donor data
   - "Recipients CSV" for recipient data
   - "Inventory CSV" for inventory data
   - "Campaigns CSV" for campaign data
   - "Export All Data" to export everything at once

### Generating Custom Reports
1. Click on "Custom Report" button
2. Select report type, format, and date range
3. Configure additional options
4. Click "Generate Report"

### Scheduling Reports
1. Click on "Scheduled Reports" button
2. Enable scheduled reporting
3. Set frequency and interval
4. Click "Start Scheduling"

## Future Enhancements
- PDF report generation with professional formatting
- Excel export with charts and graphs
- Email delivery of scheduled reports
- Real-time dashboard with interactive charts
- Advanced filtering and sorting options
- Report templates and branding customization

## Dependencies
- JavaFX for UI components
- Standard Java I/O for file operations
- BloodMate domain models and DAOs for data access

## Error Handling
The reporting system includes comprehensive error handling:
- File I/O exceptions are caught and displayed to users
- Invalid data is handled gracefully
- User-friendly error messages for common issues
- Logging of system errors for debugging