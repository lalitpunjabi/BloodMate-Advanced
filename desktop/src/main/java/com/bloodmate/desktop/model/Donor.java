package com.bloodmate.desktop.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Donor {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
    private final StringProperty gender = new SimpleStringProperty();
    private final StringProperty address = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final StringProperty state = new SimpleStringProperty();
    private final StringProperty postalCode = new SimpleStringProperty();
    private final StringProperty country = new SimpleStringProperty();
    private final StringProperty eligibilityStatus = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> lastDonationDate = new SimpleObjectProperty<>();
    private final IntegerProperty totalDonations = new SimpleIntegerProperty();
    private final DoubleProperty weightKg = new SimpleDoubleProperty();
    private final IntegerProperty heightCm = new SimpleIntegerProperty();
    private final StringProperty medicalConditions = new SimpleStringProperty();
    private final StringProperty emergencyContactName = new SimpleStringProperty();
    private final StringProperty emergencyContactPhone = new SimpleStringProperty();
    private final IntegerProperty rewardPoints = new SimpleIntegerProperty();
    private final StringProperty tierLevel = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> updatedAt = new SimpleObjectProperty<>();

    // Basic properties (existing)
    public String getId() { return id.get(); }
    public void setId(String v) { id.set(v); }
    public StringProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public void setName(String v) { name.set(v); }
    public StringProperty nameProperty() { return name; }

    public String getEmail() { return email.get(); }
    public void setEmail(String v) { email.set(v); }
    public StringProperty emailProperty() { return email; }

    public String getPhone() { return phone.get(); }
    public void setPhone(String v) { phone.set(v); }
    public StringProperty phoneProperty() { return phone; }

    public String getBloodGroup() { return bloodGroup.get(); }
    public void setBloodGroup(String v) { bloodGroup.set(v); }
    public StringProperty bloodGroupProperty() { return bloodGroup; }

    // Additional properties
    public LocalDate getDateOfBirth() { return dateOfBirth.get(); }
    public void setDateOfBirth(LocalDate v) { dateOfBirth.set(v); }
    public ObjectProperty<LocalDate> dateOfBirthProperty() { return dateOfBirth; }

    public String getGender() { return gender.get(); }
    public void setGender(String v) { gender.set(v); }
    public StringProperty genderProperty() { return gender; }

    public String getAddress() { return address.get(); }
    public void setAddress(String v) { address.set(v); }
    public StringProperty addressProperty() { return address; }

    public String getCity() { return city.get(); }
    public void setCity(String v) { city.set(v); }
    public StringProperty cityProperty() { return city; }

    public String getState() { return state.get(); }
    public void setState(String v) { state.set(v); }
    public StringProperty stateProperty() { return state; }

    public String getPostalCode() { return postalCode.get(); }
    public void setPostalCode(String v) { postalCode.set(v); }
    public StringProperty postalCodeProperty() { return postalCode; }

    public String getCountry() { return country.get(); }
    public void setCountry(String v) { country.set(v); }
    public StringProperty countryProperty() { return country; }

    public String getEligibilityStatus() { return eligibilityStatus.get(); }
    public void setEligibilityStatus(String v) { eligibilityStatus.set(v); }
    public StringProperty eligibilityStatusProperty() { return eligibilityStatus; }

    public LocalDate getLastDonationDate() { return lastDonationDate.get(); }
    public void setLastDonationDate(LocalDate v) { lastDonationDate.set(v); }
    public ObjectProperty<LocalDate> lastDonationDateProperty() { return lastDonationDate; }

    public int getTotalDonations() { return totalDonations.get(); }
    public void setTotalDonations(int v) { totalDonations.set(v); }
    public IntegerProperty totalDonationsProperty() { return totalDonations; }

    public double getWeightKg() { return weightKg.get(); }
    public void setWeightKg(double v) { weightKg.set(v); }
    public DoubleProperty weightKgProperty() { return weightKg; }

    public int getHeightCm() { return heightCm.get(); }
    public void setHeightCm(int v) { heightCm.set(v); }
    public IntegerProperty heightCmProperty() { return heightCm; }

    public String getMedicalConditions() { return medicalConditions.get(); }
    public void setMedicalConditions(String v) { medicalConditions.set(v); }
    public StringProperty medicalConditionsProperty() { return medicalConditions; }

    public String getEmergencyContactName() { return emergencyContactName.get(); }
    public void setEmergencyContactName(String v) { emergencyContactName.set(v); }
    public StringProperty emergencyContactNameProperty() { return emergencyContactName; }

    public String getEmergencyContactPhone() { return emergencyContactPhone.get(); }
    public void setEmergencyContactPhone(String v) { emergencyContactPhone.set(v); }
    public StringProperty emergencyContactPhoneProperty() { return emergencyContactPhone; }

    public int getRewardPoints() { return rewardPoints.get(); }
    public void setRewardPoints(int v) { rewardPoints.set(v); }
    public IntegerProperty rewardPointsProperty() { return rewardPoints; }

    public String getTierLevel() { return tierLevel.get(); }
    public void setTierLevel(String v) { tierLevel.set(v); }
    public StringProperty tierLevelProperty() { return tierLevel; }

    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(LocalDateTime v) { createdAt.set(v); }
    public ObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt.get(); }
    public void setUpdatedAt(LocalDateTime v) { updatedAt.set(v); }
    public ObjectProperty<LocalDateTime> updatedAtProperty() { return updatedAt; }
}
