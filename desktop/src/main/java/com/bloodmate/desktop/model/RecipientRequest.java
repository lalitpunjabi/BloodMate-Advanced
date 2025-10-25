package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecipientRequest {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty hospital = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty bloodTypeNeeded = new SimpleStringProperty();
    private final StringProperty unitsNeeded = new SimpleStringProperty();
    private final StringProperty priority = new SimpleStringProperty();
    private final StringProperty requestTime = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
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

    public String getPatientName() {
        return patientName.get();
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public StringProperty patientNameProperty() {
        return patientName;
    }

    public String getBloodTypeNeeded() {
        return bloodTypeNeeded.get();
    }

    public void setBloodTypeNeeded(String bloodTypeNeeded) {
        this.bloodTypeNeeded.set(bloodTypeNeeded);
    }

    public StringProperty bloodTypeNeededProperty() {
        return bloodTypeNeeded;
    }

    public String getUnitsNeeded() {
        return unitsNeeded.get();
    }

    public void setUnitsNeeded(String unitsNeeded) {
        this.unitsNeeded.set(unitsNeeded);
    }

    public StringProperty unitsNeededProperty() {
        return unitsNeeded;
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public StringProperty priorityProperty() {
        return priority;
    }

    public String getRequestTime() {
        return requestTime.get();
    }

    public void setRequestTime(String requestTime) {
        this.requestTime.set(requestTime);
    }

    public StringProperty requestTimeProperty() {
        return requestTime;
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
}