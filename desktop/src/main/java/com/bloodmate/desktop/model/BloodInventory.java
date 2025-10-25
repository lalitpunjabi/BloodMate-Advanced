package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BloodInventory {
	private final StringProperty id = new SimpleStringProperty();
	private final StringProperty bloodGroup = new SimpleStringProperty();
	private final StringProperty status = new SimpleStringProperty();
	private final StringProperty location = new SimpleStringProperty();

	public String getId() { return id.get(); }
	public void setId(String v) { id.set(v); }
	public StringProperty idProperty() { return id; }

	public String getBloodGroup() { return bloodGroup.get(); }
	public void setBloodGroup(String v) { bloodGroup.set(v); }
	public StringProperty bloodGroupProperty() { return bloodGroup; }

	public String getStatus() { return status.get(); }
	public void setStatus(String v) { status.set(v); }
	public StringProperty statusProperty() { return status; }

	public String getLocation() { return location.get(); }
	public void setLocation(String v) { location.set(v); }
	public StringProperty locationProperty() { return location; }
}