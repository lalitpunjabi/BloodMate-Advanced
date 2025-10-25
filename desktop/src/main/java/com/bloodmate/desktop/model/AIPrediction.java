package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;

public class AIPrediction {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty predictionType = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> predictionDate = new SimpleObjectProperty<>();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty predictedUnits = new SimpleStringProperty();
    private final DoubleProperty confidenceScore = new SimpleDoubleProperty();
    private final StringProperty insights = new SimpleStringProperty();
    private final StringProperty recommendations = new SimpleStringProperty();
    private final StringProperty dataSource = new SimpleStringProperty();

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
        return Integer.parseInt(predictedUnits.get());
    }

    public void setPredictedUnits(int predictedUnits) {
        this.predictedUnits.set(String.valueOf(predictedUnits));
    }

    public StringProperty predictedUnitsProperty() {
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
}