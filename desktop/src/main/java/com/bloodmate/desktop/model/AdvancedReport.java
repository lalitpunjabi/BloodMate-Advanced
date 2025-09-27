package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDate;

/**
 * Advanced report model for BloodMate application
 * Contains detailed analytics and metrics
 */
public class AdvancedReport {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> generatedDate = new SimpleObjectProperty<>();
    private final StringProperty reportType = new SimpleStringProperty();
    private final IntegerProperty totalRecords = new SimpleIntegerProperty();
    private final DoubleProperty averageValue = new SimpleDoubleProperty();
    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty keyInsights = new SimpleStringProperty();
    private final StringProperty recommendations = new SimpleStringProperty();
    
    public AdvancedReport() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.generatedDate.set(LocalDate.now());
    }
    
    // Getters and setters
    public String getId() {
        return id.get();
    }
    
    public StringProperty idProperty() {
        return id;
    }
    
    public void setId(String id) {
        this.id.set(id);
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public LocalDate getGeneratedDate() {
        return generatedDate.get();
    }
    
    public ObjectProperty<LocalDate> generatedDateProperty() {
        return generatedDate;
    }
    
    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate.set(generatedDate);
    }
    
    public String getReportType() {
        return reportType.get();
    }
    
    public StringProperty reportTypeProperty() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType.set(reportType);
    }
    
    public int getTotalRecords() {
        return totalRecords.get();
    }
    
    public IntegerProperty totalRecordsProperty() {
        return totalRecords;
    }
    
    public void setTotalRecords(int totalRecords) {
        this.totalRecords.set(totalRecords);
    }
    
    public double getAverageValue() {
        return averageValue.get();
    }
    
    public DoubleProperty averageValueProperty() {
        return averageValue;
    }
    
    public void setAverageValue(double averageValue) {
        this.averageValue.set(averageValue);
    }
    
    public String getSummary() {
        return summary.get();
    }
    
    public StringProperty summaryProperty() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary.set(summary);
    }
    
    public String getKeyInsights() {
        return keyInsights.get();
    }
    
    public StringProperty keyInsightsProperty() {
        return keyInsights;
    }
    
    public void setKeyInsights(String keyInsights) {
        this.keyInsights.set(keyInsights);
    }
    
    public String getRecommendations() {
        return recommendations.get();
    }
    
    public StringProperty recommendationsProperty() {
        return recommendations;
    }
    
    public void setRecommendations(String recommendations) {
        this.recommendations.set(recommendations);
    }
    
    @Override
    public String toString() {
        return "AdvancedReport{" +
                "id='" + id.get() + '\'' +
                ", title='" + title.get() + '\'' +
                ", generatedDate=" + generatedDate.get() +
                ", reportType='" + reportType.get() + '\'' +
                ", totalRecords=" + totalRecords.get() +
                ", averageValue=" + averageValue.get() +
                '}';
    }
}