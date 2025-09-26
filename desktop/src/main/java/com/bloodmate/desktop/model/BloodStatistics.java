package com.bloodmate.desktop.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Map;

public class BloodStatistics {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> reportDate = new SimpleObjectProperty<>();
    private final IntegerProperty totalDonors = new SimpleIntegerProperty();
    private final IntegerProperty totalRecipients = new SimpleIntegerProperty();
    private final IntegerProperty totalBloodUnits = new SimpleIntegerProperty();
    private final StringProperty bloodGroupDistribution = new SimpleStringProperty();
    private final IntegerProperty expiringSoon = new SimpleIntegerProperty();
    private final IntegerProperty activeCampaigns = new SimpleIntegerProperty();
    private final IntegerProperty emergencyRequests = new SimpleIntegerProperty();
    
    // Blood group specific counts
    private final IntegerProperty aPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty aNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty bPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty bNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty abPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty abNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty oPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty oNegativeUnits = new SimpleIntegerProperty();

    public BloodStatistics() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.reportDate.set(LocalDate.now());
    }

    // Getters and setters
    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public LocalDate getReportDate() {
        return reportDate.get();
    }

    public ObjectProperty<LocalDate> reportDateProperty() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate.set(reportDate);
    }

    public int getTotalDonors() {
        return totalDonors.get();
    }

    public IntegerProperty totalDonorsProperty() {
        return totalDonors;
    }

    public void setTotalDonors(int totalDonors) {
        this.totalDonors.set(totalDonors);
    }

    public int getTotalRecipients() {
        return totalRecipients.get();
    }

    public IntegerProperty totalRecipientsProperty() {
        return totalRecipients;
    }

    public void setTotalRecipients(int totalRecipients) {
        this.totalRecipients.set(totalRecipients);
    }

    public int getTotalBloodUnits() {
        return totalBloodUnits.get();
    }

    public IntegerProperty totalBloodUnitsProperty() {
        return totalBloodUnits;
    }

    public void setTotalBloodUnits(int totalBloodUnits) {
        this.totalBloodUnits.set(totalBloodUnits);
    }

    public String getBloodGroupDistribution() {
        return bloodGroupDistribution.get();
    }

    public StringProperty bloodGroupDistributionProperty() {
        return bloodGroupDistribution;
    }

    public void setBloodGroupDistribution(String bloodGroupDistribution) {
        this.bloodGroupDistribution.set(bloodGroupDistribution);
    }

    public int getExpiringSoon() {
        return expiringSoon.get();
    }

    public IntegerProperty expiringSoonProperty() {
        return expiringSoon;
    }

    public void setExpiringSoon(int expiringSoon) {
        this.expiringSoon.set(expiringSoon);
    }

    public int getActiveCampaigns() {
        return activeCampaigns.get();
    }

    public IntegerProperty activeCampaignsProperty() {
        return activeCampaigns;
    }

    public void setActiveCampaigns(int activeCampaigns) {
        this.activeCampaigns.set(activeCampaigns);
    }

    public int getEmergencyRequests() {
        return emergencyRequests.get();
    }

    public IntegerProperty emergencyRequestsProperty() {
        return emergencyRequests;
    }

    public void setEmergencyRequests(int emergencyRequests) {
        this.emergencyRequests.set(emergencyRequests);
    }

    // Blood group specific getters and setters
    public int getAPositiveUnits() {
        return aPositiveUnits.get();
    }

    public IntegerProperty aPositiveUnitsProperty() {
        return aPositiveUnits;
    }

    public void setAPositiveUnits(int aPositiveUnits) {
        this.aPositiveUnits.set(aPositiveUnits);
    }

    public int getANegativeUnits() {
        return aNegativeUnits.get();
    }

    public IntegerProperty aNegativeUnitsProperty() {
        return aNegativeUnits;
    }

    public void setANegativeUnits(int aNegativeUnits) {
        this.aNegativeUnits.set(aNegativeUnits);
    }

    public int getBPositiveUnits() {
        return bPositiveUnits.get();
    }

    public IntegerProperty bPositiveUnitsProperty() {
        return bPositiveUnits;
    }

    public void setBPositiveUnits(int bPositiveUnits) {
        this.bPositiveUnits.set(bPositiveUnits);
    }

    public int getBNegativeUnits() {
        return bNegativeUnits.get();
    }

    public IntegerProperty bNegativeUnitsProperty() {
        return bNegativeUnits;
    }

    public void setBNegativeUnits(int bNegativeUnits) {
        this.bNegativeUnits.set(bNegativeUnits);
    }

    public int getABPositiveUnits() {
        return abPositiveUnits.get();
    }

    public IntegerProperty abPositiveUnitsProperty() {
        return abPositiveUnits;
    }

    public void setABPositiveUnits(int abPositiveUnits) {
        this.abPositiveUnits.set(abPositiveUnits);
    }

    public int getABNegativeUnits() {
        return abNegativeUnits.get();
    }

    public IntegerProperty abNegativeUnitsProperty() {
        return abNegativeUnits;
    }

    public void setABNegativeUnits(int abNegativeUnits) {
        this.abNegativeUnits.set(abNegativeUnits);
    }

    public int getOPositiveUnits() {
        return oPositiveUnits.get();
    }

    public IntegerProperty oPositiveUnitsProperty() {
        return oPositiveUnits;
    }

    public void setOPositiveUnits(int oPositiveUnits) {
        this.oPositiveUnits.set(oPositiveUnits);
    }

    public int getONegativeUnits() {
        return oNegativeUnits.get();
    }

    public IntegerProperty oNegativeUnitsProperty() {
        return oNegativeUnits;
    }

    public void setONegativeUnits(int oNegativeUnits) {
        this.oNegativeUnits.set(oNegativeUnits);
    }

    @Override
    public String toString() {
        return "BloodStatistics{" +
                "id='" + id.get() + '\'' +
                ", reportDate=" + reportDate.get() +
                ", totalDonors=" + totalDonors.get() +
                ", totalRecipients=" + totalRecipients.get() +
                ", totalBloodUnits=" + totalBloodUnits.get() +
                ", expiringSoon=" + expiringSoon.get() +
                ", activeCampaigns=" + activeCampaigns.get() +
                ", emergencyRequests=" + emergencyRequests.get() +
                '}';
    }
}