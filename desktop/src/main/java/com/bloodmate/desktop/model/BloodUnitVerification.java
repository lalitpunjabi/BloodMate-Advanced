package com.bloodmate.desktop.model;

<<<<<<< HEAD
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
=======
import javafx.beans.property.*;
import java.time.LocalDateTime;
import java.util.UUID;

public class BloodUnitVerification {
    private final StringProperty id = new SimpleStringProperty(); // Blockchain transaction ID
    private final StringProperty bloodBagId = new SimpleStringProperty();
    private final StringProperty donorId = new SimpleStringProperty();
    private final StringProperty donorName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> collectionDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> verificationDate = new SimpleObjectProperty<>();
    private final StringProperty verificationStatus = new SimpleStringProperty(); // PENDING, VERIFIED, INVALID
    private final StringProperty verifier = new SimpleStringProperty(); // Who verified (hospital, lab, etc.)
    private final StringProperty location = new SimpleStringProperty(); // Where verification happened
    private final StringProperty hash = new SimpleStringProperty(); // Blockchain hash
    private final StringProperty previousHash = new SimpleStringProperty(); // Previous block hash
    private final StringProperty signature = new SimpleStringProperty(); // Digital signature
    private final StringProperty notes = new SimpleStringProperty();
    
    // Constructors
    public BloodUnitVerification() {
        this.id.set(UUID.randomUUID().toString());
        this.verificationDate.set(LocalDateTime.now());
        this.verificationStatus.set("PENDING");
    }
    
    public BloodUnitVerification(String bloodBagId, String donorId, String donorName, String bloodGroup) {
        this();
        this.bloodBagId.set(bloodBagId);
        this.donorId.set(donorId);
        this.donorName.set(donorName);
        this.bloodGroup.set(bloodGroup);
    }
    
    // Getters and Setters
    public String getId() {
        return id.get();
    }
    
    public StringProperty idProperty() {
        return id;
    }
    
    public void setId(String id) {
        this.id.set(id);
    }
    
    public String getBloodBagId() {
        return bloodBagId.get();
    }
    
    public StringProperty bloodBagIdProperty() {
        return bloodBagId;
    }
    
    public void setBloodBagId(String bloodBagId) {
        this.bloodBagId.set(bloodBagId);
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
    
    public String getBloodGroup() {
        return bloodGroup.get();
    }
    
    public StringProperty bloodGroupProperty() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup.set(bloodGroup);
    }
    
    public LocalDateTime getCollectionDate() {
        return collectionDate.get();
    }
    
    public ObjectProperty<LocalDateTime> collectionDateProperty() {
        return collectionDate;
    }
    
    public void setCollectionDate(LocalDateTime collectionDate) {
        this.collectionDate.set(collectionDate);
    }
    
    public LocalDateTime getVerificationDate() {
        return verificationDate.get();
    }
    
    public ObjectProperty<LocalDateTime> verificationDateProperty() {
        return verificationDate;
    }
    
    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate.set(verificationDate);
    }
    
    public String getVerificationStatus() {
        return verificationStatus.get();
    }
    
    public StringProperty verificationStatusProperty() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus.set(verificationStatus);
    }
    
    public String getVerifier() {
        return verifier.get();
    }
    
    public StringProperty verifierProperty() {
        return verifier;
    }
    
    public void setVerifier(String verifier) {
        this.verifier.set(verifier);
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
    
    public String getHash() {
        return hash.get();
    }
    
    public StringProperty hashProperty() {
        return hash;
    }
    
    public void setHash(String hash) {
        this.hash.set(hash);
    }
    
    public String getPreviousHash() {
        return previousHash.get();
    }
    
    public StringProperty previousHashProperty() {
        return previousHash;
    }
    
    public void setPreviousHash(String previousHash) {
        this.previousHash.set(previousHash);
    }
    
    public String getSignature() {
        return signature.get();
    }
    
    public StringProperty signatureProperty() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature.set(signature);
    }
    
    public String getNotes() {
        return notes.get();
    }
    
    public StringProperty notesProperty() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    
    @Override
    public String toString() {
        return "BloodUnitVerification{" +
                "id='" + id.get() + '\'' +
                ", bloodBagId='" + bloodBagId.get() + '\'' +
                ", donorId='" + donorId.get() + '\'' +
                ", donorName='" + donorName.get() + '\'' +
                ", bloodGroup='" + bloodGroup.get() + '\'' +
                ", collectionDate=" + collectionDate.get() +
                ", verificationDate=" + verificationDate.get() +
                ", verificationStatus='" + verificationStatus.get() + '\'' +
                ", verifier='" + verifier.get() + '\'' +
                ", location='" + location.get() + '\'' +
                ", hash='" + hash.get() + '\'' +
                ", previousHash='" + previousHash.get() + '\'' +
                ", signature='" + signature.get() + '\'' +
                ", notes='" + notes.get() + '\'' +
                '}';
    }
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
}