package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Donor {
	private final StringProperty id = new SimpleStringProperty();
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty phone = new SimpleStringProperty();
	private final StringProperty bloodGroup = new SimpleStringProperty();

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
}
