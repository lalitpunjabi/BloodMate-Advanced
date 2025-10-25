package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class EmergencyRequest {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty hospital = new SimpleStringProperty();
    private final StringProperty doctor = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final StringProperty urgencyLevel = new SimpleStringProperty(); // CRITICAL, HIGH, MEDIUM
    private final StringProperty status = new SimpleStringProperty(); // PENDING, ASSIGNED, FULFILLED, CANCELLED
    private final ObjectProperty<LocalDateTime> requestTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> responseTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> fulfillmentTime = new SimpleObjectProperty<>();
    private final StringProperty assignedUnit = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();

    public EmergencyRequest() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.requestTime.set(LocalDateTime.now());
        this.status.set("PENDING");
        this.urgencyLevel.set("HIGH");
    }

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

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public StringProperty notesProperty() {
        return notes;
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

    @Override
    public String toString() {
        return "EmergencyRequest{" +
                "id=" + id.get() +
                ", patientName='" + patientName.get() + '\'' +
                ", bloodGroup='" + bloodGroup.get() + '\'' +
                ", hospital='" + hospital.get() + '\'' +
                ", doctor='" + doctor.get() + '\'' +
                ", contactNumber='" + contactNumber.get() + '\'' +
                ", urgencyLevel='" + urgencyLevel.get() + '\'' +
                ", status='" + status.get() + '\'' +
                ", requestTime=" + requestTime.get() +
                ", responseTime=" + responseTime.get() +
                ", fulfillmentTime=" + fulfillmentTime.get() +
                ", assignedUnit='" + assignedUnit.get() + '\'' +
                ", notes='" + notes.get() + '\'' +
                ", location='" + location.get() + '\'' +
                '}';
    }
}