package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDateTime;

public class EmergencyRequest {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty hospital = new SimpleStringProperty();
    private final StringProperty doctor = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final StringProperty urgencyLevel = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> requestTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> responseTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> fulfillmentTime = new SimpleObjectProperty<>();
    private final StringProperty assignedUnit = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getPatientName() {
        return patientName.get();
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public StringProperty patientNameProperty() {
        return patientName;
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

    public String getHospital() {
        return hospital.get();
    }

    public void setHospital(String hospital) {
        this.hospital.set(hospital);
    }

    public StringProperty hospitalProperty() {
        return hospital;
    }

    public String getDoctor() {
        return doctor.get();
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public StringProperty doctorProperty() {
        return doctor;
    }

    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

    public String getUrgencyLevel() {
        return urgencyLevel.get();
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel.set(urgencyLevel);
    }

    public StringProperty urgencyLevelProperty() {
        return urgencyLevel;
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

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty() {
        return notes;
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

    public LocalDateTime getRequestTime() {
        return requestTime.get();
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime.set(requestTime);
    }

    public ObjectProperty<LocalDateTime> requestTimeProperty() {
        return requestTime;
    }

    public LocalDateTime getResponseTime() {
        return responseTime.get();
    }

    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime.set(responseTime);
    }

    public ObjectProperty<LocalDateTime> responseTimeProperty() {
        return responseTime;
    }

    public LocalDateTime getFulfillmentTime() {
        return fulfillmentTime.get();
    }

    public void setFulfillmentTime(LocalDateTime fulfillmentTime) {
        this.fulfillmentTime.set(fulfillmentTime);
    }

    public ObjectProperty<LocalDateTime> fulfillmentTimeProperty() {
        return fulfillmentTime;
    }

    public String getAssignedUnit() {
        return assignedUnit.get();
    }

    public void setAssignedUnit(String assignedUnit) {
        this.assignedUnit.set(assignedUnit);
    }

    public StringProperty assignedUnitProperty() {
        return assignedUnit;
    }
}