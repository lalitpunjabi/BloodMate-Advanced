package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDateTime;

public class BloodUnitVerification {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty bloodUnitId = new SimpleStringProperty();
    private final StringProperty verificationHash = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> verificationDate = new SimpleObjectProperty<>();
    private final StringProperty verifiedBy = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getBloodUnitId() {
        return bloodUnitId.get();
    }

    public void setBloodUnitId(String bloodUnitId) {
        this.bloodUnitId.set(bloodUnitId);
    }

    public StringProperty bloodUnitIdProperty() {
        return bloodUnitId;
    }

    public String getVerificationHash() {
        return verificationHash.get();
    }

    public void setVerificationHash(String verificationHash) {
        this.verificationHash.set(verificationHash);
    }

    public StringProperty verificationHashProperty() {
        return verificationHash;
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

    public LocalDateTime getVerificationDate() {
        return verificationDate.get();
    }

    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate.set(verificationDate);
    }

    public ObjectProperty<LocalDateTime> verificationDateProperty() {
        return verificationDate;
    }

    public String getVerifiedBy() {
        return verifiedBy.get();
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy.set(verifiedBy);
    }

    public StringProperty verifiedByProperty() {
        return verifiedBy;
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
}