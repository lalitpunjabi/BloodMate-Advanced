package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BloodUnit {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty bloodType = new SimpleStringProperty();
    private final StringProperty donorId = new SimpleStringProperty();
    private final StringProperty collectionDate = new SimpleStringProperty();
    private final StringProperty expiryDate = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty testingStatus = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getBloodType() {
        return bloodType.get();
    }

    public void setBloodType(String bloodType) {
        this.bloodType.set(bloodType);
    }

    public StringProperty bloodTypeProperty() {
        return bloodType;
    }

    public String getDonorId() {
        return donorId.get();
    }

    public void setDonorId(String donorId) {
        this.donorId.set(donorId);
    }

    public StringProperty donorIdProperty() {
        return donorId;
    }

    public String getCollectionDate() {
        return collectionDate.get();
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate.set(collectionDate);
    }

    public StringProperty collectionDateProperty() {
        return collectionDate;
    }

    public String getExpiryDate() {
        return expiryDate.get();
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate.set(expiryDate);
    }

    public StringProperty expiryDateProperty() {
        return expiryDate;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getTestingStatus() {
        return testingStatus.get();
    }

    public void setTestingStatus(String testingStatus) {
        this.testingStatus.set(testingStatus);
    }

    public StringProperty testingStatusProperty() {
        return testingStatus;
    }
}