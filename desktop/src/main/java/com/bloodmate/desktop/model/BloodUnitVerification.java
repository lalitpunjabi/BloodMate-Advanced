package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class BloodUnitVerification {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty bloodBagId = new SimpleStringProperty();
    private final StringProperty donorId = new SimpleStringProperty();
    private final StringProperty donorName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> collectionDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> verificationDate = new SimpleObjectProperty<>();
    private final StringProperty verificationStatus = new SimpleStringProperty();
    private final StringProperty verifier = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty hash = new SimpleStringProperty();
    private final StringProperty previousHash = new SimpleStringProperty();
    private final StringProperty signature = new SimpleStringProperty();
    private final StringProperty notes = new SimpleStringProperty();

    // Constructors
    public BloodUnitVerification() {
        this.id.set(UUID.randomUUID().toString());
        this.verificationDate.set(LocalDateTime.now());
        this.verificationStatus.set("PENDING");
    }
    
    public String getStatus() {
        return verificationStatus.get();
    }
    
    public void setStatus(String status) {
        this.verificationStatus.set(status);
    }
    
    public String getBloodUnitId() {
        return bloodBagId.get();
    }
    
    public void setBloodUnitId(String bloodUnitId) {
        this.bloodBagId.set(bloodUnitId);
    }
    
    public String getVerificationHash() {
        return hash.get();
    }
    
    public void setVerificationHash(String hash) {
        this.hash.set(hash);
    }
    
    public String getVerifiedBy() {
        return verifier.get();
    }
    
    public void setVerifiedBy(String verifier) {
        this.verifier.set(verifier);
    }

    public BloodUnitVerification(String bloodBagId, String donorId, String donorName, String bloodGroup) {
        this();
        this.bloodBagId.set(bloodBagId);
        this.donorId.set(donorId);
        this.donorName.set(donorName);
        this.bloodGroup.set(bloodGroup);
    }

    // Getters and Setters
    public String getId() { return id.get(); }
    public void setId(String id) { this.id.set(id); }
    public StringProperty idProperty() { return id; }

    public String getBloodBagId() { return bloodBagId.get(); }
    public void setBloodBagId(String bloodBagId) { this.bloodBagId.set(bloodBagId); }
    public StringProperty bloodBagIdProperty() { return bloodBagId; }

    public String getDonorId() { return donorId.get(); }
    public void setDonorId(String donorId) { this.donorId.set(donorId); }
    public StringProperty donorIdProperty() { return donorId; }

    public String getDonorName() { return donorName.get(); }
    public void setDonorName(String donorName) { this.donorName.set(donorName); }
    public StringProperty donorNameProperty() { return donorName; }

    public String getBloodGroup() { return bloodGroup.get(); }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup.set(bloodGroup); }
    public StringProperty bloodGroupProperty() { return bloodGroup; }

    public LocalDateTime getCollectionDate() { return collectionDate.get(); }
    public void setCollectionDate(LocalDateTime collectionDate) { this.collectionDate.set(collectionDate); }
    public ObjectProperty<LocalDateTime> collectionDateProperty() { return collectionDate; }

    public LocalDateTime getVerificationDate() { return verificationDate.get(); }
    public void setVerificationDate(LocalDateTime verificationDate) { this.verificationDate.set(verificationDate); }
    public ObjectProperty<LocalDateTime> verificationDateProperty() { return verificationDate; }

    public String getVerificationStatus() { return verificationStatus.get(); }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus.set(verificationStatus); }
    public StringProperty verificationStatusProperty() { return verificationStatus; }

    public String getVerifier() { return verifier.get(); }
    public void setVerifier(String verifier) { this.verifier.set(verifier); }
    public StringProperty verifierProperty() { return verifier; }

    public String getLocation() { return location.get(); }
    public void setLocation(String location) { this.location.set(location); }
    public StringProperty locationProperty() { return location; }

    public String getHash() { return hash.get(); }
    public void setHash(String hash) { this.hash.set(hash); }
    public StringProperty hashProperty() { return hash; }

    public String getPreviousHash() { return previousHash.get(); }
    public void setPreviousHash(String previousHash) { this.previousHash.set(previousHash); }
    public StringProperty previousHashProperty() { return previousHash; }

    public String getSignature() { return signature.get(); }
    public void setSignature(String signature) { this.signature.set(signature); }
    public StringProperty signatureProperty() { return signature; }

    public String getNotes() { return notes.get(); }
    public void setNotes(String notes) { this.notes.set(notes); }
    public StringProperty notesProperty() { return notes; }

    @Override
    public String toString() {
        return "BloodUnitVerification{" +
                "id='" + getId() + '\'' +
                ", bloodBagId='" + getBloodBagId() + '\'' +
                ", donorId='" + getDonorId() + '\'' +
                ", donorName='" + getDonorName() + '\'' +
                ", bloodGroup='" + getBloodGroup() + '\'' +
                ", collectionDate=" + getCollectionDate() +
                ", verificationDate=" + getVerificationDate() +
                ", verificationStatus='" + getVerificationStatus() + '\'' +
                ", verifier='" + getVerifier() + '\'' +
                ", location='" + getLocation() + '\'' +
                ", hash='" + getHash() + '\'' +
                ", previousHash='" + getPreviousHash() + '\'' +
                ", signature='" + getSignature() + '\'' +
                ", notes='" + getNotes() + '\'' +
                '}';
    }
}
