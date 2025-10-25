package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DonorPoints {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty donorName = new SimpleStringProperty();
    private final StringProperty points = new SimpleStringProperty();
    private final StringProperty badge = new SimpleStringProperty();
    private final StringProperty lastActivity = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getDonorName() {
        return donorName.get();
    }

    public void setDonorName(String donorName) {
        this.donorName.set(donorName);
    }

    public StringProperty donorNameProperty() {
        return donorName;
    }

    public int getPoints() {
        return Integer.parseInt(points.get());
    }

    public void setPoints(int points) {
        this.points.set(String.valueOf(points));
    }

    public StringProperty pointsProperty() {
        return points;
    }

    public String getBadge() {
        return badge.get();
    }

    public void setBadge(String badge) {
        this.badge.set(badge);
    }

    public StringProperty badgeProperty() {
        return badge;
    }

    public String getLastActivity() {
        return lastActivity.get();
    }

    public void setLastActivity(String lastActivity) {
        this.lastActivity.set(lastActivity);
    }

    public StringProperty lastActivityProperty() {
        return lastActivity;
    }
}