package com.bloodmate.desktop.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.time.LocalDateTime;

public class DonorPoints {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty donorId = new SimpleStringProperty();
    private final StringProperty donorName = new SimpleStringProperty();
    private final IntegerProperty points = new SimpleIntegerProperty();
    private final StringProperty badge = new SimpleStringProperty();
    private final StringProperty lastActivity = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> lastUpdated = new SimpleObjectProperty<>();

    public DonorPoints() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.points.set(0);
        this.lastUpdated.set(LocalDateTime.now());
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

    public String getDonorId() {
        return donorId.get();
    }

    public StringProperty donorIdProperty() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId.set(donorId);
    }

    public String getDonorName() {
        return donorName.get();
    }

    public StringProperty donorNameProperty() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName.set(donorName);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public String getBadge() {
        return badge.get();
    }

    public StringProperty badgeProperty() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge.set(badge);
    }

    public String getLastActivity() {
        return lastActivity.get();
    }

    public StringProperty lastActivityProperty() {
        return lastActivity;
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity.set(lastActivity);
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated.get();
    }

    public ObjectProperty<LocalDateTime> lastUpdatedProperty() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated.set(lastUpdated);
    }

    @Override
    public String toString() {
        return "DonorPoints{" +
                "id='" + id.get() + '\'' +
                ", donorId='" + donorId.get() + '\'' +
                ", donorName='" + donorName.get() + '\'' +
                ", points=" + points.get() +
                ", badge='" + badge.get() + '\'' +
                ", lastActivity='" + lastActivity.get() + '\'' +
                ", lastUpdated=" + lastUpdated.get() +
                '}';
    }
}