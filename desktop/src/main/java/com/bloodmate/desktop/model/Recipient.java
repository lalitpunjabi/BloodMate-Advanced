package com.bloodmate.desktop.model;

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
}