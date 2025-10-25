package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;

public class BloodStatistics {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> reportDate = new SimpleObjectProperty<>();
    private final IntegerProperty totalDonors = new SimpleIntegerProperty();
    private final IntegerProperty totalRecipients = new SimpleIntegerProperty();
    private final IntegerProperty totalBloodUnits = new SimpleIntegerProperty();
    private final IntegerProperty activeCampaigns = new SimpleIntegerProperty();
    private final IntegerProperty expiringSoon = new SimpleIntegerProperty();
    private final IntegerProperty emergencyRequests = new SimpleIntegerProperty();
    private final IntegerProperty aPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty aNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty bPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty bNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty abPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty abNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty oPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty oNegativeUnits = new SimpleIntegerProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public LocalDate getReportDate() {
        return reportDate.get();
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate.set(reportDate);
    }

    public ObjectProperty<LocalDate> reportDateProperty() {
        return reportDate;
    }

    public int getTotalDonors() {
        return totalDonors.get();
    }

    public void setTotalDonors(int totalDonors) {
        this.totalDonors.set(totalDonors);
    }

    public IntegerProperty totalDonorsProperty() {
        return totalDonors;
    }

    public int getTotalRecipients() {
        return totalRecipients.get();
    }

    public void setTotalRecipients(int totalRecipients) {
        this.totalRecipients.set(totalRecipients);
    }

    public IntegerProperty totalRecipientsProperty() {
        return totalRecipients;
    }

    public int getTotalBloodUnits() {
        return totalBloodUnits.get();
    }

    public void setTotalBloodUnits(int totalBloodUnits) {
        this.totalBloodUnits.set(totalBloodUnits);
    }

    public IntegerProperty totalBloodUnitsProperty() {
        return totalBloodUnits;
    }

    public int getActiveCampaigns() {
        return activeCampaigns.get();
    }

    public void setActiveCampaigns(int activeCampaigns) {
        this.activeCampaigns.set(activeCampaigns);
    }

    public IntegerProperty activeCampaignsProperty() {
        return activeCampaigns;
    }

    public int getExpiringSoon() {
        return expiringSoon.get();
    }

    public void setExpiringSoon(int expiringSoon) {
        this.expiringSoon.set(expiringSoon);
    }

    public IntegerProperty expiringSoonProperty() {
        return expiringSoon;
    }

    public int getEmergencyRequests() {
        return emergencyRequests.get();
    }

    public void setEmergencyRequests(int emergencyRequests) {
        this.emergencyRequests.set(emergencyRequests);
    }

    public IntegerProperty emergencyRequestsProperty() {
        return emergencyRequests;
    }

    public int getAPositiveUnits() {
        return aPositiveUnits.get();
    }

    public void setAPositiveUnits(int aPositiveUnits) {
        this.aPositiveUnits.set(aPositiveUnits);
    }

    public IntegerProperty aPositiveUnitsProperty() {
        return aPositiveUnits;
    }

    public int getANegativeUnits() {
        return aNegativeUnits.get();
    }

    public void setANegativeUnits(int aNegativeUnits) {
        this.aNegativeUnits.set(aNegativeUnits);
    }

    public IntegerProperty aNegativeUnitsProperty() {
        return aNegativeUnits;
    }

    public int getBPositiveUnits() {
        return bPositiveUnits.get();
    }

    public void setBPositiveUnits(int bPositiveUnits) {
        this.bPositiveUnits.set(bPositiveUnits);
    }

    public IntegerProperty bPositiveUnitsProperty() {
        return bPositiveUnits;
    }

    public int getBNegativeUnits() {
        return bNegativeUnits.get();
    }

    public void setBNegativeUnits(int bNegativeUnits) {
        this.bNegativeUnits.set(bNegativeUnits);
    }

    public IntegerProperty bNegativeUnitsProperty() {
        return bNegativeUnits;
    }

    public int getABPositiveUnits() {
        return abPositiveUnits.get();
    }

    public void setABPositiveUnits(int abPositiveUnits) {
        this.abPositiveUnits.set(abPositiveUnits);
    }

    public IntegerProperty abPositiveUnitsProperty() {
        return abPositiveUnits;
    }

    public int getABNegativeUnits() {
        return abNegativeUnits.get();
    }

    public void setABNegativeUnits(int abNegativeUnits) {
        this.abNegativeUnits.set(abNegativeUnits);
    }

    public IntegerProperty abNegativeUnitsProperty() {
        return abNegativeUnits;
    }

    public int getOPositiveUnits() {
        return oPositiveUnits.get();
    }

    public void setOPositiveUnits(int oPositiveUnits) {
        this.oPositiveUnits.set(oPositiveUnits);
    }

    public IntegerProperty oPositiveUnitsProperty() {
        return oPositiveUnits;
    }

    public int getONegativeUnits() {
        return oNegativeUnits.get();
    }

    public void setONegativeUnits(int oNegativeUnits) {
        this.oNegativeUnits.set(oNegativeUnits);
    }

    public IntegerProperty oNegativeUnitsProperty() {
        return oNegativeUnits;
    }
}