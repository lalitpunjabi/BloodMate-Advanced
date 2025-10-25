package com.bloodmate.desktop.model;

<<<<<<< HEAD
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
=======
import javafx.beans.property.*;

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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty idProperty() {
        return id;
    }

<<<<<<< HEAD
    public String getDonorName() {
        return donorName.get();
    }

    public void setDonorName(String donorName) {
        this.donorName.set(donorName);
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty donorNameProperty() {
        return donorName;
    }

<<<<<<< HEAD
    public int getPoints() {
        return Integer.parseInt(points.get());
    }

    public void setPoints(int points) {
        this.points.set(String.valueOf(points));
    }

    public StringProperty pointsProperty() {
        return points;
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public String getBadge() {
        return badge.get();
    }

<<<<<<< HEAD
    public void setBadge(String badge) {
        this.badge.set(badge);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public StringProperty badgeProperty() {
        return badge;
    }

<<<<<<< HEAD
=======
    public void setBadge(String badge) {
        this.badge.set(badge);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public String getLastActivity() {
        return lastActivity.get();
    }

<<<<<<< HEAD
=======
    public StringProperty lastActivityProperty() {
        return lastActivity;
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public void setLastActivity(String lastActivity) {
        this.lastActivity.set(lastActivity);
    }

<<<<<<< HEAD
    public StringProperty lastActivityProperty() {
        return lastActivity;
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }
}