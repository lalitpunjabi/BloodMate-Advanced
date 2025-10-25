package com.bloodmate.desktop.model;

<<<<<<< HEAD
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
=======
import javafx.beans.property.*;

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
import java.time.LocalDateTime;

public class EmergencyRequest {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty hospital = new SimpleStringProperty();
    private final StringProperty doctor = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
<<<<<<< HEAD
    private final StringProperty urgencyLevel = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
=======
    private final StringProperty urgencyLevel = new SimpleStringProperty(); // CRITICAL, HIGH, MEDIUM
    private final StringProperty status = new SimpleStringProperty(); // PENDING, ASSIGNED, FULFILLED, CANCELLED
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    private final ObjectProperty<LocalDateTime> requestTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> responseTime = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> fulfillmentTime = new SimpleObjectProperty<>();
    private final StringProperty assignedUnit = new SimpleStringProperty();
<<<<<<< HEAD

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
=======
    private final StringProperty notes = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();

    public EmergencyRequest() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.requestTime.set(LocalDateTime.now());
        this.status.set("PENDING");
        this.urgencyLevel.set("HIGH");
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
    public String getPatientName() {
        return patientName.get();
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
=======
    public void setId(String id) {
        this.id.set(id);
    }

    public String getPatientName() {
        return patientName.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty patientNameProperty() {
        return patientName;
    }

<<<<<<< HEAD
    public String getBloodGroup() {
        return bloodGroup.get();
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
=======
    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public String getBloodGroup() {
        return bloodGroup.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty bloodGroupProperty() {
        return bloodGroup;
    }

<<<<<<< HEAD
    public String getHospital() {
        return hospital.get();
    }

    public void setHospital(String hospital) {
        this.hospital.set(hospital);
=======
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
    }

    public String getHospital() {
        return hospital.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty hospitalProperty() {
        return hospital;
    }

<<<<<<< HEAD
    public String getDoctor() {
        return doctor.get();
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
=======
    public void setHospital(String hospital) {
        this.hospital.set(hospital);
    }

    public String getDoctor() {
        return doctor.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty doctorProperty() {
        return doctor;
    }

<<<<<<< HEAD
    public String getContactNumber() {
        return contactNumber.get();
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
=======
    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public String getContactNumber() {
        return contactNumber.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty contactNumberProperty() {
        return contactNumber;
    }

<<<<<<< HEAD
    public String getUrgencyLevel() {
        return urgencyLevel.get();
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel.set(urgencyLevel);
=======
    public void setContactNumber(String contactNumber) {
        this.contactNumber.set(contactNumber);
    }

    public String getUrgencyLevel() {
        return urgencyLevel.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public StringProperty urgencyLevelProperty() {
        return urgencyLevel;
    }

<<<<<<< HEAD
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
=======
    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel.set(urgencyLevel);
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public String getStatus() {
        return status.get();
    }

<<<<<<< HEAD
    public void setStatus(String status) {
        this.status.set(status);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public StringProperty statusProperty() {
        return status;
    }

<<<<<<< HEAD
=======
    public void setStatus(String status) {
        this.status.set(status);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public LocalDateTime getRequestTime() {
        return requestTime.get();
    }

<<<<<<< HEAD
    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime.set(requestTime);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public ObjectProperty<LocalDateTime> requestTimeProperty() {
        return requestTime;
    }

<<<<<<< HEAD
=======
    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime.set(requestTime);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public LocalDateTime getResponseTime() {
        return responseTime.get();
    }

<<<<<<< HEAD
    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime.set(responseTime);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public ObjectProperty<LocalDateTime> responseTimeProperty() {
        return responseTime;
    }

<<<<<<< HEAD
=======
    public void setResponseTime(LocalDateTime responseTime) {
        this.responseTime.set(responseTime);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public LocalDateTime getFulfillmentTime() {
        return fulfillmentTime.get();
    }

<<<<<<< HEAD
    public void setFulfillmentTime(LocalDateTime fulfillmentTime) {
        this.fulfillmentTime.set(fulfillmentTime);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public ObjectProperty<LocalDateTime> fulfillmentTimeProperty() {
        return fulfillmentTime;
    }

<<<<<<< HEAD
=======
    public void setFulfillmentTime(LocalDateTime fulfillmentTime) {
        this.fulfillmentTime.set(fulfillmentTime);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public String getAssignedUnit() {
        return assignedUnit.get();
    }

<<<<<<< HEAD
=======
    public StringProperty assignedUnitProperty() {
        return assignedUnit;
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public void setAssignedUnit(String assignedUnit) {
        this.assignedUnit.set(assignedUnit);
    }

<<<<<<< HEAD
    public StringProperty assignedUnitProperty() {
        return assignedUnit;
=======
    public String getNotes() {
        return notes.get();
    }

    public StringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    @Override
    public String toString() {
        return "EmergencyRequest{" +
                "id='" + id.get() + '\'' +
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }
}