package com.bloodmate.desktop.model;

<<<<<<< HEAD
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
=======
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Map;
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697

public class BloodStatistics {
    private final StringProperty id = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> reportDate = new SimpleObjectProperty<>();
    private final IntegerProperty totalDonors = new SimpleIntegerProperty();
    private final IntegerProperty totalRecipients = new SimpleIntegerProperty();
    private final IntegerProperty totalBloodUnits = new SimpleIntegerProperty();
<<<<<<< HEAD
    private final IntegerProperty activeCampaigns = new SimpleIntegerProperty();
    private final IntegerProperty expiringSoon = new SimpleIntegerProperty();
    private final IntegerProperty emergencyRequests = new SimpleIntegerProperty();
=======
    private final StringProperty bloodGroupDistribution = new SimpleStringProperty();
    private final IntegerProperty expiringSoon = new SimpleIntegerProperty();
    private final IntegerProperty activeCampaigns = new SimpleIntegerProperty();
    private final IntegerProperty emergencyRequests = new SimpleIntegerProperty();
    
    // Blood group specific counts
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    private final IntegerProperty aPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty aNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty bPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty bNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty abPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty abNegativeUnits = new SimpleIntegerProperty();
    private final IntegerProperty oPositiveUnits = new SimpleIntegerProperty();
    private final IntegerProperty oNegativeUnits = new SimpleIntegerProperty();

<<<<<<< HEAD
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
=======
    public BloodStatistics() {
        this.id.set(java.util.UUID.randomUUID().toString());
        this.reportDate.set(LocalDate.now());
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
    public LocalDate getReportDate() {
        return reportDate.get();
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate.set(reportDate);
=======
    public void setId(String id) {
        this.id.set(id);
    }

    public LocalDate getReportDate() {
        return reportDate.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public ObjectProperty<LocalDate> reportDateProperty() {
        return reportDate;
    }

<<<<<<< HEAD
    public int getTotalDonors() {
        return totalDonors.get();
    }

    public void setTotalDonors(int totalDonors) {
        this.totalDonors.set(totalDonors);
=======
    public void setReportDate(LocalDate reportDate) {
        this.reportDate.set(reportDate);
    }

    public int getTotalDonors() {
        return totalDonors.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty totalDonorsProperty() {
        return totalDonors;
    }

<<<<<<< HEAD
    public int getTotalRecipients() {
        return totalRecipients.get();
    }

    public void setTotalRecipients(int totalRecipients) {
        this.totalRecipients.set(totalRecipients);
=======
    public void setTotalDonors(int totalDonors) {
        this.totalDonors.set(totalDonors);
    }

    public int getTotalRecipients() {
        return totalRecipients.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty totalRecipientsProperty() {
        return totalRecipients;
    }

<<<<<<< HEAD
    public int getTotalBloodUnits() {
        return totalBloodUnits.get();
    }

    public void setTotalBloodUnits(int totalBloodUnits) {
        this.totalBloodUnits.set(totalBloodUnits);
=======
    public void setTotalRecipients(int totalRecipients) {
        this.totalRecipients.set(totalRecipients);
    }

    public int getTotalBloodUnits() {
        return totalBloodUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty totalBloodUnitsProperty() {
        return totalBloodUnits;
    }

<<<<<<< HEAD
    public int getActiveCampaigns() {
        return activeCampaigns.get();
    }

    public void setActiveCampaigns(int activeCampaigns) {
        this.activeCampaigns.set(activeCampaigns);
    }

    public IntegerProperty activeCampaignsProperty() {
        return activeCampaigns;
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public int getExpiringSoon() {
        return expiringSoon.get();
    }

<<<<<<< HEAD
=======
    public IntegerProperty expiringSoonProperty() {
        return expiringSoon;
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public void setExpiringSoon(int expiringSoon) {
        this.expiringSoon.set(expiringSoon);
    }

<<<<<<< HEAD
    public IntegerProperty expiringSoonProperty() {
        return expiringSoon;
=======
    public int getActiveCampaigns() {
        return activeCampaigns.get();
    }

    public IntegerProperty activeCampaignsProperty() {
        return activeCampaigns;
    }

    public void setActiveCampaigns(int activeCampaigns) {
        this.activeCampaigns.set(activeCampaigns);
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public int getEmergencyRequests() {
        return emergencyRequests.get();
    }

<<<<<<< HEAD
    public void setEmergencyRequests(int emergencyRequests) {
        this.emergencyRequests.set(emergencyRequests);
    }

=======
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public IntegerProperty emergencyRequestsProperty() {
        return emergencyRequests;
    }

<<<<<<< HEAD
    public int getAPositiveUnits() {
        return aPositiveUnits.get();
    }

    public void setAPositiveUnits(int aPositiveUnits) {
        this.aPositiveUnits.set(aPositiveUnits);
=======
    public void setEmergencyRequests(int emergencyRequests) {
        this.emergencyRequests.set(emergencyRequests);
    }

    // Blood group specific getters and setters
    public int getAPositiveUnits() {
        return aPositiveUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty aPositiveUnitsProperty() {
        return aPositiveUnits;
    }

<<<<<<< HEAD
    public int getANegativeUnits() {
        return aNegativeUnits.get();
    }

    public void setANegativeUnits(int aNegativeUnits) {
        this.aNegativeUnits.set(aNegativeUnits);
=======
    public void setAPositiveUnits(int aPositiveUnits) {
        this.aPositiveUnits.set(aPositiveUnits);
    }

    public int getANegativeUnits() {
        return aNegativeUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty aNegativeUnitsProperty() {
        return aNegativeUnits;
    }

<<<<<<< HEAD
    public int getBPositiveUnits() {
        return bPositiveUnits.get();
    }

    public void setBPositiveUnits(int bPositiveUnits) {
        this.bPositiveUnits.set(bPositiveUnits);
=======
    public void setANegativeUnits(int aNegativeUnits) {
        this.aNegativeUnits.set(aNegativeUnits);
    }

    public int getBPositiveUnits() {
        return bPositiveUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty bPositiveUnitsProperty() {
        return bPositiveUnits;
    }

<<<<<<< HEAD
    public int getBNegativeUnits() {
        return bNegativeUnits.get();
    }

    public void setBNegativeUnits(int bNegativeUnits) {
        this.bNegativeUnits.set(bNegativeUnits);
=======
    public void setBPositiveUnits(int bPositiveUnits) {
        this.bPositiveUnits.set(bPositiveUnits);
    }

    public int getBNegativeUnits() {
        return bNegativeUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty bNegativeUnitsProperty() {
        return bNegativeUnits;
    }

<<<<<<< HEAD
    public int getABPositiveUnits() {
        return abPositiveUnits.get();
    }

    public void setABPositiveUnits(int abPositiveUnits) {
        this.abPositiveUnits.set(abPositiveUnits);
=======
    public void setBNegativeUnits(int bNegativeUnits) {
        this.bNegativeUnits.set(bNegativeUnits);
    }

    public int getABPositiveUnits() {
        return abPositiveUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty abPositiveUnitsProperty() {
        return abPositiveUnits;
    }

<<<<<<< HEAD
    public int getABNegativeUnits() {
        return abNegativeUnits.get();
    }

    public void setABNegativeUnits(int abNegativeUnits) {
        this.abNegativeUnits.set(abNegativeUnits);
=======
    public void setABPositiveUnits(int abPositiveUnits) {
        this.abPositiveUnits.set(abPositiveUnits);
    }

    public int getABNegativeUnits() {
        return abNegativeUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty abNegativeUnitsProperty() {
        return abNegativeUnits;
    }

<<<<<<< HEAD
    public int getOPositiveUnits() {
        return oPositiveUnits.get();
    }

    public void setOPositiveUnits(int oPositiveUnits) {
        this.oPositiveUnits.set(oPositiveUnits);
=======
    public void setABNegativeUnits(int abNegativeUnits) {
        this.abNegativeUnits.set(abNegativeUnits);
    }

    public int getOPositiveUnits() {
        return oPositiveUnits.get();
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }

    public IntegerProperty oPositiveUnitsProperty() {
        return oPositiveUnits;
    }

<<<<<<< HEAD
=======
    public void setOPositiveUnits(int oPositiveUnits) {
        this.oPositiveUnits.set(oPositiveUnits);
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public int getONegativeUnits() {
        return oNegativeUnits.get();
    }

<<<<<<< HEAD
=======
    public IntegerProperty oNegativeUnitsProperty() {
        return oNegativeUnits;
    }

>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    public void setONegativeUnits(int oNegativeUnits) {
        this.oNegativeUnits.set(oNegativeUnits);
    }

<<<<<<< HEAD
    public IntegerProperty oNegativeUnitsProperty() {
        return oNegativeUnits;
=======
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
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
    }
}