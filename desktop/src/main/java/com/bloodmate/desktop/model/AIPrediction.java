package com.bloodmate.desktop.model;

<<<<<<< HEAD
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
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    private final DoubleProperty confidenceScore = new SimpleDoubleProperty();
    private final StringProperty insights = new SimpleStringProperty();
    private final StringProperty recommendations = new SimpleStringProperty();
    private final StringProperty dataSource = new SimpleStringProperty();

<<<<<<< HEAD
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
=======
    public AIPrediction() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.generatedAt.set(LocalDateTime.now());
    }

    // Getters and setters
    public String getId() {
        return id.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty idProperty() {
        return id;
    }

<<<<<<< HEAD
    public String getPredictionType() {
        return predictionType.get();
    }

    public void setPredictionType(String predictionType) {
        this.predictionType.set(predictionType);
=======
    public void setId(String id) {
        this.id.set(id);
    }

    public String getPredictionType() {
        return predictionType.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty predictionTypeProperty() {
        return predictionType;
    }

<<<<<<< HEAD
    public LocalDate getPredictionDate() {
        return predictionDate.get();
    }

    public void setPredictionDate(LocalDate predictionDate) {
        this.predictionDate.set(predictionDate);
=======
    public void setPredictionType(String predictionType) {
        this.predictionType.set(predictionType);
    }

    public LocalDate getPredictionDate() {
        return predictionDate.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public ObjectProperty<LocalDate> predictionDateProperty() {
        return predictionDate;
    }

<<<<<<< HEAD
    public String getBloodGroup() {
        return bloodGroup.get();
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
=======
    public void setPredictionDate(LocalDate predictionDate) {
        this.predictionDate.set(predictionDate);
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt.get();
    }

    public ObjectProperty<LocalDateTime> generatedAtProperty() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt.set(generatedAt);
    }

    public String getBloodGroup() {
        return bloodGroup.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty bloodGroupProperty() {
        return bloodGroup;
    }

<<<<<<< HEAD
    public int getPredictedUnits() {
        return Integer.parseInt(predictedUnits.get());
    }

    public void setPredictedUnits(int predictedUnits) {
        this.predictedUnits.set(String.valueOf(predictedUnits));
    }

    public StringProperty predictedUnitsProperty() {
        return predictedUnits;
=======
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
    }

    public int getPredictedUnits() {
        return predictedUnits.get();
    }

    public IntegerProperty predictedUnitsProperty() {
        return predictedUnits;
    }

    public void setPredictedUnits(int predictedUnits) {
        this.predictedUnits.set(predictedUnits);
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public double getConfidenceScore() {
        return confidenceScore.get();
    }

<<<<<<< HEAD
    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore.set(confidenceScore);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public DoubleProperty confidenceScoreProperty() {
        return confidenceScore;
    }

<<<<<<< HEAD
=======
    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore.set(confidenceScore);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public String getInsights() {
        return insights.get();
    }

<<<<<<< HEAD
    public void setInsights(String insights) {
        this.insights.set(insights);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public StringProperty insightsProperty() {
        return insights;
    }

<<<<<<< HEAD
=======
    public void setInsights(String insights) {
        this.insights.set(insights);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public String getRecommendations() {
        return recommendations.get();
    }

<<<<<<< HEAD
    public void setRecommendations(String recommendations) {
        this.recommendations.set(recommendations);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public StringProperty recommendationsProperty() {
        return recommendations;
    }

<<<<<<< HEAD
=======
    public void setRecommendations(String recommendations) {
        this.recommendations.set(recommendations);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public String getDataSource() {
        return dataSource.get();
    }

<<<<<<< HEAD
=======
    public StringProperty dataSourceProperty() {
        return dataSource;
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public void setDataSource(String dataSource) {
        this.dataSource.set(dataSource);
    }

<<<<<<< HEAD
    public StringProperty dataSourceProperty() {
        return dataSource;
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }
}