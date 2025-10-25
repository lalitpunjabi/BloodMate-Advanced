package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Campaign {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> startDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> endDate = new SimpleObjectProperty<>();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty city = new SimpleStringProperty();
    private final StringProperty state = new SimpleStringProperty();
    private final IntegerProperty targetUnits = new SimpleIntegerProperty();
    private final IntegerProperty collectedUnits = new SimpleIntegerProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty organizer = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> createdDate = new SimpleObjectProperty<>();
    private final IntegerProperty participantCount = new SimpleIntegerProperty();
    private final StringProperty requirements = new SimpleStringProperty();
    private final StringProperty incentives = new SimpleStringProperty();

    // ID
    public String getId() { return id.get(); }
    public void setId(String v) { id.set(v); }
    public StringProperty idProperty() { return id; }

    // Title
    public String getTitle() { return title.get(); }
    public void setTitle(String v) { title.set(v); }
    public StringProperty titleProperty() { return title; }

    // Description
    public String getDescription() { return description.get(); }
    public void setDescription(String v) { description.set(v); }
    public StringProperty descriptionProperty() { return description; }

    // Start Date
    public LocalDate getStartDate() { return startDate.get(); }
    public void setStartDate(LocalDate v) { startDate.set(v); }
    public ObjectProperty<LocalDate> startDateProperty() { return startDate; }

    // End Date
    public LocalDate getEndDate() { return endDate.get(); }
    public void setEndDate(LocalDate v) { endDate.set(v); }
    public ObjectProperty<LocalDate> endDateProperty() { return endDate; }

    // Location
    public String getLocation() { return location.get(); }
    public void setLocation(String v) { location.set(v); }
    public StringProperty locationProperty() { return location; }

    // City
    public String getCity() { return city.get(); }
    public void setCity(String v) { city.set(v); }
    public StringProperty cityProperty() { return city; }

    // State
    public String getState() { return state.get(); }
    public void setState(String v) { state.set(v); }
    public StringProperty stateProperty() { return state; }

    // Target Units
    public int getTargetUnits() { return targetUnits.get(); }
    public void setTargetUnits(int v) { targetUnits.set(v); }
    public IntegerProperty targetUnitsProperty() { return targetUnits; }

    // Collected Units
    public int getCollectedUnits() { return collectedUnits.get(); }
    public void setCollectedUnits(int v) { collectedUnits.set(v); }
    public IntegerProperty collectedUnitsProperty() { return collectedUnits; }

    // Status
    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    // Organizer
    public String getOrganizer() { return organizer.get(); }
    public void setOrganizer(String v) { organizer.set(v); }
    public StringProperty organizerProperty() { return organizer; }

    // Contact Number
    public String getContactNumber() { return contactNumber.get(); }
    public void setContactNumber(String v) { contactNumber.set(v); }
    public StringProperty contactNumberProperty() { return contactNumber; }

    // Created Date
    public LocalDateTime getCreatedDate() { return createdDate.get(); }
    public void setCreatedDate(LocalDateTime v) { createdDate.set(v); }
    public ObjectProperty<LocalDateTime> createdDateProperty() { return createdDate; }

    // Participant Count
    public int getParticipantCount() { return participantCount.get(); }
    public void setParticipantCount(int v) { participantCount.set(v); }
    public IntegerProperty participantCountProperty() { return participantCount; }

    // Requirements
    public String getRequirements() { return requirements.get(); }
    public void setRequirements(String v) { requirements.set(v); }
    public StringProperty requirementsProperty() { return requirements; }

    // Incentives
    public String getIncentives() { return incentives.get(); }
    public void setIncentives(String v) { incentives.set(v); }
    public StringProperty incentivesProperty() { return incentives; }
}