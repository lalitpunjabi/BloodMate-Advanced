package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AIPrediction {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty predictionType = new SimpleStringProperty(); // BLOOD_DEMAND, DONOR_TRENDS, CAMPAIGN_EFFECTIVENESS
    private final ObjectProperty<LocalDate> predictionDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> generatedAt = new SimpleObjectProperty<>();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final IntegerProperty predictedUnits = new SimpleIntegerProperty();
    private final DoubleProperty confidenceScore = new SimpleDoubleProperty();
    private final StringProperty insights = new SimpleStringProperty();
    private final StringProperty recommendations = new SimpleStringProperty();
    private final StringProperty dataSource = new SimpleStringProperty();

    // Default constructor
    public AIPrediction() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.generatedAt.set(LocalDateTime.now());
    }

    // Getters and Setters
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getPredictionType() {
        return predictionType.get();
    }

    public void setPredictionType(String predictionType) {
        this.predictionType.set(predictionType);
    }

    public StringProperty predictionTypeProperty() {
        return predictionType;
    }

    public LocalDate getPredictionDate() {
        return predictionDate.get();
    }

    public void setPredictionDate(LocalDate predictionDate) {
        this.predictionDate.set(predictionDate);
    }

    public ObjectProperty<LocalDate> predictionDateProperty() {
        return predictionDate;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt.get();
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt.set(generatedAt);
    }

    public ObjectProperty<LocalDateTime> generatedAtProperty() {
        return generatedAt;
    }

    public String getBloodGroup() {
        return bloodGroup.get();
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
    }

    public StringProperty bloodGroupProperty() {
        return bloodGroup;
    }

    public int getPredictedUnits() {
        return predictedUnits.get();
    }

    public void setPredictedUnits(int predictedUnits) {
        this.predictedUnits.set(predictedUnits);
    }

    public IntegerProperty predictedUnitsProperty() {
        return predictedUnits;
    }

    public double getConfidenceScore() {
        return confidenceScore.get();
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore.set(confidenceScore);
    }

    public DoubleProperty confidenceScoreProperty() {
        return confidenceScore;
    }

    public String getInsights() {
        return insights.get();
    }

    public void setInsights(String insights) {
        this.insights.set(insights);
    }

    public StringProperty insightsProperty() {
        return insights;
    }

    public String getRecommendations() {
        return recommendations.get();
    }

    public void setRecommendations(String recommendations) {
        this.recommendations.set(recommendations);
    }

    public StringProperty recommendationsProperty() {
        return recommendations;
    }

    public String getDataSource() {
        return dataSource.get();
    }

    public void setDataSource(String dataSource) {
        this.dataSource.set(dataSource);
    }

    public StringProperty dataSourceProperty() {
        return dataSource;
    }

    @Override
    public String toString() {
        return "AIPrediction{" +
                "id='" + id.get() + '\'' +
                ", predictionType='" + predictionType.get() + '\'' +
                ", predictionDate=" + predictionDate.get() +
                ", generatedAt=" + generatedAt.get() +
                ", bloodGroup='" + bloodGroup.get() + '\'' +
                ", predictedUnits=" + predictedUnits.get() +
                ", confidenceScore=" + confidenceScore.get() +
                ", insights='" + insights.get() + '\'' +
                ", recommendations='" + recommendations.get() + '\'' +
                ", dataSource='" + dataSource.get() + '\'' +
                '}';
    }
}
