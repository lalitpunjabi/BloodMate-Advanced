package com.bloodmate.desktop.model;

<<<<<<< HEAD
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Recipient {
	private final StringProperty id = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty phone = new SimpleStringProperty();
	private final StringProperty bloodGroup = new SimpleStringProperty();
	private final StringProperty hospitalName = new SimpleStringProperty();
	private final StringProperty urgencyLevel = new SimpleStringProperty();
	private final StringProperty status = new SimpleStringProperty();

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

	public String getHospitalName() { return hospitalName.get(); }
	public void setHospitalName(String v) { hospitalName.set(v); }
	public StringProperty hospitalNameProperty() { return hospitalName; }

	public String getUrgencyLevel() { return urgencyLevel.get(); }
	public void setUrgencyLevel(String v) { urgencyLevel.set(v); }
	public StringProperty urgencyLevelProperty() { return urgencyLevel; }

	public String getStatus() { return status.get(); }
	public void setStatus(String v) { status.set(v); }
	public StringProperty statusProperty() { return status; }
=======
import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Recipient {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty patientName = new SimpleStringProperty();
    private final StringProperty bloodGroup = new SimpleStringProperty();
    private final StringProperty medicalFacility = new SimpleStringProperty();
    private final StringProperty doctorName = new SimpleStringProperty();
    private final StringProperty contactNumber = new SimpleStringProperty();
    private final StringProperty urgencyLevel = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
    private final StringProperty medicalHistory = new SimpleStringProperty();
    private final IntegerProperty unitsRequired = new SimpleIntegerProperty();
    private final ObjectProperty<LocalDateTime> requestDate = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDateTime> requiredBy = new SimpleObjectProperty<>();
    private final StringProperty reason = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();

    // ID
    public String getId() { return id.get(); }
    public void setId(String v) { id.set(v); }
    public StringProperty idProperty() { return id; }

    // Patient Name
    public String getPatientName() { return patientName.get(); }
    public void setPatientName(String v) { patientName.set(v); }
    public StringProperty patientNameProperty() { return patientName; }

    // Blood Group
    public String getBloodGroup() { return bloodGroup.get(); }
    public void setBloodGroup(String v) { bloodGroup.set(v); }
    public StringProperty bloodGroupProperty() { return bloodGroup; }

    // Medical Facility
    public String getMedicalFacility() { return medicalFacility.get(); }
    public void setMedicalFacility(String v) { medicalFacility.set(v); }
    public StringProperty medicalFacilityProperty() { return medicalFacility; }

    // Doctor Name
    public String getDoctorName() { return doctorName.get(); }
    public void setDoctorName(String v) { doctorName.set(v); }
    public StringProperty doctorNameProperty() { return doctorName; }

    // Contact Number
    public String getContactNumber() { return contactNumber.get(); }
    public void setContactNumber(String v) { contactNumber.set(v); }
    public StringProperty contactNumberProperty() { return contactNumber; }

    // Urgency Level
    public String getUrgencyLevel() { return urgencyLevel.get(); }
    public void setUrgencyLevel(String v) { urgencyLevel.set(v); }
    public StringProperty urgencyLevelProperty() { return urgencyLevel; }

    // Status
    public String getStatus() { return status.get(); }
    public void setStatus(String v) { status.set(v); }
    public StringProperty statusProperty() { return status; }

    // Date of Birth
    public LocalDate getDateOfBirth() { return dateOfBirth.get(); }
    public void setDateOfBirth(LocalDate v) { dateOfBirth.set(v); }
    public ObjectProperty<LocalDate> dateOfBirthProperty() { return dateOfBirth; }

    // Medical History
    public String getMedicalHistory() { return medicalHistory.get(); }
    public void setMedicalHistory(String v) { medicalHistory.set(v); }
    public StringProperty medicalHistoryProperty() { return medicalHistory; }

    // Units Required
    public int getUnitsRequired() { return unitsRequired.get(); }
    public void setUnitsRequired(int v) { unitsRequired.set(v); }
    public IntegerProperty unitsRequiredProperty() { return unitsRequired; }

    // Request Date
    public LocalDateTime getRequestDate() { return requestDate.get(); }
    public void setRequestDate(LocalDateTime v) { requestDate.set(v); }
    public ObjectProperty<LocalDateTime> requestDateProperty() { return requestDate; }

    // Required By
    public LocalDateTime getRequiredBy() { return requiredBy.get(); }
    public void setRequiredBy(LocalDateTime v) { requiredBy.set(v); }
    public ObjectProperty<LocalDateTime> requiredByProperty() { return requiredBy; }

    // Reason
    public String getReason() { return reason.get(); }
    public void setReason(String v) { reason.set(v); }
    public StringProperty reasonProperty() { return reason; }

    // Location
    public String getLocation() { return location.get(); }
    public void setLocation(String v) { location.set(v); }
    public StringProperty locationProperty() { return location; }
>>>>>>> 50398a20edd769a7b8bc38c41f4b04b35038c697
}