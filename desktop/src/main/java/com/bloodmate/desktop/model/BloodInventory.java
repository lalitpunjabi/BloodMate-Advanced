package com.bloodmate.desktop.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BloodInventory {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty bagId = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> donationDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> expiryDate = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty volume = new SimpleDoubleProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final DoubleProperty temperature = new SimpleDoubleProperty();
    private final IntegerProperty qualityScore = new SimpleIntegerProperty();
    private final StringProperty donorId = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> lastUpdated = new SimpleObjectProperty<>();
    private final StringProperty notes = new SimpleStringProperty();
    private final BooleanProperty isReserved = new SimpleBooleanProperty();
    private final StringProperty reservedFor = new SimpleStringProperty();
    private final StringProperty testResults = new SimpleStringProperty();

    // ===== ID =====
    public String getId() { return id.get(); }
    public void setId(String v) { id.set(v); }
    public StringProperty idProperty() { return id; }

    // ===== Blood Group =====
    public String getBloodGroup() { return bloodGroup.get(); }
    public void setBloodGroup(String v) { bloodGroup.set(v); }
    public StringProperty bloodGroupProperty() { return bloodGroup; }

    // ===== Bag ID =====
    public String getBagId() { return bagId.get(); }
    public void setBagId(String v) { bagId.set(v); }
    public StringProperty bagIdProperty() { return bagId; }

    // ===== Donation Date =====
    public LocalDate getDonationDate() { return donationDate.get(); }
    public void setDonationDate(LocalDate v) { donationDate.set(v); }
    public ObjectProperty<LocalDate> donationDateProperty() { return donationDate; }

    // ===== Expiry Date =====
    public LocalDate getExpiryDate() { return expiryDate.get(); }
    public void setExpiryDate(LocalDate v) { expiryDate.set(v); }
    public ObjectProperty<LocalDate> expiryDateProperty() { return expiryDate; }

    // ===== Status =====
    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    // ===== Volume =====
    public double getVolume() { return volume.get(); }
    public void setVolume(double v) { volume.set(v); }
    public DoubleProperty volumeProperty() { return volume; }

    // ===== Location =====
    public String getLocation() { return location.get(); }
    public void setLocation(String v) { location.set(v); }
    public StringProperty locationProperty() { return location; }

    // ===== Temperature =====
    public double getTemperature() { return temperature.get(); }
    public void setTemperature(double v) { temperature.set(v); }
    public DoubleProperty temperatureProperty() { return temperature; }

    // ===== Quality Score =====
    public int getQualityScore() { return qualityScore.get(); }
    public void setQualityScore(int v) { qualityScore.set(v); }
    public IntegerProperty qualityScoreProperty() { return qualityScore; }

    // ===== Donor ID =====
    public String getDonorId() { return donorId.get(); }
    public void setDonorId(String v) { donorId.set(v); }
    public StringProperty donorIdProperty() { return donorId; }

    // ===== Last Updated =====
    public LocalDateTime getLastUpdated() { return lastUpdated.get(); }
    public void setLastUpdated(LocalDateTime v) { lastUpdated.set(v); }
    public ObjectProperty<LocalDateTime> lastUpdatedProperty() { return lastUpdated; }

    // ===== Notes =====
    public String getNotes() { return notes.get(); }
    public void setNotes(String v) { notes.set(v); }
    public StringProperty notesProperty() { return notes; }

    // ===== Is Reserved =====
    public boolean getIsReserved() { return isReserved.get(); }
    public void setIsReserved(boolean v) { isReserved.set(v); }
    public BooleanProperty isReservedProperty() { return isReserved; }

    // ===== Reserved For =====
    public String getReservedFor() { return reservedFor.get(); }
    public void setReservedFor(String v) { reservedFor.set(v); }
    public StringProperty reservedForProperty() { return reservedFor; }

    // ===== Test Results =====
    public String getTestResults() { return testResults.get(); }
    public void setTestResults(String v) { testResults.set(v); }
    public StringProperty testResultsProperty() { return testResults; }
}
