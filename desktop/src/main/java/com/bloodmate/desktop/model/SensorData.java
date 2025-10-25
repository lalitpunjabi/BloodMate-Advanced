package com.bloodmate.desktop.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDateTime;

public class SensorData {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty sensorId = new SimpleStringProperty();
    private final StringProperty sensorType = new SimpleStringProperty();
    private final DoubleProperty value = new SimpleDoubleProperty();
    private final StringProperty unit = new SimpleStringProperty();
    private final StringProperty location = new SimpleStringProperty();
    private final StringProperty bloodBagId = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> timestamp = new SimpleObjectProperty<>();
    private final StringProperty status = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getSensorId() {
        return sensorId.get();
    }

    public void setSensorId(String sensorId) {
        this.sensorId.set(sensorId);
    }

    public StringProperty sensorIdProperty() {
        return sensorId;
    }

    public String getSensorType() {
        return sensorType.get();
    }

    public void setSensorType(String sensorType) {
        this.sensorType.set(sensorType);
    }

    public StringProperty sensorTypeProperty() {
        return sensorType;
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public String getBloodBagId() {
        return bloodBagId.get();
    }

    public void setBloodBagId(String bloodBagId) {
        this.bloodBagId.set(bloodBagId);
    }

    public StringProperty bloodBagIdProperty() {
        return bloodBagId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp.set(timestamp);
    }

    public ObjectProperty<LocalDateTime> timestampProperty() {
        return timestamp;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}